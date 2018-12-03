package es.regalocio.partners.struts.action.giftvoucher;

import es.regalocio.partners.business.common.*;
import es.regalocio.partners.business.common.shared.GiftVoucherNotFoundException;
import es.regalocio.partners.business.common.shared.InvalidCurrentGiftVoucherStatusException;
import es.regalocio.partners.business.services.GiftVoucherService;
import es.regalocio.partners.config.PartnersServices;
import es.regalocio.partners.config.PartnersUtils;
import es.regalocio.partners.shared.SessionInfo;
import es.regalocio.partners.struts.action.DynaValidatorFormAction;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.validator.DynaValidatorForm;

public class GiftVoucherStatusAction extends DynaValidatorFormAction {

  private static final double VAT18 = 0.18D;
  private static final double VAT21 = 0.21D;

  private Partner createPartner(HttpServletRequest request) {

    SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(SessionInfo.SESSION_INFO);

    Partner partner = null;
    if (sessionInfo.getUser() instanceof Partner) {
      partner = (Partner) sessionInfo.getUser();
    }
    return partner;
  }

  /**
   *
   * @param ean8AndWord
   * @param thematic
   * @param partner
   * @return
   */
  public ThematicRate getThematicRate(String ean8AndWord, Thematic thematic, Partner partner) {

    ThematicRate rate = (!thematic.isMultiple())
            ? thematic.getThematicRate()
            : thematic.getThematicRate(GiftVoucher.getWordForEAN8(ean8AndWord));

    Double commission = null, priceService = null;
    SpecificAgreement specificAgreement;
    
    if (partner != null) {
      SpecificAgreementList saList = partner.getSpecificAgreementList();
      if (!thematic.isMultiple()) {
        specificAgreement = saList.getSpecificAgreement(
                thematic.getId(), thematic.getThematicRate().getEndOfValidity(), true);
      } else {
        specificAgreement = saList.getSpecificAgreement(
                thematic.getId(), thematic.getThematicRate().getEndOfValidity(), 
                GiftVoucher.getWordForEAN8(ean8AndWord), true);
      }

      if (specificAgreement != null) {
        priceService = specificAgreement.getPriceService();
        commission = specificAgreement.getCommission();
      } else {
        commission = rate.getDefaultCommission();
      }
    }
    if (partner == null || partner.getOpciones() != 1) {
    } else {
      commission = commission / VAT18 * VAT21;
    }

    if (priceService == null || priceService == 0) {
      priceService = rate.getPriceService();
    }

    return new ThematicRate(
            thematic.getThematicRate().getEndOfValidity(),
            thematic.getThematicRate().getEan13(),
            thematic.getThematicRate().isEnabled(),
            thematic.getThematicRate().isCreationEnabled(),
            thematic.getThematicRate().isOrderEnabled(),
            thematic.getThematicRate().getPrice(),
            priceService - commission,
            priceService);
  }

  /**
   *
   * @param giftVoucher
   * @param userId
   * @param newStatusInt
   * @param messages
   * @throws GiftVoucherNotFoundException
   * @throws InvalidCurrentGiftVoucherStatusException
   */
  private void changeStatus(GiftVoucher giftVoucher,
          int userId, Integer newStatusInt, ActionMessages messages)
          throws GiftVoucherNotFoundException, InvalidCurrentGiftVoucherStatusException {

    GiftVoucherStatus newStatus = GiftVoucherStatus.fromCode(newStatusInt);
    GiftVoucherService service = PartnersServices.createGiftVoucherService();

    if (newStatus.equals(GiftVoucherStatus.ACTIVO)) {

      if (giftVoucher.getStatus().equals(GiftVoucherStatus.RESERVADO)) {
        service.reactivate(giftVoucher.getGiftVoucherNumberAndWord(), userId, false);
      }

    } else if (newStatus.equals(GiftVoucherStatus.RESERVADO)) {

      service.reserve(giftVoucher.getGiftVoucherNumberAndWord(), userId);

    } else {

      messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("gift.voucher.change.status.unattented.case.error"));
    }
  }

  @Override
  public void doValidate(ActionMapping mapping, ActionMessages messages,
          DynaValidatorForm form, HttpServletRequest request, HttpServletResponse response) {

    if (!messages.isEmpty()) {
      return;
    }

    SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(SessionInfo.SESSION_INFO);
    GiftVoucherService service = PartnersServices.createGiftVoucherService();

    String ean8Word = form.getString("ean8").toUpperCase();
    try {
      GiftVoucher giftVoucher = service.getGiftVoucher(ean8Word, false);
      if (giftVoucher.getThematicId() >= 291 && giftVoucher.getThematicId() <= 294 && GiftVoucher.getWordForEAN8(ean8Word) == ' ') {

        String msg;
        switch (giftVoucher.getThematicId()) {
          case 291:
            msg = "gift.voucher.cheques.291";
            break;
          case 292:
            msg = "gift.voucher.cheques.292";
            break;
          case 293:
            msg = "gift.voucher.cheques.293";
            break;
          case 294:
            msg = "gift.voucher.cheques.294";
            break;
          default:
            msg = "gift.voucher.cheques.otros";
            break;
        }
        messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(msg));

        return;
      }

      Thematic thematic = PartnersUtils.getInstance().getThematic(giftVoucher.getThematicId(), giftVoucher.getEndOfValidity());
      ThematicRate thematicRate = getThematicRate(ean8Word, thematic, createPartner(request));

      Integer newStatus = (Integer) form.get("new_status");
      if (newStatus != null) {
        changeStatus(giftVoucher, sessionInfo.getUser().getId(), newStatus, messages);
        giftVoucher = service.getGiftVoucher(form.getString("ean8"), true);
        form.set("new_status", null);
      }

      request.setAttribute("gift_voucher", giftVoucher);
      request.setAttribute("thematic", thematic);
      request.setAttribute("thematic_rate", thematicRate);

      if (!thematic.isMultiple() || giftVoucher.getWord() != ' ') {

        List<GiftVoucherStatus> status = new ArrayList<>();
        if (giftVoucher.getStatus().equals(GiftVoucherStatus.ACTIVO)) {
          status.add(GiftVoucherStatus.RESERVADO);
        }

        if (giftVoucher.getStatus().equals(GiftVoucherStatus.RESERVADO)) {
          // Comprueba si el colaborador que lo ha reservado es éste
          int reservationUser = service.getUserHasReservedEAN8(ean8Word);
          if (reservationUser == sessionInfo.getUser().getId()) {
            status.add(GiftVoucherStatus.ACTIVO);
          }
        }

        if (!status.isEmpty()) {
          request.setAttribute("change_status_list", status);
        }
      }
      
      if (thematic.isMultiple() && (giftVoucher.getStatus().equals(GiftVoucherStatus.ACTIVO) 
              || giftVoucher.getStatus().equals(GiftVoucherStatus.UTILIZADO))) {

        request.setAttribute("message_multiple", true);
      }
      
      request.setAttribute("repayment_invoice_number", service.getRepaymentForEAN8(giftVoucher.getGiftVoucherNumber()));

    } catch (GiftVoucherNotFoundException e) {
      messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("gift.voucher.not.found.error"));
    } catch (InvalidCurrentGiftVoucherStatusException ex) {
      Logger.getLogger(GiftVoucherStatusAction.class.getName()).log(Level.SEVERE, null, ex);
    }

  }

  @Override
  public ActionForward doExecute(ActionMapping mapping, DynaValidatorForm form,
          HttpServletRequest request, HttpServletResponse response) {

    return new ActionForward(mapping.getInput());
  }

}

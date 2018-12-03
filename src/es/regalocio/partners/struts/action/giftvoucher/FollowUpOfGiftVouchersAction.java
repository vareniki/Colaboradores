package es.regalocio.partners.struts.action.giftvoucher;

import es.regalocio.partners.business.common.EntityOfDistribution;
import es.regalocio.partners.business.common.GiftVoucher;
import es.regalocio.partners.business.common.GiftVoucherStatus;
import es.regalocio.partners.business.common.RetailOutlet;
import es.regalocio.partners.business.common.StaticDefinition;
import es.regalocio.partners.business.common.shared.GiftVoucherNotFoundException;
import es.regalocio.partners.business.common.shared.InvalidCurrentGiftVoucherStatusException;
import es.regalocio.partners.business.common.shared.InvalidEAN13Exception;
import es.regalocio.partners.business.common.shared.InvalidEntityException;
import es.regalocio.partners.business.common.shared.RetailOutletReturnNotFoundException;
import es.regalocio.partners.business.services.GiftVoucherService;
import es.regalocio.partners.config.PartnersServices;
import es.regalocio.partners.config.PartnersUtils;
import es.regalocio.partners.shared.SessionInfo;
import es.regalocio.partners.struts.action.DynaValidatorFormAction;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.validator.DynaValidatorForm;

public class FollowUpOfGiftVouchersAction extends DynaValidatorFormAction {

  /**
   * 
   * @param giftVoucher
   * @param sessionInfo
   * @param newStatusInt
   * @param messages
   * @throws GiftVoucherNotFoundException
   * @throws InvalidCurrentGiftVoucherStatusException
   * @throws InvalidEAN13Exception
   * @throws InvalidEntityException
   * @throws RetailOutletReturnNotFoundException 
   */
  private void changeStatus(GiftVoucher giftVoucher, SessionInfo sessionInfo, Integer newStatusInt, ActionMessages messages)
          throws GiftVoucherNotFoundException, InvalidCurrentGiftVoucherStatusException, InvalidEAN13Exception, InvalidEntityException, RetailOutletReturnNotFoundException {

    GiftVoucherStatus newStatus = GiftVoucherStatus.fromCode(newStatusInt);
    GiftVoucherService service = PartnersServices.createGiftVoucherService();
    
    if (newStatus.equals(GiftVoucherStatus.ACTIVO)) {

      if (giftVoucher.getStatus().equals(GiftVoucherStatus.BLOQUEADO) 
              || giftVoucher.getStatus().equals(GiftVoucherStatus.CADUCADO) 
              || giftVoucher.getStatus().equals(GiftVoucherStatus.RESERVADO)) {
        service.reactivate(giftVoucher.getGiftVoucherNumber(), sessionInfo.getUser().getId(), giftVoucher.getStatus().equals(GiftVoucherStatus.CADUCADO));
      } else {
        service.activate(giftVoucher.getGiftVoucherNumber(), null, new Date(), giftVoucher.getEntityId(), sessionInfo.getUser().getId());
      }

    } else if (newStatus.equals(GiftVoucherStatus.EN_LOGISTICA)) {

      service.sendBackInLogistics(giftVoucher.getGiftVoucherNumber(), sessionInfo.getUser().getId());

    } else if (newStatus.equals(GiftVoucherStatus.CAMBIADO)) {

      service.exchange(giftVoucher.getGiftVoucherNumber(), sessionInfo.getUser().getId());

    } else if (newStatus.equals(GiftVoucherStatus.ROBADO_PERDIDO)) {

      service.registerLossTheft(giftVoucher.getGiftVoucherNumber(), sessionInfo.getUser().getId());

    } else if (newStatus.equals(GiftVoucherStatus.DESTRUIDO)) {

      service.dispose(giftVoucher.getGiftVoucherNumber(), sessionInfo.getUser().getId());

    } else if (newStatus.equals(GiftVoucherStatus.BLOQUEADO)) {

      service.lock(giftVoucher.getGiftVoucherNumber(), sessionInfo.getUser().getId());

    } else if (giftVoucher.getStatus().equals(GiftVoucherStatus.ROBADO_PERDIDO)) {

      service.reactivate(giftVoucher.getGiftVoucherNumber(), sessionInfo.getUser().getId(), false);

    } else if (giftVoucher.getStatus().equals(GiftVoucherStatus.BLOQUEADO)) {

      service.unlock(giftVoucher.getGiftVoucherNumber(), sessionInfo.getUser().getId(), newStatus);

    } else if (newStatus.equals(GiftVoucherStatus.CADUCADO)) {

      service.expire(giftVoucher.getGiftVoucherNumber(), sessionInfo.getUser().getId());

     } else if (newStatus.equals(GiftVoucherStatus.RESERVADO)) {

      service.reserve(giftVoucher.getGiftVoucherNumber(), sessionInfo.getUser().getId());
      
    } else {

      messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("gift.voucher.change.status.unattented.case.error"));
    }
  }

  @Override
  public void doValidate(ActionMapping mapping, ActionMessages messages,
          DynaValidatorForm form, HttpServletRequest request, HttpServletResponse response) {

    SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(SessionInfo.SESSION_INFO);
    int userType = PartnersUtils.getInstance().getUserType(sessionInfo.getUser());
    if (!messages.isEmpty()) {
      return;
    }
    PartnersUtils partnersUtils = PartnersUtils.getInstance();
    GiftVoucherService service = PartnersServices.createGiftVoucherService();
    try {
      GiftVoucher giftVoucher = service.getGiftVoucher(form.getString("ean8"), (userType == 1));
      
      Integer newStatusInt = (Integer) form.get("new_status");
      if (newStatusInt != null) {
        changeStatus(giftVoucher, sessionInfo, newStatusInt, messages);
        giftVoucher = service.getGiftVoucher(form.getString("ean8"), (userType == 1));
        form.set("new_status", null);
      }

      request.setAttribute("gift_voucher", giftVoucher);
      request.setAttribute("thematic", partnersUtils.getThematic(giftVoucher.getThematicId(), giftVoucher.getEndOfValidity()));
      request.setAttribute("thematic_rate", partnersUtils.getThematicRate(giftVoucher.getThematicId(), giftVoucher.getEndOfValidity()));
      request.setAttribute("repayment_invoice_id", service.getRepaymentIdForEAN8(giftVoucher.getGiftVoucherNumber()));
      request.setAttribute("repayment_invoice_number", service.getRepaymentForEAN8(giftVoucher.getGiftVoucherNumber()));

      if (sessionInfo.getUser().hasProfile(StaticDefinition.EAN8_OPERATIONS_PROFILE_CODE)) {
        request.setAttribute("change_status_list", service.getChangeStatusList(giftVoucher.getStatus()));
      }

      if (giftVoucher.getEntityId() != null) {
        EntityOfDistribution entityOfDistribution = PartnersServices.createDistributionService().getEntityOfDistribution(giftVoucher.getEntityId());
        request.setAttribute("network", entityOfDistribution.getDistributionNetwork().getName());

        if (entityOfDistribution instanceof RetailOutlet) {
          request.setAttribute("retail_outlet", ((RetailOutlet) entityOfDistribution).getName());
        }
      }

    } catch (GiftVoucherNotFoundException e) {
      messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("gift.voucher.not.found.error"));
    } catch (InvalidCurrentGiftVoucherStatusException | InvalidEAN13Exception | InvalidEntityException | RetailOutletReturnNotFoundException ex) {
      Logger.getLogger(FollowUpOfGiftVouchersAction.class.getName()).log(Level.SEVERE, null, ex);
    }

  }

  @Override
  public ActionForward doExecute(ActionMapping mapping, DynaValidatorForm form,
          HttpServletRequest request, HttpServletResponse response) {
    return new ActionForward(mapping.getInput());
  }
}

package es.regalocio.partners.struts.action.giftvoucher;

import es.regalocio.partners.business.common.GiftVoucher;
import es.regalocio.partners.business.common.GiftVoucherStatus;
import es.regalocio.partners.business.common.RetailOutlet;
import es.regalocio.partners.business.common.shared.GiftVoucherNotFoundException;
import es.regalocio.partners.business.common.shared.InvalidCurrentGiftVoucherStatusException;
import es.regalocio.partners.business.common.shared.InvalidEAN13Exception;
import es.regalocio.partners.business.common.shared.InvalidEntityException;
import es.regalocio.partners.business.common.shared.PartnersException;
import es.regalocio.partners.business.services.GiftVoucherService;
import es.regalocio.partners.config.PartnersServices;
import es.regalocio.partners.shared.SessionInfo;
import es.regalocio.partners.struts.action.DynaValidatorFormAction;

import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.validator.DynaValidatorForm;

public class GiftVoucherActivationAction extends DynaValidatorFormAction {

  @Override
  public void doValidate(ActionMapping mapping, ActionMessages messages, DynaValidatorForm form,
          HttpServletRequest request, HttpServletResponse response) throws PartnersException {

    if (!messages.isEmpty()) {
      return;
    }

    try {
      SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(SessionInfo.SESSION_INFO);
      RetailOutlet retailOutlet = (RetailOutlet) sessionInfo.getUser();

      String expedientNumber = form.getString("expedient_number");
      if (expedientNumber != null && expedientNumber.trim().equals("")) {
        messages.add(ActionMessages.GLOBAL_MESSAGE,
                new ActionMessage("gift.voucher.expedient_number.not.found.error"));

        return;
      }
      if (expedientNumber != null && expedientNumber.equalsIgnoreCase("(NA)")) {
        expedientNumber = null;
      }
      
      GiftVoucherService service = PartnersServices.createGiftVoucherService();
      
      GiftVoucher giftVoucher = service.getGiftVoucher(form.getString("ean8"), false);

      if (giftVoucher.getStatus() != GiftVoucherStatus.EN_LOGISTICA && giftVoucher.getStatus() != GiftVoucherStatus.EN_DISTRIBUCION) {
        throw new InvalidCurrentGiftVoucherStatusException(
                giftVoucher.getStatus(), new GiftVoucherStatus[] {GiftVoucherStatus.EN_LOGISTICA, GiftVoucherStatus.EN_DISTRIBUCION});
      }

      if (retailOutlet.getDistributionNetwork().getEntityId() != 5818 && giftVoucher.getThematicId() >= 101 && giftVoucher.getThematicId() <= 299) {
        messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("coleccion.obsoleta"));

        return;
      }
      
      try {
        service.activate(form.getString("ean8"), null, new Date(), retailOutlet.getEntityId(), retailOutlet.getId(), expedientNumber);
      } catch (InvalidEAN13Exception e) {
        throw new PartnersException(e.getMessage(), e);
      }
    } catch (GiftVoucherNotFoundException e) {
      messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("gift.voucher.not.found.error"));
    } catch (InvalidCurrentGiftVoucherStatusException e) {
      messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("gift.voucher.invalid.status.error", new Object[]{e.getCurrentStatus().getLabel().toLowerCase()}));
    } catch (InvalidEntityException e) {
      messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("gift.voucher.invalid.entity"));
    }

  }

  @Override
  public ActionForward doExecute(ActionMapping mapping, DynaValidatorForm form, 
          HttpServletRequest request, HttpServletResponse response) {

    ActionMessages messages = new ActionMessages();
    messages.add(org.apache.struts.Globals.MESSAGE_KEY, new ActionMessage("gift.voucher.activation.success"));
    saveMessages(request.getSession(), messages);

    return mapping.findForward("success");
  }
}

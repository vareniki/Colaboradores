package es.regalocio.partners.struts.action.giftvoucher;

import es.regalocio.partners.business.common.HandlingOfReturn;
import es.regalocio.partners.business.common.RetailOutlet;
import es.regalocio.partners.business.common.RetailOutletReturn;
import es.regalocio.partners.business.common.shared.*;
import es.regalocio.partners.business.services.GiftVoucherService;
import es.regalocio.partners.config.PartnersServices;
import es.regalocio.partners.shared.SessionInfo;
import es.regalocio.partners.struts.action.DynaValidatorFormAction;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.validator.DynaValidatorForm;

public class GiftVoucherReturnAction extends DynaValidatorFormAction {

  @Override
  public void doValidate(ActionMapping mapping, ActionMessages messages,
          DynaValidatorForm form, HttpServletRequest request, HttpServletResponse response) {

    if (!messages.isEmpty()) {
      return;
    }
    try {
      SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(SessionInfo.SESSION_INFO);
      RetailOutlet retailOutlet = (RetailOutlet) sessionInfo.getUser();

      GiftVoucherService service = PartnersServices.createGiftVoucherService();
      service.registerRetailOutletReturn(new RetailOutletReturn(retailOutlet.getEntityId(), form.getString("ean8"),
              HandlingOfReturn.fromCode((Integer) form.get("handling_code"))), retailOutlet.getId());

    } catch (GiftVoucherNotFoundException e) {
      messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("gift.voucher.not.found.error"));
    } catch (InvalidCurrentGiftVoucherStatusException e) {
      messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("gift.voucher.invalid.status.error",
              new Object[]{e.getCurrentStatus().getLabel().toLowerCase()}));
    } catch (InvalidEntityException e) {
      messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("gift.voucher.return.invalid.entity.error"));
    } catch (ObsoleteGiftVoucherException e) {
      messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("gift.voucher.return.invalid.obsolete.error"));
    } catch (SoldMoreThanXDaysException e) {
      messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("gift.voucher.return.invalid.soldmorethanxdays.error"));
    }
  }

  @Override
  public ActionForward doExecute(ActionMapping mapping, DynaValidatorForm form,
          HttpServletRequest request, HttpServletResponse response) {

    ActionMessages messages = new ActionMessages();
    messages.add(org.apache.struts.Globals.MESSAGE_KEY, new ActionMessage("gift.voucher.return.success"));
    saveMessages(request.getSession(), messages);

    return mapping.findForward("success");
  }
}

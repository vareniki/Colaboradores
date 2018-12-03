package es.regalocio.partners.struts.action.repayment;

import es.regalocio.partners.business.common.Partner;
import es.regalocio.partners.business.common.Repayment;
import es.regalocio.partners.business.common.User;
import es.regalocio.partners.business.common.shared.GiftVoucherAlreadyUsedException;
import es.regalocio.partners.business.common.shared.PartnersException;
import es.regalocio.partners.business.common.shared.UniqueGiftVoucherException;
import es.regalocio.partners.config.PartnersServices;
import es.regalocio.partners.config.PartnersUtils;
import es.regalocio.partners.shared.SessionInfo;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.*;
import org.apache.struts.validator.DynaValidatorForm;

public class AddRepaymentAction extends RepaymentAction {

  @Override
  public void runOnce(ActionMapping mapping, DynaValidatorForm form,
          HttpServletRequest request, HttpServletResponse response) {

    SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(SessionInfo.SESSION_INFO);
    User user = sessionInfo.getUser();
    if (user instanceof Partner) {
      
      form.set("partner_id", user.getId());
      request.setAttribute("partner", user);
      
    } else if (form.get("partner_id") != null) {
      
      Integer partnerId = (Integer) form.get("partner_id");
      if (partnerId > 0) {
        request.setAttribute("partner", PartnersServices.createPartnerService().getPartnerForId(partnerId));
      }

    }
  }

  @Override
  public void doValidate(ActionMapping mapping, ActionMessages messages, DynaValidatorForm form,
          HttpServletRequest request, HttpServletResponse response) throws PartnersException {

    if (!messages.isEmpty()) {
      return;
    }

    Repayment repayment;
    try {
      repayment = createRepayment(form, request);
    } catch (UniqueGiftVoucherException e) {

      String thematicName = PartnersUtils.getInstance().getThematic(e.getGiftVoucher().getThematicId(), e.getGiftVoucher().getEndOfValidity()).getName();
      messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("repayment.unique.gift.voucher.error", new Object[]{
        thematicName, e.getGiftVoucher().getEndOfValidity(), e.getGiftVoucher().getGiftVoucherNumber()}));

      return;

    } catch (GiftVoucherAlreadyUsedException e) {

      String thematicName = PartnersUtils.getInstance().getThematic(e.getGiftVoucher().getThematicId(), e.getGiftVoucher().getEndOfValidity()).getName();
      messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("repayment.gift.voucher.already.used", new Object[]{
        thematicName, e.getGiftVoucher().getEndOfValidity(), e.getGiftVoucher().getGiftVoucherNumber(), e.getInvoiceNumber()}));

      return;
    }

    form.set("repayment_id", repayment.getId());
  }

  @Override
  public ActionForward doExecute(ActionMapping mapping, DynaValidatorForm form, HttpServletRequest request,
          HttpServletResponse response) {

    ActionMessages messages = new ActionMessages();
    messages.add(org.apache.struts.Globals.MESSAGE_KEY, new ActionMessage("add.repayment.success"));
    saveMessages(request.getSession(), messages);

    StringBuilder path = new StringBuilder(mapping.findForward("success").getPath());
    path.append("?repayment_id=");
    path.append(form.get("repayment_id"));

    return new RedirectingActionForward(path.toString());
  }

  private Repayment createRepayment(DynaValidatorForm form, HttpServletRequest request)
          throws GiftVoucherAlreadyUsedException, UniqueGiftVoucherException {

    SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(SessionInfo.SESSION_INFO);
    return PartnersServices.createRepaymentService().createRepayment(createRepayment(form), sessionInfo.getUser());
  }

  private Repayment createRepayment(DynaValidatorForm form) throws UniqueGiftVoucherException {
    Repayment repayment = new Repayment((Integer) form.get("partner_id"));
    createGiftVouchers(repayment, form);
    return repayment;
  }
}

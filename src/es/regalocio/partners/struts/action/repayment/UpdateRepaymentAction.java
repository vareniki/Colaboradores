package es.regalocio.partners.struts.action.repayment;

import es.regalocio.partners.business.common.GiftVoucher;
import es.regalocio.partners.business.common.Repayment;
import es.regalocio.partners.business.common.shared.GiftVoucherAlreadyUsedException;
import es.regalocio.partners.business.common.shared.UniqueGiftVoucherException;
import es.regalocio.partners.business.services.RepaymentService;
import es.regalocio.partners.config.PartnersServices;
import es.regalocio.partners.config.PartnersUtils;
import es.regalocio.partners.shared.SessionInfo;
import java.text.MessageFormat;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.*;
import org.apache.struts.validator.DynaValidatorForm;

public class UpdateRepaymentAction extends RepaymentAction {

  @Override
  public void runOnce(ActionMapping mapping, DynaValidatorForm form, HttpServletRequest request, HttpServletResponse response) {

    RepaymentService service = PartnersServices.createRepaymentService();
    Repayment repayment = service.getRepaymentForId((Integer) form.get("repayment_id"));

    String[] giftVouchers = new String[repayment.getGiftVoucherList().size()];
    MessageFormat fmt = new MessageFormat("{0,number,integer}/{1,date,ddMMyyyy}/{2}");

    int counter = 0;
    for (GiftVoucher giftVoucher : repayment.getGiftVoucherList()) {
      giftVouchers[counter++] = fmt.format(new Object[]{
        giftVoucher.getThematicId(), 
        giftVoucher.getEndOfValidity(), 
        giftVoucher.getGiftVoucherNumberAndWord()});
    }

    form.set("partner_id", repayment.getPartnerId());
    form.set("gift_voucher_array", giftVouchers);
  }

  @Override
  public void doValidate(ActionMapping mapping, ActionMessages messages,
          DynaValidatorForm form, HttpServletRequest request, HttpServletResponse response) {

    if (!messages.isEmpty()) {
      return;
    }

    PartnersUtils utils = PartnersUtils.getInstance();
    try {
      RepaymentService service = PartnersServices.createRepaymentService();

      Repayment repayment = service.getRepaymentForId((Integer) form.get("repayment_id"));
      repayment.setPartnerId((Integer) form.get("partner_id"));
      repayment.getGiftVoucherList().clear();
      createGiftVouchers(repayment, form);

      SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(SessionInfo.SESSION_INFO);
      service.updateRepayment(repayment, sessionInfo.getUser().getId());

    } catch (UniqueGiftVoucherException e) {

      GiftVoucher giftVoucher = e.getGiftVoucher();

      messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("repayment.unique.gift.voucher.error", new Object[]{
        utils.getThematic(
        giftVoucher.getThematicId(), giftVoucher.getEndOfValidity()).getName(),
        giftVoucher.getEndOfValidity(), giftVoucher.getGiftVoucherNumber()}));

    } catch (GiftVoucherAlreadyUsedException e) {

      GiftVoucher giftVoucher = e.getGiftVoucher();

      messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("repayment.gift.voucher.already.used", new Object[]{
        utils.getThematic(
        giftVoucher.getThematicId(), giftVoucher.getEndOfValidity()).getName(),
        giftVoucher.getEndOfValidity(), giftVoucher.getGiftVoucherNumber(), e.getInvoiceNumber()}));
    }
  }

  @Override
  public ActionForward doExecute(ActionMapping mapping, DynaValidatorForm form,
          HttpServletRequest request, HttpServletResponse response) {

    ActionMessages messages = new ActionMessages();
    messages.add(org.apache.struts.Globals.MESSAGE_KEY, new ActionMessage("update.repayment.success"));
    saveMessages(request.getSession(), messages);

    StringBuilder path = new StringBuilder(mapping.findForward("success").getPath());
    path.append("?repayment_id=");
    path.append(form.get("repayment_id"));

    return new RedirectingActionForward(path.toString());
  }
}

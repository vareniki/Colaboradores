package es.regalocio.partners.struts.action.order;

import es.regalocio.partners.business.common.RetailOutlet;
import es.regalocio.partners.business.common.RetailOutletOrder;
import es.regalocio.partners.business.common.RetailOutletOrderStatus;
import es.regalocio.partners.business.common.User;
import es.regalocio.partners.business.services.RetailOutletOrderService;
import es.regalocio.partners.config.PartnersServices;
import es.regalocio.partners.shared.SessionInfo;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.validator.DynaValidatorForm;

public class AddRetailOutletOrderAction extends RetailOutletOrderAction {

  @Override
  public void runOnce(ActionMapping mapping, DynaValidatorForm form,
          HttpServletRequest request, HttpServletResponse response) {

    SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(SessionInfo.SESSION_INFO);
    User user = sessionInfo.getUser();

    if (user instanceof RetailOutlet) {
      form.set("entity_id", ((RetailOutlet) user).getEntityId());
    }
  }

  @Override
  public void doValidate(ActionMapping mapping, ActionMessages messages, DynaValidatorForm form,
          HttpServletRequest request, HttpServletResponse response) {

    if (!messages.isEmpty() || !validateForwardingCharges(messages, form)) {
      return;
    }
    form.set("order_id", createRetailOutletOrder(form, request).getId());
  }

  @Override
  public ActionForward doExecute(ActionMapping mapping, DynaValidatorForm form,
          HttpServletRequest request, HttpServletResponse response) {

    ActionMessages messages = new ActionMessages();
    messages.add(org.apache.struts.Globals.MESSAGE_KEY, new ActionMessage("add.retail.outlet.order.success"));
    saveMessages(request.getSession(), messages);

    return mapping.findForward("success");
  }

  private RetailOutletOrder createRetailOutletOrder(DynaValidatorForm form, HttpServletRequest request) {

    SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(SessionInfo.SESSION_INFO);
    RetailOutletOrderService service = PartnersServices.createRetailOutletOrderService();

    return service.createOrder(createRetailOutletOrder(form, RetailOutletOrderStatus.VALIDADO), sessionInfo.getUser());
  }
}

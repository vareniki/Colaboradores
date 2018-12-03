package es.regalocio.partners.struts.action.order;

import es.regalocio.partners.business.common.OrderLine;
import es.regalocio.partners.business.common.RetailOutletOrder;
import es.regalocio.partners.business.common.RetailOutletOrderStatus;
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

public class UpdateRetailOutletOrderAction extends RetailOutletOrderAction {

  @Override
  public void runOnce(ActionMapping mapping, DynaValidatorForm form, HttpServletRequest request,
          HttpServletResponse response) {

    RetailOutletOrderService service = PartnersServices.createRetailOutletOrderService();
    RetailOutletOrder order = service.getOrder((Integer) form.get("order_id"));

    form.set("entity_id", order.getEntityId());
    form.set("extra_discount", order.getExtraDiscount());
    form.set("forwarding_charges", order.getForwardingCharges());
    form.set("discount_on_forwarding_charges", order.getDiscountOnForwardingCharges());
    form.set("order_form_reference", order.getOrderFormReference());
    
    form.set("persona_contacto", order.getPersonaContacto());
    form.set("tfno_contacto", order.getTfnoContacto());
    form.set("email_contacto", order.getEmailContacto());
    form.set("mercancia_sin_coste", order.getMercanciaSinCoste());
    form.set("envio_plataforma", order.getEnvioPlataforma());
    
    int size = order.getOrderLines().size();
    String[] ean13Array = new String[size];
    Integer[] amountArray = new Integer[size];

    int counter = 0;

    for (OrderLine orderLine : order.getOrderLines()) {
      ean13Array[counter] = orderLine.getEan13();
      amountArray[counter++] = orderLine.getAmount();
    }

    form.set("ean13_array", ean13Array);
    form.set("amount_array", amountArray);
  }

  @Override
  public void doValidate(ActionMapping mapping, ActionMessages messages,
          DynaValidatorForm form, HttpServletRequest request, HttpServletResponse response) {

    if (!messages.isEmpty() || !validateForwardingCharges(messages, form)) {
      return;
    }
    updateRetailOutletOrder(form, request);
  }

  @Override
  public ActionForward doExecute(ActionMapping mapping, DynaValidatorForm form, 
          HttpServletRequest request, HttpServletResponse response) {

    ActionMessages messages = new ActionMessages();
    messages.add(org.apache.struts.Globals.MESSAGE_KEY, new ActionMessage("update.retail.outlet.order.success"));
    saveMessages(request.getSession(), messages);

    return mapping.findForward("success");
  }

  private void updateRetailOutletOrder(DynaValidatorForm form, HttpServletRequest request) {
    SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(SessionInfo.SESSION_INFO);
    RetailOutletOrderService service = PartnersServices.createRetailOutletOrderService();

    RetailOutletOrder order = service.getOrder((Integer) form.get("order_id"));
    service.updateOrder(updateRetailOutletOrder(order, form), sessionInfo.getUser());
  }

  private RetailOutletOrder updateRetailOutletOrder(RetailOutletOrder order, DynaValidatorForm form) {

    RetailOutletOrder newOrder = createRetailOutletOrder(form, RetailOutletOrderStatus.VALIDADO);
    newOrder.setId(order.getId());

    for (OrderLine newOrderLine : newOrder.getOrderLines()) {
      for (OrderLine orderLine : order.getOrderLines()) {
        if (newOrderLine.getEan13().equals(orderLine.getEan13())) {
          newOrderLine.setId(orderLine.getId());
          break;
        }
      }
    }

    return newOrder;
  }
}
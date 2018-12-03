package es.regalocio.partners.struts.action.order;

import es.regalocio.partners.business.services.RetailOutletOrderService;
import es.regalocio.partners.config.PartnersServices;
import es.regalocio.partners.struts.action.SuccessAction;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.Globals;
import org.apache.struts.action.*;

public class DeleteRetailOutletOrderAction extends SuccessAction {

  @Override
  public ActionForward executeChild(ActionMapping mapping, ActionForm form, 
          HttpServletRequest request, HttpServletResponse response) {

    RetailOutletOrderService service = PartnersServices.createRetailOutletOrderService();
    service.deleteOrder(Integer.parseInt(request.getParameter("order_id")));

    ActionMessages messages = new ActionMessages();
    messages.add(Globals.MESSAGE_KEY, new ActionMessage("retail.outlet.order.deleted"));
    saveMessages(request.getSession(), messages);

    return mapping.findForward("success");
  }
}

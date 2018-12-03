package es.regalocio.partners.struts.action.order;

import es.regalocio.partners.business.services.RetailOutletOrderService;
import es.regalocio.partners.config.PartnersServices;
import es.regalocio.partners.struts.action.BaseAction;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ListOfRetailOutletOrdersAction extends BaseAction {

  @Override
  public ActionForward executeChild(ActionMapping mapping, ActionForm form,
          HttpServletRequest request, HttpServletResponse response) {

    RetailOutletOrderService service = PartnersServices.createRetailOutletOrderService();
    request.setAttribute("orderExists", service.notTransmittedOrdersExists());

    return mapping.findForward("success");
  }
}

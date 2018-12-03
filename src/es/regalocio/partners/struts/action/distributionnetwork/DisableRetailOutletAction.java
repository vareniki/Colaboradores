package es.regalocio.partners.struts.action.distributionnetwork;

import es.regalocio.partners.business.services.UserService;
import es.regalocio.partners.config.PartnersServices;
import es.regalocio.partners.struts.action.SuccessAction;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.*;

public class DisableRetailOutletAction extends SuccessAction {

  @Override
  public ActionForward executeChild(ActionMapping mapping, ActionForm form,
          HttpServletRequest request, HttpServletResponse response) {

    UserService service = PartnersServices.createUserService();
    service.setEnableAccount(Integer.parseInt(request.getParameter("user_id")), false);

    ActionMessages messages = new ActionMessages();
    messages.add(org.apache.struts.Globals.MESSAGE_KEY, new ActionMessage("retail.outlet.disabled"));
    saveMessages(request.getSession(), messages);

    StringBuilder path = new StringBuilder(mapping.findForward("success").getPath());
    path.append("?entity_id=");
    path.append(request.getParameter("distribution_network_id"));

    return new RedirectingActionForward(path.toString());
  }
}

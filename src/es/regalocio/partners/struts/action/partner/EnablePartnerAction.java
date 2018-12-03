package es.regalocio.partners.struts.action.partner;

import es.regalocio.partners.business.services.UserService;
import es.regalocio.partners.config.PartnersServices;
import es.regalocio.partners.struts.action.SuccessAction;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.Globals;
import org.apache.struts.action.*;

public class EnablePartnerAction extends SuccessAction {

  @Override
  public ActionForward executeChild(ActionMapping mapping, ActionForm form, HttpServletRequest request,
          HttpServletResponse response) {

    UserService service = PartnersServices.createUserService();
    service.setEnableAccount(Integer.parseInt(request.getParameter("partner_id")), true);

    ActionMessages messages = new ActionMessages();
    messages.add(Globals.MESSAGE_KEY, new ActionMessage("partner.enabled"));
    saveMessages(request.getSession(), messages);

    return super.executeChild(mapping, form, request, response);
  }
}

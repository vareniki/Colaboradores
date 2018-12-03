package es.regalocio.partners.struts.action.partner;

import es.regalocio.partners.business.services.PartnerService;
import es.regalocio.partners.config.PartnersServices;
import es.regalocio.partners.struts.action.SuccessAction;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.Globals;
import org.apache.struts.action.*;

public class GenerateNewPasswordAction extends SuccessAction {

  @Override
  public ActionForward executeChild(ActionMapping mapping,
          ActionForm form, HttpServletRequest request, HttpServletResponse response) {

    PartnerService service = PartnersServices.createPartnerService();
    service.generateNewPassword(Integer.parseInt(request.getParameter("partner_id")));

    ActionMessages messages = new ActionMessages();
    messages.add(Globals.MESSAGE_KEY, new ActionMessage("generate.new.partner.password.success"));
    saveMessages(request.getSession(), messages);

    return super.executeChild(mapping, form, request, response);
  }
}

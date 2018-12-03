package es.regalocio.partners.struts.action.partner;

import es.regalocio.partners.business.services.PartnerService;
import es.regalocio.partners.config.PartnersServices;
import es.regalocio.partners.struts.action.SuccessAction;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.Globals;
import org.apache.struts.action.*;

public class DeleteSpecificAgreementAction extends SuccessAction {

  @Override
  public ActionForward executeChild(ActionMapping mapping, ActionForm form, HttpServletRequest request,
          HttpServletResponse response) {

    PartnerService service = PartnersServices.createPartnerService();
    service.deleteSpecificAgreement(Integer.parseInt(request.getParameter("specific_agreement_id")));

    ActionMessages messages = new ActionMessages();
    messages.add(Globals.MESSAGE_KEY, new ActionMessage("specific.agreement.deleted"));
    saveMessages(request.getSession(), messages);

    StringBuilder path = new StringBuilder(mapping.findForward("success").getPath());
    path.append("?partner_id=");
    path.append(Integer.parseInt(request.getParameter("partner_id")));

    return new RedirectingActionForward(path.toString());
  }
}

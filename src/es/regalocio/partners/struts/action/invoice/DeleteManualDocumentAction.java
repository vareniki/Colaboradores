package es.regalocio.partners.struts.action.invoice;

import es.regalocio.partners.business.services.InvoiceService;
import es.regalocio.partners.config.PartnersServices;
import es.regalocio.partners.shared.SessionInfo;
import es.regalocio.partners.struts.action.SuccessAction;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.*;

public class DeleteManualDocumentAction extends SuccessAction {

  @Override
  public ActionForward executeChild(ActionMapping mapping, ActionForm form, HttpServletRequest request,
          HttpServletResponse response) {

    SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(SessionInfo.SESSION_INFO);
    InvoiceService service = PartnersServices.createInvoiceService();
    service.deleteInvoice(Integer.parseInt(request.getParameter("invoice_id")), sessionInfo.getUser());

    ActionMessages messages = new ActionMessages();
    messages.add(org.apache.struts.Globals.MESSAGE_KEY, new ActionMessage("document.deleted"));
    saveMessages(request.getSession(), messages);

    StringBuilder path = new StringBuilder(mapping.findForward("success").getPath());
    path.append("?entity_id=");
    path.append(Integer.parseInt(request.getParameter("entity_id")));

    return new RedirectingActionForward(path.toString());
  }
}

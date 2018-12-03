package es.regalocio.partners.struts.action.invoice;

import es.regalocio.partners.business.services.EntityOfDistributionService;
import es.regalocio.partners.business.services.InvoiceService;
import es.regalocio.partners.config.PartnersServices;
import es.regalocio.partners.struts.action.SuccessAction;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.Globals;
import org.apache.struts.action.*;

public class SendInvoiceEMailAction extends SuccessAction {

  @Override
  public ActionForward executeChild(ActionMapping mapping, ActionForm form, HttpServletRequest request,
          HttpServletResponse response) {

    int entityId = Integer.parseInt(request.getParameter("entity_id"));
    int invoiceId = Integer.parseInt(request.getParameter("invoice_id"));

    EntityOfDistributionService retailService = PartnersServices.createDistributionService();
    InvoiceService invoiceService = PartnersServices.createInvoiceService();

    invoiceService.sendInvoiceEmail(retailService.getRetailOutlet(entityId), invoiceId);

    ActionMessages messages = new ActionMessages();
    messages.add(Globals.MESSAGE_KEY, new ActionMessage("sent.invoice.retail.success"));
    saveMessages(request.getSession(), messages);

    StringBuilder path = new StringBuilder(mapping.findForward("success").getPath());
    path.append("?entity_id=").append(entityId);

    return new RedirectingActionForward(path.toString());
  }
}

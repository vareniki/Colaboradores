package es.regalocio.partners.struts.action.invoice;

import es.regalocio.partners.business.common.StaticDefinition;
import es.regalocio.partners.business.services.InvoiceService;
import es.regalocio.partners.config.PartnersServices;
import es.regalocio.partners.shared.SessionInfo;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.*;
import org.apache.struts.validator.DynaValidatorForm;

public class AddManualDocumentAction extends ManualDocumentAction {

  @Override
  public void runOnce(ActionMapping mapping, DynaValidatorForm form, HttpServletRequest request,
          HttpServletResponse response) {

    Integer typeOfDocument = Integer.valueOf(mapping.getParameter());
    form.set("type_of_document", typeOfDocument);
    if (typeOfDocument == StaticDefinition.MANUAL_INVOICE) {
      form.set("comments", PartnersServices.createInvoiceService().getLastComments(false));
    } else if (typeOfDocument == StaticDefinition.MANUAL_CREDIT) {
      form.set("comments", PartnersServices.createInvoiceService().getLastComments(true));
    }
  }

  @Override
  public void doValidate(ActionMapping mapping, ActionMessages messages,
          DynaValidatorForm form, HttpServletRequest request, HttpServletResponse response) {

    if (!messages.isEmpty() || !validateForwardingCharges(messages, form)) {
      return;
    }
    createInvoice(form, request);
  }

  @Override
  public ActionForward doExecute(ActionMapping mapping, DynaValidatorForm form,
          HttpServletRequest request, HttpServletResponse response) {

    ActionMessages messages = new ActionMessages();
    int typeOfDocument = (Integer) form.get("type_of_document");
    messages.add(org.apache.struts.Globals.MESSAGE_KEY, (typeOfDocument == StaticDefinition.MANUAL_INVOICE)
            ? new ActionMessage("add.invoice.success") : new ActionMessage("add.credit.success"));
    saveMessages(request.getSession(), messages);

    StringBuilder path = new StringBuilder(mapping.findForward("success").getPath());
    path.append("?entity_id=");
    path.append(form.get("entity_id"));

    return new RedirectingActionForward(path.toString());
  }

  private void createInvoice(DynaValidatorForm form, HttpServletRequest request) {
    SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(SessionInfo.SESSION_INFO);
    InvoiceService service = PartnersServices.createInvoiceService();
    service.createInvoice(createInvoice(form), sessionInfo.getUser());
  }
}

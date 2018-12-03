package es.regalocio.partners.struts.action.invoice;

import es.regalocio.partners.business.common.Invoice;
import es.regalocio.partners.business.common.InvoiceLine;
import es.regalocio.partners.business.common.StaticDefinition;
import es.regalocio.partners.business.services.InvoiceService;
import es.regalocio.partners.config.PartnersServices;
import es.regalocio.partners.shared.SessionInfo;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.*;
import org.apache.struts.validator.DynaValidatorForm;

public class UpdateManualDocumentAction extends ManualDocumentAction {

  @Override
  public void runOnce(ActionMapping mapping, DynaValidatorForm form, HttpServletRequest request,
          HttpServletResponse response) {

    InvoiceService service = PartnersServices.createInvoiceService();
    Invoice invoice = service.getInvoiceById((Integer) form.get("invoice_id"));

    form.set("entity_id", invoice.getEntityId());
    form.set("extra_discount", invoice.getExtraDiscount());
    form.set("extra_discount2", invoice.getExtraDiscount2());
    form.set("forwarding_charges", invoice.getForwardingCharges());
    form.set("discount_on_forwarding_charges", invoice.getDiscountOnForwardingCharges());
    form.set("order_form_reference", invoice.getOrderFormReference());
    form.set("type_of_document", invoice.getTypeOfDocument());
    form.set("comments", invoice.getComments());

    form.set("numero_albaran", invoice.getNumeroAlbaran());
    form.set("numero_proveedor", invoice.getNumeroProveedor());
    form.set("numero_sucursal", invoice.getNumeroSucursal());
    
    form.set("importe_total", invoice.getImporteTotal());
    form.set("importe_comision", invoice.getImporteComision());

    int size = invoice.getInvoiceLines().size();
    String[] ean13Array = new String[size];
    Integer[] amountArray = new Integer[size];

    int counter = 0;
    for (InvoiceLine invoiceLine : invoice.getInvoiceLines()) {
      ean13Array[counter] = invoiceLine.getEan13();
      amountArray[counter++] = invoiceLine.getAmount();
    }

    form.set("ean13_array", ean13Array);
    form.set("amount_array", amountArray);
  }

  @Override
  public void doValidate(ActionMapping mapping, ActionMessages messages, DynaValidatorForm form,
          HttpServletRequest request, HttpServletResponse response) {

    if (!messages.isEmpty()) {
      return;
    }
    if (!validateForwardingCharges(messages, form)) {
      return;
    }

    updateInvoice(form, request);
  }

  @Override
  public ActionForward doExecute(ActionMapping mapping, DynaValidatorForm form, HttpServletRequest request,
          HttpServletResponse response) {

    ActionMessages messages = new ActionMessages();
    int typeOfDocument = (Integer) form.get("type_of_document");
    messages.add(org.apache.struts.Globals.MESSAGE_KEY,
            (typeOfDocument == StaticDefinition.MANUAL_INVOICE)
                    ? new ActionMessage("update.invoice.success")
                    : new ActionMessage("update.credit.success"));
    saveMessages(request.getSession(), messages);

    StringBuilder path = new StringBuilder(mapping.findForward("success").getPath());
    path.append("?entity_id=");
    path.append(form.get("entity_id"));

    return new RedirectingActionForward(path.toString());
  }

  private void updateInvoice(DynaValidatorForm form, HttpServletRequest request) {

    SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(SessionInfo.SESSION_INFO);
    InvoiceService service = PartnersServices.createInvoiceService();

    Invoice invoice = service.getInvoiceById((Integer) form.get("invoice_id"));
    service.updateInvoice(updateInvoice(invoice, form), sessionInfo.getUser());
  }

  private Invoice updateInvoice(Invoice invoice, DynaValidatorForm form) {
    Invoice newInvoice = createInvoice(form);
    newInvoice.setId(invoice.getId());

    for (InvoiceLine newInvoiceLine : newInvoice.getInvoiceLines()) {
      for (InvoiceLine invoiceLine : invoice.getInvoiceLines()) {
        if (newInvoiceLine.getEan13().equals(invoiceLine.getEan13())) {
          newInvoiceLine.setId(invoiceLine.getId());
          break;
        }
      }
    }

    return newInvoice;
  }
}

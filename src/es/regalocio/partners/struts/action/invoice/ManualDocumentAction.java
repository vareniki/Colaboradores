package es.regalocio.partners.struts.action.invoice;

import es.regalocio.partners.business.common.*;
import es.regalocio.partners.business.services.EntityOfDistributionService;
import es.regalocio.partners.config.PartnersServices;
import es.regalocio.partners.config.PartnersUtils;
import es.regalocio.partners.struts.action.DynaValidatorFormAction;
import es.regalocio.partners.utils.StrUtils;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.validator.DynaValidatorForm;

public abstract class ManualDocumentAction extends DynaValidatorFormAction {

  @Override
  public void initValues(ActionMapping mapping, DynaValidatorForm form, HttpServletRequest request) {

    Integer entityId = (Integer) form.get("entity_id");
    EntityOfDistributionService entityOfDistributionService = PartnersServices.createDistributionService();
    RetailOutlet retailOutlet = entityOfDistributionService.getRetailOutlet(entityId);

    request.setAttribute("distribution_network_name", retailOutlet.getDistributionNetwork().getName());
    request.setAttribute("retail_outlet_name", retailOutlet.getName());
  }

  protected Invoice createInvoice(DynaValidatorForm form) {

    StrUtils stringUtils = StrUtils.getInstance();
    Invoice invoice = new Invoice(
            (Integer) form.get("entity_id"),
            (Double) form.get("extra_discount"),
            (Double) form.get("extra_discount2"),
            (Double) form.get("forwarding_charges"),
            (Double) form.get("discount_on_forwarding_charges"),
            stringUtils.strFmt(form.getString("order_form_reference")),
            (Integer) form.get("type_of_document"),
            stringUtils.strFmt(form.getString("comments")),
    
            stringUtils.strFmt(form.getString("numero_albaran")),
            stringUtils.strFmt(form.getString("numero_proveedor")),
            stringUtils.strFmt(form.getString("numero_sucursal")),
            
            (Double) form.get("importe_total"),
            (Double) form.get("importe_comision")
    );
    createInvoiceLines(invoice, form);

    return invoice;
  }

  protected InvoiceLine createInvoiceLine(DistributionNetwork distributionNetwork, String ean13, int amount) {

    PartnersUtils utils = PartnersUtils.getInstance();
    Thematic thematic = utils.getEan13Thematic(ean13);
    ThematicRate rate = utils.getThematicRate(ean13);
    DistributorMargin distributorMargin = PartnersUtils.getInstance().getDistributorMarginForRate(distributionNetwork, thematic, rate);

    return new InvoiceLine(
            ean13, thematic.getId(), thematic.getName(),
            rate.getEndOfValidity(), rate.getPrice(),
            amount, distributorMargin.getMargin());
  }

  protected void createInvoiceLines(Invoice invoice, DynaValidatorForm form) {
    String[] ean13Array = (String[]) form.get("ean13_array");
    Integer[] amountArray = (Integer[]) form.get("amount_array");

    EntityOfDistributionService service = PartnersServices.createDistributionService();
    DistributionNetwork distributionNetwork =
            service.getRetailOutlet((Integer) form.get("entity_id")).getDistributionNetwork();

    for (int counter = 0; counter < ean13Array.length; counter++) {
      invoice.getInvoiceLines().add(
              createInvoiceLine(distributionNetwork, ean13Array[counter], amountArray[counter]));
    }
  }

  protected boolean validateForwardingCharges(ActionMessages messages, DynaValidatorForm form) {

    Double forwardingCharges = (Double) form.get("forwarding_charges");
    Double discountOnForwardingCharges = (Double) form.get("discount_on_forwarding_charges");

    if (forwardingCharges == null || discountOnForwardingCharges == null) {
      return true;
    }
    if (forwardingCharges < discountOnForwardingCharges) {
      messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("invoice.forwarding_charges.incomptible.values"));
      return false;
    }

    return true;
  }
}

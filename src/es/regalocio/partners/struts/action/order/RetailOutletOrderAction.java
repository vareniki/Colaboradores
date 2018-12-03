package es.regalocio.partners.struts.action.order;

import es.regalocio.partners.business.common.*;
import es.regalocio.partners.business.services.EntityOfDistributionService;
import es.regalocio.partners.business.services.RetailOutletOrderService;
import es.regalocio.partners.config.PartnersServices;
import es.regalocio.partners.config.PartnersUtils;
import es.regalocio.partners.struts.action.DynaValidatorFormAction;
import es.regalocio.partners.utils.StrUtils;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.validator.DynaValidatorForm;

public abstract class RetailOutletOrderAction extends DynaValidatorFormAction {

  protected OrderLine createOrderLine(String ean13, int amount) {

    PartnersUtils partnersUtils = PartnersUtils.getInstance();
    Thematic thematic = partnersUtils.getEan13Thematic(ean13);
    ThematicRate rate = partnersUtils.getThematicRate(ean13);

    return new OrderLine(ean13, thematic.getId(), thematic.getName(), rate.getEndOfValidity(), rate.getPrice(), amount, null);
  }

  protected void createOrderLines(RetailOutletOrder order, DynaValidatorForm form) {
    String[] ean13Array = (String[]) form.get("ean13_array");
    Integer[] amountArray = (Integer[]) form.get("amount_array");
    for (int counter = 0; counter < ean13Array.length; counter++) {
      order.getOrderLines().add(createOrderLine(ean13Array[counter], amountArray[counter]));
    }
  }

  protected RetailOutletOrder createRetailOutletOrder(DynaValidatorForm form, RetailOutletOrderStatus status) {
    StrUtils strUtils = StrUtils.getInstance();
    RetailOutletOrder retailOutletOrder = new RetailOutletOrder(
            (Integer) form.get("entity_id"),
            (Double) form.get("extra_discount"),
            (Double) form.get("forwarding_charges"),
            (Double) form.get("discount_on_forwarding_charges"),
            strUtils.strFmt(form.getString("message")),
            null, // external_reference
            strUtils.strFmt(form.getString("order_form_reference")), status,
            (Boolean) form.get("mercancia_sin_coste"),
            
            strUtils.strFmt(form.getString("tfno_contacto")),
            strUtils.strFmt(form.getString("email_contacto")),
            strUtils.strFmt(form.getString("persona_contacto")),
            (Boolean) form.get("envio_plataforma"));
    
    createOrderLines(retailOutletOrder, form);

    return retailOutletOrder;
  }

  protected boolean validateForwardingCharges(ActionMessages messages, DynaValidatorForm form) {

    Double forwardingCharges = (Double) form.get("forwarding_charges");
    Double discountOnForwardingCharges = (Double) form.get("discount_on_forwarding_charges");

    if (forwardingCharges == null || discountOnForwardingCharges == null) {
      return true;
    }
    if (forwardingCharges < discountOnForwardingCharges) {
      messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("retail_outlet_order.forwarding_charges.incomptible.values"));
      return false;
    }

    return true;
  }

  @Override
  public void initValues(ActionMapping mapping, DynaValidatorForm form, HttpServletRequest request) {

    RetailOutletOrderService retailOutletOrderService = PartnersServices.createRetailOutletOrderService();
    Map<DefaultTypology, List<DefaultTypology>> allRetailOutlets = retailOutletOrderService.getListOfRetailOutlets();

    request.setAttribute("all_retail_outlets", allRetailOutlets);
    request.setAttribute("distribution_networks", allRetailOutlets.keySet());

    Integer distributionNetworkId = (Integer) form.get("distribution_network_id");

    if (distributionNetworkId == null) {
      Integer entityId = (Integer) form.get("entity_id");
      if (entityId != null) {
        EntityOfDistributionService service = PartnersServices.createDistributionService();
        RetailOutlet retailOutlet = service.getRetailOutlet(entityId);
        distributionNetworkId = retailOutlet.getDistributionNetwork().getId();
        form.set("distribution_network_id", distributionNetworkId);
      }
    }

    if (distributionNetworkId != null) {

      for (DefaultTypology defaultTypology : allRetailOutlets.keySet()) {
        if (distributionNetworkId.equals(defaultTypology.getCode())) {
          request.setAttribute("retail_outlets", allRetailOutlets.get(defaultTypology));
          break;
        }
      }
    }
  }
}

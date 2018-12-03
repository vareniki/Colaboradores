package es.regalocio.partners.struts.action.distributionnetwork;

import es.regalocio.partners.business.common.OrderPlatform;
import es.regalocio.partners.business.common.RetailOutlet;
import es.regalocio.partners.business.common.shared.RetailOutletNameAlreadyUsedException;
import es.regalocio.partners.business.services.EntityOfDistributionService;
import es.regalocio.partners.config.PartnersServices;
import es.regalocio.partners.shared.SessionInfo;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.*;
import org.apache.struts.validator.DynaValidatorForm;

public class UpdateRetailOutletAction extends RetailOutletAction {

  @Override
  public ActionForward doExecute(ActionMapping mapping, DynaValidatorForm form,
          HttpServletRequest request, HttpServletResponse response) {

    ActionMessages messages = new ActionMessages();
    messages.add(org.apache.struts.Globals.MESSAGE_KEY, new ActionMessage("update.retail.outlet.success"));
    saveMessages(request.getSession(), messages);

    StringBuilder path = new StringBuilder(mapping.findForward("success").getPath());
    path.append("?entity_id=");
    path.append(form.get("distribution_network_id"));

    return new RedirectingActionForward(path.toString());
  }

  @Override
  public void doValidate(ActionMapping mapping, ActionMessages messages, DynaValidatorForm form,
          HttpServletRequest request, HttpServletResponse response) {

    if (!messages.isEmpty()) {
      return;
    }
    try {
      SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(SessionInfo.SESSION_INFO);
      EntityOfDistributionService service = PartnersServices.createDistributionService();

      RetailOutlet retailOutlet = createRetailOutlet(form);
      retailOutlet.setEntityId((Integer) form.get("entity_id"));
      retailOutlet.setId((Integer) form.get("user_id"));

      if (retailOutlet.getContact() != null && form.get("contact_id") != null) {
        retailOutlet.getContact().setId((Integer) form.get("contact_id"));
      }

      retailOutlet.getAddress().setId((Integer) form.get("address_id"));

      if (retailOutlet.getFactaddress() != null) {
        retailOutlet.getFactaddress().setId((Integer) form.get("factaddress_id"));
      }

      service.updateRetailOutlet(retailOutlet, (Integer) form.get("contact_id"), sessionInfo.getUser().getId());
    } catch (RetailOutletNameAlreadyUsedException e) {
      messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("retail.outlet.name.already.used"));
    }
  }

  @Override
  public void initValues(ActionMapping mapping, DynaValidatorForm form, HttpServletRequest request) {
    super.initValues(mapping, form, request);

    EntityOfDistributionService service = PartnersServices.createDistributionService();
    boolean documentExists = service.retailOutletDocumentsExists((Integer) form.get("entity_id"));

    request.setAttribute("document_exists", documentExists);
    
    RetailOutlet retailOutlet = service.getRetailOutlet((Integer) form.get("entity_id"));
    
    List<OrderPlatform> orderPlatforms = service.getOrderPlatforms(retailOutlet.getDistributionNetwork().getId());
    request.setAttribute("order_platforms", orderPlatforms);
  }

  @Override
  public void runOnce(ActionMapping mapping, DynaValidatorForm form,
          HttpServletRequest request, HttpServletResponse response) {

    EntityOfDistributionService service = PartnersServices.createDistributionService();
    RetailOutlet retailOutlet = service.getRetailOutlet((Integer) form.get("entity_id"));

    String giftVoucherActivationOnSale;
    if (retailOutlet.getGiftVoucherActivationOnSale() == null) {
      giftVoucherActivationOnSale = RetailOutletAction.UNDEFINED_VALUE;
    } else {
      giftVoucherActivationOnSale = retailOutlet.getGiftVoucherActivationOnSale().toString();
    }

    form.set("user_id", retailOutlet.getId());
    form.set("distribution_network_id", retailOutlet.getDistributionNetwork().getId());
    form.set("gift_voucher_activation_on_sale", giftVoucherActivationOnSale);
    form.set("name", retailOutlet.getName());
    form.set("phone", retailOutlet.getPhone());
    form.set("fax", retailOutlet.getFax());
    form.set("email", retailOutlet.getEmail());
    form.set("cif", retailOutlet.getCif());
    form.set("factname", retailOutlet.getFactname());

    if (retailOutlet.getContact() != null) {
      form.set("contact_id", retailOutlet.getContact().getId());
      form.set("salutation_code", retailOutlet.getContact().getSalutationCode());
      form.set("firstname", retailOutlet.getContact().getFirstName());
      form.set("lastname", retailOutlet.getContact().getLastName());
    }

    form.set("address_id", retailOutlet.getAddress().getId());
    form.set("address", retailOutlet.getAddress().getAddress());
    form.set("further_address", retailOutlet.getAddress().getFurtherAddress());
    form.set("postal_code", retailOutlet.getAddress().getPostalCode());
    form.set("town", retailOutlet.getAddress().getTown());
    form.set("order_platform_id", retailOutlet.getOrderPlatformId());
    form.set("send_last_guides", retailOutlet.getSendLastGuides());

    if (retailOutlet.getFactaddress() != null) {
      form.set("factaddress_id", retailOutlet.getFactaddress().getId());
      form.set("fact_address", retailOutlet.getFactaddress().getAddress());
      form.set("fact_further_address", retailOutlet.getFactaddress().getFurtherAddress());
      form.set("fact_postal_code", retailOutlet.getFactaddress().getPostalCode());
      form.set("fact_town", retailOutlet.getFactaddress().getTown());
    }

    form.set("profile_code_array", retailOutlet.getProfiles().toArray(new Integer[retailOutlet.getProfiles().size()]));
    
    request.setAttribute("login", retailOutlet.getLogin());
  }
}

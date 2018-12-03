package es.regalocio.partners.struts.action.distributionnetwork;

import es.regalocio.partners.business.common.DistributionNetwork;
import es.regalocio.partners.business.common.ProfileList;
import es.regalocio.partners.business.common.RetailOutlet;
import es.regalocio.partners.business.common.shared.RetailOutletNameAlreadyUsedException;
import es.regalocio.partners.business.services.EntityOfDistributionService;
import es.regalocio.partners.config.PartnersServices;
import es.regalocio.partners.config.PartnersUtils;
import es.regalocio.partners.shared.SessionInfo;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.validator.DynaValidatorForm;

public class UpdateOurInformationsAction extends RetailOutletAction {

  @Override
  public ActionForward doExecute(ActionMapping mapping, DynaValidatorForm form,
          HttpServletRequest request, HttpServletResponse response) {

    ActionMessages messages = new ActionMessages();
    messages.add(org.apache.struts.Globals.MESSAGE_KEY, new ActionMessage("retail.outlet.update.our.informations.success"));
    saveMessages(request.getSession(), messages);

    return mapping.findForward("success");
  }

  @Override
  public void doValidate(ActionMapping mapping, ActionMessages messages, DynaValidatorForm form,
          HttpServletRequest request, HttpServletResponse response) {

    if (!messages.isEmpty()) {
      return;
    }
    updateRetailOutlet(form, request);
  }

  @Override
  public void initValues(ActionMapping mapping, DynaValidatorForm form, HttpServletRequest request) {

    EntityOfDistributionService service = PartnersServices.createDistributionService();
    DistributionNetwork distributionNetwork = service.getDistributionNetwork((Integer) form.get("distribution_network_id"));
    request.setAttribute("distribution_network", distributionNetwork);

    request.setAttribute("salutation", PartnersUtils.getInstance().getTypologyList("salutation"));

    SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(SessionInfo.SESSION_INFO);
    RetailOutlet retailOutlet = (RetailOutlet) sessionInfo.getUser();

    request.setAttribute("identification_code", retailOutlet.getLogin());

    boolean documentExists = service.retailOutletDocumentsExists((Integer) form.get("entity_id"));
    request.setAttribute("document_exists", documentExists);
  }

  @Override
  public void runOnce(ActionMapping mapping, DynaValidatorForm form,
          HttpServletRequest request, HttpServletResponse response) {

    SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(SessionInfo.SESSION_INFO);
    RetailOutlet retailOutlet = (RetailOutlet) sessionInfo.getUser();

    String giftVoucherActivationOnSale;
    if (retailOutlet.getGiftVoucherActivationOnSale() == null) {
      giftVoucherActivationOnSale = RetailOutletAction.UNDEFINED_VALUE;
    } else {
      giftVoucherActivationOnSale = retailOutlet.getGiftVoucherActivationOnSale().toString();
    }
    form.set("entity_id", retailOutlet.getEntityId());
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

    form.set("profile_code_array", retailOutlet.getProfiles().toArray(new Integer[retailOutlet.getProfiles().size()]));
  }

  private void updateRetailOutlet(DynaValidatorForm form, HttpServletRequest request) {

    SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(SessionInfo.SESSION_INFO);
    RetailOutlet retailOutlet = (RetailOutlet) sessionInfo.getUser();
    EntityOfDistributionService service = PartnersServices.createDistributionService();

    RetailOutlet newRetailOutlet = createRetailOutlet(form);
    newRetailOutlet.setEntityId((Integer) form.get("entity_id"));
    newRetailOutlet.setId((Integer) form.get("user_id"));
    newRetailOutlet.setLogin(retailOutlet.getLogin());
    newRetailOutlet.setEnabled(retailOutlet.isEnabled());

    if (newRetailOutlet.getContact() != null && form.get("contact_id") != null) {
      newRetailOutlet.getContact().setId((Integer) form.get("contact_id"));
    }
    newRetailOutlet.getAddress().setId((Integer) form.get("address_id"));
    try {
      newRetailOutlet = service.updateRetailOutlet(newRetailOutlet, (Integer) form.get("contact_id"), retailOutlet.getId());
    } catch (RetailOutletNameAlreadyUsedException ex) {
      Logger.getLogger(UpdateOurInformationsAction.class.getName()).log(Level.SEVERE, null, ex);
    }
    sessionInfo.setUser(newRetailOutlet);
  }

  @Override
  @SuppressWarnings("ManualArrayToCollectionCopy")
  protected ProfileList createProfileList(DynaValidatorForm form) {
    ProfileList profileList = new ProfileList();
    for (Integer profileCode : (Integer[]) form.get("profile_code_array")) {
      profileList.add(profileCode);
    }
    return profileList;
  }
}

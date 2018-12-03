package es.regalocio.partners.struts.action.distributionnetwork;

import es.regalocio.partners.business.common.DistributionNetwork;
import es.regalocio.partners.business.common.shared.DistributionNetworkNameAlreadyUsedException;
import es.regalocio.partners.business.services.EntityOfDistributionService;
import es.regalocio.partners.config.PartnersServices;
import es.regalocio.partners.shared.SessionInfo;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.*;
import org.apache.struts.validator.DynaValidatorForm;

public class UpdateDistributionNetworkAction extends DistributionNetworkAction {

  @Override
  public ActionForward doExecute(ActionMapping mapping, DynaValidatorForm form,
          HttpServletRequest request, HttpServletResponse response) {

    ActionMessages messages = new ActionMessages();
    messages.add(org.apache.struts.Globals.MESSAGE_KEY, new ActionMessage("update.distribution.network.success"));
    saveMessages(request.getSession(), messages);

    StringBuilder path = new StringBuilder(mapping.findForward("success").getPath());
    path.append("?entity_id=");
    path.append(form.get("entity_id"));

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

      DistributionNetwork distributionNetwork = createDistributionNetwork(form);
      distributionNetwork.setId((Integer) form.get("entity_id"));
      service.updateDistributionNetwork(distributionNetwork, sessionInfo.getUser().getId());
    } catch (DistributionNetworkNameAlreadyUsedException e) {
      messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("distribution.network.name.already.used"));
    }
  }

  @Override
  public void runOnce(ActionMapping mapping, DynaValidatorForm form,
          HttpServletRequest request, HttpServletResponse response) {

    EntityOfDistributionService service = PartnersServices.createDistributionService();
    DistributionNetwork dnetwork = service.getDistributionNetwork((Integer) form.get("entity_id"));

    form.set("name", dnetwork.getName());
    form.set("cif", dnetwork.getCif());
    form.set("inherit_address", dnetwork.isInheritAddress());
    form.set("inherit_factaddress", dnetwork.isInheritFactaddress());
    form.set("disable_changepassword", dnetwork.isDisableChangePassword());
    form.set("gift_voucher_activation_on_sale", dnetwork.isGiftVoucherActivationOnSale());
    form.set("with_iva", dnetwork.isWithIva());
    // Dirección de entrega.
    if (dnetwork.getAddress() != null) {
      form.set("address_id", dnetwork.getAddress().getId());
      form.set("address", dnetwork.getAddress().getAddress());
      form.set("further_address", dnetwork.getAddress().getFurtherAddress());
      form.set("postal_code", dnetwork.getAddress().getPostalCode());
      form.set("town", dnetwork.getAddress().getTown());
    }
    // Dirección de facturación
    if (dnetwork.getFactaddress() != null) {
      form.set("factaddress_id", dnetwork.getFactaddress().getId());
      form.set("fact_address", dnetwork.getFactaddress().getAddress());
      form.set("fact_further_address", dnetwork.getFactaddress().getFurtherAddress());
      form.set("fact_postal_code", dnetwork.getFactaddress().getPostalCode());
      form.set("fact_town", dnetwork.getFactaddress().getTown());
    }
  }
}

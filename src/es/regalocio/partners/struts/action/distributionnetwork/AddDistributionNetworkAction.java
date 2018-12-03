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

public class AddDistributionNetworkAction extends DistributionNetworkAction {

  @Override
  public void doValidate(ActionMapping mapping, ActionMessages messages, DynaValidatorForm form,
          HttpServletRequest request, HttpServletResponse response) {

    if (!messages.isEmpty()) {
      return;
    }

    try {
      SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(SessionInfo.SESSION_INFO);
      EntityOfDistributionService service = PartnersServices.createDistributionService();

      DistributionNetwork distributionNetwork
              = service.createDistributionNetwork(createDistributionNetwork(form), sessionInfo.getUser().getId());
      form.set("entity_id", distributionNetwork.getId());
    } catch (DistributionNetworkNameAlreadyUsedException e) {
      messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("distribution.network.name.already.used"));
    }
  }

  @Override
  public ActionForward doExecute(ActionMapping mapping, DynaValidatorForm form, HttpServletRequest request,
          HttpServletResponse response) {

    ActionMessages messages = new ActionMessages();
    messages.add(org.apache.struts.Globals.MESSAGE_KEY, new ActionMessage("add.distribution.network.success"));
    saveMessages(request.getSession(), messages);

    StringBuilder path = new StringBuilder(mapping.findForward("success").getPath());
    path.append("?entity_id=");
    path.append(form.get("entity_id"));

    return new RedirectingActionForward(path.toString());
  }
}

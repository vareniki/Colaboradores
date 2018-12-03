package es.regalocio.partners.struts.action.distributionnetwork;

import es.regalocio.partners.business.services.EntityOfDistributionService;
import es.regalocio.partners.config.PartnersServices;
import es.regalocio.partners.shared.SessionInfo;
import es.regalocio.partners.struts.action.SuccessAction;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.*;

public class EnableDistributionNetworkAction extends SuccessAction {

  @Override
  public ActionForward executeChild(ActionMapping mapping, ActionForm form,
          HttpServletRequest request, HttpServletResponse response) {
    SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(SessionInfo.SESSION_INFO);

    EntityOfDistributionService service = PartnersServices.createDistributionService();
    service.setEnableDistributionNetwork(
            Integer.parseInt(request.getParameter("entity_id")), true, sessionInfo.getUser().getId());

    ActionMessages messages = new ActionMessages();
    messages.add(org.apache.struts.Globals.MESSAGE_KEY, new ActionMessage("distribution.network.enabled"));
    saveMessages(request.getSession(), messages);

    return super.executeChild(mapping, form, request, response);
  }
}

package es.regalocio.partners.struts.action.distributionnetwork;

import es.regalocio.partners.business.common.RetailOutlet;
import es.regalocio.partners.shared.SessionInfo;
import es.regalocio.partners.struts.action.SuccessAction;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ListOfBoxesInStockAction extends SuccessAction {

  @Override
  public ActionForward executeChild(ActionMapping mapping, ActionForm form,
          HttpServletRequest request, HttpServletResponse response) {

    SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(SessionInfo.SESSION_INFO);
    RetailOutlet retailOutlet = (RetailOutlet) sessionInfo.getUser();
    request.setAttribute("entity_id", retailOutlet.getEntityId());

    return mapping.findForward("success");
  }
}

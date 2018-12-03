package es.regalocio.partners.struts.action.logon;

import es.regalocio.partners.config.PartnersServices;
import es.regalocio.partners.shared.SessionInfo;
import es.regalocio.partners.struts.action.BaseAction;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class LogoutAction extends BaseAction {

  @Override
  public ActionForward executeChild(ActionMapping mapping, ActionForm form,
          HttpServletRequest request, HttpServletResponse response) {

    SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(SessionInfo.SESSION_INFO);

    String userName = null;
    if (sessionInfo != null && sessionInfo.getUser() != null) {
      userName = sessionInfo.getUser().getLogin();
    }
    PartnersServices.createLogService().addUsrLog("partners", "LogoutAction", userName, request.getRemoteAddr());

    if (sessionInfo != null) {
      sessionInfo.setUser(null);
    }

    return mapping.findForward("home");
  }
}

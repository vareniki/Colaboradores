package es.regalocio.partners.struts.action;

import es.regalocio.partners.business.common.User;
import es.regalocio.partners.business.common.shared.PartnersException;
import es.regalocio.partners.config.PartnersUtils;
import es.regalocio.partners.shared.SessionInfo;
import es.regalocio.partners.utils.ServletUtils;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.*;

public abstract class BaseAction extends Action {

  private static final Log logger = LogFactory.getLog(BaseAction.class);

  /**
   *
   * @param mapping
   * @param request
   * @param actionName
   * @return
   */
  private static ActionForward computeForwardToLogonPage(
          ActionMapping mapping, HttpServletRequest request, String actionName) {
    
    String url = null;
    ServletUtils utils = ServletUtils.getInstance();
    if (actionName != null && !actionName.equals("logout")) {
      url = utils.getStrutsActionMapping(request) + ".do";
      if (request.getQueryString() != null) {
        url += "?" + request.getQueryString();
      }
      try {
        url = URLEncoder.encode(url, "UTF-8");
      } catch (UnsupportedEncodingException e) {
        url = null;
      }
    }

    if (url != null) {
      return new RedirectingActionForward(mapping.findForward("logon").getPath() + "?redirect_path=" + url);
    }
    return mapping.findForward("logon");
  }

  /**
   *
   * @param mapping
   * @param user
   * @return
   */
  protected boolean isAuthorized(ActionMapping mapping, User user) {
    return user.inProfiles(mapping.getRoles());
  }

  @Override
  public ActionForward execute(ActionMapping mapping, ActionForm form,
          HttpServletRequest request, HttpServletResponse response) {

    try {
      if (PartnersUtils.getInstance() == null) {
        throw new PartnersException("PartnersUtils.getInstance() = null");
      }
      HttpSession httpSession = request.getSession();
      SessionInfo sessionInfo = (SessionInfo) httpSession.getAttribute(SessionInfo.SESSION_INFO);
      if (sessionInfo == null) {
        sessionInfo = new SessionInfo();
        httpSession.setAttribute(SessionInfo.SESSION_INFO, sessionInfo);
        httpSession.setMaxInactiveInterval(60 * 60);
      }

      if (sessionInfo.getUser() == null) {
        String actionName = ServletUtils.getInstance().getStrutsActionMapping(request);
        if (!actionName.equals("logon")) {
          return computeForwardToLogonPage(mapping, request, actionName);
        }
      } else if (!isAuthorized(mapping, sessionInfo.getUser())) {
        return mapping.findForward("unauthorized");
      }

    } catch (PartnersException t) {
      logger.error("Error occured when execute action for class \"" + getClass() + "\".", t);
    }

    try {
      return executeChild(mapping, form, request, response);
    } catch (PartnersException t) {
      logger.error("Error occured when execute action for class \"" + getClass() + "\".", t);

      return mapping.findForward("home");
    }
  }

  public abstract ActionForward executeChild(ActionMapping mapping,
          ActionForm form, HttpServletRequest request, HttpServletResponse response) throws PartnersException;
}

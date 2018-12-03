package es.regalocio.partners.struts.action.logon;

import es.regalocio.partners.business.common.RetailOutlet;
import es.regalocio.partners.business.common.User;
import es.regalocio.partners.business.common.shared.InvalidPasswordException;
import es.regalocio.partners.business.common.shared.InvalidRemoteIPException;
import es.regalocio.partners.business.common.shared.UnknownUserException;
import es.regalocio.partners.business.common.shared.UserAccountDisabledException;
import es.regalocio.partners.business.services.UserService;
import es.regalocio.partners.config.PartnersServices;
import es.regalocio.partners.config.PartnersUtils;
import es.regalocio.partners.shared.SessionInfo;
import es.regalocio.partners.struts.action.DynaValidatorFormAction;
import es.regalocio.partners.utils.CookieUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.*;
import org.apache.struts.validator.DynaValidatorForm;

public class LogonAction extends DynaValidatorFormAction {

  private static final Log logger = LogFactory.getLog(LogonAction.class);
  public static final String COOKIE_AUTHENT_KEY = "partners_application_login";

  @Override
  public void runOnce(ActionMapping mapping, DynaValidatorForm form, HttpServletRequest request,
          HttpServletResponse response) {
    form.set("login", CookieUtils.getInstance().getCookieValue(request, COOKIE_AUTHENT_KEY));
  }

  @Override
  public void doValidate(ActionMapping mapping, ActionMessages messages, DynaValidatorForm form,
          HttpServletRequest request, HttpServletResponse response) {

    if (!messages.isEmpty()) {
      return;
    }
    UserService service = PartnersServices.createUserService();
    User user;
    try {
      user = service.checkUser(form.getString("login"), form.getString("password"), request.getRemoteAddr());
    } catch (UnknownUserException e) {
      messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("unknown.user"));
      return;
    } catch (InvalidPasswordException e) {
      messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("invalid.password"));
      return;
    } catch (UserAccountDisabledException e) {
      switch (e.getMotive()) {
        case -1:
          messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("user.account.disabled2"));
          break;
        default:
          messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("user.account.disabled"));
          break;
      }

      return;
    } catch (InvalidRemoteIPException e) {
      messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("user.invalid.ip"));
      return;
    }

    SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(SessionInfo.SESSION_INFO);
    sessionInfo.setUser(user);

    // Comprueba si existen imágenes asociadas al perfil
    if (user instanceof RetailOutlet) {
      final String personalPath = "distrib/" + ((RetailOutlet) user).getDistributionNetwork().getId();
      try {
        ServletContext sc = request.getSession().getServletContext();
        if (sc.getRealPath(personalPath + "/header.jpg") != null && (new File(sc.getRealPath(personalPath + "/header.jpg"))).exists()) {
          sessionInfo.getPersonal().put("logo", personalPath + "/header.jpg");
        }
        if (sc.getRealPath(personalPath + "/personal.txt") != null && (new File(sc.getRealPath(personalPath + "/personal.txt"))).exists()) {
          ResourceBundle rb = new PropertyResourceBundle(new FileInputStream(sc.getRealPath(personalPath + "/personal.txt")));
          for (String key : rb.keySet()) {
            sessionInfo.getPersonal().put(key, rb.getString(key));
          }
        }
      } catch (Exception e) {
        logger.info(this, e);
      }
    }

    PartnersServices.createLogService().addUsrLog("partners", "LogonAction", user.getLogin(), request.getRemoteAddr());

    try {
      CookieUtils.getInstance().setCookie(response, COOKIE_AUTHENT_KEY, form.getString("login"),
              Integer.parseInt(PartnersUtils.getInstance().getProperty("cookie.max.age")));
    } catch (Exception e) {
    }
  }

  @Override
  public ActionForward doExecute(ActionMapping mapping, DynaValidatorForm form, HttpServletRequest request,
          HttpServletResponse response) {

    String url = form.getString("redirect_path");
    if (url != null) {
      try {
        url = URLDecoder.decode(url, "UTF-8");
      } catch (UnsupportedEncodingException e) {
        url = null;
      }
    }

    return (url == null) ? mapping.findForward("home") : new RedirectingActionForward(url);
  }
}

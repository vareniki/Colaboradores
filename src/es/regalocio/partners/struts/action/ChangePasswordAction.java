package es.regalocio.partners.struts.action;

import es.regalocio.partners.business.common.User;
import es.regalocio.partners.business.common.shared.InvalidPasswordException;
import es.regalocio.partners.business.common.shared.InvalidRemoteIPException;
import es.regalocio.partners.business.common.shared.UnknownUserException;
import es.regalocio.partners.business.common.shared.UserAccountDisabledException;
import es.regalocio.partners.business.services.UserService;
import es.regalocio.partners.config.PartnersServices;
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

public class ChangePasswordAction extends DynaValidatorFormAction {

  @Override
  public void doValidate(ActionMapping mapping, ActionMessages messages, DynaValidatorForm form,
          HttpServletRequest request, HttpServletResponse response) {

    if (!messages.isEmpty()) {
      return;
    }

    UserService service = PartnersServices.createUserService();
    SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(SessionInfo.SESSION_INFO);
    User user = sessionInfo.getUser();

    try {
      service.checkUser(user.getLogin(), form.getString("password"));
    } catch (InvalidPasswordException e) {
      messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("invalid.password"));
      return;
    } catch (UserAccountDisabledException | UnknownUserException | InvalidRemoteIPException e) {
      Logger.getLogger(ChangePasswordAction.class.getName()).log(Level.SEVERE, null, e);
    }

    service.updatePassword(user.getId(), form.getString("new_password"));
  }

  @Override
  public ActionForward doExecute(ActionMapping mapping, DynaValidatorForm form,
          HttpServletRequest request, HttpServletResponse response) {

    ActionMessages messages = new ActionMessages();
    messages.add(org.apache.struts.Globals.MESSAGE_KEY, new ActionMessage("password.updated"));
    saveMessages(request.getSession(), messages);

    return mapping.findForward("home");
  }
}

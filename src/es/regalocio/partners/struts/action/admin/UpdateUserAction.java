package es.regalocio.partners.struts.action.admin;

import es.regalocio.partners.business.common.InternalUser;
import es.regalocio.partners.business.common.shared.LoginAlreadyUsedException;
import es.regalocio.partners.business.services.UserService;
import es.regalocio.partners.config.PartnersServices;
import es.regalocio.partners.shared.SessionInfo;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.validator.DynaValidatorForm;

public class UpdateUserAction extends AddUserAction {

  @Override
  public void runOnce(ActionMapping mapping, DynaValidatorForm form,
          HttpServletRequest request, HttpServletResponse response) {

    UserService service = PartnersServices.createUserService();
    InternalUser user = service.loadInternalUser((Integer) form.get("user_id"));

    form.set("login", user.getLogin());
    form.set("firstname", user.getFirstname());
    form.set("lastname", user.getLastname());
    form.set("email", user.getEmail());
    form.set("profile_code_array", user.getProfiles().toArray(new Integer[user.getProfiles().size()]));
  }

  @Override
  public void doValidate(ActionMapping mapping, ActionMessages messages, DynaValidatorForm form,
          HttpServletRequest request, HttpServletResponse response) {

    if (!messages.isEmpty()) {
      return;
    }
    if (noProfileChecked(form)) {
      messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("profil.mandatory"));
      return;
    }
    InternalUser user;
    try {
      user = createUser(form);
      user.setId((Integer) form.get("user_id"));
      PartnersServices.createUserService().updateInternalUser(user);
    } catch (LoginAlreadyUsedException e) {
      messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("login.already.used"));
      return;
    }

    SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(SessionInfo.SESSION_INFO);
    if (sessionInfo.getUser().getId() == user.getId()) {
      sessionInfo.setUser(user);
    }
  }

  @Override
  public ActionForward doExecute(ActionMapping mapping, DynaValidatorForm form,
          HttpServletRequest request, HttpServletResponse response) {

    ActionMessages messages = new ActionMessages();
    messages.add(org.apache.struts.Globals.MESSAGE_KEY, new ActionMessage("update.user.success"));
    saveMessages(request.getSession(), messages);

    return mapping.findForward("success");
  }
}

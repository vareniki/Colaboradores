package es.regalocio.partners.struts.action.admin;

import es.regalocio.partners.business.common.shared.LoginAlreadyUsedException;
import es.regalocio.partners.business.services.UserService;
import es.regalocio.partners.config.PartnersServices;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.validator.DynaValidatorForm;

public class AddUserAction extends UserAction {

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

    try {
      UserService service = PartnersServices.createUserService();
      service.createInternalUser(createUser(form));
    } catch (LoginAlreadyUsedException e) {
      messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("login.already.used"));
    }
  }

  @Override
  public ActionForward doExecute(ActionMapping mapping, DynaValidatorForm form,
          HttpServletRequest request, HttpServletResponse response) {
    ActionMessages messages = new ActionMessages();
    messages.add(Globals.MESSAGE_KEY, new ActionMessage("add.user.success"));
    saveMessages(request.getSession(), messages);

    return mapping.findForward("success");
  }
}

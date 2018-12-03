package es.regalocio.partners.struts.action.admin;

import es.regalocio.partners.business.common.StaticDefinition;
import es.regalocio.partners.business.common.sql.OptionalSQLClause;
import es.regalocio.partners.business.services.UserService;
import es.regalocio.partners.config.PartnersServices;
import es.regalocio.partners.struts.action.AjaxAction;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ListOfUsersAjaxAction extends AjaxAction {

  @Override
  public ActionForward execute(ActionMapping mapping, ActionForm form,
          HttpServletRequest request, HttpServletResponse response) {

    UserService service = PartnersServices.createUserService();
    OptionalSQLClause optionalSQLClause = createOptionalSQLClause(request, StaticDefinition.USER_COLUMNS);
    request.setAttribute("list_of_users", service.loadAllInternalUsers(optionalSQLClause));
    request.setAttribute("total_records", service.getRowCount());

    return new ActionForward(mapping.findForward("success"));
  }
}

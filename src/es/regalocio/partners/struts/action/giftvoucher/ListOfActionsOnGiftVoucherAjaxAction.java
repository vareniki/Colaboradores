package es.regalocio.partners.struts.action.giftvoucher;

import es.regalocio.partners.business.common.StaticDefinition;
import es.regalocio.partners.business.common.sql.OptionalSQLClause;
import es.regalocio.partners.business.services.GiftVoucherService;
import es.regalocio.partners.config.PartnersServices;
import es.regalocio.partners.struts.action.AjaxAction;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ListOfActionsOnGiftVoucherAjaxAction extends AjaxAction {

  @Override
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

    GiftVoucherService service = PartnersServices.createGiftVoucherService();
    OptionalSQLClause optionalSQLClause = createOptionalSQLClause(request, StaticDefinition.ACTION_COLUMNS);
    request.setAttribute("list_of_actions", service.getListOfActions(request.getParameter("ean8"), optionalSQLClause));

    return mapping.findForward("success");
  }
}

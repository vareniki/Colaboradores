package es.regalocio.partners.struts.action.distributionnetwork;

import es.regalocio.partners.business.common.StaticDefinition;
import es.regalocio.partners.business.common.sql.OptionalSQLClause;
import es.regalocio.partners.business.services.EntityOfDistributionService;
import es.regalocio.partners.config.PartnersServices;
import es.regalocio.partners.struts.action.AjaxAction;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ListOfRetailOutletsAjaxAction extends AjaxAction {

  @Override
  public ActionForward execute(ActionMapping mapping, ActionForm form,
          HttpServletRequest request, HttpServletResponse response) {

    EntityOfDistributionService service = PartnersServices.createDistributionService();
    OptionalSQLClause optionalSQLClause = createOptionalSQLClause(request, StaticDefinition.RETAIL_OUTLET_COLUMNS);
    int entityId = Integer.parseInt(request.getParameter("distribution_network_id"));

    request.setAttribute("disable_change_password", service.getDistributionNetwork(entityId).isDisableChangePassword());
    request.setAttribute("list_of_retail_outlets",
            service.loadAllRetailOutlets(entityId, optionalSQLClause, Boolean.valueOf(request.getParameter("only_enabled"))));
    request.setAttribute("total_records", service.getRowCount());

    HashMap<String, String> URLParameters = new HashMap<>();

    URLParameters.put("distribution_network_id", "" + entityId);
    request.setAttribute("URLParameters", URLParameters);

    return mapping.findForward("success");
  }
}

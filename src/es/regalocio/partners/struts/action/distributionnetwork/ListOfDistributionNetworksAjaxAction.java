package es.regalocio.partners.struts.action.distributionnetwork;

import es.regalocio.partners.business.common.StaticDefinition;
import es.regalocio.partners.business.common.sql.OptionalSQLClause;
import es.regalocio.partners.business.services.EntityOfDistributionService;
import es.regalocio.partners.config.PartnersServices;
import es.regalocio.partners.struts.action.AjaxAction;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ListOfDistributionNetworksAjaxAction extends AjaxAction {

  @Override
  public ActionForward execute(ActionMapping mapping, ActionForm form,
          HttpServletRequest request, HttpServletResponse response) {

    EntityOfDistributionService service = PartnersServices.createDistributionService();
    OptionalSQLClause optionalSQLClause = createOptionalSQLClause(request, StaticDefinition.DISTRIBUTION_NETWORK_COLUMNS);
    request.setAttribute("list_of_distribution_networks", service.loadAllDistributionNetworks(optionalSQLClause));
    request.setAttribute("total_records", service.getRowCount());

    return mapping.findForward("success");
  }
}
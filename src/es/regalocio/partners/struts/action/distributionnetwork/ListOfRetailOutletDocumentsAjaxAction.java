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

public class ListOfRetailOutletDocumentsAjaxAction extends AjaxAction {

  @Override
  public ActionForward execute(ActionMapping mapping, ActionForm form,
          HttpServletRequest request, HttpServletResponse response) {

    EntityOfDistributionService service = PartnersServices.createDistributionService();
    OptionalSQLClause optionalSQLClause = createOptionalSQLClause(request, StaticDefinition.RETAIL_OUTLET_DOCUMENTS_COLUMNS);

    if (optionalSQLClause.getOrderClause() == null) {
      optionalSQLClause.setOrder(2, OptionalSQLClause.ORDER_DESC);
    }

    request.setAttribute("list_of_documents",
            service.getRetailOutletDocuments(Integer.parseInt(request.getParameter("entity_id")), optionalSQLClause));
    request.setAttribute("total_records", service.getRowCount());

    HashMap<String, String> URLParameters = new HashMap<>();
    URLParameters.put("entity_id", request.getParameter("entity_id"));
    request.setAttribute("URLParameters", URLParameters);

    return mapping.findForward("success");
  }
}

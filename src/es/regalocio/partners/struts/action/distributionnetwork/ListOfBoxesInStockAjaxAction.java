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

public class ListOfBoxesInStockAjaxAction extends AjaxAction {

  @Override
  public ActionForward execute(ActionMapping mapping, ActionForm form,
          HttpServletRequest request, HttpServletResponse response) {

    int entityId = Integer.parseInt(request.getParameter("entity_id"));
    EntityOfDistributionService service = PartnersServices.createDistributionService();
    OptionalSQLClause optionalSQLClause = createOptionalSQLClause(request, StaticDefinition.BOXES_IN_STOCK_COLUMNS);
    request.setAttribute("list_of_boxes_in_stock", service.getBoxesInStock(entityId, optionalSQLClause));
    request.setAttribute("total_records", service.getRowCount());
    
    return mapping.findForward("success");
  }

}
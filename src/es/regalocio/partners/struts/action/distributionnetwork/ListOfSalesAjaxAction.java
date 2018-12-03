package es.regalocio.partners.struts.action.distributionnetwork;

import es.regalocio.partners.business.common.StaticDefinition;
import es.regalocio.partners.business.common.sql.OptionalSQLClause;
import es.regalocio.partners.business.services.EntityOfDistributionService;
import es.regalocio.partners.config.PartnersServices;
import es.regalocio.partners.struts.action.AjaxAction;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ListOfSalesAjaxAction extends AjaxAction {

  @Override
  public ActionForward execute(ActionMapping mapping, ActionForm baseForm,
          HttpServletRequest request, HttpServletResponse response) {

    EntityOfDistributionService srv = PartnersServices.createDistributionService();
    SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy", new Locale("es", "ES"));
    OptionalSQLClause optionalSQLClause = createOptionalSQLClause(request, StaticDefinition.SALES_COLUMNS);

    Date fromDate = null;
    try {
      if (request.getParameter("sales_start") != null && !request.getParameter("sales_start").isEmpty()) {
        fromDate = fmt.parse(request.getParameter("sales_start"));
      }
    } catch (ParseException e) {
    }

    Date toDate = null;
    try {
      if (request.getParameter("sales_end") != null && !request.getParameter("sales_end").isEmpty()) {
        toDate = fmt.parse(request.getParameter("sales_end"));
      }
    } catch (ParseException e) {
      toDate = null;
    }

    if (!optionalSQLClause.hasOrderAttributes()) {
      optionalSQLClause.setOrder(2, OptionalSQLClause.ORDER_DESC);
    }
    request.setAttribute("list_of_sales",
            srv.getListOfSales(Integer.valueOf(request.getParameter("entity_id")),
                    fromDate, toDate, optionalSQLClause));
    request.setAttribute("total_records", srv.getRowCount());

    return mapping.findForward("success");
  }

}

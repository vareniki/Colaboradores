package es.regalocio.partners.struts.action.repayment;

import es.regalocio.partners.business.common.SearchRepaymentRequest;
import es.regalocio.partners.business.common.StaticDefinition;
import es.regalocio.partners.business.common.sql.OptionalSQLClause;
import es.regalocio.partners.business.services.RepaymentService;
import es.regalocio.partners.config.PartnersServices;
import es.regalocio.partners.struts.action.AjaxAction;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ListOfRepaymentsAjaxAction extends AjaxAction {

  private static SearchRepaymentRequest createSearchRepayment(HttpServletRequest request) {

    SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
    SearchRepaymentRequest searchRepayments = new SearchRepaymentRequest();

    String input = request.getParameter("input_text");
    if (!GenericValidator.isBlankOrNull(input)) {
      searchRepayments.setGenericSearch(input);
    }

    String value = request.getParameter("partner_code");
    if (!GenericValidator.isBlankOrNull(value)) {
      if (value.length() == 7) {
        value = value.substring(0, 2) + ' ' + value.substring(2);
      }
      searchRepayments.setPartnerCode(value);
    }

    value = request.getParameter("repayment_start");
    if (!GenericValidator.isBlankOrNull(value)) {
      try {
        searchRepayments.setMinRepaymentDate(fmt.parse(value));
      } catch (ParseException ex) {
        Logger.getLogger(ListOfRepaymentsAjaxAction.class.getName()).log(Level.SEVERE, null, ex);
      }
    }

    value = request.getParameter("repayment_end");
    if (!GenericValidator.isBlankOrNull(value)) {
      try {
        searchRepayments.setMaxRepaymentDate(fmt.parse(value));
      } catch (ParseException ex) {
        Logger.getLogger(ListOfRepaymentsAjaxAction.class.getName()).log(Level.SEVERE, null, ex);
      }
    }

    searchRepayments.setStatus(request.getParameter("repayment_status"));

    return searchRepayments;
  }

  @Override
  public ActionForward execute(ActionMapping mapping, ActionForm form,
          HttpServletRequest request, HttpServletResponse response) {

    RepaymentService srv = PartnersServices.createRepaymentService();
    OptionalSQLClause optionalSQLClause = createOptionalSQLClause(request, StaticDefinition.LIST_OF_REPAYMENTS_COLUMNS);
    SearchRepaymentRequest search = createSearchRepayment(request);

    request.setAttribute("list_of_repayments", srv.lookForRepayment(search, optionalSQLClause));
    request.setAttribute("total_records", srv.getRowCount());

    return mapping.findForward("success");
  }
}

package es.regalocio.partners.struts.action;

import es.regalocio.partners.business.common.sql.Filter;
import es.regalocio.partners.business.common.sql.OptionalSQLClause;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.Action;

public abstract class AjaxAction extends Action {

  /**
   *
   * @param request
   * @param optionalSQLClause
   */
  private static void setLimitClause(HttpServletRequest request, OptionalSQLClause optionalSQLClause) {
    Integer offset = null;
    try {
      offset = Integer.valueOf(request.getParameter("start"));
    } catch (Exception e) {
    }

    Integer numberOfRows = null;
    try {
      numberOfRows = Integer.valueOf(request.getParameter("length"));
    } catch (Exception e) {
    }

    if (numberOfRows != null) {
      if (offset == null) {
        optionalSQLClause.setLimit(numberOfRows);
      } else {
        optionalSQLClause.setLimit(offset, numberOfRows);
      }
    }
  }

  /**
   *
   * @param request
   * @param optionalSQLClause
   */
  private static void setOrderClause(HttpServletRequest request, OptionalSQLClause optionalSQLClause) {

    if (request.getParameter("order[0][column]") == null) {
      return;
    }
    Integer col = Integer.valueOf(request.getParameter("order[0][column]"));
    String dir = request.getParameter("order[0][dir]");
    if (dir == null || dir.equals("asc")) {
      optionalSQLClause.setOrder(col, OptionalSQLClause.ORDER_ASC);
    } else {
      optionalSQLClause.setOrder(col, OptionalSQLClause.ORDER_DESC);
    }
  }

  /**
   *
   * @param request
   * @param optionalSQLClause
   */
  private void setFiltersClause(HttpServletRequest request, OptionalSQLClause optionalSQLClause) {
    optionalSQLClause.putFilter(new Filter(request.getParameter("search[value]")));
  }

  /**
   *8
   * @param request
   * @param columnNames
   * @return
   */
  protected OptionalSQLClause createOptionalSQLClause(HttpServletRequest request, String[] columnNames) {
    OptionalSQLClause optionalSQLClause = new OptionalSQLClause(columnNames);
    setLimitClause(request, optionalSQLClause);
    setOrderClause(request, optionalSQLClause);
    setFiltersClause(request, optionalSQLClause);

    return optionalSQLClause;
  }
}

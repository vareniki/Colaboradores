package es.regalocio.partners.struts.action.distributionnetwork;

import es.regalocio.partners.business.common.DistributionNetwork;
import es.regalocio.partners.business.common.DistributorMargin;
import es.regalocio.partners.business.common.DistributorMarginList;
import es.regalocio.partners.business.services.EntityOfDistributionService;
import es.regalocio.partners.config.PartnersServices;
import es.regalocio.partners.struts.action.AjaxAction;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ListOfDistributorMarginsAjaxAction extends AjaxAction {

  private static List<Boolean> computeRemovableMargins(DistributorMarginList margins, DistributorMarginList unremovableMargins) {

    List<Boolean> removableMargins = new ArrayList<>(margins.size());
    for (DistributorMargin margin : margins) {
      removableMargins.add(!unremovableMargins.contains(margin));
    }
    return removableMargins;
  }

  private static int getFirstIndex(HttpServletRequest request) {

    Integer offset = null;
    try {
      offset = Integer.valueOf(request.getParameter("start"));
    } catch (Exception e) {
    }
    return (offset == null) ? 0 : offset;
  }

  private static int getLastIndex(HttpServletRequest request, int firstIndex, int maxIndex) {
    Integer numberOfRows = null;
    try {
      numberOfRows = Integer.valueOf(request.getParameter("length"));
    } catch (Exception e) {
    }
    return (numberOfRows == null) ? maxIndex : Math.min(firstIndex + numberOfRows - 1, maxIndex);
  }

  @Override
  public ActionForward execute(ActionMapping mapping, ActionForm form,
          HttpServletRequest request, HttpServletResponse response) {

    if (request.getParameter("entity_id") == null) {
      return null;
    }
    EntityOfDistributionService srv = PartnersServices.createDistributionService();

    DistributionNetwork dn = srv.getDistributionNetwork(
            Integer.parseInt(request.getParameter("entity_id")));

    DistributorMarginList allMargins = filterMargins(dn.getDistributorMarginList());
    DistributorMarginList marginsToReturn = new DistributorMarginList();

    int firstIndex = getFirstIndex(request);
    int lastIndex = getLastIndex(request, firstIndex, allMargins.size() - 1);

    for (int counter = firstIndex; counter <= lastIndex; counter++) {
      DistributorMargin margin = allMargins.get(counter);
      marginsToReturn.add(margin);
    }

    request.setAttribute("list_of_distributor_margins", marginsToReturn);
    request.setAttribute("total_records", allMargins.size());

    request.setAttribute("removable_margins", computeRemovableMargins(marginsToReturn,
            srv.getUnremovableMargins(Integer.parseInt(request.getParameter("entity_id")))));

    Map<String, String> URLParameters = new HashMap<>();
    URLParameters.put("entity_id", request.getParameter("entity_id"));
    request.setAttribute("URLParameters", URLParameters);

    return mapping.findForward("success");
  }

  /**
   *
   * @param distributorMarginList
   * @return
   */
  private DistributorMarginList filterMargins(DistributorMarginList distributorMarginList) {
    DistributorMarginList result = new DistributorMarginList();
    for (DistributorMargin margin : distributorMarginList.getListOfMargins()) {
      Calendar cal = GregorianCalendar.getInstance();
      int anioActual = cal.get(Calendar.YEAR);

      cal.setTime(margin.getEndOfValidity());
      int anioCaducidad = cal.get(Calendar.YEAR);
      if (anioCaducidad < anioActual) {
        continue;
      }

      result.add(margin);
    }
    return result;
  }
}

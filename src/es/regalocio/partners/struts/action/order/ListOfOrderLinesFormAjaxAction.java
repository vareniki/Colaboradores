package es.regalocio.partners.struts.action.order;

import es.regalocio.partners.bean.OrderLineBean;
import es.regalocio.partners.bean.OrderLineComparator;
import es.regalocio.partners.business.common.DistributionNetwork;
import es.regalocio.partners.business.common.DistributorMargin;
import es.regalocio.partners.business.common.Thematic;
import es.regalocio.partners.business.common.ThematicRate;
import es.regalocio.partners.business.services.EntityOfDistributionService;
import es.regalocio.partners.config.PartnersServices;
import es.regalocio.partners.config.PartnersUtils;
import java.text.MessageFormat;
import java.text.ParseException;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.DynaValidatorForm;

public class ListOfOrderLinesFormAjaxAction extends Action {

  @Override
  public ActionForward execute(ActionMapping mapping, ActionForm baseForm,
          HttpServletRequest request, HttpServletResponse response) {

    DynaValidatorForm form = (DynaValidatorForm) baseForm;

    List<OrderLineBean> orderLinesToReturn = new ArrayList<>();

    EntityOfDistributionService service = PartnersServices.createDistributionService();
    Integer dnId = (Integer) form.get("distribution_network_id");

    if (dnId == 0) {
      return mapping.findForward("success");
    }

    DistributionNetwork distributionNetwork = service.getDistributionNetwork(dnId);
    String[] ean13Array = (String[]) form.get("ean13_array");
    Integer[] amountArray = (Integer[]) form.get("amount_array");

    PartnersUtils utils = PartnersUtils.getInstance();

    List<OrderLineBean> allOrderLines = new ArrayList<>();
    for (Thematic thematic : utils.getThematics()) {

      ThematicRate thematicRate = thematic.getThematicRate();
      if (!thematicRate.isOrderEnabled() || !thematicRate.isCreationEnabled()) {
        continue;
      }

      DistributorMargin dm = utils.getDistributorMarginForRate(distributionNetwork, thematic, thematicRate);
      
      if ((thematic.getId() < 900 || thematic.getId() > 999) && dm == null) {
        continue;
      }

      int amount = 0;

      if (ean13Array != null) {
        for (int counter = 0; counter < ean13Array.length; counter++) {
          if (thematicRate.getEan13().equals(ean13Array[counter])) {
            amount = amountArray[counter];
            break;
          }
        }
      }

      double margin = (dm != null)?dm.getMargin() : 0;
      
      allOrderLines.add(
              new OrderLineBean("" + thematic.getId() + " - " + thematic.toString(),
                      thematicRate.getEan13(), thematicRate.getEndOfValidity(),
                      thematicRate.getPrice(), margin, amount));
    }

    int firstIndex = getFirstIndex(request);
    int lastIndex = getLastIndex(request, firstIndex, allOrderLines.size() - 1);

    for (int i = firstIndex; i <= lastIndex; i++) {
      orderLinesToReturn.add(allOrderLines.get(i));
    }

    Comparator<OrderLineBean> comparator = createComparator(request);
    if (comparator != null) {
      Collections.sort(orderLinesToReturn, comparator);
    }

    request.setAttribute("order_line_list", orderLinesToReturn);

    return mapping.findForward("success");
  }

  private static int getFirstIndex(HttpServletRequest request) {
    Integer offset = null;
    try {
      offset = Integer.valueOf(request.getParameter("offset"));
    } catch (NumberFormatException e) {
    }
    return (offset == null) ? 0 : offset;
  }

  private static int getLastIndex(HttpServletRequest request, int firstIndex, int maxIndex) {
    Integer numberOfRows = null;
    try {
      numberOfRows = Integer.valueOf(request.getParameter("page_size"));
    } catch (NumberFormatException e) {
    }
    return (numberOfRows == null) ? maxIndex : Math.min(firstIndex + numberOfRows - 1, maxIndex);
  }

  private static OrderLineComparator createComparator(HttpServletRequest request) {

    MessageFormat messageFormat = new MessageFormat("s{0,number,integer}");
    Enumeration<String> enum15 = request.getParameterNames();
    while (enum15.hasMoreElements()) {
      String name = enum15.nextElement();
      try {
        Object[] result = messageFormat.parse(name);
        String direction = request.getParameter(name);
        if (direction == null) {
          continue;
        }
        if (direction.equals("ASC")) {
          return new OrderLineComparator(
                  OrderLineComparator.SORTABLE_FIELD[((Number) result[0]).intValue()],
                  OrderLineComparator.ORDER_ASC);
        }
        if (direction.equals("DESC")) {
          return new OrderLineComparator(
                  OrderLineComparator.SORTABLE_FIELD[((Number) result[0]).intValue()],
                  OrderLineComparator.ORDER_DESC);
        }
      } catch (ParseException e) {
      }
    }

    return null;
  }
}

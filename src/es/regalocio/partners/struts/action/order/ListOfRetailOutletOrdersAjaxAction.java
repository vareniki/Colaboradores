package es.regalocio.partners.struts.action.order;

import es.regalocio.partners.business.common.StaticDefinition;
import es.regalocio.partners.business.common.shared.PartnersServerRuntimeException;
import es.regalocio.partners.business.common.sql.OptionalSQLClause;
import es.regalocio.partners.business.services.RetailOutletOrderService;
import es.regalocio.partners.config.PartnersServices;
import es.regalocio.partners.struts.action.AjaxAction;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ListOfRetailOutletOrdersAjaxAction extends AjaxAction {

  private static List<Map<String, Object>> addNumbersOfOrder(List<Map<String, Object>> orders) {
    for (Map<String, Object> order : orders) {
      order.put("number_of_order", computeEAN8((Integer) order.get("order_id")));
    }
    return orders;
  }

  private static String computeEAN8(int orderId) {

    if ((orderId <= 0) || (orderId >= 1000000)) {
      throw new PartnersServerRuntimeException("Invalid order value to compute ean8.");
    }
    StringBuilder ean8 = new StringBuilder(Integer.toString(orderId));
    int length = ean8.length();

    while (length < 6) {
      ean8.insert(0, '0');
      length++;
    }

    ean8.insert(0, StaticDefinition.PARTNERS_ORDER_PREFIX_EAN8);
    length++;

    int sum = 0;
    for (int counter = 0; counter < length; counter++) {
      if ((counter % 2) == 1) {
        sum += Integer.parseInt(Character.toString(ean8.charAt(counter)));
      } else {
        sum += 3 * Integer.parseInt(Character.toString(ean8.charAt(counter)));
      }
    }

    int key = sum % 10;
    if (key != 0) {
      key = 10 - key;
    }
    ean8.append(Integer.toString(key));

    return ean8.toString();
  }

  @Override
  public ActionForward execute(ActionMapping mapping, ActionForm form,
          HttpServletRequest request, HttpServletResponse response) {

    RetailOutletOrderService service = PartnersServices.createRetailOutletOrderService();
    OptionalSQLClause optionalSQLClause = createOptionalSQLClause(request, StaticDefinition.RETAIL_OUTLET_ORDERS_COLUMNS);

    List<Map<String, Object>> orders = addNumbersOfOrder(service.loadNotTransmittedOrders(optionalSQLClause));

    request.setAttribute("list_of_retail_outlet_orders", orders);
    request.setAttribute("total_records", service.getRowCount());

    return mapping.findForward("success");
  }
}

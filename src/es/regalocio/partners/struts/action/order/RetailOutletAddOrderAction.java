package es.regalocio.partners.struts.action.order;

import es.regalocio.partners.business.common.*;
import es.regalocio.partners.business.services.RetailOutletOrderService;
import es.regalocio.partners.config.PartnersServices;
import es.regalocio.partners.config.PartnersUtils;
import es.regalocio.partners.shared.SessionInfo;
import es.regalocio.partners.struts.action.DynaValidatorFormAction;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.validator.DynaValidatorForm;

public class RetailOutletAddOrderAction extends DynaValidatorFormAction {

  private static ThematicRate getOrderAvailableRate(Thematic thematic) {
    ThematicRate thematicRate = thematic.getThematicRate();
    return (thematicRate.isOrderEnabled()
            && thematicRate.isCreationEnabled() && thematicRate.getPrice() > 0) ? thematicRate : null;
  }

  private static List<OrderLine> getOrderLines(DistributionNetwork distributionNetwork, Map<String, Integer> mapEan13) {

    PartnersUtils partnersUtils = PartnersUtils.getInstance();
    List<OrderLine> orderLines = new ArrayList<>();
    List<Thematic> thematics = PartnersUtils.getInstance().getThematics();
    
    Collections.sort(thematics, new Comparator<Thematic>() {
      @Override
      public int compare(Thematic o1, Thematic o2) {
        if (o1.getThematicRate().getEndOfValidity().getTime() > o2.getThematicRate().getEndOfValidity().getTime()) {
          return -1;
        } else if (o1.getThematicRate().getEndOfValidity().getTime() < o2.getThematicRate().getEndOfValidity().getTime()) {
          return 1;
        } else {
          return 0;
        }
      }
    }) ;
    
    for (Thematic thematic : thematics) {
      ThematicRate thematicRate = getOrderAvailableRate(thematic);
      if (thematicRate == null || thematicRate.getEan13() == null || !thematicRate.isOrderEnabled()) {
        continue;
      }

      DistributorMargin distributorMargin
              = partnersUtils.getDistributorMarginForRate(distributionNetwork, thematic, thematicRate);

      if (distributorMargin == null) {
        continue;
      }
      int amount;
      if (mapEan13 != null && mapEan13.containsKey(thematicRate.getEan13())) {
        amount = mapEan13.get(thematicRate.getEan13());
        if (amount == 0) {
          continue;
        }
      } else {
        amount = 999;
      }
      orderLines.add(new OrderLine(
              thematicRate.getEan13(), thematic.getId(),
              "" + thematic.getId() + " - " + thematic.getName(),
              thematicRate.getEndOfValidity(),
              thematicRate.getPrice(), amount, distributorMargin.getMargin()));
    }
    return orderLines;
  }

  protected OrderLine createOrderLine(String ean13, int amount) {
    PartnersUtils partnersUtils = PartnersUtils.getInstance();
    Thematic thematic = partnersUtils.getEan13Thematic(ean13);
    ThematicRate rate = partnersUtils.getThematicRate(ean13);

    return new OrderLine(
            ean13, thematic.getId(), thematic.getName(), rate.getEndOfValidity(), rate.getPrice(), amount, null);
  }

  protected void createOrderLines(RetailOutletOrder order, DynaValidatorForm form) {
    String[] ean13Array = (String[]) form.get("ean13_array");
    Integer[] amountArray = (Integer[]) form.get("amount_array");
    for (int i = 0; i < ean13Array.length; i++) {
      order.getOrderLines().add(createOrderLine(ean13Array[i], amountArray[i]));
    }
  }

  @Override
  public ActionForward doExecute(ActionMapping mapping, DynaValidatorForm form,
          HttpServletRequest request, HttpServletResponse response) {

    String[] ean13Array = (String[]) form.get("ean13_array");
    Integer[] amountArray = (Integer[]) form.get("amount_array");
    String message = (String) form.getString("message");

    ActionMessages messages = new ActionMessages();
    if (ean13Array.length == amountArray.length) {
      messages.add(org.apache.struts.Globals.MESSAGE_KEY, new ActionMessage("retail.outlet.add.order.success"));
      saveMessages(request.getSession(), messages);
    } else {
      messages.add(org.apache.struts.Globals.MESSAGE_KEY, new ActionMessage("retail.outlet.add.order.error"));
      saveMessages(request.getSession(), messages);

      return mapping.findForward("success");
    }

    Map<String, Integer> mapEan13 = new HashMap<>();
    for (int i = 0; i < ean13Array.length; i++) {
      if (amountArray[i] == 999) {
        continue;
      }
      mapEan13.put(ean13Array[i], amountArray[i]);
    }
    SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(SessionInfo.SESSION_INFO);
    DistributionNetwork distributionNetwork = ((RetailOutlet) sessionInfo.getUser()).getDistributionNetwork();

    List<OrderLine> orderLines = getOrderLines(distributionNetwork, mapEan13);

    RetailOutletOrderService service = PartnersServices.createRetailOutletOrderService();
    service.sendRetailOrderEmail((RetailOutlet) sessionInfo.getUser(), orderLines, message);
    
    request.setAttribute("orderLines", orderLines);
    request.setAttribute("message", message);
    request.setAttribute("result", "true");
    return mapping.findForward("success");
  }

  @Override
  public void runOnce(ActionMapping mapping, DynaValidatorForm form,
          HttpServletRequest request, HttpServletResponse response) {

    SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(SessionInfo.SESSION_INFO);
    DistributionNetwork distributionNetwork = ((RetailOutlet) sessionInfo.getUser()).getDistributionNetwork();
    request.setAttribute("orderLines", getOrderLines(distributionNetwork, null));
  }
}

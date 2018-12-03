package es.regalocio.partners.struts.action.invoice;

import es.regalocio.partners.bean.InvoiceLineBean;
import es.regalocio.partners.bean.InvoiceLineComparator;
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

public class ListOfInvoiceLinesFormAjaxAction extends Action {

  /**
   *
   * @param request
   * @return
   */
  private static InvoiceLineComparator createComparator(HttpServletRequest request) {

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
          return new InvoiceLineComparator(
                  InvoiceLineComparator.SORTABLE_FIELD[((Number) result[0]).intValue()], InvoiceLineComparator.ORDER_ASC);
        }
        if (direction.equals("DESC")) {
          return new InvoiceLineComparator(
                  InvoiceLineComparator.SORTABLE_FIELD[((Number) result[0]).intValue()], InvoiceLineComparator.ORDER_DESC);
        }
      } catch (ParseException e) {
      }
    }

    return null;
  }

  private static int getFirstIndex(HttpServletRequest request) {
    Integer offset = null;
    try {
      offset = Integer.valueOf(request.getParameter("offset"));
    } catch (NumberFormatException e) {
    }
    return (offset == null) ? 0 : offset;
  }

  @Override
  public ActionForward execute(ActionMapping mapping, ActionForm baseForm,
          HttpServletRequest request, HttpServletResponse response) {

    DynaValidatorForm form = (DynaValidatorForm) baseForm;

    List<InvoiceLineBean> invoiceLinesToReturn = new ArrayList<>();
    try {
      int firstIndex = getFirstIndex(request);
      if (firstIndex == 0) {

        EntityOfDistributionService service = PartnersServices.createDistributionService();
        DistributionNetwork distributionNetwork = service.getRetailOutlet((Integer) form.get("entity_id")).getDistributionNetwork();
        String[] ean13Array =  ((String) form.get("ean13_array2")).split(",");
        String[] amountArray = ((String) form.get("amount_array2")).split(",");

        List<InvoiceLineBean> allInvoiceLines = new ArrayList<>();
        for (Thematic thematic : PartnersUtils.getInstance().getThematics()) {
          ThematicRate thematicRate = thematic.getThematicRate();
          if (!thematicRate.isCreationEnabled()) {
            continue;
          }

          Calendar cal = GregorianCalendar.getInstance();
          int anioActual = cal.get(Calendar.YEAR);

          cal.setTime(thematicRate.getEndOfValidity());
          int anioCaducidad = cal.get(Calendar.YEAR);

          if (anioCaducidad < anioActual && (thematic.getId() < 461 || thematic.getId() > 466)) {
            continue;
          }

          DistributorMargin distributorMargin
                  = PartnersUtils.getInstance().getDistributorMarginForRate(distributionNetwork, thematic, thematicRate);
          int amount = 0;
          if (distributorMargin != null) {
            if (ean13Array != null) {
              for (int counter = 0; counter < ean13Array.length; counter++) {
                if (thematicRate.getEan13().equals(ean13Array[counter])) {
                  amount = Integer.valueOf(amountArray[counter]);
                  break;
                }
              }
            }

            allInvoiceLines.add(new InvoiceLineBean(thematic.getId(),
                    thematic.toString(), thematicRate.getEan13(), thematicRate.getEndOfValidity(),
                    thematicRate.getPrice(), distributorMargin.getMargin(), amount));
          }

        }
        invoiceLinesToReturn.addAll(allInvoiceLines);
      }
      Comparator<InvoiceLineBean> comparator = createComparator(request);
      if (comparator != null) {
        Collections.sort(invoiceLinesToReturn, comparator);
      }
    } catch (NumberFormatException t) {
    }

    request.setAttribute("invoice_line_list", invoiceLinesToReturn);
    request.setAttribute("total_records", invoiceLinesToReturn.size());

    return mapping.findForward("success");
  }
}

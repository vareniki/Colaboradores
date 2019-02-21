package es.regalocio.partners.struts.action.distributionnetwork;

import es.regalocio.partners.business.common.DistributionNetwork;
import es.regalocio.partners.business.services.EntityOfDistributionService;
import es.regalocio.partners.business.services.InvoiceService;
import es.regalocio.partners.config.PartnersServices;
import es.regalocio.partners.config.PartnersUtils;
import es.regalocio.partners.struts.action.SuccessAction;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ConsultDistributionNetworkSheetAction extends SuccessAction {

  @Override
  public ActionForward executeChild(ActionMapping mapping, ActionForm form,
          HttpServletRequest request, HttpServletResponse response) {

    int entityId = 0;
    try {
      entityId = Integer.parseInt(request.getParameter("entity_id"));
    } catch (NumberFormatException e) {
    }
    
    GregorianCalendar calendar = new GregorianCalendar();
    calendar.add(Calendar.MONTH, -1);
    int month = calendar.get(Calendar.MONTH) + 1;
    int year = calendar.get(Calendar.YEAR);

    EntityOfDistributionService entityOfDistributionService = PartnersServices.createDistributionService();
    DistributionNetwork distributionNetwork = entityOfDistributionService.getDistributionNetwork(entityId);
    boolean retailOutletExists = entityOfDistributionService.retailOutletsExists(entityId);

    request.setAttribute("distribution_network", distributionNetwork);
    request.setAttribute("retail_outlet_exists", retailOutletExists);
    request.setAttribute("available_thematics_for_distributor_margin",
            PartnersUtils.getInstance().getDistributorMarginThematics(distributionNetwork));

    return super.executeChild(mapping, form, request, response);
  }
}

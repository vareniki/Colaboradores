package es.regalocio.partners.struts.action.distributionnetwork;

import es.regalocio.partners.business.common.DistributionNetwork;
import es.regalocio.partners.business.common.DistributorMargin;
import es.regalocio.partners.business.common.Thematic;
import es.regalocio.partners.business.common.ThematicRate;
import es.regalocio.partners.business.services.EntityOfDistributionService;
import es.regalocio.partners.config.PartnersServices;
import es.regalocio.partners.config.PartnersUtils;
import es.regalocio.partners.shared.SessionInfo;

import java.text.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.*;
import org.apache.struts.util.LabelValueBean;
import org.apache.struts.validator.DynaValidatorForm;

public class AddDistributorMarginAction extends DistributorMarginAction {

  private static List<String[]> createDistributorMarginData(
          List<Thematic> availableThematics, DistributionNetwork distributionNetwork) {

    NumberFormat af = NumberFormat.getCurrencyInstance(new Locale("es", "ES", "EURO"));

    SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy", new Locale("es", "ES"));
    List<String[]> distributorMarginData = new ArrayList<>();

    for (Thematic thematic : availableThematics) {
      ThematicRate thematicRate = thematic.getThematicRate();

      Calendar cal = GregorianCalendar.getInstance();
      int anioActual = cal.get(Calendar.YEAR);

      cal.setTime(thematicRate.getEndOfValidity());
      int anioCaducidad = cal.get(Calendar.YEAR);

      if (anioCaducidad < anioActual) {
        continue;
      }

      boolean found = false;
      for (DistributorMargin margin : distributionNetwork.getDistributorMarginList()) {
        if (margin.getThematicId() == thematic.getId()
                && margin.getEndOfValidity().equals(thematicRate.getEndOfValidity())) {

          found = true;
          break;
        }
      }
      if (!found) {
        distributorMarginData.add(new String[]{
          Integer.toString(thematic.getId()),
          df.format(thematicRate.getEndOfValidity()), af.format(thematicRate.getPrice())});
      }
    }

    return distributorMarginData;
  }

  private static List<LabelValueBean> createEndOfValidityList(DynaValidatorForm form, DistributionNetwork distributionNetwork) {

    List<LabelValueBean> list = new ArrayList<>();

    if (form.get("thematic_id") != null) {
      SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy", new Locale("es", "ES"));
      int thematicId = (Integer) form.get("thematic_id");
      Date endOfValidity;
      try {
        endOfValidity = (!form.getString("end_of_validity").equals("")) ? df.parse(form.getString("end_of_validity")) : null;
      } catch (ParseException ex) {
        Logger.getLogger(AddDistributorMarginAction.class.getName()).log(Level.SEVERE, null, ex);
        endOfValidity = null;
      }

      ThematicRate thematicRate = PartnersUtils.getInstance().getThematic(thematicId, endOfValidity).getThematicRate();

      boolean found = false;
      for (DistributorMargin margin : distributionNetwork.getDistributorMarginList()) {
        if ((margin.getThematicId() == thematicId) && margin.getEndOfValidity().equals(thematicRate.getEndOfValidity())) {
          found = true;
          break;
        }
      }

      if (!found) {
        String fmtEndOfValidity = df.format(thematicRate.getEndOfValidity());
        list.add(0, new LabelValueBean(fmtEndOfValidity, fmtEndOfValidity));
      }
    }

    return list;
  }

  private void createDistributorMargin(DynaValidatorForm form, HttpServletRequest request) {
    SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(SessionInfo.SESSION_INFO);
    EntityOfDistributionService service = PartnersServices.createDistributionService();
    service.addDistributorMarginToDistributionNetwork((Integer) form.get("entity_id"), createDistributorMargin(form), sessionInfo.getUser().getId());
  }

  @Override
  public ActionForward doExecute(ActionMapping mapping, DynaValidatorForm form,
          HttpServletRequest request, HttpServletResponse response) {

    ActionMessages messages = new ActionMessages();
    messages.add(org.apache.struts.Globals.MESSAGE_KEY, new ActionMessage("add.distributor.margin.success"));
    saveMessages(request.getSession(), messages);

    StringBuilder path = new StringBuilder(mapping.findForward("success").getPath());
    path.append("?entity_id=");
    path.append(form.get("entity_id"));

    return new RedirectingActionForward(path.toString());
  }

  @Override
  public void doValidate(ActionMapping mapping, ActionMessages messages,
          DynaValidatorForm form, HttpServletRequest request, HttpServletResponse response) {

    if (!messages.isEmpty() || !validateMargin(messages, form)) {
      return;
    }
    createDistributorMargin(form, request);
  }

  @Override
  public void initValues(ActionMapping mapping, DynaValidatorForm form, HttpServletRequest request) {

    EntityOfDistributionService service = PartnersServices.createDistributionService();
    DistributionNetwork distributionNetwork = service.getDistributionNetwork((Integer) form.get("entity_id"));
    request.setAttribute("distribution_network", distributionNetwork);

    List list = PartnersUtils.getInstance().getDistributorMarginThematics(distributionNetwork);
    request.setAttribute("distributor_margin_data", createDistributorMarginData(list, distributionNetwork));

    list.add(0, new LabelValueBean(null, null));
    request.setAttribute("thematic_list", list);

    list = createEndOfValidityList(form, distributionNetwork);
    if (!list.isEmpty()) {
      list.add(0, new LabelValueBean(null, null));
    }
    request.setAttribute("end_of_validity", list);

    createDistributorMarginInfo(form, request);
  }
}

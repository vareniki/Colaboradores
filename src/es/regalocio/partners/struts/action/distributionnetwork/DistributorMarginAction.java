package es.regalocio.partners.struts.action.distributionnetwork;

import es.regalocio.partners.business.common.DistributorMargin;
import es.regalocio.partners.business.common.ThematicRate;
import es.regalocio.partners.config.PartnersUtils;
import es.regalocio.partners.struts.action.DynaValidatorFormAction;
import java.io.Serializable;
import java.text.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.validator.DynaValidatorForm;

public abstract class DistributorMarginAction extends DynaValidatorFormAction {

  protected void createDistributorMarginInfo(DynaValidatorForm form, HttpServletRequest request) {

    HashMap<String, Serializable> distributorMarginInfo = new HashMap<>();
    Integer thematicId = (Integer) form.get("thematic_id");
    SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy", new Locale("es", "ES"));
    Date endOfValidity;
    try {
      endOfValidity = (!form.getString("end_of_validity").equals("")) ? df.parse(form.getString("end_of_validity")) : null;
    } catch (ParseException ex) {
      endOfValidity = null;
    }

    distributorMarginInfo.put("thematic",
            (thematicId == null) ? null : PartnersUtils.getInstance().getThematic(thematicId, endOfValidity));

    ThematicRate thematicRate = null;
    if (thematicId != null) {
      try {
        thematicRate = PartnersUtils.getInstance().getDistributorMarginThematicRate(
                new DistributorMargin(thematicId, endOfValidity, 0.0D));
      } catch (Exception e) {
      }
    }

    NumberFormat af = NumberFormat.getCurrencyInstance(new Locale("es", "ES", "EURO"));
    distributorMarginInfo.put("price", (thematicRate == null) ? null : af.format(thematicRate.getPrice()));

    request.setAttribute("distributor_margin_info", distributorMarginInfo);
  }

  protected DistributorMargin createDistributorMargin(DynaValidatorForm form) {
    SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy", new Locale("es", "ES"));
    Date date;
    try {
      date = df.parse(form.getString("end_of_validity"));
    } catch (ParseException ex) {
      date = null;
    }
    return new DistributorMargin((Integer) form.get("thematic_id"), date,
            Double.valueOf(form.getString("margin").replace(',', '.')));
  }

  protected boolean validateMargin(ActionMessages messages, DynaValidatorForm form) {

    if (GenericValidator.isBlankOrNull(form.getString("margin"))) {
      return true;
    }
    Double margin;
    try {
      margin = Double.valueOf(form.getString("margin").replace(',', '.'));
    } catch (NumberFormatException e) {
      messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("distributor.margin.margin.invalid"));
      return false;
    }

    Integer thematicId = (Integer) form.get("thematic_id");
    if (thematicId == null) {
      return true;
    }
    if (margin < 0.0D) {
      messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("distributor.margin.margin.negative"));
      return false;
    }

    SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy", new Locale("es", "ES"));
    Date endOfValidity;
    try {
      endOfValidity = dateFormatter.parse(form.getString("end_of_validity"));
    } catch (ParseException ex) {
      endOfValidity = null;
    }
    ThematicRate thematicRate = PartnersUtils.getInstance().getDistributorMarginThematicRate(
            new DistributorMargin(thematicId, endOfValidity, 0.0D));

    if (thematicRate == null) {
      return true;
    }
    if (margin.doubleValue() <= thematicRate.getPrice()) {
      return true;
    }
    messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("distributor.margin.margin.too.high"));
    return false;
  }
}

package es.regalocio.partners.struts.action.partner;

import es.regalocio.partners.business.common.SpecificAgreement;
import es.regalocio.partners.business.common.ThematicRate;
import es.regalocio.partners.config.PartnersUtils;
import es.regalocio.partners.struts.action.DynaValidatorFormAction;

import java.text.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.validator.DynaValidatorForm;

public abstract class SpecificAgreementAction extends DynaValidatorFormAction {

  /**
   * 
   * @param form
   * @param request 
   */
  protected void createSpecificAgreementInfo(DynaValidatorForm form, HttpServletRequest request) {

    HashMap agreementInfo = new HashMap();
    Integer thematicId = (Integer) form.get("thematic_id");

    PartnersUtils partnersUtils = PartnersUtils.getInstance();

    Date date;
    try {
      SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy", new Locale("es", "ES"));
      date = df.parse(form.getString("end_of_validity"));
    } catch (ParseException e) {
      date = null;
    }

    ThematicRate thematicRate = null;
    if (thematicId != null) {
      thematicRate = partnersUtils.getThematicRate(thematicId, date);
      thematicRate = partnersUtils.getAgreementThematicRate(
              new SpecificAgreement(thematicId, thematicRate.getEndOfValidity(), 0, null, null, null, 1, 0, null));
    }

    NumberFormat df = NumberFormat.getCurrencyInstance(new Locale("es", "ES", "EURO"));

    agreementInfo.put("thematic", thematicRate);
    agreementInfo.put("price", (thematicRate != null) ? df.format(thematicRate.getPrice()) : null);
    agreementInfo.put("default_commission", (thematicRate != null) ? df.format(thematicRate.getDefaultCommission()) : null);

    request.setAttribute("specific_agreement_info", agreementInfo);
  }

  /**
   * 
   * @param form
   * @return 
   */
  protected SpecificAgreement createSpecificAgreement(DynaValidatorForm form) {
    SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy", new Locale("es", "ES"));

    Date date;
    try {
      date = df.parse(form.getString("end_of_validity"));
    } catch (ParseException e) {
      date = null;
    }

    Double commission = 0D;
    try {
      commission = Double.valueOf(form.getString("commission").replace(',', '.'));
    } catch (NumberFormatException e) {
    }

    Double priceService = null;
    try {
      priceService = Double.valueOf(form.getString("price_service").replace(',', '.'));
    } catch (NumberFormatException e) {
    }
    
    String word = form.getString("word");
    
    Integer newAgreement = 1;
    try {
      newAgreement = (Integer) form.get("new_agreement");
    } catch (NumberFormatException e) {
    }
    
    String agreementSince = (String) form.getString("agreement_since");

    return new SpecificAgreement(
            (Integer) form.get("thematic_id"), date, 
            commission, priceService, null, word, 
            newAgreement, 0, agreementSince);
  }

  /**
   * 
   * @param messages
   * @param form
   * @return 
   */
  protected boolean validateCommission(ActionMessages messages, DynaValidatorForm form) {

    if (GenericValidator.isBlankOrNull(form.getString("commission"))) {
      return true;
    }
    Double commission;
    try {
      commission = Double.valueOf(form.getString("commission").replace(',', '.'));
    } catch (NumberFormatException e) {
      messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("specific.agreement.commission.invalid"));
      return false;
    }

    Integer thematicId = (Integer) form.get("thematic_id");
    if (thematicId == null) {
      return true;
    }
    if (commission < 0) {
      messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("specific.agreement.commission.negative"));
      return false;
    }

    ThematicRate thematicRate = null;
    SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy", new Locale("es", "ES"));
    try {
      Date endOfValidity = (!form.getString("end_of_validity").isEmpty()) ? df.parse(form.getString("end_of_validity")) : null;
      thematicRate = PartnersUtils.getInstance().getAgreementThematicRate(
              new SpecificAgreement(thematicId, endOfValidity, 0, null, null, null, 1, 0, null));
    } catch (Exception e) {
    }

    if (thematicRate == null || commission <= thematicRate.getPrice()) {
      return true;
    }

    messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("specific.agreement.commission.too.high"));
    return false;
  }
}

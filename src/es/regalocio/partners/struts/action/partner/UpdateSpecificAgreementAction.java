package es.regalocio.partners.struts.action.partner;

import es.regalocio.partners.business.common.Partner;
import es.regalocio.partners.business.common.SpecificAgreement;
import es.regalocio.partners.business.services.PartnerService;
import es.regalocio.partners.config.PartnersServices;
import es.regalocio.partners.shared.SessionInfo;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.Globals;
import org.apache.struts.action.*;
import org.apache.struts.validator.DynaValidatorForm;

public class UpdateSpecificAgreementAction extends SpecificAgreementAction {

  @Override
  public void initValues(ActionMapping mapping, DynaValidatorForm form, HttpServletRequest request) {

    PartnerService service = PartnersServices.createPartnerService();
    Partner partner = service.getPartnerForId((Integer) form.get("partner_id"));
    request.setAttribute("partner", partner);
    
    SpecificAgreement sa = service.getSpecificAgreementForId((Integer) form.get("specific_agreement_id"));
    request.setAttribute("thematic_name", sa.getThematicName().toUpperCase());
    
    createSpecificAgreementInfo(form, request);
  }

  @Override
  public void runOnce(ActionMapping mapping, DynaValidatorForm form,
          HttpServletRequest request, HttpServletResponse response) {

    SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy", new Locale("es", "ES"));
    DecimalFormat af = new DecimalFormat("0.00;0.00", new DecimalFormatSymbols(new Locale("es", "ES")));
    PartnerService service = PartnersServices.createPartnerService();
    SpecificAgreement sa = service.getSpecificAgreementForId((Integer) form.get("specific_agreement_id"));

    form.set("thematic_id", sa.getThematicId());
    form.set("end_of_validity", df.format(sa.getEndOfValidity()));
    form.set("commission", af.format(sa.getCommission()));
    form.set("price_service", af.format(sa.getPriceService()));
    form.set("word", sa.getWord());
    form.set("new_agreement", sa.getNewAgreement());
    form.set("agreement_since", sa.getAgreementSince());
    
    request.setAttribute("thematic_name", sa.getThematicName().toUpperCase());
  }

  @Override
  public void doValidate(ActionMapping mapping, ActionMessages messages, 
      DynaValidatorForm form, HttpServletRequest request, HttpServletResponse response) {

    if (!messages.isEmpty() || !validateCommission(messages, form)) {
      return;
    }
    updateSpecificAgreement(form, request);
  }

  @Override
  public ActionForward doExecute(ActionMapping mapping, DynaValidatorForm form,
          HttpServletRequest request, HttpServletResponse response) {

    ActionMessages messages = new ActionMessages();
    messages.add(Globals.MESSAGE_KEY, new ActionMessage("update.specific.agreement.success"));
    saveMessages(request.getSession(), messages);

    StringBuilder path = new StringBuilder(mapping.findForward("success").getPath());
    path.append("?partner_id=");
    path.append(form.get("partner_id"));

    return new RedirectingActionForward(path.toString());
  }

  private void updateSpecificAgreement(DynaValidatorForm form, HttpServletRequest request) {

    SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(SessionInfo.SESSION_INFO);
    PartnerService service = PartnersServices.createPartnerService();

    SpecificAgreement specificAgreement = createSpecificAgreement(form);
    specificAgreement.setId((Integer) form.get("specific_agreement_id"));

    service.updateSpecificAgreement(specificAgreement, sessionInfo.getUser().getId());
  }
}

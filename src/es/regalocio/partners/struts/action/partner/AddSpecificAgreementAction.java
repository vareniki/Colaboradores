package es.regalocio.partners.struts.action.partner;

import es.regalocio.partners.business.common.Partner;
import es.regalocio.partners.business.common.SpecificAgreement;
import es.regalocio.partners.business.common.Thematic;
import es.regalocio.partners.business.common.ThematicRate;
import es.regalocio.partners.business.services.PartnerService;
import es.regalocio.partners.config.PartnersServices;
import es.regalocio.partners.config.PartnersUtils;
import es.regalocio.partners.shared.SessionInfo;

import java.text.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.*;
import org.apache.struts.util.LabelValueBean;
import org.apache.struts.validator.DynaValidatorForm;

public class AddSpecificAgreementAction extends SpecificAgreementAction {

  /**
   *
   * @param form
   * @param partner
   * @return
   */
  private static List<LabelValueBean> createEndOfValidityList(DynaValidatorForm form, Partner partner) {

    List<LabelValueBean> list = new ArrayList<>();
    if (form.get("thematic_id") != null) {
      SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy", new Locale("es", "ES"));
      int thematicId = (Integer) form.get("thematic_id");

      Date endOfValidity;
      try {
        endOfValidity = (!form.getString("end_of_validity").equals("")) ? df.parse(form.getString("end_of_validity")) : null;
      } catch (ParseException ex) {
        endOfValidity = null;
      }

      ThematicRate thematicRate = PartnersUtils.getInstance().getThematic(thematicId, endOfValidity).getThematicRate();
      boolean found = false;
      
      for (SpecificAgreement specificAgreement : partner.getSpecificAgreementList()) {
        if (specificAgreement.getInherited() == 0 && specificAgreement.getThematicId() == thematicId && specificAgreement.getEndOfValidity().equals(thematicRate.getEndOfValidity())) {
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

  /**
   *
   * @param availableThematics
   * @param partner
   * @return
   */
  private static List<String[]> createSpecificAgreementData(List<Thematic> availableThematics, Partner partner) {

    NumberFormat fmt = NumberFormat.getCurrencyInstance(new Locale("es", "ES", "EURO"));

    SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy", new Locale("es", "ES"));
    List<String[]> agreements = new ArrayList<>();

    for (Thematic thematic : availableThematics) {
      ThematicRate tr = thematic.getThematicRate();
      boolean found = false;
      /*
      for (SpecificAgreement sa : partner.getSpecificAgreementList(true)) {
        if (sa.getInherited() == 0 && sa.getThematicId() == thematic.getId() && sa.getEndOfValidity().equals(tr.getEndOfValidity())) {
          found = true;
        }
      }*/
      if (!found) {
        agreements.add(new String[] {
          "" + thematic.getId(), df.format(tr.getEndOfValidity()), fmt.format(tr.getPrice()), fmt.format(tr.getDefaultCommission())});
      }
    }

    return agreements;
  }

  /**
   *
   * @param form
   * @param request
   */
  private void createSpecificAgreement(DynaValidatorForm form, HttpServletRequest request) {
    SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(SessionInfo.SESSION_INFO);
    PartnerService service = PartnersServices.createPartnerService();
    service.addSpecificAgreementToPartner(
            (Integer) form.get("partner_id"), createSpecificAgreement(form), sessionInfo.getUser().getId());
  }

  @Override
  public ActionForward doExecute(ActionMapping mapping, DynaValidatorForm form,
          HttpServletRequest request, HttpServletResponse response) {

    ActionMessages messages = new ActionMessages();
    messages.add(org.apache.struts.Globals.MESSAGE_KEY, new ActionMessage("add.specific.agreement.success"));
    saveMessages(request.getSession(), messages);

    StringBuilder path = new StringBuilder(mapping.findForward("success").getPath());
    path.append("?partner_id=");
    path.append(form.get("partner_id"));

    return new RedirectingActionForward(path.toString());
  }

  @Override
  public void doValidate(ActionMapping mapping, ActionMessages messages, DynaValidatorForm form,
          HttpServletRequest request, HttpServletResponse response) {

    if (!messages.isEmpty() || !validateCommission(messages, form)) {
      return;
    }
    createSpecificAgreement(form, request);
  }

  @Override
  public void initValues(ActionMapping mapping, DynaValidatorForm form, HttpServletRequest request) {

    PartnerService service = PartnersServices.createPartnerService();
    Partner partner = service.getPartnerForId((Integer) form.get("partner_id"));
    request.setAttribute("partner", partner);

    List list = PartnersUtils.getInstance().getThematicsForAgreement(partner);
    request.setAttribute("specific_agreement_data", createSpecificAgreementData(list, partner));

    list.add(0, new LabelValueBean(null, null));
    request.setAttribute("thematic_list", list);

    List list2 = createEndOfValidityList(form, partner);
    if (!list2.isEmpty()) {
      list2.add(0, new LabelValueBean(null, null));
    }
    request.setAttribute("end_of_validity", list2);

    createSpecificAgreementInfo(form, request);
  }
}

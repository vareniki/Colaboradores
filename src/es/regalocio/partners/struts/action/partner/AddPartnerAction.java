package es.regalocio.partners.struts.action.partner;

import es.regalocio.partners.business.common.DefaultTypology;
import es.regalocio.partners.business.common.Partner;
import es.regalocio.partners.business.common.StaticDefinition;
import es.regalocio.partners.business.services.PartnerService;
import es.regalocio.partners.config.PartnersServices;
import es.regalocio.partners.config.PartnersUtils;
import es.regalocio.partners.shared.SessionInfo;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.*;
import org.apache.struts.validator.DynaValidatorForm;

public class AddPartnerAction extends PartnerAction {

  private Partner createPartner(DynaValidatorForm form, HttpServletRequest request) {

    SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(SessionInfo.SESSION_INFO);
    PartnerService service = PartnersServices.createPartnerService();

    Partner partner = createPartner(form);
    partner.getProfiles().add(StaticDefinition.PARTNER_PROFILE_CODE);

    return service.createPartner(partner, sessionInfo.getUser().getId());
  }

  @Override
  public ActionForward doExecute(ActionMapping mapping, DynaValidatorForm form,
          HttpServletRequest request, HttpServletResponse response) {

    ActionMessages msg = new ActionMessages();

    msg.add(org.apache.struts.Globals.MESSAGE_KEY, new ActionMessage("add.partner.success"));
    saveMessages(request.getSession(), msg);

    StringBuilder sb = new StringBuilder(mapping.findForward("success").getPath());
    sb.append("?partner_id=");
    sb.append(form.get("partner_id"));

    return new RedirectingActionForward(sb.toString());
  }

  @Override
  public void doValidate(ActionMapping mapping, ActionMessages messages,
          DynaValidatorForm form, HttpServletRequest request, HttpServletResponse response) {

    if (!messages.isEmpty()) {
      return;
    }
    form.set("partner_id", createPartner(form, request).getId());
  }

  @Override
  public void initValues(ActionMapping mapping, DynaValidatorForm form, HttpServletRequest request) {
    PartnersUtils utils = PartnersUtils.getInstance();
    request.setAttribute("department", utils.getTypologyList("department"));
    request.setAttribute("salutation", utils.getTypologyList("salutation"));
    request.setAttribute("groupal_agreements", utils.getGroupalAgreements());

    // Facturación con o sin IVA.
    List<DefaultTypology> tiposIVA = new ArrayList<>();
    tiposIVA.add(new DefaultTypology(0, "Sin IVA"));
    tiposIVA.add(new DefaultTypology(1, "Con IVA"));

    request.setAttribute("con_sin_iva", tiposIVA);

    List<DefaultTypology> tiposIVA2 = new ArrayList<>();
    tiposIVA2.add(new DefaultTypology(0, "Sin IVA"));
    tiposIVA2.add(new DefaultTypology(201706, "Con IVA desde 06/2017"));
    tiposIVA2.add(new DefaultTypology(201707, "Con IVA desde 07/2017"));
    tiposIVA2.add(new DefaultTypology(201708, "Con IVA desde 08/2017"));
    tiposIVA2.add(new DefaultTypology(201709, "Con IVA desde 09/2017"));
    tiposIVA2.add(new DefaultTypology(201710, "Con IVA desde 10/2017"));

    request.setAttribute("opciones2", tiposIVA2);
  }
}

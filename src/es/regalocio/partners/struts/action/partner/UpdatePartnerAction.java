package es.regalocio.partners.struts.action.partner;

import es.regalocio.partners.business.common.DefaultTypology;
import es.regalocio.partners.business.common.Partner;
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

public class UpdatePartnerAction extends PartnerAction {

  /**
   *
   * @param mapping
   * @param form
   * @param request
   */
  @Override
  public void initValues(ActionMapping mapping, DynaValidatorForm form, HttpServletRequest request) {
    request.setAttribute("salutation", PartnersUtils.getInstance().getTypologyList("salutation"));
    request.setAttribute("groupal_agreements", PartnersUtils.getInstance().getGroupalAgreements());
    
    // Facturación con o sin IVA.
    List<DefaultTypology> tiposIVA = new ArrayList<>();
    tiposIVA.add(new DefaultTypology(0, "Sin IVA"));
    tiposIVA.add(new DefaultTypology(1, "Con IVA"));

    request.setAttribute("con_sin_iva", tiposIVA);

/*
int diffYear = endCalendar.get(Calendar.YEAR) - startCalendar.get(Calendar.YEAR);
int diffMonth = diffYear * 12 + endCalendar.get(Calendar.MONTH) - startCalendar.get(Calendar.MONTH);
*/  
      
    List<DefaultTypology> tiposIVA2 = new ArrayList<>();
    tiposIVA2.add(new DefaultTypology(0, "Sin IVA"));
    tiposIVA2.add(new DefaultTypology(201706, "Con IVA desde 06/2017"));
    tiposIVA2.add(new DefaultTypology(201707, "Con IVA desde 07/2017"));
    tiposIVA2.add(new DefaultTypology(201708, "Con IVA desde 08/2017"));
    tiposIVA2.add(new DefaultTypology(201709, "Con IVA desde 09/2017"));
    tiposIVA2.add(new DefaultTypology(201710, "Con IVA desde 10/2017"));
        
    request.setAttribute("opciones2", tiposIVA2);    
  }

  /**
   *
   * @param mapping
   * @param form
   * @param request
   * @param response
   */
  @Override
  public void runOnce(ActionMapping mapping, DynaValidatorForm form, HttpServletRequest request,
          HttpServletResponse response) {

    PartnerService service = PartnersServices.createPartnerService();
    Partner partner = service.getPartnerForId((Integer) form.get("partner_id"));

    if (partner == null) {
      return;
    }

    form.set("code", partner.getCode());
    form.set("name", partner.getName());
    form.set("phone", partner.getPhone());
    form.set("fax", partner.getFax());
    form.set("email", partner.getEmail());
    form.set("cif", partner.getCif());
    form.set("factname", partner.getFactname());
    form.set("contact_id", partner.getContact().getId());
    form.set("salutation_code", partner.getContact().getSalutationCode());
    form.set("firstname", partner.getContact().getFirstName());
    form.set("lastname", partner.getContact().getLastName());
    form.set("address_id", partner.getAddress().getId());
    form.set("address", partner.getAddress().getAddress());
    form.set("further_address", partner.getAddress().getFurtherAddress());
    form.set("postal_code", partner.getAddress().getPostalCode());
    form.set("town", partner.getAddress().getTown());
    form.set("opciones", partner.getOpciones());
    form.set("opciones2", partner.getOpciones2());
    form.set("groupal_agreement_id", partner.getGroupal_agreement_id());

    if (partner.getRib() != null) {
      form.set("domiciliation", partner.getRib().getDomiciliation());
      form.set("bank_code", partner.getRib().getBankCode());
      form.set("agency_code", partner.getRib().getAgencyCode());
      form.set("account_number", partner.getRib().getAccountNumber());
      form.set("key", partner.getRib().getKey());
      form.set("iban", partner.getRib().getIban());
    }
  }

  /**
   *
   * @param mapping
   * @param messages
   * @param form
   * @param request
   * @param response
   */
  @Override
  public void doValidate(ActionMapping mapping, ActionMessages messages,
          DynaValidatorForm form, HttpServletRequest request, HttpServletResponse response) {

    if (!messages.isEmpty()) {
      return;
    }
    updatePartner(form, request);
  }

  /**
   *
   * @param mapping
   * @param form
   * @param request
   * @param response
   * @return 
   */
  @Override
  public ActionForward doExecute(ActionMapping mapping, DynaValidatorForm form, HttpServletRequest request,
          HttpServletResponse response) {

    ActionMessages messages = new ActionMessages();

    messages.add(org.apache.struts.Globals.MESSAGE_KEY, new ActionMessage("update.partner.success"));
    saveMessages(request.getSession(), messages);

    StringBuilder path = new StringBuilder(mapping.findForward("success").getPath());
    path.append("?partner_id=");
    path.append(form.get("partner_id"));

    return new RedirectingActionForward(path.toString());
  }

  /**
   *
   * @param form
   * @param request
   * @
   */
  private void updatePartner(DynaValidatorForm form, HttpServletRequest request) {

    SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(SessionInfo.SESSION_INFO);
    PartnerService service = PartnersServices.createPartnerService();

    Partner partner = createPartner(form);
    partner.setId((Integer) form.get("partner_id"));
    partner.setCode(form.getString("code"));
    partner.getContact().setId((Integer) form.get("contact_id"));
    partner.getAddress().setId((Integer) form.get("address_id"));

    service.updatePartner(partner, sessionInfo.getUser().getId());
  }
}

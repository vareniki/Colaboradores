package es.regalocio.partners.struts.action.partner;

import es.regalocio.partners.business.common.Partner;
import es.regalocio.partners.config.PartnersServices;
import es.regalocio.partners.config.PartnersUtils;
import es.regalocio.partners.struts.action.SuccessAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.HashMap;

public class ConsultPartnerSheetAction extends SuccessAction {

  @Override
  public ActionForward executeChild(ActionMapping mapping, ActionForm form,
          HttpServletRequest request, HttpServletResponse response) {

    Partner partner = PartnersServices.createPartnerService().getPartnerForId(Integer.parseInt(request.getParameter("partner_id")));

    Boolean readOnly = false;
    try {
      readOnly = Boolean.valueOf(request.getParameter("read_only"));
    } catch (Exception e) {
    }

    HashMap<String, String> URLParameters = new HashMap<>();
    URLParameters.put("partner_id", request.getParameter("partner_id"));

    request.setAttribute("partner", partner);
    request.setAttribute("contact", PartnersUtils.getInstance().createContactAttribute(partner.getContact()));
    request.setAttribute("address", PartnersUtils.getInstance().createAddressAttribute(partner.getAddress()));
    request.setAttribute("readOnly", readOnly);
    request.setAttribute("URLParameters", URLParameters);
    
    request.setAttribute("groupalAgreement", PartnersUtils.getInstance().getGroupalAgreementForId(partner.getGroupal_agreement_id()));

    return super.executeChild(mapping, form, request, response);
  }
}

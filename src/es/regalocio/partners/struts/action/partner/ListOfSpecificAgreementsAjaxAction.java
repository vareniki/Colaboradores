package es.regalocio.partners.struts.action.partner;

import es.regalocio.partners.business.common.Partner;
import es.regalocio.partners.business.common.SpecificAgreementList;
import es.regalocio.partners.business.services.PartnerService;
import es.regalocio.partners.config.PartnersServices;
import es.regalocio.partners.struts.action.AjaxAction;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ListOfSpecificAgreementsAjaxAction extends AjaxAction {

  @Override
  public ActionForward execute(ActionMapping mapping, ActionForm form,
          HttpServletRequest request, HttpServletResponse response) {

    PartnerService service = PartnersServices.createPartnerService();
    Partner partner = service.getPartnerForId(Integer.parseInt(request.getParameter("partner_id")));
    
    SpecificAgreementList agreementList = partner.getSpecificAgreementList(true);
    
    request.setAttribute("list_of_specific_agreements", agreementList);
    request.setAttribute("total_records", agreementList.size());
    
    HashMap<String, String> URLParameters = new HashMap<>();
    URLParameters.put("partner_id", request.getParameter("partner_id"));
    request.setAttribute("URLParameters", URLParameters);

    Boolean readOnly = false;
    try {
      readOnly = Boolean.valueOf(request.getParameter("read_only"));
    } catch (Exception e) {
    }
    request.setAttribute("readOnly", readOnly);

    return mapping.findForward("success");
  }
}

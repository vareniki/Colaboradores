package es.regalocio.partners.struts.action.repayment;

import es.regalocio.partners.business.common.Partner;
import es.regalocio.partners.business.services.PartnerService;
import es.regalocio.partners.config.PartnersServices;
import es.regalocio.partners.config.PartnersUtils;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class PartnerInformationAjaxAction extends Action {

  @Override
  public ActionForward execute(ActionMapping mapping, ActionForm form,
          HttpServletRequest request, HttpServletResponse response) {

    PartnerService partnerService = PartnersServices.createPartnerService();
    Partner partner = partnerService.getPartnerForId(Integer.parseInt(request.getParameter("partner_id")));

    request.setAttribute("partner", partner);
    request.setAttribute("contact", PartnersUtils.getInstance().createContactAttribute(partner.getContact()));
    request.setAttribute("address", PartnersUtils.getInstance().createAddressAttribute(partner.getAddress()));

    return mapping.findForward("success");
  }
}
package es.regalocio.partners.struts.action.partner;

import es.regalocio.partners.business.common.Partner;
import es.regalocio.partners.business.common.shared.PartnersException;
import es.regalocio.partners.business.services.PartnerService;
import es.regalocio.partners.config.PartnersServices;
import es.regalocio.partners.config.PartnersUtils;
import es.regalocio.partners.struts.action.DynaValidatorFormAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import org.apache.struts.validator.DynaValidatorForm;

public class PartnerSheetPopupAction extends DynaValidatorFormAction {

  @Override
  public ActionForward executeChild(ActionMapping mapping, ActionForm form,
          HttpServletRequest request, HttpServletResponse response) {

    PartnerService service = PartnersServices.createPartnerService();
    Partner partner = service.getPartnerForId(Integer.parseInt(request.getParameter("partner_id")));

    Boolean readOnly = false;
    try {
      readOnly = Boolean.valueOf(request.getParameter("read_only"));
    } catch (Exception e) {
    }

    request.setAttribute("partner", partner);
    request.setAttribute("contact", PartnersUtils.getInstance().createContactAttribute(partner.getContact()));
    request.setAttribute("address", PartnersUtils.getInstance().createAddressAttribute(partner.getAddress()));

    request.setAttribute("readOnly", readOnly);

    return mapping.findForward("success");
  }

  @Override
  public ActionForward doExecute(ActionMapping mapping, DynaValidatorForm form,
          HttpServletRequest request, HttpServletResponse response) throws PartnersException {
    return null;
  }
}

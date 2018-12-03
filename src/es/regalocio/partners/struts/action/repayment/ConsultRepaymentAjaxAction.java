package es.regalocio.partners.struts.action.repayment;

import es.regalocio.partners.business.common.Partner;
import es.regalocio.partners.business.common.Repayment;
import es.regalocio.partners.business.services.PartnerService;
import es.regalocio.partners.business.services.RepaymentService;
import es.regalocio.partners.config.PartnersServices;
import es.regalocio.partners.struts.action.SuccessAction;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ConsultRepaymentAjaxAction extends SuccessAction {

  @Override
  public ActionForward executeChild(ActionMapping mapping, ActionForm form,
          HttpServletRequest request, HttpServletResponse response) {

    RepaymentService repaymentService = PartnersServices.createRepaymentService();
    Repayment repayment = repaymentService.getRepaymentForId(Integer.parseInt(request.getParameter("repayment_id")));
    request.setAttribute("repayment", repayment);

    PartnerService partnerService = PartnersServices.createPartnerService();
    Partner partner = partnerService.getPartnerForId(repayment.getPartnerId());
    request.setAttribute("partner", partner);

    return super.executeChild(mapping, form, request, response);
  }
}

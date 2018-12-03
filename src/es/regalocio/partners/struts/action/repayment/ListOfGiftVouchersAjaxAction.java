package es.regalocio.partners.struts.action.repayment;

import es.regalocio.partners.business.services.RepaymentService;
import es.regalocio.partners.config.PartnersServices;
import es.regalocio.partners.struts.action.AjaxAction;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ListOfGiftVouchersAjaxAction extends AjaxAction {

  @Override
  public ActionForward execute(ActionMapping mapping, ActionForm form, 
          HttpServletRequest request, HttpServletResponse response) {

    RepaymentService service = PartnersServices.createRepaymentService();

    List<Map<String, Object>> results = service.getListOfGiftVouchers(Integer.parseInt(request.getParameter("repayment_id")));
    request.setAttribute("total_records", results.size());
    request.setAttribute("list_of_gift_vouchers", results);

    return mapping.findForward("success");
  }
}
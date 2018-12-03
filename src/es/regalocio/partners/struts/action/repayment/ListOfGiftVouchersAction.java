package es.regalocio.partners.struts.action.repayment;

import es.regalocio.partners.business.common.shared.PartnersException;
import es.regalocio.partners.struts.action.DynaValidatorFormAction;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.DynaValidatorForm;

public class ListOfGiftVouchersAction extends DynaValidatorFormAction {

  @Override
  public ActionForward executeChild(ActionMapping mapping, ActionForm baseForm, 
          HttpServletRequest request, HttpServletResponse response) {

    request.setAttribute("repayment_id", request.getParameter("repayment_id"));
    return mapping.findForward("success");
  }

  @Override
  public ActionForward doExecute(ActionMapping mapping, DynaValidatorForm form, 
          HttpServletRequest request, HttpServletResponse response) throws PartnersException {
    return null;
  }
}

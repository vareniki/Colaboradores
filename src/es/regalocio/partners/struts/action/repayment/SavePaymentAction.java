package es.regalocio.partners.struts.action.repayment;

import es.regalocio.partners.struts.action.DynaValidatorFormAction;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.validator.DynaValidatorForm;

public class SavePaymentAction extends DynaValidatorFormAction {

  @Override
  public ActionForward doExecute(ActionMapping mapping, DynaValidatorForm form,
          HttpServletRequest request, HttpServletResponse response) {

    request.setAttribute("payment_reference", form.getString("credit_transfered_reference"));
    return mapping.findForward("success");
  }

  @Override
  public void doValidate(ActionMapping mapping, ActionMessages messages,
          DynaValidatorForm form, HttpServletRequest request, HttpServletResponse response) {
  }
}

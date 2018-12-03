package es.regalocio.partners.struts.action.repayment;

import es.regalocio.partners.business.common.Partner;
import es.regalocio.partners.business.common.RepaymentStatus;
import es.regalocio.partners.business.common.User;
import es.regalocio.partners.shared.SessionInfo;
import es.regalocio.partners.struts.action.DynaValidatorFormAction;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.DynaValidatorForm;

public class ListOfRepaymentsAction extends DynaValidatorFormAction {

  @Override
  public ActionForward doExecute(ActionMapping mapping, DynaValidatorForm form,
          HttpServletRequest request, HttpServletResponse response) {

    SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(SessionInfo.SESSION_INFO);

    List<RepaymentStatus> status = new ArrayList();
    status.add(RepaymentStatus.PENDIENTE_VALIDACION);
    status.add(RepaymentStatus.PENDIENTE_PAGO);
    status.add(RepaymentStatus.PAGO_REALIZADO);

    request.setAttribute("repayment_status_list", status);

    User user = sessionInfo.getUser();
    if (user instanceof Partner) {
      form.set("partner_code", ((Partner) user).getCode());
    }
    return mapping.findForward("success");
  }

  @Override
  public void runOnce(ActionMapping mapping, DynaValidatorForm form,
          HttpServletRequest request, HttpServletResponse response) {

    SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(SessionInfo.SESSION_INFO);

    List<RepaymentStatus> status = new ArrayList();
    status.add(RepaymentStatus.PENDIENTE_VALIDACION);
    status.add(RepaymentStatus.PENDIENTE_PAGO);
    status.add(RepaymentStatus.PAGO_REALIZADO);

    request.setAttribute("repayment_status_list", status);

    User user = sessionInfo.getUser();
    if (user instanceof Partner) {
      form.set("partner_code", ((Partner) user).getCode());
    } else {
      form.set("repayment_status", "1");
    }
  }
}

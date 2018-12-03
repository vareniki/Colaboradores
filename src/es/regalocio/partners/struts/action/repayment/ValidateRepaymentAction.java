package es.regalocio.partners.struts.action.repayment;

import es.regalocio.partners.business.services.RepaymentService;
import es.regalocio.partners.config.PartnersServices;
import es.regalocio.partners.shared.SessionInfo;
import es.regalocio.partners.struts.action.SuccessAction;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.*;

public class ValidateRepaymentAction extends SuccessAction {

  @Override
  public ActionForward executeChild(ActionMapping mapping, ActionForm form,
          HttpServletRequest request, HttpServletResponse response) {

    int repaymentId = Integer.parseInt(request.getParameter("repayment_id"));

    SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(SessionInfo.SESSION_INFO);

    RepaymentService service = PartnersServices.createRepaymentService();
    service.validateRepayment(repaymentId, sessionInfo.getUser().getId());

    ActionMessages messages = new ActionMessages();
    messages.add(org.apache.struts.Globals.MESSAGE_KEY, new ActionMessage("validate.repayment.success"));
    saveMessages(request.getSession(), messages);

    StringBuilder path = new StringBuilder(mapping.findForward("success").getPath());
    path.append("?repayment_id=");
    path.append(repaymentId);

    return new RedirectingActionForward(path.toString());
  }
}
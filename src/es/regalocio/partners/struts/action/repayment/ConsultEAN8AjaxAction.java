package es.regalocio.partners.struts.action.repayment;

import es.regalocio.partners.business.common.GiftVoucherInfo;
import es.regalocio.partners.config.PartnersServices;
import es.regalocio.partners.shared.SessionInfo;
import es.regalocio.partners.struts.action.SuccessAction;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ConsultEAN8AjaxAction extends SuccessAction {

  @Override
  public ActionForward executeChild(ActionMapping mapping, ActionForm form,
          HttpServletRequest request, HttpServletResponse response) {

    String ean8 = request.getParameter("ean8");
    String pin = request.getParameter("pin");

    try {
      GiftVoucherInfo info = new GiftVoucherInfo();

      SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(SessionInfo.SESSION_INFO);
      if (PartnersServices.createGiftVoucherService().checkEAN8AndPin(ean8, pin, info, sessionInfo.getUser().getId())) {

        DateFormat df = new SimpleDateFormat("ddMMyyyy");
        response.getWriter().println(
                "{ result: \"OK\", ean8: \"" + info.getGiftVoucherNumber() + "\""
                + ", word: \"" + String.valueOf(info.getWord()) + "\""
                + ", fecha: \"" + df.format(info.getEndOfValidity()) + "\""
                + ", thematicId: \"" + info.getThematicId() + "\" }");

        /*
        String email = request.getParameter("email");
        if (email != null && !email.isEmpty()) {
          PartnersServices.createLogService().addEMailLog(
                  "partners", "ConsultEAN8Ajax", email, request.getRemoteAddr());
        }*/

      } else {
        response.getWriter().println("{ result: \"KO\" }");
      }

    } catch (IOException ex) {
      Logger.getLogger(ConsultEAN8AjaxAction.class.getName()).log(Level.SEVERE, null, ex);
    }

    return super.executeChild(mapping, form, request, response);
  }
}

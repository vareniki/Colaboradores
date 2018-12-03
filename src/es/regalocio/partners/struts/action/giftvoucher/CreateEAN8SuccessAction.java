package es.regalocio.partners.struts.action.giftvoucher;

import es.regalocio.partners.business.common.shared.PartnersException;
import es.regalocio.partners.struts.action.BaseAction;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class CreateEAN8SuccessAction extends BaseAction {

  @Override
  public ActionForward executeChild(ActionMapping mapping, ActionForm form, HttpServletRequest request,
          HttpServletResponse response) throws PartnersException {

    String ean8Filename = request.getParameter("ean8_filename");
    if (ean8Filename == null) {
      throw new PartnersException("ean8Filename url parameter must be not null.");
    }
    request.setAttribute("ean8_filename", ean8Filename);

    return mapping.findForward("success");
  }
}

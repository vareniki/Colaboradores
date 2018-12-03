package es.regalocio.partners.struts.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class SuccessAction extends BaseAction {

  @Override
  public ActionForward executeChild(ActionMapping mapping, ActionForm form,
          HttpServletRequest request, HttpServletResponse response) {
    return mapping.findForward("success");
  }
  
}

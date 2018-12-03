package es.regalocio.partners.struts.action.partner;

import es.regalocio.partners.business.common.Partner;
import es.regalocio.partners.business.common.shared.PartnersException;
import es.regalocio.partners.config.PartnersUtils;
import es.regalocio.partners.shared.SessionInfo;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.DynaValidatorForm;

public class UpdateOurInformationsAction extends PartnerAction {

  @Override
  public void initValues(ActionMapping mapping, DynaValidatorForm form, HttpServletRequest request) {
    request.setAttribute("salutation", PartnersUtils.getInstance().getTypologyList("salutation"));
  }

  @Override
  public void runOnce(ActionMapping mapping, DynaValidatorForm form, HttpServletRequest request,
          HttpServletResponse response) {

    SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(SessionInfo.SESSION_INFO);
    Partner partner = (Partner) sessionInfo.getUser();

    request.setAttribute("partner", partner);
    request.setAttribute("contact", PartnersUtils.getInstance().createContactAttribute(partner.getContact()));
    request.setAttribute("address", PartnersUtils.getInstance().createAddressAttribute(partner.getAddress()));
  }

  @Override
  public ActionForward doExecute(ActionMapping mapping, DynaValidatorForm form, HttpServletRequest request, HttpServletResponse response) throws PartnersException {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }


}

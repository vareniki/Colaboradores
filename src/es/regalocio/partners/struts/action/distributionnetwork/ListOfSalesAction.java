package es.regalocio.partners.struts.action.distributionnetwork;

import es.regalocio.partners.business.common.RetailOutlet;
import es.regalocio.partners.shared.SessionInfo;
import es.regalocio.partners.struts.action.DynaValidatorFormAction;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.DynaValidatorForm;

public class ListOfSalesAction extends DynaValidatorFormAction {

  @Override
  public void runOnce(ActionMapping mapping, DynaValidatorForm form,
          HttpServletRequest request, HttpServletResponse response) {

    SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(SessionInfo.SESSION_INFO);
    RetailOutlet retailOutlet = (RetailOutlet) sessionInfo.getUser();
    form.set("entity_id", retailOutlet.getEntityId());

    GregorianCalendar calendar = new GregorianCalendar();
    calendar.set(Calendar.DAY_OF_MONTH, 1);

    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", new Locale("es", "ES"));
    form.set("sales_start", formatter.format(calendar.getTime()));
  }

  @Override
  public ActionForward doExecute(ActionMapping mapping, DynaValidatorForm form,
          HttpServletRequest request, HttpServletResponse response) {
    return mapping.findForward("success");
  }
}

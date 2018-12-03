package es.regalocio.partners.struts.action.distributionnetwork;

import es.regalocio.partners.business.common.DistributionNetwork;
import es.regalocio.partners.business.common.DistributorMargin;
import es.regalocio.partners.business.services.EntityOfDistributionService;
import es.regalocio.partners.config.PartnersServices;
import es.regalocio.partners.shared.SessionInfo;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.*;
import org.apache.struts.validator.DynaValidatorForm;

public class UpdateDistributorMarginAction extends DistributorMarginAction {

  @Override
  public void initValues(ActionMapping mapping, DynaValidatorForm form, HttpServletRequest request) {
    EntityOfDistributionService service = PartnersServices.createDistributionService();
    DistributionNetwork distributionNetwork = service.getDistributionNetwork((Integer) form.get("entity_id"));
    request.setAttribute("distribution_network", distributionNetwork);

    createDistributorMarginInfo(form, request);
  }

  @Override
  public void runOnce(ActionMapping mapping, DynaValidatorForm form,
          HttpServletRequest request, HttpServletResponse response) {

    SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy", new Locale("es", "ES"));
    DecimalFormat amountFormatter = new DecimalFormat("0.00;0.00", new DecimalFormatSymbols(new Locale("es", "ES")));
    EntityOfDistributionService service = PartnersServices.createDistributionService();
    DistributorMargin distributorMargin = service.getDistributorMarginForId((Integer) form.get("distributor_margin_id"));

    form.set("thematic_id", distributorMargin.getThematicId());
    form.set("end_of_validity", dateFormatter.format(distributorMargin.getEndOfValidity()));
    form.set("margin", amountFormatter.format(distributorMargin.getMargin()));
  }

  @Override
  public void doValidate(ActionMapping mapping, ActionMessages messages, DynaValidatorForm form,
          HttpServletRequest request, HttpServletResponse response) {
    if (!messages.isEmpty() || !validateMargin(messages, form)) {
      return;
    }

    updateDistributorMargin(form, request);
  }

  @Override
  public ActionForward doExecute(ActionMapping mapping, DynaValidatorForm form, HttpServletRequest request,
          HttpServletResponse response) {

    ActionMessages messages = new ActionMessages();
    messages.add(org.apache.struts.Globals.MESSAGE_KEY, new ActionMessage("update.distributor.margin.success"));
    saveMessages(request.getSession(), messages);

    StringBuilder path = new StringBuilder(mapping.findForward("success").getPath());
    path.append("?entity_id=");
    path.append(form.get("entity_id"));

    return new RedirectingActionForward(path.toString());
  }

  private void updateDistributorMargin(DynaValidatorForm form, HttpServletRequest request) {

    SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(SessionInfo.SESSION_INFO);
    EntityOfDistributionService service = PartnersServices.createDistributionService();

    DistributorMargin distributorMargin = createDistributorMargin(form);
    distributorMargin.setId((Integer) form.get("distributor_margin_id"));

    service.updateDistributorMargin(distributorMargin, sessionInfo.getUser().getId());
  }
}

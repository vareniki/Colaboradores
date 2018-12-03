package es.regalocio.partners.struts.action;

import es.regalocio.partners.business.common.ExtraInvoice;
import es.regalocio.partners.business.common.Partner;
import es.regalocio.partners.config.PartnersServices;
import es.regalocio.partners.config.PartnersUtils;
import es.regalocio.partners.shared.SessionInfo;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class HomeAction extends BaseAction {

  @Override
  public ActionForward executeChild(ActionMapping mapping, ActionForm form,
          HttpServletRequest request, HttpServletResponse response) {

    ActionForward result;

    SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(SessionInfo.SESSION_INFO);
    switch (PartnersUtils.getInstance().getUserType(sessionInfo.getUser())) {
      case 1:
        result = mapping.findForward("partner_home");
        
        // Comprueba si hay alguna factura extra
        Partner partner = (Partner) sessionInfo.getUser();
        
        List<ExtraInvoice> invoices = new ArrayList<>();
        
        ExtraInvoice invoice = PartnersServices.createRepaymentService().getExtraInvoice(partner.getId());
        if (invoice != null) {
          request.setAttribute("extraInvoice1", invoice);
          invoices.add(invoice);
        }
        
        invoice = PartnersServices.createRepaymentService().getExtraInvoiceAbril(partner.getId());
        if (invoice != null) {
          request.setAttribute("extraInvoice2", invoice);
          invoices.add(invoice);
        }
        
        invoice = PartnersServices.createRepaymentService().getExtraInvoiceMayo(partner.getId());
        if (invoice != null) {
          request.setAttribute("extraInvoice3", invoice);
          invoices.add(invoice);
        }
        
        invoice = PartnersServices.createRepaymentService().getExtraInvoiceJunio(partner.getId());
        if (invoice != null) {
          request.setAttribute("extraInvoice4", invoice);
          invoices.add(invoice);
        }
        
        invoice = PartnersServices.createRepaymentService().getExtraInvoiceJulio(partner.getId());
        if (invoice != null) {
          request.setAttribute("extraInvoice5", invoice);
          invoices.add(invoice);
        }
        
        invoice = PartnersServices.createRepaymentService().getExtraInvoiceAgosto(partner.getId());
        if (invoice != null) {
          request.setAttribute("extraInvoice6", invoice);
          invoices.add(invoice);
        }
        
        invoice = PartnersServices.createRepaymentService().getExtraInvoiceSeptiembre(partner.getId());
        if (invoice != null) {
          request.setAttribute("extraInvoice7", invoice);
          invoices.add(invoice);
        }
        
        if (!invoices.isEmpty()) {
          request.setAttribute("extraInvoices", invoices);
        }
        
        break;
      case 2:
        result = mapping.findForward("retail_outlet_home");
        break;
      default:
        result = mapping.findForward("internal_user_home");
        break;
    }
    return result;
  }
}

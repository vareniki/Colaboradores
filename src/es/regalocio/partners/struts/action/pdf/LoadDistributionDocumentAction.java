package es.regalocio.partners.struts.action.pdf;

import com.documents.OrdersDocumentGenerator;
import com.documents.beans.Document;
import es.regalocio.partners.business.common.Invoice;
import es.regalocio.partners.business.common.RetailOutlet;
import es.regalocio.partners.business.common.RetailOutletOrder;
import es.regalocio.partners.business.common.RetailOutletOrderAction;
import es.regalocio.partners.business.common.shared.PartnersException;
import es.regalocio.partners.business.services.EntityOfDistributionService;
import es.regalocio.partners.business.services.InvoiceService;
import es.regalocio.partners.business.services.RetailOutletOrderService;
import es.regalocio.partners.config.PartnersServices;
import es.regalocio.partners.config.PartnersUtils;
import es.regalocio.partners.documents.RetailOutletInvoiceDocument;
import es.regalocio.partners.documents.RetailOutletOrderDocument;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.DynaValidatorForm;

public class LoadDistributionDocumentAction extends Action {

  private static final Log logger = LogFactory.getLog(LoadDistributionDocumentAction.class);

  @Override
  public ActionForward execute(
          ActionMapping mapping, ActionForm baseForm, HttpServletRequest request, HttpServletResponse response) {

    List<Document> documents;
    DynaValidatorForm form = (DynaValidatorForm) baseForm;
    try {
      Integer orderId = (Integer) form.get("order_id");
      if (orderId != null) {
        documents = getOrderDocument(orderId);
      } else {
        documents = getInvoice(form);
      }
      if (documents != null) {
        OrdersDocumentGenerator generator = new OrdersDocumentGenerator(PartnersUtils.getInstance().getProperty("documents.base_dir"));

        byte[] content = generator.renderFO(documents);

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "inline;filename=distribution_document.pdf");
        response.setContentLength(content.length);
        response.getOutputStream().write(content);
        response.getOutputStream().flush();
      }
    } catch (Throwable t) {
      logger.error("Unable to get distribution document for parameters (entity_id, invoice_id, order_id) : ("
              + form.get("entity_id") + ", " + form.get("invoice_id") + ", " + form.get("order_id") + ").", t);
    }

    return null;
  }

  /**
   *
   * @param form
   * @return
   * @throws Throwable
   */
  private List<Document> getInvoice(DynaValidatorForm form) throws Throwable {
    return getInvoiceById((Integer) form.get("invoice_id"));
  }

  /**
   *
   * @param invoiceId
   * @return
   * @throws Throwable
   */
  private List<Document> getInvoiceById(int invoiceId) throws Throwable {

    InvoiceService invoiceService = PartnersServices.createInvoiceService();
    Invoice invoice = invoiceService.getInvoiceById(invoiceId);

    List<Document> documents = new ArrayList<>();
    EntityOfDistributionService entityOfDistributionService = PartnersServices.createDistributionService();
    RetailOutlet retailOutlet = entityOfDistributionService.getRetailOutlet(invoice.getEntityId());

    documents.add(new RetailOutletInvoiceDocument(invoice, retailOutlet));

    return documents;
  }

  /**
   *
   * @param orderId
   * @return
   * @throws Exception
   */
  private List<Document> getOrderDocument(int orderId) throws Exception {

    RetailOutletOrderService orderSrv = PartnersServices.createRetailOutletOrderService();
    RetailOutletOrder order = orderSrv.getOrder(orderId);
    Date dateOfTransmission = orderSrv.getDateOfAction(orderId, RetailOutletOrderAction.TRANSMISSION);

    int typeOfDocument = RetailOutletOrderDocument.INVOICE;
    if (orderSrv.getGiftVoucherActivationOnSale(orderId)) {
      typeOfDocument = RetailOutletOrderDocument.DELIVERY_SLIP;
    }
    RetailOutlet retailOutlet = PartnersServices.createDistributionService().getRetailOutlet(order.getEntityId());

    List<Document> documents = new ArrayList<>();
    documents.add(new RetailOutletOrderDocument(order, retailOutlet, dateOfTransmission, typeOfDocument, false));

    return documents;
  }
}

package es.regalocio.partners.struts.action.pdf;

import com.sendmail.shared.PDFGenerationException;
import es.regalocio.partners.business.common.Repayment;
import es.regalocio.partners.business.common.shared.PartnersException;
import es.regalocio.partners.config.PartnersServices;
import es.regalocio.partners.config.PartnersUtils;
import es.regalocio.partners.documents.PartnersDocument;
import es.regalocio.partners.documents.RepaymentDocumentGenerator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class LoadDetailedCreditNoteAction extends Action {

  private static final Log logger = LogFactory.getLog(LoadDetailedCreditNoteAction.class);

  private static byte[] loadDocument(int repaymentId, int documentType, boolean isDraft) throws PartnersException {
    String baseDir = PartnersUtils.getInstance().getProperty("documents.base_dir");
    try {
      RepaymentDocumentGenerator generator = new RepaymentDocumentGenerator(baseDir);
      return generator.renderFO(new PartnersDocument(repaymentId, documentType, isDraft));
    } catch (PDFGenerationException ex) {
      throw new PartnersException(ex.getMessage(), ex);
    }
  }

  @Override
  public ActionForward execute(ActionMapping mapping, ActionForm baseForm,
          HttpServletRequest request, HttpServletResponse response) {

    int repaymentId;
    try {
      repaymentId = Integer.parseInt(request.getParameter("repayment_id"));
    } catch (NumberFormatException e) {
      logger.error("Invalid or not found repayment_id in request.");
      return null;
    }

    try {
      byte[] content = loadDetailedCreditNote(repaymentId);

      response.setContentType("application/pdf");
      response.setHeader("Content-Disposition", "inline;filename=repayment_" + repaymentId + ".pdf");
      response.setContentLength(content.length);
      response.getOutputStream().write(content);
      response.getOutputStream().flush();
    } catch (Throwable t) {
      logger.error("Unable to get detailed credit note for repayment n. " + repaymentId + ".", t);
    }

    return null;
  }

  private byte[] loadDetailedCreditNote(int repaymentId) throws Throwable {

    Repayment repayment = PartnersServices.createRepaymentService().getRepaymentForId(repaymentId);

    int tipoInforme = (PartnersUtils.getTipoIVA(repayment) == 21)
            ? PartnersDocument.NEW_INVOICE : PartnersDocument.DETAILED_CREDIT_NOTE;

    return loadDocument(repaymentId, tipoInforme, true);
  }
}

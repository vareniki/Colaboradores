package es.regalocio.partners.struts.action.giftvoucher;

import es.regalocio.partners.business.common.GiftVoucher;
import es.regalocio.partners.business.common.shared.GiftVoucherNotFoundException;
import es.regalocio.partners.business.common.shared.PartnersException;
import es.regalocio.partners.business.services.GiftVoucherService;
import es.regalocio.partners.config.PartnersServices;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

public class GetGiftVoucherStatusAction extends Action {

  private static final Log logger = LogFactory.getLog(GetGiftVoucherStatusAction.class);

  private static Element getResultat(int statut) {
    Element result = new Element("RESULTAT");

    Element status = new Element("STATUT");
    status.setText(Integer.toString(statut));
    result.addContent(status);

    return result;
  }

  private static int getStatutForEAN8(String ean8) {

    try {
      GiftVoucherService service = PartnersServices.createGiftVoucherService();
      GiftVoucher giftVoucher = service.getGiftVoucher(ean8);

      return giftVoucher.getStatus().getCode();
    } catch (GiftVoucherNotFoundException e) {
      return 0;
    } catch (Throwable t) {
      logger.error("Unable to get statut for ean8 " + ean8 + ".", t);
      return -1;
    }
  }

  @Override
  public ActionForward execute(ActionMapping mapping, ActionForm baseForm, HttpServletRequest request,
          HttpServletResponse response) throws PartnersException {

    try {
      String ean8 = request.getParameter("ean8");

      if (ean8 == null) {
        throw new PartnersException("ean8 must be not null.");
      }
      Document document = new Document(getResultat(getStatutForEAN8(ean8)));
      Format format = Format.getPrettyFormat();
      format.setEncoding("ISO-8859-1");
      XMLOutputter outputter = new XMLOutputter(format);

      response.setContentType("text/xml; charset=iso-8859-1");
      response.addHeader("Pragma", "no-cache");
      response.addHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0, public");
      response.addHeader("Expires", "0");

      outputter.output(document, response.getOutputStream());
      response.getOutputStream().flush();
    } catch (PartnersException | IOException e) {
      logger.error("Unable get gift voucher status.", e);
      throw new PartnersException(e.getMessage(), e);
    }

    return null;
  }
}

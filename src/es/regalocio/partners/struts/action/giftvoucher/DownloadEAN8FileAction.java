package es.regalocio.partners.struts.action.giftvoucher;

import es.regalocio.partners.business.common.shared.PartnersException;
import es.regalocio.partners.config.PartnersUtils;
import java.io.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class DownloadEAN8FileAction extends Action {

  private static final Log logger = LogFactory.getLog(DownloadEAN8FileAction.class);

  private static void pipe(InputStream inputStream, OutputStream outputStream) throws IOException {
    int byteRead;

    byte[] buffer = new byte[1024];
    while ((byteRead = inputStream.read(buffer)) != -1) {
      outputStream.write(buffer, 0, byteRead);
      outputStream.flush();
    }
  }

  @Override
  public ActionForward execute(ActionMapping mapping, ActionForm baseForm, HttpServletRequest request,
          HttpServletResponse response) throws PartnersException {

    String ean8Filename = request.getParameter("ean8_filename");
    if (ean8Filename == null) {
      throw new PartnersException("ean8Filename url parameter must be not null.");
    }
    FileInputStream inputStream = null;
    try {
      File archiveDirectory = new File(PartnersUtils.getInstance().getProperty("archives.ean8.directory"));
      File ean8File = new File(archiveDirectory, ean8Filename);
      inputStream = new FileInputStream(ean8File);
      response.addHeader("Content-disposition", "attachment; filename=" + ean8Filename);
      response.setContentType("text/x-csv");
      response.setContentLength((int) ean8File.length());
      response.addHeader("Pragma", "no-cache");
      response.addHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0, public");
      response.addHeader("Expires", "0");

      pipe(inputStream, response.getOutputStream());
    } catch (Exception e) {
      logger.error("Unable to execute download ean8 file.", e);
      throw new PartnersException(e.getMessage(), e);
    } finally {
      try {
        if (inputStream != null) {
          inputStream.close();
        }
      } catch (IOException e) {
        logger.warn("Unable to close input stream.");
      }
    }

    return null;
  }
}

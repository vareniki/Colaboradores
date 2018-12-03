package es.regalocio.partners.struts.action.giftvoucher;

import es.regalocio.partners.business.common.GiftVoucher;
import es.regalocio.partners.business.common.InternalUser;
import es.regalocio.partners.business.common.Thematic;
import es.regalocio.partners.business.common.ThematicRate;
import es.regalocio.partners.business.common.shared.EAN8AlreadyUsedException;
import es.regalocio.partners.business.common.shared.PartnersException;
import es.regalocio.partners.business.services.GiftVoucherService;
import es.regalocio.partners.config.PartnersServices;
import es.regalocio.partners.config.PartnersUtils;
import es.regalocio.partners.shared.SessionInfo;
import es.regalocio.partners.struts.action.DynaValidatorFormAction;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.RedirectingActionForward;
import org.apache.struts.validator.DynaValidatorForm;

public class CreateEAN8Action extends DynaValidatorFormAction {

  private static final String FILE_HEADER_1 = "EAN8;CLE\n";
  private static final String FILE_HEADER_M = "EAN8|EAN8;WORD;CLE|EAN8;WORD;CLE|...\n";

  private static String createChequeNumber(int value) {
    StringBuilder chequeNumber = new StringBuilder(Integer.toString(value));
    int length = chequeNumber.length();
    while (length < 7) {
      chequeNumber.insert(0, '0');
      length++;
    }

    int sum = 0;
    for (int counter = 0; counter < length; counter++) {
      if ((counter % 2) == 1) {
        sum += Integer.parseInt(Character.toString(chequeNumber.charAt(counter)));
      } else {
        sum += 3 * Integer.parseInt(Character.toString(chequeNumber.charAt(counter)));
      }
    }

    int key = sum % 10;
    if (key != 0) {
      key = 10 - key;
    }
    chequeNumber.append(Integer.toString(key));

    return chequeNumber.toString();
  }

  private static String renameFile(File tempFile, File archiveDirectory, Thematic thematic) throws PartnersException {

    SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMddHHmmss");
    StringBuilder buffer = new StringBuilder(
            "" + thematic.getId() + "_" + thematic.getName().trim().replaceAll("[^a-zA-Z0-9]", "") + "_" + thematic.getThematicRate().getEan13());

    buffer.append('-');
    buffer.append(fmt.format(new Date()));
    buffer.append(".csv");
    String ean8Filename = buffer.toString();
    if (tempFile.renameTo(new File(archiveDirectory, ean8Filename))) {
      return ean8Filename;
    }

    throw new PartnersException("Unable to rename temporary ean8 file.");
  }

  private static String createAllEAN8(int amount, Thematic thematic, int userId) throws PartnersException {

    try {
      File archiveDirectory = new File(PartnersUtils.getInstance().getProperty("archives.ean8.directory"));
      File tempFile = File.createTempFile("ean8", null, archiveDirectory);

      writeAllEAN8(tempFile, amount, thematic, userId);
      String ean8Filename = renameFile(tempFile, archiveDirectory, thematic);
      return ean8Filename;
    } catch (IOException e) {
      throw new PartnersException(e.getMessage(), e);
    }

  }

  @Override
  public ActionForward doExecute(ActionMapping mapping, DynaValidatorForm form,
          HttpServletRequest request, HttpServletResponse response) {

    StringBuilder path = new StringBuilder(mapping.findForward("success").getPath());
    path.append("?ean8_filename=");
    path.append((String) request.getAttribute("ean8_filename"));

    return new RedirectingActionForward(path.toString());
  }

  @Override
  public void doValidate(ActionMapping mapping, ActionMessages messages,
          DynaValidatorForm form, HttpServletRequest request, HttpServletResponse response) throws PartnersException {

    try {
      if (!messages.isEmpty()) {
        return;
      }
      PartnersUtils partnersUtils = PartnersUtils.getInstance();

      SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy", new Locale("es", "ES"));
      Date endOfValidity = (!form.getString("end_of_validity").equals("")) ? df.parse(form.getString("end_of_validity")) : null;

      Thematic thematic = partnersUtils.getThematic((Integer) form.get("thematic_id"), endOfValidity);

      int amount = (Integer) form.get("amount");
      SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(SessionInfo.SESSION_INFO);
      int userId = sessionInfo.getUser().getId();

      String ean8Filename = createAllEAN8(amount, thematic, userId);
      request.setAttribute("ean8_filename", ean8Filename);

    } catch (ParseException ex) {
      Logger.getLogger(CreateEAN8Action.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  @Override
  public void initValues(ActionMapping mapping, DynaValidatorForm form, HttpServletRequest request) throws PartnersException {

    SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(SessionInfo.SESSION_INFO);
    if (!(sessionInfo.getUser() instanceof InternalUser)) {
      throw new PartnersException("User must be an internal user to accomplish this operation.");
    }

    Integer thematicId = ((Integer) form.get("thematic_id"));

    List<Thematic> thematics = new ArrayList<>();
    for (Thematic thematic : PartnersUtils.getInstance().getThematics()) {
      ThematicRate rate = thematic.getThematicRate();
      if (rate.getEan13() == null || !rate.isCreationEnabled()) {
        continue;
      }
      thematics.add(thematic);

      if (thematicId != null && thematic.getId() == thematicId) {
        List<ThematicRate> rates = new ArrayList<>();
        rates.add(rate);
        request.setAttribute("end_of_validity", rates);
      }
    }

    request.setAttribute("thematic_list", thematics);
  }

  private static void writeAllEAN8(File tempFile, int amount, Thematic thematic, int userId) throws PartnersException {

    try (FileOutputStream outputStream = new FileOutputStream(tempFile, false)) {
      if (!thematic.isMultiple()) {
        outputStream.write(FILE_HEADER_1.getBytes());
      } else {
        outputStream.write(FILE_HEADER_M.getBytes());
      }
      DecimalFormat fmt = new DecimalFormat("000");
      Random random = new Random(new Date().getTime());
      GiftVoucherService service = PartnersServices.createGiftVoucherService();

      int counter = 0;
      while (counter < amount) {
        String chequeNumber = createChequeNumber((int) (Math.abs(random.nextLong() % 10000000)));
        List<String> pins = new ArrayList<>();
        for (int i = 0; i < thematic.getNumberOfGiftVouchers(); i++) {
          pins.add(fmt.format(Math.abs(random.nextInt() % 1000)));
        }
        ThematicRate thematicRate = thematic.getThematicRate();
        try {
          service.createGiftVoucher(new GiftVoucher(thematic.getId(), thematicRate.getEndOfValidity(), chequeNumber, 0, null), pins, userId);
          if (!thematic.isMultiple()) {
            outputStream.write(MessageFormat.format("{0};{1}\n", new Object[]{chequeNumber, pins.iterator().next()}).getBytes());
          } else {
            outputStream.write((chequeNumber + "|").getBytes());
            char word = 'A';
            for (String pin : pins) {

              if (word != 'A') {
                outputStream.write('|');
              }
              outputStream.write(MessageFormat.format("{0};{1};{2}", new Object[]{chequeNumber, word, pin}).getBytes());

              word++;
            }
            outputStream.write('\n');
          }
          counter++;
        } catch (EAN8AlreadyUsedException e) {
        }

      }
    } catch (IOException e) {
      throw new PartnersException(e.getMessage(), e);
    }
  }
}

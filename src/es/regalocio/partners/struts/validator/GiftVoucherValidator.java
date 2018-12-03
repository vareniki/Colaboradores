package es.regalocio.partners.struts.validator;

import es.regalocio.partners.business.common.GiftVoucher;
import es.regalocio.partners.business.common.Thematic;
import es.regalocio.partners.config.PartnersUtils;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.validator.Field;
import org.apache.commons.validator.GenericValidator;
import org.apache.commons.validator.Validator;
import org.apache.commons.validator.ValidatorAction;
import org.apache.commons.validator.util.ValidatorUtils;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.validator.Resources;

public class GiftVoucherValidator extends CommonsValidator {

  private static long generateKey(long sequence, long thematicCode, long month, long year) {
    return 97L - (((81 * sequence) + (27 * thematicCode) + (9 * month) + (3 * year)) % 97);
  }

  private static boolean isEndOfValidityInfoValid(Thematic thematic, String endOfValidityInfo) {

    SimpleDateFormat formatter = new SimpleDateFormat("yyMM", new Locale("es", "ES"));

    Date endOfValidityOfRate = thematic.getThematicRate().getEndOfValidity();
    return formatter.format(endOfValidityOfRate).equals(endOfValidityInfo);

  }

  public static boolean validatePin(Object bean, ValidatorAction validatorAction,
          Field field, ActionMessages messages, Validator validator, HttpServletRequest request) {

    return true;
    /*
     String pin = ValidatorUtils.getValueAsString(bean, field.getProperty());
     if (validateFixedLengthPartNumericalField(pin, 2, 1)) {
     return true;
     }
     messages.add(field.getKey(), Resources.getActionMessage(validator, request, validatorAction, field));
     return false;
     */
  }

  private static boolean validateSecureGiftVoucherNumber(String giftVoucherNumber) {
    return validateFixedLengthNumericalField(giftVoucherNumber, 8);
  }

  private static boolean validateUnsecuredGiftVoucherNumber(String giftVoucherNumber) {

    try {
      if (!validateFixedLengthNumericalField(giftVoucherNumber, 13)) {
        return false;
      }

      long year = Long.parseLong(giftVoucherNumber.substring(0, 2));
      long month = Long.parseLong(giftVoucherNumber.substring(2, 4));
      Thematic thematic = PartnersUtils.getInstance().getThematic(Integer.parseInt(giftVoucherNumber.substring(4, 6)));
      long sequence = Long.parseLong(giftVoucherNumber.substring(6, 11));
      long key = Long.parseLong(giftVoucherNumber.substring(11, 13));

      if (!isEndOfValidityInfoValid(thematic, giftVoucherNumber.substring(0, 4))) {
        return false;
      }
      if (key != generateKey(sequence, thematic.getCode(), month, year)) {
        return false;
      }
    } catch (Throwable t) {
      return false;
    }
    return true;
  }

  public static boolean validateGiftVoucherNumber(Object bean, ValidatorAction validatorAction,
          Field field, ActionMessages messages, Validator validator, HttpServletRequest request) {

    String ean8Word = ValidatorUtils.getValueAsString(bean, field.getProperty());

    String ean8 = GiftVoucher.filterEAN8Word(ean8Word);
    char word = GiftVoucher.getWordForEAN8(ean8Word);

    if ((GenericValidator.isBlankOrNull(ean8) || validateSecureGiftVoucherNumber(ean8) || validateUnsecuredGiftVoucherNumber(ean8))
            && ((word == ' ') || (word >= 'A' && word <= 'Z'))) {

      return true;
    }
    messages.add(field.getKey(), Resources.getActionMessage(validator, request, validatorAction, field));

    return false;
  }

  public static boolean validateSecureGiftVoucherNumber(Object bean, ValidatorAction validatorAction,
          Field field, ActionMessages messages, Validator validator, HttpServletRequest request) {

    String ean8Word = ValidatorUtils.getValueAsString(bean, field.getProperty());

    String ean8 = GiftVoucher.filterEAN8Word(ean8Word);
    char word = GiftVoucher.getWordForEAN8(ean8Word);

    if (validateSecureGiftVoucherNumber(ean8) && ((word == ' ') || (word >= 'A' && word <= 'Z'))) {
      return true;
    }
    messages.add(field.getKey(), Resources.getActionMessage(validator, request, validatorAction, field));

    return false;
  }

  public static boolean validateUnsecuredGiftVoucherNumber(Object bean, ValidatorAction validatorAction,
          Field field, ActionMessages messages, Validator validator, HttpServletRequest request) {

    String giftVoucherNumber = ValidatorUtils.getValueAsString(bean, field.getProperty());
    if (GenericValidator.isBlankOrNull(giftVoucherNumber) || validateUnsecuredGiftVoucherNumber(giftVoucherNumber)) {
      return true;
    }
    messages.add(field.getKey(), Resources.getActionMessage(validator, request, validatorAction, field));
    return false;
  }
}

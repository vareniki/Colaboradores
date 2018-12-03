package es.regalocio.partners.struts.validator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.validator.Field;
import org.apache.commons.validator.GenericValidator;
import org.apache.commons.validator.Validator;
import org.apache.commons.validator.ValidatorAction;
import org.apache.commons.validator.util.ValidatorUtils;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.validator.Resources;

public class MiscellaneousValidator extends CommonsValidator {

  private static final int ES_PHONE_LENGTH = 14;

  public static boolean validatePhone(Object bean, ValidatorAction validatorAction, Field field,
          ActionMessages messages, Validator validator, HttpServletRequest request) {

    char aCharacter;
    int counter;
    int nbDigit;
    String value = ValidatorUtils.getValueAsString(bean, field.getProperty());

    if (GenericValidator.isBlankOrNull(value)) {
      return true;
    }

    nbDigit = 0;
    for (counter = 0; counter < value.length(); counter++) {
      aCharacter = value.charAt(counter);
      if (Character.isDigit(aCharacter)) {
        nbDigit++;
      } else if (aCharacter != ' ') {
        break;
      }
    }

    if ((counter <= value.length()) && (nbDigit <= ES_PHONE_LENGTH)) {
      return true;
    }

    //messages.add(field.getKey(), Resources.getActionMessage(request, validatorAction, field));
    messages.add(field.getKey(), Resources.getActionMessage(validator, request, validatorAction, field));
    return false;
  }

  public static boolean validatePostalCode(Object bean, ValidatorAction validatorAction, Field field,
          ActionMessages messages, Validator validator, HttpServletRequest request) {

    int counter;
    int departmentCode;
    String value;

    value = ValidatorUtils.getValueAsString(bean, field.getProperty());

    if (GenericValidator.isBlankOrNull(value)) {
      return true;
    }

    if (value.length() == 5) {
      for (counter = 0; counter < 5; counter++) {
        if (!Character.isDigit(value.charAt(counter))) {
          break;
        }
      }

      if (counter == 5) {
        try {
          departmentCode = Integer.parseInt(value.substring(0, 2));
          if ((departmentCode > 0) && (departmentCode < 96)) {
            return true;
          }
        } catch (NumberFormatException e) {
        }
      }
    }

    //messages.add(field.getKey(), Resources.getActionMessage(request, validatorAction, field));
    messages.add(field.getKey(), Resources.getActionMessage(validator, request, validatorAction, field));
    return false;
  }

  public static boolean validateInvoiceNumber(Object bean, ValidatorAction validatorAction, Field field,
          ActionMessages messages, Validator validator, HttpServletRequest request) {

    SimpleDateFormat parser;
    String invoiceNumber = ValidatorUtils.getValueAsString(bean, field.getProperty());

    if (GenericValidator.isBlankOrNull(invoiceNumber)) {
      return true;
    }

    if (validateFixedLengthNumericalField(invoiceNumber, 9)) {
      try {
        parser = new SimpleDateFormat("yyMM");
        parser.setLenient(false);
        parser.parse(invoiceNumber.substring(0, 4));

        return true;
      } catch (ParseException e) {
      }
    }

    //messages.add(field.getKey(), Resources.getActionMessage(request, validatorAction, field));
    messages.add(field.getKey(), Resources.getActionMessage(validator, request, validatorAction, field));
    return false;
  }

  public static boolean validatePartnerCode(Object bean, ValidatorAction validatorAction, Field field,
          ActionMessages messages, Validator validator, HttpServletRequest request) {

    String value = ValidatorUtils.getValueAsString(bean, field.getProperty());

    if (GenericValidator.isBlankOrNull(value)) {
      return true;
    }

    if (value.length() == 9 && value.charAt(2) == ' ' && value.charAt(5) == ' ') {
      value = value.substring(0, 2) + value.substring(3, 5) + value.substring(6, 9);
    }

    if (value.length() == 7) {
      String yy = new SimpleDateFormat("yy").format(GregorianCalendar.getInstance().getTime());
      if (value.substring(2, 4).equals(yy)) {
        return true;
      }
    }

    //messages.add(field.getKey(), Resources.getActionMessage(request, validatorAction, field));
    messages.add(field.getKey(), Resources.getActionMessage(validator, request, validatorAction, field));

    return false;
  }

  public static boolean validateChequeNumber(
          Object bean, ValidatorAction validatorAction, Field field,
          ActionMessages messages, Validator validator, HttpServletRequest request) {

    String chequeNumber = ValidatorUtils.getValueAsString(bean, field.getProperty());

    if (validateFixedLengthNumericalField(chequeNumber, 7)) {
      return true;
    }
    //messages.add(field.getKey(), Resources.getActionMessage(request, validatorAction, field));
    messages.add(field.getKey(), Resources.getActionMessage(validator, request, validatorAction, field));

    return false;
  }
}

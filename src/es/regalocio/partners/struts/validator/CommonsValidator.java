package es.regalocio.partners.struts.validator;

import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.validator.Field;
import org.apache.commons.validator.GenericValidator;
import org.apache.commons.validator.Validator;
import org.apache.commons.validator.ValidatorAction;
import org.apache.commons.validator.util.ValidatorUtils;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.validator.Resources;

public class CommonsValidator extends Object {
  protected static boolean validateFixedLengthAlphanumericField(String field, int length) {

    if (GenericValidator.isBlankOrNull(field)) {
      return true;
    }

    if (field.length() == length) {
      int counter;
      for (counter = 0; counter < length; counter++) {
        char character = field.charAt(counter);
        if (!Character.isDigit(character) && (character < 'A' || character > 'Z') && (character < 'a' || character > 'z')) {
          break;
        }
      }

      if (counter == length) {
        return true;
      }
    }

    return false;
  }

  protected static boolean validateFixedLengthNumericalField(String field, int length) {
    
    if (GenericValidator.isBlankOrNull(field)) {
      return true;
    }
    
    int counter;
    if (field.length() == length) {
      for (counter = 0; counter < length; counter++) {
        if (!Character.isDigit(field.charAt(counter))) {
          break;
        }
      }

      if (counter == length) {
        return true;
      }
    }

    return false;
  }

  protected static boolean validateFixedLengthPartNumericalField(String field, int lengthNum, int lengthAlpha) {
      
      if (GenericValidator.isBlankOrNull(field)) {
        return true;
      }
      
      int counter;
      if (field.length() == (lengthNum + lengthAlpha)) {
        for (counter = 0; counter < lengthNum; counter++) {
          if (!Character.isDigit(field.charAt(counter))) {
            break;
          }
        }
        if (counter == lengthNum) {
          return true;
        }
      }
  
      return false;
    }

  public static boolean validateDateSlot(Object bean, ValidatorAction validatorAction,
      Field field, ActionMessages messages, Validator validator, HttpServletRequest request) {
  
    String firstValue = ValidatorUtils.getValueAsString(bean, field.getProperty());
    String secondValue = ValidatorUtils.getValueAsString(bean, field.getVarValue("secondProperty"));
    String format = field.getVarValue("format");
  
    if (GenericValidator.isBlankOrNull(firstValue) || GenericValidator.isBlankOrNull(secondValue)) {
      return true;
    }
    
    try {
      SimpleDateFormat formatter = new SimpleDateFormat(format);
      Date firstDate = formatter.parse(firstValue);
      Date secondDate = formatter.parse(secondValue);
  
      if (secondDate.after(firstDate) || secondDate.equals(firstDate)) {
        return true;
      }
    } catch (Exception e) {
    }
  
    messages.add(field.getKey(), Resources.getActionMessage(validator, request, validatorAction, field));
  
    return false;
  }  
  
  public static boolean validateTwoFields(Object bean, ValidatorAction validatorAction,
      Field field, ActionMessages messages, Validator validator, HttpServletRequest request) {

    String firstValue = ValidatorUtils.getValueAsString(bean, field.getProperty());
    if (GenericValidator.isBlankOrNull(firstValue)) {
      return true;
    }
    
    String secondValue = ValidatorUtils.getValueAsString(bean, field.getVarValue("secondProperty"));
    if (firstValue.equals(secondValue)) {
      return true;
    }
    messages.add(field.getKey(), Resources.getActionMessage(validator, request, validatorAction, field));
    //messages.add(field.getKey(), Resources.getActionMessage(request, validatorAction, field));

    return false;
  }
}

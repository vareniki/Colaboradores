package es.regalocio.partners.struts.action;

import es.regalocio.partners.business.common.shared.PartnersException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.*;
import org.apache.struts.validator.DynaValidatorForm;

public abstract class DynaValidatorFormAction extends BaseAction {

  private static void rewriteMessages(ActionMessages messages) {

    List<String> allValues = new ArrayList<>();
    ActionMessages messagesUnrewrited = new ActionMessages();
    Iterator<String> props = messages.properties();

    while (props.hasNext()) {
      String property = props.next();
      Iterator<ActionMessage> messagesIterator = messages.get(property);

      while (messagesIterator.hasNext()) {
        ActionMessage actionMessage = messagesIterator.next();
        if (actionMessage.getKey() != null) {
          if (actionMessage.getKey().equals("errors.required")) {
            Object[] values = actionMessage.getValues();
            if (values != null && values.length > 0) {
              allValues.add(values[0].toString());
            }
          } else {
            messagesUnrewrited.add(property, actionMessage);
          }
        }
      }
    }

    if (allValues.size() > 0) {
      StringBuilder buffer = new StringBuilder();
      buffer.append(allValues.get(0));
      for (int counter = 1; counter < allValues.size() - 1; counter++) {
        buffer.append(", ").append(allValues.get(counter));
      }
      if (allValues.size() > 1) {
        buffer.append(" y ").append(allValues.get(allValues.size() - 1));
      }
      messages.clear();
      messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.required", buffer.toString()));
      messages.add(messagesUnrewrited);
    }
  }

  public abstract ActionForward doExecute(ActionMapping mapping, DynaValidatorForm form,
          HttpServletRequest request, HttpServletResponse response) throws PartnersException;

  public void doValidate(ActionMapping mapping, ActionMessages messages, DynaValidatorForm form,
          HttpServletRequest request, HttpServletResponse response) throws PartnersException {
  }

  @Override
  public ActionForward executeChild(ActionMapping mapping, ActionForm baseForm,
          HttpServletRequest request, HttpServletResponse response) throws PartnersException {

    Boolean runOnce = null;
    DynaValidatorForm form = (DynaValidatorForm) baseForm;
    try {
      runOnce = (Boolean) form.get("run_once");
    } catch (IllegalArgumentException e) {
    }

    if (runOnce != null && runOnce) {
      runOnce(mapping, form, request, response);
      initValues(mapping, form, request);
      form.set("run_once", Boolean.FALSE);
      return new ActionForward(mapping.getInput());
    }

    initValues(mapping, form, request);

    ActionMessages messages = form.validate(mapping, request);
    doValidate(mapping, messages, form, request, response);

    if (!messages.isEmpty()) {
      rewriteMessages(messages);
      saveErrors(request, messages);
      return new ActionForward(mapping.getInput());
    }

    return doExecute(mapping, form, request, response);
  }

  public void initValues(ActionMapping mapping, DynaValidatorForm form, HttpServletRequest request) throws PartnersException {
  }

  public void runOnce(ActionMapping mapping, DynaValidatorForm form, HttpServletRequest request, HttpServletResponse response) throws PartnersException {
  }
}

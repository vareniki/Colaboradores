package es.regalocio.partners.struts.taglib.app;

import java.util.Iterator;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.taglib.TagUtils;

public class InformationTag extends TagSupport {

  protected String bundle;
  protected String locale;
  protected String property;

  public InformationTag() {
    bundle = null;
    locale = org.apache.struts.Globals.LOCALE_KEY;
    property = null;
  }

  public String getBundle() {
    return (this.bundle);
  }

  public void setBundle(String bundle) {
    this.bundle = bundle;
  }

  public String getLocale() {
    return (this.locale);
  }

  public void setLocale(String locale) {
    this.locale = locale;
  }

  public String getProperty() {
    return (this.property);
  }

  public void setProperty(String property) {
    this.property = property;
  }

  @Override
  public int doStartTag() throws JspException {

    StringBuilder results = new StringBuilder();

    results.append("\n<div class=\"alert alert-warning\" role=\"alert\">");
    results.append("<h4>Informaci&oacute;n</h4>");

    ActionMessages info = null;
    try {
      info = TagUtils.getInstance().getActionMessages(pageContext, Globals.MESSAGE_KEY);
    } catch (JspException e) {
      TagUtils.getInstance().saveException(pageContext, e);
      throw e;
    }

    Iterator<ActionMessage> iterator = (Iterator<ActionMessage>) ((property == null) ? info.get() : info.get(property));
    if (!iterator.hasNext()) {
      return EVAL_BODY_INCLUDE;
    }

    ActionMessage information = iterator.next();
    String message = TagUtils.getInstance().message(pageContext, bundle, locale, information.getKey(), information.getValues());
    if (message != null) {
      String header = TagUtils.getInstance().message(pageContext, bundle, locale, "information.header");
      String footer = TagUtils.getInstance().message(pageContext, bundle, locale, "information.footer");

      results.append("<p>");
      if (header != null && footer != null) {
        results.append(header);
      }
      results.append(message.replaceAll("'", "\\\\\'"));

      if (header != null && footer != null) {
        results.append(footer);
      }

      results.append("</p>");
    }
    results.append("</div>");

    TagUtils.getInstance().write(pageContext, results.toString());

    return EVAL_BODY_INCLUDE;
  }

  @Override
  public void release() {
    super.release();

    bundle = org.apache.struts.Globals.MESSAGES_KEY;
    locale = org.apache.struts.Globals.LOCALE_KEY;
    property = null;
  }
}

package es.regalocio.partners.struts.taglib.app;

import java.util.Iterator;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.taglib.TagUtils;

public class ErrorsTag extends TagSupport {

  protected String bundle;
  protected String locale;
  protected String name;
  protected String property;
  protected String title;

  public ErrorsTag() {
    bundle = null;
    locale = Globals.LOCALE_KEY;
    name = Globals.ERROR_KEY;
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

  public String getName() {
    return (this.name);
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getProperty() {
    return (this.property);
  }

  public void setProperty(String property) {
    this.property = property;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  @Override
  public int doStartTag() throws JspException {
    ActionMessages errors = null;
    try {
      errors = TagUtils.getInstance().getActionMessages(pageContext, name);
    } catch (JspException e) {
      TagUtils.getInstance().saveException(pageContext, e);
      throw e;
    }

    int size = (errors == null) ? 0 : (property == null) ? errors.size() : errors.size(property);
    if (size == 0) {
      return (EVAL_BODY_INCLUDE);
    }

    StringBuilder results = new StringBuilder();
    computeErrors(errors, results, (size == 1) ? "error" : "errors");
    TagUtils.getInstance().write(pageContext, results.toString());

    return (EVAL_BODY_INCLUDE);
  }

  @Override
  public void release() {
    super.release();

    bundle = Globals.MESSAGES_KEY;
    locale = Globals.LOCALE_KEY;
    name = Globals.ERROR_KEY;
    property = null;
  }

  private void computeErrors(ActionMessages errors, StringBuilder results, String prefixError) throws JspException {

    String header = TagUtils.getInstance().message(pageContext, bundle, locale, prefixError + ".header");
    String prefix = TagUtils.getInstance().message(pageContext, bundle, locale, prefixError + ".prefix");
    String suffix = TagUtils.getInstance().message(pageContext, bundle, locale, prefixError + ".suffix");
    String footer = TagUtils.getInstance().message(pageContext, bundle, locale, prefixError + ".footer");

    results.append("\n<div class=\"alert alert-warning\" role=\"alert\">");
    results.append("\n<h4>").append(TagUtils.getInstance().message(pageContext, bundle, locale, title).replaceAll("'", "\\\\\'"));

    if (header != null && footer != null) {
      results.append(header);
    }

    results.append("</h4><ul>");

    Iterator<ActionMessage> reports
            = (Iterator<ActionMessage>) ((property == null) ? errors.get() : errors.get(property));

    while (reports.hasNext()) {
      ActionMessage report = reports.next();
      String message = TagUtils.getInstance().message(pageContext, bundle, locale, report.getKey(), report.getValues());
      if (message == null) {
        continue;
      }

      results.append("<li>");

      if (prefix != null && suffix != null) {
        results.append(prefix);
      }

      results.append(message.replaceAll("'", "\\\\\'"));

      if (prefix != null && suffix != null) {
        results.append(suffix);
      }

      results.append("</li>");
    }

    results.append("</ul>");

    if (header != null && footer != null) {
      results.append("<h5>").append(footer).append("</h5>");
    }

    results.append("\n</div>");
  }
}

package es.regalocio.partners.struts.taglib.app;

import es.regalocio.partners.utils.XMLUtils;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import org.apache.struts.taglib.TagUtils;

@SuppressWarnings("serial")
public class Text2XMLTag extends BodyTagSupport {

  protected String body;

  public Text2XMLTag() {
    body = null;
  }

  @Override
  public int doStartTag() throws JspException {
    return (EVAL_BODY_BUFFERED);
  }

  @Override
  public int doAfterBody() throws JspException {
    if (bodyContent != null) {
      body = bodyContent.getString();

      if (body != null) {
        body = body.trim();
      }

      if (body.length() < 1) {
        body = null;
      }
    }

    return (SKIP_BODY);
  }

  @Override
  public int doEndTag() throws JspException {
    if (body != null) {
      TagUtils.getInstance().write(pageContext, XMLUtils.getInstance().text2XML(body));
    }

    return EVAL_PAGE;
  }

  @Override
  public void release() {
    super.release();

    body = null;
  }
}

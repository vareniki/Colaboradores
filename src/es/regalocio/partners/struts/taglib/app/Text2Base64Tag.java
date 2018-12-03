package es.regalocio.partners.struts.taglib.app;

import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import org.apache.struts.taglib.TagUtils;

@SuppressWarnings("serial")
public class Text2Base64Tag extends BodyTagSupport {

  protected String body;

  public Text2Base64Tag() {
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
      try {
        TagUtils.getInstance().write(pageContext, new String(Base64.getEncoder().encode(body.getBytes("utf-8"))));
      } catch (UnsupportedEncodingException ex) {
        Logger.getLogger(Text2Base64Tag.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
    return EVAL_PAGE;
  }

  @Override
  public void release() {
    super.release();

    body = null;
  }
}

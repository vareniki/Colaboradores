package es.regalocio.partners.struts.taglib.app;

import es.regalocio.partners.config.PartnersUtils;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyTagSupport;
import org.apache.struts.taglib.TagUtils;

public class GetStaticDataTag extends BodyTagSupport {

  protected String body;
  @SuppressWarnings("FieldNameHidesFieldInSuperclass")
  protected String id;
  protected String toScope;
  protected String typology;
  protected String code;

  public GetStaticDataTag() {
    body = null;
    id = null;
    toScope = null;
    typology = null;
    code = null;
  }

  @Override
  public String getId() {
    return id;
  }

  @Override
  public void setId(String id) {
    this.id = id;
  }

  public String getToScope() {
    return toScope;
  }

  public void setToScope(String toScope) {
    this.toScope = toScope;
  }

  public String getTypology() {
    return typology;
  }

  public void setTypology(String typology) {
    this.typology = typology;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
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
    int inScope, valueOfCode;
    JspException jspException;
    Object attribute;

    attribute = "";
    if ((this.body != null) || (this.code != null)) {
      try {
        valueOfCode = Integer.parseInt((code != null) ? code : body);
      } catch (NumberFormatException e) {
        jspException = new JspException("code is not an integer.");
        TagUtils.getInstance().saveException(pageContext, jspException);
        throw jspException;
      }
      attribute = PartnersUtils.getInstance().getTypologyForCode(typology, valueOfCode);
    }

    inScope = PageContext.PAGE_SCOPE;
    try {
      if (toScope != null) {
        inScope = TagUtils.getInstance().getScope(toScope);
      }
    } catch (JspException e) {
      //logger.warn("toScope was invalid name so we default to PAGE_SCOPE", e);
    }

    pageContext.setAttribute(id, attribute, inScope);

    return EVAL_PAGE;
  }

  @Override
  public void release() {
    super.release();

    body = null;
    id = null;
    toScope = null;
    typology = null;
    code = null;
  }
}

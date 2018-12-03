package es.regalocio.partners.struts.taglib.app;

import es.regalocio.partners.business.common.User;
import es.regalocio.partners.shared.SessionInfo;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import org.apache.struts.taglib.TagUtils;

public class UserTag extends TagSupport {

  @Override
  public int doStartTag() throws JspException {
    return SKIP_BODY;
  }

  @Override
  public int doEndTag() throws JspException {
    User user;
    SessionInfo sessionInfo;
    String username;

    if (pageContext.getSession() == null) {
      return EVAL_PAGE;
    }

    sessionInfo = (SessionInfo) pageContext.getSession().getAttribute(SessionInfo.SESSION_INFO);
    if (sessionInfo == null) {
      return EVAL_PAGE;
    }

    user = sessionInfo.getUser();
    if (user == null) {
      return EVAL_PAGE;
    }

    username = user.toString();
    if (username == null) {
      return EVAL_PAGE;
    }

    TagUtils.getInstance().write(pageContext, username);

    return EVAL_PAGE;
  }
}

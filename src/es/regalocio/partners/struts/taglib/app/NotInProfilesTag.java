package es.regalocio.partners.struts.taglib.app;

import es.regalocio.partners.business.common.User;
import es.regalocio.partners.shared.SessionInfo;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

public class NotInProfilesTag extends TagSupport {

  protected String profiles;

  public NotInProfilesTag() {
    profiles = null;
  }

  public String getProfiles() {
    return profiles;
  }

  public void setProfiles(String profiles) {
    this.profiles = profiles;
  }

  @Override
  public int doStartTag() throws JspException {
    if (!isAuthorized()) {
      return (EVAL_BODY_INCLUDE);
    }

    return (SKIP_BODY);
  }

  @Override
  public int doEndTag() throws JspException {
    return (EVAL_PAGE);
  }

  @Override
  public void release() {
    super.release();

    profiles = null;
  }

  private boolean isAuthorized() {

    if (pageContext.getSession() == null) {
      return true;
    }

    SessionInfo sessionInfo = (SessionInfo) pageContext.getSession().getAttribute(SessionInfo.SESSION_INFO);
    if (sessionInfo == null) {
      return true;
    }

    User user = sessionInfo.getUser();
    if (user == null) {
      return true;
    }

    return user.inProfiles(profiles);
  }
}

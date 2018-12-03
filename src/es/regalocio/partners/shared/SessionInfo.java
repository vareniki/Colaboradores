package es.regalocio.partners.shared;

import es.regalocio.partners.business.common.User;
import java.util.HashMap;
import java.util.Map;

public class SessionInfo extends Object {

  public static final String SESSION_INFO = "SESSION_INFO";
  private User user;
  private final Map<String, String> personal = new HashMap<>();

  public SessionInfo() {
    user = null;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public Map<String, String> getPersonal() {
    return personal;
  }
}

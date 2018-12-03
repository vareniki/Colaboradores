package es.regalocio.partners.utils;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.config.ActionConfig;

public class ServletUtils {

  private static ServletUtils instance = null;

  public static synchronized ServletUtils getInstance() {
    if (instance == null) {
      instance = new ServletUtils();
    }
    return instance;
  }

  private ServletUtils() {
  }

  public String getStrutsActionMapping(HttpServletRequest request) {
    ActionConfig config = (ActionConfig) request.getAttribute("org.apache.struts.action.mapping.instance");
    return (config != null) ? getStrutsActionKey(config) : "";
  }

  public String getStrutsActionKey(ActionConfig config) {
    String result = "";
    if (config != null && config.getPath() != null && !config.getPath().isEmpty()) {
      result = config.getPath().substring(1);
    }

    return (result);
  }
}

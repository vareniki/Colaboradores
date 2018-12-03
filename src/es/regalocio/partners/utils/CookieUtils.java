package es.regalocio.partners.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtils {

  private static CookieUtils instance = null;

  public static synchronized CookieUtils getInstance() {
    if (instance == null) {
      instance = new CookieUtils();
    }
    return instance;
  }

  public String getCookieValue(HttpServletRequest request, String cookieName) {
    if (request == null || cookieName == null) {
      return null;
    }
    Cookie[] cookieArray = request.getCookies();
    if (cookieArray != null) {
      for (Cookie cookieArray1 : cookieArray) {
        if (cookieName.equals(cookieArray1.getName())) {
          return cookieArray1.getValue();
        }
      }
    }
    return null;
  }

  public void setCookie(HttpServletResponse response, String cookieName, String cookieValue, int cookieMaxAge) {
    Cookie cookie = new Cookie(cookieName, cookieValue);
    cookie.setMaxAge(cookieMaxAge);
    response.addCookie(cookie);
  }
}

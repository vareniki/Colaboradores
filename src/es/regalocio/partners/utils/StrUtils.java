package es.regalocio.partners.utils;

public class StrUtils {

  private static StrUtils instance = null;

  public static synchronized StrUtils getInstance() {
    if (instance == null) {
      instance = new StrUtils();
    }
    return instance;
  }

  private StrUtils() {
  }

  public String capitalizeFirstOnly(String value) {

    String newValue = strFmt(value);
    if (newValue == null) {
      return null;
    }
    boolean capitalize = true;
    StringBuilder sb = new StringBuilder();
    for (int counter = 0; counter < newValue.length(); counter++) {
      char aCharacter = newValue.charAt(counter);
      if (Character.isLetter(aCharacter)) {
        if (capitalize) {
          sb.append(Character.toUpperCase(aCharacter));
          capitalize = false;
        } else {
          sb.append(Character.toLowerCase(aCharacter));
        }
      } else {
        sb.append(aCharacter);
        capitalize = true;
      }
    }

    return sb.toString();
  }

  /**
   * @param value
   * @return
   */
  public String phoneFmt(String value) {

    String newValue = strFmt(value);
    if (newValue == null) {
      return null;
    }

    int nbDigit = 0;
    StringBuilder aStringbuffer = new StringBuilder();
    int counter;
    for (counter = 0; counter < newValue.length(); counter++) {
      char aCharacter = newValue.charAt(counter);
      if (Character.isDigit(aCharacter)) {
        nbDigit++;
        if (((nbDigit - 1) > 0) && ((nbDigit - 1) % 2 == 0)) {
          aStringbuffer.append(' ');
        }
        aStringbuffer.append(aCharacter);
      } else if (aCharacter != ' ') {
        break;
      }
    }

    if ((counter == newValue.length()) && (nbDigit == 10)) {
      return aStringbuffer.toString();
    }

    return newValue;
  }

  public String strFmt(String value) {
    if (value == null) {
      return null;
    }

    String newValue = value.trim();
    return (newValue.length() == 0) ? null : newValue;
  }

  public static final String escapeHTML(String s) {
    StringBuilder sb = new StringBuilder();
    int n = s.length();
    for (int i = 0; i < n; i++) {
      char c = s.charAt(i);
      switch (c) {
        case '<':
          sb.append("&lt;");
          break;
        case '>':
          sb.append("&gt;");
          break;
        case '&':
          sb.append("&amp;");
          break;
        case '"':
          sb.append("&quot;");
          break;
        case '�':
          sb.append("&agrave;");
          break;
        case '�':
          sb.append("&Agrave;");
          break;
        case '�':
          sb.append("&acirc;");
          break;
        case '�':
          sb.append("&Acirc;");
          break;
        case '�':
          sb.append("&auml;");
          break;
        case '�':
          sb.append("&Auml;");
          break;
        case '�':
          sb.append("&aring;");
          break;
        case '�':
          sb.append("&Aring;");
          break;
        case '�':
          sb.append("&aelig;");
          break;
        case '�':
          sb.append("&AElig;");
          break;
        case '�':
          sb.append("&ccedil;");
          break;
        case '�':
          sb.append("&Ccedil;");
          break;
        case '�':
          sb.append("&eacute;");
          break;
        case '�':
          sb.append("&Eacute;");
          break;
        case '�':
          sb.append("&egrave;");
          break;
        case '�':
          sb.append("&Egrave;");
          break;
        case '�':
          sb.append("&ecirc;");
          break;
        case '�':
          sb.append("&Ecirc;");
          break;
        case '�':
          sb.append("&euml;");
          break;
        case '�':
          sb.append("&Euml;");
          break;
        case '�':
          sb.append("&iuml;");
          break;
        case '�':
          sb.append("&Iuml;");
          break;
        case '�':
          sb.append("&ocirc;");
          break;
        case '�':
          sb.append("&Ocirc;");
          break;
        case '�':
          sb.append("&ouml;");
          break;
        case '�':
          sb.append("&Ouml;");
          break;
        case '�':
          sb.append("&oslash;");
          break;
        case '�':
          sb.append("&Oslash;");
          break;
        case '�':
          sb.append("&szlig;");
          break;
        case '�':
          sb.append("&ugrave;");
          break;
        case '�':
          sb.append("&Ugrave;");
          break;
        case '�':
          sb.append("&ucirc;");
          break;
        case '�':
          sb.append("&Ucirc;");
          break;
        case '�':
          sb.append("&uuml;");
          break;
        case '�':
          sb.append("&Uuml;");
          break;
        case '�':
          sb.append("&reg;");
          break;
        case '�':
          sb.append("&copy;");
          break;
        case '�':
          sb.append("&euro;");
          break;
        case '�':
          sb.append("&ntilde;");
          break;
        case '�':
          sb.append("&Ntilde;");
          break;
        // be carefull with this one (non-breaking whitee space)
        case ' ':
          sb.append("&nbsp;");
          break;

        default:
          sb.append(c);
          break;
      }
    }
    return sb.toString();
  }

  public static final String escapeHTMLSpecialChars(String s) {
    StringBuilder sb = new StringBuilder();
    int n = s.length();
    for (int i = 0; i < n; i++) {
      char c = s.charAt(i);
      switch (c) {
        case '�':
          sb.append("&aacute;");
          break;
        case '�':
          sb.append("&Aacute;");
          break;
        case '�':
          sb.append("&agrave;");
          break;
        case '�':
          sb.append("&Agrave;");
          break;
        case '�':
          sb.append("&acirc;");
          break;
        case '�':
          sb.append("&Acirc;");
          break;
        case '�':
          sb.append("&auml;");
          break;
        case '�':
          sb.append("&Auml;");
          break;
        case '�':
          sb.append("&aring;");
          break;
        case '�':
          sb.append("&Aring;");
          break;
        case '�':
          sb.append("&aelig;");
          break;
        case '�':
          sb.append("&AElig;");
          break;
        case '�':
          sb.append("&ccedil;");
          break;
        case '�':
          sb.append("&Ccedil;");
          break;
        case '�':
          sb.append("&eacute;");
          break;
        case '�':
          sb.append("&Eacute;");
          break;
        case '�':
          sb.append("&egrave;");
          break;
        case '�':
          sb.append("&Egrave;");
          break;
        case '�':
          sb.append("&ecirc;");
          break;
        case '�':
          sb.append("&Ecirc;");
          break;
        case '�':
          sb.append("&euml;");
          break;
        case '�':
          sb.append("&Euml;");
          break;
        case '�':
          sb.append("&iuml;");
          break;
        case '�':
          sb.append("&Iacute;");
          break;
        case '�':
          sb.append("&iacute;");
          break;
        case '�':
          sb.append("&Iuml;");
          break;
        case '�':
          sb.append("&Oacute;");
          break;
        case '�':
          sb.append("&oacute;");
          break;
        case '�':
          sb.append("&ocirc;");
          break;
        case '�':
          sb.append("&Ocirc;");
          break;
        case '�':
          sb.append("&ouml;");
          break;
        case '�':
          sb.append("&Ouml;");
          break;
        case '�':
          sb.append("&oslash;");
          break;
        case '�':
          sb.append("&Oslash;");
          break;
        case '�':
          sb.append("&szlig;");
          break;
        case '�':
          sb.append("&ugrave;");
          break;
        case '�':
          sb.append("&Uacute;");
          break;
        case '�':
          sb.append("&uacute;");
          break;
        case '�':
          sb.append("&Ugrave;");
          break;
        case '�':
          sb.append("&ucirc;");
          break;
        case '�':
          sb.append("&Ucirc;");
          break;
        case '�':
          sb.append("&uuml;");
          break;
        case '�':
          sb.append("&Uuml;");
          break;
        case '�':
          sb.append("&reg;");
          break;
        case '�':
          sb.append("&copy;");
          break;
        case '�':
          sb.append("&euro;");
          break;
        case '�':
          sb.append("&ntilde;");
          break;
        case '�':
          sb.append("&Ntilde;");
          break;
        case '�':
          sb.append("&iquest;");
          break;
        case '�':
          sb.append("&iexcl;");
          break;

        default:
          sb.append(c);
          break;
      }
    }
    return sb.toString();
  }

}

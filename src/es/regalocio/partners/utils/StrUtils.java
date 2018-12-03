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
        case 'à':
          sb.append("&agrave;");
          break;
        case 'À':
          sb.append("&Agrave;");
          break;
        case 'â':
          sb.append("&acirc;");
          break;
        case 'Â':
          sb.append("&Acirc;");
          break;
        case 'ä':
          sb.append("&auml;");
          break;
        case 'Ä':
          sb.append("&Auml;");
          break;
        case 'å':
          sb.append("&aring;");
          break;
        case 'Å':
          sb.append("&Aring;");
          break;
        case 'æ':
          sb.append("&aelig;");
          break;
        case 'Æ':
          sb.append("&AElig;");
          break;
        case 'ç':
          sb.append("&ccedil;");
          break;
        case 'Ç':
          sb.append("&Ccedil;");
          break;
        case 'é':
          sb.append("&eacute;");
          break;
        case 'É':
          sb.append("&Eacute;");
          break;
        case 'è':
          sb.append("&egrave;");
          break;
        case 'È':
          sb.append("&Egrave;");
          break;
        case 'ê':
          sb.append("&ecirc;");
          break;
        case 'Ê':
          sb.append("&Ecirc;");
          break;
        case 'ë':
          sb.append("&euml;");
          break;
        case 'Ë':
          sb.append("&Euml;");
          break;
        case 'ï':
          sb.append("&iuml;");
          break;
        case 'Ï':
          sb.append("&Iuml;");
          break;
        case 'ô':
          sb.append("&ocirc;");
          break;
        case 'Ô':
          sb.append("&Ocirc;");
          break;
        case 'ö':
          sb.append("&ouml;");
          break;
        case 'Ö':
          sb.append("&Ouml;");
          break;
        case 'ø':
          sb.append("&oslash;");
          break;
        case 'Ø':
          sb.append("&Oslash;");
          break;
        case 'ß':
          sb.append("&szlig;");
          break;
        case 'ù':
          sb.append("&ugrave;");
          break;
        case 'Ù':
          sb.append("&Ugrave;");
          break;
        case 'û':
          sb.append("&ucirc;");
          break;
        case 'Û':
          sb.append("&Ucirc;");
          break;
        case 'ü':
          sb.append("&uuml;");
          break;
        case 'Ü':
          sb.append("&Uuml;");
          break;
        case '®':
          sb.append("&reg;");
          break;
        case '©':
          sb.append("&copy;");
          break;
        case '¤':
          sb.append("&euro;");
          break;
        case 'ñ':
          sb.append("&ntilde;");
          break;
        case 'Ñ':
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
        case 'á':
          sb.append("&aacute;");
          break;
        case 'Á':
          sb.append("&Aacute;");
          break;
        case 'à':
          sb.append("&agrave;");
          break;
        case 'À':
          sb.append("&Agrave;");
          break;
        case 'â':
          sb.append("&acirc;");
          break;
        case 'Â':
          sb.append("&Acirc;");
          break;
        case 'ä':
          sb.append("&auml;");
          break;
        case 'Ä':
          sb.append("&Auml;");
          break;
        case 'å':
          sb.append("&aring;");
          break;
        case 'Å':
          sb.append("&Aring;");
          break;
        case 'æ':
          sb.append("&aelig;");
          break;
        case 'Æ':
          sb.append("&AElig;");
          break;
        case 'ç':
          sb.append("&ccedil;");
          break;
        case 'Ç':
          sb.append("&Ccedil;");
          break;
        case 'é':
          sb.append("&eacute;");
          break;
        case 'É':
          sb.append("&Eacute;");
          break;
        case 'è':
          sb.append("&egrave;");
          break;
        case 'È':
          sb.append("&Egrave;");
          break;
        case 'ê':
          sb.append("&ecirc;");
          break;
        case 'Ê':
          sb.append("&Ecirc;");
          break;
        case 'ë':
          sb.append("&euml;");
          break;
        case 'Ë':
          sb.append("&Euml;");
          break;
        case 'ï':
          sb.append("&iuml;");
          break;
        case 'Í':
          sb.append("&Iacute;");
          break;
        case 'í':
          sb.append("&iacute;");
          break;
        case 'Ï':
          sb.append("&Iuml;");
          break;
        case 'Ó':
          sb.append("&Oacute;");
          break;
        case 'ó':
          sb.append("&oacute;");
          break;
        case 'ô':
          sb.append("&ocirc;");
          break;
        case 'Ô':
          sb.append("&Ocirc;");
          break;
        case 'ö':
          sb.append("&ouml;");
          break;
        case 'Ö':
          sb.append("&Ouml;");
          break;
        case 'ø':
          sb.append("&oslash;");
          break;
        case 'Ø':
          sb.append("&Oslash;");
          break;
        case 'ß':
          sb.append("&szlig;");
          break;
        case 'ù':
          sb.append("&ugrave;");
          break;
        case 'Ú':
          sb.append("&Uacute;");
          break;
        case 'ú':
          sb.append("&uacute;");
          break;
        case 'Ù':
          sb.append("&Ugrave;");
          break;
        case 'û':
          sb.append("&ucirc;");
          break;
        case 'Û':
          sb.append("&Ucirc;");
          break;
        case 'ü':
          sb.append("&uuml;");
          break;
        case 'Ü':
          sb.append("&Uuml;");
          break;
        case '®':
          sb.append("&reg;");
          break;
        case '©':
          sb.append("&copy;");
          break;
        case '¤':
          sb.append("&euro;");
          break;
        case 'ñ':
          sb.append("&ntilde;");
          break;
        case 'Ñ':
          sb.append("&Ntilde;");
          break;
        case '¿':
          sb.append("&iquest;");
          break;
        case '¡':
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

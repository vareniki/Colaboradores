package es.regalocio.partners.utils;

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.HashMap;
import java.util.Map;

public class XMLUtils {

  private static XMLUtils instance = null;
  private static final Object[][] XMLTable
          = new Object[][]{{34, "&quot;"}, {38, "&amp;"}, {39, "&apos;"}, {60, "&lt;"}, {62, "&gt;"}};

  public static synchronized XMLUtils getInstance() {
    if (instance == null) {
      instance = new XMLUtils();
    }
    return instance;
  }
  private final Map<Object, Object> XMLMap;

  private XMLUtils() {
    int counter;

    XMLMap = new HashMap<>(XMLTable.length);
    for (counter = 0; counter < XMLTable.length; counter++) {
      XMLMap.put(XMLTable[counter][0], XMLTable[counter][1]);
    }
  }

  public String text2XML(String text) {
    if (text == null) {
      return null;
    }
    char character;
    String XMLValue;

    StringBuilder buffer = new StringBuilder();
    StringCharacterIterator iterator = new StringCharacterIterator(text);
    for (character = iterator.first(); character != CharacterIterator.DONE; character = iterator.next()) {
      XMLValue = (String) XMLMap.get(character);
      if (XMLValue == null) {
        buffer.append(character);
      } else {
        buffer.append(XMLValue);
      }
    }

    return buffer.toString();
  }

  public String XML2Text(String text) {
    if (text == null) {
      return null;
    }

    String returnValue = text;
    for (Object[] XMLTable1 : XMLTable) {
      String replacement = Character.toString((char) XMLTable1[0]);
      returnValue = replaceAll(returnValue, (String) XMLTable1[1], replacement);
    }

    return returnValue;
  }

  private static String replaceAll(String text, String expression, String replacement) {

    if (expression == null) {
      throw new NullPointerException("expression argument mut be not null");
    }
    if (replacement == null) {
      throw new NullPointerException("replacement argument mut be not null");
    }
    if (text == null) {
      return null;
    }
    StringBuilder buffer = new StringBuilder();

    int firstIndex = 0;
    int lengthOfExpression = expression.length();
    int endIndex = text.indexOf(expression);
    while (endIndex != -1) {
      buffer.append(text.substring(firstIndex, endIndex));
      buffer.append(replacement);
      firstIndex = endIndex + lengthOfExpression;
      endIndex = text.indexOf(expression, firstIndex);
    }

    if (firstIndex == 0) {
      return text;
    }

    if (firstIndex < text.length()) {
      buffer.append(text.substring(firstIndex));
    }

    return buffer.toString();
  }
}

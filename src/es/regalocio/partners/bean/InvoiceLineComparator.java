package es.regalocio.partners.bean;

import java.text.Collator;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Locale;

public class InvoiceLineComparator implements Comparator {

  public static final int ORDER_ASC = 1;
  public static final int ORDER_DESC = 2;
  public static final String SORTABLE_FIELD[] = {"THEMATIC_BOX", "EAN13", "END_OF_VALIDITY", "UNIT_PRICE", "UNIT_MARGIN", "AMOUNT"};
  private int direction;
  private String field;
  private Collator collator;

  public InvoiceLineComparator(String field, int direction) {
    if (!Arrays.asList(SORTABLE_FIELD).contains(field)) {
      throw new IllegalArgumentException("unknown parameter field.");
    }

    if ((direction != ORDER_ASC) && (direction != ORDER_DESC)) {
      throw new IllegalArgumentException("direction parameter have to take one value of OrderLineComparator.ORDER_ASC or OrderLineComparator.ORDER_DESC");
    }

    this.field = field;
    this.direction = direction;

    collator = Collator.getInstance(new Locale("es", "ES"));
    collator.setStrength(Collator.SECONDARY);
  }

  @Override
  public int compare(Object object1, Object object2) throws ClassCastException {
    if (direction == ORDER_DESC) {
      return -compare((InvoiceLineBean) object1, (InvoiceLineBean) object2);
    }

    return compare((InvoiceLineBean) object1, (InvoiceLineBean) object2);
  }

  public int compare(InvoiceLineBean object1, InvoiceLineBean object2) {
    if (field.equals(SORTABLE_FIELD[0])) {
      return compare(object1.getThematicBox(), object2.getThematicBox());
    }

    if (field.equals(SORTABLE_FIELD[1])) {
      return compare(object1.getEan13(), object2.getEan13());
    }

    if (field.equals(SORTABLE_FIELD[2])) {
      return compare(object1.getEndOfValidity(), object2.getEndOfValidity());
    }

    if (field.equals(SORTABLE_FIELD[3])) {
      return compare(object1.getUnitPrice(), object2.getUnitPrice());
    }

    if (field.equals(SORTABLE_FIELD[4])) {
      return compare(object1.getUnitMargin(), object2.getUnitMargin());
    }

    if (field.equals(SORTABLE_FIELD[5])) {
      return compare(object1.getAmount(), object2.getAmount());
    }

    throw new RuntimeException("Unreachable code raised.");
  }

  public int compare(String object1, String object2) {
    if ((object1 == null) && (object2 == null)) {
      return 0;
    }

    if (object1 == null) {
      return -1;
    }

    if (object2 == null) {
      return 1;
    }

    return collator.compare(object1, object2);
  }

  public int compare(Comparable object1, Comparable object2) {
    if ((object1 == null) && (object2 == null)) {
      return 0;
    }

    if (object1 == null) {
      return -1;
    }

    if (object2 == null) {
      return 1;
    }

    return object1.compareTo(object2);
  }

  public boolean equals(Object object1, Object object2) throws ClassCastException {
    return equals((InvoiceLineBean) object1, (InvoiceLineBean) object2);
  }

  public boolean equals(InvoiceLineBean object1, InvoiceLineBean object2) {
    if (field.equals(SORTABLE_FIELD[0])) {
      return equals(object1.getThematicBox(), object2.getThematicBox());
    }

    if (field.equals(SORTABLE_FIELD[1])) {
      return equals(object1.getEan13(), object2.getEan13());
    }

    if (field.equals(SORTABLE_FIELD[2])) {
      return equals(object1.getEndOfValidity(), object2.getEndOfValidity());
    }

    if (field.equals(SORTABLE_FIELD[3])) {
      return equals(object1.getUnitPrice(), object2.getUnitPrice());
    }

    if (field.equals(SORTABLE_FIELD[4])) {
      return equals(object1.getUnitMargin(), object2.getUnitMargin());
    }

    if (field.equals(SORTABLE_FIELD[5])) {
      return equals(object1.getAmount(), object2.getAmount());
    }

    throw new RuntimeException("Unreachable code raised.");
  }

  public boolean equals(String object1, String object2) {
    if ((object1 == null) && (object2 == null)) {
      return true;
    }

    if ((object1 == null) || (object2 == null)) {
      return true;
    }

    return collator.equals(object1, object2);
  }

  public boolean equals(Comparable object1, Comparable object2) {
    if ((object1 == null) && (object2 == null)) {
      return true;
    }

    if ((object1 == null) || (object2 == null)) {
      return true;
    }

    return object1.equals(object2);
  }
}
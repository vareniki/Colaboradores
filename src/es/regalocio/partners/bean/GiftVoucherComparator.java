package es.regalocio.partners.bean;

import java.text.Collator;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;

public class GiftVoucherComparator implements Comparator {

  public static final int ORDER_ASC = 1;
  public static final int ORDER_DESC = 2;
  public static final String SORTABLE_FIELD[] = {"GIFT_VOUCHER_NUMBER", "THEMATIC_BOX", "END_OF_VALIDITY", "PRICE", "COMMISSION", "REPAYMENT"};
  private int direction;
  private String field;
  private Collator collator;

  public GiftVoucherComparator(String field, int direction) {
    if (!Arrays.asList(SORTABLE_FIELD).contains(field)) {
      throw new IllegalArgumentException("unknown parameter field.");
    }

    if ((direction != ORDER_ASC) && (direction != ORDER_DESC)) {
      throw new IllegalArgumentException("direction parameter have to take one value of GiftVoucherComparator.ORDER_ASC or GiftVoucherComparator.ORDER_DESC");
    }

    this.field = field;
    this.direction = direction;

    collator = Collator.getInstance(new Locale("es", "ES"));
    collator.setStrength(Collator.SECONDARY);
  }

  @Override
  public int compare(Object object1, Object object2) throws ClassCastException {
    if (direction == ORDER_DESC) {
      return -compare((GiftVoucherBean) object1, (GiftVoucherBean) object2);
    }

    return compare((GiftVoucherBean) object1, (GiftVoucherBean) object2);
  }

  public int compare(GiftVoucherBean object1, GiftVoucherBean object2) {
    if (field.equals(SORTABLE_FIELD[0])) {
      return compare(object1.getGiftVoucherNumber(), object2.getGiftVoucherNumber());
    }

    if (field.equals(SORTABLE_FIELD[1])) {
      return compare(object1.getThematicBox(), object2.getThematicBox());
    }

    if (field.equals(SORTABLE_FIELD[2])) {
      return compare(object1.getEndOfValidity(), object2.getEndOfValidity());
    }

    if (field.equals(SORTABLE_FIELD[3])) {
      return compare(object1.getPrice(), object2.getPrice());
    }

    if (field.equals(SORTABLE_FIELD[4])) {
      return compare(object1.getCommission(), object2.getCommission());
    }

    if (field.equals(SORTABLE_FIELD[5])) {
      return compare(object1.getRepayment(), object2.getRepayment());
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

  public int compare(Date object1, Date object2) {
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

  public int compare(Double object1, Double object2) {
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
    return equals((GiftVoucherBean) object1, (GiftVoucherBean) object2);
  }

  public boolean equals(GiftVoucherBean object1, GiftVoucherBean object2) {
    if (field.equals(SORTABLE_FIELD[0])) {
      return equals(object1.getGiftVoucherNumber(), object2.getGiftVoucherNumber());
    }

    if (field.equals(SORTABLE_FIELD[1])) {
      return equals(object1.getThematicBox(), object2.getThematicBox());
    }

    if (field.equals(SORTABLE_FIELD[2])) {
      return equals(object1.getEndOfValidity(), object2.getEndOfValidity());
    }

    if (field.equals(SORTABLE_FIELD[3])) {
      return equals(object1.getPrice(), object2.getPrice());
    }

    if (field.equals(SORTABLE_FIELD[4])) {
      return equals(object1.getCommission(), object2.getCommission());
    }

    if (field.equals(SORTABLE_FIELD[5])) {
      return equals(object1.getRepayment(), object2.getRepayment());
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

  public boolean equals(Date object1, Date object2) {
    if ((object1 == null) && (object2 == null)) {
      return true;
    }

    if ((object1 == null) || (object2 == null)) {
      return true;
    }

    return object1.equals(object2);
  }

  public boolean equals(Double object1, Double object2) {
    if ((object1 == null) && (object2 == null)) {
      return true;
    }

    if ((object1 == null) || (object2 == null)) {
      return true;
    }

    return object1.equals(object2);
  }
}
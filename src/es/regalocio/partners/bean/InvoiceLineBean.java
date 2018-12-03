package es.regalocio.partners.bean;

import java.util.Date;

public class InvoiceLineBean extends Object {

  private final Integer thematicId;
  private final String thematicBox;
  private final String ean13;
  private final Date endOfValidity;
  private final Double unitPrice;
  private final Double unitMargin;
  private final Integer amount;

  public InvoiceLineBean(Integer thematicId, String thematicBox, String ean13,
          Date endOfValidity, Double unitPrice, Double unitMargin, Integer amount) {

    this.thematicId = thematicId;
    this.thematicBox = thematicBox;
    this.ean13 = ean13;
    this.endOfValidity = endOfValidity;
    this.unitPrice = unitPrice;
    this.unitMargin = unitMargin;
    this.amount = amount;
  }

  public String getThematicBox() {
    return thematicBox;
  }

  public String getEan13() {
    return ean13;
  }

  public Date getEndOfValidity() {
    return endOfValidity;
  }

  public Double getUnitPrice() {
    return unitPrice;
  }

  public Double getUnitMargin() {
    return unitMargin;
  }

  public Integer getAmount() {
    return amount;
  }

  public Integer getThematicId() {
    return thematicId;
  }

}

package es.regalocio.partners.bean;

import es.regalocio.partners.business.common.GiftVoucher;
import java.util.Date;

public class GiftVoucherBean extends Object { 

  private final Integer index;
  private final String giftVoucherNumber;
  private final char word;
  private final String thematicBox;
  private final Date endOfValidity;
  private final Double price;
  private final Double commission;
  private final Double repayment;
  private final Boolean specificAgreementExists;

  public GiftVoucherBean(Integer index, String ean8Word, String thematicBox, Date endOfValidity,
          Double price, Double commission, Double repayment, Boolean specificAgreementExists) {

    this.index = index;

    this.giftVoucherNumber = GiftVoucher.filterEAN8Word(ean8Word);
    this.word = GiftVoucher.getWordForEAN8(ean8Word);

    this.thematicBox = thematicBox;
    this.endOfValidity = endOfValidity;
    this.price = price;
    this.commission = commission;
    this.repayment = repayment;
    this.specificAgreementExists = specificAgreementExists;
  }

  public Double getCommission() {
    return commission;
  }

  public Date getEndOfValidity() {
    return endOfValidity;
  }

  public String getGiftVoucherNumber() {
    return giftVoucherNumber;
  }

  public String getGiftVoucherNumberAndWord() {
    return giftVoucherNumber + ((word != ' ') ? word : "");
  }

  public Integer getIndex() {
    return index;
  }

  public Double getPrice() {
    return price;
  }

  public Double getRepayment() {
    return repayment;
  }

  public Boolean getSpecificAgreementExists() {
    return specificAgreementExists;
  }

  public String getThematicBox() {
    return thematicBox;
  }
}

package es.regalocio.partners.struts.action.repayment;

import es.regalocio.partners.business.common.GiftVoucher;
import es.regalocio.partners.business.common.Partner;
import es.regalocio.partners.business.common.Repayment;
import es.regalocio.partners.business.common.shared.UniqueGiftVoucherException;
import es.regalocio.partners.business.services.PartnerService;
import es.regalocio.partners.config.PartnersServices;
import es.regalocio.partners.config.PartnersUtils;
import es.regalocio.partners.struts.action.DynaValidatorFormAction;
import java.text.MessageFormat;
import java.text.ParseException;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.DynaValidatorForm;

public abstract class RepaymentAction extends DynaValidatorFormAction {

  @Override
  public void initValues(ActionMapping mapping, DynaValidatorForm form, HttpServletRequest request) {

    Integer partnerId = (Integer) form.get("partner_id");
    if (partnerId != null && partnerId != 0) {
      PartnerService service = PartnersServices.createPartnerService();
      Partner partner = service.getPartnerForId(partnerId);
      PartnersUtils utils = PartnersUtils.getInstance();

      request.setAttribute("partner", partner);
      request.setAttribute("contact", utils.createContactAttribute(partner.getContact()));
      request.setAttribute("address", utils.createAddressAttribute(partner.getAddress()));
    }
  }

  /**
   *
   * @param repayment
   * @param form
   * @throws UniqueGiftVoucherException
   */
  protected void createGiftVouchers(Repayment repayment, DynaValidatorForm form) throws UniqueGiftVoucherException {

    String[] giftVouchers = (String[]) form.get("gift_voucher_array");
    for (String giftVoucherStr : giftVouchers) {
      GiftVoucher giftVoucher = createGiftVoucher(giftVoucherStr);
      if (repayment.getGiftVoucherList().contains(giftVoucher)) {
        throw new UniqueGiftVoucherException(giftVoucher);
      }
      repayment.getGiftVoucherList().add(giftVoucher);
    }
  }

  protected GiftVoucher createGiftVoucher(String giftVoucher) {

    MessageFormat parser = new MessageFormat("{0,number,integer}/{1,date,ddMMyyyy}/{2}");
    try {
      Object[] giftVoucherData = parser.parse(giftVoucher);
      return new GiftVoucher(((Number) giftVoucherData[0]).intValue(), (Date) giftVoucherData[1], (String) giftVoucherData[2], 0, null);
    } catch (ParseException ex) {
      return null;
    }
  }
}

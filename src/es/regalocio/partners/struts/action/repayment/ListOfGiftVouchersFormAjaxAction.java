package es.regalocio.partners.struts.action.repayment;

import es.regalocio.partners.bean.GiftVoucherBean;
import es.regalocio.partners.bean.GiftVoucherComparator;
import es.regalocio.partners.business.common.*;
import es.regalocio.partners.business.services.PartnerService;
import es.regalocio.partners.config.PartnersServices;
import es.regalocio.partners.config.PartnersUtils;
import java.text.MessageFormat;
import java.text.ParseException;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.DynaValidatorForm;

public class ListOfGiftVouchersFormAjaxAction extends Action {

  @Override
  public ActionForward execute(ActionMapping mapping, ActionForm baseForm,
          HttpServletRequest request, HttpServletResponse response) {

    DynaValidatorForm form = (DynaValidatorForm) baseForm;
    PartnerService service = PartnersServices.createPartnerService();
    Integer partnerId = (Integer) form.get("partner_id");
    Partner partner = null;
    if (partnerId != null && partnerId != 0) {
      partner = service.getPartnerForId(partnerId);
    }
    List<GiftVoucherBean> list = new ArrayList<>();
    String[] giftVouchers = (String[]) form.get("gift_voucher_array");
    if (giftVouchers != null) {
      for (int i = 0; i < giftVouchers.length; i++) {
        addGiftVoucher(i, list, partner, giftVouchers[i]);
      }
    }

    GiftVoucherComparator comparator = createComparator(request);
    if (comparator != null) {
      Collections.sort(list, comparator);
    }
    request.setAttribute("gift_voucher_list", list);
    request.setAttribute("rowcount", Integer.toString(list.size()));

    return mapping.findForward("success");
  }

  private static GiftVoucherComparator createComparator(HttpServletRequest request) {

    MessageFormat messageFormat = new MessageFormat("s{0,number,integer}");
    Enumeration<String> enum15 = request.getParameterNames();
    while (enum15.hasMoreElements()) {
      String name = enum15.nextElement();
      try {
        Object[] result = messageFormat.parse(name);
        String direction = request.getParameter(name);
        if (direction == null) {
          continue;
        }
        if (direction.equals("ASC")) {
          return new GiftVoucherComparator(
                  GiftVoucherComparator.SORTABLE_FIELD[((Number) result[0]).intValue()],
                  GiftVoucherComparator.ORDER_ASC);
        }
        if (direction.equals("DESC")) {
          return new GiftVoucherComparator(
                  GiftVoucherComparator.SORTABLE_FIELD[((Number) result[0]).intValue()],
                  GiftVoucherComparator.ORDER_DESC);
        }
      } catch (Exception e) {
      }
    }

    return null;
  }

  private static void addGiftVoucher(int index, List<GiftVoucherBean> list, Partner partner, String giftVoucher) {

    MessageFormat parser = new MessageFormat("{0,number,integer}/{1,date,ddMMyyyy}/{2}");
    Object[] giftVoucherData;
    try {
      giftVoucherData = parser.parse(giftVoucher);
    } catch (ParseException ex) {
      return;
    }

    Thematic thematic = PartnersUtils.getInstance().getThematic(((Number) giftVoucherData[0]).intValue());
    ThematicRate rate = (!thematic.isMultiple())
            ? thematic.getThematicRate() : thematic.getThematicRate(GiftVoucher.getWordForEAN8(giftVoucher));

    Double commission = null, priceService = null;
    SpecificAgreement specificAgreement = null;
    if (partner != null) {
      SpecificAgreementList saList = partner.getSpecificAgreementList();
      if (!thematic.isMultiple()) {
        specificAgreement = saList.getSpecificAgreement(thematic.getId(), (Date) giftVoucherData[1], true);
      } else {
        specificAgreement = saList.getSpecificAgreement(thematic.getId(), (Date) giftVoucherData[1], GiftVoucher.getWordForEAN8(giftVoucher), true);
      }

      if (specificAgreement != null) {
        commission = specificAgreement.getCommission();
        priceService = specificAgreement.getPriceService();
      } else {
        commission = rate.getDefaultCommission();
      }
    }

    if (priceService == null || priceService == 0) {
      priceService = rate.getPriceService();
    }

    list.add(new GiftVoucherBean(
            index, (String) giftVoucherData[2], thematic.toString(),
            (Date) giftVoucherData[1], priceService, commission,
            (commission == null) ? null : priceService - commission, specificAgreement != null));
  }
}

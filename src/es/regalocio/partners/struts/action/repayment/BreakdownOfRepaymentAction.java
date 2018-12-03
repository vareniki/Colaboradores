package es.regalocio.partners.struts.action.repayment;

import es.regalocio.partners.business.common.Edition;
import es.regalocio.partners.business.common.GiftVoucher;
import es.regalocio.partners.business.common.Partner;
import es.regalocio.partners.business.common.Repayment;
import es.regalocio.partners.business.common.Thematic;
import es.regalocio.partners.business.common.shared.PartnersException;
import es.regalocio.partners.config.PartnersServices;
import es.regalocio.partners.config.PartnersUtils;
import es.regalocio.partners.struts.action.DynaValidatorFormAction;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.TreeMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.DynaValidatorForm;

public class BreakdownOfRepaymentAction extends DynaValidatorFormAction {

  private static final double VAT21 = 0.21D;

  private static void addToTotal(Properties totals, String total, double value) {
    double currentValue = (Double) totals.get(total) + round(value, 2);
    totals.put(total, currentValue);
  }

  private static void addToTotal(Properties totals, String total, int value) {
    int currentValue = (Integer) totals.get(total) + value;
    totals.put(total, currentValue);
  }

  private static boolean reembolsoConIva(Partner partner, Repayment repayment) {

    String conIvaDesde = null;
    if (partner.getOpciones2() >= 201706) {
      conIvaDesde = ("" + partner.getOpciones2()).substring(2) + "00000";
    }

    return (conIvaDesde != null && repayment.getInvoiceNumber().compareTo(conIvaDesde) > 0);
  }
  
  private static Properties generateFinancialData(Partner partner, Repayment repayment) {

    Map<Edition, Integer> records = new TreeMap<>();
    Properties totals = initTotalsForDetailedCreditNote();
    PartnersUtils partnersUtils = PartnersUtils.getInstance();

    for (GiftVoucher giftVoucher : repayment.getGiftVoucherList()) {
      Thematic thematic = partnersUtils.getThematic(giftVoucher.getThematicId(), giftVoucher.getEndOfValidity());
      Edition edition = new Edition(partner, thematic, giftVoucher.getEndOfValidity(),
              PartnersUtils.getTipoIVA(repayment), giftVoucher.getWord(), repayment.getId());
      if (records.containsKey(edition)) {
        records.put(edition, records.get(edition) + 1);
      } else {
        records.put(edition, 1);
      }
    }
    
    float totalAmount = 0;
    float totalCommissionVAT = 0;

    for (Entry<Edition, Integer> entry : records.entrySet()) {
      Edition edition = entry.getKey();
      int amount = entry.getValue();

      double commision, commisionET, vatOnCommision, finalRepayment;

      boolean conIva = reembolsoConIva(partner, repayment);
      boolean conIva2013 = (partner.getOpciones() == 1);
      
      // Antes de nada recalcula la comisi�n en funci�n de si al colaborador se le aplica IVA o no.
      if (!conIva && conIva2013) {
        // Se le aplica IVA
        commisionET = edition.getCommissionET();
        vatOnCommision = commisionET * VAT21;
        commision = commisionET * (1 + VAT21);
      } else {
        // No se le aplica IVA
        commision = edition.getCommission();
        vatOnCommision = edition.getVATOnCommission();
        commisionET = edition.getCommission();
      }
      finalRepayment = edition.getValueFace() - commision;

      totalAmount += amount * edition.getValueFace();
      addToTotal(totals, "amount", amount);
      addToTotal(totals, "total_amount", amount * edition.getValueFace());
      addToTotal(totals, "total_commission_ET", amount * commisionET);
      if (conIva) {
        totalCommissionVAT += amount * commisionET * (1+VAT21);
        addToTotal(totals, "total_commission_ET_VAT", amount * commisionET * (1+VAT21));
      } else {
        totalCommissionVAT += amount * commisionET;
        addToTotal(totals, "total_commission_ET_VAT", amount * commisionET);
      }
      
      addToTotal(totals, "total_VAT_on_commission", amount * vatOnCommision);
      addToTotal(totals, "total_commission_TCC", amount * commision);
      addToTotal(totals, "total_repayment_by_thematic", amount * finalRepayment);
      
    }
    totals.put("total_repayment_by_thematic_VAT", totalAmount - totalCommissionVAT);

    return totals;
  }

  private static Properties initTotalsForDetailedCreditNote() {
    Properties totals = new Properties();
    totals.put("amount", 0);
    totals.put("total_amount", (double) 0);
    totals.put("total_commission_ET", (double) 0);
    totals.put("total_commission_ET_VAT", (double) 0);
    totals.put("total_VAT_on_commission", (double) 0);
    totals.put("total_commission_TCC", (double) 0);
    totals.put("total_repayment_by_thematic", (double) 0);

    return totals;
  }

  private static double round(double value, int decimalPlace) {
    BigDecimal bigDecimal = new BigDecimal(value);
    bigDecimal = bigDecimal.setScale(decimalPlace, RoundingMode.HALF_UP);
    return bigDecimal.doubleValue();
  }

  @Override
  public ActionForward executeChild(ActionMapping mapping, ActionForm baseForm,
          HttpServletRequest request, HttpServletResponse response) {

    Repayment repayment = PartnersServices.createRepaymentService().getRepaymentForId(
            Integer.parseInt(request.getParameter("repayment_id")));

    Partner partner = PartnersServices.createPartnerService().getPartnerForId(repayment.getPartnerId());

    if (PartnersUtils.getTipoIVA(repayment) == 21) {
      request.setAttribute("doc_unico", "true");
    }

    request.setAttribute("repayment", repayment);
    request.setAttribute("partner", partner);
    request.setAttribute("accounting_data", generateFinancialData(partner, repayment));

    return mapping.findForward("success");
  }

  @Override
  public ActionForward doExecute(ActionMapping mapping, DynaValidatorForm form, HttpServletRequest request, HttpServletResponse response) throws PartnersException {
    return null;
  }
}

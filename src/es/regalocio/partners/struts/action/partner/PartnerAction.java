package es.regalocio.partners.struts.action.partner;

import es.regalocio.partners.business.common.Address;
import es.regalocio.partners.business.common.Contact;
import es.regalocio.partners.business.common.Partner;
import es.regalocio.partners.business.common.RIB;
import es.regalocio.partners.struts.action.DynaValidatorFormAction;
import es.regalocio.partners.utils.StrUtils;
import org.apache.struts.validator.DynaValidatorForm;

public abstract class PartnerAction extends DynaValidatorFormAction {

  private static Address createAddress(DynaValidatorForm form) {
    StrUtils strUtils = StrUtils.getInstance();
    return new Address(
            strUtils.strFmt(form.getString("address").toUpperCase()),
            strUtils.strFmt(form.getString("further_address").toUpperCase()),
            form.getString("postal_code"),
            strUtils.strFmt(form.getString("town").toUpperCase()));
  }

  private static Contact createContact(DynaValidatorForm form) {
    StrUtils strUtils = StrUtils.getInstance();
    return new Contact((Integer) form.get("salutation_code"),
            strUtils.capitalizeFirstOnly(form.getString("firstname")),
            strUtils.capitalizeFirstOnly(form.getString("lastname")));
  }

  private static RIB createRib(DynaValidatorForm form) {
    return new RIB(
            form.getString("domiciliation").toUpperCase(),
            form.getString("bank_code"),
            form.getString("agency_code"),
            form.getString("key"),
            form.getString("account_number").toUpperCase(),
            form.getString("iban").toUpperCase());
  }

  protected Partner createPartner(DynaValidatorForm form) {
    StrUtils strUtils = StrUtils.getInstance();

    int opciones = 0;
    try {
      opciones = (form.get("opciones") != null) ? (Integer) form.get("opciones") : 0;
    } catch (java.lang.IllegalArgumentException e) {
    }
    int opciones2 = 0;
    try {
      opciones2 = (form.get("opciones2") != null) ? (Integer) form.get("opciones2") : 0;
    } catch (java.lang.IllegalArgumentException e) {
    }
    
    int groupalAgreement = 0;
    try {
      groupalAgreement = (form.get("groupal_agreement_id") != null) ? (Integer) form.get("groupal_agreement_id") : 0;
    } catch (java.lang.IllegalArgumentException e) {
    }

    Partner partner = new Partner(
            strUtils.strFmt(form.getString("name").toUpperCase()),
            strUtils.strFmt(form.getString("code")),
            strUtils.phoneFmt(form.getString("phone")),
            strUtils.phoneFmt(form.getString("phone2")),
            strUtils.phoneFmt(form.getString("fax")),
            strUtils.strFmt(form.getString("email").toLowerCase()),
            strUtils.strFmt(form.getString("cif").toUpperCase()),
            strUtils.strFmt(form.getString("factname")),
            opciones, opciones2, groupalAgreement);

    partner.setContact(createContact(form));
    partner.setAddress(createAddress(form));

    if (strUtils.strFmt(form.getString("key")) != null || strUtils.strFmt(form.getString("iban")) != null) {
      partner.setRib(createRib(form));
    }
    return partner;
  }
}

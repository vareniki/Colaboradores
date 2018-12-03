package es.regalocio.partners.struts.action.distributionnetwork;

import es.regalocio.partners.business.common.Address;
import es.regalocio.partners.business.common.DistributionNetwork;
import es.regalocio.partners.struts.action.DynaValidatorFormAction;
import es.regalocio.partners.utils.StrUtils;
import org.apache.struts.validator.DynaValidatorForm;

public abstract class DistributionNetworkAction extends DynaValidatorFormAction {

  private static Address createAddress(DynaValidatorForm form) {
    StrUtils strUtils = StrUtils.getInstance();
    if (strUtils.strFmt(form.getString("town")) == null
            || strUtils.strFmt(form.getString("postal_code")) == null) {
      return null;
    }
    Address address = new Address(
            strUtils.strFmt(form.getString("address").toUpperCase()),
            strUtils.strFmt(form.getString("further_address").toUpperCase()),
            form.getString("postal_code"), strUtils.strFmt(form.getString("town").toUpperCase()));
    if (form.get("address_id") != null && (Integer) form.get("address_id") > 0) {
      address.setId((Integer) form.get("address_id"));
    }

    return address;
  }

  private static Address createFactaddress(DynaValidatorForm form) {
    StrUtils strUtils = StrUtils.getInstance();
    if (strUtils.strFmt(form.getString("fact_town")) == null
            || strUtils.strFmt(form.getString("fact_postal_code")) == null) {
      return null;
    }

    Address address = new Address(
            strUtils.strFmt(form.getString("fact_address").toUpperCase()),
            strUtils.strFmt(form.getString("fact_further_address").toUpperCase()),
            form.getString("fact_postal_code"), strUtils.strFmt(form.getString("fact_town").toUpperCase()));

    if (form.get("factaddress_id") != null && (Integer) form.get("factaddress_id") > 0) {
      address.setId((Integer) form.get("factaddress_id"));
    }
    return address;
  }

  protected DistributionNetwork createDistributionNetwork(DynaValidatorForm form) {

    StrUtils strUtils = StrUtils.getInstance();
        
    DistributionNetwork distributionNetwork
            = new DistributionNetwork(
                    strUtils.strFmt(
                            form.getString("name").toUpperCase()), form.getString("cif"),
                    createAddress(form), createFactaddress(form),
                    (form.get("inherit_address") != null),
                    (form.get("inherit_factaddress") != null),
                    (form.get("disable_changepassword") != null),
                    (Boolean) form.get("gift_voucher_activation_on_sale"), true, 
                    (form.get("with_iva") != null));

    return distributionNetwork;
  }
}

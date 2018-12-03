package es.regalocio.partners.struts.action.distributionnetwork;

import es.regalocio.partners.business.common.*;
import es.regalocio.partners.business.services.EntityOfDistributionService;
import es.regalocio.partners.config.PartnersServices;
import es.regalocio.partners.config.PartnersUtils;
import es.regalocio.partners.struts.action.DynaValidatorFormAction;
import es.regalocio.partners.utils.StrUtils;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.DynaValidatorForm;

public abstract class RetailOutletAction extends DynaValidatorFormAction {

  private static final StrUtils strUtils = StrUtils.getInstance();
  public static final String UNDEFINED_VALUE = "undefined";

  private static Address createAddress(DynaValidatorForm form) {
    return new Address(
            strUtils.strFmt(form.getString("address").toUpperCase()),
            strUtils.strFmt(form.getString("further_address").toUpperCase()),
            form.getString("postal_code"), strUtils.strFmt(form.getString("town").toUpperCase()));
  }

  private static Contact createContact(DynaValidatorForm form) {
    if (GenericValidator.isBlankOrNull(form.getString("lastname"))) {
      return null;
    }
    return new Contact(
            (Integer) form.get("salutation_code"),
            strUtils.capitalizeFirstOnly(form.getString("firstname")),
            strUtils.capitalizeFirstOnly(form.getString("lastname")));
  }

  private static Address createFactaddress(DynaValidatorForm form, Address address) {
    if (strUtils.strFmt(form.getString("fact_postal_code")) == null
            || strUtils.strFmt(form.getString("fact_town")) == null) {

      if (address == null) {
        return null;
      }
      return new Address(
              address.getAddress(), address.getFurtherAddress(), address.getPostalCode(), address.getTown());
    }

    String factAddress = strUtils.strFmt(form.getString("fact_address"));
    if (factAddress != null) {
      factAddress = factAddress.toUpperCase();
    }

    String factFurtherAddress = strUtils.strFmt(form.getString("fact_further_address"));
    if (factFurtherAddress != null) {
      factFurtherAddress = factFurtherAddress.toUpperCase();
    }

    return new Address(
            factAddress, factFurtherAddress,
            form.getString("fact_postal_code"), form.getString("fact_town").toUpperCase());
  }

  @Override
  public void initValues(ActionMapping mapping, DynaValidatorForm form, HttpServletRequest request) {

    EntityOfDistributionService service = PartnersServices.createDistributionService();
    DistributionNetwork distributionNetwork = service.getDistributionNetwork((Integer) form.get("distribution_network_id"));
    request.setAttribute("distribution_network", distributionNetwork);
    // Hereda datos de facturación
    if (distributionNetwork.isInheritFactaddress() && distributionNetwork.getFactaddress() != null) {
      if (strUtils.strFmt(form.getString("fact_postal_code")) == null
              || strUtils.strFmt(form.getString("fact_town")) == null) {

        form.set("fact_postal_code", distributionNetwork.getFactaddress().getPostalCode());
        form.set("fact_town", distributionNetwork.getFactaddress().getTown());
        form.set("fact_address", distributionNetwork.getFactaddress().getAddress());
        form.set("fact_further_address", distributionNetwork.getFactaddress().getFurtherAddress());
        form.set("cif", distributionNetwork.getCif());
        form.set("name", distributionNetwork.getName());
        form.set("factname", distributionNetwork.getName());
      }
    }
    // Hereda datos de env�o
    if (distributionNetwork.isInheritAddress() && distributionNetwork.getAddress() != null) {
      if (strUtils.strFmt(form.getString("postal_code")) == null
              || strUtils.strFmt(form.getString("town")) == null) {

        form.set("postal_code", distributionNetwork.getAddress().getPostalCode());
        form.set("town", distributionNetwork.getAddress().getTown());
        form.set("address", distributionNetwork.getAddress().getAddress());
        form.set("further_address", distributionNetwork.getAddress().getFurtherAddress());
      }
    }

    PartnersUtils partnersUtils = PartnersUtils.getInstance();
    request.setAttribute("salutation", partnersUtils.getTypologyList("salutation"));

    Profile profileToRemoved = null;
    List<Profile> profileList = partnersUtils.getProfileList(UserType.RETAIL_OUTLET_USER_TYPE);
    for (Profile profile : profileList) {
      if (profile.getCode() == StaticDefinition.RETAIL_OUTLET_PROFILE_CODE) {
        profileToRemoved = profile;
        break;
      }
    }
    profileList.remove(profileToRemoved);
    request.setAttribute("profile_list", profileList);
  }

  @SuppressWarnings("ManualArrayToCollectionCopy")
  protected ProfileList createProfileList(DynaValidatorForm form) {

    ProfileList profileList = new ProfileList();
    profileList.add(StaticDefinition.RETAIL_OUTLET_PROFILE_CODE);
    Integer[] profileCodeArray = (Integer[]) form.get("profile_code_array");

    for (Integer profileCode : profileCodeArray) {
      profileList.add(profileCode);
    }

    return profileList;
  }

  protected RetailOutlet createRetailOutlet(DynaValidatorForm form) {

    String giftVoucherActivationOnSale = form.getString("gift_voucher_activation_on_sale");
    EntityOfDistributionService service = PartnersServices.createDistributionService();
    DistributionNetwork distributionNetwork = service.getDistributionNetwork((Integer) form.get("distribution_network_id"));

    Address address = createAddress(form);
    if (strUtils.strFmt(form.getString("factname")) == null) {
      form.set("factname", form.getString("name"));
    }
    
    Object obj = form.get("order_platform_id");
    Integer orderPlatformId;
    if (obj != null) {
      orderPlatformId = (Integer) obj;
    } else {
      orderPlatformId = null;
    }
    
    obj = form.get("send_last_guides");
    Integer sendLastGuides;
    if (obj != null) {
      sendLastGuides = (Integer) obj;
    } else {
      sendLastGuides = null;
    }

    return new RetailOutlet(
            strUtils.strFmt(form.getString("name").toUpperCase()),
            strUtils.phoneFmt(form.getString("phone")),
            strUtils.phoneFmt(form.getString("fax")),
            strUtils.strFmt(form.getString("email").toLowerCase()),
            strUtils.strFmt(form.getString("cif")),
            strUtils.strFmt(form.getString("factname")),
            (giftVoucherActivationOnSale.equals(UNDEFINED_VALUE)) ? null : Integer.valueOf(giftVoucherActivationOnSale),
            distributionNetwork, address, createFactaddress(form, address), createContact(form), 
            createProfileList(form), orderPlatformId, sendLastGuides);
  }
}

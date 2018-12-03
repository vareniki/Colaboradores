package es.regalocio.partners.struts.action.admin;

import es.regalocio.partners.business.common.InternalUser;
import es.regalocio.partners.business.common.ProfileList;
import es.regalocio.partners.business.common.UserType;
import es.regalocio.partners.config.PartnersUtils;
import es.regalocio.partners.struts.action.DynaValidatorFormAction;
import es.regalocio.partners.utils.StrUtils;
import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.DynaValidatorForm;

public abstract class UserAction extends DynaValidatorFormAction {

  @Override
  public void initValues(ActionMapping mapping, DynaValidatorForm form, HttpServletRequest request) {
    request.setAttribute("profile_list", PartnersUtils.getInstance().getProfileList(UserType.INTERNAL_USER_TYPE));
  }

  protected boolean noProfileChecked(DynaValidatorForm form) {
    Integer[] profileCodeArray = (Integer[]) form.get("profile_code_array");
    return (profileCodeArray == null) || (profileCodeArray.length == 0);
  }

  protected InternalUser createUser(DynaValidatorForm form) {
    StrUtils strUtils = StrUtils.getInstance();

    InternalUser user = new InternalUser(strUtils.strFmt(
            form.getString("login")),
            strUtils.capitalizeFirstOnly(form.getString("firstname")),
            strUtils.capitalizeFirstOnly(form.getString("lastname")),
            strUtils.strFmt(form.getString("email").toLowerCase()));

    user.getProfiles().addAll(createProfileList(form));

    return user;
  }

  private static ProfileList createProfileList(DynaValidatorForm form) {
    ProfileList profileList = new ProfileList();
    Integer[] profileCodeArray = (Integer[]) form.get("profile_code_array");
    profileList.addAll(Arrays.asList(profileCodeArray));

    return profileList;
  }
}

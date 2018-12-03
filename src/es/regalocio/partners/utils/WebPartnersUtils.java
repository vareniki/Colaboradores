package es.regalocio.partners.utils;

import es.regalocio.partners.business.common.RetailOutlet;
import es.regalocio.partners.config.PartnersUtils;
import es.regalocio.partners.shared.SessionInfo;
import java.util.MissingResourceException;

public class WebPartnersUtils {

  private WebPartnersUtils() {
  }

  public static boolean isExpedientNumberAvailable(SessionInfo sessionInfo) {
    boolean expedientNumber = false;

    if (sessionInfo != null && sessionInfo.getUser() instanceof RetailOutlet) {
      String[] netsId = null;
      try {
        netsId = PartnersUtils.getInstance().getStrProperty("distribution_network.expedient_number").split(",");
      } catch (MissingResourceException e) {
      }
      if (netsId != null) {
        int id = ((RetailOutlet) sessionInfo.getUser()).getDistributionNetwork().getId();
        for (String netId : netsId) {
          if (netId.indexOf("|") > 0) {
            netId = netId.substring(0, netId.indexOf("|"));
          }
          if (netId.equals("" + id)) {
            expedientNumber = true;
            break;
          }
        }
      }
    }
    return expedientNumber;
  }
}

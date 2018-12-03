package es.regalocio.partners.struts;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.tiles.TilesRequestProcessor;

public class UserRequestProcessor extends TilesRequestProcessor {

  @Override
  protected boolean processRoles(HttpServletRequest request,
          HttpServletResponse response, ActionMapping mapping) throws IOException, ServletException {
    return true;
  }
}

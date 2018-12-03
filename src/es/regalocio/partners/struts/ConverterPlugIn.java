package es.regalocio.partners.struts;

import javax.servlet.ServletException;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.*;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.action.PlugIn;
import org.apache.struts.config.ModuleConfig;

public class ConverterPlugIn implements PlugIn {

  @Override
  public void destroy() {
  }

  @Override
  public void init(ActionServlet actionServlet, ModuleConfig moduleConfig) throws ServletException {
    ConvertUtils.register(new LongConverter(null), java.lang.Long.class);
    ConvertUtils.register(new IntegerConverter(null), java.lang.Integer.class);
    ConvertUtils.register(new ShortConverter(null), java.lang.Short.class);
    ConvertUtils.register(new DoubleConverter(null), java.lang.Double.class);
    ConvertUtils.register(new FloatConverter(null), java.lang.Float.class);
  }
}

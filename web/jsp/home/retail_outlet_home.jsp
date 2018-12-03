<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://www.cofrevip.com/partners/tags-app.tld" prefix="app" %>
<%@ page import="es.regalocio.partners.business.common.RetailOutlet" %>
<%@ page import="es.regalocio.partners.shared.SessionInfo" %>

<bean:define id="user" name="SESSION_INFO" property="user" />
<bean:define id="personalInfo" name="SESSION_INFO" property="personal" />
<%
  int aviso = 0;

  RetailOutlet ro = (RetailOutlet) ((SessionInfo) session.getAttribute(SessionInfo.SESSION_INFO)).getUser();
  Integer dist[] = {27, 31, 115, 116, 203, 214, 5119, 5220, 5224, 5239, 5242, 5248, 5304, 5386, 6083, 6455, 6529, 6817, 6862, 7147, 7779, 7834, 7922, 8391};
  for (Integer i : dist) {
    if (i == ro.getDistributionNetwork().getEntityId()) {
      aviso = 1;
      break;
    }
  }

  if (ro.getDistributionNetwork().getEntityId() == 5827) {
    aviso = 5827;
  }
%>
<div class="page-header">
  <h1>Espacio Colaboradores <small>A trav&eacute;s de este espacio pueden:</small></h1>
</div>
<ul class="list-group">
  <app:inprofiles profiles="13">
    <li class="list-group-item">Registrar las devoluciones de los clientes a sus puntos de venta.</li>
    </app:inprofiles>
    <app:inprofiles profiles="10">
    <li class="list-group-item">Activar los cheques regalo en el momento de sus ventas, a fin de hacer que &eacute;stos sean utilizables.</li>
    <li class="list-group-item">Comprobar el hist&oacute;rico de sus ventas.</li>
    </app:inprofiles>
</ul>
<div class="alert alert-info" role="alert">
  <p>Para obtener la informaci&oacute;n que necesite, puede contactar con nosotros al tel&eacute;fono <strong>902 090 336</strong>.</p>
  <logic:notEmpty name="personalInfo" property="homeText">
    <p><bean:write name="personalInfo" property="homeText" filter="no" /></p>
  </logic:notEmpty>
  <% if (aviso == 1) { %>
  <p>Descargue la operativa pinchando siguiente enlace:
    <a href="http://www.cofrevip.com/content/docs/operativa-distribuidores-2017.pdf" target="_blank">OPERATIVA DISTRIBUIDORES 2017</a>
  </p>
  <% } else if (aviso == 5827) { %>
  <p>Descargue la operativa pinchando siguiente enlace:
    <a href="http://www.cofrevip.com/content/docs/operativa-franquicias-carrefour-2015.pdf" target="_blank">OPERATIVA FRANQUICIAS CARREFOUR 2015</a>
  </p>
  <% }%>
</div>
<hr>
<div class="alert alert-info" role="alert">
  Estimado Distribuidor,<br><br>
  
 Os informamos que a partir del 1 de mayo emitiremos nuestras auto-facturas aplicando IVA sobre su comisión, 
 en aplicaci&oacute;n de la Consulta Vinculante reci&eacute;n publicada por la DGT (num de consulta V4588-16, normativa Ley 37/1192 arts.8,11,94-uno), 
 relativa a las cajas de experiencias. Seg&uacute;n dicha consulta, existe un cambio de criterio de la DGT v&aacute;lido para todos los distribuidores 
 de cajas de experiencias acerca de la tributaci&oacute;n de las comisiones de intermediaci&oacute;n. <br><br>

 Quedamos a su disposición para cualquier aclaración al respecto.<br>
Agradeciendo de antemano su comprensi&oacute;n, les saludamos atentamente.  
</div>
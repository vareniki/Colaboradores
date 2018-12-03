<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://www.cofrevip.com/partners/tags-app.tld" prefix="app" %>
<%@ page import="es.regalocio.partners.shared.SessionInfo" %>
<%@ page import="es.regalocio.partners.business.common.Partner" %>

<bean:define id="user" name="SESSION_INFO" property="user" />
<bean:define id="personalInfo" name="SESSION_INFO" property="personal" />
<app:information />
<div class="page-header">
  <h1>Espacio Colaboradores <small>A trav&eacute;s de este espacio pueden:</small></h1>
</div>
<div class="row">
  <div class="col-xs-6">
    <ul class="list-group">
      <li class="list-group-item"><html:link action="/partner_update_our_informations">Comprobar sus datos fiscales.</html:link></li>
      <li class="list-group-item"><html:link action="/gift_voucher_status">Controlar la validez de los cheques.</html:link></li>
    </ul>
  </div>
  <div class="col-xs-6">
    <ul class="list-group">
      <li class="list-group-item"><html:link action="/add_repayment">Solicitar un reembolso.</html:link></li>
      <li class="list-group-item"><html:link action="/list_of_repayments">Listar reembolsos.</html:link></li>
    </ul>
  </div>
</div>
<logic:notEmpty name="extraInvoices">
<div class="alert alert-info" role="alert">
  Estimado Colaborador,<br><br>

Emitimos las correspondientes facturas rectificativas de IVA relativa a las comisiones entre
enero y abril (desde  01/01/2017) aplicando la Consulta Vinculante publicada por la DGT 
(num de consulta V4588-16, normativa Ley 37/1192 arts.8,11,94-uno).
Esta consulta, reci&eacute;n publicada acerca de las cajas de experiencias; 
m&aacute;s concretamente, acerca de la tributaci&oacute;n de las comisiones de 
intermediaci&oacute;n, nos dice que existe un cambio de criterio de la DGT v&aacute;lido 
para todos los proveedores de cajas de experiencias y que dichas comisiones 
deber&iacute;an ahora llevar IVA.  El importe del IVA descontado, lo recuperar&eacute;is 
en vuestra pr&oacute;xima liquidaci&oacute;n del IVA con la presentaci&oacute;n 
de la presente factura, ya que como sabr&eacute;is el IVA es un impuesto neutro 
para las empresas, y no afecta a vuestra rentabilidad con nuestros cheques.<br><br>

¿C&oacute;mo contabilizar est&aacute; factura?<br><br>

Para contabilizar correctamente estas facturas, quitar el importe de las comisiones 
anteriormente contabilizadas sin IVA y contabilizar estas facturas con el IVA correspondiente.<br><br>

Quedamos a su disposici&oacute;n para cualquier aclaraci&oacute;n al respecto. <br>
Agradeciendo de antemano su comprensi&oacute;n, les saludamos atentamente.
</div>
<hr>
</logic:notEmpty>
<logic:notEmpty name="extraInvoice1">
<p class="text-center"><a target="_blank" href="http://www2.cofrevip.com/facturas/factura.php?id_factura=<bean:write name='extraInvoice1' property='numeroFactura' />&id_colaborador=<bean:write name='extraInvoice1' property='colaboradorId' />">Descargar factura rectificativa <bean:write name='extraInvoice1' property='descripcionFactura' /></a></p>
<hr>
</logic:notEmpty>
<logic:notEmpty name="extraInvoice2">
<p class="text-center"><a target="_blank" href="http://www2.cofrevip.com/facturas/factura-abril.php?id_factura=<bean:write name='extraInvoice2' property='numeroFactura' />&id_colaborador=<bean:write name='extraInvoice2' property='colaboradorId' />">Descargar factura rectificativa <bean:write name='extraInvoice2' property='descripcionFactura' /></a></p>
<hr>
</logic:notEmpty>
<logic:notEmpty name="extraInvoice3">
<p class="text-center"><a target="_blank" href="http://www2.cofrevip.com/facturas/factura-mayo.php?id_factura=<bean:write name='extraInvoice3' property='numeroFactura' />&id_colaborador=<bean:write name='extraInvoice3' property='colaboradorId' />">Descargar factura rectificativa <bean:write name='extraInvoice3' property='descripcionFactura' /></a></p>
<hr>
</logic:notEmpty>
<logic:notEmpty name="extraInvoice4">
<p class="text-center"><a target="_blank" href="http://www2.cofrevip.com/facturas/factura-junio.php?id_factura=<bean:write name='extraInvoice4' property='numeroFactura' />&id_colaborador=<bean:write name='extraInvoice4' property='colaboradorId' />">Descargar factura rectificativa <bean:write name='extraInvoice4' property='descripcionFactura' /></a></p>
<hr>
</logic:notEmpty>
<logic:notEmpty name="extraInvoice5">
<p class="text-center"><a target="_blank" href="http://www2.cofrevip.com/facturas/factura-julio.php?id_factura=<bean:write name='extraInvoice5' property='numeroFactura' />&id_colaborador=<bean:write name='extraInvoice5' property='colaboradorId' />">Descargar factura rectificativa <bean:write name='extraInvoice5' property='descripcionFactura' /></a></p>
<hr>
</logic:notEmpty>
<logic:notEmpty name="extraInvoice6">
<p class="text-center"><a target="_blank" href="http://www2.cofrevip.com/facturas/factura-agosto.php?id_factura=<bean:write name='extraInvoice6' property='numeroFactura' />&id_colaborador=<bean:write name='extraInvoice6' property='colaboradorId' />">Descargar factura rectificativa <bean:write name='extraInvoice6' property='descripcionFactura' /></a></p>
<hr>
</logic:notEmpty>
<logic:notEmpty name="extraInvoice7">
<p class="text-center"><a target="_blank" href="http://www2.cofrevip.com/facturas/factura-septiembre.php?id_factura=<bean:write name='extraInvoice7' property='numeroFactura' />&id_colaborador=<bean:write name='extraInvoice7' property='colaboradorId' />">Descargar factura rectificativa <bean:write name='extraInvoice7' property='descripcionFactura' /></a></p>
<hr>
</logic:notEmpty>

<div class="alert alert-danger">
  Estimados colaboradores,<br><br>
  Recordaros que la colecci&oacute;n 2014 caduca el 31 de marzo de 2017, y desde esa fecha no se pueden aceptar reservas de clientes con estos cofres.<br><br>
No obstante, los cheques correspondientes a las colecciones 2015 con validez hasta el 31 de marzo de 2018 y 2016 con validez hasta el 31 de marzo de 2019 siguen en vigor y se deben seguir aceptando.
</div>
<div class="alert alert-info" role="alert">
  Para cualquier informaci&oacute;n adicional, pueden contactar con nosotros en el <strong>902 090 336</strong>.<hr>
  MUY IMPORTANTE: descargue la operativa aquÌ:
  <a href="http://www.cofrevip.com/content/docs/operativa-colaboradores-2016.pdf" target="_blank">OPERATIVA COLABORADORES</a>
</div>
<%
  Partner pa = (Partner) ((SessionInfo) session.getAttribute(SessionInfo.SESSION_INFO)).getUser();
  if (pa.getId() == 9278) { %>
<p>HOTEL IPV BEATRIZ PALACE PARTICIPA EN LOS SIGUIENTES COFRES DE ALOJAMIENTO:</p>
<ul class="list-group">
  <li class="list-group-item">Escapada Rom&aacute;tica</li>
  <li class="list-group-item">Best Of</li>
  <li class="list-group-item">Un D&iacute;a Perfecto</li>
  <li class="list-group-item">Estancia Gourmet</li>
  <li class="list-group-item">Estancia Zen</li>
  <li class="list-group-item">3 d&iacute;as Exclusivos</li>
</ul>
<% } %>
<hr>
<div class="alert alert-warning">
  <p>La aplicaci&oacute;n para colaboradores CofreVIP ha sido actualizada. En caso de incidencias, por favor, contacte con 
    <a href="mailto:soporte@cofrevip.com">soporte@cofrevip.com</a>. Si desea acceder a la aplicaci&oacute;n antigua podr&aacute; hacerlo a trav&eacute;s
    de <a href="http://www2.cofrevip.com/colaboradores2">CofreVIP.com/colaboradores2</a></p>
</div>


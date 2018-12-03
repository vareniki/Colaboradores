<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.cofrevip.com/partners/tags-app.tld" prefix="app" %>
<html:xhtml />

<app:information />
<div class="row">
  <div class="col-xs-2"><strong>Nombre:</strong></div>
  <div class="col-xs-4"><bean:write name="partner" property="name"/></div>
  <div class="col-xs-2"><strong>Tel&eacute;fono 1:</strong></div>
  <div class="col-xs-4"><bean:write name="partner" property="phone"/></div>
</div>
<div class="row">
  <div class="col-xs-2"><strong>C&oacute;digo:</strong></div>
  <div class="col-xs-4"><bean:write name="partner" property="code"/></div>
  <div class="col-xs-2"><strong>Tel&eacute;fono 2:</strong></div>
  <div class="col-xs-4"><bean:write name="partner" property="phone2"/></div>
</div>
<div class="row">
  <div class="col-xs-2"><strong>Email:</strong></div>
  <div class="col-xs-4"><a href='mailto:<bean:write name="partner" property="email"/>'><bean:write name="partner" property="email"/></a></div>
  <div class="col-xs-2"><strong>Fax:</strong></div>
  <div class="col-xs-4"><bean:write name="partner" property="fax"/></div>
</div>
<hr>
<div class="row">
  <div class="col-xs-4"><strong>Contacto:</strong></div>
  <div class="col-xs-8"><bean:write name="contact" /></div>
</div>
<div class="row">
  <div class="col-xs-4"><strong>Denominaci&oacute;n social:</strong></div>
  <div class="col-xs-8"><bean:write name="partner" property="factname" /></div>
</div>
<div class="row">
  <div class="col-xs-4"><strong>CIF:</strong></div>
  <div class="col-xs-8"><bean:write name="partner" property="cif" /></div>
</div>

<div class="row">
  <div class="col-xs-4"><strong>Direcci&oacute;n:</strong></div>
  <div class="col-xs-8"><bean:write name="address" filter="false" /></div>
</div>
<hr>
<div class="row">
  <div class="col-xs-4"><strong>Datos bancarios:</strong></div>
  <div class="col-xs-8">
    <logic:empty name="partner" property="rib">No existen datos bancarios</logic:empty>
    <logic:notEmpty name="partner" property="rib">
      <bean:define id="rib" name="partner" property="rib" />
      <logic:notEmpty name="rib" property="accountNumber">
        <bean:write name="rib" property="bankCode"/>-
        <bean:write name="rib" property="agencyCode"/>-
        <bean:write name="rib" property="key"/>-
        <bean:write name="rib" property="accountNumber"/> (<bean:write name="rib" property="domiciliation"/>)<br>
      </logic:notEmpty>
      <logic:notEmpty name="rib" property="iban"><strong>IBAN:</strong> <bean:write name="rib" property="iban"/></logic:notEmpty>
    </logic:notEmpty>
    
  </div>
</div>
        <div class="row">
      <div class="col-xs-4"><strong>Grupo de comisiones:</strong></div>
      <div class="col-xs-8"><bean:write name="groupalAgreement" /></div>
    </div>
<div class="row">
  <div class="col-xs-4"><strong>Facturaci&oacute;n e IVA:</strong></div>
  <div class="col-xs-8">
    <logic:equal name="partner" property="opciones" value="0">
      <p>A este colaborador NO se le aplica IVA en la comisi&oacute;n.</p>
    </logic:equal>
    <logic:equal name="partner" property="opciones" value="1">
      <p>A este colaborador S&Iacute; se le aplica IVA en la comisi&oacute;n.</p>
    </logic:equal>

    <logic:equal name="partner" property="opciones2" value="0">
      <p>A este colaborador NO se le aplica IVA en las facturas a partir de mayo de 2017.</p>
    </logic:equal>
    <logic:notEqual name="partner" property="opciones2" value="0">
      <p>A este colaborador S&Iacute; se le aplica IVA en las facturas a partir de <bean:write name="partner" property="opciones2" />.</p>
    </logic:notEqual>

    <logic:notEqual name="readOnly" value="true">
      <hr><html:link action="/add_repayment" paramName="partner" paramProperty="id" paramId="partner_id" styleClass="btn btn-default btn-sm"><span class="glyphicon glyphicon-plus-sign"></span> A&ntilde;adir <strong>reembolso</strong> a este colaborador</html:link>
      &nbsp;&nbsp;<html:link action="/update_partner" paramName="partner" paramProperty="id" paramId="partner_id" styleClass="btn btn-default btn-sm"><span class="glyphicon glyphicon-pencil"></span> Modificar datos del colaborador</html:link>
    </logic:notEqual>
  </div>
</div>

<hr>
<h4 class="text-center">Condiciones particulares de comisiones</h4>
<hr>
<logic:notEmpty name="partner" property="specificAgreementList">
  <tiles:insert attribute="list_of_specific_agreements"/>
</logic:notEmpty>
<logic:empty name="partner" property="specificAgreementList">
  <div>No existen condiciones particulares registradas para este colaborador</div>
</logic:empty>
<logic:notEqual name="readOnly" value="true">
  <hr>
  <div class="text-right">
    <html:link action="/add_specific_agreement" paramName="partner" paramProperty="id" paramId="partner_id" styleClass="btn btn-default btn-sm"><span class="glyphicon glyphicon-plus-sign"></span> A&ntilde;adir condiciones particulares</html:link>
  </div>
</logic:notEqual>
<br>
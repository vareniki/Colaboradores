<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.cofrevip.com/partners/tags-app.tld" prefix="app" %>
<html:xhtml />
<app:information />
<logic:iterate id="current" name="retailOutletForm" property="profile_code_array">
  <input type="hidden" name="profile_code_array" value="<bean:write name="current" />">
</logic:iterate>
<div class="row">
  <div class="col-sm-2 text-right"><strong>Denominaci&oacute;n:</strong></div>
  <div class="col-sm-4"><bean:write name="retailOutletForm" property="name"/></div>
  <div class="col-sm-2 text-right"><strong>Red:</strong></div>
  <div class="col-sm-4"><bean:write name="distribution_network" property="name"/></div>
</div>
<div class="row">
  <div class="col-sm-2 text-right"><strong>Identificaci&oacute;n:</strong></div>
  <div class="col-sm-4"><bean:write name="identification_code"/></div>
  <div class="col-sm-2 text-right"><strong>Tel&eacute;fono:</strong></div>
  <div class="col-sm-4"><bean:write name="retailOutletForm" property="phone" /></div>
</div>

<div class="row">
  <div class="col-sm-2 text-right"><strong>Email:</strong></div>
  <div class="col-sm-4"><bean:write name="retailOutletForm" property="email" /></div>
  <div class="col-sm-2 text-right"><strong>Fax:</strong></div>
  <div class="col-sm-4"><bean:write name="retailOutletForm" property="fax" /></div>
</div>
<div class="row">
  <div class="col-sm-2 text-right"><strong>Direcci&oacute;n:</strong></div>
  <div class="col-sm-10"><bean:write name="retailOutletForm" property="firstname" /> <bean:write name="retailOutletForm" property="lastname" /></div>
</div>
<hr><h5 class="text-center text-uppercase">Direcci&oacute;n de entrega</h5><hr>
<div class="row">
  <div class="col-sm-2 text-right"><strong>Direcci&oacute;n:</strong></div>
  <div class="col-sm-4"><bean:write name="retailOutletForm" property="address" /></div>
  <div class="col-sm-6"><bean:write name="retailOutletForm" property="further_address" /></div>
</div>
<div class="row">
  <div class="col-sm-2 text-right"><strong>C. Postal:</strong></div>
  <div class="col-sm-4"><bean:write name="retailOutletForm" property="postal_code" /></div>
  <div class="col-sm-2 text-right"><strong>Poblaci&oacute;n:</strong></div>
  <div class="col-sm-4"><bean:write name="retailOutletForm" property="town" /></div>
</div>
<hr><h5 class="text-center text-uppercase">Datos de facturaci&oacute;n</h5><hr>
<div class="row">
  <div class="col-sm-2 text-right"><strong>Direcci&oacute;n:</strong></div>
  <div class="col-sm-4"><bean:write name="retailOutletForm" property="fact_address" /></div>
  <div class="col-sm-6"><bean:write name="retailOutletForm" property="fact_further_address" /></div>
</div>
<div class="row">
  <div class="col-sm-2 text-right"><strong>C. Postal:</strong></div>
  <div class="col-sm-4"><bean:write name="retailOutletForm" property="fact_postal_code" /></div>
  <div class="col-sm-2 text-right"><strong>Poblaci&oacute;n:</strong></div>
  <div class="col-sm-4"><bean:write name="retailOutletForm" property="fact_town" /></div>
</div>
<div class="row">
  <div class="col-sm-2 text-right"><strong>CIF:</strong></div>
  <div class="col-sm-4"><bean:write name="retailOutletForm" property="cif" /></div>
  <div class="col-sm-2 text-right"><strong>Denominaci&oacute;n:</strong></div>
  <div class="col-sm-4"><bean:write name="retailOutletForm" property="factname" /></div>
</div>

<logic:notEmpty name="retailOutletForm" property="entity_id">
  <hr><h5 class="text-center">DOCUMENTOS</h5><hr>
  <logic:equal name="document_exists" value="true">
    <tiles:insert attribute="list_of_documents"/>
  </logic:equal>
  <logic:notEqual name="document_exists" value="true">
    <p class="text-info">No se han encontrado documentos para este punto de venta.</div>
  </logic:notEqual>
</logic:notEmpty>
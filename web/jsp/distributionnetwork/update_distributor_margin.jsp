<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://www.cofrevip.com/partners/tags-app.tld" prefix="app" %>
<html:xhtml />					
<app:errors title="update.distributor.margin.errors.title" />

<div class="panel panel-default">
  <div class="panel-body">
    <div class="row">
      <div class="col-md-10">
        <strong>Red:</strong> <bean:write name="distribution_network" property="name"/>.
        <strong>Activación:</strong>
        <logic:equal name="distribution_network" property="giftVoucherActivationOnSale" value="true">A LA VENTA.</logic:equal>
        <logic:notEqual name="distribution_network" property="giftVoucherActivationOnSale" value="true">PREACTIVA&Oacute;N.</logic:notEqual>
        </div>
        <div class="col-md-2 text-right"><html:link action="/update_distribution_network" paramName="distribution_network" paramProperty="id" paramId="entity_id" styleClass="btn btn-default btn-xs">Modificar</html:link></div>
      </div>
      <hr>
      <strong>Cofre:</strong> <bean:write name="distributor_margin_info" property="thematic"/>. <strong>Validez:</strong>
    <bean:write name="distributorMarginForm" property="end_of_validity"/>.
    <strong>Precio:</strong> <bean:write name="distributor_margin_info" property="price" filter="false"/>.
  </div>
</div>
<html:form action="/update_distributor_margin">
  <html:hidden property="distributor_margin_id" />
  <html:hidden property="entity_id" />
  <html:hidden property="thematic_id" />
  <html:hidden property="end_of_validity" />
  <html:hidden property="run_once" />

  <div class="form-group">
    <label>Margen del distribuidor:</label>
    <html:text styleClass="form-control" property="margin" maxlength="6" />
  </div>
  <hr>
  <div class="text-right">
    <html:link action="/consult_distribution_network_sheet" paramName="distribution_network" paramProperty="id" paramId="entity_id" styleClass="btn btn-default"><span class="glyphicon glyphicon-chevron-left"></span> Volver</html:link>
    &nbsp;<html:submit styleClass="btn btn-primary">Actualizar</html:submit>
    </div>
</html:form>
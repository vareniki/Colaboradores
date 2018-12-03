<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.cofrevip.com/partners/tags-app.tld" prefix="app" %> 
<html:xhtml />

<app:errors title="gift.voucher.change.status.errors.title" />
<app:information />

<div class="panel panel-default">
  <div class="panel-body">
    <div class="row">
      <div class="col-md-10">
        <strong>Red:</strong> <bean:write name="distribution_network" property="name"/>.
        <strong>Activación:</strong>
        <logic:equal name="distribution_network" property="giftVoucherActivationOnSale" value="true">A LA VENTA.</logic:equal>
        <logic:notEqual name="distribution_network" property="giftVoucherActivationOnSale" value="true">PREACTIVA&Oacute;N.</logic:notEqual>
        <strong>IVA:</strong>
        <logic:equal name="distribution_network" property="withIva" value="true">con IVA</logic:equal>
        <logic:notEqual name="distribution_network" property="withIva" value="true">sin IVA</logic:notEqual>
      </div>
      <div class="col-md-2 text-right"><html:link action="/update_distribution_network" paramName="distribution_network" paramProperty="id" paramId="entity_id" styleClass="btn btn-default btn-xs">Modificar</html:link></div>
    </div>
    </div>
  </div>
  <hr>
  <ul class="nav nav-pills" role="tablist">
    <li role="presentation" class="active"><a href="#distrib" aria-controls="home" role="tab" data-toggle="tab">Distribuidores</a></li>
    <app:inprofiles profiles="14">
    <li role="presentation"><a href="#margenes" aria-controls="profile" role="tab" data-toggle="tab">M&aacute;rgenes</a></li>
    </app:inprofiles>
</ul>
<hr>
<div class="tab-content">
  <div role="tabpanel" class="tab-pane active" id="distrib">
    <div style="text-align: center">
      <html:link action="/list_of_distribution_networks" styleClass="btn btn-default btn-sm"><span class="glyphicon glyphicon-chevron-left"></span> Volver</html:link>
      &nbsp;<html:link action="/add_retail_outlet" paramName="distribution_network" paramProperty="id" paramId="distribution_network_id" styleClass="btn btn-default btn-sm"><span class="glyphicon glyphicon-plus-sign"></span> A&ntilde;adir distribuidor</html:link>
        <hr>
      </div>
    <tiles:insert attribute="list_of_retail_outlets"/>
  </div>
  <app:inprofiles profiles="14">
    <div role="tabpanel" class="tab-pane" id="margenes">
      <logic:notEmpty name="available_thematics_for_distributor_margin">
        <div style="text-align: center">
          <html:link action="/add_distributor_margin" paramName="distribution_network" paramProperty="id" paramId="entity_id" styleClass="btn btn-default btn-sm"><span class="glyphicon glyphicon-plus-sign"></span> A&ntilde;adir margen</html:link>
            <hr>
          </div>
      </logic:notEmpty>
      <logic:notEmpty name="distribution_network" property="distributorMarginList">
        <tiles:insert attribute="list_of_distributor_margins"/>
      </logic:notEmpty>
      <logic:empty name="distribution_network" property="distributorMarginList">
        <p class="text-warning">No existe m&aacute;rgenes para este distribuidor</p>
      </logic:empty>
    </div>
  </app:inprofiles>
</div>
<br>
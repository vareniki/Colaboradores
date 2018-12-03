<%@taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@taglib uri="http://www.cofrevip.com/partners/tags-app.tld" prefix="app" %>

<%@page language="java" import="es.regalocio.partners.business.common.StaticDefinition" %>
<%@page language="java" import="es.regalocio.partners.struts.action.distributionnetwork.RetailOutletAction" %>
<%@page language="java" import="org.apache.struts.Globals" %>

<html:xhtml />				

<logic:empty name="retailOutletForm" property="entity_id">
  <app:errors title="add.retail.outlet.errors.title" />
</logic:empty>
<logic:notEmpty name="retailOutletForm" property="entity_id">
  <app:errors title="update.retail.outlet.errors.title"  />
</logic:notEmpty>

<app:information />

<div class="panel panel-default">
  <div class="panel-body">
    <div class="row">
      <div class="col-md-10">
        <logic:present name="login">
        <strong>Login:</strong> <bean:write name="login" />.</logic:present>
        <strong>Red:</strong> <bean:write name="distribution_network" property="name"/>.
        <strong>Activación:</strong>
        <logic:equal name="distribution_network" property="giftVoucherActivationOnSale" value="true">A LA VENTA.</logic:equal>
        <logic:notEqual name="distribution_network" property="giftVoucherActivationOnSale" value="true">PREACTIVA&Oacute;N.</logic:notEqual>
      </div>
      <div class="col-md-2 text-right"><html:link action="/update_distribution_network" paramName="distribution_network" paramProperty="id" paramId="entity_id" styleClass="btn btn-default btn-xs">Modificar</html:link></div>
    </div>
  </div>
</div>
<bean:define id="action_name" name="<%=Globals.MAPPING_KEY%>" property="path" type="java.lang.String"/>
<html:form action="<%=action_name%>" styleClass="form-horizontal">
  <html:hidden property="entity_id" />
  <html:hidden property="user_id" />
  <html:hidden property="distribution_network_id" />
  <html:hidden property="contact_id" />
  <html:hidden property="address_id" />
  <html:hidden property="factaddress_id" />
  <html:hidden property="run_once" />

  <div class="form-group">
    <label class="col-sm-2 control-label">Denominaci&oacute;n:</label>
    <div class="col-sm-4">
      <html:text styleClass="form-control" property="name" maxlength="64" />
    </div>

    <label class="col-sm-2 control-label">Tel&eacute;fono:</label>
    <div class="col-sm-4">
      <html:text styleClass="form-control" property="phone" maxlength="14" />
    </div>
  </div>
  <div class="form-group">
    <label class="col-sm-2 control-label">Email:</label>
    <div class="col-sm-4">
      <html:text styleClass="form-control" property="email" maxlength="128" />
    </div>

    <label class="col-sm-2 control-label">Fax:</label>
    <div class="col-sm-4">
      <html:text styleClass="form-control" property="fax" maxlength="9" />
    </div>
  </div>

  <hr><h5 class="text-center">LOCALIZACI&Oacute;N Y CONTACTO</h5><hr>
  <div class="form-group">

  </div>
  <div class="form-group">
    <label class="col-sm-2 control-label">Nombre:</label>

    <div class="col-sm-2">
      <html:select styleClass="form-control" property="salutation_code">
        <html:option value=""></html:option>
        <html:optionsCollection name="salutation" />
      </html:select>
    </div>

    <div class="col-sm-3">
      <html:text styleClass="form-control" property="firstname" maxlength="64" />
    </div>
    <label class="col-sm-1 control-label">Apellidos:</label>
    <div class="col-sm-4">
      <html:text styleClass="form-control" property="lastname" maxlength="32" />
    </div>
  </div>

  <div class="form-group">
    <label class="col-sm-2 control-label">Direcci&oacute;n comercial:</label>
    <div class="col-sm-10">
      <html:text styleClass="form-control" property="address" maxlength="128" />
    </div>
  </div>
  <div class="form-group">
    <label class="col-sm-2 control-label">&nbsp;</label>
    <div class="col-sm-10">
      <html:text styleClass="form-control" property="further_address" maxlength="128" />
    </div>
  </div>
  <div class="form-group">
    <label class="col-sm-2 control-label">C&oacute;digo postal:</label>
    <div class="col-sm-2">
      <html:text styleClass="form-control" property="postal_code" maxlength="5" />
    </div>
    <label class="col-sm-2 control-label">Poblaci&oacute;n:</label>
    <div class="col-sm-6">
      <html:text styleClass="form-control" property="town" maxlength="128" />
    </div>
  </div>
  <hr><h5 class="text-center">FACTURACI&Oacute;N</h5><hr>
  <div class="form-group">
    <label class="col-sm-2 control-label">Direcci&oacute;n fiscal:</label>
    <div class="col-sm-10">
      <html:text styleClass="form-control" property="fact_address" maxlength="128" />
    </div>
  </div>
  <div class="form-group">
    <label class="col-sm-2 control-label">&nbsp;</label>
    <div class="col-sm-10">
      <html:text styleClass="form-control" property="fact_further_address" maxlength="128" />
    </div>
  </div>
  <div class="form-group">
    <label class="col-sm-2 control-label">C&oacute;digo postal:</label>
    <div class="col-sm-2">
      <html:text styleClass="form-control" property="fact_postal_code" maxlength="5" />
    </div>
    <label class="col-sm-2 control-label">Poblaci&oacute;n:</label>
    <div class="col-sm-6">
      <html:text styleClass="form-control" property="fact_town" maxlength="128" />
    </div>
  </div>

  <div class="form-group">
    <label class="col-sm-2 control-label">CIF:</label>
    <div class="col-sm-2">
      <html:text styleClass="form-control" property="cif" maxlength="9" />
    </div>
    <label class="col-sm-2 control-label">Denominaci&oacute;n:</label>
    <div class="col-sm-6">
      <html:text styleClass="form-control" property="factname" maxlength="64" />
    </div>
  </div>
  <hr><h5 class="text-center">CONFIGURACIONES</h5><hr>
  <div class="row">
    <div class="col-md-4">
      <h5>Permisos</h5>
      <logic:iterate id="current" name="profile_list" indexId="index">
        <bean:define id="check_box_disabled" value="false" />
        <logic:equal name="current" property="code" value="<%=Integer.toString(StaticDefinition.GIFT_VOUCHER_ACTIVATION_PROFILE_CODE)%>">
          <logic:equal name="retailOutletForm" property="gift_voucher_activation_on_sale" value="false">
            <bean:define id="check_box_disabled" value="true" />
          </logic:equal>
          <logic:equal name="retailOutletForm" property="gift_voucher_activation_on_sale" value="<%=RetailOutletAction.UNDEFINED_VALUE%>">
            <logic:equal name="distribution_network" property="giftVoucherActivationOnSale" value="false">
              <bean:define id="check_box_disabled" value="true" />
            </logic:equal>
          </logic:equal>
        </logic:equal>

        <div class="checkbox">
          <label>
            <html:multibox property="profile_code_array" disabled="<%=Boolean.valueOf(check_box_disabled)%>">
              <bean:write name="current" property="code" />
            </html:multibox>
            <bean:write name="current"/>
          </label>
        </div>
      </logic:iterate>
    </div>
    <div class="col-md-4">
      <h5>Activaci&oacute;n de los cheques</h5>
      <div class="radio">
        <label>
          <logic:equal name="distribution_network" property="giftVoucherActivationOnSale" value="true">
            <html:radio property="gift_voucher_activation_on_sale" value="<%=RetailOutletAction.UNDEFINED_VALUE%>" onclick="updateProfilesAccess(this, true);" />
          </logic:equal>
          <logic:notEqual name="distribution_network" property="giftVoucherActivationOnSale" value="true">
            <html:radio property="gift_voucher_activation_on_sale" value="<%=RetailOutletAction.UNDEFINED_VALUE%>" onclick="updateProfilesAccess(this, false);" />
          </logic:notEqual>
          &nbsp;Heredado de la Red.
        </label>
      </div>   
      <div class="radio">
        <label><html:radio property="gift_voucher_activation_on_sale" value="1" onclick="updateProfilesAccess(this, true);" />&nbsp;A la venta.</label>
      </div>
      <div class="radio">
        <label><html:radio property="gift_voucher_activation_on_sale" value="0" onclick="updateProfilesAccess(this, false);" />&nbsp;Preactivaci&oacute;n.</label>
      </div>   
      <div class="radio">
        <label><html:radio property="gift_voucher_activation_on_sale" value="2" onclick="updateProfilesAccess(this, false);" />&nbsp;<span class="text-warning">Prepago.</span></label>
      </div>
    </div>

    <div class="col-md-4">
      <h5>Plataforma de env&iacute;o</h5>
      <html:select styleClass="form-control" property="order_platform_id" styleId="order_platform_id">
        <html:option value=""></html:option>
        <logic:present name="order_platforms">
          <html:optionsCollection name="order_platforms"  />
        </logic:present>
      </html:select>
      <h5>Otras configuraciones</h5>
      <div class="radio">
        <label><html:multibox property="send_last_guides" value="1"/>&nbsp; Gu&iacute;as anteriores.</label>
      </div>
    </div>

  </div>
  <hr>
  <div class="row">
    <div class="col-xs-6">
      <html:link action="add_manual_invoice" paramName="retailOutletForm" paramProperty="entity_id" paramId="entity_id" styleClass="btn btn-default"><span class="glyphicon glyphicon-plus-sign"></span> A&ntilde;adir factura</html:link>
      &nbsp;<html:link action="add_manual_credit" paramName="retailOutletForm" paramProperty="entity_id" paramId="entity_id" styleClass="btn btn-default"><span class="glyphicon glyphicon-plus-sign"></span> A&ntilde;adir abono</html:link>

    </div>
    <div class="col-xs-6 text-right">
      <html:link action="/consult_distribution_network_sheet" paramName="retailOutletForm" paramProperty="distribution_network_id" paramId="entity_id" styleClass="btn btn-default"><span class="glyphicon glyphicon-chevron-left"></span> Volver</html:link>
      &nbsp;<html:submit styleClass="btn btn-primary">Actualizar</html:submit>
    </div>
  </div>
  <logic:notEmpty name="retailOutletForm" property="entity_id">
    <hr><h5 class="text-center">DOCUMENTOS</h5><hr>
    <hr>
    <logic:equal name="document_exists" value="true">
      <tiles:insert attribute="list_of_documents"/>
    </logic:equal>
    <logic:notEqual name="document_exists" value="true">
      <p class="text-info">No se han encontrado documentos para este punto de venta.</div>
    </logic:notEqual>
  </logic:notEmpty>
</html:form>
<br>
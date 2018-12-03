<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://www.cofrevip.com/partners/tags-app.tld" prefix="app" %> 
<html:xhtml />					
<app:errors title="add.distribution.network.errors.title" />

<html:form action="/add_distribution_network" styleClass="form-horizontal">
  <html:hidden property="run_once" />
  <html:hidden property="address_id" />
  <html:hidden property="factaddress_id" />

  <div class="form-group">
    <label class="col-sm-4 control-label">Nombre de la red:</label>
    <div class="col-sm-8">
      <html:text styleClass="form-control" property="name" maxlength="64" />
    </div>
  </div>
  <div class="form-group">
    <label class="col-sm-4 control-label">Modo de activaci&oacute;n de los cheques:</label>
    <div class="col-sm-8">
      <div class="checkbox">
        <html:radio property="gift_voucher_activation_on_sale" value="true" /> a la venta
      </div>
      <div class="checkbox">
        <html:radio property="gift_voucher_activation_on_sale" value="false" /> preactivaci&oacute;n
      </div>
    </div>
  </div>
  <div class="form-group">
    <label class="col-sm-4 control-label">Traspasar datos al punto de venta:</label>
    <div class="col-sm-8">
      <div class="checkbox">
        <label>
          <html:checkbox property="inherit_address" value="true" /> direcci&oacute;n de env&iacute;o
        </label>
      </div>
      <div class="checkbox">
        <label>
          <html:checkbox property="inherit_factaddress" value="true" /> datos de facturaci&oacute;n
        </label>
      </div>
    </div>
  </div>

  <div class="form-group">
    <label class="col-sm-4 control-label">Varios:</label>
    <div class="col-sm-8">
      <div class="checkbox">
        <label><html:checkbox property="disable_changepassword" value="false" />
          deshabilitar cambio de contrase&ntilde;a</label>
      </div>
      <div class="checkbox">
        <label>
          <html:checkbox property="with_iva" value="true" />
          facturas con IVA
        </label>
      </div>
    </div>
  </div>  
  <hr><h5 class="text-center">DIRECCI&Oacute;N DE ENTREGA</h5><hr> 
  <div class="form-group">
    <label class="col-sm-4 control-label">Direcci&oacute;n:</label>
    <div class="col-sm-8">
      <html:text styleClass="form-control" property="address" maxlength="64" />
    </div>
  </div>
  <div class="form-group">
    <label class="col-sm-4 control-label"></label>
    <div class="col-sm-8">
      <html:text styleClass="form-control" property="further_address" maxlength="128" />
    </div>
  </div>
  <div class="form-group">
    <label class="col-sm-4 control-label">C&oacute;digo postal:</label>
    <div class="col-sm-2">
      <html:text styleClass="form-control" property="postal_code" maxlength="5" />
    </div>
    <label class="col-sm-2 control-label">Poblaci&oacute;n:</label>
    <div class="col-sm-4">
      <html:text styleClass="form-control" property="town" maxlength="128" />
    </div>
  </div>

  <hr><h5 class="text-center">DIRECCI&Oacute;N DE FACTURACI&Oacute;N</h5><hr>

  <div class="form-group">
    <label class="col-sm-4 control-label">Direcci&oacute;n:</label>
    <div class="col-sm-8">
      <html:text styleClass="form-control" property="fact_address" maxlength="128" />
    </div>
  </div>
  <div class="form-group">
    <label class="col-sm-4 control-label"></label>
    <div class="col-sm-8">
      <html:text styleClass="form-control" property="fact_further_address"  maxlength="128" />
    </div>
  </div>
  <div class="form-group">
    <label class="col-sm-4 control-label">C&oacute;digo postal:</label>
    <div class="col-sm-2">
      <html:text styleClass="form-control" property="fact_postal_code" maxlength="5" />
    </div>
    <label class="col-sm-2 control-label">Poblaci&oacute;n:</label>
    <div class="col-sm-4">
      <html:text styleClass="form-control" property="fact_town" maxlength="128" />
    </div>
  </div>
  <div class="form-group">
    <label class="col-sm-4 control-label">CIF:</label>
    <div class="col-sm-8">
      <html:text styleClass="form-control" property="cif" maxlength="12" />
    </div>
  </div>
  <hr>
  <div class="text-right">
    <html:link action="/list_of_distribution_networks" styleClass="btn btn-default"><span class="glyphicon glyphicon-chevron-left"></span> Volver</html:link>
    &nbsp;<html:submit styleClass="btn btn-primary">Enviar</html:submit>
  </div>
</html:form>
<br>
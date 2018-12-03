<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://www.cofrevip.com/partners/tags-app.tld" prefix="app" %>
<html:xhtml />					

<app:errors title="update.partner.errors.title" />

<p class="text-info text-justify">Verifique los siguiente datos relativos al colaborador. 
  En caso de direcci&oacute;n electr&oacute;nica err&oacute;nea o no rellena,
  el colaborador no podr&aacute; recibir informaci&oacute;n alguna por e-mail.
  De la misma forma, ning&uacute;n reembolso por tranferencia podr&aacute; ser efectuado en caso
  de que los datos bancarios sean err&oacute;neos o incompletos.</p>
<hr>
<html:form action="/update_partner" styleClass="form-horizontal">
  <html:hidden property="partner_id" />
  <html:hidden property="contact_id" />
  <html:hidden property="address_id" />
  <html:hidden property="code" />
  <html:hidden property="run_once" />

  <div class="form-group">
    <label class="col-md-2 control-label"><strong>C&oacute;digo:</strong></label>
    <div class="col-md-4"><html:text styleClass="form-control" property="code" disabled="true" readonly="true" /></div>

    <label class="col-md-2 control-label"><strong>Tel&eacute;fono 1:</strong></label>
    <div class="col-md-4"><html:text styleClass="form-control" property="phone" maxlength="14" /></div>
  </div>
  <div class="form-group">
    <label class="col-md-2 control-label"><strong>Empresa:</strong></label>
    <div class="col-md-4"><html:text styleClass="form-control" property="name" maxlength="64" /></div>

    <label class="col-md-2 control-label"><strong>Tel&eacute;fono 2:</strong></label>
    <div class="col-md-4"><html:text styleClass="form-control" property="phone2" maxlength="14" /></div>
  </div>
  <div class="form-group">
    <label class="col-md-2 control-label"><strong>EMail:</strong></label>
    <div class="col-md-4"><html:text styleClass="form-control" property="email" maxlength="128" /></div>

    <label class="col-md-2 control-label"><strong>Fax:</strong></label>
    <div class="col-md-4"><html:text styleClass="form-control" property="fax" maxlength="9" /></div>
  </div>

  <div class="form-group">
    <label class="col-md-2 control-label"><strong>Den. social:</strong></label>
    <div class="col-md-4"><html:text styleClass="form-control" property="factname" maxlength="92" /></div>

    <label class="col-md-2 control-label"><strong>CIF:</strong></label>
    <div class="col-md-4"><html:text styleClass="form-control" property="cif" maxlength="12" /></div>
  </div>
  <hr><h5 class="text-center">DATOS DE CONTACTO</h5><hr>

  <div class="form-group">
    <label class="col-md-2 control-label"><strong>Tratamiento:</strong></label>
    <div class="col-md-4">
      <html:select styleClass="form-control" property="salutation_code">
        <html:option value=""></html:option>
        <html:optionsCollection name="salutation" />
      </html:select>
    </div>
  </div>

  <div class="form-group">
    <label class="col-md-2 control-label"><strong>Nombre:</strong></label>
    <div class="col-md-4"><html:text styleClass="form-control" property="firstname" maxlength="64" /></div>

    <label class="col-md-2 control-label"><strong>Apellidos:</strong></label>
    <div class="col-md-4"><html:text styleClass="form-control" property="lastname" maxlength="32" /></div>
  </div>

  <hr><h5 class="text-center">DIRECCI&Oacute;N FISCAL</h5><hr>

  <div class="form-group">
    <label class="col-md-2 control-label"><strong>Direcci&oacute;n:</strong></label>
    <div class="col-md-4"><html:text styleClass="form-control" property="address" maxlength="128" /></div>

    <label class="col-md-2 control-label"><strong>Comp. direcci&oacute;n:</strong></label>
    <div class="col-md-4"><html:text styleClass="form-control" property="further_address" maxlength="128" /></div>
  </div>

  <div class="form-group">
    <label class="col-md-2 control-label"><strong>C. Postal:</strong></label>
    <div class="col-md-4"><html:text styleClass="form-control" property="postal_code" maxlength="5" /></div>

    <label class="col-md-2 control-label"><strong>Poblaci&oacute;n:</strong></label>
    <div class="col-md-4"><html:text styleClass="form-control" property="town" maxlength="128" /></div>
  </div>

  <hr><h5 class="text-center">DATOS BANCARIOS Y COMISIONES</h5><hr>
  <div class="form-group">
    <label class="col-md-2 control-label"><strong>Facturaci&oacute;n e IVA:</strong><br><span style="font-weight:normal">(antiguos colaboradores)</span></label>
    <div class="col-md-4">
      <html:select styleClass="form-control" property="opciones">
        <html:optionsCollection name="con_sin_iva" />
      </html:select>
    </div>
    
    <label class="col-md-2 control-label"><strong>Nuevas facturas con IVA:</strong></label>
    <div class="col-md-4">
      <html:select styleClass="form-control" property="opciones2">
        <html:optionsCollection name="opciones2" />
      </html:select>
    </div>
  </div>
  <div class="form-group">
    <label class="col-md-2 control-label"><strong>Grupo de comisiones:</strong><br><span style="font-weight:normal">(desde noviembre 2017)</span></label>
    <div class="col-md-4">
      <html:select styleClass="form-control" property="groupal_agreement_id">
        <html:option value=""></html:option>
        <html:optionsCollection name="groupal_agreements" />
      </html:select>
    </div>
  </div>
  <hr>
  <div class="form-group">
    <div class="col-md-12">
      <label>IBAN</label><br>
      <html:text styleClass="form-control" property="iban" maxlength="30" />
    </div>
  </div>
  <hr>
  <p class="label label-info">Informaci&oacute;n obsoleta</p>
  <div class="form-group">
    <div class="col-md-2">
      <label>Banco</label><br>
      <html:text styleClass="form-control" property="bank_code" maxlength="4" />
    </div>
    <div class="col-md-2">
      <label>Oficina</label><br>
      <html:text styleClass="form-control" property="agency_code" maxlength="4" />
    </div>
    <div class="col-md-1">
      <label>DC</label><br>
      <html:text styleClass="form-control" property="key" size="1" maxlength="2" />
    </div>
    <div class="col-md-4">
      <label>Cuenta</label><br>
      <html:text styleClass="form-control" property="account_number" maxlength="10" />
    </div>
    <div class="col-md-3">
      <label>Domiciliaci&oacute;n</label><br>
      <html:text styleClass="form-control" property="domiciliation" maxlength="24" />
    </div>
  </div>
  <hr>

  <div class="text-right">
    <html:link action="/consult_partner_sheet" paramName="partnerForm" paramProperty="partner_id" paramId="partner_id" styleClass="btn btn-default"><span class="glyphicon glyphicon-chevron-left"></span> Volver</html:link>
    &nbsp;<html:submit styleClass="btn btn-primary">Actualizar</html:submit>
    </div>
</html:form>
<br>
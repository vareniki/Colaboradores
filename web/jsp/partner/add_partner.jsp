<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://www.cofrevip.com/partners/tags-app.tld" prefix="app" %>
<% String df = new java.text.SimpleDateFormat("yy").format(java.util.GregorianCalendar.getInstance().getTime());%>
<html:xhtml />
<app:errors title="add.partner.errors.title" />
<p class="text-primary">Por favor, seleccione los siguientes datos relativos al colaborador. En caso de direcci&oacute;n electr&oacute;nica
  err&oacute;nea o vac&iacute;a, este colaborador no podr&aacute; recibir informaci&oacute;n alguna a trav&eacute;s de email.
  De la misma forma si los datos bancarios se encuentran err&oacute;neos o incompletos tampoco podr&aacute;n ser
  efectuados reembolsos por transferencia.</p>
<hr>
<script type="text/javascript">
  $(document).ready(function () {
    $("#frmCode").attr('placeholder', "XX <%=df%> XXX / XX<%=df%>XXX");
  });
</script>
<html:form action="/add_partner" styleClass="form-horizontal">
  <html:hidden property="run_once" />
  <div class="row">
    <div class="col-sm-6">
      <div class="form-group">
        <label class="col-sm-4 control-label">Código:</label>
        <div class="col-sm-8">
          <html:text styleClass="form-control" property="code" maxlength="9" styleId="frmCode" />
        </div>
      </div>

      <div class="form-group">
        <label class="col-sm-4 control-label">Provincia:</label>
        <div class="col-sm-8">
          <html:select styleClass="form-control" property="department_code">
            <html:option value=""></html:option>
            <html:optionsCollection name="department" />
          </html:select>
        </div>
      </div>

      <div class="form-group">
        <label class="col-sm-4 control-label">Empresa:</label>
        <div class="col-sm-8">
          <html:text styleClass="form-control" property="name" maxlength="64" />
        </div>
      </div>

      <div class="form-group">
        <label class="col-sm-4 control-label">Razón Social:</label>
        <div class="col-sm-8">
          <html:text styleClass="form-control" property="factname" maxlength="64" />
        </div>
      </div>
      <div class="form-group">
        <label class="col-sm-4 control-label">Email:</label>
        <div class="col-sm-8">
          <html:text styleClass="form-control" property="email" maxlength="128" />
        </div>
      </div>
    </div>
    <div class="col-sm-6">
      <div class="form-group">
        <label class="col-sm-4 control-label">Tel&eacute;fono 1:</label>
        <div class="col-sm-8">
          <html:text styleClass="form-control" property="phone" maxlength="14" />
        </div>
      </div>
      <div class="form-group">
        <label class="col-sm-4 control-label">Tel&eacute;fono 2:</label>
        <div class="col-sm-8">
          <html:text styleClass="form-control" property="phone2" maxlength="14" />
        </div>
      </div>
      <div class="form-group">
        <label class="col-sm-4 control-label">CIF:</label>
        <div class="col-sm-8">
          <html:text styleClass="form-control" property="cif" maxlength="12" />
        </div>
      </div>
      <div class="form-group">
        <label class="col-sm-4 control-label">Fax:</label>
        <div class="col-sm-8">
          <html:text styleClass="form-control" property="fax" maxlength="9" />
        </div>
      </div>
    </div>
  </div>
  <hr><h5 class="text-center">CONTACTO</h5><hr>
  <div class="row">
    <div class="col-sm-6">
      <div class="form-group">
        <label class="col-sm-4 control-label">Nombre:</label>
        <div class="col-sm-8"><html:text styleClass="form-control" property="firstname" maxlength="64" /></div>
      </div>
    </div>
    <div class="col-sm-6">
      <div class="form-group">
        <label class="col-sm-4 control-label">Apellidos:</label>
        <div class="col-sm-8"><html:text styleClass="form-control" property="lastname" maxlength="32" /></div>
      </div>
    </div>
  </div>
  <hr><h5 class="text-center">DIRECCI&Oacute;N FISCAL</h5><hr>
  <div class="form-group">
    <label class="col-sm-2 control-label">Direcci&oacute;n:</label>
    <div class="col-sm-10"><html:text styleClass="form-control" property="address" maxlength="128" /></div>
  </div>
  <div class="form-group">
    <label class="col-sm-2 control-label"></label>
    <div class="col-sm-10"><html:text styleClass="form-control" property="further_address" maxlength="128" /></div>
  </div>
  <div class="form-group">
    <label class="col-sm-2 control-label">C.Postal:</label>
    <div class="col-sm-2"><html:text styleClass="form-control" property="postal_code" maxlength="5" /></div>
    <label class="col-sm-2 control-label">Poblaci&oacute;n:</label>
    <div class="col-sm-6"><html:text styleClass="form-control" property="town" maxlength="128" /></div>
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
  <p class="label label-info">Información obsoleta</p>
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
      <html:text styleClass="form-control" property="key" maxlength="2" />
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
    <html:submit styleClass="btn btn-primary">Enviar</html:submit>
    </div>
</html:form>
<br>
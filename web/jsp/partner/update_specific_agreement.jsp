<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://www.cofrevip.com/partners/tags-app.tld" prefix="app" %>
<html:xhtml />					
<app:errors title="update.specific.agreement.errors.title" />
<p class="text-center">
  <strong><bean:write name="partner" property="name"/></strong> (<bean:write name="partner" property="code"/>) | 
  <strong><bean:write name="thematic_name" /></strong> 
  - <bean:write name="specificAgreementForm" property="end_of_validity"/>
  (<bean:write name="specific_agreement_info" property="price" filter="false"/>)
</p>
<hr>
<html:form action="/update_specific_agreement" styleClass="form-horizontal">
  <html:hidden property="specific_agreement_id" />
  <html:hidden property="partner_id" />
  <html:hidden property="thematic_id" />
  <html:hidden property="end_of_validity" />
  <html:hidden property="run_once" />

  <div class="form-group">
    <label class="col-sm-4 control-label">Precio de la prestaci&oacute;n:</label>
    <div class="col-sm-4">
      <html:text styleClass="form-control" property="price_service" maxlength="6" />
    </div>
  </div>
  <div class="form-group">
    <label class="col-sm-4 control-label">Comisi&oacute;n est&aacute;ndar CofreVIP:</label>
    <div class="col-sm-4">
      <input type="text" class="form-control" disabled="disabled" value='<bean:write name="specific_agreement_info" property="default_commission" filter="false"/>'>
    </div>
  </div>
  <div class="form-group">
    <label class="col-sm-4 control-label">Comisi&oacute;n específica CofreVIP:</label>
    <div class="col-sm-4">
      <html:text styleClass="form-control" property="commission" maxlength="6" />
    </div>
  </div>
  <hr>
  <div class="form-group">
    <label class="col-sm-4 control-label">Letra:</label>
    <div class="col-sm-4">
      <html:select styleClass="form-control" property="word">
        <html:option value=""></html:option>
        <html:option value="A"></html:option>
        <html:option value="B"></html:option>
      </html:select>
    </div>
  </div>
  <div class="form-group">
    <label class="col-sm-4 control-label">Noviembre 2017+</label>
    <div class="col-sm-4">
      <html:select styleClass="form-control" property="new_agreement">
        <html:option value="1">S&Iacute;</html:option>
        <html:option value="2">NO</html:option>
      </html:select>
    </div>
  </div>
   <div class="form-group">
    <label class="col-sm-4 control-label">V&aacute;lido desde <small>(AAAAMM)</small></label>
    <div class="col-sm-4">
      <html:text styleClass="form-control" property="agreement_since" maxlength="6" />
    </div>
  </div>
  <hr>
  <div class="row">
    <div class="col-sm-8 col-sm-offset-4">
      <html:link action="/consult_partner_sheet" paramName="partner" paramProperty="id" paramId="partner_id" styleClass="btn btn-default"><span class="glyphicon glyphicon-chevron-left"></span> Volver</html:link>
      &nbsp;<html:submit styleClass="btn btn-primary">Actualizar</html:submit>
      </div>
    </div>
</html:form>
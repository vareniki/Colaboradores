<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://www.cofrevip.com/partners/tags-app.tld" prefix="app" %>
<html:xhtml />
<script type="text/javascript">
  var array_of_rates = [];

  function changeEndOfValidityOptions(thematicId) {
    var currentValue;
    var endOfValidity;

    var endOfValidity = document.forms['specificAgreementForm'].end_of_validity;

    var currentValue = '';
    var selectedIndex = endOfValidity.selectedIndex;
    if (selectedIndex > 0)
      currentValue = endOfValidity.options[selectedIndex].value;

    var index = 0;
    for (var counter = 0; counter < array_of_rates.length; counter++)
      if (array_of_rates[counter][0] == thematicId)
        index++;

    if (index == 0) {
      endOfValidity.disabled = true;
      endOfValidity.options.length = 0;
    } else {
      endOfValidity.disabled = false;
      endOfValidity.options.length = index + 1;

      index = 1;
      endOfValidity.options[0].value = '';
      endOfValidity.options[0].text = '';

      selectedIndex = 0;
      for (var counter = 0; counter < array_of_rates.length; counter++) {
        if (array_of_rates[counter][0] == thematicId) {
          endOfValidity.options[index].value = array_of_rates[counter][1];
          endOfValidity.options[index].text = array_of_rates[counter][1];
          if (endOfValidity.options[index].value == currentValue)
            selectedIndex = index;
          index++;
        }
      }

      endOfValidity.selectedIndex = selectedIndex;
    }

    changeRateInformations((selectedIndex > 0) ? endOfValidity.options[selectedIndex].value : '');
  }

  function changeRateInformations(end_of_validity) {
    var thematic_select = document.forms['specificAgreementForm'].thematic_id;
    var thematicId = thematic_select.options[thematic_select.selectedIndex].value;

    var price = '', commission = '';

    for (counter = 0; counter < array_of_rates.length; counter++) {
      if (array_of_rates[counter][0] == thematicId && array_of_rates[counter][1] == end_of_validity) {
        price = array_of_rates[counter][2];
        commission = array_of_rates[counter][3];
        found = true;
        break;
      }
    }

    $("#price").val(price);
    $("#commission").val(commission);
  }

  <logic:iterate id="current" indexId="index" name="specific_agreement_data" type="java.lang.String[]">
  array_of_rates[<%=index%>] = ["<%=current[0]%>", "<%=current[1]%>", "<%=current[2]%>", "<%=current[3]%>"];</logic:iterate>
  </script>

<app:errors title="add.specific.agreement.errors.title" />    

<div class="row">
  <p class="col-sm-2">Cod. Colaborador:</p>
  <p class="col-sm-3"><bean:write name="partner" property="code"/></p>
  <p class="col-sm-2">Denominaci&oacute;n:</p>
  <p class="col-sm-5"><bean:write name="partner" property="name"/></p>
</div>

<html:form action="/add_specific_agreement" styleClass="form-horizontal">
  <html:hidden property="partner_id" />
  <html:hidden property="run_once" />
  <hr>
  <div class="form-group">
    <label class="col-sm-3 control-label">Cofre tem&aacute;tico:</label>
    <div class="col-sm-3">
      <html:select styleClass="form-control" property="thematic_id" onchange="changeEndOfValidityOptions(this.options[this.selectedIndex].value);"><html:optionsCollection name="thematic_list" /></html:select>
      </div>
      <label class="col-sm-3 control-label">V&aacute;lido hasta:</label>
      <div class="col-sm-3">
      <logic:empty name="end_of_validity">
        <html:select styleClass="form-control" property="end_of_validity" disabled="true" onchange="changeRateInformations(this.options[this.selectedIndex].value);"></html:select>
      </logic:empty>
      <logic:notEmpty name="end_of_validity">
        <html:select styleClass="form-control" property="end_of_validity" onchange="changeRateInformations(this.options[this.selectedIndex].value);"><html:optionsCollection name="end_of_validity" /></html:select>
      </logic:notEmpty>
    </div>
  </div>
  <div class="form-group">
    <label class="col-sm-3 control-label">Precio de venta:</label>
    <div class="col-sm-3">
      <input type="text" id="price" value="<bean:write name='specific_agreement_info' property='price' filter='false'/>" disabled="disabled" class="form-control" />
    </div>
    <label class="col-sm-3 control-label">Precio prestaci&oacute;n:</label>
    <div class="col-sm-3">
      <html:text styleClass="form-control" property="price_service" maxlength="6" />
    </div>
  </div>
  <div class="form-group">
    <label class="col-sm-3 control-label">Comisi&oacute;n est&aacute;ndar:</label>
    <div class="col-sm-3">
      <input type="text" id="commission" value="<bean:write name='specific_agreement_info' property='default_commission' filter='false'/>" disabled="disabled" class="form-control" />
    </div>
    <label class="col-sm-3 control-label">Comisi&oacute;n CofreVIP:</label>
    <div class="col-sm-3">
      <html:text styleClass="form-control" property="commission" maxlength="6" />
    </div>
  </div>
  <hr>
  <div class="form-group">
    <label class="col-sm-3 control-label">Letra:</label>
    <div class="col-sm-3">
      <html:select styleClass="form-control" property="word">
        <html:option value=""></html:option>
        <html:option value="A"></html:option>
        <html:option value="B"></html:option>
      </html:select>
    </div>
    <label class="col-sm-3 control-label">Noviembre 2017+</label>
    <div class="col-sm-3">
      <html:select styleClass="form-control" property="new_agreement">
        <html:option value="1">S&Iacute;</html:option>
        <html:option value="2">NO</html:option>
      </html:select>
    </div>
    
  </div>
  <div class="form-group">
  	<label class="col-sm-6 control-label">&nbsp;</label>
    <label class="col-sm-3 control-label">V&aacute;lido desde <small>(AAAAMM)</small>:</label>
    <div class="col-sm-3">
      <html:text styleClass="form-control" property="agreement_since" maxlength="6" />
    </div>
  </div>
  <hr>
  <div class="row">
    <div class="col-sm-8 col-sm-offset-4">
      <html:link action="/consult_partner_sheet" paramName="specificAgreementForm" paramProperty="partner_id" paramId="partner_id" styleClass="btn btn-default"><span class="glyphicon glyphicon-chevron-left"></span> Volver</html:link>
      &nbsp;<html:submit styleClass="btn btn-primary">Actualizar</html:submit>
      </div>
    </div>
</html:form>
<br>
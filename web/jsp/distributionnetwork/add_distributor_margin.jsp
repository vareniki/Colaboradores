<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://www.cofrevip.com/partners/tags-app.tld" prefix="app" %> 
<html:xhtml />
<app:errors title="add.distributor.margin.errors.title" />
<script type="text/javascript">
  var array_of_rates = [];

  function changeEndOfValidityOptions(thematicId) {
    var currentValue;
    var endOfValidity;

    var endOfValidity = document.forms['distributorMarginForm'].end_of_validity;

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
    var found = false;
    var thematic_select = document.forms['distributorMarginForm'].thematic_id;
    var thematicId = thematic_select.options[thematic_select.selectedIndex].value;

    for (counter = 0; counter < array_of_rates.length; counter++) {
      if (array_of_rates[counter][0] == thematicId && array_of_rates[counter][1] == end_of_validity) {
        $("#price").val(array_of_rates[counter][2]);
        found = true;
        break;
      }
    }

    if (!found) {
      $("#price").val("");
    }
  }

  <logic:iterate id="current" indexId="index" name="distributor_margin_data" type="java.lang.String[]">
  array_of_rates[<%=index%>] = ["<%=current[0]%>", "<%=current[1]%>", "<%=current[2]%>"];</logic:iterate>
  </script>

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
    </div>
  </div>
<html:form action="/add_distributor_margin" styleClass="form-horizontal">
  <html:hidden property="entity_id" />
  <html:hidden property="run_once" />

  <div class="form-group">
    <label class="col-sm-2 control-label">Cofre:</label>
    <div class="col-sm-4">
      <html:select styleClass="form-control" property="thematic_id" onchange="changeEndOfValidityOptions(this.options[this.selectedIndex].value);"><html:optionsCollection name="thematic_list" /></html:select>
      </div>

      <label class="col-sm-2 control-label">V&aacute;lido hasta:</label>
      <div class="col-sm-4">
      <logic:empty name="end_of_validity">
        <html:select styleClass="form-control" property="end_of_validity" disabled="true" onchange="changeRateInformations(this.options[this.selectedIndex].value);"></html:select>
      </logic:empty>
      <logic:notEmpty name="end_of_validity">
        <html:select styleClass="form-control" property="end_of_validity" onchange="changeRateInformations(this.options[this.selectedIndex].value);"><html:optionsCollection name="end_of_validity" /></html:select>
      </logic:notEmpty>
    </div>
  </div>
  <div class="form-group">
    <label class="col-sm-2 control-label">Precio ( &euro; ):</label>
    <div class="col-sm-4"><input type="text" id="price" value="" readonly="readonly" class="form-control"></div>

    <label class="col-sm-2 control-label">Margen ( &euro; ):</label>
    <div class="col-sm-4">
      <html:text styleClass="form-control" property="margin" maxlength="6" />&nbsp;
    </div>
  </div>
  <hr>
  <div class="text-center">
    <html:link action="/consult_distribution_network_sheet" paramName="distribution_network" paramProperty="id" paramId="entity_id" styleClass="btn btn-default"><span class="glyphicon glyphicon-chevron-left"></span> Volver</html:link>
    <html:submit styleClass="btn btn-primary">Actualizar</html:submit>
    </div>
</html:form>
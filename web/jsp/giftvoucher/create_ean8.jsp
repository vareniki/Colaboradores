<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://www.cofrevip.com/partners/tags-app.tld" prefix="app" %>
<html:xhtml />
<script type="text/javascript">
  var array_of_rates = [];

  function changeEndOfValidityOptions(thematicId) {
    var endOfValidity = document.forms['createGiftVoucherForm'].end_of_validity;

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
    }
    else {
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
  }
  <%
    int index = 0;
  %>
  <logic:iterate id="current_thematic" name="thematic_list">
    <bean:define id="current_rate" name="current_thematic" property="thematicRate" />
  array_of_rates[<%=index++%>] = ["<bean:write name='current_thematic' property='id' />", "<bean:write name='current_rate' property='endOfValidity' format='dd/MM/yyyy' />"];
  </logic:iterate>
</script>
<app:errors title="create.ean8.errors.title" />
<html:form action="/create_ean8" styleClass="form-horizontal">
  <html:hidden property="run_once" />

  <div class="form-group">
    <label class="col-sm-5 control-label">Cofre tem&aacute;tico:</label>
    <div class="col-sm-3">
      <html:select styleClass="form-control" property="thematic_id" onchange="changeEndOfValidityOptions(this.options[this.selectedIndex].value);">
        <html:option value=""></html:option>
        <html:optionsCollection name="thematic_list" />
      </html:select>
    </div>
  </div>

  <div class="form-group">
    <label class="col-sm-5 control-label">V&aacute;lido hasta:</label>
    <div class="col-sm-3">
      <logic:empty name="end_of_validity">
        <html:select styleClass="form-control" property="end_of_validity" disabled="true" />
      </logic:empty>
      <logic:notEmpty name="end_of_validity">
        <html:select styleClass="form-control" property="end_of_validity">
          <html:option value=""></html:option>
          <html:options name="end_of_validity" />
        </html:select>
      </logic:notEmpty>
    </div>
  </div>

  <div class="form-group">
    <label class="col-sm-5 control-label">N&uacute;mero de cheques:</label>
    <div class="col-sm-3">
      <html:text styleClass="form-control" property="amount" maxlength="5" />
    </div>
  </div>

  <hr>
  <div class="text-right">
    <html:submit styleClass="btn btn-primary">Confirmar creaci&oacute;n</html:submit>
    </div>
</html:form>
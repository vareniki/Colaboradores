<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.cofrevip.com/partners/tags-app.tld" prefix="app" %>

<%@ page language="java" import="org.apache.struts.Globals" %>
<html:xhtml />
<script type="text/javascript">
  function changeDecimalSep() {
    var fields = [document.forms[0]["extra_discount"],
      document.forms[0]["extra_discount2"],
      document.forms[0]["forwarding_charges"],
      document.forms[0]["discount_on_forwarding_charges"]];

    for (i = 0; i < fields.length; i++) {
      fields[i].value = String(fields[i].value).replace(",", ".");
    }
  }
</script>

<logic:equal name="invoiceForm" property="type_of_document" value="4">
  <app:errors title="invoice.errors.title" />
</logic:equal>
<logic:equal name="invoiceForm" property="type_of_document" value="5">
  <app:errors title="credit.errors.title" />
</logic:equal>

<logic:equal name="invoiceForm" property="type_of_document" value="4">
  <p class="text-primary">Por favor, elija los siguiente datos relativos a su factura.</p>
</logic:equal>
<logic:equal name="invoiceForm" property="type_of_document" value="5">
  <p class="text-primary">Por favor, elija los siguiente datos relativos a su abono.</p>
</logic:equal>
<hr> 
<div class="row">
  <div class="col-sm-3 text-right"><strong>Distribuidor:</strong></div>
  <div class="col-sm-9">
    <bean:write name="retail_outlet_name"/> (<bean:write name="distribution_network_name"/>)
  </div>
</div>
<hr>
<bean:define id="action_name" name="<%=Globals.MAPPING_KEY%>" property="path" type="java.lang.String"/>
<html:form action="<%=action_name%>" onsubmit="changeDecimalSep()" styleClass="form-horizontal">
  <html:hidden property="run_once" />

  <html:hidden property="invoice_id" />
  <html:hidden property="entity_id" />
  <html:hidden property="type_of_document" />
  <div id="ean13_array">
    <logic:iterate id="current" name="invoiceForm" property="ean13_array">
      <input type="hidden" name="ean13_array" value="<%=current%>">
    </logic:iterate>
  </div>
  <div id="amount_array">
    <logic:iterate id="current" name="invoiceForm" property="amount_array">
      <input type="hidden" name="amount_array" value="<%=current%>">
    </logic:iterate>
  </div>

  <div class="form-group">
    <label class="col-sm-3 control-label">Fianza sobre ventas:</label>
    <div class="col-sm-3">
      <html:text styleClass="form-control" property="forwarding_charges" maxlength="9" />
    </div>

    <label class="col-sm-3 control-label">Fianza de reposici&oacute;n:</label>
    <div class="col-sm-3">
      <html:text styleClass="form-control" property="extra_discount2" maxlength="9" />
    </div>
  </div>        
  <div class="form-group">
    <label class="col-sm-3 control-label">Gastos de entrega:</label>
    <div class="col-sm-3">
      <html:text styleClass="form-control" property="extra_discount" maxlength="9" />
    </div>
    <label class="col-sm-3 control-label">Descuento gastos de entrega:</label>
    <div class="col-sm-3">
      <html:text styleClass="form-control" property="discount_on_forwarding_charges" maxlength="9" />
    </div>
  </div> 
    <hr>
  <div class="form-group">
    <label class="col-sm-3 control-label">N&uacute;mero de pedido:</label>
    <div class="col-sm-3">
      <html:text styleClass="form-control" property="order_form_reference" maxlength="16" />
    </div>
    <label class="col-sm-3 control-label">N&uacute;mero de albar&aacute;n:</label>
    <div class="col-sm-3">
      <html:text styleClass="form-control" property="numero_albaran" maxlength="16" />
    </div>
  </div>  

  <div class="form-group">
    <label class="col-sm-3 control-label">N&uacute;mero de proveedor:</label>
    <div class="col-sm-3">
      <html:text styleClass="form-control" property="numero_proveedor" maxlength="16" />
    </div>
    <label class="col-sm-3 control-label">N&uacute;mero de sucursal:</label>
    <div class="col-sm-3">
      <html:text styleClass="form-control" property="numero_sucursal" maxlength="16" />
    </div>
  </div>  
  <hr>
  <div class="form-group">
    <label class="col-sm-3 control-label">Importe total:</label>
    <div class="col-sm-3">
      <html:text styleClass="form-control" property="importe_total" maxlength="9" />
    </div>

    <label class="col-sm-3 control-label">Importe comisi&oacute;n:</label>
    <div class="col-sm-3">
      <html:text styleClass="form-control" property="importe_comision" maxlength="9" />
    </div>
  </div>  
  <hr>
  <div class="form-group">
    <label class="col-sm-3 control-label">Comentarios:</label>
    <div class="col-sm-9">
      <html:text styleClass="form-control" property="comments" maxlength="48" />
    </div>
  </div>
  <hr>
  <h5 class="text-center">LISTA DE COFRES</h5>
  <hr>
  <tiles:insert attribute="list_of_invoice_lines_form"/>
  <hr>
  <div class="row text-right">
    <html:submit styleClass="btn btn-primary">Registrar</html:submit>
  </div>
</html:form>
<br>
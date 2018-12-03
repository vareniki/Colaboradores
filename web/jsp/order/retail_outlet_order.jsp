<%@taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@taglib uri="http://www.cofrevip.com/partners/tags-app.tld" prefix="app" %>
<%@page language="java" import="org.apache.struts.Globals" %>
<html:xhtml />
<script type="text/javascript">

  var array_of_retail_outlets = [];
  var dataTable = null;

  <% int index = 0;%>
  <logic:iterate id="current" name="all_retail_outlets">
    <bean:define id="distribution_network" name="current" property="key" type="es.regalocio.partners.business.common.DefaultTypology" />
    <bean:define id="retail_outlets_array" name="current" property="value" type="java.util.List" />
    <logic:iterate id="retail_outlet" name="retail_outlets_array" type="es.regalocio.partners.business.common.DefaultTypology">
  array_of_retail_outlets[<%=index++%>] = ["<bean:write name="distribution_network" property="code" />", "<bean:write name="retail_outlet" property="code" />", "<bean:write name="retail_outlet" property="label" filter="false" />"];
    </logic:iterate>
  </logic:iterate>

  function cargarPuntosVenta(dnetworkId) {

    var result = '<option value=""></option>';
    jQuery.each(array_of_retail_outlets, function (i, data) {
      if (data[0] == dnetworkId) {
        result += '<option value="' + data[1] + '">' + data[2] + '</option>';
      }
    });

    $("#entity_id").html(result).removeAttr("disabled");
  }

  function cargarCofresRed(dnetwork_id) {

    if (dataTable != null) {
      dataTable.destroy();
    }

    dataTable = $('#list_of_order_lines').DataTable({
      "processing": true,
      "serverSide": false,
      "info": false,
      "pageLength": 25,
      "language": {
        "url": "//cdn.datatables.net/plug-ins/1.10.16/i18n/Spanish.json"
      },
      "ajax": {
        "url": "<%=request.getContextPath()%>/list_of_order_lines_form_ajax.do?distribution_network_id=" + dnetwork_id + "<logic:iterate id="current" name="retailOutletOrderForm" property="ean13_array">&ean13_array=<%=current%></logic:iterate>&<logic:iterate id="current" name="retailOutletOrderForm" property="amount_array">&amount_array=<%=current%></logic:iterate>",
                "type": "POST",
                "error": function (xhr, error, thrown) {
                }
              },
              "columnDefs": [
                {
                  "render": function (data, type, row) {
                    return Base64.decode(data);
                  }, "targets": [0, 1, 2, 3]
                },
                {
                  "sClass": "text-center", "targets": [1, 2, 3]
                }
              ]
            });
          }

          $(document).ready(function () {

            $(".form-horizontal").validate({
              errorClass: 'text-danger',
              rules: {
                // simple rule, converted to {required:true}
                distribution_network_id: "required",
                entity_id: "required",
                persona_contacto: {
                  required: false
                },
                tfno_contacto: {
                  required: false,
                  digits: true
                },
                email_contacto: {
                  required: false,
                  email: true
                }
              }
            });

            if ($("#distribution_network_id").val() != '') {
              cargarCofresRed($("#distribution_network_id").val());
            }

            $("#distribution_network_id").on("change", function () {
              cargarPuntosVenta(this.value);
              cargarCofresRed(this.value);
            });

            $('#retailOutletOrderForm').submit(function () {

              var htmlEAN13 = '';
              var htmlAmount = '';
              dataTable.rows().iterator('row', function (context, index) {
                var $node = $(this.row(index).node());
                var $input = $("input[name^='ean13_']", $node);

                if (parseInt($input.val()) > 0) {
                  htmlEAN13 += '<input type="hidden" name="ean13_array" value="' + $input.attr("name").substr(6) + '">';
                  htmlAmount += '<input type="hidden" name="amount_array" value="' + $input.val() + '">';
                }

              });

              $("#ean13_arrays").html(htmlEAN13 + htmlAmount);

              return true;
            });

          });
  </script>
<app:errors title="retail.outlet.order.errors.title" />
<app:information />
<bean:define id="action_name" name="<%=Globals.MAPPING_KEY%>" property="path" type="java.lang.String"/>
<html:form action="<%=action_name%>" styleClass="form-horizontal">
  <html:hidden property="order_id" />
  <html:hidden property="run_once" />
  <html:hidden property="gift_voucher_activation_on_sale" value="1" />

  <div id="ean13_arrays">
  </div>
  <div class="form-group">
    <label class="col-sm-2 control-label">Red:</label>
    <div class="col-sm-4">
      <html:select styleClass="form-control" property="distribution_network_id" styleId="distribution_network_id">
        <html:option value=""></html:option>
        <html:optionsCollection name="distribution_networks" />
      </html:select>
    </div>
    <label class="col-sm-2 control-label">Punto de venta:</label>
    <div class="col-sm-4">
      <logic:empty name="retail_outlets">
        <html:select styleClass="form-control" property="entity_id" disabled="true" styleId="entity_id" />
      </logic:empty>
      <logic:notEmpty name="retail_outlets">
        <logic:notEmpty name="retailOutletOrderForm" property="distribution_network_id">
          <html:select styleClass="form-control" property="entity_id" styleId="entity_id">
            <html:option value=""></html:option>
            <html:optionsCollection name="retail_outlets" />
          </html:select>
        </logic:notEmpty>
        <logic:empty name="retailOutletOrderForm" property="distribution_network_id">
          <html:select styleClass="form-control" property="entity_id" disabled="true" styleId="entity_id" />
        </logic:empty>
      </logic:notEmpty>
    </div>
  </div>
  <hr>
  <div class="form-group">
    <label class="col-sm-2 control-label">Fianza (&euro;):</label>
    <div class="col-sm-2">
      <html:text styleClass="form-control" property="extra_discount" maxlength="9" />
    </div>
    <label class="col-sm-2 control-label">Gastos de entrega (&euro;):</label>
    <div class="col-sm-2">
      <html:text styleClass="form-control" property="forwarding_charges" maxlength="9" />
    </div>
    <div class="col-sm-3 col-sm-offset-1">
      <label><html:checkbox property="envio_plataforma" value="true" />&nbsp;&nbsp;Enviar a la plataforma.</label><br>
      <small>(en caso de plataforma asignada)</small>
    </div>
  </div>    

  <div class="form-group">
    <label class="col-sm-2 control-label">Orden de pedido:</label>
    <div class="col-sm-2">
      <html:text styleClass="form-control" property="order_form_reference" maxlength="32" />
    </div>
    <label class="col-sm-2 control-label">Descuento:</label>
    <div class="col-sm-2">
      <html:text styleClass="form-control" property="discount_on_forwarding_charges" maxlength="9" />
    </div>

    <div class="col-sm-3 col-sm-offset-1">
      <label><html:checkbox property="mercancia_sin_coste" value="true" />&nbsp;&nbsp;Mercanc&iacute;a sin coste.</label>
    </div>
  </div>
  <hr>
  <div class="form-group">
    <label class="col-sm-2 control-label">Persona de contacto:</label>
    <div class="col-sm-3">
      <html:text styleClass="form-control" property="persona_contacto" maxlength="64" />
    </div>
    <label class="col-sm-1 control-label">Tel&eacute;fono:</label>
    <div class="col-sm-2">
      <html:text styleClass="form-control" property="tfno_contacto" maxlength="16" />
    </div>
    <label class="col-sm-1 control-label">Email:</label>
    <div class="col-sm-3">
      <html:text styleClass="form-control" property="email_contacto" maxlength="128" />
    </div>
  </div>
  <hr>
  <tiles:insert attribute="list_of_order_lines_form"/>
  <hr>
  <div class="text-right"><html:submit styleClass="btn btn-primary">Registrar pedido</html:submit></div>
</html:form>
<br>
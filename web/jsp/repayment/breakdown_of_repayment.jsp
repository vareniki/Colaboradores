<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://www.cofrevip.com/partners/tags-app.tld" prefix="app" %>
<%@ page language="java" import="es.regalocio.partners.business.common.Repayment" %>
<script type="text/javascript">
  var cargaColaborador = false;
  $(document).ready(function () {

    $(".colaborador-info").on("click", function () {
      if (!cargaColaborador) {
        var request = $.ajax({
          url: '<%=request.getContextPath()%>/consult_partner_sheet_popup.do?partner_id=<bean:write name="partner" property="id"/>&read_only=true',
          context: document.body
        });
        request.done(function (data) {
          $("#colaboradorInfo .modal-body").html(data);
        });
        
        cargaColaborador = true;
      }
    });

  });
</script>
<table class="table table-condensed table-striped">
  <tr>
    <th>N&deg; de reembolso:</th>
    <td><bean:write name="repayment" property="invoiceNumber"/>.</td>
    <th>Estado:</th>
    <td><bean:write name="repayment" property="status" filter="no"/></td>
  </tr>
  <tr>
    <th>N&uacute;mero de cheques:</th>
    <td><bean:write name="accounting_data" property="amount" /></td>
    <th>Total:</th>
    <td><bean:write name="accounting_data" property="total_amount" format="#,##0.00;#,##0.00"/> &euro;</td>
  </tr>
  <tr>
    <th>Comisi&oacute;n CofreVIP sin IVA:</th>
    <td><bean:write name="accounting_data" property="total_commission_TCC" format="#,##0.00;#,##0.00"/> &euro;</td>
    <th>Reembolso sin IVA:</th>
    <td><bean:write name="accounting_data" property="total_repayment_by_thematic" format="#,##0.00;#,##0.00"/> &euro;</td>
  </tr>
  <tr>
    <th>Comisi&oacute;n CofreVIP con IVA:</th>
    <td><bean:write name="accounting_data" property="total_commission_ET_VAT" format="#,##0.00;#,##0.00"/> &euro;</td>
    <th>Reembolso con IVA:</th>
    <td><bean:write name="accounting_data" property="total_repayment_by_thematic_VAT" format="#,##0.00;#,##0.00"/> &euro;</td>
  </tr>
  <app:notInprofiles profiles="7">
    <tr>
      <th>Colaborador:</th>      
      <td><a href="#" type="button" data-toggle="modal" data-target="#colaboradorInfo" class="colaborador-info"><bean:write name="partner" property="name"/></a></td>
      <th>C&oacute;digo:</th>
      <td><a href="#" type="button" data-toggle="modal" data-target="#colaboradorInfo" class="colaborador-info"><bean:write name="partner" property="code"/></a></td>
    </tr>
  </app:notInprofiles>
</table>
<hr>
<h4 class="text-center text-uppercase">Documentos</h4>
<hr>

<logic:notPresent name="doc_unico">
  <div class="row">
    <div class="col-xs-6">
      <html:link action="/load_invoice" paramName="repayment" paramProperty="id" paramId="repayment_id" target="_blank">
        <span class="glyphicon glyphicon-file"></span> Factura</html:link>
      </div>
      <div class="col-xs-6">
      <html:link action="/load_detailed_credit_note" paramName="repayment" paramProperty="id" paramId="repayment_id" target="_blank">
        <span class="glyphicon glyphicon-file"></span> Abono</html:link>
      </div>
    </div>
</logic:notPresent>
<logic:present name="doc_unico">
  <html:link action="/load_invoice" paramName="repayment" paramProperty="id" paramId="repayment_id" target="_blank">
    <span class="glyphicon glyphicon-file"></span> LIQUIDACI&Oacute;N / FACTURA</html:link>
</logic:present> 

<div class="modal fade bs-example-modal-lg" tabindex="-1" role="dialog" id="colaboradorInfo">
  <div class="modal-dialog modal-lg">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title">Informaci&oacute;n de <bean:write name="partner" property="name"/></h4>
      </div>
      <div class="modal-body">
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Cerrar</button>
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
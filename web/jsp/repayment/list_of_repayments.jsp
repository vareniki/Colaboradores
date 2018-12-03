<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://www.cofrevip.com/partners/tags-app.tld" prefix="app" %>

<html:xhtml />
<link rel="stylesheet" href="css/bootstrap-datetimepicker.min.css">

<script type="text/javascript" src="js/moment-with-locales.min.js"></script>
<script type="text/javascript" src="js/bootstrap-datetimepicker.min.js"></script>
<script type="text/javascript">
  $(document).ready(function () {

    $('#repayment_start, #repayment_end').datetimepicker({
      locale: 'es',
      format: 'DD/MM/YYYY'
    });

    $('#repayments_results').DataTable({
      "processing": true,
      "serverSide": true,
      "info": false,
      //"searching": false,
      "pageLength": 25,
      "language": {
        "url": "//cdn.datatables.net/plug-ins/1.10.16/i18n/Spanish.json"
      },
      "ajax": {
        "url": "<%=request.getContextPath()%>/list_of_repayments_ajax.do?partner_code=<bean:write name='repaymentsSearchForm' property='partner_code'/>&input_text=<bean:write name='repaymentsSearchForm' property='input_text'/>&repayment_start=<bean:write name='repaymentsSearchForm' property='repayment_start'/>&repayment_end=<bean:write name='repaymentsSearchForm' property='repayment_end'/>&repayment_status=<bean:write name='repaymentsSearchForm' property='repayment_status'/>",
                "type": "POST"
              },
              "order": [[ 0, "desc" ]],
              "columnDefs": [
                {
                  "render": function (data, type, row) {
                    return Base64.decode(data);
                  },
                  "targets": [0, 1, 2, 3, 4, 5, 6]
                },
                {
                  "sClass": "text-center", "targets": [2, 3, 4, 5]
                },
                {
                  "sClass": "text-right", "targets": [6]
                },
                {
                  "orderable": false,
                  "targets": [2, 3, 4, 6]
                }
              ]
            });

          });
</script>
<app:errors title="look.for.repayment.errors.title" />
<html:form action="/list_of_repayments">
  <app:inprofiles profiles="7">
    <html:hidden property="partner_code" />
  </app:inprofiles>
  <app:notInprofiles profiles="7">
    <html:hidden property="partner_code" value="" />
  </app:notInprofiles>
  <html:hidden property="run_once" />

  <div class="row">
    <div class="col-sm-3">
      <div class="form-group">
        <app:notInprofiles profiles="7">
          <label>Colaborador, factura, EAN8.</label>
        </app:notInprofiles>
        <app:inprofiles profiles="7">
          <label>Factura, cheque.</label>
        </app:inprofiles>
        <html:text styleClass="form-control" property="input_text" maxlength="15" />
      </div>
    </div>
    <div class="col-sm-2">
      <div class="form-group">
        <label>F. Pago desde</label>
        <html:text styleClass="form-control" styleId="repayment_start" property="repayment_start" maxlength="10" />
      </div>
    </div>  

    <div class="col-sm-2">
      <div class="form-group">
        <label>F. Pago hasta</label>
        <html:text styleClass="form-control" styleId="repayment_end" property="repayment_end" maxlength="10" />
      </div>
    </div>

    <div class="col-sm-3">
      <div class="form-group">
        <label>Estado</label>
        <html:select styleClass="form-control" property="repayment_status">
          <html:option value="">(todos)</html:option>
          <html:optionsCollection name="repayment_status_list" />
        </html:select>
      </div>
    </div>  

    <div class="col-sm-2 text-right">
      <label>&nbsp;</label><br>
      <html:submit styleClass="btn btn-primary">Buscar</html:submit>
      </div>
    </div>
    <hr>
    <table id="repayments_results" class="table table-striped table-condensed table-bordered table-hover" cellspacing="0" width="100%">
      <thead>
        <tr>
          <th>Factura</th>
          <th>Colaborador</th>
          <th>Cheques</th>
          <th>Comisi&oacute;n</th>
          <th>Reembolso</th>
          <th>Estado</th>
          <th>F. Pago</th>
        </tr>
      </thead>
      <tbody>
      </tbody>
    </table>        
</html:form>
<br>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ page import="es.regalocio.partners.utils.WebPartnersUtils" %>
<%@ page import="es.regalocio.partners.shared.SessionInfo" %>
<% boolean expedientNumber = WebPartnersUtils.isExpedientNumberAvailable((SessionInfo) session.getAttribute(SessionInfo.SESSION_INFO));%>
<html:xhtml />
<link rel="stylesheet" href="css/bootstrap-datetimepicker.min.css">

<script type="text/javascript" src="js/moment-with-locales.min.js"></script>
<script type="text/javascript" src="js/bootstrap-datetimepicker.min.js"></script>
<script type="text/javascript">

  $(document).ready(function () {
    $('#sales_start, #sales_end').datetimepicker({
      locale: 'es',
      format: 'DD/MM/YYYY'
    });

    var url_ajax = "<%=request.getContextPath()%>/list_of_sales_ajax.do?entity_id=<bean:write name='listOfSalesForm' property='entity_id'/>&sales_start=<bean:write name='listOfSalesForm' property='sales_start'/>&sales_end=<bean:write name='listOfSalesForm' property='sales_end'/>";
    $('#sales_results').DataTable({
      "processing": true,
      "serverSide": false,
      "info": false,
      "pageLength": 25,
      "language": {
        "url": "//cdn.datatables.net/plug-ins/1.10.16/i18n/Spanish.json"
      },
      "ajax": {
        "url": url_ajax,
        "type": "POST"
      },
      "columnDefs": [
        {
          "render": function (data, type, row) {
            return Base64.decode(data);
          },
          "targets": [0, 1, 2<% if (expedientNumber) { %>, 3<% } %>]
        },
        {
          "sClass": "text-center", "targets": [0, 2<% if (expedientNumber) { %>, 3<% } %>]
        }
      ]
    });
  });

</script>
<html:form action="/list_of_sales">
  <html:hidden property="entity_id" />
  <html:hidden property="run_once" />
  <div class="row">
    <div class="col-sm-3">
      <div class="form-group">
        <label>Ventas desde:</label>
        <html:text styleClass="form-control" styleId="sales_start" property="sales_start" maxlength="10" />        
      </div>
    </div>  
    <div class="col-sm-3">
      <div class="form-group">
        <label>Hasta:</label>
        <html:text styleClass="form-control" styleId="sales_end" property="sales_end" maxlength="10" />
      </div>
    </div>
    <div class="col-sm-2 text-right">
      <label>&nbsp;</label><br>
      <html:submit styleClass="btn btn-primary">Buscar</html:submit>
    </div>
  </div>
  <hr>
  <table id="sales_results" class="table table-striped table-condensed table-bordered table-hover" cellspacing="0" width="100%">
    <thead>
      <tr>
        <th>EAN8</th>
        <th>Descripci&oacute;n</th>
        <th>F. Venta</th>
        <% if (expedientNumber) { %>
        <th>Expediente</th>
        <% }%>
      </tr>
    </thead>
    <tbody>
    </tbody>
  </table>  
</html:form>
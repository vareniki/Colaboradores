<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://www.cofrevip.com/partners/tags-app.tld" prefix="app" %> 
<html:xhtml />					
<script type="text/javascript">
  jQuery(document).ready(function () {
    jQuery('#list_of_distribution').DataTable({
      "processing": true,
      "serverSide": true,
      "info": false,
      "pageLength": 25,
      "language": {
        "url": "//cdn.datatables.net/plug-ins/1.10.16/i18n/Spanish.json"
      },
      "ajax": {
        "url": "<%=request.getContextPath()%>/list_of_distribution_networks_ajax.do?only_enabled=false",
        "type": "POST"
      },
      "columnDefs": [
        {
          "render": function (data, type, row) {
            return Base64.decode(data);
          }, "targets": [0, 1, 2, 3]
        },
        {
          "orderable": false,
          "targets": [1, 2, 3]
        },
        {
          "sClass": "text-center", "targets": [1, 2, 3]
        }
      ]
    });
  });
</script>
<table id="list_of_distribution" class="table table-striped table-condensed table-bordered table-hover" cellspacing="0" width="100%">
  <thead>
    <tr>
      <th>Nombre</th>
      <th>Modo de activaci&oacute;n</th>
      <th>Puntos de Venta</th>
      <th></th>
    </tr>
  </thead>
</table>
<br>
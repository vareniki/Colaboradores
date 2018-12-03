<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://www.cofrevip.com/partners/tags-app.tld" prefix="app" %> 
<script type="text/javascript">
  $(document).ready(function () {
    $('#list_of_boxes_ion_stock').DataTable({
      "processing": true,
      "serverSide": true,
      "info": false,
      "pageLength": 25,
      "language": {
        "url": "//cdn.datatables.net/plug-ins/1.10.16/i18n/Spanish.json"
      },
      "ajax": {
        "url": "<%=request.getContextPath()%>/list_of_boxes_in_stock_ajax.do?entity_id=<bean:write name="entity_id"/>",
        "type": "POST"
      },
      "columnDefs": [
        {
          "render": function (data, type, row) {
            return Base64.decode(data);
          },
          "targets": [0, 1, 2, 3, 4, 5]
        },
        {
          "sClass": "text-center", "targets": [0, 1, 3, 4, 5]
        }
      ]
    });
  });
</script>
<table id="list_of_boxes_ion_stock" class="table table-striped table-condensed table-bordered table-hover" cellspacing="0" width="100%">
  <thead>
    <tr>
      <th>EAN8</th>
      <th>EAN13</th>
      <th>Descripci&oacute;n</th>
      <th>P. Venta</th>
      <th>Fin de validez</th>
      <th>Desde</th>
    </tr>
  </thead>
</table>
<br>
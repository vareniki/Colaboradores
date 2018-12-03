<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>

<script type="text/javascript">
  $(document).ready(function () {
    $('#list_of_retail_outlet_orders').DataTable({
      "processing": true,
      "serverSide": true,
      "info": false,
      "pageLength": 25,
      "language": {
        "url": "//cdn.datatables.net/plug-ins/1.10.16/i18n/Spanish.json"
      },
      "ajax": {
        "url": "<%=request.getContextPath() %>/list_of_retail_outlet_orders_ajax.do",
        "type": "POST"
      },
      "columnDefs": [
        {
          "render": function (data, type, row) {
            return Base64.decode(data);
          },
          "targets": [0, 1, 2, 3, 4, 5, 6, 7]
        },
        {
          "orderable":false,
          "targets": [3,4,5,6]
        },
        {
          "sClass": "text-center", "targets": [0, 1, 2, 3, 4, 5, 6, 7]
        }
      ]
    });
  });
</script>
<table id="list_of_retail_outlet_orders" class="table table-striped table-condensed table-bordered table-hover" cellspacing="0" width="100%">
  <thead>
    <tr>
      <th>Pedido</th>
      <th>Red</th>
      <th>Punto de venta</th>
      <th>Num. orden</th>
      <th>Cofres</th>
      <th>Plat.</th>
      <th></th>
      <th></th>
    </tr>
  </thead>
</table>

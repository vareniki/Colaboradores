<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>

<script type="text/javascript">
  $(document).ready(function () {
    $('#list_of_retails').DataTable({
      "processing": true,
      "serverSide": true,
      "info": false,
      "pageLength": 25,
      "language": {
        "url": "//cdn.datatables.net/plug-ins/1.10.16/i18n/Spanish.json"
      },
      "ajax": {
        "url": "<%=request.getContextPath()%>/list_of_retail_outlets_ajax.do?distribution_network_id=<bean:write name='distribution_network' property='id'/>&amp;only_enabled=false",
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
          "orderable": false,
          "targets": [4, 5]
        }
      ]
    });
  });
</script>
<table id="list_of_retails" class="table table-striped table-condensed table-bordered table-hover" cellspacing="0" width="100%">
  <thead>
    <tr>
      <th>Denominaci&oacute;n</th>
      <th>Direcci&oacute;n</th>
      <th>Contacto</th>
      <th>Raz&oacute;n social</th>
      <th></th>
      <th></th>
    </tr>
  </thead>
</table>

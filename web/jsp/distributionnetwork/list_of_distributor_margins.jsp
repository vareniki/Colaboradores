<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>

<script type="text/javascript">
  $(document).ready(function () {
    $('#list_of_distributor_margins').DataTable({
      "processing": true,
      "serverSide": true,
      "info": false,
      "searching": false,
      "pageLength": 25,
      "language": {
        "url": "//cdn.datatables.net/plug-ins/1.10.16/i18n/Spanish.json"
      },
      "ajax": {
        "url": "<%=request.getContextPath()%>/list_of_distributor_margins_ajax.do?entity_id=<bean:write name="distribution_network" property="id"/>",
                "type": "POST"
              },
              "columnDefs": [
                {
                  "render": function (data, type, row) {
                    return Base64.decode(data);
                  }, "targets": [0, 1, 2, 3]
                },
                {
                  "sClass": "text-center", "targets": [2, 3]
                }
              ]
            });
          });
</script>
<table id="list_of_distributor_margins" class="table table-striped table-condensed table-bordered table-hover" cellspacing="0" width="100%">
  <thead>
    <tr>
      <th>Cofre tem&aacute;tico</th>
      <th>V&aacute;lido hasta</th>
      <th>Margen</th>
      <th></th>
    </tr>
  </thead>
</table>

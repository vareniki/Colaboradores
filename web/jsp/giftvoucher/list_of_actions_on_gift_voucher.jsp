<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<script type="text/javascript">
  $(document).ready(function () {
    $('#list_of_actions_on_gift_voucher').DataTable({
      "processing": true,
      "serverSide": false,
      "info": false,
      "paging": false,
      "searching": false,
      "ordering": false,
      "pageLength": 25,
      "language": {
        "url": "//cdn.datatables.net/plug-ins/1.10.16/i18n/Spanish.json"
      },
      "ajax": {
        "url": "<%=request.getContextPath()%>/list_of_actions_on_gift_voucher_ajax.do?ean8=<bean:write name="gift_voucher" property="giftVoucherNumber"/>",
                "type": "POST"
              },
              "columnDefs": [
                {
                  "render": function (data, type, row) {
                    return Base64.decode(data);
                  }, "targets": [0, 1, 2]
                },
                {
                  "sClass": "text-center", "targets": [0, 1, 2]
                }
              ]
            });
          });
</script>
<table id="list_of_actions_on_gift_voucher" class="table table-striped table-condensed table-hover" cellspacing="0" width="100%">
  <thead>
    <tr>
      <th>Operaci&oacute;n</th>
      <th>Fecha</th>
      <th>Usuario</th>
    </tr>
  </thead>
</table>
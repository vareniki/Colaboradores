<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://www.cofrevip.com/partners/tags-app.tld" prefix="app" %>

<script type="text/javascript">
  $(document).ready(function () {
    $('#list_of_gift_vouchers').DataTable({
      "processing": true,
      "serverSide": true,
      "info": false,
      "pageLength": 25,
      "searching": false,
      "language": {
        "url": "//cdn.datatables.net/plug-ins/1.10.16/i18n/Spanish.json"
      },
      "ajax": {
        "url": "<%=request.getContextPath()%>/list_of_gift_vouchers_ajax.do?repayment_id=<bean:write name="repayment_id" />",
        "type": "POST"
      },
      "columnDefs": [
        {
          "render": function (data, type, row) {
            return Base64.decode(data);
          },
          "targets": [0, 1, 2, 3, 4, 5, 6]
        },
        {
          "sClass": "text-center", "targets": [0, 1, 2, 3, 4, 5, 6]
        }
      ]
    });
  });
</script>
<table id="list_of_gift_vouchers" class="table table-striped table-condensed table-bordered table-hover" cellspacing="0" width="100%">
  <thead>
    <tr>
      <th>N&deg; de cheque</th>
      <th>Cofre tem&aacute;tico</th>
      <th>V&aacute;lido hasta</th>
      <th>Valor</th>
      <th>Comisi&oacute;n</th>
      <th>Reembolso</th>
      <th></th>
    </tr>
  </thead>
</table>
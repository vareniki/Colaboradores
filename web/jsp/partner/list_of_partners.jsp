<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://www.cofrevip.com/partners/tags-app.tld" prefix="app" %>
<%@ taglib prefix="tiles" uri="http://struts.apache.org/tags-bean" %>
<script type="text/javascript">
  $(document).ready(function () {
    $('#list_of_partners').DataTable({
      "processing": true,
      "serverSide": true,
      "info": false,
      "pageLength": 25,
      "language": {
        "url": "//cdn.datatables.net/plug-ins/1.10.16/i18n/Spanish.json"
      },
      "ajax": {
        "url": "<%=request.getContextPath() %>/list_of_partners_ajax.do?read_only=true",
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
          "orderable": false,
          "targets": [5, 6]
        },
        {
          "sClass": "text-center", "targets": [0, 3]
        }
      ]
    });
  });
</script>
<table id="list_of_partners" class="table table-striped table-condensed table-bordered table-hover" cellspacing="0" width="100%">
  <thead>
    <tr>
      <th>C&oacute;digo</th>
      <th>Denominaci&oacute;n</th>
      <th>Raz. Social</th>
      <th>Tel&eacute;fono</th>
      <th>Email</th>
      <th></th>
      <th></th>
    </tr>
  </thead>
</table>
<br>
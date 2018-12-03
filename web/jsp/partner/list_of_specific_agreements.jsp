<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<script type="text/javascript">
  $(document).ready(function () {
    $('#list_of_specific_agreements').DataTable({
      "processing": true,
      "serverSide": false,
      "pageLength": 25,
      "searching": false,
      "language": {
        "url": "//cdn.datatables.net/plug-ins/1.10.16/i18n/Spanish.json"
      },
      "ajax": {
        "url": "<%=request.getContextPath() %>/list_of_specific_agreements_ajax.do?partner_id=<bean:write name='partner' property='id'/><logic:equal name="readOnly" value="true">&read_only=true</logic:equal>",
        "type": "POST"
      },
      "columnDefs": [
        {
          "render": function (data, type, row) {
            return Base64.decode(data);
          }, "targets": [0, 1, 2, 3, 4, 5, 6, 7, 8]
        },
        {
          "sClass": "text-center", "targets": [1, 2, 3, 4, 5, 6, 7, 8]
        }
      ]
    });
  });
</script>
<table id="list_of_specific_agreements" class="table table-striped table-condensed table-bordered table-hover" cellspacing="0" width="100%">
  <thead>
    <tr>
      <th>Cofre tem&aacute;tico</th>
      <th>V&aacute;lido hasta</th>
      <th>Valor venal</th>
      <th>Comisi&oacute;n</th>
      <th>Letra</th>
      <th>Nov. 2017+</th>
      <th>Desde</th>
      <th>Global</th>
      <th></th>
    </tr>
  </thead>
</table>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://www.cofrevip.com/partners/tags-app.tld" prefix="app" %>

<script type="text/javascript">
  $(document).ready(function () {
    $('#list_of_retail_documents').DataTable({
      "processing": true,
      "serverSide": true,
      "info": false,
      "language": {
        "url": "//cdn.datatables.net/plug-ins/1.10.16/i18n/Spanish.json"
      },
      "ajax": {
        "url": "<%=request.getContextPath() %>/list_of_retail_outlet_documents_ajax.do?entity_id=<bean:write name='retailOutletForm' property='entity_id'/>",
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
          "sClass": "text-center", "targets": [3, 4, 5]
        }
      ],
      "order": [[ 2, "desc" ]]
      
    });
  });
</script>
<table id="list_of_retail_documents" class="table table-striped table-condensed table-bordered table-hover" cellspacing="0" width="100%">
  <thead>
    <tr>
      <th>Tipo de documento</th>
      <th>N&deg; de documento</th>
      <th>Fecha</th>
      <th></th>
      <th></th>
      <th></th>
    </tr>
  </thead>
</table>
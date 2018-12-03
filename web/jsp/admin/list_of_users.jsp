<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://www.cofrevip.com/partners/tags-app.tld" prefix="app" %> 
<script type="text/javascript">
  $(document).ready(function () {
    $('#list_of_users').DataTable({
      "processing": true,
      "serverSide": true,
      "info": false,
      "pageLength": 25,
      "language": {
        "url": "//cdn.datatables.net/plug-ins/1.10.16/i18n/Spanish.json"
      },
      "ajax": {
        "url": "<%=request.getContextPath()%>/list_of_users_ajax.do",
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
          "sClass": "text-center", "targets": [4, 5]
        }
      ]
    });
  });
</script>
<table id="list_of_users" class="table table-striped table-condensed table-bordered table-hover" cellspacing="0" width="100%">
  <thead>
    <tr>
      <th>Usuario</th>
      <th>Nombre</th>
      <th>Apellidos</th>
      <th>Email</th>
      <th></th>
      <th></th>
    </tr>
  </thead>
</table>
<hr>
<div class="text-right">
  <html:link action="/add_user" styleClass="btn btn-default"><span class="glyphicon glyphicon-plus-sign"></span> A&ntilde;adir usuario</html:link>
</div>
<br>
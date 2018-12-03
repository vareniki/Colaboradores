<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>

<script type="text/javascript">

  function addInvoiceLine(ean13, amount) {
    jQuery("#ean13_array").append("<input type='hidden' name='ean13_array' value='" + ean13 + "'>");
    jQuery("#amount_array").append("<input type='hidden' name='amount_array' value='" + amount + "'>");
  }

  function updateInvoiceLine(index, value) {
    var amount = jQuery("#amount_array input[name='amount_array']").get(index);
    if (amount) {
      amount.value = value;
    }
  }

  function deleteInvoiceLine(index) {

    var ean13 = jQuery("#ean13_array input[name='ean13_array']").get(index);
    var amount = jQuery("#amount_array input[name='amount_array']").get(index);

    if (ean13 && amount) {
      jQuery(ean13).remove();
      jQuery(amount).remove();
    }
  }

  function getFormData($form) {
    var unindexed_array = $form.serializeArray();
    var indexed_array = {};

    jQuery.map(unindexed_array, function (n, i) {
      if (n['name'] == 'ean13_array') {
        
        if (indexed_array['ean13_array2'] == null) {
          indexed_array['ean13_array2'] = n['value'];
        } else {
          indexed_array['ean13_array2'] += "," + n['value'];
        }
        
      } else if (n['name'] == 'amount_array') {
        
        if (indexed_array['amount_array2'] == null) {
          indexed_array['amount_array2'] = n['value'];
        } else {
          indexed_array['amount_array2'] += "," + n['value'];
        }
        
      } else {
        
        indexed_array[n['name']] = n['value'];
      }
    });

    return indexed_array;
  }

  $(document).ready(function () {

    jQuery("#list_of_invoice_lines").on("keypress", ".input_grid_amount", function (e) {

      var keynum = e.keyCode || e.charCode;
      return ((keynum >= 48 && keynum <= 57) || (keynum >= 37 && keynum <= 40) || keynum == 8 || keynum == 9 || keynum == 46 || keynum == 45);

    }).on("change", ".input_grid_amount", function (e) {

      var ean13 = jQuery(this).attr("data-ean13");

      var value = parseInt(this.value, 10);
      if (isNaN(value)) {
        this.value = '0';
      } else {
        this.value = value;
      }

      var index = jQuery("#ean13_array input[name='ean13_array']").index("input[value='" + ean13 + "']");
      if (index == -1) {
        if (this.value != '0') {
          addInvoiceLine(ean13, this.value);
        }
      } else if (this.value != '0') {
        updateInvoiceLine(index, this.value);
      } else {
        deleteInvoiceLine(index);
      }

      return true;
    });

    var invoiceData = getFormData(jQuery("#invoiceForm"));
    
    $('#list_of_invoice_lines').DataTable({
      "processing": true,
      "serverSide": false,
      "info": false,
      "pageLength": 25,
      "language": {
        "url": "//cdn.datatables.net/plug-ins/1.10.16/i18n/Spanish.json"
      },
      "ajax": {
        "url": "<%=request.getContextPath()%>/list_of_invoice_lines_form_ajax.do",
        "type": "POST",
        "data": invoiceData
      },
      "columnDefs": [
        {
          "render": function (data, type, row) {
            return Base64.decode(data);
          }, "targets": [0, 1, 2, 3, 4, 5, 6]
        },
        {
          "width": "5", "targets": 5
        },
        {
          "sClass": "text-center", "targets": [0, 1, 2, 3, 4, 5]
        }
      ],
        "order": [[ 3, "asc" ], [0, "asc"]]
    });
  });
</script>
<table id="list_of_invoice_lines" class="table table-striped table-condensed table-bordered table-hover" cellspacing="0" width="100%">
  <thead>
    <tr>
      <th>ID del Cofre</th>
      <th>Cofre tem&aacute;tico</th>
      <th>EAN13</th>
      <th>V&aacute;lido hasta</th>
      <th>Precio unitario</th>
      <th>Margen unitario</th>
      <th>Cantidad</th>
    </tr>
  </thead>
</table>

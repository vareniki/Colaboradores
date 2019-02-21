<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://www.cofrevip.com/partners/tags-app.tld" prefix="app" %> 
<html:xhtml />
<app:errors title="repayment.errors.title" />
<logic:present name="partner">
  <script type="text/javascript">

    function check_repayment(ean8, pin, email, form) {
      jQuery.ajax({
        method: "GET",
        url: "<%=request.getContextPath()%>/consult_ean8_ajax.do?ean8=" + ean8 + "&pin=" + pin + "&email=" + email,
        dataType: "text"
      }).done(function (data, textStatus, jqXHR) {

        var obj = eval('(' + data + ')');

        if (obj.result == 'OK') {
          form.find("button").addClass("hidden");
          form.find("input").attr("readonly", "readonly");

          jQuery("#gift_vouchers").append(jQuery("#template").html());

          var ean8Word = obj.ean8;
          var word = obj.word.toUpperCase();
          if (word == 'A' || word == 'B') {
            ean8Word += word;
          }
          var input = '<input value="' + obj.thematicId + '/' + obj.fecha + '/' + ean8Word + '" name="gift_voucher_array" type="hidden">';

          jQuery("#gift_voucher_array").append(input);

        } else {
          bootbox.alert("La combinaci&oacute;n de EAN8 y c&oacute;digo de seguridad no es correcta.<hr>"
                  + "Es posible que el cheque haya sido utilizado anteriormente o reservado por otro colaborador.");
        }

        jQuery(this).addClass("done");
        
      }).fail(function (jqXHR, textStatus, errorThrown) { 
      });
    }

    jQuery(document).ready(function () {

      var html = jQuery("#template").html();
      jQuery("#gift_vouchers").append(html);

      jQuery("#gift_vouchers").on("click", "button[name='validar']", function (e) {
        var form = jQuery(this).closest(".gift-voucher-form");
        var ean8 = form.find("input[name=ean8]").val();
        var pin = form.find("input[name=pin]").val();
        var email = form.find("input[name=email]").val();

        if (ean8.length < 8 || pin.length != 3) {
          bootbox.alert("Debe escribir un EAN8 y PIN correctos.");
          return;
        }
        check_repayment(ean8, pin, email, form);

      });
      
      jQuery("#repaymentForm").on("submit", function() {
        var lastForm = jQuery("#gift_vouchers .gift-voucher-form:last-of-type");
        if (jQuery("input[name=ean8]", lastForm).val() != '') {
          alert("Es necesario validar el cheque antes de intentar enviar el reembolso.");
          return false;
        }
        return true;
      });
      
    });
  </script>
  <div id="template" class="hidden">
    <div class="gift-voucher-form">
      <div class="form-group">
        <label class="sr-only">N. Cheque</label>
        <input type="hidden" name="email" value="">
        <input class="form-control input-sm" value="0" maxlength="0" disabled="disabled" style="width: 28px">
        <input name="ean8" type="text" class="form-control input-sm " placeholder="EAN8 + letra (s&oacute;lo cofres bipack)" maxlength="9" minlength="8" size="30">
      </div>
      <div class="form-group">
        <label class="sr-only">PIN</label>
        <input name="pin" type="text" class="form-control input-sm" placeholder="c&oacute;digo de seguridad" maxlength="3" minlength="3">
      </div>
      <div class="form-group">
        <button type="submit" class="btn btn-default btn-sm" name="validar"><span class="glyphicon glyphicon-ok"></span> Validar</button>
      </div>
    </div>
    <br>
  </div>
  <p class="text-info text-center"><bean:write name="partner" property="name" /></p>
  <hr>
  <div id="gift_vouchers" class="form-inline">
  </div>
  <hr>
  <html:form action="/add_repayment">
    <html:hidden property="run_once" />
    <html:hidden property="repayment_id" />
    <html:hidden property="partner_id" />
    <div id="gift_voucher_array"></div>
    <div class="text-right">
      <button type="submit" class="btn btn-primary btn-sm"><span class="glyphicon glyphicon-plus-sign"></span> Registrar reembolsos validados</button>
    </div>
  </html:form>
</logic:present>
<logic:notPresent name="partner">
<div class="alert alert-danger" role="alert">
  No se ha asignado colaborador para el Reembolso. <a href="<%=request.getContextPath() %>/list_of_partners.do">Seleccione un colaborador...</a>
</div>
</logic:notPresent>
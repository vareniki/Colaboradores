<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.cofrevip.com/partners/tags-app.tld" prefix="app" %>
<html:xhtml />
<jsp:useBean id="repayment" type="es.regalocio.partners.business.common.Repayment" scope="request" />
<script type="text/javascript">
  $(document).ready(function () {

    $('[data-toggle="tabajax"]').click(function (e) {
      var $this = $(this),
          loadurl = $this.attr('href'),
          targ = $this.attr('data-target');

      $.get(loadurl, function (data) {
        $(targ).html(data);
      });

      $this.tab('show');
      return false;
    });

    // Activa la primera solapa
    var $tab1 = $("#iTab1");
    var loadurl = $tab1.attr('href');
    var targ = $tab1.attr('data-target');

    jQuery.get(loadurl, function (data) {
      $(targ).html(data);
    });

  });
</script>
<app:information />
<ul class="nav nav-pills">
  <li class="active"><a href="breakdown_of_repayment.do?repayment_id=<bean:write name="repayment" property="id" />" id="iTab1" data-target="#tab1" data-toggle="tabajax">Detalle reembolso</a></li>
  <li><a href="list_of_gift_vouchers.do?repayment_id=<bean:write name="repayment" property="id" />" data-target="#tab2" data-toggle="tabajax">Cheques regalo</a></li>
    <app:notInprofiles profiles="7">
    <li><a href="list_of_actions.do?repayment_id=<bean:write name="repayment" property="id" />" data-target="#tab3" data-toggle="tabajax">Hist&oacute;rico</a></li>
    </app:notInprofiles>
</ul>
<hr>
<div class="tab-content">
  <div role="tabpanel" class="tab-pane active" id="tab1"></div>
  <div role="tabpanel" class="tab-pane" id="tab2"></div>
  <app:notInprofiles profiles="7">
    <div role="tabpanel" class="tab-pane" id="tab3"></div>
  </app:notInprofiles>
</div>

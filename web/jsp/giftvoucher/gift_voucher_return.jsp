<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://www.cofrevip.com/partners/tags-app.tld" prefix="app" %>

<%@page language="java" import="es.regalocio.partners.business.common.HandlingOfReturn" %>
<html:xhtml />
<app:errors title="gift.voucher.return.errors.title" />
<app:information />
<p class="text-info text-justify">
  Por favor, escriba un n&deg; de cheque regalo (EAN8), as&iacute; como el modo de gesti&oacute;n asociado de devoluci&oacute;n, 
  bien sea "contin&uacute;a a la venta" si el cofre se encuentra en un estado que permite ser revendido o "enviado para su destrucci&oacute;n".
  En este &uacute;ltimo caso usted debe devolvernos el cheque regalo a la dirección siguiente:<br><br>
  <strong>Regalocio - Avda. Espa&ntilde;a, 17. 28100 - Alcobendas (Madrid)</strong>
</p>
<hr>
<html:form action="/gift_voucher_return" styleClass="form-horizontal">
  <html:hidden property="run_once" />
  <div class="form-group">
    <label class="col-sm-4 control-label">Modo de gesti&oacute;n:</label>
    <div class="col-sm-8">
      <div class="checkbox">
        <html:radio property="handling_code" value="<%=Integer.toString(HandlingOfReturn.DEVUELTO_A_LA_VENTA.getCode())%>" />&nbsp;&nbsp;contin&uacute;a a la venta.
      </div>
      <div class="checkbox">
        <html:radio property="handling_code" value="<%=Integer.toString(HandlingOfReturn.ENVIADO_PARA_SU_DESTRUCCION.getCode())%>" />&nbsp;&nbsp;enviado para su destrucci&oacute;n.
      </div>
    </div>
  </div>

  <div class="form-group">
    <label class="col-sm-4 control-label">N&deg; de cheque:</label>
    <div class="col-sm-8">
      <html:text styleClass="form-control" style="margin-left: 5px" property="ean8" maxlength="8" />
    </div>
  </div>
  <div class="form-group">
    <label class="col-sm-4 control-label"></label>
    <div class="col-sm-8">
      <html:submit styleClass="btn btn-primary">Devolver cheque</html:submit>
    </div>
  </div>
</html:form>
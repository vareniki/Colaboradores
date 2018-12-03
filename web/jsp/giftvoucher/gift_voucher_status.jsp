<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.cofrevip.com/partners/tags-app.tld" prefix="app" %>
<html:xhtml />	
<%@page language="java" import="es.regalocio.partners.business.common.GiftVoucherStatus" %>

<logic:notPresent name="gift.voucher.obsolete.error.title">
  <app:errors title="gift.voucher.status.errors.title" />
</logic:notPresent>
<logic:present name="gift.voucher.obsolete.error.title">
  <app:errors title="gift.voucher.obsolete.error.title" />
</logic:present>
<html:form action="/gift_voucher_status" styleClass="form-horizontal">
  <html:hidden property="run_once" />
  <div class="form-group">
    <label class="col-sm-4 control-label">N&deg; de Cheque:</label>
    <div class="col-sm-1">
      <input class="form-control pull-right" type="text" value="0" disabled="disabled" style="width:36px;">
    </div>
    <div class="col-sm-4">
      <html:text styleClass="form-control" property="ean8" maxlength="9" />
    </div>
    <div class="col-sm-3"><html:submit styleClass="btn btn-primary">Buscar</html:submit></div>
  </div>
  <logic:notEmpty name="gift_voucher">
    <bean:define id="gift_voucher_status" name="gift_voucher" property="status" type="es.regalocio.partners.business.common.GiftVoucherStatus"/>
    <hr>
    <div class="panel panel-default">
      <div class="panel-heading"><strong>N&deg; de Cheque:</strong> <bean:write name="gift_voucher" property="giftVoucherNumberAndWord" />, <bean:write name="gift_voucher" property="status" /></div>
      <div class="panel-body">
        <div class="row">
          <div class="col-sm-2"><strong>Cofre:</strong></div>
          <div class="col-sm-6"><bean:write name="thematic" property="name" /> (<bean:write name="thematic" property="id" />) - <bean:write name="thematic_rate" property="ean13" /></div>
          <div class="col-sm-2"><strong>V&aacute;lido hasta:</strong></div>
          <div class="col-sm-2"><bean:write name="gift_voucher" property="realEndOfValidity" format="dd/MM/yyyy" /></div>
        </div>
        <logic:greaterThan name="thematic_rate" property="priceService" value="0">
          <div class="row">
            <div class="col-sm-2"><strong>Valor facial:</strong></div>
            <div class="col-sm-6"><bean:write name="thematic_rate" property="priceService" format="#,##0.00;#,##0.00" /> &euro;</div>
            <div class="col-sm-2"><logic:notEmpty name="repayment_invoice_number"><strong>N&ordm; Reembolso:</strong></logic:notEmpty></div>
            <div class="col-sm-2">
              <logic:notEmpty name="repayment_invoice_number">
                <bean:write name="repayment_invoice_number" /> 
              </logic:notEmpty>
            </div>
          </div>
        </logic:greaterThan>

        <logic:notEmpty name="thematic" property="description">
          <hr>
          <p><strong>Prestaciones:</strong></p><bean:write name="thematic" property="description" />.</p>
        </logic:notEmpty>
        <hr>
        
        <logic:notEmpty name="message_multiple">
          
          <logic:equal name="gift_voucher" property="word" value=" ">
            <p class="text-danger">Solicite la letra de seguridad que debe añadir tras el código de reserva (letra A o B).</p>
          </logic:equal>
          <logic:notEqual name="gift_voucher" property="word" value=" ">
            
            <% if (gift_voucher_status.equals(GiftVoucherStatus.ACTIVO)) { %>
            <p class="text-success"><strong>El cheque est&aacute; activo y puede ser utilizado.</strong><br>
              <i>Es posible reservar el bono para que no sea utilizado por otro colaborador, pero en ese caso deber&aacute; 
                especificar además del número de bono la letra correspondiente (A o B).</i></p>
            <% } else if (gift_voucher_status.equals(GiftVoucherStatus.RESERVADO)) { %>

              <logic:empty name="change_status_list">
                <p><span class="text-danger"><strong>Este cheque no est&aacute; activo y sólo puede ser utilizado por el colaborador que lo ha reservado.</strong></span><br>
                  Puede contactarnos en el <strong>902 090 336</strong> para cualquier informaci&oacute;n asociada.</p>
              </logic:empty>
              <logic:notEmpty name="change_status_list">
                <p class="text-success "><strong>Este cheque está reservado por usted y por tanto puede ser utilizado.</strong>
              </logic:notEmpty>

            <% } else { %>
              <p><span class="text-danger"><strong>Este cheque no est&aacute; activo y por tanto no puede ser reservado o utilizarlo.</strong></span><br>
                Puede contactarnos en el <strong>902 090 336</strong> para cualquier informaci&oacute;n asociada.</p>
            <% } %>
              
          </logic:notEqual>
          
        </logic:notEmpty>
        
          <logic:empty name="message_multiple">
            <% if (gift_voucher_status.equals(GiftVoucherStatus.ACTIVO)) { %>
              <p class="text-success"><strong>El cheque est&aacute; activo y puede ser utilizado.</strong></p>
            <% } else if (gift_voucher_status.equals(GiftVoucherStatus.RESERVADO)) { %>

              <logic:empty name="change_status_list">
                <p><span class="text-danger"><strong>Este cheque no est&aacute; activo y sólo puede ser utilizado por el colaborador que lo ha reservado.</strong></span><br>
                  Puede contactarnos en el <strong>902 090 336</strong> para cualquier informaci&oacute;n asociada.</p>
              </logic:empty>
              <logic:notEmpty name="change_status_list">
                <p class="text-success "><strong>Este cheque está reservado por usted y por tanto puede ser utilizado.</strong>
              </logic:notEmpty>

            <% } else { %>
            <p class="text-danger"><span class="text-danger"><strong>Este cheque no est&aacute; activo y por tanto no puede ser reservado o utilizarlo.</strong></span><br>
                Puede contactarnos en el <strong>902 090 336</strong> para cualquier informaci&oacute;n asociada.</p>
            <% } %>
          </logic:empty>
      </div>
    </div>
  </logic:notEmpty>
</html:form>

<logic:notEmpty name="change_status_list">
  <html:form action="/gift_voucher_status" styleClass="form-horizontal">
    <html:hidden property="ean8" />
    <html:hidden property="run_once" />
    <div class="form-group">
      <label class="col-sm-4 control-label">Reservar / Reactivar:</label>
      <div class="col-sm-5">
        <html:select styleClass="form-control" property="new_status">
          <html:option value=""></html:option>
          <html:optionsCollection name="change_status_list" />
        </html:select>
      </div>
      <div class="col-sm-3"><html:submit styleClass="btn btn-primary">Cambiar</html:submit></div>
    </div>
  </html:form>
  <hr>
</logic:notEmpty>      

<br>
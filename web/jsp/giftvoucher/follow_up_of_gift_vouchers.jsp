<%@taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@taglib uri="http://www.cofrevip.com/partners/tags-app.tld" prefix="app" %>
<%@page language="java" import="es.regalocio.partners.business.common.GiftVoucherStatus" %>
<html:xhtml />					
<app:errors title="follow.up.of.gift.vouchers.errors.title" />

<html:form action="/follow_up_of_gift_vouchers" styleClass="form-horizontal">
  <html:hidden property="run_once" />
  <div class="form-group">
    <label class="col-sm-4 control-label">N&deg; de Cheque:</label>
    <div class="col-sm-5">
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
          <div class="col-sm-4"><bean:write name="thematic" property="name" /> | <bean:write name="thematic_rate" property="ean13" /></div>
          <div class="col-sm-2"><strong>V&aacute;lido hasta:</strong></div>
          <div class="col-sm-4"><bean:write name="gift_voucher" property="realEndOfValidity" format="dd/MM/yyyy" /></div>
        </div>
        <div class="row">
          <div class="col-sm-2"><strong>Valor facial:</strong></div>
          <div class="col-sm-4"><bean:write name="thematic_rate" property="priceService" format="#,##0.00;#,##0.00" /> &euro;</div>
          <div class="col-sm-2"><logic:notEmpty name="repayment_invoice_number"><strong>N&ordm; Reembolso:</strong></logic:notEmpty></div>
          <div class="col-sm-4">
            <logic:notEmpty name="repayment_invoice_number">
              <html:link action="/consult_repayment" paramId="repayment_id" paramName="repayment_invoice_id"><bean:write name="repayment_invoice_number" /></html:link>
            </logic:notEmpty>
          </div>
        </div>
        <logic:notEmpty name="retail_outlet">
          <div class="row">
            <div class="col-sm-2"><strong>Red de distribuci&oacute;n:</strong></div>
            <div class="col-sm-4"><bean:write name="network" /></div>
            <div class="col-sm-2"><strong>Punto de venta:</strong></div>
            <div class="col-sm-4"><bean:write name="retail_outlet" /></div>
          </div>
        </logic:notEmpty>
        <div class="row">
          <logic:notEmpty name="gift_voucher" property="dateOfSale">
            <div class="col-sm-2"><strong>Fecha de venta:</strong></div>
            <div class="col-sm-4"><bean:write name="gift_voucher" property="dateOfSale" format="dd/MM/yyyy" /></div>
          </logic:notEmpty>
          <logic:empty name="gift_voucher" property="dateOfSale">
            <div class="col-sm-6"></div>
          </logic:empty>
        </div>

        <logic:notEmpty name="thematic" property="description">
          <hr>
          <p class="text-info">El cofre ofrece las siguientes prestaciones:</p>
          <p><bean:write name="thematic" property="description" /></p>
        </logic:notEmpty>
      </div>
      <hr>
      <h4 class="text-center">Hist&oacute;rico de operaciones</h4>
      <hr>
      <tiles:insert attribute="list_of_actions_on_gift_voucher"/>
    </div>
  </logic:notEmpty>
</html:form>
<logic:notEmpty name="change_status_list">
  <html:form action="/follow_up_of_gift_vouchers" styleClass="form-horizontal">
    <html:hidden property="ean8" />
    <html:hidden property="run_once" />
    <div class="form-group">
      <label class="col-sm-4 control-label">Nuevo estado: </label>
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
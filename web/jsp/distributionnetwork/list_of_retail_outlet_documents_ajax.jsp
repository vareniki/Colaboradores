<%@taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@taglib uri="http://www.cofrevip.com/partners/tags-app.tld" prefix="app" %>

<%@page language="java" import="es.regalocio.partners.business.common.StaticDefinition" %>
<jsp:useBean id="URLParameters" class="java.util.HashMap" scope="request" />
{
"draw": 0,
"recordsFiltered": <bean:write name="total_records" />,
"data": [
<% boolean inicial = true; %>
<logic:iterate id="current" name="list_of_documents" type="java.util.Map">
  <bean:define id="invoice_id" name="current" property="id" />
  <bean:define id="key">retail.outlet.document.<bean:write name="current" property="type_of_document"/></bean:define>
  <% URLParameters.put("invoice_id", invoice_id);%>

  <% if (inicial) {
      inicial = false;
    } else { %>,<% }%> [
  "<app:tobase64><bean:message key="<%=key%>"/></app:tobase64>",
  "<app:tobase64>
    <logic:greaterEqual name="current" property="type_of_document" value="<%=Integer.toString(StaticDefinition.MANUAL_INVOICE)%>">
      <logic:equal name="current" property="type_of_document" value="<%=Integer.toString(StaticDefinition.MANUAL_INVOICE)%>">
        <app:inprofiles profiles="1,8">
          <app:inprofiles profiles="14">
            <html:link action="/update_manual_invoice" paramName="current" paramProperty="id" paramId="invoice_id"><bean:write name="current" property="external_reference"/></html:link>
          </app:inprofiles>
          <app:notInprofiles profiles="14">
            <bean:write name="current" property="external_reference"/>
          </app:notInprofiles>
        </app:inprofiles>
        <app:notInprofiles profiles="1,8">
          <bean:write name="current" property="external_reference"/>
        </app:notInprofiles>
      </logic:equal>
      <logic:equal name="current" property="type_of_document" value="<%=Integer.toString(StaticDefinition.MANUAL_CREDIT)%>">
        <app:inprofiles profiles="1,8">
          <app:inprofiles profiles="14">
            <html:link action="/update_manual_credit" paramName="current" paramProperty="id" paramId="invoice_id">
              <bean:write name="current" property="external_reference"/>
            </html:link>
          </app:inprofiles>
          <app:notInprofiles profiles="14">
            <bean:write name="current" property="external_reference"/>
          </app:notInprofiles>
        </app:inprofiles>
        <app:notInprofiles profiles="1,8">
          <bean:write name="current" property="external_reference"/>
        </app:notInprofiles>
      </logic:equal>
    </logic:greaterEqual>
    <logic:lessThan name="current" property="type_of_document" value="<%=Integer.toString(StaticDefinition.MANUAL_INVOICE)%>">
      <bean:write name="current" property="external_reference"/>
    </logic:lessThan>
  </app:tobase64>",
  "<app:tobase64><bean:write name="current" property="date_of_document" format="dd/MM/yyyy" /></app:tobase64>",
  "<app:tobase64>
    <logic:greaterEqual name="current" property="type_of_document" value="<%=Integer.toString(StaticDefinition.MONTHLY_INVOICE)%>">
      <app:inprofiles profiles="9,14">
        <html:link action="/load_distribution_document" paramName="current" paramProperty="id" paramId="invoice_id" target="_blank"><img src="img/pdf.gif" title="Carga del documento" border="0" /></html:link>
      </app:inprofiles>
    </logic:greaterEqual>
    <logic:lessThan name="current" property="type_of_document" value="<%=Integer.toString(StaticDefinition.MONTHLY_INVOICE)%>">
      <html:link action="/load_distribution_document" paramName="current" paramProperty="id" paramId="order_id" target="_blank"><img src="img/pdf.gif" title="Carga del documento" border="0" /></html:link>
    </logic:lessThan>
  </app:tobase64>",
  "<app:tobase64>
    <app:inprofiles profiles="1,8">
      <app:inprofiles profiles="14">
        <logic:greaterEqual name="current" property="type_of_document" value="<%=Integer.toString(StaticDefinition.MANUAL_INVOICE)%>">
          <html:link action="/delete_manual_document" name="URLParameters" onclick="return confirm('¿Está seguro de querer eliminar este documento?');"><img src="img/bin.gif" title="Eliminar este documento" border="0" /></html:link>
        </logic:greaterEqual>
      </app:inprofiles>
    </app:inprofiles>
  </app:tobase64>",
  "<app:tobase64>
    <app:inprofiles profiles="1,8">
      <app:inprofiles profiles="14">

        <logic:greaterEqual name="current" property="type_of_document" value="<%=Integer.toString(StaticDefinition.MONTHLY_INVOICE)%>">
          <logic:lessEqual name="current" property="type_of_document" value="<%=Integer.toString(StaticDefinition.MANUAL_CREDIT)%>">
            <html:link action="/send_invoice_email" name="URLParameters" onclick="return confirm('¿Está seguro de enviar un correo a este punto de venta confirmando la generación de su factura o abono?');"><img src="img/send_mail.gif" title="Enviar confirmaci&oacute;n de factura o abono" border="0" /></html:link>
          </logic:lessEqual>
        </logic:greaterEqual>

      </app:inprofiles>
    </app:inprofiles>
  </app:tobase64>"
  ]
</logic:iterate>
]
}
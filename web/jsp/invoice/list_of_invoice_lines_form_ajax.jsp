<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://www.cofrevip.com/partners/tags-app.tld" prefix="app" %>
{
"draw": 0,
"recordsFiltered": <bean:write name="total_records" />,
"data": [
<% boolean inicial = true; %>
<logic:iterate id="current" name="invoice_line_list" type="es.regalocio.partners.bean.InvoiceLineBean">
  <logic:greaterThan name="current" property="unitPrice" value="0">
    <% if (inicial) {
        inicial = false;
      } else { %>,<% }%> [
      "<app:tobase64><bean:write name="current" property="thematicId"/></app:tobase64>",
    "<app:tobase64><bean:write name="current" property="thematicBox"/></app:tobase64>",
    "<app:tobase64><bean:write name="current" property="ean13"/></app:tobase64>",
    "<app:tobase64><bean:write name="current" property="endOfValidity" format="dd/MM/yyyy"/></app:tobase64>",
    "<app:tobase64><bean:write name="current" property="unitPrice" format="#,##0.00;#,##0.00"/> &euro;</app:tobase64>",
    "<app:tobase64><bean:write name="current" property="unitMargin" format="#,##0.00;#,##0.00"/> &euro;</app:tobase64>",
    "<app:tobase64><input type="text" class="input_grid_amount" data-ean13="<bean:write name='current' property='ean13'/>" value="<bean:write name='current' property='amount'/>" maxlength="3"></app:tobase64>"]
  </logic:greaterThan>
</logic:iterate>
]
}
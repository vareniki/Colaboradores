<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://www.cofrevip.com/partners/tags-app.tld" prefix="app" %>
{
"draw": 0,
"recordsFiltered": <bean:write name="total_records" />,
"data": [
<% boolean inicial = true; %>
<logic:iterate id="current" name="list_of_repayments">
    <% if (inicial) {
        inicial = false;
      } else { %>,<% }%> [
    "<app:tobase64><html:link action="/consult_repayment" paramName="current" paramProperty="repayment_id" paramId="repayment_id"><bean:write name="current" property="invoice_number"/></html:link></app:tobase64>",
    "<app:tobase64><bean:write name="current" property="partner_code"/> - <bean:write name="current" property="partner_name"/></app:tobase64>",
    "<app:tobase64><bean:write name="current" property="number_of_gift_vouchers"/></app:tobase64>", 
    "<app:tobase64><bean:write name="current" property="amount_of_commission" format="#,##0.00;#,##0.00"/> &euro;</app:tobase64>",
    "<app:tobase64><bean:write name="current" property="amount_of_repayment" format="#,##0.00;#,##0.00"/> &euro;</app:tobase64>", 
    "<app:tobase64><bean:write name="current" property="status"/></app:tobase64>",
    "<app:tobase64><bean:write name="current" property="payment_date" format="dd/MM/yyyy"/> <bean:write name="current" property="credit_transfered_reference"/></app:tobase64>"]
</logic:iterate>
]
}
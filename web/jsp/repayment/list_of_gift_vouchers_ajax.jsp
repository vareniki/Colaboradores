<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://www.cofrevip.com/partners/tags-app.tld" prefix="app" %>
{
"draw": 0,
"recordsFiltered": <bean:write name="total_records" />,
"data": [
<% boolean inicial = true; %>
<logic:iterate id="current" name="list_of_gift_vouchers">
  <% if (inicial) {
      inicial = false;
    } else { %>,<% }%> [
  "<app:tobase64><bean:write name="current" property="gift_voucher_number"/></app:tobase64>",
  "<app:tobase64><bean:write name="current" property="thematic_box"/></app:tobase64>",
  "<app:tobase64><bean:write name="current" property="real_end_of_validity" format="dd/MM/yyyy"/></app:tobase64>",
  "<app:tobase64><bean:write name="current" property="price" format="#,##0.00;#,##0.00"/> &euro;</app:tobase64>",
  "<app:tobase64><bean:write name="current" property="amount_commission" format="#,##0.00;#,##0.00"/> &euro;</app:tobase64>",
  "<app:tobase64><bean:write name="current" property="amount_of_repayment" format="#,##0.00;#,##0.00"/> &euro;</app:tobase64>",
  "<logic:equal name="current" property="specific_agreement_exists" value="true"><app:tobase64><span class="glyphicon glyphicon-info-sign" data-toggle="tooltip" data-placement="left" title="Existen condiciones particulares"></span></app:tobase64></logic:equal>"]
</logic:iterate>
]
}

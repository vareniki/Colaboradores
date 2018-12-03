<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://www.cofrevip.com/partners/tags-app.tld" prefix="app" %>
{
"draw": 0,
"recordsFiltered": <bean:write name="total_records" />,
"data": [
<% boolean inicial = true; %>
<logic:iterate id="current" name="list_of_boxes_in_stock">
  <% if (inicial) {
      inicial = false;
    } else { %>,<% }%> [
  "<app:tobase64><bean:write name="current" property="ean8"/></app:tobase64>",
  "<app:tobase64><bean:write name="current" property="ean13"/></app:tobase64>",
  "<app:tobase64><bean:write name="current" property="box_name"/></app:tobase64>",
  "<app:tobase64><bean:write name="current" property="price" format="#,##0.00;#,##0.00"/></app:tobase64>",
  "<app:tobase64><bean:write name="current" property="end_of_validity" format="dd/MM/yyyy"/></app:tobase64>",
  "<app:tobase64><bean:write name="current" property="last_status_date" format="dd/MM/yyyy"/></app:tobase64>"]
</logic:iterate>
]
}

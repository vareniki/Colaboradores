<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://www.cofrevip.com/partners/tags-app.tld" prefix="app" %>
{
"draw": 0,
"recordsFiltered": <bean:write name="total_records" />,
"data": [
<% boolean inicial = true; %>
<logic:iterate id="current" name="list_of_actions">
  <% if (inicial) {
      inicial = false;
    } else { %>,<% }%> [
  "<app:tobase64><bean:write name="current" property="type_of_action"/></app:tobase64>",
  "<app:tobase64><bean:write name="current" property="date_of_action" format="dd/MM/yyyy HH:mm:ss"/></app:tobase64>",
  "<app:tobase64><bean:write name="current" property="login"/></app:tobase64>"]
</logic:iterate>
]
}
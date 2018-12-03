<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://www.cofrevip.com/partners/tags-app.tld" prefix="app" %>

<%@ page import="es.regalocio.partners.utils.WebPartnersUtils" %>
<%@ page import="es.regalocio.partners.shared.SessionInfo" %>
<% boolean expedientNumber = WebPartnersUtils.isExpedientNumberAvailable((SessionInfo) session.getAttribute(SessionInfo.SESSION_INFO)); %>
{
"draw": 0,
"recordsFiltered": <bean:write name="total_records" />,
"data": [
<% boolean inicial = true; %>
<logic:iterate id="current" name="list_of_sales">
    <% if (inicial) {
        inicial = false;
      } else { %>,<% }%> [
    "<app:tobase64><bean:write name="current" property="ean8"/></app:tobase64>",
    "<app:tobase64><bean:write name="current" property="thematic_name"/></app:tobase64>",
    "<app:tobase64><bean:write name="current" property="date_of_sale" format="dd/MM/yyyy"/></app:tobase64>"
    <% if (expedientNumber) {%>
    ,"<app:tobase64><bean:write name="current" property="expedient_number"/></app:tobase64>"
    <% }%>]
</logic:iterate>
]
}
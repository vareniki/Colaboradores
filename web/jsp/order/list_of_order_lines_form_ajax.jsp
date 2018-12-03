<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://www.cofrevip.com/partners/tags-app.tld" prefix="app" %> 
{
"draw": 0,
"data": [
<logic:present name="order_line_list">
  <% boolean inicial = true; %>
  <logic:iterate id="current" name="order_line_list" type="es.regalocio.partners.bean.OrderLineBean">
    <% if (inicial) {
        inicial = false;
      } else { %>,<% }%> [
    "<app:tobase64><bean:write name="current" property="thematicBox"/></app:tobase64>",
    "<app:tobase64><bean:write name="current" property="ean13"/></app:tobase64>",
    "<app:tobase64><bean:write name="current" property="endOfValidity" format="dd/MM/yyyy"/></app:tobase64>",
    "<app:tobase64><input name="ean13_<bean:write name="current" property="ean13" />" class="form-control text-center" type="text" value="<bean:write name="current" property="amount" />" maxlength="3"></app:tobase64>"]
  </logic:iterate>
</logic:present>
]
}
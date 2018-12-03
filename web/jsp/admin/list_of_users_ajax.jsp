<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://www.cofrevip.com/partners/tags-app.tld" prefix="app" %>
{
"draw": 0,
"recordsFiltered": <bean:write name="total_records" />,
"data": [
<% boolean inicial = true; %>
<logic:iterate id="current" name="list_of_users">
  <% if (inicial) {
      inicial = false;
    } else { %>,<% }%> [
  "<app:tobase64><html:link action="/update_user" paramName="current" paramProperty="id" paramId="user_id"><bean:write name="current" property="login"/></html:link></app:tobase64>",
  "<app:tobase64><bean:write name="current" property="firstname"/></app:tobase64>",
  "<app:tobase64><bean:write name="current" property="lastname"/></app:tobase64>",
  "<app:tobase64><bean:write name="current" property="email"/></app:tobase64>",
  "<app:tobase64><html:link action="/generate_new_user_password" paramName="current" paramProperty="id" paramId="user_id" onclick="return confirm('Comfirme que desea otorgar una nueva password a este usuario');"><img src="img/send-new-password.gif" title="Enviar una nueva password" border="0" /></html:link></app:tobase64>",
  "<app:tobase64><logic:equal name="current" property="enabled" value="true">
      <html:link action="/disable_account" paramName="current" paramProperty="id" paramId="user_id"><img src="img/enable.gif" title="Desactivar cuenta de usuario" border="0" /></html:link>
    </logic:equal><logic:notEqual name="current" property="enabled" value="true">
      <html:link action="/enable_account" paramName="current" paramProperty="id" paramId="user_id"><img src="img/disable.gif" title="Activar la cuenta de usuario" border="0" /></html:link></logic:notEqual></app:tobase64>"]
</logic:iterate>
]
}

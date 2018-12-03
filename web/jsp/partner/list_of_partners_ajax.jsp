<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://www.cofrevip.com/partners/tags-app.tld" prefix="app" %>
{
"draw": 0,
"recordsFiltered": <bean:write name="total_records" />,
"data": [
<% boolean inicial = true; %>
<logic:iterate id="current" name="list_of_partners">
  <% if (inicial) {
        inicial = false;
      } else { %>,<% }%> [
  "<app:tobase64><html:link action="/consult_partner_sheet" paramName="current" paramProperty="id" paramId="partner_id"><bean:write name="current" property="code" /></html:link></app:tobase64>",
  "<app:tobase64><html:link action="/consult_partner_sheet" paramName="current" paramProperty="id" paramId="partner_id"><bean:write name="current" property="name" /></html:link></app:tobase64>",
  "<app:tobase64><bean:write name="current" property="factname"/></app:tobase64>",
  "<app:tobase64><bean:write name="current" property="phone"/></app:tobase64>",
  "<app:tobase64><a href="mailto:<bean:write name='current' property='email'/>"><bean:write name="current" property="email"/></a></app:tobase64>",
  "<app:tobase64>
    <logic:notEmpty name="current" property="email">
        <html:link action="/generate_new_partner_password" paramName="current" paramProperty="id" paramId="partner_id"><img src="img/send-new-password.gif" title="Nueva password" border="0" /></html:link>
    </logic:notEmpty>
  </app:tobase64>",
  "<app:tobase64>
    <logic:equal name="current" property="enabled" value="true">
      <html:link action="/disable_partner" paramName="current" paramProperty="id" paramId="partner_id"><img src="img/enable.gif" title="Desactivar colaborador" border="0"/></html:link>
    </logic:equal>
    <logic:notEqual name="current" property="enabled" value="true">
      <html:link action="/enable_partner" paramName="current" paramProperty="id" paramId="partner_id"><img src="img/disable.gif" title="Activar colaborador" border="0"/></html:link>
    </logic:notEqual>
  </app:tobase64>"]
</logic:iterate>
]
}
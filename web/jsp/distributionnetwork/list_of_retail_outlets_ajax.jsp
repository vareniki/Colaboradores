<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://www.cofrevip.com/partners/tags-app.tld" prefix="app" %>
<jsp:useBean id="URLParameters" class="java.util.HashMap" scope="request"/>
{
"draw": 0,
"recordsFiltered": <bean:write name="total_records" />,
"data": [
<% boolean inicial = true; %>
<logic:iterate id="current" name="list_of_retail_outlets">
  <bean:define id="entity_id" name="current" property="entity_id" />
  <bean:define id="user_id" name="current" property="user_id" />
  <% if (inicial) {
      inicial = false;
    } else { %>,<% }%> [
  "<app:tobase64><html:link action="/update_retail_outlet" paramName="current" paramProperty="entity_id" paramId="entity_id"><bean:write name="current" property="name"/></html:link></app:tobase64>",
  "<app:tobase64><bean:write name="current" property="address"/></app:tobase64>",
  "<app:tobase64><bean:write name="current" property="contact"/></app:tobase64>",
  "<app:tobase64><bean:write name="current" property="factname"/></app:tobase64>",
  "<app:tobase64>
    <logic:notEmpty name="current" property="email">
      <% URLParameters.put("entity_id", entity_id); %>
      <logic:equal name="disable_change_password" value="false">
        <html:link action="/generate_new_retail_outlet_password" name="URLParameters"><img src="img/send-new-password.gif" title="Nueva password" border="0" /></html:link>
      </logic:equal>
      <% URLParameters.remove("entity_id");%>
    </logic:notEmpty>
  </app:tobase64>",
  "<app:tobase64>
    <% URLParameters.put("user_id", user_id); %>
    <logic:equal name="current" property="enabled" value="true">
      <html:link action="/disable_retail_outlet" name="URLParameters"><img src="img/enable.gif"
           title="Desactivar punto de venta"
           border="0"/></html:link>
    </logic:equal>
    <logic:notEqual name="current" property="enabled" value="true">
      <html:link action="/enable_retail_outlet" name="URLParameters"><img src="img/disable.gif"
           title="Activar punto de venta" border="0"/></html:link>
    </logic:notEqual>
    <% URLParameters.remove("user_id");%>
  </app:tobase64>"]
</logic:iterate>
]
}
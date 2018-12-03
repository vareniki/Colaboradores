<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://www.cofrevip.com/partners/tags-app.tld" prefix="app" %> 
{
"draw": 0,
"recordsFiltered": <bean:write name="total_records" />,
"data": [
<% boolean inicial = true; %>
<logic:iterate id="current" name="list_of_distribution_networks">
    <% if (inicial) {
        inicial = false;
      } else { %>,<% }%> [
    "<app:tobase64><html:link action="/consult_distribution_network_sheet" paramName="current" paramProperty="entity_id" paramId="entity_id"><bean:write name="current" property="name"/></html:link></app:tobase64>",
    "<app:tobase64><logic:equal name="current" property="gift_voucher_activation_on_sale" value="true">Activaci&oacute;n a la venta</logic:equal><logic:notEqual name="current" property="gift_voucher_activation_on_sale" value="true">Preactivaci&oacute;n</logic:notEqual></app:tobase64>",
    "<app:tobase64><bean:write name="current" property="retail_outlet_count"/></app:tobase64>",
    "<app:tobase64><logic:equal name="current" property="enabled" value="true">
        <html:link action="/disable_distribution_network" paramName="current" paramProperty="entity_id" paramId="entity_id"><img src="img/enable.gif" title="Desactivar red de distribuci&oacute;n" border="0"></html:link>
        </logic:equal><logic:notEqual name="current" property="enabled" value="true">
            <html:link action="/enable_distribution_network" paramName="current" paramProperty="entity_id" paramId="entity_id"><img src="img/disable.gif" title="Activar red de distribuci&oacute;n" border="0"></html:link>
        </logic:notEqual></app:tobase64>"]
</logic:iterate>
]
}

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://www.cofrevip.com/partners/tags-app.tld" prefix="app" %>
{
"draw": 0,
"recordsFiltered": <bean:write name="total_records" />,
"data": [
<% boolean inicial = true; %>
<logic:iterate id="current" name="list_of_retail_outlet_orders">
    <% if (inicial) {
        inicial = false;
      } else { %>,<% }%> [
    "<app:tobase64><html:link action="/update_retail_outlet_order" paramName="current" paramProperty="order_id" paramId="order_id"><bean:write name="current" property="number_of_order"/></html:link></app:tobase64>",
    "<app:tobase64><bean:write name="current" property="distribution_network_name"/></app:tobase64>",
    "<app:tobase64><bean:write name="current" property="retail_outlet_name"/></app:tobase64>",
    "<app:tobase64><logic:notEmpty name="current" property="order_form_reference"><bean:write name="current" property="order_form_reference"/></logic:notEmpty></app:tobase64>",
    "<app:tobase64><bean:write name="current" property="number_of_boxes"/></app:tobase64>",
    "<logic:equal name="current" property="envio_plataforma" value="1"><app:tobase64><span class="glyphicon glyphicon-ok"></span></app:tobase64></logic:equal>",
    "<app:tobase64><html:link action="/load_distribution_document" paramName="current" paramProperty="order_id" paramId="order_id" target="_blank"><img src="img/pdf.gif" title="PDF" border="0" /></html:link></app:tobase64>",
    "<app:tobase64><html:link action="/delete_retail_outlet_order" paramName="current" paramProperty="order_id" paramId="order_id" 
     onclick="return confirm('¿Está usted seguro que desear eliminar este pedido?');"><img src="img/bin.gif" title="Eliminar este pedido" border="0" /></html:link></app:tobase64>"
    ]
</logic:iterate>
]
}

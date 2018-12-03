<%@taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@taglib uri="http://www.cofrevip.com/partners/tags-app.tld" prefix="app" %>
<jsp:useBean id="removable_margins" class="java.util.ArrayList" scope="request" />
<jsp:useBean id="URLParameters" class="java.util.HashMap" scope="request" />
{
"draw": 0,
"recordsFiltered": <bean:write name="total_records" />,
"data": [
<% boolean inicial = true; %>
<logic:iterate id="current" name="list_of_distributor_margins" type="es.regalocio.partners.business.common.DistributorMargin" indexId="counter">
    <% URLParameters.put("distributor_margin_id", Integer.toString(current.getId()));%>
    <bean:define id="removable"><%=removable_margins.get(counter.intValue())%></bean:define>
    <app:staticdata id="thematic" typology="thematic" code="<%=Integer.toString(current.getThematicId())%>" />    

    <% if (inicial) {
    inicial = false;
  } else { %>,<% }%> [
    "<app:tobase64><html:link action="/update_distributor_margin" name="URLParameters"><bean:write name="thematic"/></html:link></app:tobase64>",
    "<app:tobase64><bean:write name="current" property="endOfValidity" format="dd/MM/yyyy"/></app:tobase64>",
    "<app:tobase64><bean:write name="current" property="margin" format="#,##0.00;#,##0.00"/> &euro;</app:tobase64>",
    "<app:tobase64>
        <logic:equal name="removable" value="true">
            <html:link action="/delete_distributor_margin" name="URLParameters" onclick="return confirm('¿está seguro de querer suprimir este margen?');"><img src="img/bin.gif" title="Eliminar este margen de distribución" border="0" /></html:link>
        </logic:equal>
    </app:tobase64>"]
</logic:iterate>
]
}

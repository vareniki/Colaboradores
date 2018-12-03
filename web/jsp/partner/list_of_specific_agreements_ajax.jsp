<%@page import="es.regalocio.partners.business.common.Thematic"%>
<%@page import="es.regalocio.partners.config.PartnersUtils"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://www.cofrevip.com/partners/tags-app.tld" prefix="app" %>
<jsp:useBean id="URLParameters" class="java.util.HashMap" scope="request" />
{
"draw": 0,
"recordsFiltered": <bean:write name="total_records" />,
"data": [
<% boolean inicial = true; %>
<logic:iterate id="current" name="list_of_specific_agreements" type="es.regalocio.partners.business.common.SpecificAgreement">
    <% URLParameters.put("specific_agreement_id", "" + current.getId());%>
    <% if (inicial) {
        inicial = false;
      } else { %>,<% }%> [
      "<app:tobase64><logic:equal name="readOnly" value="false"><logic:equal name="current" property="inherited" value="1"><bean:write name="current" property="thematicId" /> - <bean:write name="current" property="thematicName" /></logic:equal></logic:equal><logic:equal name="readOnly" value="true"><bean:write name="current" property="thematicId" /> - <bean:write name="current" property="thematicName" /></logic:equal><logic:notEqual name="readOnly" value="true"><logic:equal name="current" property="inherited" value="0"><html:link action="/update_specific_agreement" name="URLParameters"><bean:write name="current" property="thematicId" /> - <bean:write name="current" property="thematicName" /></html:link></logic:equal></logic:notEqual></app:tobase64>",
    "<app:tobase64><bean:write name="current" property="endOfValidity" format="dd/MM/yyyy"/></app:tobase64>",
    "<app:tobase64><logic:greaterThan name="current" property="priceService" value="0"><bean:write name="current" property="priceService" format="#,##0.00;#,##0.00"/> &euro;</logic:greaterThan></app:tobase64>",
    "<app:tobase64><bean:write name="current" property="commission" format="#,##0.00;#,##0.00"/> &euro;</app:tobase64>",
    "<app:tobase64><bean:write name="current" property="word" /></app:tobase64>",
    "<app:tobase64><logic:equal name="current" property="newAgreement" value="1">S&iacute;</logic:equal></app:tobase64>",
    "<app:tobase64><bean:write name="current" property="agreementSince" /></app:tobase64>",
    "<app:tobase64><logic:equal name="current" property="inherited" value="1">S&iacute;</logic:equal></app:tobase64>",
    "<app:tobase64><logic:equal name="current" property="inherited" value="0"><logic:notEqual name="readOnly" value="true"><html:link action="/delete_specific_agreement" name="URLParameters"><img src="img/bin.gif" title="" border="0" /></html:link></logic:notEqual></logic:equal></app:tobase64>"]
</logic:iterate>
]
}

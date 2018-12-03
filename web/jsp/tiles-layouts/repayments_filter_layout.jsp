<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.cofrevip.com/partners/tags-app.tld" prefix="app" %>

<app:errors title="multiple.action.on.repayments.errors.title" />
<app:information />

<logic:equal name="repaymentExists" value="true">
    <tiles:insert attribute="body"/>
</logic:equal>
<logic:notEqual name="repaymentExists" value="true">
    <div class="text-warning">No existe ning&uacute;n reembolso en este estado.</div>
</logic:notEqual>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.cofrevip.com/partners/tags-app.tld" prefix="app" %>				
<app:information />
<logic:notEqual name="orderExists" value="true">
  <div class="alert alert-warning" role="alert">No existen pedidos en curso.</div>
</logic:notEqual>
<logic:equal name="orderExists" value="true">
  <tiles:insert attribute="body"/>
</logic:equal>
<br>
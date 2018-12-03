<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.cofrevip.com/partners/tags-app.tld" prefix="app" %>

<%@ page import="java.util.MissingResourceException" %>
<%@ page import="es.regalocio.partners.utils.WebPartnersUtils" %>
<%@ page import="es.regalocio.partners.shared.SessionInfo" %>
<%@ page import="es.regalocio.partners.business.common.RetailOutlet" %>
<%
  boolean expedientNumber
          = WebPartnersUtils.isExpedientNumberAvailable((SessionInfo) session.getAttribute(SessionInfo.SESSION_INFO));
%>
<html:xhtml />			
<app:errors title="gift.voucher.activation.errors.title" />
<app:information />
<p class="text-info text-center">Por favor, escriba el n&deg; de cheque que desea activar:</p>
<html:form action="/gift_voucher_activation" styleClass="form-horizontal">
  <html:hidden property="run_once" />
  <% if (!expedientNumber) { %>
  <html:hidden property="expedient_number" value="(NA)" />
  <% } else { %>
  <bean:define id="personalInfo" name="SESSION_INFO" property="personal" />
  <div class="form-group">
    <label class="col-sm-5 control-label">
      <logic:present name="personalInfo" property="expedientText"><bean:write name="personalInfo" property="expedientText" filter="no" />:</logic:present>
      <logic:notPresent name="personalInfo" property="expedientText">N&deg; de expediente:</logic:notPresent>
      </label>
      <div class="col-sm-3"><html:text styleClass="form-control" property="expedient_number" maxlength="20" /></div>
  </div>
  <% }%>
  <div class="form-group">
    <label class="col-sm-5 control-label">N&deg; de cheque:</label>
    <div class="col-sm-1"><input class="form-control pull-right" type="text" value="0" disabled="disabled" style="width:36px;"></div>
    <div class="col-sm-3"><html:text styleClass="form-control" property="ean8" maxlength="8" /></div>
  </div>
  <div class="form-group">
    <label class="col-sm-5 control-label">&nbsp;</label>
    <div class="col-sm-3 text-right"><html:submit styleClass="btn btn-primary">Activar cheque</html:submit></div>
  </div>
</html:form>
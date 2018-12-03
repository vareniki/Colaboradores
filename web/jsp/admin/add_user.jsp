<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://www.cofrevip.com/partners/tags-app.tld" prefix="app" %> 
<html:xhtml />
<app:errors title="add.user.errors.title" />
<p class="text-primary">Por favor, escoja los siguientes datos relativos al usuario sin omitir otorgarle al menos un perfil:</p>
<hr>
<html:form action="/add_user" styleClass="form-horizontal">
  <html:hidden property="run_once" />
  <div class="form-group">
    <label class="col-sm-4 control-label">Identificaci&oacute;n:</label>
    <div class="col-sm-8">
      <html:text styleClass="form-control" property="login" maxlength="32" />
    </div>
  </div>
  <div class="form-group">
    <label class="col-sm-4 control-label">Nombre:</label>
    <div class="col-sm-8">
      <html:text styleClass="form-control" property="firstname" maxlength="64" />
    </div>
  </div>

  <div class="form-group">
    <label class="col-sm-4 control-label">Apellidos:</label>
    <div class="col-sm-8">
      <html:text styleClass="form-control" property="lastname" maxlength="32" />
    </div>
  </div>

  <div class="form-group">
    <label class="col-sm-4 control-label">EMail:</label>
    <div class="col-sm-8">
      <html:text styleClass="form-control" property="email" maxlength="256" />
    </div>
  </div>
  <div class="row">
    <div class="col-sm-4">&nbsp;</div>
    <div class="col-sm-8">
      <h4>Perfiles de usuario:</h4>
      <logic:iterate id="current" name="profile_list" indexId="index">
        <div class="checkbox">
          <label>
            <html:multibox property="profile_code_array">
              <bean:write name="current" property="code" />
            </html:multibox>
            <bean:write name="current" />
          </label>
        </div>
      </logic:iterate>
    </div>
  </div>
  <hr>
  <div class="text-right">
    <html:submit styleClass="btn btn-primary">Enviar</html:submit>
    </div>
</html:form>

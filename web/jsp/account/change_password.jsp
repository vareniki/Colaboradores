<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://www.cofrevip.com/partners/tags-app.tld" prefix="app" %> 
<html:xhtml />
<app:errors title="change.password.errors.title" />
<p class="text-primary">Por favor, teclee su antigua password, escoja una nueva y conf&iacute;rmela.</p>

<html:form action="/change_password" styleClass="form-horizontal">
  <html:hidden property="run_once" />

  <div class="form-group">
    <label class="col-sm-4 control-label">Su anterior password:</label>
    <div class="col-sm-3">
      <html:password property="password" maxlength="32" styleClass="form-control" />
    </div>
  </div>

  <div class="form-group">
    <label class="col-sm-4 control-label">Su nueva password:</label>
    <div class="col-sm-3">
      <html:password property="new_password" maxlength="32" styleClass="form-control" />
    </div>
    <div class="col-sm-3 col-lg-offset-2">
      <html:password property="confirm_password" maxlength="32" styleClass="form-control" />
    </div>
  </div>
  <hr>
  <div class="form-group">
    <div class="col-sm-4 col-sm-offset-4">
      <html:submit styleClass="btn btn-primary">Actualizar</html:submit>
      </div>
    </div>
</html:form>
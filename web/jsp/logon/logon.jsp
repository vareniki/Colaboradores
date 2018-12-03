<!DOCTYPE html>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.cofrevip.com/partners/tags-app.tld" prefix="app" %>
<html:html>
  <head>
    <meta http-equiv="Expires" content="0"/>
    <meta http-equiv="Pragma" content="no-cache"/>
    <meta http-equiv="Cache-Control" content="must-revalidate, proxy-revalidate, max-age=0, no-cache, no-store, private"/>
    <link rel="shortcut icon" href="favicon.ico" type="image/vnd.microsoft.icon" />
    <link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="//code.jquery.com/jquery-3.2.1.min.js"></script>
    <title>Espacio Colaboradores</title>
  </head>
  <body>
    <app:errors title="logon.errors.title" />
    <div class="container">
      <img src="img/cabecera-colaboradores.png" alt="CofreVIP" class="img-responsive" />

      <div class="row" style="margin-top: 30px">
        <div class="col-xs-12 col-sm-3">&nbsp;</div>
        <div class="col-xs-12 col-sm-6">
          <html:form action="/logon" styleClass="form-horizontal">
            <html:hidden property="redirect_path"/>
            <html:hidden property="run_once"/>
            <div class="form-group">
              <label class="col-sm-4">Usuario</label>
              <div class="col-sm-8">
                <html:text property="login" styleClass="form-control"/>
              </div>
            </div>
            <div class="form-group">
              <label class="col-sm-4">Password</label>
              <div class="col-sm-8">
                <html:password property="password" styleClass="form-control"/>
              </div>
            </div>
            <div class="form-group">
              <div class="col-sm-offset-4 col-sm-8">
                <html:submit styleClass="btn btn-primary" value="Acceso"/>
              </div>
            </div>
          </html:form>
        </div>
        <div class="col-xs-12 col-sm-3">&nbsp;</div>
      </div>
      <img src="img/banner-colaboradores.png" alt="Colaboradores" class="img-responsive" style="margin: 30px auto 0 auto" />
    </div>
  </body>
</html:html>

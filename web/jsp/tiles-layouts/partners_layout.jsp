<!DOCTYPE html>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<html:html>
  <head>
    <tiles:useAttribute id="title" classname="java.lang.String" name="title" ignore="true"/>
    <title>Colaboradores<logic:notEmpty name="title"> - <bean:message key="<%=title%>"/></logic:notEmpty></title>
    <meta http-equiv="Content-Style-Type" content="text/css" />
    <meta http-equiv="Expires" content="0" />
    <meta http-equiv="Pragma" content="no-cache" />
    <meta http-equiv="Cache-Control" content="must-revalidate, proxy-revalidate, max-age=0, no-cache, no-store, private" />
    <link rel="shortcut icon" href="favicon.ico" type="image/vnd.microsoft.icon" />

    <link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css">
    <link rel="stylesheet" href="//cdn.datatables.net/plug-ins/1.10.16/integration/bootstrap/3/dataTables.bootstrap.css">

    <script type="text/javascript" src="//code.jquery.com/jquery-3.2.1.min.js"></script>
    <script type="text/javascript" src="//maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="//cdn.datatables.net/1.10.16/js/jquery.dataTables.min.js"></script>
    <script type="text/javascript" src="//cdn.datatables.net/plug-ins/1.10.16/integration/bootstrap/3/dataTables.bootstrap.js"></script>
    <script type="text/javascript" src="//cdn.jsdelivr.net/jquery.validation/1.16.0/jquery.validate.min.js"></script>
    <script type="text/javascript" src="js/bootbox.min.js"></script>
    <script type="text/javascript" src="js/base64.js"></script>
    <script type="text/javascript" src="js/json2.js"></script>

    <script type="text/javascript">

      $.extend($.validator.messages, {
        required: "Este campo es obligatorio.",
        remote: "Por favor, rellena este campo.",
        email: "Por favor, escribe una dirección de correo válida.",
        url: "Por favor, escribe una URL válida.",
        date: "Por favor, escribe una fecha válida.",
        dateISO: "Por favor, escribe una fecha (ISO) válida.",
        number: "Por favor, escribe un número válido.",
        digits: "Por favor, escribe sólo dígitos.",
        creditcard: "Por favor, escribe un número de tarjeta válido.",
        equalTo: "Por favor, escribe el mismo valor de nuevo.",
        extension: "Por favor, escribe un valor con una extensión aceptada.",
        maxlength: $.validator.format("Por favor, no escribas más de {0} caracteres."),
        minlength: $.validator.format("Por favor, no escribas menos de {0} caracteres."),
        rangelength: $.validator.format("Por favor, escribe un valor entre {0} y {1} caracteres."),
        range: $.validator.format("Por favor, escribe un valor entre {0} y {1}."),
        max: $.validator.format("Por favor, escribe un valor menor o igual a {0}."),
        min: $.validator.format("Por favor, escribe un valor mayor o igual a {0}."),
        nifES: "Por favor, escribe un NIF válido.",
        nieES: "Por favor, escribe un NIE válido.",
        cifES: "Por favor, escribe un CIF válido."
      });
    </script>
  </head>
  <body>
    <article class="container">
      <header>
        <bean:define id="personalInfo" name="SESSION_INFO" property="personal" />
        <logic:notEmpty name="personalInfo" property="logo">
          <img src='<bean:write name="personalInfo" property="logo" />' alt="Cofre VIP - Colaboradores" />
        </logic:notEmpty>
        <logic:empty name="personalInfo" property="logo">
          <div style="text-align: center">
            <img src="img/cabecera-colaboradores-990.png" alt="Cofre VIP - Colaboradores" />
          </div>
        </logic:empty>
      </header>
      <main>
        <tiles:insert attribute="menu"/>       
        <logic:notEmpty name="title"><h4 class="text-center text-uppercase"><bean:message key="<%=title%>"/></h4><hr></logic:notEmpty>
        <tiles:insert attribute="body"/>
      </article>
    </div>
  </body>
</html:html>
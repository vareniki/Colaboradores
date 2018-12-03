<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://www.cofrevip.com/partners/tags-app.tld" prefix="app" %>

<nav class="navbar navbar-default">
  <div class="container-fluid">
    <div class="navbar-header">
      <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
        <span class="sr-only">Men&uacute;</span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
    </div>

    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
      <ul class="nav navbar-nav">
        <li class="dropdown">
          <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"><span class="glyphicon glyphicon-home"></span> <span class="caret"></span></a>
          <ul class="dropdown-menu">
            <li><html:link action="/home">Inicio</html:link></li>
        <app:inprofiles profiles="9">
          <li><html:link action="/retail_outlet_update_our_informations">Informaci&oacute;n de usuario</html:link></li>
        </app:inprofiles>
        <app:inprofiles profiles="7">
          <li><html:link action="/partner_update_our_informations">Informaci&oacute;n de usuario</html:link></li>
        </app:inprofiles>

        <app:inprofiles profiles="1">

          <li><html:link action="/list_of_users">Lista de usuarios</html:link></li>
          <li><html:link action="/add_user">A&ntilde;adir usuario</html:link></li>
        </app:inprofiles>

        <app:inprofiles profiles="1,2,3,4,5,6,8">
          <li role="separator" class="divider"></li>
          <li><html:link action="/change_password">Modificar contrase&ntilde;a</html:link></li>
        </app:inprofiles>

        <li role="separator" class="divider"></li>
        <li><html:link action="/logout">Cerrar sesi&oacute;n</html:link></li>
      </ul>
      </li>
      <app:inprofiles profiles="2">
        <li class="dropdown">
          <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Colaboradores <span class="caret"></span></a>
          <ul class="dropdown-menu">
            <li><html:link action="/list_of_partners">Lista de colaboradores</html:link></li>
        <li><html:link action="/add_partner">A&ntilde;adir colaborador</html:link></li>
        <li role="separator" class="divider"></li>
        <li><html:link action="/list_of_repayments">Lista de reembolsos</html:link></li>
        </ul>
        </li>
      </app:inprofiles>
      <app:inprofiles profiles="8,3">

        <li class="dropdown">
          <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Distribuidores <span class="caret"></span></a>
          <ul class="dropdown-menu">
            <li><html:link action="/list_of_distribution_networks">Redes de distribuci&oacute;n</html:link></li>
        <li><html:link action="/add_distribution_network">A&ntilde;adir red de distribuci&oacute;n</html:link></li>
        </ul>
        </li>

        <app:inprofiles profiles="3">
          <li class="dropdown">
            <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Pedidos <span class="caret"></span></a>
            <ul class="dropdown-menu">
              <li><html:link action="/list_of_retail_outlet_orders">Pedidos en curso</html:link></li>
          <li><html:link action="/add_retail_outlet_order">Generar pedido</html:link></li>
          </ul>
        </app:inprofiles>
        </li>

      </app:inprofiles>
      <app:inprofiles profiles="1,2,3,4,5,6">
        <app:notInprofiles profiles="6">
          <li><html:link action="/follow_up_of_gift_vouchers">Cheques</html:link></li>
        </app:notInprofiles>
        <app:inprofiles profiles="6">
          <li class="dropdown">
            <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Cheques <span class="caret"></span></a>
            <ul class="dropdown-menu">
              <li><html:link action="/follow_up_of_gift_vouchers">Comprobar cheque</html:link></li>
          <li role="separator" class="divider"></li>
          <li><html:link action="/create_ean8">Crear nuevos EAN8</html:link></li>
          </ul>
          </li>
        </app:inprofiles>
      </app:inprofiles>
      <app:inprofiles profiles="7">
        <li class="dropdown">
          <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Cheques <span class="caret"></span></a>
          <ul class="dropdown-menu">
            <li><html:link action="/gift_voucher_status">Consultar validez</html:link></li>
        </ul>
        </li>

        <li class="dropdown">
          <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Reembolsos <span class="caret"></span></a>
          <ul class="dropdown-menu">
            <li><html:link action="/add_repayment">Solicitar reembolso</html:link></li>
        <li><html:link action="/list_of_repayments">Lista de reembolsos</html:link></li>
        </ul>
        </li>
      </app:inprofiles>

      <app:inprofiles profiles="10,13">
        <li class="dropdown">
          <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Ventas <span class="caret"></span></a>
          <ul class="dropdown-menu">
            <app:inprofiles profiles="10">
              <li><html:link action="/list_of_sales">Hist&oacute;rico de ventas</html:link></li>
              <app:inprofiles profiles="13">
                <li role="separator" class="divider"></li>
                <li><html:link action="/gift_voucher_activation">Registrar venta</html:link></li>
                <li><html:link action="/gift_voucher_return">Registrar devoluci&oacute;n</html:link></li>
              </app:inprofiles>
            </app:inprofiles>
          </ul>
        </li>  
      </app:inprofiles>
      <app:inprofiles profiles="9">
        <li class="dropdown">
          <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Stock <span class="caret"></span></a>
          <ul class="dropdown-menu">
            <li><html:link action="/list_of_boxes_in_stock">Control de stock</html:link></li>
        <li><html:link action="/retail_outlet_add_order">Propuesta de pedido</html:link></li>
        </ul>
        </li>  
      </app:inprofiles>
      </ul>
    </div>
  </div>
</nav>
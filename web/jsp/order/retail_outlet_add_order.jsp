<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://www.cofrevip.com/partners/tags-app.tld" prefix="app" %>
<%@ page language="java" import="org.apache.struts.Globals"%>
<html:xhtml />
<app:information />
<div class="bg-warning alert">
  <strong><u>Esta solicitud no es un pedido en firme</u></strong>, se revisar&aacute; y servir&aacute; en funci&oacute;n del stock disponible.
  En el caso de necesitar alg&uacute;n cofre de forma segura y/o urgente, o de necesitar m&aacute;s de 2 unidades de una misma tem&aacute;tica 
  deben indicarlo en el campo "OBSERVACIONES" detallando de qu&eacute; cofre se trata y la fecha en que tiene que entregarse. 
  No se servir&aacute;n cofres de los cuales ya tenga disponibilidad en el stock de su agencia.<br><br>

  Para poder asegurar la recepci&oacute;n de su pedido correctamente, les informamos que no se atender&aacute;n propuestas de pedido por Email.
  <hr>
  Los gastos de env&iacute;o son de 4 &euro; para env&iacute;os normales (3, 4 d&iacute;as h&aacute;biles) y 8 &euro; para env&iacute;os urgentes.<br>
  <strong>MUY IMPORTANTE:</strong> los cofres tienen que entregarse al cliente siempre activados, 
  existiendo un plazo de 15 d&iacute;as con el producto sin desembalar para su cambio o devoluci&oacute;n.
</div>
<div class="alert alert-danger">
  <p>Los siguientes cofres est&aacute;n agotados:</p><br>
  <ul>
    <li>Aventura Extrema</li>
    <li>M&aacute;xima Emoci&oacute;n</li>
  </ul>
</div>
<bean:define id="action_name" name="<%=Globals.MAPPING_KEY%>" property="path" type="java.lang.String" />
<app:inprofiles profiles="10,13">
  <app:inprofiles profiles="9">

    <html:form action="<%=action_name%>" styleClass="form-inline">
      <html:hidden property="run_once" />
      <logic:present name="orderLines">
        <table id="list_of_retails" class="table table-striped table-condensed table-bordered table-hover" cellspacing="0" width="100%">
          <thead>
            <tr>
              <th>Cofre</th>
              <th>EAN13</th>
              <th>PVP</th>
              <th>Unid.</th>
            </tr>
          </thead>
          <tbody>
          <logic:iterate id="orderLine" name="orderLines">
            <input type="hidden" name="ean13_array" value="<bean:write name='orderLine' property='ean13' />" />
            <tr>
              <td><bean:write name="orderLine" property="thematicName" /></td>
            <td class="text-center"><bean:write name="orderLine" property="ean13" /></td>
            <td class="text-center"><bean:write name="orderLine" property="price" format="#,###.##" /> &euro;</td>
            <td class="text-center">
            <logic:notPresent name="result">
              <input type="number" name="amount_array" size="1" value="0" max="2" styleClass="form-control" />
            </logic:notPresent>
            <logic:present name="result">
              <bean:write name="orderLine" property="amount" format="#,###.##" />
            </logic:present>
            </td>
            </tr>
          </logic:iterate>
          </tbody>
        </table>

      </logic:present>
      <h3>Observaciones</h3>
      <logic:present name="result">
        <p><bean:write name="message" /></p>
      </logic:present>
      <logic:notPresent name="result">
        <textarea name="message" class="form-control" style="width: 100%" rows="4"></textarea>
        <hr>
        <div class="text-right">
          <html:submit styleClass="form-control btn btn-primary" value="Enviar">Enviar</html:submit>
        </div>
        <br><br>
      </logic:notPresent>
    </html:form>

  </app:inprofiles>
</app:inprofiles>

<app:notInprofiles profiles="10,13">
  <div class="text-danger alert">No es posible realizar pedidos puesto que usted se encuentra en prepago. 
    Para m&aacute;s informaci&oacute;n puede contactarnos a trav&eacute;s de 
    <a href="mailto:agencias@cofrevip.com">agencias@cofrevip.com</a>.</div>
</app:notInprofiles>
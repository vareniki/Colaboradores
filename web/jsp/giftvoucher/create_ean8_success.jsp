<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<p class="text-success">
    Sus nuevos n&uacute;meros de cheque han sido creados, recibiendo un email con el enlace al mismo.
</p>
<p class="text-info">
    Tambi&eacute;n es posible descargar el fichero pinchando el siguiente enlace:
    <html:link action="/download_ean8_file.do" paramName="ean8_filename" paramId="ean8_filename"><bean:write name="ean8_filename"/></html:link>.
</p>
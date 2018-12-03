package es.regalocio.partners.struts.action.partner;

import es.regalocio.partners.business.common.StaticDefinition;
import es.regalocio.partners.business.common.sql.OptionalSQLClause;
import es.regalocio.partners.business.services.PartnerService;
import es.regalocio.partners.config.PartnersServices;
import es.regalocio.partners.struts.action.AjaxAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ListOfPartnersAjaxAction extends AjaxAction {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request, HttpServletResponse response) {

        PartnerService service = PartnersServices.createPartnerService();
        OptionalSQLClause optionalSQL = createOptionalSQLClause(request, StaticDefinition.PARTNER_COLUMNS);
        request.setAttribute("list_of_partners", service.loadAllPartners(
                optionalSQL, Boolean.valueOf(request.getParameter("only_enabled"))));
        request.setAttribute("total_records", service.getRowCount());

        return mapping.findForward("success");
    }
}
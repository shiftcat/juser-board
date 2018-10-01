package han.role;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.config.ModuleConfig;
import org.apache.struts.tiles.TilesRequestProcessor;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class RoleRequestProcessor extends TilesRequestProcessor
{
	private RoleManager roleManager;
	
	
	public void init(ActionServlet servlet, ModuleConfig moduleConfig) throws ServletException
	{
		super.init(servlet, moduleConfig);
		WebApplicationContext wac = 
			WebApplicationContextUtils.getRequiredWebApplicationContext(servlet.getServletContext());
		
		roleManager = (RoleManager)wac.getBean("role");
	}
	 
	 
	private void saveErrors(HttpServletRequest request, ActionMessages errors)
	{
		// Remove any error messages attribute if none are required
		if ((errors == null) || errors.isEmpty()) {
			request.removeAttribute(Globals.MESSAGES_KEY);

			return;
		}

		// Save the error messages we need
		request.setAttribute(Globals.ERROR_KEY, errors);
	}

	public boolean processPreprocess(HttpServletRequest request, HttpServletResponse response)
	{
		try{
			String path = processPath(request, response);
			if (path == null) {
				return false;
			}
			ActionMapping mapping = processMapping(request, response, path);
	
			if (mapping == null) {
				return false;
			}
	
			if (log.isDebugEnabled()) {
				log.debug("RoleRequestProcessor execute");
			}
	
			ActionMessages messages = new ActionMessages();
			if (roleManager.isAdmin(request)) {
				log.info("현재 접속자는 관리자 입니다.");
			}
			if (!roleManager.isAllow(request)) {
				messages.add("RoleFilter", new ActionMessage("errors.global.denyAccess"));
				saveErrors(request, messages);
				ActionForward forward = mapping.findForward("fw-error");
				processForwardConfig(request, response, forward);
				return false;
			}
		} catch(Exception e) {
			log.error(e.getMessage(), e);
			return false;
		}
		return true;
	}
}

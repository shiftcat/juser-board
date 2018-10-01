package han.juser.controller.action;

import han.juser.controller.manager.CategoryManager;
import han.juser.controller.manager.ManagerHelper;
import han.juser.controller.manager.VisitorManager;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.actions.MappingDispatchAction;

public abstract class ActionBase extends MappingDispatchAction
{
	protected Log log = LogFactory.getLog(ActionBase.class);
	
	
	protected CategoryManager categoryManager;
	
	protected VisitorManager visitorManager;
	
	protected static final String MANAGER_EXCEPTION = "han.juser.controller.manager.ManagerException";
	
	
	public void setServlet(ActionServlet servlet) 
	{
		super.setServlet(servlet);
		categoryManager = ManagerHelper.getCategoryManager(servlet.getServletContext());
		visitorManager = ManagerHelper.getVisitorManager(servlet.getServletContext());
	}
	
	
	public void setCategoryManager(CategoryManager cm)
	{
		categoryManager = cm;
		if(log.isDebugEnabled()) {
			log.debug("===== category manager setting =======");
		}
	}
	
	
	public void setVisitorManager(VisitorManager vm)
	{
		visitorManager = vm;
	}
	
	
	protected ActionForward resultMessage(ActionMapping mapping, HttpServletRequest req, String key, String redirectURL)
	{
		ActionMessages messages = new ActionMessages();
		messages.add("message", new ActionMessage(key));
		saveMessages(req, messages);
		req.setAttribute("redirect", redirectURL);
		return mapping.findForward("fw-message");
	}


	protected ActionForward errorMessage(ActionMapping mapping, HttpServletRequest req, String key)
	{
		ActionErrors errors = new ActionErrors();
		errors.add("error", new ActionMessage(key));
		saveErrors(req, errors);
		return mapping.findForward("fw-error");
	}
	

	protected String getRealPath(String path)
	{
		ServletContext context = getServlet().getServletContext();
		return context.getRealPath(path);
	}
	
	protected void common(HttpServletRequest req)
	{
		setSubMenu(req, "subMenu");
		visitorManager.setVisitCount(req, "visitCount");
		visitorManager.setTodayCount(req, "todayCount");
		
	}
	
	public abstract void setSubMenu(HttpServletRequest req, String key);
	
}

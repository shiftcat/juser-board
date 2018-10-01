package han.juser.controller.manager;


import han.juser.service.ServiceHelper;
import han.juser.service.VisitorService;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class VisitorManager 
{	
	VisitorService service;
	
	
	private static final String VISITOR_SESSION_KEY = "han.juser.controller.manager.VisitorManager";
	
	public VisitorManager()
	{
		super();
	}
	
	
	public void setService(VisitorService svc)
	{
		this.service = svc;
	}
	
	
	
	public VisitorService getService(ServletContext context)
	{
		return ServiceHelper.getVisitorService( context );
	}
	
	
	public void setVisitor(HttpServletRequest req)
	{
		HttpSession session = req.getSession();
		synchronized(session) {
			if( session.getAttribute(VISITOR_SESSION_KEY) == null ) {
				service.insert(req.getHeader("USER-AGENT"), 
						req.getRemoteAddr(), req.getRemoteHost(), req.getHeader("referer"));
				session.setAttribute(VISITOR_SESSION_KEY, VISITOR_SESSION_KEY);
			}
		}
	}
	
	
	public void setVisitor(ServletContext context, HttpServletRequest req)
	{
		HttpSession session = req.getSession();
		synchronized(session) {
			if( session.getAttribute(VISITOR_SESSION_KEY) == null ) {
				getService(context).insert(req.getHeader("USER-AGENT"), 
						req.getRemoteAddr(), req.getRemoteHost(), req.getHeader("referer"));
				session.setAttribute(VISITOR_SESSION_KEY, VISITOR_SESSION_KEY);
			}
		}
	}
	
	
	
	public void setVisitCount(HttpServletRequest req, String key)
	{
		Long cnt = service.getVisitCount();
		req.setAttribute(key, cnt);
	}
	
	
	public void setTodayCount(HttpServletRequest req, String key)
	{
		Long cnt = service.getTodayCount();
		req.setAttribute(key, cnt);
	}

}

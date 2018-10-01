package han.juser.controller.manager;

import javax.servlet.ServletContext;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class ManagerHelper 
{
	private static final String BORADMANAGER_BEAN = "boardManager";
	
	private static final String GUESTMANAGER_BEAN = "guestManager";
	
	private static final String MEMBERMANAGER_BEAN = "memberManager";
	
	private static final String JBOARDMANAGER_BEAN = "jboardManager";
	
	private static final String BLOGMANAGER_BEAN = "blogManager";
	
	private static final String VISITORMANAGER_BEAN = "visitorManager";
	
	private static final String CATEGORYMANAGER_BEAN = "categoryManager";
	
	private static final String MAINMANAGER_BEAN = "mainManager";
	
	
	public static MainManager getMainManager(ServletContext ctx)
	{
		WebApplicationContext wac = 
			WebApplicationContextUtils.getRequiredWebApplicationContext(ctx);
		return (MainManager)wac.getBean(MAINMANAGER_BEAN);
	}
	
	
	
	public static VisitorManager getVisitorManager(ServletContext ctx)
	{
		WebApplicationContext wac = 
			WebApplicationContextUtils.getRequiredWebApplicationContext(ctx);
		return (VisitorManager)wac.getBean(VISITORMANAGER_BEAN);
		
	}
	
	
	public static BoardManager getBoardManager(ServletContext ctx)
	{
		WebApplicationContext wac = 
			WebApplicationContextUtils.getRequiredWebApplicationContext(ctx);
		return (BoardManager)wac.getBean(BORADMANAGER_BEAN);
		
	}
	
	
	public static GuestManager getGuestManager(ServletContext ctx)
	{
		WebApplicationContext wac = 
			WebApplicationContextUtils.getRequiredWebApplicationContext(ctx);
		return (GuestManager)wac.getBean(GUESTMANAGER_BEAN);
		
	}
	
	
	
	public static MemberManager getMemberManager(ServletContext ctx)
	{
		WebApplicationContext wac = 
			WebApplicationContextUtils.getRequiredWebApplicationContext(ctx);
		return (MemberManager)wac.getBean(MEMBERMANAGER_BEAN);
		
	}
	
	
	public static JBoardManager getJBoardManager(ServletContext ctx)
	{
		WebApplicationContext wac = 
			WebApplicationContextUtils.getRequiredWebApplicationContext(ctx);
		return (JBoardManager)wac.getBean(JBOARDMANAGER_BEAN);
		
	}
	
	
	public static BlogManager getBlogManager(ServletContext ctx)
	{
		WebApplicationContext wac = 
			WebApplicationContextUtils.getRequiredWebApplicationContext(ctx);
		return (BlogManager)wac.getBean(BLOGMANAGER_BEAN);
		
	}
	
	
	
	public static CategoryManager getCategoryManager(ServletContext ctx)
	{
		WebApplicationContext wac = 
			WebApplicationContextUtils.getRequiredWebApplicationContext(ctx);
		return (CategoryManager)wac.getBean(CATEGORYMANAGER_BEAN);
	}
}

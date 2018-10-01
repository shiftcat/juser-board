package han.juser.service;

import javax.servlet.ServletContext;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class ServiceHelper 
{
	private static final String BOARDSERVICE_BEANID = "boardService";
	
	private static final String GUESTSERVICE_BEAN = "guestService";
	
	private static final String MEMBERSERVICE_BEAN = "memberService";
	
	private static final String JBOARDSERVICE_BEAN = "jboardService";
	
	private static final String CATEGORYSERVICE_BEAN = "categoryService";
	
	private static final String BLOGSERVICE_BEAN = "blogService";
	
	private static final String VISITORSERVICE_BEAN = "visitorService";
	
	
	public static VisitorService getVisitorService(ServletContext ctx)
	{
		WebApplicationContext wac = 
			WebApplicationContextUtils.getRequiredWebApplicationContext(ctx);
		return (VisitorService)wac.getBean(VISITORSERVICE_BEAN);
	}
	
	public static BoardService getBoardService(ServletContext ctx)
	{
		WebApplicationContext wac = 
			WebApplicationContextUtils.getRequiredWebApplicationContext(ctx);
		return (BoardService)wac.getBean(BOARDSERVICE_BEANID);
	}
	
	
	
	public static GuestService getGuestService(ServletContext ctx)
	{
		WebApplicationContext wac = 
			WebApplicationContextUtils.getRequiredWebApplicationContext(ctx);
		return (GuestService)wac.getBean(GUESTSERVICE_BEAN);
	}
	
	
	public static MemberService getMemberService(ServletContext ctx)
	{
		WebApplicationContext wac =
			WebApplicationContextUtils.getRequiredWebApplicationContext(ctx);
		return (MemberService)wac.getBean(MEMBERSERVICE_BEAN);
	}
	
	
	
	public static JBoardService getJBoardService(ServletContext ctx)
	{
		WebApplicationContext wac =
			WebApplicationContextUtils.getRequiredWebApplicationContext(ctx);
		return (JBoardService)wac.getBean(JBOARDSERVICE_BEAN);
	}
	
	
	public static CategoryService getCategoryService(ServletContext ctx)
	{
		WebApplicationContext wac =
			WebApplicationContextUtils.getRequiredWebApplicationContext(ctx);
		return (CategoryService)wac.getBean(CATEGORYSERVICE_BEAN);
	}
	
	
	public static BlogService getBlogService(ServletContext ctx)
	{
		WebApplicationContext wac =
			WebApplicationContextUtils.getRequiredWebApplicationContext(ctx);
		return (BlogService)wac.getBean(BLOGSERVICE_BEAN);
	}
}

package han.juser.tag.role;

import han.juser.tag.link.LinkTagSupport;
import han.role.RoleManager;
import han.role.RoleManagerImpl;

import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class RoleTagSupport extends BodyTagSupport
{
	protected RoleManager roleManager;
	
	public void release()
	{
		roleManager = null;
		super.release();
	}
	
	public void setPageContext(PageContext pageContext)
	{
		super.setPageContext(pageContext);
		WebApplicationContext wac = 
			WebApplicationContextUtils.getRequiredWebApplicationContext(pageContext.getServletContext());
		
		roleManager = (RoleManager)wac.getBean("role");
	}
	
}

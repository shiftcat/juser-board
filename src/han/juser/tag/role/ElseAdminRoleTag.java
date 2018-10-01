package han.juser.tag.role;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;

public class ElseAdminRoleTag extends RoleTagSupport 
{
	public int doStartTag() throws JspException
	{
		if( !roleManager.isAdmin( (HttpServletRequest)pageContext.getRequest()) ) {
			return EVAL_BODY_INCLUDE;
		}else {
			return SKIP_BODY;
		}
	}
	
	public int doEndTag() throws JspException
	{
		return EVAL_PAGE;
	}

}

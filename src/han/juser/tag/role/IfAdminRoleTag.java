package han.juser.tag.role;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;

public class IfAdminRoleTag extends RoleTagSupport
{
	
	public int doStartTag() throws JspException
	{
		if( roleManager.isAdmin( (HttpServletRequest)pageContext.getRequest()) ) {
			return EVAL_BODY_INCLUDE;
		}else {
			return SKIP_BODY;
		}
	}
	
	public int doEndTag() throws JspException
	{
		//TagUtils.getInstance().write(super.pageContext, bodyContent.getString());
		return EVAL_PAGE;
	}

}

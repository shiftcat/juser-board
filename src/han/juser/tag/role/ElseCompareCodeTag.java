package han.juser.tag.role;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;

public class ElseCompareCodeTag extends RoleTagSupport 
{
	private String code;
	
	public void setCode(String code) 
	{
		this.code = code;
	}
	
	public void release()
	{
		super.release();
		this.code = null;
	}
	
	public int doStartTag() throws JspException
	{
		if( !roleManager.compareCode((HttpServletRequest)pageContext.getRequest(), code)) {
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

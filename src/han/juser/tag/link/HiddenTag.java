package han.juser.tag.link;


import javax.servlet.jsp.JspException;

import org.apache.struts.taglib.TagUtils;

public class HiddenTag extends LinkTagSupport
{
	
	public int doStartTag() throws JspException
	{
		return SKIP_BODY;
	}
	
	
	public int doEndTag() throws JspException
	{
		TagUtils.getInstance().write(super.pageContext, createHiddenTag() );
		return EVAL_PAGE;
	}

}

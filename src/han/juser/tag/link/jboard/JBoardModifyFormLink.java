package han.juser.tag.link.jboard;


import han.juser.tag.link.AbstractLink;
import han.juser.tag.link.ModifyFormLink;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;

import org.apache.struts.taglib.TagUtils;

public class JBoardModifyFormLink  extends AbstractLink implements ModifyFormLink 
{
	
	
	public int doEnd() throws JspException 
	{
		StringBuffer tag = new StringBuffer();
		tag.append("<a href='" + computeURL("/modifyForm") + "'>");
		tag.append(body);
		tag.append("</a>");
		TagUtils.getInstance().write(super.pageContext, tag.toString());
		return Tag.EVAL_PAGE;
	}
}

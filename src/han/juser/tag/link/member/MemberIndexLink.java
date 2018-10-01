package han.juser.tag.link.member;


import han.juser.controller.param.MemberParam;
import han.juser.tag.link.AbstractLink;
import han.juser.tag.link.IndexLink;

import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTag;
import javax.servlet.jsp.tagext.Tag;

import org.apache.struts.taglib.TagUtils;

public class MemberIndexLink extends AbstractLink implements IndexLink 
{

	public int doStart(Map map) throws JspException
	{
		urlManager.removeParameter(MemberParam.USER_ID);
		return BodyTag.EVAL_BODY_BUFFERED;
	}
	
	
	public int doEnd() throws JspException
	{
		StringBuffer result = new StringBuffer();
		result.append("<a href='" + computeURL("/memberList") + "'>");
		result.append(body);
		result.append("</a>");
		TagUtils.getInstance().write(super.pageContext, result.toString());
		return Tag.EVAL_PAGE;
	}

}

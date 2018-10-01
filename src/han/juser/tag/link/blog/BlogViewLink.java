package han.juser.tag.link.blog;


import han.juser.controller.param.BlogParam;
import han.juser.tag.link.AbstractLink;
import han.juser.tag.link.ViewLink;

import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTag;
import javax.servlet.jsp.tagext.Tag;

import org.apache.struts.taglib.TagUtils;

public class BlogViewLink  extends AbstractLink implements ViewLink 
{

	public int doStart(Map map) throws JspException
	{
		urlManager.setParameter(BlogParam.ARTICLE_ID, String.valueOf(map.get("contentId")));	
		return BodyTag.EVAL_BODY_BUFFERED;
	}
	

	
	public int doEnd() throws JspException 
	{
		StringBuffer tag = new StringBuffer();
		tag.append("<a href='" + computeURL("/view") + "'>");
		tag.append(body);
		tag.append("</a>");
		TagUtils.getInstance().write(super.pageContext, tag.toString());
		return Tag.EVAL_PAGE;
	}
}

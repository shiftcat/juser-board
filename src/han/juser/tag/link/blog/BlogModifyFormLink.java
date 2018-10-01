package han.juser.tag.link.blog;

import han.juser.controller.param.BlogParam;
import han.juser.tag.link.AbstractLink;
import han.juser.tag.link.ModifyFormLink;

import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTag;
import javax.servlet.jsp.tagext.Tag;

import org.apache.struts.taglib.TagUtils;

public class BlogModifyFormLink  extends AbstractLink implements ModifyFormLink 
{
	
	
	public int doStart(Map property) throws JspException 
	{
		String str = (String)property.get("contentId");
		urlManager.setParameter(BlogParam.ARTICLE_ID, str);
		return BodyTag.EVAL_BODY_BUFFERED;
	}


	
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

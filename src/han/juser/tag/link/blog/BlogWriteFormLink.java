package han.juser.tag.link.blog;


import han.juser.controller.param.BlogParam;
import han.juser.tag.link.AbstractLink;
import han.juser.tag.link.WriteFormLink;

import javax.servlet.jsp.JspException;

import org.apache.struts.taglib.TagUtils;

public class BlogWriteFormLink  extends AbstractLink implements WriteFormLink
{

	
	public int doEnd() throws JspException 
	{
		urlManager.removeParameter(BlogParam.ARTICLE_ID);
		StringBuffer tag = new StringBuffer();
		tag.append("<a href='" + computeURL("/writeForm") + "'>");
		tag.append(body);
		tag.append("</a>");
		TagUtils.getInstance().write(super.pageContext, tag.toString());
		return 0;
	}

}

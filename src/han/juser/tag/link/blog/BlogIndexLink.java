package han.juser.tag.link.blog;


import han.juser.controller.param.BlogParam;
import han.juser.tag.link.AbstractLink;
import han.juser.tag.link.IndexLink;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;

import org.apache.struts.taglib.TagUtils;

public class BlogIndexLink  extends AbstractLink implements IndexLink 
{

	
	public int doEnd() throws JspException 
	{
		urlManager.removeParameter(BlogParam.ARTICLE_ID);
		
		String index = null;
		if(urlManager.getParameter(BlogParam.TARGET) != null){
			index = "/search";
		}else {
			index = "/index";
		}
		StringBuffer tag = new StringBuffer();
		tag.append("<a href='" + computeURL(index) + "'>");
		tag.append(body);
		tag.append("</a>");
		TagUtils.getInstance().write(super.pageContext, tag.toString());
		return Tag.EVAL_PAGE;
	}
}

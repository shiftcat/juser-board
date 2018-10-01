package han.juser.tag.link.jboard;


import han.juser.controller.param.JBoardParam;
import han.juser.tag.link.AbstractLink;
import han.juser.tag.link.IndexLink;

import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTag;
import javax.servlet.jsp.tagext.Tag;

import org.apache.struts.taglib.TagUtils;

public class JBoardIndexLink extends AbstractLink implements IndexLink 
{
	
	public int doStart(Map property) throws JspException 
	{
		urlManager.removeParameter(JBoardParam.ARTICLE_ID);
		return BodyTag.EVAL_BODY_BUFFERED;
	}
	
	
	
	
	public int doEnd() throws JspException 
	{
		String index = null;
		if(urlManager.getParameter(JBoardParam.TARGET) != null){
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

package han.juser.tag.link.board;

import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTag;
import javax.servlet.jsp.tagext.Tag;

import org.apache.struts.taglib.TagUtils;

import han.juser.controller.param.BoardParam;
import han.juser.tag.link.AbstractLink;
import han.juser.tag.link.ViewLink;

public class BoardViewLink  extends AbstractLink implements ViewLink 
{
	
	
	public int doStart(Map property) throws JspException 
	{
		urlManager.setParameter( BoardParam.CONTENT_ID, (String)property.get("contentId") );
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

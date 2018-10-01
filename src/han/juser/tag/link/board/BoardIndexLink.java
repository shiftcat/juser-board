package han.juser.tag.link.board;

import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTag;
import javax.servlet.jsp.tagext.Tag;

import org.apache.struts.taglib.TagUtils;

import han.juser.controller.param.BoardParam;
import han.juser.tag.link.AbstractLink;
import han.juser.tag.link.IndexLink;

public class BoardIndexLink extends AbstractLink implements IndexLink 
{
	
	public int doStart(Map property) throws JspException 
	{
		urlManager.removeParameter(BoardParam.CONTENT_ID);
		return BodyTag.EVAL_BODY_BUFFERED;
	}
	

	
	public int doEnd() throws JspException 
	{
		String index = null;
		if(urlManager.getParameter(BoardParam.TARGET) != null){
			index = "/search";
		}else {
			index = "/index";
		}
		StringBuffer tag = new StringBuffer();
		tag.append("<a href='" + super.computeURL(index) + "'>");
		tag.append(body);
		tag.append("</a>");
		TagUtils.getInstance().write(super.pageContext, tag.toString());
		return Tag.EVAL_PAGE;
	}
}

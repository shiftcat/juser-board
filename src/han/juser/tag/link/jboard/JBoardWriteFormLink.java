package han.juser.tag.link.jboard;

import han.juser.tag.link.AbstractLink;
import han.juser.tag.link.WriteFormLink;

import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTag;
import javax.servlet.jsp.tagext.Tag;

import org.apache.struts.taglib.TagUtils;

public class JBoardWriteFormLink extends AbstractLink implements WriteFormLink
{

	
	public int doStart(Map map) throws JspException
	{
		//urlManager.removeParameter(JBoardParam.ARTICLE_ID);
		return BodyTag.EVAL_BODY_BUFFERED;
	}
	
	
	
	
	public int doEnd() throws JspException 
	{
		StringBuffer tag = new StringBuffer();
		tag.append("<a href='" + computeURL("/writeForm") + "'>");
		tag.append(body);
		tag.append("</a>");
		TagUtils.getInstance().write(super.pageContext, tag.toString());
		return Tag.EVAL_PAGE;
	}
}

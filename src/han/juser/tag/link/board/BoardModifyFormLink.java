package han.juser.tag.link.board;

import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTag;
import javax.servlet.jsp.tagext.Tag;

import org.apache.struts.taglib.TagUtils;

import han.juser.tag.link.AbstractLink;
import han.juser.tag.link.ModifyFormLink;

public class BoardModifyFormLink  extends AbstractLink implements ModifyFormLink 
{
	
	public int doStart(Map property) throws JspException 
	{
		return BodyTag.EVAL_BODY_BUFFERED;
	}


	
	public int doEnd() throws JspException 
	{
		StringBuffer tag = new StringBuffer();
		tag.append("<a href='" + computeURL("/frm-bbsModify") + "'>");
		tag.append(body);
		tag.append("</a>");
		TagUtils.getInstance().write(super.pageContext, tag.toString());
		return Tag.EVAL_PAGE;
	}

}

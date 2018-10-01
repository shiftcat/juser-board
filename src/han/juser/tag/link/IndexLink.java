package han.juser.tag.link;

import java.util.Map;

import han.juser.url.URLManager;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyContent;

public interface IndexLink 
{
	public void init(PageContext pageContext, URLManager urlManager);
	
	public int doStart(Map property) throws JspException;
	
	public int doAfter(BodyContent bodyContent) throws JspException;
	
	public int doEnd() throws JspException;

}

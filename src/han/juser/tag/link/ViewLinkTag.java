package han.juser.tag.link;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

public class ViewLinkTag  extends LinkTagSupport 
{
	private ViewLink link;
	
	private Map property = new HashMap();
	
	public void release()
	{
		super.release();
		property.clear();
		link = null;
	}
	
	
	public void setContentId(String id)
	{
		property.put("contentId", id);
	}
	
	
	public void setPageContext(PageContext pageContext)
	{
		super.setPageContext(pageContext);
		link = LinkFactory.getViewLink(pageContext);
		if( link != null) {
			link.init(pageContext, urlManager);
		}
	}
	
	
	public int doStartTag() throws JspException
	{
		if( link == null ) {
			return SKIP_BODY;
		}
		return link.doStart(property);
	}
	
	public int doAfterBody() throws JspException
	{
		if( link == null ) {
			return SKIP_BODY;
		}
		return link.doAfter(bodyContent);
	}
	
	public int doEndTag() throws JspException
	{
		if( link == null ) {
			return EVAL_PAGE;
		}
		return link.doEnd();
	}

}

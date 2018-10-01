package han.juser.tag.link;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

public class IndexLinkTag extends LinkTagSupport 
{
	private IndexLink link;
	
	public void release()
	{
		super.release();
		link = null;
	}
	
	public void setPageContext(PageContext pageContext)
	{
		super.setPageContext(pageContext);
		link = LinkFactory.getIndexLink(pageContext);
		if( link != null) {
			link.init(pageContext, urlManager);
		}
	}
	
	
	public int doStartTag() throws JspException
	{
		if( link == null) {
			return SKIP_BODY;
		}
		return link.doStart(null);
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

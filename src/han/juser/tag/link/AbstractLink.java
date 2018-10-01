package han.juser.tag.link;

import han.juser.url.URLManager;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTag;
import javax.servlet.jsp.tagext.Tag;

import org.apache.struts.taglib.TagUtils;

public abstract class AbstractLink 
{
	protected PageContext pageContext;
	
	protected URLManager urlManager;
	
	protected String body;
	
	
	public void init(PageContext pageContext, URLManager urlManager)
	{
		this.pageContext = pageContext;
		this.urlManager = urlManager; 
	}
	
	
	
	// computeURLWithCharEncoding(PageContext pageContext String forward, 
	// String href, String page, Stirng action, String module, Map params, 
	// String anchor, boolean redirect, boolean encode) 
	protected String computeURL(String action) throws JspException
	{
		String url = null;
		try {
			url = TagUtils.getInstance().computeURLWithCharEncoding(
					pageContext, null, null, null, action, null, urlManager.getMap(),
					null, false, true);
		} catch (MalformedURLException e) {
			//TagUtils.getInstance().saveException(pageContext, e);
			throw new JspException(e.getMessage(), e);
		}
		return url;
	}
	
	
	
	
	public int doStart(Map property) throws JspException 
	{
		return BodyTag.EVAL_BODY_BUFFERED;
	}
	
	
	
	
	public int doAfter(BodyContent bodyContent) throws JspException 
	{
		if(bodyContent != null ) {
			String value = bodyContent.getString().trim();
            if (value.length() > 0) {
                body = value;
            }
		}
		
		return Tag.SKIP_BODY;
	}
	
    public void write(String text) throws JspException
	{
	    JspWriter writer = pageContext.getOut();
	    try {
	        writer.print(text);
	    } catch(IOException e) {
	        //saveException(pageContext, e);
	        throw new JspException(e.getMessage());
	    }
	}
	public abstract int doEnd() throws JspException ;
}

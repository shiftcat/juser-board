
package han.juser.tag.link;

import han.juser.url.URLFactory;
import han.juser.url.URLManager;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.IterationTag;

import org.apache.struts.taglib.TagUtils;
import org.apache.struts.taglib.html.BaseHandlerTag;



public abstract class LinkTagSupport extends BaseHandlerTag implements IterationTag, Serializable
{
	protected URLManager urlManager;
	
	
	
	
	public LinkTagSupport()
	{
		super();
	}
	
	
	public void release()
	{
		super.release();
		
		if(urlManager != null) {
			urlManager.clear();
		}
		urlManager = null;
	}
	
	
	
	
	public void setPageContext(PageContext pageContext)
	{
		super.setPageContext(pageContext);
		urlManager = getURLManager();
	}
	
	
	

	protected String computeURL(String action) throws JspException
	{
		String url = null;
		try {
			url = TagUtils.getInstance().computeURLWithCharEncoding(
					pageContext, null, null, null, action, null, urlManager.getMap(),
					null, false, true);
		} catch (MalformedURLException e) {
			TagUtils.getInstance().saveException(pageContext, e);
			throw new JspException(e.getMessage(), e);
		}
		return url;
	}
	
	
	/**
	 * ���� Hashtable�� ����� ���� �̿��Ͽ� hidden�±׸� �����Ѵ�.
	 * @return hidden�±׹��ڿ��� ����
	 */
	protected String createHiddenTag()
	{
		String key = null;
		StringBuffer hidden = new StringBuffer();
		Map map = urlManager.getMap();
		Iterator it = map.keySet().iterator();
		while(it.hasNext()) {
			key = it.next().toString();
			hidden.append("<input type='hidden' name='" + key + "' value='" + map.get(key) + "' />");
		}
		return hidden.toString();
	}
	
	
	protected URLManager getURLManager()
	{
		URLManager manager = URLFactory.getURLManager(pageContext);
		manager.parseParameter(pageContext.getRequest());
		return manager;
	}
}

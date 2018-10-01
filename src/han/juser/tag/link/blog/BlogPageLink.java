package han.juser.tag.link.blog;

import han.juser.controller.param.BlogParam;
import han.juser.tag.link.AbstractLink;
import han.juser.tag.link.PageLink;
import han.juser.url.PageBean;

import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;

public class BlogPageLink extends AbstractLink implements PageLink
{
	private PageBean pageBean;
	
	public int doStart(Map map) throws JspException
	{
		urlManager.removeParameter(BlogParam.PAGE_NUMBER);
		urlManager.removeParameter(BlogParam.ARTICLE_ID);
		pageBean = (PageBean)pageContext.getRequest().getAttribute(PageBean.PAGEBEAN_KEY);
		return Tag.SKIP_BODY;
	}
	

	
	public int doEnd() throws JspException 
	{
		if(pageBean == null) {
			return Tag.EVAL_PAGE;
		}
		String url = "";
		if(urlManager.getParameter(BlogParam.TARGET) != null ) {
			url = computeURL("/search");
		}else {
			url = computeURL("/index");
		}
		
		if( urlManager.getMap().size() > 0) {
			url += "&" + BlogParam.PAGE_NUMBER + "=";
		}else {
			url += "?" + BlogParam.PAGE_NUMBER + "=";
		}
		
		write(pageBean.getPageList(url));
		return Tag.EVAL_PAGE;
	}
}

package han.juser.tag.link.guest;

import han.juser.controller.param.GuestParam;
import han.juser.tag.link.AbstractLink;
import han.juser.tag.link.PageLink;
import han.juser.url.PageBean;

import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;

import org.apache.struts.taglib.TagUtils;

public class GuestPageLink extends AbstractLink implements PageLink
{
	private PageBean pageBean;
	
	public int doStart(Map map) throws JspException
	{
		urlManager.removeParameter(GuestParam.PAGE_NUMBER);
		pageBean = (PageBean)pageContext.getRequest().getAttribute(PageBean.PAGEBEAN_KEY);
		return Tag.SKIP_BODY;
	}
	
	


	
	public int doEnd() throws JspException 
	{
		if(pageBean == null) {
			return Tag.EVAL_PAGE;
		}
		
		String url = computeURL("/index");
		if( urlManager.getMap().size() > 0) {
			url += "&" + GuestParam.PAGE_NUMBER + "=";
		}else {
			url += "?" + GuestParam.PAGE_NUMBER + "=";
		}
		TagUtils.getInstance().write(super.pageContext, pageBean.getPageList(url));
		return Tag.EVAL_PAGE;
	}
}

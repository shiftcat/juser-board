package han.juser.tag.link.jboard;

import han.juser.controller.param.JBoardParam;
import han.juser.tag.link.AbstractLink;
import han.juser.tag.link.PageLink;
import han.juser.url.PageBean;

import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;

import org.apache.struts.taglib.TagUtils;


public class JBoardPageLink  extends AbstractLink implements PageLink
{
	private PageBean pageBean;
	
	
	public int doStart(Map pro) throws JspException
	{
		urlManager.removeParameter(JBoardParam.PAGE_NUMBER);
		pageBean = (PageBean)pageContext.getRequest().getAttribute(PageBean.PAGEBEAN_KEY);
		return Tag.SKIP_BODY;
	}
	
	
	
	public int doEnd() throws JspException {
		if(pageBean == null) {
			return Tag.EVAL_PAGE;
		}
		String url = "";
		if(urlManager.getParameter(JBoardParam.TARGET) != null ) {
			url = computeURL("/search");
		}else {
			url = computeURL("/index");
		}
		
		if( urlManager.getMap().size() > 0) {
			url += "&" + JBoardParam.PAGE_NUMBER + "=";
		}else {
			url += "?" + JBoardParam.PAGE_NUMBER + "=";
		}
		
		TagUtils.getInstance().write(super.pageContext, pageBean.getPageList(url));
		return Tag.EVAL_PAGE;
	}
}

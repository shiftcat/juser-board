package han.juser.tag.link.board;

import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTag;
import javax.servlet.jsp.tagext.Tag;

import org.apache.struts.taglib.TagUtils;

import han.juser.controller.param.BoardParam;
import han.juser.tag.link.AbstractLink;
import han.juser.tag.link.PageLink;
import han.juser.url.PageBean;

public class BoardPageLink  extends AbstractLink implements PageLink
{
	
	public int doStart(Map property) throws JspException 
	{
		urlManager.removeParameter(BoardParam.PAGE_NUMBER);
		urlManager.removeParameter(BoardParam.CONTENT_ID);
		
		return Tag.SKIP_BODY;
	}
	

	
	
	public int doAfter(BodyContent bodyContent) throws JspException 
	{
		return BodyTag.SKIP_BODY;
	}

	
	
	
	public int doEnd() throws JspException 
	{
		PageBean pageBean = (PageBean)pageContext.getRequest().getAttribute(PageBean.PAGEBEAN_KEY);
		if(pageBean == null) {
			return Tag.EVAL_PAGE;
		}
		String url = "";
		if(urlManager.getParameter(BoardParam.TARGET) != null ) {
			url = computeURL("/search");
		}else {
			url = computeURL("/index");
		}
		
		if( urlManager.getMap().size() > 0) {
			url += "&" + BoardParam.PAGE_NUMBER + "=";
		}else {
			url += "?" + BoardParam.PAGE_NUMBER + "=";
		}
		TagUtils.getInstance().write(super.pageContext, pageBean.getPageList(url));
		return Tag.EVAL_PAGE;
	}

}

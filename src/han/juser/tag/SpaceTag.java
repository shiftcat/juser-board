package han.juser.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.struts.taglib.TagUtils;

public class SpaceTag extends BodyTagSupport 
{
	private int space;
	
	public void release()
	{
		super.release();
		space = 0;
	}
	
	public void setSpace(int s)
	{
		this.space = s;
	}
	
	
	public int doAfterBody() throws JspException
	{
		return SKIP_BODY;
	}
	
	
	public int doEndTag() throws JspException
	{
		String str = "";
		for(int i = 1; i < space; i++ ){
			str += "&nbsp;&nbsp;";
		}
		TagUtils.getInstance().write(super.pageContext, str);
		return EVAL_PAGE;
	}
}

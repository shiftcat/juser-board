package han.juser.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.struts.taglib.TagUtils;

public class NltoBrTag extends BodyTagSupport 
{
	private String text;
	
	public void release()
	{
		super.release();
		text = null;
	}
	
	public int doStartTag() throws JspException
	{
		return EVAL_BODY_BUFFERED;
	}
	
	public int doAfterBody() throws JspException
	{
		if(bodyContent != null ) {
			String value = bodyContent.getString().trim();

            if (value.length() > 0) {
                text = value;
            }
		}
		return SKIP_BODY;
	}
	
	public int doEndTag() throws JspException
	{
		//String var = TagUtils.getInstance().filter( text );
		if(text == null) {
			return EVAL_PAGE;
		}
		String var = text.replaceAll("\n", "<br/>");
		TagUtils.getInstance().write(super.pageContext,  var );
		return EVAL_PAGE;
	}
}

package han.juser.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.struts.taglib.TagUtils;

public class EscapeHTMLTag  extends BodyTagSupport 
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
		String reg = "<[/a-zA-Z]+[^>]*/?>{1}";
		String creg = "<!--[^(-->)]*-->";
		/*
		StringBuffer str = new StringBuffer();
		while( text.indexOf('<') != -1) {
			int startPos = text.indexOf('<');
			str.append(text.substring(0, startPos).trim());
			int endPos = text.indexOf('>', startPos);
			text = text.substring(endPos +1);
		}
		TagUtils.getInstance().write(super.pageContext, str.toString());
		*/
		
		TagUtils.getInstance().write(super.pageContext, 
				text.replaceAll(reg, "").replaceAll(creg, "").replaceAll("\\p{Space}+", " "));
		return EVAL_PAGE;
	}
}

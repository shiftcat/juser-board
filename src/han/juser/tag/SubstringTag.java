package han.juser.tag;


import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.struts.taglib.TagUtils;

public class SubstringTag extends BodyTagSupport 
{
	private String text;
	
	private int length;
	
	private String suffix;
	
	private boolean filter;
	
	public void release()
	{
		super.release();
		text = null;
		length = -1;
		suffix = null;
		filter = false;
	}
	
	
	public void setLength(Integer length)
	{
		if(length < 0 ) {
			this.length = 1;
		}else {
			this.length = length;
		}
	}
	
	public void setSuffix(String suffix)
	{
		this.suffix = suffix;
	}
	
	public void setFilter(boolean b)
	{
		this.filter = b;
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
		if( text.length() > length ) {
			if(suffix == null) {
				suffix = "";
			}
			if( this.filter ) {
				this.text = TagUtils.getInstance().filter( text );
			}
			TagUtils.getInstance().write(super.pageContext,  text.substring(0, length) + suffix );
		}else {
			TagUtils.getInstance().write(super.pageContext, text);
		}
		return EVAL_PAGE;
	}
}

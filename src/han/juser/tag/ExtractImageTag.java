package han.juser.tag;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.struts.taglib.TagUtils;

public class ExtractImageTag extends BodyTagSupport 
{
	private String text;
	
	private int width;
	
	private int height;
	
	
	public void release()
	{
		super.release();
		text = null;
	}
	
	
	public void setWidth(int w)
	{
		this.width = w;
	}
	
	public void setHeight(int h)
	{
		this.height = h;
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
		String reg = "<(img|IMG)[^>]+>{1}";
		
		String[] ss = null;
		Pattern p = Pattern.compile(reg);
		Matcher m = p.matcher(text);
		if(m.find()) {
			ss = m.group().split(" ");
		}
		
		String srcreg = "^(src|SRC)=.+";
		String src = null;
		if( ss != null ) {
			for(int i =0; i < ss.length; i++) {
				if(Pattern.matches(srcreg, ss[i].trim())) {
					src = ss[i].trim();
					break;
				}
			}
		}
		if( src != null ) {
			String img = "<img " + src + " width='" + this.width + "' height='" + this.height + "' />";
			TagUtils.getInstance().write(super.pageContext, img);
		}
		return EVAL_PAGE;
	}
}

package han.juser.tag;

import han.juser.controller.form.Member;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.taglib.TagUtils;

public class LoginMemberInfoTag extends TagSupport
{
	private static final Log log = LogFactory.getLog(LoginMemberInfoTag.class);
	
	private Member member;
	
	private String property;
	
	public void setProperty(String property)
	{
		this.property = property;
	}
	
	public LoginMemberInfoTag()
	{
		super();
	}
	
	public void release()
	{
		super.release();
		member = null;
		property = null;
		if(log.isDebugEnabled()) {
			log.debug("LoginMemberInfoTag release");
		}
	}
	
	public int doStartTag() throws JspException
	{
		HttpSession session = pageContext.getSession();
		member = (Member)session.getAttribute(Member.MEMBER_SESSION_KEY);
		if(member == null) {
			log.warn("로그인 되어 있지 않습니다.");
		}
		return SKIP_BODY;
	}
	
	public int doEndTag() throws JspException
	{
		String value = null;
		try {
			value = BeanUtils.getProperty(member, property);
		} catch (Exception e) {
			log.error(e);
			throw new JspException(e.getMessage(), e);
		}
		if(value == null || value.equals("")) {
			value = "&nbsp;";
		}
		TagUtils.getInstance().write(pageContext, value);
		return EVAL_PAGE;
	}
}

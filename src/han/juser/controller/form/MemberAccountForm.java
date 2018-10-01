package han.juser.controller.form;

import han.juser.model.MemberDTO;
import han.util.JPattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

public final class MemberAccountForm extends MemberModifyForm
{
	private String passwd;

	private String pwdCheck;

	public MemberAccountForm()
	{
		super();
	}

	public void setUserId(String userId)
	{
		super.userId = userId;
	}

	public String getUserId()
	{
		return userId;
	}

	public void setPasswd(String passwd)
	{
		this.passwd = passwd;
	}

	public String getPasswd()
	{
		return passwd;
	}

	public String getPwdCheck()
	{
		return pwdCheck;
	}

	public void setPwdCheck(String pwdCheck)
	{
		this.pwdCheck = pwdCheck;
	}

	public void setJumin(int i, String jumin)
	{
		super.jumin[i] = jumin;
	}

	public String getJumin(int i)
	{
		return jumin[i];
	}

	public String getJuminNumber()
	{
		if(jumin == null || jumin.length < 2 ) {
			return null;
		}
		if(jumin[0] == null || jumin[1] == null) {
			return null;
		}
		if (!jumin[0].equals("") && !jumin[1].equals("")) {
			return jumin[0] + "-" + jumin[1];
		} else {
			return null;
		}
	}

	public MemberDTO getTransferObject()
	{
		MemberDTO dto = new MemberDTO();
		dto.setUserId(userId);
		dto.setUserName(userName);
		dto.setJuminNumber(getJuminNumber());
		dto.setEmail(email);
		dto.setPasswd(passwd);
		dto.setMobileNumber(getMobileNumber());
		dto.setPhoneNumber(getPhoneNumber());
		return dto;
	}

	public ActionErrors validate(ActionMapping mapping, HttpServletRequest req)
	{
		ActionErrors errors = super.validate(mapping, req);

		if ( getJuminNumber() == null ) {
			errors.add("jumin", new ActionMessage("errors.required", "주민등록번호"));
		} else if (!JPattern.jumin(this.getJuminNumber())) {
			errors.add("jumin", new ActionMessage("errors.invalid", "주민등록번호"));
		}

		return errors;
	}
	
	
	public String toString()
	{
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
	
	
	public boolean equals(Object o)
	{
		return EqualsBuilder.reflectionEquals(this, o);
	}
	
	
	public int hashCode()
	{
		return HashCodeBuilder.reflectionHashCode(this);
	}

}

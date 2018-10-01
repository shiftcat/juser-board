package han.juser.controller.form;

import han.util.JPattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

public class MemberModifyForm extends Member
{

	public MemberModifyForm()
	{
		super();
	}

	public void setUserName(String userName)
	{
		super.userName = userName;
	}

	public void setEmail(String email)
	{
		super.email = email;
	}

	public void setMobile(int i, String mobile)
	{
		super.mobile[i] = mobile;
	}

	public String getMobile(int i)
	{
		return mobile[i];
	}

	public void setPhone(int i, String phone)
	{
		super.phone[i] = phone;
	}

	public String getPhone(int i)
	{
		return phone[i];
	}

	public String getPhoneNumber()
	{
		if(phone == null || phone.length < 3) {
			return null;
		}
		if(phone[0] == null || phone[1] == null || phone[2] == null) {
			return null;
		}
		if( !phone[0].equals("") && !phone[1].equals("") && !phone[2].equals("") ) {
			return phone[0] + "-" + phone[1] + "-" + phone[2];
		} else {
			return null;
		}
	}

	public String getMobileNumber()
	{
		if(mobile == null || mobile.length < 3) {
			return null;
		}
		if(mobile[1] == null || mobile[1] == null || mobile[2] == null) {
			return null;
		}
		if (!mobile[0].equals("") && !mobile[1].equals("") && !mobile[2].equals("")) {
			return mobile[0] + "-" + mobile[1] + "-" + mobile[2];
		} else {
			return null;
		}
	}

	public ActionErrors validate(ActionMapping mapping, HttpServletRequest req)
	{
		ActionErrors errors = super.validate(mapping, req);

		if (userName == null || userName.equals("")) {
			errors.add("userid", new ActionMessage("errors.required", "이름"));
		} else if (!JPattern.hanglName(userName)) {
			errors.add("userid", new ActionMessage("errors.invalid", "이름"));
		}

		if ( getPhoneNumber() != null ) {
			if ( !JPattern.phone( getPhoneNumber() ) ) {
				errors.add("phone", new ActionMessage("errors.invalid", "전화번호"));
			}
		}

		if ( getMobileNumber() != null ) {
			if ( !JPattern.mobile(getMobileNumber()) ) {
				errors.add("mobile", new ActionMessage("errors.invalid", "휴대폰번호"));
			}
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

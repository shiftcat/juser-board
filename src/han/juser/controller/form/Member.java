package han.juser.controller.form;

import java.util.Date;

import han.juser.model.MemberDTO;

import org.apache.struts.validator.ValidatorForm;

public class Member extends ValidatorForm
{
	public static final String MEMBER_SESSION_KEY = "member";
	
	protected String userId;

	protected String userName;

	protected String email;

	protected int level;

	private String juminNumber;

	private String phoneNumber;

	private String mobileNumber;

	protected Date logDate;

	protected Date lastLogin;

	protected String[] jumin;

	protected String[] phone;

	protected String[] mobile;
	
	public Member()
	{
		jumin = new String[2];
		phone = new String[3];
		mobile = new String[3];
	}
	
	public Member(MemberDTO dto)
	{
		this.userId = dto.getUserId();
		this.userName = dto.getUserName();
		this.email = dto.getEmail();
		this.level = dto.getLevel();
		this.juminNumber = dto.getJuminNumber();
		this.phoneNumber = dto.getPhoneNumber();
		this.mobileNumber = dto.getMobileNumber();
		this.logDate = dto.getLogDate();
		this.lastLogin = dto.getLastLogin();

		if (this.juminNumber != null) {
			this.jumin = this.juminNumber.split("-");
		}

		if (this.mobileNumber != null) {
			this.mobile = this.mobileNumber.split("-");
		}

		if (this.phoneNumber != null) {
			this.phone = this.phoneNumber.split("-");
		}
	}
	
	public String getUserId()
	{
		return userId;
	}
	
	public String getUserName()
	{
		return userName;
	}

	public String getEmail()
	{
		return email;
	}

	public Date getLastLogin()
	{
		return lastLogin;
	}
	
	public int getLevel()
	{
		return level;
	}
	
	public Date getLogDate()
	{
		return logDate;
	}
	
	public String getJuminNumber()
	{
		return juminNumber;
	}


	public String getMobileNumber()
	{
		return mobileNumber;
	}

	public String getPhoneNumber()
	{
		return phoneNumber;
	}
	
	public String[] getJumin()
	{
		return jumin;
	}
	
	
	public String[] getPhone()
	{
		return phone;
	}
	
	public String[] getMobile()
	{
		return mobile;
	}
	
}

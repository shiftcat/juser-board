package han.juser.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class MemberDTO implements Serializable
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private long num;

	private String userId;

	private String userName;

	private String email;

	private String juminNumber;

	private String phoneNUmber;

	private String mobileNumber;

	private String passwd;

	private Timestamp logDate;

	private Timestamp lastLogin;

	private int lev;

	public MemberDTO()
	{
		super();
	}

	public long getNum()
	{
		return num;
	}

	public void setNum(long num)
	{
		this.num = num;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public String getJuminNumber()
	{
		return juminNumber;
	}

	public void setJuminNumber(String jumin)
	{
		this.juminNumber = jumin;

	}

	public void setPasswd(String passwd)
	{
		this.passwd = passwd;
	}

	public String getPasswd()
	{
		return passwd;
	}

	public String getMobileNumber()
	{
		return mobileNumber;
	}

	public void setMobileNumber(String mobile)
	{
		this.mobileNumber = mobile;

	}

	public Timestamp getLastLogin()
	{
		return lastLogin;
	}

	public void setLastLogin(Timestamp lastLogin)
	{
		this.lastLogin = lastLogin;
	}

	public int getLevel()
	{
		return lev;
	}

	public void setLevel(int lev)
	{
		this.lev = lev;
	}

	public Timestamp getLogDate()
	{
		return logDate;
	}

	public void setLogDate(Timestamp logDate)
	{
		this.logDate = logDate;
	}

	public String getPhoneNumber()
	{
		return phoneNUmber;
	}

	public void setPhoneNumber(String phone)
	{
		this.phoneNUmber = phone;

	}

	public String getUserId()
	{
		return userId;
	}

	public void setUserId(String userId)
	{
		this.userId = userId;
	}

	public String getUserName()
	{
		return userName;
	}

	public void setUserName(String userName)
	{
		this.userName = userName;
	}

}

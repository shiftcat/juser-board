package han.juser.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

public class GuestDTO implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private long gid;

	private String userId;

	private String content;

	private Timestamp logDate;

	private String remoteAddr;

	public GuestDTO()
	{
		super();
	}

	public String getContent()
	{
		return content;
	}

	public void setContent(String content)
	{
		this.content = content;
	}

	public long getGid()
	{
		return gid;
	}

	public void setGid(long id)
	{
		this.gid = id;
	}

	public Timestamp getLogDate()
	{
		return logDate;
	}

	public void setLogDate(Timestamp logDate)
	{
		this.logDate = logDate;
	}

	public String getRemoteAddr()
	{
		return remoteAddr;
	}

	public void setRemoteAddr(String remoteAddr)
	{
		this.remoteAddr = remoteAddr;
	}

	public String getUserId()
	{
		return userId;
	}

	public void setUserId(String userId)
	{
		this.userId = userId;
	}

	

};
package han.juser.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class JBoardDTO implements Serializable
{

	/**
     * 
     */
	private static final long serialVersionUID = 1L;
	
	private long rownum;
	
	private long articleId;

	private long parentId;

	private long groupId;

	private int level;

	private String userId;

	private String systemFiles;

	private String originalFiles;

	private String fLink;

	private String subject;

	private String content;

	private Timestamp signDate;

	private Timestamp updated;

	private int hit;

	private int vote;

	private String remoteAddr;

	public JBoardDTO()
	{
		super();
	}
	
	
	public void setRownum(long rownum)
	{
		this.rownum = rownum;
	}
	
	public long getRownum()
	{
		return rownum;
	}
	
	
	public long getArticleId()
	{
		return articleId;
	}

	public void setArticleId(long articleId)
	{
		this.articleId = articleId;
	}

	public String getContent()
	{
		return content;
	}

	public void setContent(String content)
	{
		this.content = content;
	}

	public String getLink()
	{
		return fLink;
	}

	public void setLink(String link)
	{
		fLink = link;
	}

	public int getLevel()
	{
		return level;
	}

	public void setLevel(int level)
	{
		this.level = level;
	}

	public long getGroupId()
	{
		return groupId;
	}

	public void setGroupId(long groupId)
	{
		this.groupId = groupId;
	}

	public int getHit()
	{
		return hit;
	}

	public void setHit(int hit)
	{
		this.hit = hit;
	}

	public String getOriginalFiles()
	{
		return originalFiles;
	}

	public void setOriginalFiles(String originalfiles)
	{
		this.originalFiles = originalfiles;
	}

	public long getParentId()
	{
		return parentId;
	}

	public void setParentId(long parentId)
	{
		this.parentId = parentId;
	}

	public String getRemoteAddr()
	{
		return remoteAddr;
	}

	public void setRemoteAddr(String remoteAddr)
	{
		this.remoteAddr = remoteAddr;
	}

	public Timestamp getSignDate()
	{
		return signDate;
	}

	public void setSignDate(Timestamp signDate)
	{
		this.signDate = signDate;
	}

	public String getSubject()
	{
		return subject;
	}

	public void setSubject(String subject)
	{
		this.subject = subject;
	}

	public String getSystemFiles()
	{
		return systemFiles;
	}

	public void setSystemFiles(String systemfiles)
	{
		this.systemFiles = systemfiles;
	}

	public Timestamp getUpdated()
	{
		return updated;
	}

	public void setUpdated(Timestamp updated)
	{
		this.updated = updated;
	}

	public String getUserId()
	{
		return userId;
	}

	public void setUserId(String userId)
	{
		this.userId = userId;
	}

	public int getVote()
	{
		return vote;
	}

	public void setVote(int vote)
	{
		this.vote = vote;
	}
}

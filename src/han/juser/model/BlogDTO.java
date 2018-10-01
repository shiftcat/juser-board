package han.juser.model;


import java.io.Serializable;
import java.sql.Timestamp;

public class BlogDTO implements Serializable
{
	
	
	private static final long serialVersionUID = 1L;
	private long rowNum;
	private long articleId;
	private int cateId;
	private String userId;
	private String systemFiles;
	private String originalFiles;
	private String subject;
	private String content;
	private Timestamp signDate;
	private Timestamp updated;
	private String remoteAddr;
	private int replyCount;
	
	
	public BlogDTO() 
	{
		super();
	}

	

	
	public long getRowNum()
	{
		return rowNum;
	}



	
	public void setRowNum(long rowNum)
	{
		this.rowNum = rowNum;
	}



	
	public long getArticleId() {
		return articleId;
	}


	
	public void setArticleId(long articleId) {
		this.articleId = articleId;
	}


	
	public int getCateId() {
		return cateId;
	}


	
	public void setCateId(int cateId) {
		this.cateId = cateId;
	}


	
	public String getContent() {
		return content;
	}



	public void setContent(String content) {
		this.content = content;
	}


	
	public String getOriginalFiles() {
		return originalFiles;
	}


	
	public void setOriginalFiles(String originalFiles) {
		this.originalFiles = originalFiles;
	}


	
	public String getRemoteAddr() {
		return remoteAddr;
	}


	
	public void setRemoteAddr(String remoteAddr) {
		this.remoteAddr = remoteAddr;
	}


	
	public Timestamp getSignDate() {
		return signDate;
	}


	
	public void setSignDate(Timestamp signDate) {
		this.signDate = signDate;
	}


	
	public String getSubject() {
		return subject;
	}


	
	public void setSubject(String subject) {
		this.subject = subject;
	}


	
	public String getSystemFiles() {
		return systemFiles;
	}


	
	public void setSystemFiles(String systemFiles) {
		this.systemFiles = systemFiles;
	}


	
	public Timestamp getUpdated() {
		return updated;
	}


	
	public void setUpdated(Timestamp updated) {
		this.updated = updated;
	}


	
	public String getUserId() {
		return userId;
	}


	
	public void setUserId(String userId) {
		this.userId = userId;
	}


	
	public int getReplyCount() {
		return replyCount;
	}




	
	public void setReplyCount(int replyCount) {
		this.replyCount = replyCount;
	}
	
	
}

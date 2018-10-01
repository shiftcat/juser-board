package han.juser.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class BlogReplyDTO implements Serializable 
{
	private long replyId;
	private long articleId;
	private String userId;
	private String content;
	private Timestamp signDate;
	private String remoteAddr;
	
	public BlogReplyDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	public long getArticleId() {
		return articleId;
	}
	
	
	
	public void setArticleId(long articleId) {
		this.articleId = articleId;
	}


	
	
	public String getContent() {
		return content;
	}


	
	public void setContent(String content) {
		this.content = content;
	}


	
	
	public String getRemoteAddr() {
		return remoteAddr;
	}


	public void setRemoteAddr(String remoteAddr) {
		this.remoteAddr = remoteAddr;
	}


	
	
	
	public long getReplyId() {
		return replyId;
	}


	
	
	public void setReplyId(long replyId) {
		this.replyId = replyId;
	}


	
	public Timestamp getSignDate() {
		return signDate;
	}


	
	public void setSignDate(Timestamp signDate) {
		this.signDate = signDate;
	}


	
	public String getUserId() {
		return userId;
	}


	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	

}

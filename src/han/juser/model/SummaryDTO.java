package han.juser.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class SummaryDTO  implements Serializable
{
	private long contentId;
	private String subject;
	private String content;
	private Timestamp signDate;
	
	public SummaryDTO() {
		super();
		
	}
	
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public long getContentId() {
		return contentId;
	}
	
	public void setContentId(long contentId) {
		this.contentId = contentId;
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

}

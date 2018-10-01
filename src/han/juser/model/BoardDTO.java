package han.juser.model;


import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class BoardDTO implements Serializable
{


	
	private static final long serialVersionUID = 1L;
	
	private long bbsId;
	private String userId;
	private String originalFiles;
	private String systemFiles;
	private String subject;
	private String content;
	private Timestamp logDate;
	private int hit;
	private int vote;
	private String link;
	private String remoteAddr;
	private int replyCount;
	private List replyList;
	
	public BoardDTO() {
		super();
	}
	

	public long getBbsId() {
		return bbsId;
	}

	public void setBbsId(long bbsId) {
		this.bbsId = bbsId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getHit() {
		return hit;
	}

	public void setHit(int hit) {
		this.hit = hit;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public Timestamp getLogDate() {
		return logDate;
	}

	public void setLogDate(Timestamp logDate) {
		this.logDate = logDate;
	}

	public String getRemoteAddr() {
		return remoteAddr;
	}

	public void setRemoteAddr(String remoteAddr) {
		this.remoteAddr = remoteAddr;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public int getVote() {
		return vote;
	}

	public void setVote(int vote) {
		this.vote = vote;
	}
	
	
	public String getOriginalFiles() {
		return originalFiles;
	}

	public void setOriginalFiles(String originalFiles) {
		this.originalFiles = originalFiles;
	}

	public String getSystemFiles() {
		return systemFiles;
	}

	public void setSystemFiles(String systemFiles) {
		this.systemFiles = systemFiles;
	}
	
	
	
	public void setReply(BoardReplyDTO reply)
	{
		if( this.replyList == null ) {
			replyList = new ArrayList();
		}
		this.replyList.add(reply);
	}
	
	public BoardReplyDTO getReply(int index)
	{
		return (BoardReplyDTO)this.replyList.get(index);
	}
	
	
	public void setReplyList(List reply)
	{
		
		this.replyList = reply;
	}
	
	
	public List getReplyList()
	{
		return this.replyList;
	}

	
	public int getReplyCount() 
	{
		return this.replyCount;
	}

	
	public void setReplyCount(int replyCount) {
		this.replyCount = replyCount;
	}
	
	
	
}

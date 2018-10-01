package han.juser.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class BoardReplyDTO implements Serializable 
{
	private long replId;
	private long bbsId;
	private String userId;
	private String reply;
	private Timestamp logdate;
	private String remoteAddr;
	public BoardReplyDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public long getBbsId() {
		return bbsId;
	}
	public void setBbsId(long bbsId) {
		this.bbsId = bbsId;
	}
	public Timestamp getLogdate() {
		return logdate;
	}
	public void setLogdate(Timestamp logdate) {
		this.logdate = logdate;
	}
	public String getRemoteAddr() {
		return remoteAddr;
	}
	public void setRemoteAddr(String remoteAddr) {
		this.remoteAddr = remoteAddr;
	}
	public String getReply() {
		return reply;
	}
	public void setReply(String reply) {
		this.reply = reply;
	}
	public long getReplId() {
		return replId;
	}
	public void setReplId(long replId) {
		this.replId = replId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
}

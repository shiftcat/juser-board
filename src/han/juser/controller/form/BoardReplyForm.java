package han.juser.controller.form;


import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

public class BoardReplyForm extends BaseForm
{
	private long bbsId;
	private String userId;
	private String reply;
	
	
	public BoardReplyForm()
	{
		super();
	}
	
	
	public long getBbsId() 
	{
		return bbsId;
	}
	
	public void setBbsId(long bbsId) 
	{
		this.bbsId = bbsId;
	}
	
	
	public String getReply() 
	{
		return reply;
	}
	
	
	public void setReply(String reply) 
	{
		this.reply = reply;
	}
	
	
	public String getUserId() 
	{
		return userId;
	}
	
	
	public void setUserId(String userId)
	{
		this.userId = userId;
	}
	
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request)
	{
		ActionErrors errors = super.validate(mapping, request);
		if(reply == null || reply.length() < 10) {
			errors.add("reply", new ActionMessage("errors.board.failReplyWrite"));
		}
		return errors;
	}
	
}

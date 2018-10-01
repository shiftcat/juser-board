package han.juser.controller.form;


import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.upload.FormFile;


/**
 * 
 * @author Y.Han Lee
 * 
 */

public class BoardForm extends BaseForm
{
	
	private long bbsId;
	private String userId;
	private String subject;
	private String content;
	private String link;
	private FormFile formFile;
	private boolean submit = false;
	
	
	public BoardForm() 
	{
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	

	public long getBbsId() 
	{
		return bbsId;
	}

	
	

	public void setBbsId(long bbsId) 
	{
		this.bbsId = bbsId;
	}


	

	public String getContent() 
	{
		return content;
	}
	
	
	

	public void setContent(String content) 
	{
		this.content = content;
		this.submit = true;
	}
	
	

	public String getLink() 
	{
		return link;
	}
	
	
	

	public void setLink(String link)
	{
		this.link = link;
	}
	
	

	public String getSubject() 
	{
		return subject;
	}
	
	
	
	
	public void setSubject(String subject) 
	{
		this.subject = subject;
		this.submit = true;
	}

	
	
	public FormFile getFormFile() 
	{
		return formFile;
	}


	
	public void setFormFile(FormFile formFile) 
	{
		this.formFile = formFile;
	}
	
	
	public String getOriginalFileName()
	{
		return formFile.getFileName();
	}
	
	
	public String getUserId() 
	{
		return userId;
	}

	
	public void setUserId(String userId) 
	{
		this.userId = userId;
	}

	public boolean isSubmit()
	{
		return submit;
	}
	
	
	
	public void setSubmit(boolean b)
	{
		this.submit = b;
	}
	
	

	public ActionErrors validate(ActionMapping mapping, HttpServletRequest req)
	{
		ActionErrors errors = super.validate(mapping, req);
		
		if(this.formFile == null ) {
			return errors;
		}
		int size = this.formFile.getFileSize();
		if(size > (1024*1024*2)) {
			errors.add("fileSize", new ActionMessage("errors.fileSize", "2"));
		}
		
		return errors;
	}
	
}

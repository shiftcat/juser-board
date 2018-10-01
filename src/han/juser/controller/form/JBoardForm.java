package han.juser.controller.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.upload.FormFile;

public class JBoardForm extends BaseForm
{
	private long articleId = -1;
	private String userId;
	private String subject;
	private String content;
	private String link;
	private FormFile formFile;
	private String originalFiles;
	private boolean submit = false;
	
	
	public JBoardForm() 
	{
		super();
	}

	
	public long getArticleId() 
	{
		return articleId;
	}

	
	public void setArticleId(long articleId) 
	{
		this.articleId = articleId;
	}


	public String getUserId() 
	{
		return userId;
	}


	public void setUserId(String userId) 
	{
		this.userId = userId;
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
	

	
	public FormFile getFormFile() 
	{
		return formFile;
	}
	
	
	public void setFormFile(FormFile formFile) 
	{
		this.formFile = formFile;
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


	public void setOriginalFiles(String files) 
	{
		originalFiles = files;
	}


	public String getOriginalFiles()
	{
		if(formFile != null) {
			return formFile.getFileName();			
		}
		return null;
	}
	
	
	public boolean isSubmit()
	{
		return this.submit;
	}
	
	
	public void setSubmit(boolean b)
	{
		this.submit = b;
	}
	
	
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest req)
	{
		ActionErrors errors = super.validate(mapping, req);
		
		int size = this.formFile.getFileSize();
		if(size > (1024*1024*2)) {
			errors.add("fileSize", new ActionMessage("errors.fileSize", "2"));
		}
		
		return errors;
	}
	
}

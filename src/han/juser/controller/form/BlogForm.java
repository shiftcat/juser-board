package han.juser.controller.form;


import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;


/**
 * 
 * @author Y.Han Lee
 * 
 */

public class BlogForm extends BaseForm
{
	
	private long articleId;
	private int cateId;
	private String subject;
	private String content;
	private String userId;
	private boolean submit = false;

	public BlogForm() 
	{
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	

	public long getArticleId() 
	{
		return articleId;
	}

	
	

	public void setArticleId(long bbsId) 
	{
		this.articleId = bbsId;
	}
	
	
	
	
	
	
	public String getUserId() 
	{
		return userId;
	}




	
	public void setUserId(String userId) 
	{
		this.userId = userId;
	}




	public int getCateId()
	{
		return this.cateId;
	}
	
	
	public void setCateId(int cateId)
	{
		this.cateId = cateId;
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
	
	
	
	public String getSubject() 
	{
		return subject;
	}
	
	
	
	
	public void setSubject(String subject) 
	{
		this.subject = subject;
		this.submit = true;
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
		if(errors == null || errors.isEmpty()) {
			return errors;
		}else {
			this.submit = false;
			return errors;
		}
	}
	
	
}

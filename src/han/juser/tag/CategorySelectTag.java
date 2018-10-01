package han.juser.tag;

import han.juser.model.CategoryDTO;
import han.juser.service.CategoryService;
import han.juser.service.ServiceHelper;

import java.util.Iterator;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.taglib.TagUtils;

public class CategorySelectTag extends TagSupport
{
	private Log log = LogFactory.getLog(CategorySelectTag.class);
	
	private String name;
	
	private String script;
	
	private String defaultOption;
	
	
	private int cate;
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public void setScript(String script)
	{
		this.script = script;
	}
	
	public void setCategory(int cate)
	{
		this.cate = cate;
	}
	
	public void setDefaultOption(String option)
	{
		this.defaultOption = option;
	}
	
	
	public void release() 
	{
		super.release();
		this.name = null;
		this.script = null;
		this.cate = 0;
	}
	
	
	public int doStartTag() throws JspException
	{
		return SKIP_BODY;
	}
	
	
	public int doEndTag() throws JspException
	{
		List cate = null;
		CategoryService service = ServiceHelper.getCategoryService(pageContext.getServletContext());
		try {
			
			cate = service.getCategoryList();
		} catch (Throwable e) {
			log.error(e.getMessage(), e);
			throw new JspException(e);
		}
		
		StringBuffer sb = new StringBuffer();
		
		if(script == null) {
			sb.append("<select name='" + name + "'>");
		}else {
			sb.append("<select name='" + name + "'  " + script + ">");
		}
		sb.append("<option value='0' >" + defaultOption + "</option>");
		for( Iterator i = cate.iterator(); i.hasNext();) {
			CategoryDTO dto = (CategoryDTO)i.next();
			if( this.cate == dto.getCateId() ) {
				sb.append("<option value='" + dto.getCateId() + "' selected>" + dto.getCateName() + "</option>");
			}else {
				sb.append("<option value='" + dto.getCateId() + "' >" + dto.getCateName() + "</option>");
			}
		}
		
		sb.append("</select>");
		TagUtils.getInstance().write(super.pageContext, sb.toString());
		return EVAL_PAGE;
	}
}

package han.juser.tag.role;

import han.role.UserConfig;

import java.util.Iterator;
import java.util.Map;

import javax.servlet.jsp.JspException;

import org.apache.struts.taglib.TagUtils;


public class SelectLevelTag extends RoleTagSupport 
{
	private String name;
	
	private String level;
	
	private String script;
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public void setLevel(String level)
	{
		this.level = level;
	}
	
	
	public void setScript(String script)
	{
		this.script = script;
	}
	
	public void release()
	{
		super.release();
		this.level = null;
		this.script = null;
		this.name = null;
	}
	
	public int doStartTag() throws JspException
	{
		return SKIP_BODY;
	}
	
	public int doEndTag() throws JspException
	{
		Map map = roleManager.getUserConfig();
		StringBuffer sb = new StringBuffer();
		
		if(script == null) {
			sb.append("<select name='" + name + "'>");
		}else {
			sb.append("<select name='" + name + "'  " + script + ">");
		}
		
		for(Iterator i = map.keySet().iterator(); i.hasNext(); ) {
			String key = (String)i.next();
			UserConfig userConfig = (UserConfig)map.get(key);
			if(key.equals(level)) {
				sb.append("<option value='" + key + "' selected>"+ userConfig.getRole() +"</option>");
			}else {
				sb.append("<option value='" + key + "'>"+ userConfig.getRole() +"</option>");
			}
		}
		sb.append("</select>");
		TagUtils.getInstance().write(super.pageContext, sb.toString());
		return EVAL_PAGE;
	}

}

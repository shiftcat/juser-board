package han.role;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.action.PlugIn;
import org.apache.struts.config.ModuleConfig;

public class RolePlugIn implements PlugIn
{
	private String path;
	
	private String className;
	
	private static final Log log = LogFactory.getLog(RolePlugIn.class);
	
	private RoleContext roles;
	
	public void setPath(String path)
	{
		this.path = path;
		if(log.isInfoEnabled()) {
			log.info("role 설정파일" + path + " 설정됨");
		}
	}
	
	public void setClassName(String className)
	{
		this.className = className;
	}
	
	public void destroy()
	{
		path = null;
		if(roles != null) {
			roles.release();
			if(log.isInfoEnabled()) {
				log.info("RoleContext Release");
			}
		}
		roles = null;
		if(log.isInfoEnabled()) {
			log.info("RolePlugIn destroy");
		}
	}
	
	public void init(ActionServlet servlet, ModuleConfig config) throws ServletException 
	{
		ServletContext context = null;
		try{
			context = servlet.getServletContext();
			String resource = context.getRealPath(path);
			roles = new RoleContext();
			//roles.setSource(resource);
			context.setAttribute(RoleContext.ROLE_CONFIG_CONTEXT_KEY, roles);
			if(log.isInfoEnabled()) {
				log.info("Role 로딩 성공" + path);
				log.info( context.getAttribute( Globals.MODULE_KEY ) );
			}
		}catch(Exception e) {
			log.fatal(e.getMessage(), e);
			throw new ServletException("Role 로딩 실패" + path);
		}
		if(log.isDebugEnabled()) {
			roles.debug();
		}
	}
}

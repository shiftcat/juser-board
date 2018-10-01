package han.juser.controller.action;

import han.juser.service.ServiceException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.struts.DelegatingActionProxy;

public class DelegateActionProxy extends DelegatingActionProxy
{
	protected final Log log = LogFactory.getLog( DelegateActionProxy.class );
	
	
	public DelegateActionProxy()
	{
		super();
	}
	
	
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception, ServiceException
	{
    	WebApplicationContext wac = getWebApplicationContext(getServlet(), mapping.getModuleConfig());
   	
    	Class c[] = new Class[]{ActionMapping.class, ActionForm.class, HttpServletRequest.class, HttpServletResponse.class};
    	Object param[] = new Object[]{mapping, form, request, response};
    	
    	Object obj = wac.getBean(mapping.getModuleConfig().getPrefix());
    	Method method = obj.getClass().getMethod(mapping.getParameter(), c);
    	
    	try {
    		return (ActionForward)method.invoke(obj, param);
    	} catch(InvocationTargetException te) {
    		Throwable ta = te.getTargetException();
    		if(ta instanceof ServiceException) {
    			throw (ServiceException)te.getTargetException();
    		}else if(ta instanceof RuntimeException) {
    			if(log.isErrorEnabled()) {
    				log.error(ta.getMessage(), ta);
    			}
    			throw (RuntimeException)te.getTargetException();
    		} else {
    			if(log.isErrorEnabled()) {
    				log.error(ta.getMessage(), ta);
    			}
    			throw (Exception)te.getTargetException();    			
    		}
    	}
	}
    
}

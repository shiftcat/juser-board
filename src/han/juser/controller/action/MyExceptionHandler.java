package han.juser.controller.action;

import han.juser.service.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ExceptionHandler;
import org.apache.struts.config.ExceptionConfig;

public class MyExceptionHandler extends ExceptionHandler 
{
    public ActionForward execute(Exception ex, ExceptionConfig ae, ActionMapping mapping, ActionForm formInstance, HttpServletRequest request, HttpServletResponse response) throws ServletException
	{
	    ActionForward forward;
	    if(ae.getPath() != null) {
	        forward = new ActionForward(ae.getPath());
	    } else {
	        forward = mapping.getInputForward();
	    }
	    
	    ActionMessage error = null;
	    if(ex instanceof ServiceException) {
	    	error = new ActionMessage("errors." + ex.getClass().getName(), ex.getMessage());	    	
	    } else {
	    	error = new ActionMessage(ae.getKey(), ex.getMessage());
	    }
	    String property = error.getKey();
	    
	    logException(ex);
	    request.setAttribute("org.apache.struts.action.EXCEPTION", ex);
	    storeException(request, property, error, forward, ae.getScope());
	    if(!response.isCommitted()) {
	        return forward;
	    } else {
	    	return null;
	    }
	}
}

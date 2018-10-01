package han.juser.service;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ServiceLoggerAdvice implements MethodInterceptor 
{
	protected final Log log = LogFactory.getLog( getClass() );
	
	public Object invoke(MethodInvocation invocation) throws Throwable 
	{
		String className = invocation.getThis().getClass().getName(); 
		if(log.isDebugEnabled()) {
			log.debug( className + '.' + invocation.getMethod().getName()+ "()" + " 호출");
			Object[] args = invocation.getArguments();
			if(args != null && args.length > 0) {
				for(int i = 0; i < args.length; i++) {
					log.debug("Argument[" + i + "] : " + args[i]);
				}
			}
		}
		
		Object retVal = invocation.proceed();
		
		if(log.isDebugEnabled()) {
			log.debug(className + '.' + invocation.getMethod().getName() + "()" + " 종료" );
		}
		
		return retVal;
	}

}

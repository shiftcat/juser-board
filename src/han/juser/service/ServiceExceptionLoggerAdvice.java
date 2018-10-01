package han.juser.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.aop.ThrowsAdvice;

public class ServiceExceptionLoggerAdvice implements ThrowsAdvice
{
	private final Log log = LogFactory.getLog(getClass());
	
	public void afterThrowing(Throwable e) throws Throwable
	{
		log.error(e.getMessage(), e);
	}
}

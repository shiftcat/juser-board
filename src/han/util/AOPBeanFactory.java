package han.util;

import org.aopalliance.aop.Advice;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.aop.Advisor;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

public class AOPBeanFactory 
         implements FactoryBean, BeanFactoryAware, InitializingBean
{
	
	private boolean singleton = false;
	
	private Object instance;
	
	private String[] interceptorNames;
	
	private Object target;
	
	private BeanFactory beanFactory;
	
	
	
	public Object getObject() throws Exception 
	{
		return instance;
	}

	public Class getObjectType() 
	{
		if(instance != null ) {
			return instance.getClass();
		}
		return null;
	}

	public boolean isSingleton() 
	{
		return this.singleton;
	}
	
	
	public void setTarget(Object target)
	{
		this.target = target;
	}
	
	public void setInterceptorNames(String[] names) 
	{
		this.interceptorNames = names;
	}
	
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException 
	{	
		this.beanFactory = beanFactory;
	}

	public void afterPropertiesSet() throws Exception 
	{
		if( interceptorNames == null || interceptorNames.length == 0) {
			instance =  target;
		} else {
			ProxyFactory pf = new ProxyFactory();
			pf.setTarget(target);
			for( int i = 0; i < interceptorNames.length; i++) {
				Advisor advisor = getAdvisor(interceptorNames[i]);
				if( advisor != null) {
					pf.addAdvisor(advisor);
				}
			}
			instance = pf.getProxy();
		}
	}
	
	
	public Advisor getAdvisor(String interceptorName)
	{
		Object adv = beanFactory.getBean(interceptorName);
		if( adv instanceof Advisor) {
			return (Advisor)adv;
		}else if (adv instanceof Advice) {
			return new DefaultPointcutAdvisor((Advice)adv);
		} else {
			return null;
		}
	}
	

}

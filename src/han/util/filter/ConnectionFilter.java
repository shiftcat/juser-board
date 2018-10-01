package han.util.filter;

import han.juser.db.oracle.ConnectionManager;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ConnectionFilter implements Filter 
{
	private final Log log = LogFactory.getLog(getClass());
	
	private ServletContext context;
	
	public void destroy() 
	{
		context = null;
		if(log.isInfoEnabled()) {
			log.info("ConnectionFilter destroy");
		}
	}
	
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException 
	{
		long start = System.currentTimeMillis();
		if(log.isDebugEnabled()) {
			log.debug( ((HttpServletRequest)req).getRequestURI() + "요청 시작");
		}
		try {
			ConnectionManager.setConnection(req);
			log.debug(chain);
			chain.doFilter(req, res);
		} catch (Throwable e) {
			log.error(e.getMessage(), e);
			throw new ServletException(e);
		} finally {
			ConnectionManager.releaseConnection(req);
		}
		long end = System.currentTimeMillis() - start;
		if(log.isDebugEnabled()) {
			double sec = (double)end / (double)1000;
			log.debug("응답시간 : " + sec + "sec");
		}
		if(log.isDebugEnabled()) {
			log.debug( ((HttpServletRequest)req).getRequestURI() + "요청종료");
		}
	}
	
	public void init(FilterConfig config) throws ServletException 
	{
		context = config.getServletContext();
		if(log.isInfoEnabled()) {
			log.info("ConnectionFilter init");
		}
	}

}

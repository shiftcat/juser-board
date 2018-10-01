package han.juser.url;

import java.util.ResourceBundle;

import javax.servlet.jsp.PageContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.taglib.TagUtils;


public class URLFactory 
{
	private static final Log log = LogFactory.getLog(URLFactory.class);
	
	private static ResourceBundle bundle = ResourceBundle.getBundle("han.juser.url.manager");
	
	public static URLManager getURLManager(PageContext pageContext)
	{
		String prefix = TagUtils.getInstance().getModuleConfig(pageContext).getPrefix();
		String className = bundle.getString(prefix);
		URLManager manager = null;
		try {
			manager = (URLManager)Class.forName(className).newInstance();
		} catch (Throwable e) {
			log.fatal(e.getMessage(), e);
			throw new RuntimeException("URLManager 생성 실패", e);
		}
		return manager;
	}

}

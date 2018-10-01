package han.juser.tag.link;

import java.util.ResourceBundle;

import javax.servlet.jsp.PageContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.taglib.TagUtils;

public class LinkFactory 
{
	private static final Log log = LogFactory.getLog(LinkFactory.class);
	
	
	public static IndexLink getIndexLink(PageContext pageContext)
	{
		String prefix = TagUtils.getInstance().getModuleConfig(pageContext).getPrefix();
		String className = ResourceBundle.getBundle("han.juser.tag.link.index").getString(prefix);
		try {
			return (IndexLink)Class.forName(className).newInstance();
		} catch (Throwable e ) {
			log.error(e.getMessage(), e);
			throw new RuntimeException(e.getMessage(), e);
		}
	}
	
	
	public static ViewLink getViewLink(PageContext pageContext)
	{
		String prefix = TagUtils.getInstance().getModuleConfig(pageContext).getPrefix();
		String className = ResourceBundle.getBundle("han.juser.tag.link.view").getString(prefix);
		try {
			return (ViewLink)Class.forName(className).newInstance();
		} catch (Throwable e ) {
			log.error(e.getMessage(), e);
			throw new RuntimeException(e.getMessage(), e);
		}
	}
	
	
	public static PageLink getPageLink(PageContext pageContext)
	{
		String prefix = TagUtils.getInstance().getModuleConfig(pageContext).getPrefix();
		String className = ResourceBundle.getBundle("han.juser.tag.link.page").getString(prefix);
		try {
			return (PageLink)Class.forName(className).newInstance();
		} catch (Throwable e ) {
			log.error(e.getMessage(), e);
			throw new RuntimeException(e.getMessage(), e);
		}
	}
	
	
	public static WriteFormLink getWriteFormLink(PageContext pageContext)
	{
		String prefix = TagUtils.getInstance().getModuleConfig(pageContext).getPrefix();
		String className = ResourceBundle.getBundle("han.juser.tag.link.write").getString(prefix);
		try {
			return (WriteFormLink)Class.forName(className).newInstance();
		} catch (Throwable e ) {
			log.error(e.getMessage(), e);
			throw new RuntimeException(e.getMessage(), e);
		}
	}
	
	
	public static ModifyFormLink getModifyFormLink(PageContext pageContext)
	{
		String prefix = TagUtils.getInstance().getModuleConfig(pageContext).getPrefix();
		String className = ResourceBundle.getBundle("han.juser.tag.link.modify").getString(prefix);
		try {
			return (ModifyFormLink)Class.forName(className).newInstance();
		} catch (Throwable e ) {
			log.error(e.getMessage(), e);
			throw new RuntimeException(e.getMessage(), e);
		}
	}

}

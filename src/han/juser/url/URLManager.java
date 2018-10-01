package han.juser.url;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class URLManager
{
	protected static final Log log = LogFactory.getLog(URLManager.class);
	
	protected final String CHAR_SET = "UTF-8";
	
	private Map map = new HashMap();
	
	protected ServletRequest request;
	
	
	
	public URLManager()
	{
		
	}
	
	
	public URLManager(ServletRequest req)
	{
		request = req;
		parseParameter(req);
	}

	public abstract void parseParameter(ServletRequest req);
	
	
	protected abstract String getModulName();
	
	
	/**
	 * '?'������ ���ڿ��� �����Ѵ�.
	 * @return ������Ʈ���� ����
	 */
	public String getQueryString()
	{
		String key = null;
		String value = null;
		StringBuffer query = new StringBuffer();
		Iterator it = map.keySet().iterator();
		int cnt = 1;
		while(it.hasNext()) {
			key = it.next().toString();
			try {
				value = URLEncoder.encode(map.get(key).toString(), CHAR_SET);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
			query.append(key + "=" + value);
			if(cnt < map.size()) {
				query.append('&');
			}
			cnt++;
		}
		return query.toString();
	}
	
	
	/**
	 * URI�� ������Ʈ���� ���յ� ���ڿ��� �����Ѵ�.
	 * @param key ���ҽ����鿡 ���ǵ� action key �̸�
	 * @return URI�� ������Ʈ���� ���յ� ���ڿ��� ����
	 */
	public String getURI(String action)
	{
		String contextPath = ((HttpServletRequest)request).getContextPath();
		String modulPath = getModulName();
		if( this.getQueryString().equals("") ) {
			return contextPath + modulPath + action + ".do";
		}else {
			return contextPath + modulPath + action + ".do" + "?" + this.getQueryString();
		}
	}
	
	
	/**
     * 
     * @param key
     * @param value
     */
	public void setParameter(String key, String value)
	{
		this.map.put(key, value);
	}

	/**
     * 
     * @param key
     */
	public void removeParameter(String key)
	{
		this.map.remove(key);
	}

	public String getParameter(String key)
	{
		return (String)this.map.get(key);
	}

	public Map getMap()
	{
		return new HashMap(map);
	}

	public void clear()
	{
		map.clear();
	}

}

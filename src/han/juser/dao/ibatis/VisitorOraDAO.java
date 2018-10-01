package han.juser.dao.ibatis;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import han.juser.dao.VisitorDAO;

public class VisitorOraDAO  extends SqlMapBaseDAO implements VisitorDAO
{

	public long insert(String userAgent, String remoteAddr, String remoteHost, String referer) 
	{
		Map map = new HashMap();
		map.put("userAgent", userAgent);
		map.put("userAddr", remoteAddr);
		map.put("remoteHost", remoteHost);
		map.put("referer", referer);
		return (Long)getSqlMapClientTemplate().insert("Visitor.insert", map);
	}

	public int delete(long idx)
	{
		return 0;
	}

	public long getRowCount()
	{
		return (Long)getSqlMapClientTemplate().queryForObject("Visitor.rowCount");
	}

	public long getRowCount(Date date) 
	{
		java.util.Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		java.sql.Timestamp t = new java.sql.Timestamp(c.getTimeInMillis());
		return (Long)getSqlMapClientTemplate().queryForObject("Visitor.rowCountToday", t);
	}

}

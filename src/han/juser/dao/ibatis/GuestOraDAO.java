package han.juser.dao.ibatis;

import han.juser.dao.GuestDAO;
import han.juser.model.GuestDTO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class GuestOraDAO extends SqlMapBaseDAO implements GuestDAO 
{
	
	public int delete(long gid) 
	{
		return getSqlMapClientTemplate().delete("Guest.guestDelete", new Long(gid));
	}

	public GuestDTO getContent(long gid) 
	{
		return (GuestDTO)getSqlMapClientTemplate().queryForObject("Guest.guestContent", new Long(gid));
	}

	public List getContentList(long start, int limit) 
	{
		HashMap param = new HashMap();
		param.put("start", new Long(start));
		param.put("limit", new Integer(limit));
		return getSqlMapClientTemplate().queryForList("Guest.guestContentList", param);
	}

	public List getContentList(String userId, long start, int limit) 
	{
		HashMap param = new HashMap();
		param.put("start", new Long(start));
		param.put("limit", new Integer(limit));
		param.put("userid", userId);
		//return getSqlMapClientTemplate().queryForList("Guest.guestUserContentList", param);
		return getSqlMapClientTemplate().queryForList("Guest.guestContentList", param);
	}

	public long getRowCount() 
	{
		Long cnt = (Long)getSqlMapClientTemplate().queryForObject("Guest.guestRowCount");
		return cnt == null ? -1 : cnt;
	}

	public long getRowCount(String userId) 
	{
		Long cnt = (Long)getSqlMapClientTemplate().queryForObject("Guest.guestUserRowCount", userId);
		return cnt == null ? -1 : cnt;
	}

	public long insert(String userId, String content, String remoteAddr) 
	{
		Map param = new HashMap();
		param.put("userid", userId);
		param.put("content", content);
		param.put("remoteAddr", remoteAddr);
		Long rs = (Long)getSqlMapClientTemplate().insert("Guest.guestInsert", param);
		return rs == null ? -1 : rs;
	}

	public int update(long gid, String content, String remoteAddr) 
	{
		Map map = new HashMap();
		map.put("gid", gid);
		map.put("content", content);
		map.put("remoteAddr", remoteAddr);
		return getSqlMapClientTemplate().update("Guest.guestUpdate", map);
	}

	public List getSummary(int limit) 
	{
		return getSqlMapClientTemplate().queryForList("Guest.guestSummary", new Integer(limit));
	}

}

package han.juser.dao.ibatis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ibatis.sqlmap.client.event.RowHandler;

import han.juser.dao.JBoardDAO;
import han.juser.model.JBoardDTO;

public class JBoardOraDAO extends SqlMapBaseDAO implements JBoardDAO 
{

	
	public class JBoardRowHandler implements RowHandler
	{
		private long rownum;
		
		private List list = new ArrayList();
		
		public JBoardRowHandler(long rownum)
		{
			this.rownum = rownum;
		}
		

		public void handleRow(Object value) 
		{
			JBoardDTO dto = (JBoardDTO)value;
			dto.setRownum(this.rownum--);
			list.add(dto);
		}
		
		public List getCotentList()
		{
			return list;
		}
	}
	
	
	public long getRowCount() 
	{
		return (Long)getSqlMapClientTemplate().queryForObject("JBoard.getRowCount", new HashMap());
	}

	public long getRowCount(long articleId) 
	{
		return (Long)getSqlMapClientTemplate().queryForObject("JBoard.getChildRowCount", articleId);
	}

	public long getRowCount(String column, String keyword) 
	{
		Map map = new HashMap();
		map.put("target", column.toUpperCase());
		map.put("keyword", column.equalsIgnoreCase("content") ? keyword : '%' + keyword + '%');
		return (Long)getSqlMapClientTemplate().queryForObject("JBoard.getRowCount", map);
	}

	
	
	public List getContentList(long start, long end)
	{
		Map map = new HashMap();
		map.put("start", start);
		map.put("end", end);
		JBoardRowHandler rowHandler = new JBoardRowHandler(getRowCount() - start);
		getSqlMapClientTemplate().queryWithRowHandler("JBoard.getContentList", map, rowHandler);
		return rowHandler.getCotentList();
	}

	public List getContentList(long start, long end, String column, String keyword) 
	{
		Map map = new HashMap();
		map.put("start", start);
		map.put("end", end);
		map.put("target", column.toUpperCase());
		map.put("keyword", column.equalsIgnoreCase("content") ? keyword : '%' + keyword + '%');
		JBoardRowHandler rowHandler = new JBoardRowHandler(getRowCount() - start);
		getSqlMapClientTemplate().queryWithRowHandler("JBoard.getContentList", map, rowHandler);
		return rowHandler.getCotentList();
	}

	public List getChildList(long articleId) 
	{
		JBoardRowHandler rowHandler = new JBoardRowHandler(getRowCount(articleId));
		getSqlMapClientTemplate().queryWithRowHandler("JBoard.getChildList", articleId, rowHandler);
		return rowHandler.getCotentList();
	}

	
	public JBoardDTO getContent(long articleId) 
	{
		return (JBoardDTO)getSqlMapClientTemplate().queryForObject("JBoard.getContent", articleId);
	}

	public long insert(JBoardDTO dto) 
	{
		if( getContent(dto.getParentId()) == null ) {
			return (Long)getSqlMapClientTemplate().insert("JBoard.insertRoot", dto);
		} else {
			return (Long)getSqlMapClientTemplate().insert("JBoard.insertChild", dto);
		}
		
	}

	public int update(JBoardDTO dto) 
	{
		return getSqlMapClientTemplate().update("JBoard.update",dto);
	}

	public void updateHit(long articleId) 
	{
		getSqlMapClientTemplate().update("JBoard.updateHit", articleId);
	}

	public void updateVote(long articleId) 
	{
		getSqlMapClientTemplate().update("JBoard.updateVote", articleId);
	}

	public int deleteAll(long articleId) 
	{
		return getSqlMapClientTemplate().delete("JBoard.deleteAll", articleId);
	}

	public int deleteChild(long parentId)
	{
		return getSqlMapClientTemplate().delete("JBoard.deleteChild", parentId);
	}

	public int unSetFileNames(long articleId)
	{
		return getSqlMapClientTemplate().update("JBoard.unSetFile", articleId);
	}

	public List getSummary(int limit)
	{
		return getSqlMapClientTemplate().queryForList("JBoard.getSummary", limit);
	}

}

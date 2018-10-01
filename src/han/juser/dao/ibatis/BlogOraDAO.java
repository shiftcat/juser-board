package han.juser.dao.ibatis;

import han.juser.dao.BlogDAO;
import han.juser.dao.ibatis.CategoryOraDAO;
import han.juser.model.BlogDTO;
import han.juser.model.BlogReplyDTO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ibatis.sqlmap.client.event.RowHandler;

public class BlogOraDAO extends CategoryOraDAO implements BlogDAO
{
	
	public class BlogRowHandler implements RowHandler
	{
		private long rownum;
		
		private List list = new ArrayList();
		
		public BlogRowHandler(long rn)
		{
			rownum = ++rn;
		}
		
		public void handleRow(Object obj) 
		{
			BlogDTO dto = (BlogDTO)obj;
			dto.setRowNum(rownum--);
			list.add(dto);
		}
		
		public List getContentList()
		{
			return list;
		}
		
	}
	

	public int deleteContent(long articleId) 
	{
		return getSqlMapClientTemplate().delete("Blog.deleteBlog", articleId);
	}

	public int deleteReply(long replyId) 
	{
		return getSqlMapClientTemplate().delete("Blog.deleteReply", replyId);
	}

	public BlogDTO getContent(long articleId) 
	{
		return (BlogDTO)getSqlMapClientTemplate().queryForObject("Blog.getContent",articleId);
	}

	public List getContentList(int cateId, long start, int limit) 
	{
		Map map = new HashMap();
		map.put("start", start);
		map.put("limit", limit);
		map.put("cateId", cateId);
		
		BlogRowHandler handler = new BlogRowHandler(getRowCount(cateId)-start);
		getSqlMapClientTemplate().queryWithRowHandler("Blog.getContentList", map, handler);
		return handler.getContentList();
	}

	public List getContentList(int cateId, long start, int limit, String column, String keyword) 
	{
		Map map = new HashMap();
		map.put("start", start);
		map.put("limit", limit);
		map.put("cateId", cateId);
		map.put("target", column.toUpperCase());
		map.put("keyword", column.equalsIgnoreCase("content") ? keyword : '%'+keyword+'%');
		
		BlogRowHandler handler = new BlogRowHandler(getRowCount(cateId, column, keyword)-start);
		getSqlMapClientTemplate().queryWithRowHandler("Blog.getContentList", map, handler);
		return handler.getContentList();
	}

	public List getContentList(long start, int limit) 
	{
		Map map = new HashMap();
		map.put("start", start);
		map.put("limit", limit);
		
		BlogRowHandler handler = new BlogRowHandler(getRowCount()-start);
		getSqlMapClientTemplate().queryWithRowHandler("Blog.getContentList", map, handler);
		return handler.getContentList();
	}

	
	public List getContentList(long start, int limit, String column, String keyword)
	{
		Map map = new HashMap();
		map.put("start", start);
		map.put("limit", limit);
		map.put("target", column.toUpperCase());
		map.put("keyword", column.equalsIgnoreCase("content") ? keyword : '%'+keyword+'%');
		
		BlogRowHandler handler = new BlogRowHandler(getRowCount(column, keyword)-start);
		getSqlMapClientTemplate().queryWithRowHandler("Blog.getContentList", map, handler);
		return handler.getContentList();
	}

	public List getReplyList(long articleId)
	{
		return getSqlMapClientTemplate().queryForList("Blog.getReplyList", articleId);
	}

	public BlogReplyDTO getReply(long replyId) 
	{
		return (BlogReplyDTO)getSqlMapClientTemplate().queryForObject("Blog.getReplyContent", replyId);
	}

	public long getRowCount() 
	{
		Map map = new HashMap();
		return (Long)getSqlMapClientTemplate().queryForObject("Blog.getRowCount", map);
	}

	public long getRowCount(int cateId) 
	{
		Map map = new HashMap();
		map.put("cateId",cateId);
		return (Long)getSqlMapClientTemplate().queryForObject("Blog.getRowCount", map);
	}

	public long getRowCount(int cateId, String column, String keyword) 
	{
		Map map = new HashMap();
		map.put("cateId",cateId);
		map.put("target", column.toUpperCase());
		map.put("keyword", column.equalsIgnoreCase("content") ? keyword : '%'+keyword+'%');
		return (Long)getSqlMapClientTemplate().queryForObject("Blog.getRowCount", map);
	}

	public long getRowCount(String column, String keyword)
	{
		Map map = new HashMap();
		map.put("target", column.toUpperCase());
		map.put("keyword", column.equalsIgnoreCase("content") ? keyword : '%'+keyword+'%');
		return (Long)getSqlMapClientTemplate().queryForObject("Blog.getRowCount", map);
	}

	public long insertContent(BlogDTO dto) 
	{
		return (Long)getSqlMapClientTemplate().insert("Blog.insertBlog", dto);
	}

	public int insertReply(BlogReplyDTO reply) 
	{
		return (Integer)getSqlMapClientTemplate().insert("Blog.insertReply", reply);
	}

	public int unSetFileNames(long articleId) 
	{
		return getSqlMapClientTemplate().update("Blog.unsetFile", articleId);
	}

	public int updateContent(BlogDTO dto) 
	{
		return getSqlMapClientTemplate().update("Blog.updateBlog", dto);
	}


	public List getSummary(int limit) 
	{
		return getSqlMapClientTemplate().queryForList("Blog.summary", limit);
	}

}

package han.juser.dao.ibatis;

import han.juser.dao.BoardDAO;
import han.juser.model.BoardDTO;
import han.juser.model.BoardReplyDTO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BoardOraDAO extends SqlMapBaseDAO implements BoardDAO
{
	
	public BoardOraDAO()
	{
		super();
	}
	
	
	public int delete(long bbsId) 
	{
		return getSqlMapClientTemplate().delete("Board.boardDelete", new Long(bbsId));
	}

	public int deleteReply(long replId) 
	{
		return getSqlMapClientTemplate().delete("Board.boardReplyDelete", new Long(replId));
	}

	public BoardDTO getContent(long bbsId) 
	{
		BoardDTO dto = null;
		dto = (BoardDTO)getSqlMapClientTemplate().queryForObject("Board.boardContent", new Long(bbsId));
		/*
		if( dto != null ) {
			dto.setReplyList(getReplyList(bbsId));
		}
		*/
		return dto;
	}

	public List getReplyList(long bbsId)
	{
		return getSqlMapClientTemplate().queryForList("Board.boardReplyList", new Long(bbsId));
	}
	
	
	public List getContentList(long start, int limit) 
	{
		Map map = new HashMap();
		map.put("start", start);
		map.put("limit", limit);
		return getSqlMapClientTemplate().queryForList("Board.boardContentList", map);
	}

	public List getContentList(long start, int limit, String column, String keyword) 
	{
		Map map = new HashMap();
		map.put("start", start);
		map.put("limit", limit);
		map.put("target", column.toUpperCase());
		map.put("keyword", column.equalsIgnoreCase("content") ? keyword : '%' + keyword + '%' );
		return getSqlMapClientTemplate().queryForList("Board.boardContentList", map);
	}

	public BoardReplyDTO getReply(long replId) 
	{
		return (BoardReplyDTO)getSqlMapClientTemplate().queryForObject("Board.boardReply", new Long(replId));
	}

	public long getRowCount() 
	{
		Long cnt = (Long)getSqlMapClientTemplate().queryForObject("Board.boardRowCount");
		return cnt == null ? 0 : cnt;
	}

	public long getRowCount(String column, String keyword) 
	{
		Long cnt = null;
		if(column.equalsIgnoreCase("content")) {
			cnt = (Long)getSqlMapClientTemplate().queryForObject("Board.boardSearchRowCount", keyword);
		} else if( column.equalsIgnoreCase("userid") ) {
			cnt = (Long)getSqlMapClientTemplate().queryForObject("Board.boardSearchUserRowCount", '%' + keyword + '%');
		} else {
			cnt = (Long)getSqlMapClientTemplate().queryForObject("Board.boardSearchSubjectRowCount", '%' + keyword + '%');
		}
		return cnt == null ? 0 : cnt;
	}

	public long insert(String userId, String subject, String content, String remoteAddr, String link, String originalFiles, String systemFiles) 
	{
		BoardDTO dto = new BoardDTO();
		dto.setUserId(userId);
		dto.setSubject(subject);
		dto.setContent(content);
		dto.setRemoteAddr(remoteAddr);
		dto.setLink(link);
		dto.setOriginalFiles(originalFiles);
		dto.setSystemFiles(systemFiles);
		Long id = (Long)getSqlMapClientTemplate().insert("Board.boardInsert", dto);
		return id == null ? 0 : id;
	}

	public int insertReply(long parentId, String userId, String remoteAddr, String reply) 
	{
		Map map = new HashMap();
		map.put("bbsId", parentId);
		map.put("userId", userId);
		map.put("remoteAddr", remoteAddr);
		map.put("reply", reply);
		return getSqlMapClientTemplate().insert("Board.boardInsertReply", map) != null ? 1 : -1;
	}

	public int update(long contentId, String subject, String content, String link, String remoteAddr, String originalFiles, String systemFiles) 
	{
		Map map = new HashMap();
		map.put("bbsId", contentId);
		map.put("subject", subject);
		map.put("content", content);
		map.put("link", link);
		map.put("remoteAddr", remoteAddr);
		map.put("originalFiles", originalFiles);
		map.put("systemFiles", systemFiles);
		return getSqlMapClientTemplate().update("Board.boardUpdateWithFile", map);
	}


	public int update(long contentId, String subject, String content, String link, String remoteAddr) 
	{
		Map map = new HashMap();
		map.put("bbsId", contentId);
		map.put("subject", subject);
		map.put("content", content);
		map.put("link", link);
		map.put("remoteAddr", remoteAddr);
		return getSqlMapClientTemplate().update("Board.boardUpdate", map);
	}

	
	public void updateHitCount(long bbsId) 
	{
		getSqlMapClientTemplate().update("Board.boardUpdateHit", new Long(bbsId));
	}

	public void updateVoteCount(long bbsId) 
	{
		getSqlMapClientTemplate().update("Board.boardUpdateVote", new Long(bbsId));
	}

	public List getSummary(int limit) 
	{
		return getSqlMapClientTemplate().queryForList("Board.boardSummary", new Integer(limit));
	}



}

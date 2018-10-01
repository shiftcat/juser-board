package han.juser.dao.spring;

import han.juser.dao.BoardDAO;
import han.juser.dao.DAOException;
import han.juser.db.oracle.LobUtil;
import han.juser.model.BoardDTO;
import han.juser.model.BoardReplyDTO;
import han.juser.model.SummaryDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.SqlLobValue;

public class BoardOraDAO extends SpringDAOSupport implements BoardDAO , ResultSetExtractor
{
	
	protected static ResourceBundle sql;
	
	private static volatile long id = -1;
	
	private static volatile long replyId = -1;
	
	
	public BoardOraDAO()
	{
		super();
	}
	
	public void setSqlResource(String arg)
	{
		try {
			sql = ResourceBundle.getBundle(arg);
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	
	
	private static synchronized long getNextId(JdbcTemplate jt)
	{
		if( id == -1 ) {
			String sql = "SELECT max(bbsid) FROM bbs";
			id = jt.queryForLong(sql);
		}
		return ++id;
	}
	
	
	
	private static synchronized long getReplyNextId(JdbcTemplate jt) throws DAOException
	{
		if( replyId == -1) {
			replyId = jt.queryForLong(sql.getString("board.oracle.ReplyCurrentId"));
		}
		return ++replyId;
	}
	
	
	
	
	public Object extractData(ResultSet rs) throws SQLException, DataAccessException 
	{
		List list = new ArrayList();
		while(rs.next()) {
			BoardDTO dto = new BoardDTO();
			dto.setBbsId(rs.getLong("BBSID"));
			dto.setUserId(rs.getString("USERID"));
			dto.setReplyCount( rs.getInt("REPLYCOUNT") );
			dto.setSubject(rs.getString("SUBJECT"));
			dto.setOriginalFiles(rs.getString("ORIGINALFILES"));
			dto.setSystemFiles(rs.getString("SYSTEMFILES"));
			dto.setHit(rs.getInt("HIT"));
			dto.setVote(rs.getInt("VOTE"));
			dto.setLogDate(rs.getTimestamp("LOGDATE"));
			dto.setRemoteAddr(rs.getString("REMOTEADDR"));
			list.add(dto);
		}
		return list;
	}
	
	
	
	

	public int delete(long bbsId) 
	{
		return getJdbcTemplate().update(sql.getString("board.oracle.Delete"), new Object[]{new Long(bbsId)});
	}

	public int deleteReply(long replId) 
	{
		return getJdbcTemplate().update(sql.getString("board.oracle.DeleteReply"), new Object[]{new Long(replId)});
	}
	
	
	private void appendReplyList(long bbsId, final BoardDTO dto)
	{
		getJdbcTemplate().query(sql.getString("board.oracle.ReplyList"),
				new Object[]{new Long(bbsId)},
				new ResultSetExtractor()
				{
					public Object extractData(ResultSet rs) throws SQLException
					{
						while(rs.next()) {
							BoardReplyDTO re = new BoardReplyDTO();
							re.setReplId( rs.getLong("REPLID") );
							re.setBbsId( rs.getInt("BBSID") );
							re.setUserId( rs.getString("USERID") );
							re.setReply( rs.getString("REPLE") );
							re.setLogdate( rs.getTimestamp("LOGDATE") );
							re.setRemoteAddr( rs.getString("REMOTEADDR") );
							dto.setReply(re);
						}
						return null;
					}
				}
		);
	}
	
	
	public BoardDTO getContent(final long bbsId) 
	{
		return (BoardDTO)getJdbcTemplate().queryForObject( 
				sql.getString("board.oracle.Content"), 
				new Object[]{new Long(bbsId)},
				new RowMapper()
				{
					public Object mapRow(ResultSet rs, int rowNum) throws SQLException
					{
						BoardDTO dto = new BoardDTO();
						dto.setBbsId( rs.getLong("BBSID") );
						dto.setUserId(rs.getString("USERID"));
						dto.setSubject(rs.getString("SUBJECT"));
						dto.setOriginalFiles(rs.getString("ORIGINALFILES"));
						dto.setSystemFiles(rs.getString("SYSTEMFILES"));
						dto.setHit(rs.getInt("HIT"));
						dto.setVote(rs.getInt("VOTE"));
						dto.setLink(rs.getString("LINK"));
						dto.setLogDate(rs.getTimestamp("LOGDATE"));
						dto.setRemoteAddr(rs.getString("REMOTEADDR"));
						dto.setContent( BoardOraDAO.super.lobHandler.getClobAsString(rs, "CONTENT") );
						appendReplyList(bbsId, dto);
						return dto;
					}
				}
		);
	}

	public List getContentList(long start, int limit) 
	{
		return (List)getJdbcTemplate().query(sql.getString("board.oracle.ContentList"), new Object[]{new Long(start), new Integer(limit)}, this);
	}

	public List getContentList(long start, int limit, String column, String keyword)
	{
		String query = null;
		
		if(column.equalsIgnoreCase("content")) {
			query = sql.getString("board.oracle.search.content.ContentList");
		} else if( column.equalsIgnoreCase("userid") ) {
			query = sql.getString("board.oracle.search.userid.ContentList");
			keyword = '%' + keyword + '%';
		} else {
			query = sql.getString("board.oracle.search.subject.ContentList");
			keyword = '%' + keyword + '%';
		}
		
		return (List)getJdbcTemplate().query(query, new Object[]{keyword, keyword, new Long(start), new Integer(limit+1)}, this);
	}

	
	public BoardReplyDTO getReply(long replId) 
	{
		return (BoardReplyDTO)getJdbcTemplate().queryForObject(sql.getString("board.oracle.Reply"),
				new Object[]{new Long(replId)},
				new RowMapper()
				{
					public Object mapRow(ResultSet rs, int rowNum) throws SQLException
					{
						BoardReplyDTO dto = new BoardReplyDTO();
						dto.setReplId( rs.getLong("REPLID") );
						dto.setBbsId( rs.getInt("BBSID") );
						dto.setUserId( rs.getString("USERID") );
						dto.setReply( rs.getString("REPLE") );
						dto.setLogdate( rs.getTimestamp("LOGDATE") );
						dto.setRemoteAddr( rs.getString("REMOTEADDR") );
						return dto;
					}
				}
		);
	}

	public long getRowCount() 
	{
		String sql = "SELECT count(bbsid) FROM bbs";
		return getJdbcTemplate().queryForLong(sql);
	}

	public long getRowCount(String column, String keyword) 
	{
		String query = null;
		if(column.equalsIgnoreCase("content")) {
			query = sql.getString("board.oracle.search.content.RowCount");
		} else if( column.equalsIgnoreCase("userid") ) {
			query = sql.getString("board.oracle.search.userid.RowCount");
			keyword = '%' + keyword + '%';
		}else {
			query = sql.getString("board.oracle.search.subject.RowCount");
			keyword = '%' + keyword + '%';
		}
		
		return getJdbcTemplate().queryForLong(query, new Object[]{keyword});
	}

	
	public long insert(String userId, String subject, String content, String remoteAddr, String link, String originalFiles, String systemFiles) 
	{
		/*
			INSERT INTO bbs(bbsid, userid, subject, content, logdate, link, remoteaddr, originalfiles, systemfiles) \
        	VALUES(?, ?, ?, ?, sysdate, ?, ?, ?, ?)
		 */
		
		JdbcTemplate jt = getJdbcTemplate();
		long bbsId = getNextId(jt);
		jt.update( sql.getString("board.oracle.InsertWithContent"), 
				new Object[]{ new Long(bbsId), userId, subject, new SqlLobValue(content, lobHandler), link, remoteAddr,  originalFiles, systemFiles },
				new int[]{ Types.BIGINT, Types.VARCHAR, Types.VARCHAR, Types.CLOB, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR }
		);
		
		return bbsId;
	}

	public int insertReply(long parentId, String userId, String remoteAddr, String reply) 
	{
		/*
		 * INSERT INTO reply(replid, bbsid, userid, reple, remoteaddr, logdate) VALUES(?, ?, ?, ?, ?, sysdate)
		 */
		JdbcTemplate jt = getJdbcTemplate();
		long nextId = getReplyNextId(jt);
		return jt.update(sql.getString("board.oracle.InsertReply"), new Object[]{new Long(nextId), new Long(parentId), userId, reply, remoteAddr});
	}

	
	public int update(long contentId, String subject, String content, String link, String remoteAddr, String originalFiles, String systemFiles) 
	{
		/*
		UPDATE bbs SET                  \
				subject 	  = ?       \
	    , 		link 		  = ?       \
	    , 		remoteaddr 	  = ?       \
	    , 		content 	  = ?       \
	    , 		systemfiles   = ?       \
	    , 		originalfiles = ?       \
	    , 		logdate       = sysdate \
		WHERE                           \
				bbsid         = ? 
		 */
		
		return getJdbcTemplate().update(sql.getString("board.oracle.UpdateWithContent"),
				new Object[]{subject, link, remoteAddr, new SqlLobValue(content, lobHandler), systemFiles, originalFiles, new Long(contentId)},
				new int[]{Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.CLOB, Types.VARCHAR, Types.VARCHAR, Types.BIGINT});
	}

	
	
	public void updateHitCount(long bbsId)
	{
		getJdbcTemplate().update(sql.getString("board.oracle.UpdateHitCount"), new Object[]{new Long(bbsId)});
	}

	
	
	public void updateVoteCount(long bbsId) 
	{
		getJdbcTemplate().update(sql.getString("board.oracle.UpdateVoteCount"), new Object[]{new Long(bbsId)});
	}

	
	
	public List getSummary(int limit) 
	{
		String sql = "SELECT /*+ index_desc(bbs pk_bbs_bbsid) */ rownum rn, bbsid, subject, content, logdate FROM bbs WHERE rownum <= ?";
		return (List)getJdbcTemplate().query(sql, new Object[]{new Integer(limit)}, 
				new ResultSetExtractor()
				{
					public Object extractData(ResultSet rs) throws SQLException
					{
						List summary = new ArrayList();
						while (rs.next() ) {
							SummaryDTO dto = new SummaryDTO();
							dto.setContentId( rs.getLong("BBSID") );
							dto.setSubject( rs.getString("SUBJECT") );
							dto.setContent( LobUtil.getClobData(rs, "CONTENT") );
							dto.setSignDate( rs.getTimestamp("LOGDATE") );
							summary.add(dto);
						}
						return summary;
					}
				}
		);
	}

	public int update(long contentId, String subject, String content, String link, String remoteAddr) {
		// TODO Auto-generated method stub
		return 0;
	}

}

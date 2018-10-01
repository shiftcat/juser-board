package han.juser.dao.core;

import han.juser.dao.BoardDAO;
import han.juser.dao.DAOException;
import han.juser.db.oracle.LobUtil;
import han.juser.model.BoardDTO;
import han.juser.model.BoardReplyDTO;
import han.juser.model.SummaryDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class BoardOraDAO extends OraDAOBase implements BoardDAO
{
	
	private static ResourceBundle sql; 
	
	private static volatile long id = -1;
	
	private static volatile long replyId = -1;
	
	public BoardOraDAO()
	{
		try {
			sql = ResourceBundle.getBundle("han.juser.dao.core.BoardSQL");
		} catch (Exception e) {
			log.fatal(e.getMessage(), e);
		}
	}
	
	
	
	
	private static synchronized void setId(Connection con) throws SQLException 
	{
		Statement stmt = null;
		ResultSet rs = null;
		try{
			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT max(bbsid) FROM bbs");
			if(rs.next()) {
				id = rs.getLong(1);
			}
		}finally {
			if( rs != null) try { rs.close(); } catch (SQLException e) {}
			if( stmt != null) try { stmt.close(); } catch (SQLException e) {}
		}
	}
	
	
	private static synchronized long getNextId(Connection con) throws SQLException
	{
		if( id == -1 ) {
			setId(con);
		}
		return ++id;
	}
	
	
	
	
	// 오라클에서 지원 않함
	private long getLastInsertId(Statement stmt) throws SQLException 
	{
		ResultSet rs = null;
		long maxId = -1;
		try{
			rs = stmt.getGeneratedKeys();
			if(rs.next()) {
				maxId = rs.getLong(1);
			}
		}finally{
			close(rs);
		}
		return maxId;
	}
	
	
	
	// 여러스레드에서 동일한 값을 취득하는 문제 있음
	private int getCurrentId(Connection con) throws SQLException
	{
		Statement stmt = null;
		ResultSet rs = null;
		
		int id = -1;
		try{
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql.getString("board.oracle.CurrentId"));
			if( rs.next()) {
				id = rs.getInt(1);
			}
		}finally {
			close(rs);
			close(stmt);
		}
		return id;
	}
	
	

	public long insert(String userId, String subject, String content, String remoteAddr, String link, 
			String originalFiles, String systemFiles)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		
		long id = -1;
		int index = 1;
		try{
			con = ds.getConnection();
			con.setAutoCommit(false);
			// content를 제외한 내용 인서트
			id = getNextId(con);
			pstmt = con.prepareStatement( sql.getString("board.oracle.Insert") );
			pstmt.setLong(index++, id);
			pstmt.setString(index++, userId );
			pstmt.setString(index++, subject );
			pstmt.setString(index++, link );
			pstmt.setString(index++, remoteAddr);
			pstmt.setString(index++, originalFiles );
			pstmt.setString(index++, systemFiles );
			int irs = pstmt.executeUpdate();
			if( irs != 1){
				con.rollback();
				return -1;
			}
			
			// lob데이터 인서트
			if( !updateClob(con, id, content ) ) {
				con.rollback();
				return -1;
			}
			
			// 커밋
			con.commit();
		} catch (Throwable e) {
			log.error(e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		}finally{
			close(pstmt);
			close(con);
		}
		
		// 인서트한 아이디 값 리턴
		return id;
	}
	
	
	
	private boolean updateClob(Connection con, long bbsId, String data)
	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try{
			pstmt = con.prepareStatement( sql.getString("board.oracle.UpdateClob") );
			pstmt.setLong(1, bbsId);
			rs = pstmt.executeQuery();
			
			if( rs.next() ) {
				return LobUtil.setClobDate(rs, "CONTENT", data);
			}else {
				return false;
			}
		}catch(Throwable e){
			log.warn(e.getMessage(), e);
			return false;
		}finally {
			close(rs);
			close(pstmt);
		}
	}
	
	
	
	public List getContentList(long start, int limit) 
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try{
			con = ds.getConnection();
			pstmt = con.prepareStatement( sql.getString("board.oracle.ContentList") );
			pstmt.setLong(1, start );
			pstmt.setInt(2, limit+1 );
			rs = pstmt.executeQuery();
			return getArrayList(rs);
		} catch (Throwable e) {
			log.error(e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		}finally{
			close(rs);
			close(pstmt);
			close(con);
		}
		
	}
	
	
	
	
	public List getContentList(long start, int limit , String column, String keyword)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try{
			con = ds.getConnection();
			if(column.equalsIgnoreCase("content")) {
				pstmt = con.prepareStatement( sql.getString("board.oracle.search.content.ContentList") );
				pstmt.setString(1, keyword);
				pstmt.setString(2, keyword);
			} else if( column.equalsIgnoreCase("userid") ) {
				pstmt = con.prepareStatement(  sql.getString("board.oracle.search.userid.ContentList") );
				pstmt.setString(1, '%'+keyword+'%');
				pstmt.setString(2, '%'+keyword+'%');
			}else {
				pstmt = con.prepareStatement(  sql.getString("board.oracle.search.subject.ContentList") );
				pstmt.setString(1, '%'+keyword+'%');
				pstmt.setString(2, '%'+keyword+'%');
			}
			pstmt.setLong(3, start );
			pstmt.setInt(4, limit+1 );
			rs = pstmt.executeQuery();
			return getArrayList(rs);
		} catch (Throwable e) {
			log.error(e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		}finally{
			close(rs);
			close(pstmt);
			close(con);
		}
		
	}
	
	
	
	
	private List getArrayList(ResultSet rs) throws SQLException
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
	
	
	
	
	public long getRowCount()
	{
		Connection con = null;
		ResultSet rs = null;
		Statement stmt = null;
		String sql = "SELECT count(bbsid) FROM bbs";
		
		long tot = -1;
		try{
			con = ds.getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			if(rs.next()) {
				tot = rs.getLong(1);
			}
		} catch (Throwable e) {
			log.error(e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		}finally{
			close(rs);
			close(stmt);
			close(con);
		}
		return tot;
	}
	
	
	
	
	public long getRowCount(String column, String keyword)
	{
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		long tot = -1;
		try{
			con = ds.getConnection();
			if(column.equalsIgnoreCase("content")) {
				pstmt = con.prepareStatement( sql.getString("board.oracle.search.content.RowCount") );
				pstmt.setString(1, keyword);
			} else if( column.equalsIgnoreCase("userid") ) {
				pstmt = con.prepareStatement( sql.getString("board.oracle.search.userid.RowCount") );
				pstmt.setString(1, '%'+keyword+'%');
			}else {
				pstmt = con.prepareStatement( sql.getString("board.oracle.search.subject.RowCount") );
				pstmt.setString(1, '%'+keyword+'%');
			}
			
			rs = pstmt.executeQuery();
			if(rs.next()) {
				tot = rs.getLong(1);
			}
		} catch (Throwable e) {
			log.error(e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		}finally{
			close(rs);
			close(pstmt);
			close(con);
		}
		return tot;
	}
	
	
	
	
	
	public BoardDTO getContent(long bbsId)
	{
		Connection con = null;
		BoardDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try{
			con = ds.getConnection();
			pstmt = con.prepareStatement( sql.getString("board.oracle.Content") );
			pstmt.setLong(1, bbsId);
			rs = pstmt.executeQuery();
			if( rs.next()) {
				dto = new BoardDTO();
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
				dto.setContent( LobUtil.getClobData(rs, "CONTENT") );
				getReplyList(bbsId, dto);
			}
		} catch (Throwable e) {
			log.error(e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		}finally {
			close(rs);
			close(pstmt);
			close(con);
		}
		
		return dto;
	}
	
	
	/*
	public BoardDTO getContent(Connection con, long bbsId) throws DAOException
	{
		try {
			String resource = "han/juser/dao/ibatis/SqlMapConfig.xml";
			Reader reader= Resources.getResourceAsReader(resource);
			SqlMapClient sqlMap = SqlMapClientBuilder.buildSqlMapClient(reader);
			return (BoardDTO)sqlMap.queryForObject("selectBoardById", new Long(bbsId));
		} catch (Throwable e) {
			throw new DAOException(e.getMessage(), e);
		}
	}
	*/
	
	
	
	public int update(long contentId, String subject, String content, String link, String remoteAddr, 
			String originalFiles, String systemFiles)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		
		int irs = -1;
		int index = 1;
		
		try{
			con = ds.getConnection();
			con.setAutoCommit(false);
			pstmt = con.prepareStatement( sql.getString("board.oracle.Update") );
			pstmt.setString(index++, subject ); //1
			pstmt.setString(index++, link); //2
			pstmt.setString(index++, remoteAddr ); //3
			pstmt.setString(index++, systemFiles ); //4
			pstmt.setString(index++, originalFiles ); //5
			pstmt.setLong(index++, contentId); //6
			irs = pstmt.executeUpdate();
			if( irs != 1){
				con.rollback();
				return -1;
			}
			
			if( pstmt != null ) try{ pstmt.close(); } catch( SQLException e){}
			
			if( !updateClob( con, contentId, content ) ) {
				con.rollback();
				return -1;
			}
			
			con.commit();
		} catch (Throwable e) {
			log.error(e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		}finally{
			close(pstmt);
			close(con);
		}
		
		return irs;
	}
	
	
	
	
	public int delete(long bbsId)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try{
			int irs = -1;
			con = ds.getConnection();
			pstmt = con.prepareStatement( sql.getString("board.oracle.Delete") );
			pstmt.setLong(1, bbsId);
			irs = pstmt.executeUpdate();
			return irs;
		} catch (Throwable e) {
			log.error(e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		}finally {
			close(pstmt);
			close(con);
		}
	}
	
	
	
	private static synchronized void setReplyId(Connection con) throws DAOException
	{
		Statement stmt = null;
		ResultSet rs = null;
		try{
			stmt = con.createStatement();
			rs = stmt.executeQuery( sql.getString("board.oracle.ReplyCurrentId") );
			if(rs.next()) {
				replyId = rs.getLong(1);
			}
		} catch (Throwable e) {
			log.error(e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		}finally{
			if(rs != null) try { rs.close(); } catch(SQLException e) {}
			if(stmt != null) try{ stmt.close(); } catch(SQLException e) {}
		}
	}
	
	
	
	private static synchronized long getReplyNextId(Connection con) throws DAOException
	{
		if( replyId == -1) {
			setReplyId(con);
		}
		return ++replyId;
	}
	
	
	
	
	public int insertReply(long parentId, String userId, String remoteAddr, String reply)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		long maxId = 0;
		int irs = -1;
		
		try{
			con = ds.getConnection();
			maxId = getReplyNextId(con);
			pstmt = con.prepareStatement( sql.getString("board.oracle.InsertReply") );
			pstmt.setLong(1, ++maxId);
			pstmt.setLong(2, parentId );
			pstmt.setString(3, userId );
			pstmt.setString(4, reply );
			pstmt.setString(5, remoteAddr );
			irs = pstmt.executeUpdate();
		} catch (Throwable e) {
			log.error(e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		}finally{
			close(pstmt);
			close(con);
		}
		return irs;
		
	}
	
	
	
	
	private void getReplyList(long bbsId, BoardDTO dto) throws DAOException
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try{
			con = ds.getConnection();
			pstmt = con.prepareStatement(  sql.getString("board.oracle.ReplyList") );
			pstmt.setLong(1, bbsId);
			rs = pstmt.executeQuery();
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
		} catch (Throwable e) {
			log.error(e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		}finally{
			close(rs);
			close(pstmt);
			close(con);
		}
		
	}
	
	
	
	
	public BoardReplyDTO getReply(long replId)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		BoardReplyDTO dto = null;
		try{
			con = ds.getConnection();
			pstmt = con.prepareStatement(  sql.getString("board.oracle.Reply") );
			pstmt.setLong(1, replId);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				dto = new BoardReplyDTO();
				dto.setReplId( rs.getLong("REPLID") );
				dto.setBbsId( rs.getInt("BBSID") );
				dto.setUserId( rs.getString("USERID") );
				dto.setReply( rs.getString("REPLE") );
				dto.setLogdate( rs.getTimestamp("LOGDATE") );
				dto.setRemoteAddr( rs.getString("REMOTEADDR") );
			}
			return dto;
		} catch (Throwable e) {
			log.error(e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		}finally {
			close(rs);
			close(pstmt);
			close(con);
		}
	}
	
	
	
	
	public int deleteReply(long replId)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		int irs = -1;
		try{
			con = ds.getConnection();
			pstmt = con.prepareStatement(  sql.getString("board.oracle.DeleteReply") );
			pstmt.setLong(1, replId);
			irs = pstmt.executeUpdate();
			return irs;
		} catch (Throwable e) {
			log.error(e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		}finally{
			close(pstmt);
			close(con);
		}
	}
	
	
	
	
	public void updateHitCount(long bbsId)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		try{
			con = ds.getConnection();
			pstmt = con.prepareStatement(  sql.getString("board.oracle.UpdateHitCount") );
			pstmt.setLong(1, bbsId);
			pstmt.executeUpdate();
		} catch (Throwable e) {
			log.error(e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		}finally{
			close(pstmt);
			close(con);
		}
	}
	
	
	
	
	
	public void updateVoteCount(long bbsId)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		try{
			con = ds.getConnection();
			pstmt = con.prepareStatement(  sql.getString("board.oracle.UpdateVoteCount") );
			pstmt.setLong(1, bbsId);
			pstmt.executeUpdate();
		} catch (Throwable e) {
			log.error(e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		}finally{
			close(pstmt);
			close(con);
		}
	}


	
	public List getSummary(int limit) 
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List summary = new ArrayList();
		String sql = "SELECT /*+ index_desc(bbs pk_bbs_bbsid) */ rownum rn, bbsid, subject, content, logdate FROM bbs WHERE rownum <= ?";
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, limit);
			rs = pstmt.executeQuery();
			while (rs.next() ) {
				SummaryDTO dto = new SummaryDTO();
				dto.setContentId( rs.getLong("BBSID") );
				dto.setSubject( rs.getString("SUBJECT") );
				dto.setContent( LobUtil.getClobData(rs, "CONTENT") );
				dto.setSignDate( rs.getTimestamp("LOGDATE") );
				summary.add(dto);
			}
			return summary;
		} catch (Throwable e) {
			throw new DAOException(e.getMessage(), e);
		} finally {
			close(rs);
			close(pstmt);
			close(con);
		}
	}




	public int update(long contentId, String subject, String content, String link, String remoteAddr) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
}

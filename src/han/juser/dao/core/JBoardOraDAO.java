package han.juser.dao.core;

import han.juser.dao.DAOException;
import han.juser.dao.JBoardDAO;
import han.juser.db.oracle.LobUtil;
import han.juser.model.JBoardDTO;
import han.juser.model.SummaryDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


/**
 * 
 * @author Y.Han Lee
 *
 */
public class JBoardOraDAO extends OraDAOBase implements JBoardDAO
{
	
	private volatile static long id = -1;
	
	private static ResourceBundle sql;
	
	
	
	public JBoardOraDAO()
	{
		try {
			sql = ResourceBundle.getBundle("han.juser.dao.core.JBoardSQL");
		} catch(Throwable e) {
			log.fatal(e.getMessage(), e);
		}
	}
	

	
	public long getRowCount()
	{
		Connection con = null;
		ResultSet rs = null;
		Statement stmt = null;
		
		long tot = -1;
		try{
			con = ds.getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql.getString("jboard.oracle.all.RowCount"));
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
	
	
	
	public long getRowCount(long articleId)
	{
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		long tot = -1;
		try{
			con = ds.getConnection();
			pstmt = con.prepareStatement(sql.getString("jboard.oracle.child.RowCount"));
			pstmt.setLong(1, articleId);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				tot = rs.getLong(1);
			}
			return tot;
		} catch (Throwable e) {
			log.error(e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		}finally{
			close(rs);
			close(pstmt);
			close(con);
		}
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
				pstmt = con.prepareStatement(sql.getString("jboard.oracle.search.content.RowCount"));
				pstmt.setString(1, keyword);
			} else if ( column.equalsIgnoreCase("userid") ) {
				pstmt = con.prepareStatement(sql.getString("jboard.oracle.search.userid.RowCount"));
				pstmt.setString(1, '%'+keyword+'%');
			} else {
				pstmt = con.prepareStatement(sql.getString("jboard.oracle.search.subject.RowCount"));
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
	
	
	
	
	public void updateHit(long articleId)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		try{
			con = ds.getConnection();
			pstmt = con.prepareStatement(sql.getString("jboard.oracle.UpdateHit"));
			pstmt.setLong(1, articleId);
			pstmt.executeUpdate();
		} catch (Throwable e) {
			log.error(e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		}finally{
			close(pstmt);
			close(con);
		}
	}
	
	
	
	
	
	
	public void updateVote(long articleId)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		try{
			con = ds.getConnection();
			pstmt = con.prepareStatement(sql.getString("jboard.oracle.UpdateVote"));
			pstmt.setLong(1, articleId);
			pstmt.executeUpdate();
		} catch (Throwable e) {
			log.error(e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		}finally{
			close(pstmt);
			close(con);
		}
	}
	
	
	
	
	
	public List getContentList(long start, long end)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try{
			con = ds.getConnection();
			pstmt = con.prepareStatement(sql.getString("jboard.oracle.ContentList"));
			pstmt.setLong(1, end );
			pstmt.setLong(2, start );
			rs = pstmt.executeQuery();
			
			long rowNum = getRowCount() - start;
			return getArrayList(rs, rowNum);
		} catch (Throwable e) {
			log.error(e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		}finally{
			close(rs);
			close(pstmt);
			close(con);
		}
	}

	
	
	
	public List getChildList(long articleId)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try{
			con = ds.getConnection();
			pstmt = con.prepareStatement(sql.getString("jboard.oracle.ChildList"));
			pstmt.setLong(1, articleId );
			rs = pstmt.executeQuery();
			long rowNum = getRowCount(articleId);
			return getArrayList(rs, rowNum);
		} catch (Throwable e) {
			log.error(e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		}finally{
			close(rs);
			close(pstmt);
			close(con);
		}
	}
	
	
	public List getContentList(long start, long end, String column, String keyword)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try{
			con = ds.getConnection();
			if(column.equalsIgnoreCase("content")) {
				pstmt = con.prepareStatement( sql.getString("jboard.oracle.search.content.ContentList") );
				pstmt.setString(1, keyword);
			} else if ( column.equalsIgnoreCase("userid")) {
				pstmt = con.prepareStatement( sql.getString("jboard.oracle.search.userid.ContentList") );
				pstmt.setString(1, '%'+keyword+'%');
			}else {
				pstmt = con.prepareStatement( sql.getString("jboard.oracle.search.subject.ContentList") );
				pstmt.setString(1, '%'+keyword+'%');
			}
			pstmt.setLong(2, end );
			pstmt.setLong(3, start );
			rs = pstmt.executeQuery();
			
			long rowNum = getRowCount(column, keyword) - start;
			return getArrayList(rs, rowNum);
		} catch (Throwable e) {
			log.error(e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		}finally{
			close(rs);
			close(pstmt);
			close(con);
		}
	}
	
	
	
	
	private List getArrayList(ResultSet rs, long rowNum) throws SQLException
	{
		
		List list = new ArrayList();
		while(rs.next()) {
			JBoardDTO dto = new JBoardDTO();
			dto.setRownum( rowNum-- );
			dto.setArticleId( rs.getLong("ARTICLEID") );
			dto.setParentId( rs.getLong("PARENTID") );
			dto.setGroupId( rs.getLong("GROUPID") );
			dto.setLevel( rs.getInt("LEV") );
			dto.setUserId( rs.getString("USERID") );
			dto.setSubject( rs.getString("SUBJECT") );
			dto.setSignDate( rs.getTimestamp("SIGNDATE") );
			dto.setSystemFiles( rs.getString("SYSTEMFILES") );
			dto.setOriginalFiles( rs.getString("ORIGINALFILES") );
			dto.setHit( rs.getInt("HIT") );
			dto.setVote( rs.getInt("VOTE") );
			
			list.add(dto);
		}
		return list;
	}
	
	
	
	
	
	public JBoardDTO getContent(long articleId)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		JBoardDTO dto = null;
		
		try{
			con = ds.getConnection();
			pstmt = con.prepareStatement(sql.getString("jboard.oracle.Content"));
			pstmt.setLong(1, articleId);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				dto = new JBoardDTO();
				//dto.setRownum( rs.getLong("RN") );
				//dto.setLevel( rs.getInt("LEVEL") );
				dto.setArticleId( rs.getLong("ARTICLEID") );
				dto.setParentId( rs.getLong("PARENTID") );
				dto.setGroupId( rs.getLong("GROUPID") );
				dto.setUserId( rs.getString("USERID") );
				dto.setSystemFiles( rs.getString("SYSTEMFILES") );
				dto.setOriginalFiles( rs.getString("ORIGINALFILES") );
				dto.setLink( rs.getString("FLINK") );
				dto.setSubject( rs.getString("SUBJECT") );
				dto.setSignDate( rs.getTimestamp("SIGNDATE") );
				dto.setUpdated( rs.getTimestamp("UPDATED") );
				dto.setHit( rs.getInt("HIT") );
				dto.setVote( rs.getInt("VOTE") );
				dto.setRemoteAddr( rs.getString("REMOTEADDR") );
				dto.setContent( LobUtil.getClobData(rs, "CONTENT") );
			}
		} catch (Throwable e) {
			log.error(e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		} finally {
			close(rs);
			close(pstmt);
			close(con);
		}
		
		return dto;
	}
	
	
	private static synchronized void setId(Connection con) throws DAOException
	{
		Statement stmt = null;
		ResultSet rs = null;
		try{
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql.getString("jboard.oracle.MaxId"));
			if(rs.next()) {
				id = rs.getLong(1);
			}
		} catch (Throwable e) {
			log.error(e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		}finally {
			if(rs != null ) try { rs.close(); } catch(SQLException e) {}
			if(stmt != null ) try { stmt.close(); } catch(SQLException e) {}
		}
	}
	
	
	private static synchronized long getNextId(Connection con)
	{
		if(id == -1) {
			setId(con);
		}
		return ++id;
	}
	
	
	
	/*
	public int insert(JBoardDTO dto)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		
		int index = 1;
		int irs = -1;
		try{
			con = ds.getConnection();
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(sql.getString("jboard.oracle.Insert") );
			pstmt.setLong(index++, dto.getArticleId() );
			pstmt.setLong(index++, dto.getParentId() );
			pstmt.setLong(index++, dto.getGroupId() );
			pstmt.setString(index++, dto.getUserId() );
			pstmt.setString(index++, dto.getSystemFiles() );
			pstmt.setString(index++, dto.getOriginalFiles() );
			pstmt.setString(index++, dto.getLink() );
			pstmt.setString(index++, dto.getSubject() );
			pstmt.setString(index, dto.getRemoteAddr() );
			irs = pstmt.executeUpdate();
			if( irs != 1){
				con.rollback();
				return -1;
			}
			close(pstmt);
			
			if( !updateClob(con, dto.getArticleId(), dto.getContent()) ) {
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
	*/
	
	
	
	public long insert(JBoardDTO dto)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		int index = 1;
		int irs = -1;
		long newId = -1;
		try{
			con = ds.getConnection();
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(sql.getString("jboard.oracle.Content"));
			pstmt.setLong(1, dto.getParentId());
			rs = pstmt.executeQuery();
			
			
			newId = getNextId(con);
			//부모글이 존재하면 자식글로 존재하지 않으면 최상위 글로 인서트
			if(rs.next()) {
				pstmt.close();
				pstmt = con.prepareStatement(sql.getString("jboard.oracle.InsertChild") );
				pstmt.setLong(index++, newId );
				pstmt.setLong(index++, dto.getParentId() );
				pstmt.setLong(index++, dto.getParentId() );
				pstmt.setString(index++, dto.getUserId() );
				pstmt.setString(index++, dto.getSystemFiles() );
				pstmt.setString(index++, dto.getOriginalFiles() );
				pstmt.setString(index++, dto.getLink() );
				pstmt.setString(index++, dto.getSubject() );
				pstmt.setString(index, dto.getRemoteAddr() );
				irs = pstmt.executeUpdate();
			} else {
				pstmt.close();
				pstmt = con.prepareStatement(sql.getString("jboard.oracle.InsertRoot") );
				//pstmt.setLong(index++, dto.getArticleId() );
				pstmt.setLong(index++, newId );
				pstmt.setLong(index++, newId );
				pstmt.setString(index++, dto.getUserId() );
				pstmt.setString(index++, dto.getSystemFiles() );
				pstmt.setString(index++, dto.getOriginalFiles() );
				pstmt.setString(index++, dto.getLink() );
				pstmt.setString(index++, dto.getSubject() );
				pstmt.setString(index, dto.getRemoteAddr() );
				irs = pstmt.executeUpdate();
			}
			if( irs != 1){
				con.rollback();
				return -1;
			}
			close(pstmt);
			
			if( !updateClob(con, newId, dto.getContent()) ) {
				con.rollback();
				return -1;
			}
			con.commit();
		} catch (Throwable e) {
			log.error(e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		}finally{
			close(rs);
			close(pstmt);
			close(con);
		}
		
		return newId;
	}
	
	
	
	public int update(JBoardDTO dto)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		
		int index = 1;
		int irs = -1;
		try{
			con = ds.getConnection();
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(sql.getString("jboard.oracle.Update"));
			pstmt.setString(index++, dto.getSystemFiles() );
			pstmt.setString(index++, dto.getOriginalFiles() );
			pstmt.setString(index++, dto.getLink() );
			pstmt.setString(index++, dto.getSubject() );
			pstmt.setString(index++, dto.getRemoteAddr() );
			pstmt.setLong(index++, dto.getArticleId() );
			pstmt.setString(index++, dto.getUserId() );
			irs = pstmt.executeUpdate();
			if( irs != 1){
				con.rollback();
				return -1;
			}
			close(pstmt);
			
			if( !updateClob(con, dto.getArticleId(), dto.getContent()) ) {
				con.rollback();
				return -1;
			}
			con.commit();
		} catch (Throwable e) {
			log.error(e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		}finally {
			close(pstmt);
			close(con);
		}
		
		return irs;
	}

	
	public int deleteAll(long articleId)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		int irs = -1;
		
		try{
			con = ds.getConnection();
			pstmt = con.prepareStatement(sql.getString("jboard.oracle.DeleteAll"));
			pstmt.setLong(1, articleId);
			irs = pstmt.executeUpdate();
		} catch (Throwable e) {
			log.error(e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		} finally {
			close(pstmt);
			close(con);
		}
		return irs;
	}
	
	
	
	public int deleteChild(long parentId)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		int irs = -1;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(sql.getString("jboard.oracle.DeleteChild"));
			pstmt.setLong(1, parentId );
			irs = pstmt.executeUpdate();
		} catch (Throwable e) {
			log.error(e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		}finally {
			close(pstmt);
			close(con);
		}
		return irs;
	}
	
	
	
	
	
	public int unSetFileNames(long articleId)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		int irs = -1;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(sql.getString("jboard.oracle.DeleteFile"));
			pstmt.setLong(1, articleId);
			irs = pstmt.executeUpdate();
		} catch (Throwable e) {
			log.error(e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		}finally {
			close(pstmt);
			close(con);
		}
		return irs;
	}
	
	
	
	private boolean updateClob(Connection con, long bbsId, String data)
	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try{
			pstmt = con.prepareStatement(sql.getString("jboard.oracle.UpdateClob"));
			pstmt.setLong(1, bbsId);
			rs = pstmt.executeQuery();
			
			if( rs.next() ) {
				return LobUtil.setClobDate(rs, "CONTENT", data);
			}else {
				return false;
			}
		}catch(Throwable e) {
			log.error(e.getMessage(), e);
			return false;
		}finally {
			close(rs);
			close(pstmt);
		}
	}




	public List getSummary(int limit) 
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List summary = new ArrayList();
		String sql = "SELECT /*+ index_desc(jboard ind_jboard_groupid) */ rownum rn, articleid, subject, content, signdate FROM jboard WHERE parentid = 0 AND rownum <= ?";
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, limit);
			rs = pstmt.executeQuery();
			while (rs.next() ) {
				SummaryDTO dto = new SummaryDTO();
				dto.setContentId( rs.getLong("ARTICLEID") );
				dto.setSubject( rs.getString("SUBJECT") );
				dto.setContent(  LobUtil.getClobData(rs, "CONTENT") );
				dto.setSignDate( rs.getTimestamp("SIGNDATE") );
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
	
}

package han.juser.dao.core;

import han.juser.dao.BlogDAO;
import han.juser.dao.DAOException;
import han.juser.db.oracle.LobUtil;
import han.juser.model.BlogDTO;
import han.juser.model.BlogReplyDTO;
import han.juser.model.SummaryDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class BlogOraDAO extends CategoryOraDAO implements BlogDAO
{
	private volatile static long id = -1;
	
	private volatile static long rid = -1;
	
	
	private static ResourceBundle sql;
	
	
	public BlogOraDAO()
	{
		try {
			sql = ResourceBundle.getBundle("han.juser.dao.core.BlogSQL");
		} catch( Throwable e) {
			log.error(e.getMessage(), e);
			throw new MissingResourceException(e.getMessage(), BlogOraDAO.class.getName(), "han.juser.dao.core.BlogSQL");
		}
	}

	
	
	
	private List getArrayList(ResultSet rs, long rowNum) throws SQLException
	{
		List list = new ArrayList();
		while(rs.next()) {
			BlogDTO dto = new BlogDTO();
			dto.setRowNum( rowNum-- );
			dto.setArticleId( rs.getLong("ARTICLEID") );
			dto.setCateId( rs.getInt("CATEID") );
			dto.setUserId( rs.getString("USERID") );
			dto.setSubject( rs.getString("SUBJECT") );
			dto.setSystemFiles( rs.getString("SYSTEMFILES") );
			dto.setOriginalFiles( rs.getString("ORIGINALFILES") );
			dto.setSignDate( rs.getTimestamp("SIGNDATE") );
			dto.setUpdated( rs.getTimestamp("UPDATED") );
			dto.setRemoteAddr( rs.getString("REMOTEADDR") );
			dto.setReplyCount( rs.getInt("REPLYCOUNT") );
			//dto.setContent( getClobData(rs, "CONTENT"));
			list.add(dto);
		}
		
		return list;
	}
	
	
	private static synchronized void setId(Connection con) throws SQLException
	{
		Statement stmt = null;
		ResultSet rs = null;
		
		long irs = -1;
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql.getString("blog.oracle.MaxId"));
			if(rs.next()) {
				irs = rs.getLong(1);
			}
			id = irs;
		} finally {
			if(rs != null) try { rs.close(); } catch (SQLException e) {}
			if(stmt != null) try { stmt.close(); } catch(SQLException e){}
		}
	}
	
	
	
	private static synchronized long getNextId(Connection con) throws SQLException
	{
		if( id == -1 ) {
			setId(con);
		}
		return ++id;
	}
	
	
	
	
	private static synchronized void setReplyId(Connection con) throws SQLException
	{
		Statement stmt = null;
		ResultSet rs = null;
		
		long irs = -1;
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql.getString("blog.oracle.reply.MaxId"));
			if(rs.next()) {
				irs = rs.getLong(1);
			}
			rid = irs;
		} finally {
			if(rs != null) try { rs.close(); } catch (SQLException e) {}
			if(stmt != null) try { stmt.close(); } catch(SQLException e){}
		}
	}
	
	
	private static synchronized long getReplyNextId(Connection con) throws SQLException
	{
		if( rid == -1) {
			setReplyId(con);
		}
		return ++rid;
	}
	
	
	
	public BlogDTO getContent(long articleId) 
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		BlogDTO dto = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(sql.getString("blog.oracle.Content"));
			pstmt.setLong(1, articleId);
			rs = pstmt.executeQuery();
			if( rs.next()) {
				dto = new BlogDTO();
				dto.setArticleId( rs.getLong("ARTICLEID") );
				dto.setCateId( rs.getInt("CATEID") );
				dto.setSystemFiles( rs.getString("SYSTEMFILES") );
				dto.setOriginalFiles( rs.getString("ORIGINALFILES") );
				dto.setSubject( rs.getString("SUBJECT") );
				dto.setUserId( rs.getString("USERID") );
				dto.setSignDate( rs.getTimestamp("SIGNDATE") );
				dto.setUpdated( rs.getTimestamp("UPDATED") );
				dto.setContent( LobUtil.getClobData(rs, "CONTENT") );
			}
		} catch (Throwable e) {
			log.error(e.getMessage(), e);
			throw new DAOException(e.getMessage());
		} finally {
			close(rs);
			close(pstmt);
			close(con);
		}
		
		return dto;
	}
	
	
	
	public long getRowCount()
	{
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		long irs = -1;
		try {
			con = ds.getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql.getString("blog.oracle.all.RowCount"));
			if(rs.next()) {
				irs = rs.getLong(1);
			}
		} catch (Throwable e) {
			log.error(e.getMessage(), e);
			throw new DAOException(e.getMessage());
		} finally {
			close(rs);
			close(stmt);
			close(con);
		}
		return irs;
	}
	
	
	
	public List getContentList(long start, int limit)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(sql.getString("blog.oracle.all.ContentList"));
			pstmt.setLong(1, start);
			pstmt.setInt(2, limit+1);
			rs = pstmt.executeQuery();
			long rowNum = getRowCount() - --start;
			return getArrayList(rs, rowNum);
		} catch (Throwable e) {
			log.error(e.getMessage(), e);
			throw new DAOException(e.getMessage());
		} finally {
			close(rs);
			close(pstmt);
			close(con);
		}
		
	}
	
	
	
	public long getRowCount(int cateId)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		long irs = -1;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(sql.getString("blog.oracle.cate.RowCount"));
			pstmt.setInt(1, cateId);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				irs = rs.getLong(1);
			}
		} catch (Throwable e) {
			log.error(e.getMessage(), e);
			throw new DAOException(e.getMessage());
		} finally {
			close(rs);
			close(pstmt);
			close(con);
		}
		
		return irs;
	}
	
	
	
	
	public List getContentList(int cateId, long start, int limit)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(sql.getString("blog.oracle.cate.ContentList"));
			pstmt.setInt(1, cateId);
			pstmt.setInt(2, cateId);
			pstmt.setLong(3, start);
			pstmt.setInt(4, limit+1);
			rs = pstmt.executeQuery();
			long rowNum = getRowCount(cateId) - --start;
			return getArrayList(rs, rowNum);
		} catch (Throwable e) {
			log.error(e.getMessage(), e);
			throw new DAOException(e.getMessage());
		} finally {
			close(rs);
			close(pstmt);
			close(con);
		}
	}
	
	
	
	
	public long getRowCount(String column, String keyword) 
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		long irs = -1;
		try {
			con = ds.getConnection();
			if (column.equalsIgnoreCase("content")) {
				pstmt = con.prepareStatement(sql.getString("blog.oracle.search.content.RowCount"));
				pstmt.setString(1, keyword);
			} else if ( column.equalsIgnoreCase("userid")) {
				pstmt = con.prepareStatement(sql.getString("blog.oracle.search.userid.RowCount"));
				pstmt.setString(1, '%' + keyword + '%');
			} else {
				pstmt = con.prepareStatement(sql.getString("blog.oracle.search.subject.RowCount"));
				pstmt.setString(1, '%' + keyword + '%');
			}
			rs = pstmt.executeQuery();
			if (rs.next()) {
				irs = rs.getLong(1);
			}
		} catch (Throwable e) {
			log.error(e.getMessage(), e);
			throw new DAOException(e.getMessage());
		} finally {
			close(rs);
			close(pstmt);
			close(con);
		}

		return irs;
	}

	
	
	public List getContentList(long start, int limit, String column, String keyword)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;		
		try {
			con = ds.getConnection();
			if( column.equalsIgnoreCase("content") ) { 
				pstmt = con.prepareStatement( sql.getString("blog.oracle.search.content.ContentList") );
				pstmt.setString(1, keyword);
				pstmt.setString(2, keyword);
			} else if ( column.equalsIgnoreCase("userid")) {
				pstmt = con.prepareStatement( sql.getString("blog.oracle.search.userid.ContentList") );
				pstmt.setString(1, '%' + keyword + '%');
				pstmt.setString(2, '%' + keyword + '%');
			}else {
				pstmt = con.prepareStatement( sql.getString("blog.oracle.search.subject.ContentList") );
				pstmt.setString(1, '%' + keyword + '%');
				pstmt.setString(2, '%' + keyword + '%');
			}
			pstmt.setLong(3, start);
			pstmt.setInt(4, limit+1);
			rs = pstmt.executeQuery();
			long rowNum = getRowCount(column, keyword) - --start;
			return getArrayList(rs, rowNum);
		} catch (Throwable e) {
			log.error(e.getMessage(), e);
			throw new DAOException(e.getMessage());
		} finally {
			close(rs);
			close(pstmt);
			close(con);
		}
	}
	
	
	
	public long getRowCount(int cateId, String column, String keyword)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		long irs = -1;
		
		try {
			con = ds.getConnection();
			if(column.equalsIgnoreCase("content")) {
				pstmt = con.prepareStatement(sql.getString("blog.oracle.search.cate.content.RowCount"));
				pstmt.setString(2, keyword);
			} else if (column.equalsIgnoreCase("userid")) {
				pstmt = con.prepareStatement(sql.getString("blog.oracle.search.cate.userid.RowCount"));
				pstmt.setString(2, '%'+keyword+'%');
			}else {
				pstmt = con.prepareStatement(sql.getString("blog.oracle.search.cate.subject.RowCount"));
				pstmt.setString(2, '%'+keyword+'%');
			}
			pstmt.setInt(1, cateId);
			
			rs = pstmt.executeQuery();
			if(rs.next()) {
				irs = rs.getLong(1);
			}
		} catch (Throwable e) {
			log.error(e.getMessage(), e);
			throw new DAOException(e.getMessage());
		} finally {
			close(rs);
			close(pstmt);
			close(con);
		}
		
		return irs;
	}
	
	
	
	public List getContentList(int cateId, long start,
			int limit, String column, String keyword)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;		
		try {
			con = ds.getConnection();
			if(column.equalsIgnoreCase("content")) {
				pstmt = con.prepareStatement(sql.getString("blog.oracle.search.cate.content.ContentList"));
				pstmt.setString(1, keyword);
				pstmt.setString(3, keyword);
			} else if ( column.equalsIgnoreCase("userid")) {
				pstmt = con.prepareStatement(sql.getString("blog.oracle.search.cate.userid.ContentList"));
				pstmt.setString(1, '%'+keyword+'%');
				pstmt.setString(3, '%'+keyword+'%');
			}else {
				pstmt = con.prepareStatement(sql.getString("blog.oracle.search.cate.subject.ContentList"));
				pstmt.setString(1, '%'+keyword+'%');
				pstmt.setString(3, '%'+keyword+'%');
			}
			pstmt.setInt(2, cateId);
			pstmt.setInt(4, cateId);
			pstmt.setLong(5, start);
			pstmt.setInt(6, limit+1);
			rs = pstmt.executeQuery();
			long rowNum = getRowCount(cateId, column, keyword) - --start;
			return getArrayList(rs, rowNum);
		} catch (Throwable e) {
			log.error(e.getMessage(), e);
			throw new DAOException(e.getMessage());
		} finally {
			close(rs);
			close(pstmt);
			close(con);
		}
	}
	
	
	
	
	
	
	
	

	private boolean updateClob(Connection con, long articleId, String data)
	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try{
			pstmt = con.prepareStatement(sql.getString("blog.oracle.UpdateClob"));
			pstmt.setLong(1, articleId);
			rs = pstmt.executeQuery();
			
			if( rs.next() ) {
				return LobUtil.setClobDate(rs, "CONTENT", data);
			}else {
				return false;
			}
		}catch(SQLException e) {
			if(log.isDebugEnabled()) {
				log.debug(e.getMessage(), e);
			}
			return false;
		}finally {
			close(rs);
			close(pstmt);
		}
	}
	
	
	
	
	public long insertContent(BlogDTO dto)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		
		long irs = -1;
		int index = 1;
		try {
			con = ds.getConnection();
			long articleId = getNextId(con);
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(sql.getString("blog.oracle.Insert"));
			pstmt.setLong( index++ , ++articleId ); //1
			pstmt.setInt(index++, dto.getCateId() ); //2
			pstmt.setString( index++ , dto.getUserId() ); //3
			pstmt.setString(index++ , dto.getSystemFiles() ); //4
			pstmt.setString(index++, dto.getOriginalFiles() ); //5
			pstmt.setString( index++ , dto.getSubject() ); //7
			pstmt.setString( index++, dto.getRemoteAddr() ); //8
			irs = pstmt.executeUpdate();
			//if(irs != 1) {
			//	con.rollback();
			//	return -1;
			//}
			//close(pstmt);
			
			// lob데이터 인서트
			if( !updateClob(con, articleId , dto.getContent()) ) {
				con.rollback();
				return -1;
			}
			
			// 커밋
			con.commit();
		} catch (Throwable e) {
			log.error(e.getMessage(), e);
			throw new DAOException(e.getMessage());
		} finally {
			close(pstmt);
			close(con);
		}
		
		return irs;
	}
	
	

	public int updateContent(BlogDTO dto)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		
		int irs = -1;
		int index = 1;
		try {
			con = ds.getConnection();
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(sql.getString("blog.oracle.Update"));
			pstmt.setInt(index++, dto.getCateId() ); //1
			pstmt.setString(index++, dto.getSystemFiles() ); //2
			pstmt.setString(index++, dto.getOriginalFiles() ); //3
			pstmt.setString(index++, dto.getSubject() ); //5
			pstmt.setString(index++, dto.getRemoteAddr() ); //6
			pstmt.setLong( index++, dto.getArticleId() ); //7
			irs = pstmt.executeUpdate();
			if(irs != 1) {
				con.rollback();
				return -1;
			}
			close(pstmt);
			
			if( !updateClob(con, dto.getArticleId(), dto.getContent() )) {
				con.rollback();
				return -1;
			}
			con.commit();
		} catch (Throwable e) {
			log.error(e.getMessage(), e);
			throw new DAOException(e.getMessage());
		} finally {
			close(pstmt);
			close(con);
		}
		
		return irs;
	}
	
	
	
	
	
	public int deleteContent(long articleId)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		int irs = -1;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(sql.getString("blog.oracle.Delete"));
			pstmt.setLong(1, articleId);
			irs = pstmt.executeUpdate();
		} catch (Throwable e) {
			log.error(e.getMessage(), e);
			throw new DAOException(e.getMessage());
		} finally {
			close(pstmt);
			close(con);
		}
		
		return irs;
	}

	
	
	
	
	
	
	
	
	
	public int deleteReply(long replyId)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		int irs = -1;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(sql.getString("blog.oracle.reply.Delete"));
			pstmt.setLong(1, replyId);
			irs = pstmt.executeUpdate();
		} catch (Throwable e) {
			log.error(e.getMessage(), e);
			throw new DAOException(e.getMessage());
		} finally {
			close(pstmt);
			close(con);
		}
		
		return irs;
	}
	
	
	
	public BlogReplyDTO getReply(long replyId)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		BlogReplyDTO dto = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(sql.getString("blog.oracle.reply.Content"));
			pstmt.setLong(1, replyId);
			rs = pstmt.executeQuery();
			if( rs.next() ) {
				dto = new BlogReplyDTO();
				dto.setArticleId( rs.getLong("ARTICLEID") );
				dto.setContent( rs.getString("CONTENT") );
				dto.setRemoteAddr( rs.getString("REMOTEADDR") );
				dto.setReplyId( rs.getLong("REPLYID") );
				dto.setSignDate( rs.getTimestamp("SIGNDATE") );
				dto.setUserId( rs.getString("USERID") );
			}
			return dto;
		} catch (Throwable e) {
			log.error(e.getMessage(), e);
			throw new DAOException(e.getMessage());
		} finally {
			close(rs);
			close(pstmt);
			close(con);
		}
	}
	


	public int insertReply(BlogReplyDTO reply)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(sql.getString("blog.oracle.reply.Insert"));
			pstmt.setLong(1, getReplyNextId(con) ); //replyid
			pstmt.setLong(2, reply.getArticleId() ); //articleid
			pstmt.setString(3, reply.getUserId() ); //userid
			pstmt.setString(4, reply.getContent() ); //content
			pstmt.setString(5, reply.getRemoteAddr() ); //remoteaddr
			return pstmt.executeUpdate();
		} catch (Throwable e) {
			log.error(e.getMessage(), e);
			throw new DAOException(e.getMessage());
		} finally {
			close(pstmt);
			close(con);
		}
	}
	
	
	
	
	public int unSetFileNames(long articleId)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(sql.getString("blog.oracle.DeleteFile"));
			pstmt.setLong(1, articleId);
			return pstmt.executeUpdate();
		} catch (Throwable e) {
			log.error(e.getMessage(), e);
			throw new DAOException(e.getMessage());
		} finally {
			close(pstmt);
			close(con);
		}
	}

	
	
	
	public List getReplyList(long articleId)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List list = new ArrayList();
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(sql.getString("blog.oracle.reply.ReplyList"));
			pstmt.setLong(1, articleId);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				BlogReplyDTO dto = new BlogReplyDTO();
				dto.setReplyId( rs.getLong("REPLYID") );
				dto.setArticleId( rs.getLong("ARTICLEID") );
				dto.setUserId( rs.getString("USERID") );
				dto.setContent( rs.getString("CONTENT") );
				dto.setRemoteAddr( rs.getString("REMOTEADDR") );
				dto.setSignDate( rs.getTimestamp("SIGNDATE") );
				list.add(dto);
			}
			return list;
		} catch (Throwable e) {
			log.error(e.getMessage(), e);
			throw new DAOException(e.getMessage());
		} finally {
			close(rs);
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
		String sql = "SELECT /*+ index_desc(blog pk_blog_articleid) */ rownum rn, articleid, subject, content, signdate FROM blog WHERE rownum <= ?";
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, limit);
			rs = pstmt.executeQuery();
			while (rs.next() ) {
				SummaryDTO dto = new SummaryDTO();
				dto.setContentId( rs.getLong("ARTICLEID") );
				dto.setSubject( rs.getString("SUBJECT") );
				dto.setContent( LobUtil.getClobData(rs, "CONTENT")  );
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

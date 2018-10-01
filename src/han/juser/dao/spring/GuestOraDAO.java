package han.juser.dao.spring;

import han.juser.dao.DAOException;
import han.juser.dao.GuestDAO;
import han.juser.model.GuestDTO;
import han.juser.model.SummaryDTO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

/*
import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;
*/

public class GuestOraDAO extends SpringDAOSupport implements GuestDAO
{
	
	protected static ResourceBundle sql;
	
	private static volatile long id = -1;
	
	
	public void setSqlResource(String arg)
	{
		try {
			sql = ResourceBundle.getBundle(arg);
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	
    protected class GuestRowMapper implements RowMapper
	{
    	public Object mapRow(ResultSet rs, int rowNum) throws SQLException
    	{
			GuestDTO gbo = new GuestDTO();
			gbo.setGid( rs.getLong("GID") ); //1
			gbo.setUserId( rs.getString("USERID") ); //2
			gbo.setContent( rs.getString("CONTENT") ); //3
			gbo.setLogDate( rs.getTimestamp("LOGDATE") ); //4
			gbo.setRemoteAddr( rs.getString("REMOTEADDR") ); //5
    		return gbo;
    	}
	}
	
    
    protected class GuestResultSetExtractor implements ResultSetExtractor
    {
    	public Object extractData(ResultSet rs) throws SQLException
    	{
    		List list = new ArrayList();
    		while(rs.next()) {
				GuestDTO gbo = new GuestDTO();
				gbo.setGid( rs.getLong("GID") ); //1
				gbo.setUserId( rs.getString("USERID") ); //2
				gbo.setContent( rs.getString("CONTENT") ); //3
				gbo.setLogDate( rs.getTimestamp("LOGDATE") ); //4
				gbo.setRemoteAddr( rs.getString("REMOTEADDR") ); //5
				list.add(gbo);
			}
    		return list;
    	}
    }
    
    
    protected class GuestPreparedStatementSetter implements PreparedStatementSetter
    {
    	
    	private long articleId;
    	private String userId;
    	private String content;
    	private String remoteAddr;
    	
    	public GuestPreparedStatementSetter(long articleId, String userId, String content, String remoteAddr)
    	{
    			this.articleId = articleId;
    			this.userId = userId;
    			this.content = content;
    			this.remoteAddr = remoteAddr;
    	}
    	
    	
    	public void setValues(PreparedStatement pstmt) throws SQLException
    	{
			pstmt.setLong(1, articleId);
			pstmt.setString(2, userId );
			pstmt.setString(3, content );
			pstmt.setString(4, remoteAddr );
    	}
    	
    }
    
	
	private static synchronized long getNextId(JdbcTemplate jt) throws DAOException
	{
		if( id == -1) {
			id = jt.queryForLong(sql.getString("guest.oracle.MaxId"));
		}
		return ++id;
	}
	
	
	public int delete(long gid) 
	{
		return getJdbcTemplate().update(sql.getString("guest.oracle.Delete"), new Object[]{new Long(gid)});
	}

	public GuestDTO getContent(long gid) 
	{
		return (GuestDTO)getJdbcTemplate().queryForObject(sql.getString("guest.oracle.Content"), new Object[]{new Long(gid)}, new GuestRowMapper());
	}

	public List getContentList(long start, int limit) 
	{
		/*
		List rs = null;
		try {
			String resource = "han/juser/dao/ibatis/SqlMapConfig.xml";
			Reader reader= Resources.getResourceAsReader(resource);
			SqlMapClient sqlMap = SqlMapClientBuilder.buildSqlMapClient(reader);
			HashMap param = new HashMap();
			param.put("start", new Long(start));
			param.put("limit", new Integer(limit));
			return sqlMap.queryForList("guest.oracle.ContentList", param);
		} catch (Throwable e) {
			throw new DAOException(e.getMessage());
		}
		*/
		return (List)getJdbcTemplate().query(sql.getString("guest.oracle.ContentList"), 
				new Object[]{new Long(start), new Integer(limit)},
				new GuestResultSetExtractor());
		
	}

	public List getContentList(String userId, long start, int limit) 
	{
		return (List)getJdbcTemplate().query(sql.getString("guest.oracle.user.ContentList"), 
				new Object[]{userId, userId, new Long(start), new Integer(limit)},
				new GuestResultSetExtractor());
	}

	public long getRowCount() 
	{
		return getJdbcTemplate().queryForLong(sql.getString("guest.oracle.RowCount"));
	}

	public long getRowCount(String userId) 
	{
		return getJdbcTemplate().queryForLong(sql.getString("guest.oracle.user.RowCount"), new Object[]{userId});
	}

	public long insert(String userId, String content, String remoteAddr) 
	{
		JdbcTemplate jt = getJdbcTemplate();
		Long nextid = new Long(getNextId(jt));
		return jt.update(sql.getString("guest.oracle.Insert"), new GuestPreparedStatementSetter(nextid, userId, content, remoteAddr ) );
	}

	public int update(final long gid, final String content, final String remoteAddr) 
	{
		return getJdbcTemplate().update(sql.getString("guest.oracle.Update"), 
				new PreparedStatementSetter(){
			public void setValues(PreparedStatement pstmt) throws SQLException
			{
				pstmt.setString(1, content );
				pstmt.setString(2, remoteAddr );
				pstmt.setLong(3, gid );
			}
		});
	}

	
	public List getSummary(int limit) 
	{
		String sql = "SELECT /*+ index_desc(guest pk_guest_gid)*/ rownum rn, gid, content, logdate FROM guest WHERE rownum <= ?";
		return (List)getJdbcTemplate().query(sql, new Object[]{new Integer(limit)}, 
				new ResultSetExtractor(){
			public Object extractData(ResultSet rs) throws SQLException
			{
				List summary = new ArrayList();
				while (rs.next() ) {
					SummaryDTO dto = new SummaryDTO();
					dto.setContentId( rs.getLong("GID") );
					dto.setContent( rs.getString("CONTENT") );
					dto.setSignDate( rs.getTimestamp("LOGDATE") );
					summary.add(dto);
				}
				return summary;
			}
		});
	}

}

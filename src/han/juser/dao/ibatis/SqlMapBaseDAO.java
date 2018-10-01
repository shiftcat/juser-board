package han.juser.dao.ibatis;

import han.juser.dao.BaseDAO;
import han.util.ParameterMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

public class SqlMapBaseDAO extends SqlMapClientDaoSupport implements BaseDAO
{
	private String namespace;

	protected final Log log = LogFactory.getLog(getClass());

	public void setNamespace(String namespace) 
	{
		this.namespace = namespace;
	}

	public Object create(Object parameterObject) 
	{
		String fullClassName = parameterObject.getClass().getName();
		String objectName;
		int sep = fullClassName.lastIndexOf(".");
		if (sep > 0) {
			objectName = fullClassName.substring(sep + 1, fullClassName.length());
		} else {
			objectName = fullClassName;
		}
		String statementName = namespace + ".create" + objectName;
		log.debug("entering '" + statementName + "' method...");
		return getSqlMapClientTemplate().insert(statementName, parameterObject);
	}

	public Object read(ParameterMap parameterMap) 
	{
		String statementName = namespace + ".read" + parameterMap.getObjectName();
		log.debug("entering '" + statementName + "' method...");
		return getSqlMapClientTemplate().queryForObject(statementName, parameterMap);
	}

	public int update(Object parameterObject) 
	{
		String fullClassName = parameterObject.getClass().getName();
		String objectName;
		int sep = fullClassName.lastIndexOf(".");
		if (sep > 0) {
			objectName = fullClassName.substring(sep + 1, fullClassName.length());
		} else {
			objectName = fullClassName;
		}
		String statementName = namespace + ".update" + objectName;
		log.debug("entering '" + statementName + "' method...");
		return getSqlMapClientTemplate().update(statementName, parameterObject);
	}

	public int delete(ParameterMap parameterMap) 
	{
		String statementName = namespace + ".delete" + parameterMap.getObjectName();
		log.debug("entering '" + statementName + "' method...");
		return getSqlMapClientTemplate().delete(statementName, parameterMap);
	}
}

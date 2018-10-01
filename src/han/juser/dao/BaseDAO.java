package han.juser.dao;

import han.util.ParameterMap;

public interface BaseDAO 
{
	  
	/**
	 * INSERT ������ �����Ͽ� ���ڵ带 �����Ѵ�.
	 * @param parameterObject ������ ��ü
	 * @return Primary Key�� ������ ��ü
	 */
	Object create(Object parameterObject);

	/**
	 * SELECT ������ �����Ͽ� ���ڵ带 ��ȸ�Ѵ�.
	 * @param parameterMap Primary Key�� ������ �ؽ���
	 * @return ��ȸ ��ü
	 */
	Object read(ParameterMap parameterMap);

	/**
	 * UPDATE ������ �����Ͽ� ���ڵ带 �����Ѵ�.
	 * @param parameterObject ������ ��ü
	 * @return ���ŵ� ROW ��
	 */
	int update(Object parameterObject);

	/**
	 * DELETE ������ �����Ͽ� ���ڵ带 �����Ѵ�.
	 * @param parameterMap Primary Key�� ������ �ؽ���
	 * @return ������ ROW ��
	 */
	int delete(ParameterMap parameterMap);

}

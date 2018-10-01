package han.juser.dao;

import han.util.ParameterMap;

public interface BaseDAO 
{
	  
	/**
	 * INSERT 문장을 실행하여 레코드를 저장한다.
	 * @param parameterObject 저장할 객체
	 * @return Primary Key로 구성된 객체
	 */
	Object create(Object parameterObject);

	/**
	 * SELECT 문장을 실행하여 레코드를 조회한다.
	 * @param parameterMap Primary Key로 구성된 해쉬맵
	 * @return 조회 객체
	 */
	Object read(ParameterMap parameterMap);

	/**
	 * UPDATE 문장을 실행하여 레코드를 갱신한다.
	 * @param parameterObject 갱신할 객체
	 * @return 갱신된 ROW 수
	 */
	int update(Object parameterObject);

	/**
	 * DELETE 문장을 실행하여 레코드를 삭제한다.
	 * @param parameterMap Primary Key로 구성된 해쉬맵
	 * @return 삭제된 ROW 수
	 */
	int delete(ParameterMap parameterMap);

}

package han.juser.dao.ibatis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import han.juser.dao.MemberDAO;
import han.juser.model.MemberDTO;

public class MemberOraDAO extends SqlMapBaseDAO implements MemberDAO
{

	public MemberDTO getMember(String userId) 
	{
		return (MemberDTO)getSqlMapClientTemplate().queryForObject("Member.getMember", userId);
	}

	public int insert(String userId, String password, String userName, String juminNumber, String email, String phoneNumber, String mobileNumber) 
	{
		MemberDTO dto = new MemberDTO();
		dto.setUserId(userId);
		dto.setPasswd(password);
		dto.setUserName(userName);
		dto.setJuminNumber(juminNumber);
		dto.setEmail(email);
		dto.setPhoneNumber(phoneNumber);
		dto.setMobileNumber(mobileNumber);
		getSqlMapClientTemplate().insert("Member.insert", dto);
		return 1;
	}

	public int update(String userName, String email, String phoneNumber, String mobileNumber, String userId) 
	{
		MemberDTO dto = new MemberDTO();
		dto.setUserId(userId);
		dto.setUserName(userName);
		dto.setEmail(email);
		dto.setPhoneNumber(phoneNumber);
		dto.setMobileNumber(mobileNumber);
		return getSqlMapClientTemplate().update("Member.update", dto);
	}

	public int delete(String userId) 
	{
		return getSqlMapClientTemplate().delete("Member.delete", userId);
	}

	public int changePasswd(String userId, String newPasswd) 
	{
		Map map = new HashMap();
		map.put("passwd", newPasswd);
		map.put("userId", userId);
		return getSqlMapClientTemplate().update("Member.updatePassword", map);
	}

	public int setLevel(String userId, int lev) 
	{
		Map map = new HashMap();
		map.put("userId", userId);
		map.put("level", lev);
		return getSqlMapClientTemplate().update("Member.updateLevel", map);
	}

	public int setLastLogin(String userId) 
	{
		return getSqlMapClientTemplate().update("Member.updateLastLongin", userId);
	}

	public List getMemberList(long start, int limit) 
	{
		Map map = new HashMap();
		map.put("start", start);
		map.put("limit", limit);
		return getSqlMapClientTemplate().queryForList("Member.getMemberList", map);
	}

	public long getRowCount() 
	{
		return (Long)getSqlMapClientTemplate().queryForObject("Member.getRowCount");
	}

}

package han.juser.dao;

import java.util.List;

import han.juser.model.MemberDTO;


public interface MemberDAO 
{
	public abstract MemberDTO getMember(String userId);
	
	public abstract int insert(String userId, String password, String userName, String juminNumber, String email, String phoneNumber, String mobileNumber);
	
	public abstract int update(String userName, String email, String phoneNumber, String mobileNumber, String userId);

	public abstract int delete(String userId);
	
	public abstract int changePasswd(String userId, String newPasswd);

	public abstract int setLevel(String userId, int lev);

	public abstract int setLastLogin(String userId);
	
	public abstract List getMemberList(long start, int limit);
	
	public abstract long getRowCount() ;
}
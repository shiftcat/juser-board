package han.juser.service;

import han.juser.dao.MemberDAO;
import han.juser.model.MemberDTO;

import java.util.List;

public interface MemberService extends ServiceBase
{

	public abstract void setMemberDAO(MemberDAO dao);

	public abstract List getMemberList(int index);

	public abstract int insert(String userId, String password, String userName,
			String juminNumber, String email, String phoneNumber,
			String mobileNumber) throws MemberExistException;

	public abstract int update(String userName, String email,
			String phoneNumber, String mobileNumber, String userId)
			throws MemberNotFoundException;

	public abstract MemberDTO getMember(String userId, String password)
			throws MemberNotFoundException, MissMatchPasswordException;

	public abstract int changePasswd(String userId, String oldPasswd,
			String newPasswd) throws MemberNotFoundException,
			MissMatchPasswordException;

	public abstract int setLevel(String userId, int lev)
			throws MemberNotFoundException;

	public abstract int setLastLogin(String userId);

	public abstract int delete(String userId);

	public abstract long getRowCount();

	public abstract MemberDTO getMember(String userId)
			throws MemberNotFoundException;

	// 존재하면 true
	public abstract boolean checkMember(String userId);

}
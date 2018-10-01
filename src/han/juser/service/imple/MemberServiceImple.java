package han.juser.service.imple;

import han.juser.dao.MemberDAO;
import han.juser.model.MemberDTO;
import han.juser.service.MemberExistException;
import han.juser.service.MemberNotFoundException;
import han.juser.service.MemberService;
import han.juser.service.MissMatchPasswordException;
import han.juser.url.PageBean;

import java.util.List;


public class MemberServiceImple extends PageBean implements MemberService
{
	private MemberDAO dao;
	
	public MemberServiceImple()
	{
		super();
	}
	
	
	/* (non-Javadoc)
	 * @see han.juser.service.MemberService#setMemberDAO(han.juser.dao.MemberDAO)
	 */
	public void setMemberDAO(MemberDAO dao)
	{
		this.dao = dao;
	}
	
	
	/* (non-Javadoc)
	 * @see han.juser.service.MemberService#getMemberList(int)
	 */
	public List getMemberList(int index)
	{
		setCurrentPage(index);
		setRowCount( getRowCount() );
		return dao.getMemberList(getStart()+1, getLimit());
	}
	
	
	
	
	/* (non-Javadoc)
	 * @see han.juser.service.MemberService#insert(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	public int insert(String userId, String password, String userName, String juminNumber, String email, String phoneNumber, String mobileNumber ) throws MemberExistException
	{
		MemberDTO dto = dao.getMember(userId);
		if( dto != null ) {
			throw new MemberExistException();
		}
		return dao.insert(userId, password, userName, juminNumber, email, phoneNumber, mobileNumber);
	}
	
	
	
	
	/* (non-Javadoc)
	 * @see han.juser.service.MemberService#update(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	public int update(String userName, String email, String phoneNumber, String mobileNumber, String userId) throws MemberNotFoundException
	{
		MemberDTO dto = dao.getMember(userId);
		if(dto == null ) {
			throw new MemberNotFoundException();
		}
		return dao.update(userName, email, phoneNumber, mobileNumber, userId);
	}
	
	
	
	
	/* (non-Javadoc)
	 * @see han.juser.service.MemberService#login(java.lang.String, java.lang.String)
	 */
	public MemberDTO getMember(String userId, String password) throws MemberNotFoundException, MissMatchPasswordException
	{
		MemberDTO dto = getMember( userId);
		if(dto.getLevel() == 0) {
			throw new MemberNotFoundException();
		}
		if( !dto.getPasswd().equals(password) ) {
			throw new MissMatchPasswordException();
		}
		return dto;
	}
	
	
	
	
	/* (non-Javadoc)
	 * @see han.juser.service.MemberService#changePasswd(java.lang.String, java.lang.String, java.lang.String)
	 */
	public int changePasswd(String userId, String oldPasswd, String newPasswd) throws MemberNotFoundException, MissMatchPasswordException
	{
		MemberDTO dto = getMember(userId);
		
		if(dto.getLevel() == 0) {
			throw new MemberNotFoundException();
		}
		if( !dto.getPasswd().equals(oldPasswd) ) {
			throw new MissMatchPasswordException();
		}

		return dao.changePasswd(userId, newPasswd);
	}
	
	
	
	
	
	/* (non-Javadoc)
	 * @see han.juser.service.MemberService#setLevel(java.lang.String, int)
	 */
	public int setLevel(String userId, int lev) throws MemberNotFoundException
	{
		MemberDTO dto = dao.getMember(userId);
		if(dto == null) {
			throw new MemberNotFoundException();
		}
		return dao.setLevel(userId, lev);
	}
	
	
	
	
	/* (non-Javadoc)
	 * @see han.juser.service.MemberService#setLastLogin(java.lang.String)
	 */
	public int setLastLogin(String userId)
	{
		return dao.setLastLogin(userId);
	}
	
	
	
	
	
	/* (non-Javadoc)
	 * @see han.juser.service.MemberService#delete(java.lang.String)
	 */
	public int delete(String userId)
	{
		return dao.delete(userId);
	}
	
	
	
	
	
	/* (non-Javadoc)
	 * @see han.juser.service.MemberService#getRowCount()
	 */
	public long getRowCount()
	{
		return dao.getRowCount();
	}
	
	
	
	
	
	/* (non-Javadoc)
	 * @see han.juser.service.MemberService#getMember(java.lang.String)
	 */
	public MemberDTO getMember(String userId) throws MemberNotFoundException
	{
		MemberDTO dto = dao.getMember(userId);
		if(dto == null) {
			throw new MemberNotFoundException();
		}
		return dto;
	}

	
	// 존재하면 true
	/* (non-Javadoc)
	 * @see han.juser.service.MemberService#checkMember(java.lang.String)
	 */
	public boolean checkMember(String userId)
	{
		if( dao.getMember(userId) == null ) {
			return false;
		}else {
			return true;
		}
	}
	
}

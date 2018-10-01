package han.juser.controller.action;

public class Properties 
{
	
	/* ################# 전역 메세지  ############################# */
	
	/**
	 * 뒤로가기 후 재전송, 새로고침은 허용하지 않습니다.
	 */
	public static final String FAIL_TOKEN = "errors.global.failTokenValid";
	
	
	/**
	 * 로그인 되어있지 않습니다.
	 */
	public static final String NOT_LOGIN = "errors.global.notLogin";
	
	
	/**
	 * 회원전용 입니다.
	 */
	public static final String ONLY_MEMBER = "errors.global.onlyMember";
	
	
	
	
	/* ################# Member 메세지  ############################# */
	
	/**
	 * 유효한 요청이 아닙니다.
	 */
	public static final String NOT_VALID_REQUEST = "errors.member.notValidRequest";
	
	/**
	 * !! 정상적으로 로그인 되었습니다. !!
	 */
	public static final String MEMBER_SUCCESS_LOGIN = "messages.member.successLogin";
	
	
	/**
	 * !! 정상적으로 가입되었습니다. !!
	 */
	public static final String MEMBER_SUCCESS_ACCOUNT = "messages.member.successAccount";
	
	
	/**
	 * !!정상적으로 변경되었습니다.!!
	 */
	public static final String MEMBER_SUCCESS_MODIFY = "messages.member.successModify";
	
	
	/**
	 * !! 정상적으로 탈퇴되었습니다. !!
	 */
	public static final String MEMBER_SUCCESS_SECEDE = "messages.member.successSecede";
	
	
	
	
	
	
	
	/* ################# 게시판 메세지  ############################# */
	

	
	/**
	 * !!정상적으로 입력되었습니다.!!
	 */
	public static final String SUCCESS_WRITE = "messages.board.successWrite";
	
	
	
	/**
	 * !!정상적으로 변경되었습니다. !!
	 */
	public static final String SUCCESS_MODIFY = "messages.board.successModify";
	
	
	
	/**
	 * !!정상적으로 삭제되었습니다. !!
	 */
	public static final String SUCCESS_REMOVE = "messages.board.successRemove";
	
	
	
	/**
	 * !! 정상적으로 반영되었습니다. !!
	 */
	public static final String SUCCESS_VOTE = "messages.board.successVote";

}

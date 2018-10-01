package han.juser.dao;

import han.juser.model.BlogDTO;
import han.juser.model.BlogReplyDTO;

import java.util.List;


/**
 * 
 * @author Y.Han Lee
 * 블로그 데이터 Access Object
 *
 */
public interface BlogDAO extends CategoryDAO, Summary
{
	
	/**
	 * 게시물 삭제
	 */
	public int deleteContent(long articleId);

	
	/**
	 * 게시물의 뎃글 삭제
	 */
	public int deleteReply(long replyId);
	
	
	/**
	 * 게시물 반환
	 */
	public BlogDTO getContent(long articleId);
	
	
	/**
	 * 게시물 리스트 반환
	 */
	public List getContentList(long start, int limit);
	
	
	/**
	 * 해당 글분류에 속하는 게시물 리스트 반환
	 */
	public List getContentList(int cateId, long start, int limit);
	
	
	/**
	 * 게시물 검색
	 */
	public List getContentList(long start, int limit, String column, String keyword);
	
	
	/**
	 * 해당 글분류에 속하는 게시물의 검색
	 */
	public List getContentList(int cateId, long start, int limit, String column, String keyword);

	
	/**
	 * 해당 게시물의 뎃글리스트를 반환
	 */
	public List getReplyList(long articleId);
	
	/**
	 * 뎃글반환
	 */
	public BlogReplyDTO getReply(long replyId);
	
	
	/**
	 * 전체 게시물의 수
	 */
	public long getRowCount();
	
	
	/**
	 * 특정 글분류에 속하는 게시물의 수
	 */
	public long getRowCount(int cateId);
	
	
	/**
	 * 특정 글분류에 속하는 검색된 게시물의 수
	 */
	public long getRowCount(int cateId, String column, String keyword);
	
	
	/**
	 * 전체검색 게시물의 수
	 */
	public long getRowCount(String column, String keyword);
	
	
	/**
	 * 게시물을 저장하고 생성된 고유번호 리턴
	 */
	public long insertContent(BlogDTO dto);
	
	
	/**
	 * 뎃글을 저장하고 생성된 고유번호 리턴
	 */
	public int insertReply(BlogReplyDTO reply); 
	
	
	/**
	 * 업로드된 파일명을 비운다.
	 */
	public int unSetFileNames(long articleId);
	
	
	/**
	 * 게시물의 변경
	 */
	public int updateContent(BlogDTO dto);

}

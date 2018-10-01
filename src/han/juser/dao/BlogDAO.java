package han.juser.dao;

import han.juser.model.BlogDTO;
import han.juser.model.BlogReplyDTO;

import java.util.List;


/**
 * 
 * @author Y.Han Lee
 * ��α� ������ Access Object
 *
 */
public interface BlogDAO extends CategoryDAO, Summary
{
	
	/**
	 * �Խù� ����
	 */
	public int deleteContent(long articleId);

	
	/**
	 * �Խù��� ���� ����
	 */
	public int deleteReply(long replyId);
	
	
	/**
	 * �Խù� ��ȯ
	 */
	public BlogDTO getContent(long articleId);
	
	
	/**
	 * �Խù� ����Ʈ ��ȯ
	 */
	public List getContentList(long start, int limit);
	
	
	/**
	 * �ش� �ۺз��� ���ϴ� �Խù� ����Ʈ ��ȯ
	 */
	public List getContentList(int cateId, long start, int limit);
	
	
	/**
	 * �Խù� �˻�
	 */
	public List getContentList(long start, int limit, String column, String keyword);
	
	
	/**
	 * �ش� �ۺз��� ���ϴ� �Խù��� �˻�
	 */
	public List getContentList(int cateId, long start, int limit, String column, String keyword);

	
	/**
	 * �ش� �Խù��� ���۸���Ʈ�� ��ȯ
	 */
	public List getReplyList(long articleId);
	
	/**
	 * ���۹�ȯ
	 */
	public BlogReplyDTO getReply(long replyId);
	
	
	/**
	 * ��ü �Խù��� ��
	 */
	public long getRowCount();
	
	
	/**
	 * Ư�� �ۺз��� ���ϴ� �Խù��� ��
	 */
	public long getRowCount(int cateId);
	
	
	/**
	 * Ư�� �ۺз��� ���ϴ� �˻��� �Խù��� ��
	 */
	public long getRowCount(int cateId, String column, String keyword);
	
	
	/**
	 * ��ü�˻� �Խù��� ��
	 */
	public long getRowCount(String column, String keyword);
	
	
	/**
	 * �Խù��� �����ϰ� ������ ������ȣ ����
	 */
	public long insertContent(BlogDTO dto);
	
	
	/**
	 * ������ �����ϰ� ������ ������ȣ ����
	 */
	public int insertReply(BlogReplyDTO reply); 
	
	
	/**
	 * ���ε�� ���ϸ��� ����.
	 */
	public int unSetFileNames(long articleId);
	
	
	/**
	 * �Խù��� ����
	 */
	public int updateContent(BlogDTO dto);

}

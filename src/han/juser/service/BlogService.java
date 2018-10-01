package han.juser.service;

import han.juser.dao.BlogDAO;
import han.juser.model.BlogDTO;
import han.juser.model.BlogReplyDTO;

import java.util.List;

public interface BlogService  extends ServiceBase, SummaryService 
{

	public abstract void setBlogDAO(BlogDAO dao);

	public abstract long getRowCount();

	public abstract long getRowCount(String column, String keyword);

	public abstract long getRowCount(int cateId);

	public abstract long getRowCount(int cateId, String column, String keyword);

	public abstract List getContentList(int index);

	public abstract List getContentList(int cateId, int index);

	public abstract List getContentList(int cateId, int index, String column, String keyword);

	public abstract List getContentList(int index, String column, String keyword);

	public abstract BlogDTO getContent(long articleId) throws ContentNotFoundException;

	public abstract BlogDTO getContent(long articleId, String userId) throws ContentNotFoundException, NotOwnerException;

	public abstract long insert(BlogDTO dto) throws CategoryNotFoundException;

	public abstract int update(BlogDTO dto) throws CategoryNotFoundException, ContentNotFoundException, NotOwnerException;

	public abstract int delete(long articleId);

	public abstract int delete(long articleId, String userId) throws ContentNotFoundException, NotOwnerException;

	public abstract int insertReply(BlogReplyDTO reply) throws ParentContentNotFoundException;

	public abstract int deleteReply(long replyId);

	public abstract int deleteReply(long replyId, String userId) throws ContentNotFoundException, NotOwnerException;

	public abstract BlogReplyDTO getReply(long replyId) throws ContentNotFoundException;

	public abstract int unSetFileNames(long articleId, String userId) throws ContentNotFoundException, NotOwnerException;

	public abstract List getReplyList(long articleId);

}
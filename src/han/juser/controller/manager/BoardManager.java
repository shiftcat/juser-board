package han.juser.controller.manager;

import han.juser.controller.form.BoardForm;
import han.juser.controller.form.BoardReplyForm;
import han.juser.controller.param.BoardParam;
import han.juser.model.BoardDTO;
import han.juser.service.BoardService;
import han.juser.service.ContentNotFoundException;
import han.juser.service.NotOwnerException;
import han.juser.service.ParentContentNotFoundException;
import han.juser.service.ReplyNotFoundException;
import han.juser.url.PageBean;
import han.util.FileDown;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 * Board°ü¸®ÀÚ 
 * 
 * @author Y.Han Lee
 *
 */
public class BoardManager extends ManagerBase
{
	
	private static final String BOARD_HIT = "han.action.board.BoardManager/hit";
	
	private static final String BOARD_VOTE = "han.action.board.BoardManager/vote";
	
	
	private BoardService service;
	
	public BoardManager()
	{
		super();
	}

	
	public void setService(BoardService service)
	{
		this.service = service; 
	}
	
	
	/*
	public void download(HttpServletRequest req, HttpServletResponse res)
	{
		BoardDTO dto = null;
		
		try {
			long bbsId = BoardParam.getContentId(req);
			dto = getContent(req, bbsId);
		}catch(Exception e) {
			log.error(e.getMessage(), e);
		}
		
		if( dto.getSystemFiles() == null ) {
			return;
		}
		
		String fileName = dto.getOriginalFiles();
		File file = new File( saveDir, dto.getSystemFiles() );
		super.download(req, res, fileName, file);
	}
	*/
	
	
	public void download(HttpServletRequest req, HttpServletResponse res)
	{
		BoardDTO dto = null;
		InputStream is = null;
		try {
			long bbsId = BoardParam.getContentId(req);
			dto = getContent(req, bbsId);
			is = service.getFileStream(bbsId);
		}catch(Exception e) {
			log.error(e.getMessage(), e);
		}
		
		if( is == null ) {
			return;
		}
		
		FileDown down = new FileDown(req, res);
		down.setHeader(dto.getOriginalFiles());
		down.flush(is);
		if( log.isDebugEnabled() ) {
			log.debug(" !! file download success !! ");
		}
	}
	
	
	
	
	private BoardDTO getContent(HttpServletRequest req, long bbsId) throws ContentNotFoundException
	{
			return service.getContent( bbsId);
	}
	
	
	
	public long addContent(HttpServletRequest req, BoardForm form) throws FileNotFoundException, IOException
	{
		return service.insert( loginHandler.getMember(req).getUserId() , form.getSubject(), 
					form.getContent(), req.getRemoteAddr(), form.getLink(), 
					form.getOriginalFileName(), null, form.getFormFile().getInputStream(), form.getFormFile().getFileSize());
	}
	
	
	
	
	public int addReply(HttpServletRequest req, BoardReplyForm form) throws ParentContentNotFoundException
	{
		return service.insertReply( form.getBbsId(), 
						loginHandler.getMember(req).getUserId(), req.getRemoteAddr(), form.getReply());
	}
	
	
	
	
	
	public int removeReply(HttpServletRequest req) throws ReplyNotFoundException, NotOwnerException
	{
		if( role.isAdmin(req) ) { 
			return service.deleteReply( BoardParam.getReplyId(req));
		} else {
			return service.deleteReply( BoardParam.getReplyId(req), loginHandler.getMember(req).getUserId() );
		}
	}
	
	
	
	
	
	public int modifyContent(HttpServletRequest req, BoardForm form) throws ContentNotFoundException, NotOwnerException, FileNotFoundException, IOException
	{
		return service.update( form.getBbsId(), loginHandler.getMember(req).getUserId(), form.getSubject(), form.getContent(), form.getLink(), 
					req.getRemoteAddr(), form.getOriginalFileName(), null, form.getFormFile().getInputStream(), form.getFormFile().getFileSize());
	}
	
	
	
	
	
	
	public int removeContent(HttpServletRequest req) throws ContentNotFoundException, NotOwnerException
	{
			if( role.isAdmin(req) ) {
				return service.delete( BoardParam.getContentId(req));
			} else {
				return  service.delete( BoardParam.getContentId(req), loginHandler.getMember(req).getUserId() );
			}
	}
	
	
	
	
	
	
	
	public void setContentList(HttpServletRequest req, String key)
	{
		List list = service.getContentList( BoardParam.getPageNumber(req));
		req.setAttribute(PageBean.PAGEBEAN_KEY, service.getPageBean());
		req.setAttribute(key, list);
	}
	
	
	
	
	
	public void setContentList(HttpServletRequest req, String key, String column, String keyword)
	{
		List list = service.getContentList( BoardParam.getPageNumber(req), column, keyword);
		req.setAttribute(PageBean.PAGEBEAN_KEY, service.getPageBean());
		req.setAttribute(key, list);
	}
	
	
	
	
	
	private void updateHit(HttpServletRequest req, long bbsId)
	{
		HttpSession session = req.getSession();
		synchronized(session) {
			List hit = (List)session.getAttribute(BOARD_HIT);
			if(hit == null || hit.isEmpty()) {
				hit = new ArrayList();
				hit.add(bbsId);
				session.setAttribute(BOARD_HIT, hit);
				service.updateHit(  bbsId );
			}else {
				if( hit.indexOf(bbsId) == -1 ) {
					hit.add(bbsId);
					service.updateHit(  bbsId );
				}
			}
		}
	}
	
	
	
	
	
	public void updateVote(HttpServletRequest req) throws ContentNotFoundException
	{
		HttpSession session = req.getSession();
		synchronized(session) {
			List vote = (List)session.getAttribute(BOARD_VOTE);
			Long bbsId = BoardParam.getContentId(req);
			getContent(req, bbsId );
			if(vote == null || vote.isEmpty()) {
				vote = new ArrayList();
				vote.add(bbsId);
				session.setAttribute(BOARD_VOTE, vote);
				service.updateVote( bbsId);
			}else {
				if( vote.indexOf(bbsId) == -1 ) {
					vote.add(bbsId);
					service.updateVote( bbsId);
				}
			}
		}
	}
	
	
	
	
	public void setContent(HttpServletRequest req, String key) throws ContentNotFoundException
	{
		long bbsId = BoardParam.getContentId(req);
		updateHit(req, bbsId);
		BoardDTO dto = getContent(req, bbsId);
		
		req.setAttribute(key, dto);
	}
	
	
	public void setContent(HttpServletRequest req, String key, String userId) throws ContentNotFoundException, NotOwnerException
	{
		long bbsId = BoardParam.getContentId(req);
		BoardDTO dto = service.getContent( bbsId, loginHandler.getMember(req).getUserId());
		updateHit(req, bbsId);
		
		req.setAttribute(key, dto);
	}
}

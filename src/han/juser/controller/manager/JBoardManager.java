package han.juser.controller.manager;

import han.juser.controller.form.JBoardForm;
import han.juser.controller.param.JBoardParam;
import han.juser.model.JBoardDTO;
import han.juser.service.ContentNotFoundException;
import han.juser.service.JBoardService;
import han.juser.service.NotOwnerException;
import han.juser.service.ParentContentNotFoundException;
import han.juser.url.PageBean;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.core.io.Resource;


public class JBoardManager extends ManagerBase
{
	
	private static final String JBOARD_HIT = "han.action.jboard.JBoardManager/hit";
	
	private static final String JBOARD_VOTE = "han.action.jboard.JBoardManager/vote";
	
	JBoardService service;
	
	
	private String saveFilePath;
	
	
	public JBoardManager()
	{
		super();
	}
	
	
	public void setService(JBoardService svc)
	{
		service = svc;
	}

	
	public void setSaveFilePath(Resource path) throws IOException
	{
		this.saveFilePath = path.getFile().getPath();
		if(log.isDebugEnabled()) {
			log.debug("파일 업로드 디렉토리 " + saveFilePath);
		}
	}
	
	
	
	public void download(HttpServletRequest req, HttpServletResponse res)
	{
		JBoardDTO dto = null;
		try {
			dto = getContent(req);
		}catch(Exception e) {
			log.warn(e.getMessage(), e);
		}
		
		String fileName = dto.getOriginalFiles();
		File file = new File( saveFilePath, dto.getSystemFiles() );
		
		super.download(req, res, fileName, file);
	}
	
	
	
	
	private void updateHit(HttpServletRequest req, Long articleId)
	{

		HttpSession session = req.getSession();
		List hit = (List)session.getAttribute(JBOARD_HIT);
		if(hit == null || hit.isEmpty()) {
			hit = new ArrayList();
			hit.add(articleId);
			session.setAttribute(JBOARD_HIT, hit);
			service.updateHit( articleId);
		}else {
			if( hit.indexOf(articleId) == -1 ) {
				hit.add(articleId);
				service.updateHit( articleId);
			}
		}
	}
	
	
	
	
	
	
	public void updateVote(HttpServletRequest req) throws ContentNotFoundException
	{
		HttpSession session = req.getSession();
		List vote = (List)session.getAttribute(JBOARD_VOTE);
		
		Long articleId = JBoardParam.getContentId(req);
		getContent(req);
		if(vote == null || vote.isEmpty()) {
			vote = new ArrayList();
			vote.add(articleId);
			session.setAttribute(JBOARD_VOTE, vote);
			service.updateVote( articleId);
		}else {
			if( vote.indexOf(articleId) == -1 ) {
				vote.add(articleId);
				service.updateVote( articleId);
			}
		}
	}
	
	
	
	private JBoardDTO getContent(HttpServletRequest req) throws ContentNotFoundException
	{
		JBoardDTO dto = service.getContent( JBoardParam.getContentId(req));
		return dto;
	}
	
	
	
	
	
	private JBoardDTO getContent(HttpServletRequest req, String userId) throws ContentNotFoundException, NotOwnerException
	{
		JBoardDTO dto = service.getContent( JBoardParam.getContentId(req), loginHandler.getMember(req).getUserId());
		return dto;
	}
	
	
	
	public void setContentList(HttpServletRequest req, String key)
	{
		List list = service.getContentList( JBoardParam.getPageNumber(req) );
		
		req.setAttribute(PageBean.PAGEBEAN_KEY,  service.getPageBean() );
		req.setAttribute(key, list);
	}
	
	
	
	
	
	public void setContentList(HttpServletRequest req, String key, String column, String keyword)
	{
		List list = service.getContentList(JBoardParam.getPageNumber(req),  column, keyword );
		
		req.setAttribute(PageBean.PAGEBEAN_KEY, service.getPageBean());
		req.setAttribute(key, list);
	}
	
	
	
	public void setContent(HttpServletRequest req, String contentKey) throws ContentNotFoundException
	{
		updateHit( req, JBoardParam.getContentId(req));
		JBoardDTO dto = getContent(req);
		req.setAttribute(contentKey, dto);
	}
	
	
	
	public void setContent(HttpServletRequest req, String contentKey, String userId) throws ContentNotFoundException, NotOwnerException
	{
		JBoardDTO dto = getContent(req ,  loginHandler.getMember(req).getUserId());
		req.setAttribute(contentKey, dto);
	}
	
	
	
	public void setGroupList(HttpServletRequest req, String key) throws ContentNotFoundException
	{
		JBoardDTO dto = getContent(req);
		List list = service.getChildList(dto.getGroupId());
		req.setAttribute(key, list);
		
	}
	
	
	
	public long addContent(HttpServletRequest req, JBoardForm form) throws ParentContentNotFoundException
	{
		String systemFile = null;
		long articleId = -1;
		
		try{
			systemFile = upload(form.getFormFile(), saveFilePath);
			articleId = service.insert(form.getArticleId(), loginHandler.getMember(req).getUserId(), form.getLink(), form.getSubject(), form.getContent(), form.getOriginalFiles(), systemFile, req.getRemoteAddr());
		}finally {
			if(articleId == -1){
				deleteFile(saveFilePath, systemFile);
			}
		}
		
		return articleId;
	}
	
	
	
	
	
	/*
	 * long articleId, String userId, String subject, String content, String systemFiles, String originalFiles, String link, String remoteAddr
	 */
	public int modifyContent(HttpServletRequest req, JBoardForm form) throws ContentNotFoundException, NotOwnerException
	{
		String oldSystemFile = null;  // 기존파일
		String newSystemFile = null;
		
		int irs = -1;
		try{
			newSystemFile = upload(form.getFormFile(), saveFilePath);  // 새 파일
			JBoardDTO dto = service.getContent( form.getArticleId(), loginHandler.getMember(req).getUserId() );
			oldSystemFile = dto.getSystemFiles();
			irs = service.update( form.getArticleId(), loginHandler.getMember(req).getUserId(), form.getSubject(), form.getContent(), newSystemFile, form.getOriginalFiles(), form.getLink(), req.getRemoteAddr());
		}finally {
			if(irs == -1) {
				deleteFile(saveFilePath, newSystemFile);
			}
		}
		
		
		// 널이 아니면 업로드된걸로 간주, 기존 파일 삭제
		if( newSystemFile != null ) {
			deleteFile( saveFilePath, oldSystemFile );
		}
		
		return irs;
	}
	
	
	
	
	
	public int removeContent(HttpServletRequest req) throws ContentNotFoundException, NotOwnerException
	{
		JBoardDTO dto = null; //부모글
		List list = null; //자식글 리스트
		
		int irs = -1;
			
		if( role.isAdmin(req) ) {
			dto = service.getContent( JBoardParam.getContentId(req) );
		}else {
			dto = service.getContent( JBoardParam.getContentId(req) , loginHandler.getMember(req).getUserId() );
		}
		
		list = service.getChildList(  dto.getArticleId() );
		irs = service.delete( dto.getArticleId());
		
		
		//정상적으로 삭제되면 업로드한 파일 삭제
		deleteFile(this.saveFilePath, dto.getSystemFiles());
		for( Iterator i = list.iterator(); i.hasNext(); ) {
			JBoardDTO jb = (JBoardDTO)i.next();
			deleteFile(this.saveFilePath, jb.getSystemFiles());
		}
		
		return irs;
	}
	
	
	
	
	
	
	public int deleteFile(HttpServletRequest req) throws ContentNotFoundException, NotOwnerException
	{
		long articleId = JBoardParam.getContentId(req);
		JBoardDTO dto = service.getContent( articleId, loginHandler.getMember(req).getUserId() );
		int irs = service.unSetFileNames( loginHandler.getMember(req).getUserId(), articleId);
		if(irs == 1) {
			deleteFile(this.saveFilePath, dto.getSystemFiles());
		}
		
		return irs;
	}
	
}

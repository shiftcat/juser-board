package han.juser.controller.manager;

import han.juser.controller.param.GuestParam;
import han.juser.service.ContentNotFoundException;
import han.juser.service.GuestService;
import han.juser.service.NotOwnerException;
import han.juser.url.PageBean;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



/**
 * 
 * @author Y.Han Lee
 *
 */
public class GuestManager extends ManagerBase
{
	
	GuestService service;
	
	
	public GuestManager()
	{
	}
	
	public void setService(GuestService service)
	{
		this.service = service;
	}
	
	
	private boolean isMy(HttpServletRequest req)
	{
		Cookie cook[] = req.getCookies();
		if( cook == null ) {
			return false;
		}
		for( int i = 0; i < cook.length; i++ ) {
			if ( cook[i].getName().equals("MYGUEST") ) {
				return Boolean.valueOf(cook[i].getValue());
			}
		}
		return false;
	}
	
	
	private void setMy(HttpServletRequest req, HttpServletResponse res, boolean b)
	{
		Cookie cook[] = req.getCookies();
		Cookie cookie = null;
		if( cook == null ) {
			cookie = new Cookie("MYGUEST", String.valueOf(b));
			res.addCookie(cookie);
			return;
		}
		for( int i = 0; i < cook.length; i++ ) {
			if ( cook[i].getName().equals("MYGUEST") ) {
				cook[i].setValue(String.valueOf(b));
				res.addCookie(cook[i]);
				return;
			}
		}
	}
	
	
	public void setContentList(HttpServletRequest req, HttpServletResponse res, String key)
	{
		int pno = GuestParam.getPageNumber(req);
		if(isMy(req)) {
			if( loginHandler.isLogin(req)) {
				req.setAttribute(key, service.getContentList( pno, loginHandler.getMember(req).getUserId() ));
			} else {
				setMy(req, res, false);
				req.setAttribute(key, service.getContentList( pno ) );
			}
			
		} else {
			req.setAttribute(key, service.getContentList( pno ) );
		}		
		req.setAttribute(PageBean.PAGEBEAN_KEY, service.getPageBean());
	}
	
	
	
	public void setContent(HttpServletRequest req,  String key) throws ContentNotFoundException, NotOwnerException
	{
		long gid = GuestParam.getContentId(req);
		if(gid > 0 && loginHandler.getMember(req) != null) {
			req.setAttribute( key , service.getContent( gid, loginHandler.getMember(req).getUserId()));			
		}
	}
	
	
	public long addContent(HttpServletRequest req,  String content)
	{
		return service.insert(  loginHandler.getMember(req).getUserId(), content, req.getRemoteAddr() );
	}
	
	
	
	
	public int modifyContent(HttpServletRequest req, long gid, String content) throws ContentNotFoundException, NotOwnerException
	{
		return service.update(  gid, loginHandler.getMember(req).getUserId(), content, req.getRemoteAddr());
	}
	
	
	public int delete(HttpServletRequest req) throws ContentNotFoundException, NotOwnerException
	{
		long gid = GuestParam.getContentId(req);
		if( role.isAdmin(req) ) {
			return service.delete( gid );
		} else {
			return service.delete( gid , loginHandler.getMember(req).getUserId() );
		}
	}
}

package han.juser.controller.action;

import han.juser.controller.manager.MainManager;
import han.juser.controller.manager.SubMenuItem;
import han.juser.model.CategoryDTO;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class MainAction extends ActionBase
{
	private MainManager manager;
	
	
	public void setMainManager(MainManager mm)
	{
		manager = mm;
	}
	
	
	
	public void setSubMenu(HttpServletRequest req, String key) 
	{
		List sub = new ArrayList();
		List cate = categoryManager.getCategorys(req);
		for(Iterator i = cate.iterator(); i.hasNext();) {
			CategoryDTO dto = (CategoryDTO)i.next();
			SubMenuItem item = new SubMenuItem();
			item.setName(dto.getCateName());
			item.setUrl(req.getContextPath() + "/blog/index.do?cate=" + dto.getCateId());
			sub.add(item);
		}
		req.setAttribute(key, sub);
	}
	
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, 
			HttpServletRequest req, HttpServletResponse res)
	{
		common(req);
		/*
		MainItem item[] = new MainItem[4];
		item[0] = new MainItem(ServiceHelper.getBlogService(servlet.getServletContext()), 
				"blogMainItem", 5);
		item[1] = new MainItem(ServiceHelper.getBoardService(servlet.getServletContext()), 
				"boardMainItem", 3);
		item[2] = new MainItem(ServiceHelper.getJBoardService(servlet.getServletContext()), 
				"jboardMainItem", 3);
		item[3] = new MainItem(ServiceHelper.getGuestService(servlet.getServletContext()), 
				"guestMainItem", 4);
		
		manager.addItem(item);
		*/
		
		manager.setMainItems(req);
		return mapping.findForward("fw-index");
	}

}

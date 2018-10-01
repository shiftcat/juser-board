package han.juser.controller.manager;


import han.juser.model.CategoryDTO;
import han.juser.service.CategoryService;
import han.juser.service.ServiceHelper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class CategoryManager 
{
	private Log log = LogFactory.getLog(CategoryManager.class);
	
	
	public CategoryManager()
	{
		
	}
	
	private CategoryService getService(HttpServletRequest req)
	{
		return ServiceHelper.getCategoryService(req.getSession().getServletContext());
	}
	
	

	
	public void setCategorys(HttpServletRequest req, String key)
	{
		CategoryService service = getService(req);
		List cate = service.getCategoryList();
		req.setAttribute(key, cate);
	}
	
	
	public List getCategorys(HttpServletRequest req)
	{
		CategoryService service = getService(req);
		return service.getCategoryList();
	}
	
	
	public CategoryDTO getCategory(HttpServletRequest req, int cateId)
	{
		CategoryService service = getService(req);
		return service.getCategory(cateId);
	}
	
	
	public synchronized void computeCategory(HttpServletRequest req, String[] values)
	{
		if(values == null ) {
			return;
		}
		
		CategoryService service = getService(req);
		List cate0 = service.getCategoryList();
		List cate1 = parseCategory(values);
		
		List del = notExistCategorys(cate0, cate1); // 삭제대상
		List ins = notExistCategorys(cate1, cate0); // 인서트 대상
		List upd = existCategorys(cate1, cate0); // 업데이트 대상
		
		debug(del, ins, upd);
		service.saveAll(ins, upd, del);
	}
	
	
	private void debug(List del, List ins, List upd)
	{
		if( del != null ) {
			log.debug("삭제 대상 ");
			for(Iterator i = del.iterator(); i.hasNext();) {
				CategoryDTO dto = (CategoryDTO)i.next();
				log.debug(dto.getCateId() + " - " + dto.getCateName() + " - " + dto.getOrd());
			}
		}
		if( ins != null ) {
			log.debug("입력 대상 ");
			for(Iterator i = ins.iterator(); i.hasNext();) {
				CategoryDTO dto = (CategoryDTO)i.next();
				log.debug(dto.getCateId() + " - " + dto.getCateName() + " - " + dto.getOrd());
			}
		}
		if( upd != null ) {
			log.debug("변경대상");
			for(Iterator i = upd.iterator(); i.hasNext();) {
				CategoryDTO dto = (CategoryDTO)i.next();
				log.debug(dto.getCateId() + " - " + dto.getCateName() + " - " + dto.getOrd());
			}
		}
	}
	
	
	//0에서 1에 존재하지 안는 것
	private List notExistCategorys(List cate0, List cate1)
	{
		List cate = new ArrayList();
		for(Iterator i = cate0.iterator(); i.hasNext();) {
			CategoryDTO dto = (CategoryDTO)i.next();
			if( !cate1.contains(dto) ) {
				cate.add(dto);
				log.debug("존재하지 안는" + dto.getCateName());
			}
		}
		return cate;
	}
	
	
	private List existCategorys(List cate0, List cate1)
	{
		List cate = new ArrayList();
		for(Iterator i = cate0.iterator(); i.hasNext();) {
			CategoryDTO dto = (CategoryDTO)i.next();
			if( cate1.contains(dto) ) {
				cate.add(dto);
				log.debug("존재하는" + dto.getCateName());
			}
		}
		return cate;
	}
	
	
	private List parseCategory(String[] values)
	{
		List cate = new ArrayList();
		for(int i = 0; i < values.length; i++) {
			String v = values[i];
			if( Pattern.matches("^cateId=[0-9]{1,4},cateName=[^,*-+=!@#$%^&*\"\'\\~`/?]+$", v) ) {
				cate.add(makeDTO(v, i));
			}
		}
		
		return cate;
	}
	
	
	private CategoryDTO makeDTO(String value, int ord)
	{
		String[] v = value.split(",");
		int cateId = -1;
		String cateName = null;
		try {
			cateId = Integer.valueOf( v[0].substring(v[0].indexOf("cateId=")+7, v[0].length()) );
			cateName = v[1].substring(v[1].indexOf("cateName=")+9, v[1].length());
			if(log.isDebugEnabled()) {
				log.debug("\n cateId : " + cateId + "\n" + "cateName : " + cateName);
			}
		} catch (Throwable e) {
			log.error(e.getMessage(), e);
			return null;
		}
		
		CategoryDTO dto = new CategoryDTO();
		dto.setCateId(cateId);
		dto.setCateName(cateName);
		dto.setOrd(ord);
		return dto;
	}
}

package han.juser.service.imple;

import han.juser.dao.CategoryDAO;
import han.juser.model.CategoryDTO;
import han.juser.service.CategoryService;

import java.util.Iterator;
import java.util.List;

public class CategoryServiceImple implements CategoryService
{
	private CategoryDAO dao;
	
	public CategoryServiceImple()
	{
		
	}
	

	public void setCategoryDAO(CategoryDAO dao)
	{
		this.dao = dao;
	}
	
	

	public int insert(String cateName, int ord)
	{
		return dao.insertCategory(cateName, ord);
	}
	

	public int update(int cateId, String cateName, int ord)
	{
		return dao.updateCategory(cateId, cateName, ord);
	}
	

	public int delete(int cateId)
	{
		return dao.deleteCategory(cateId);
	}
	
	

	public CategoryDTO getCategory(int cateId)
	{
		return dao.getCategory(cateId);
	}
	

	public List getCategoryList()
	{
		return dao.getCategoryList();
	}
	
	
	

	public void saveAll(List ins, List upd, List del)
	{
		if( del != null ) {
			for(Iterator i = del.iterator(); i.hasNext();) {
				CategoryDTO dto = (CategoryDTO)i.next();
				dao.deleteCategory( dto.getCateId());
			}
		}
		
		if( upd != null ) {
			for(Iterator i = upd.iterator(); i.hasNext();) {
				CategoryDTO dto = (CategoryDTO)i.next();
				dao.updateCategory( dto.getCateId(), dto.getCateName(), dto.getOrd());
			}
		}
		
		if( ins != null ) {
			for(Iterator i = ins.iterator(); i.hasNext();) {
				CategoryDTO dto = (CategoryDTO)i.next();
				dao.insertCategory( dto.getCateName(), dto.getOrd());
			}
		}
	}
	
	
}

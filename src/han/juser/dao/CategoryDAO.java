package han.juser.dao;

import han.juser.model.CategoryDTO;

import java.util.List;

public interface CategoryDAO 
{
	
	public abstract int insertCategory(String cateName, int ord);
	
	public abstract int updateCategory(int cateId, String cateName, int ord);
	
	public abstract int deleteCategory(int cateId);
	
	public abstract CategoryDTO getCategory(int cateId);
	
	public abstract List getCategoryList();
	
}

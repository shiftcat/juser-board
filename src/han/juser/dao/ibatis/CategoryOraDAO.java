package han.juser.dao.ibatis;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import han.juser.dao.CategoryDAO;
import han.juser.model.CategoryDTO;

public class CategoryOraDAO extends SqlMapBaseDAO implements CategoryDAO
{

	public int insertCategory(String cateName, int ord) 
	{
		Map map = new HashMap();
		map.put("cateName", cateName);
		map.put("ord",ord);
		return (Integer)getSqlMapClientTemplate().insert("Category.insert", map);
	}

	
	
	public int updateCategory(int cateId, String cateName, int ord) 
	{
		Map map = new HashMap();
		map.put("cateId", cateId);
		map.put("cateName", cateName);
		map.put("ord",ord);
		return getSqlMapClientTemplate().update("Category.update", map);
	}

	public int deleteCategory(int cateId) 
	{
		return getSqlMapClientTemplate().delete("Category.delete", cateId);
	}

	public CategoryDTO getCategory(int cateId) 
	{
		return (CategoryDTO)getSqlMapClientTemplate().queryForObject("Category.getCategory", cateId);
	}

	public List getCategoryList() 
	{
		return getSqlMapClientTemplate().queryForList("Category.getCategoryList");
	}

}

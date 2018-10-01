package han.juser.service;

import han.juser.dao.CategoryDAO;
import han.juser.model.CategoryDTO;

import java.util.List;

public interface CategoryService {

	public abstract void setCategoryDAO(CategoryDAO dao);

	public abstract int insert(String cateName, int ord);

	public abstract int update(int cateId, String cateName, int ord);

	public abstract int delete(int cateId);

	public abstract CategoryDTO getCategory(int cateId);

	public abstract List getCategoryList();

	public abstract void saveAll(List ins, List upd, List del);

}
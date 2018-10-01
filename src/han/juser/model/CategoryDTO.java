package han.juser.model;

import java.io.Serializable;

public class CategoryDTO implements Serializable 
{
	private int cateId;
	
	private String cateName;
	
	private int limit;
	
	private int ord;
	
	

	public CategoryDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	public int getCateId() {
		return cateId;
	}

	
	public void setCateId(int cateId) {
		this.cateId = cateId;
	}



	public String getCateName() {
		return cateName;
	}



	
	public void setCateName(String cateName) {
		this.cateName = cateName;
	}


	
	public int getLimit() {
		return limit;
	}


	
	public void setLimit(int limit) {
		this.limit = limit;
	}


	
	public int getOrd() {
		return ord;
	}


	
	public void setOrd(int ord) {
		this.ord = ord;
	}
	
	
	public boolean equals(Object dto)
	{
		if( dto instanceof CategoryDTO ) {
			if( this.cateId == ((CategoryDTO)dto).getCateId() ) {
				return true;
			}else {
				return false;
			}
		} else {
			return false;
		}
	}

}

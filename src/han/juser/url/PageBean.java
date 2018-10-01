package han.juser.url;

/**
 * 
 * @author Y.Han Lee
 *
 */
public class PageBean
{
	
	public static final String PAGEBEAN_KEY = "han.tag.PageBean"; 
	
	
	/**
	 * 페이지당  게시물 수
	 */
	private double limit;

	/**
	 * 총 페이지 수
	 */
	private int pageCount;

	/**
	 * 현재 페이지
	 */
	private int currentPage;

	/**
	 * 총 게시물 수
	 */
	private long rowCount;
	
	/**
	 * 페이지 인덱스의 시작 값
	 */
	private int start;

	
	/**
	 * 기본 값으로 페이지당 게시물 수 10 현재페이지 번호가 1인 객체를 생성 
	 *
	 */
	public PageBean()
	{
		this.limit = 10;
		this.currentPage = 1;
	}
	
	
	
	/**
	 * 페이지당 게시물 수 설정
	 * @param limit
	 */
	public void setLimit(int limit)
	{
		this.limit = limit;
	}
	
	
	/**
	 * 현재 페이지 설정
	 * @param curr
	 */
	public void setCurrentPage(int curr)
	{
		this.currentPage = curr;
	}
	
	
	/**
	 * 현재 페이지 반환
	 * @return
	 */
	public int getCurrentPage()
	{
		return this.currentPage;
	}
	
	/**
	 * 총 게시물 수 반환
	 * @return
	 */
	public long getRowCount()
	{
		return this.rowCount;
	}
	
	
	/**
	 * 시작값 반환, 현재 페이지가 1이면 0을 반환한다
	 * @return
	 */
	public int getStart()
	{
		return this.start;
	}
	
	/**
	 * 페이지당 게시물 수 반환
	 * @return
	 */
	public int getLimit()
	{
		return (int)this.limit;
	}
	
	
	/**
	 * 총 게시물 설정, 총 페이지 수와 시작값이 설정 된다.
	 * @param totRow
	 */
	public void setRowCount(long totRow)
	{
		this.rowCount = totRow;
		this.pageCount = (int)Math.ceil(this.rowCount / this.limit);
		this.start = (int)this.limit * (this.currentPage - 1);
	}

	
	
	/**
	 * 페이지 수 반환
	 * @return
	 */
	public int getPageCount() 
	{
		return pageCount;
	}
	
	
	
	/**
	 * 
	 * @param url 'http://myurl.com?page_number=' 의 형태
	 * @return 
	 */
	public String getPageList(String url)
	{
		StringBuffer page = new StringBuffer();
		int startPage = (( getCurrentPage() - 1 ) / 5 ) * 5 + 1;
		int endPage = startPage + 4;

		if( getCurrentPage() > 1 ) {
			page.append("<a href='" + url + "1'>◀</a>");
			page.append("<a href='" + url + (getCurrentPage()-1) + "'>◁</a>");
		}

		if( getPageCount() <= endPage ) {
			endPage = getPageCount();
		}
		if( startPage > 1 ) {
			page.append("<a href='" + url + (startPage-1) + "'>...</a>");
		}

		if( getPageCount() > 1 ) {
			for(int i = startPage; i <= endPage; i++) {
				if( getCurrentPage() == i) {
					page.append("<b> [" + i + "] </b>");
				}else {
					page.append("<a href='" + url + i + "'> [" + i + "] </a>");
				}
			}
		}
		
		if( getPageCount() > endPage ) {
			page.append("<a href=" + url + (endPage+1) + ">...</a>");
		}
		
		if( getCurrentPage() < getPageCount() ) {
			page.append("<a href='" + url + ( getCurrentPage()+1) + "'>▷</a>");
			page.append("<a href='" + url + getPageCount() + "'>▶</a>");
		}
		
		return page.toString();
	}
	
	
	public PageBean getPageBean()
	{
		return this;
	}
	

};


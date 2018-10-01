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
	 * ��������  �Խù� ��
	 */
	private double limit;

	/**
	 * �� ������ ��
	 */
	private int pageCount;

	/**
	 * ���� ������
	 */
	private int currentPage;

	/**
	 * �� �Խù� ��
	 */
	private long rowCount;
	
	/**
	 * ������ �ε����� ���� ��
	 */
	private int start;

	
	/**
	 * �⺻ ������ �������� �Խù� �� 10 ���������� ��ȣ�� 1�� ��ü�� ���� 
	 *
	 */
	public PageBean()
	{
		this.limit = 10;
		this.currentPage = 1;
	}
	
	
	
	/**
	 * �������� �Խù� �� ����
	 * @param limit
	 */
	public void setLimit(int limit)
	{
		this.limit = limit;
	}
	
	
	/**
	 * ���� ������ ����
	 * @param curr
	 */
	public void setCurrentPage(int curr)
	{
		this.currentPage = curr;
	}
	
	
	/**
	 * ���� ������ ��ȯ
	 * @return
	 */
	public int getCurrentPage()
	{
		return this.currentPage;
	}
	
	/**
	 * �� �Խù� �� ��ȯ
	 * @return
	 */
	public long getRowCount()
	{
		return this.rowCount;
	}
	
	
	/**
	 * ���۰� ��ȯ, ���� �������� 1�̸� 0�� ��ȯ�Ѵ�
	 * @return
	 */
	public int getStart()
	{
		return this.start;
	}
	
	/**
	 * �������� �Խù� �� ��ȯ
	 * @return
	 */
	public int getLimit()
	{
		return (int)this.limit;
	}
	
	
	/**
	 * �� �Խù� ����, �� ������ ���� ���۰��� ���� �ȴ�.
	 * @param totRow
	 */
	public void setRowCount(long totRow)
	{
		this.rowCount = totRow;
		this.pageCount = (int)Math.ceil(this.rowCount / this.limit);
		this.start = (int)this.limit * (this.currentPage - 1);
	}

	
	
	/**
	 * ������ �� ��ȯ
	 * @return
	 */
	public int getPageCount() 
	{
		return pageCount;
	}
	
	
	
	/**
	 * 
	 * @param url 'http://myurl.com?page_number=' �� ����
	 * @return 
	 */
	public String getPageList(String url)
	{
		StringBuffer page = new StringBuffer();
		int startPage = (( getCurrentPage() - 1 ) / 5 ) * 5 + 1;
		int endPage = startPage + 4;

		if( getCurrentPage() > 1 ) {
			page.append("<a href='" + url + "1'>��</a>");
			page.append("<a href='" + url + (getCurrentPage()-1) + "'>��</a>");
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
			page.append("<a href='" + url + ( getCurrentPage()+1) + "'>��</a>");
			page.append("<a href='" + url + getPageCount() + "'>��</a>");
		}
		
		return page.toString();
	}
	
	
	public PageBean getPageBean()
	{
		return this;
	}
	

};


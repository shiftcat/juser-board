package han.juser.controller.manager;

import han.juser.service.SummaryService;

public class MainItem 
{
	private SummaryService service;
	private String key;
	private int limit;
	
	
	
	
	public MainItem() {
		super();
		// TODO Auto-generated constructor stub
	}




	public MainItem(SummaryService service, String key, int limit) {
		super();
		this.service = service;
		this.key = key;
		this.limit = limit;
	}




	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}




	/**
	 * @param key the key to set
	 */
	public void setKey(String key) {
		this.key = key;
	}




	/**
	 * @return the limit
	 */
	public int getLimit() {
		return limit;
	}




	/**
	 * @param limit the limit to set
	 */
	public void setLimit(int limit) {
		this.limit = limit;
	}




	/**
	 * @return the service
	 */
	public SummaryService getService() {
		return service;
	}




	/**
	 * @param service the service to set
	 */
	public void setService(SummaryService service) {
		this.service = service;
	}


}

package han.juser.controller.manager;

import han.juser.service.SummaryService;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

public class MainManager 
{
	private List summary = new ArrayList();
	
	private MainItem items[];
	
	public MainManager()
	{
		super();
	}
	
	public void addSummary(SummaryService ss)
	{
		summary.add(ss);
	}
	
	
	public void setItems(MainItem item[])
	{
		items = item;
	}
	
	
	
	public void setMainItems(HttpServletRequest req)
	{

		for(int i = 0; i < items.length;i++ ) {
			SummaryService service = items[i].getService();
			String key = items[i].getKey();
			int limit = items[i].getLimit();
			List summary = service.getSummary(limit);
			req.setAttribute(key, summary);
			
		}
	}
}

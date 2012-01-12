package alpv.mwp.crawler;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

import alpv.mwp.Task;

public class CrawlerTask implements Task<HttpURL, List<String>> {

	private static final long serialVersionUID = 3659366838266519515L;
	private CrawlerJob _job;
	private Map<HttpURL, Boolean> _checkedURLs;

	public CrawlerTask(CrawlerJob job, Map<HttpURL, Boolean> checkedUrls) {
		_job = job;
		_checkedURLs = checkedUrls;
	}

	@Override
	public List<String> exec(HttpURL url) {
		System.out.println("Task started. Parsing url: " + url.getHost()
				+ url.getPath());
		if (!_checkedURLs.containsKey(url)) {
			URLParser parser = new URLParser(url);
			for (HttpURL newUrl : parser.get_urls()) {
				try {
					_job.getArgPool().put(newUrl);
					_checkedURLs.put(newUrl, true);
				} catch (RemoteException e) {
					e.printStackTrace();
				}
			}
			System.out.println("Task finished (" + url.getHost()
					+ url.getPath() + ")");
			return parser.get_mailTos();
		}else{
			System.out.println("url already checked");
			return null;
		}
	}
}
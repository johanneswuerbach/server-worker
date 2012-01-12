package alpv.mwp.crawler;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

import alpv.mwp.Task;

public class CrawlerTask implements Task<HttpURL, List<String>> {

	private static final long serialVersionUID = 3659366838266519515L;
	private CrawlerJob _job;
	private Map<String, Boolean> _checkedURLs;

	public CrawlerTask(CrawlerJob job, Map<String, Boolean> checkedUrls) {
		_job = job;
		_checkedURLs = checkedUrls;
	}

	@Override
	public List<String> exec(HttpURL url) {
		System.out.println("Task started. Parsing url: " + url.getHost()
				+ url.getPath());
			URLParser parser = new URLParser(url, _checkedURLs);
			for (HttpURL newUrl : parser.get_urls()) {
				try {
					_job.getArgPool().put(newUrl);
				} catch (RemoteException e) {
					e.printStackTrace();
				}
			}
			System.out.println("Task finished (" + url.getHost()
					+ url.getPath() + ")");
			return parser.get_mailTos();
	}
}
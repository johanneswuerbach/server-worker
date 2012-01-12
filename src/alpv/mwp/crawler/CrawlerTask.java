package alpv.mwp.crawler;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

import alpv.mwp.Task;

public class CrawlerTask implements Task<CrawlerArgument, List<String>> {

	private static final int MAX_DEEP = 2;
	private static final long serialVersionUID = 3659366838266519515L;
	private CrawlerJob _job;
	private Map<String, Boolean> _checkedURLs;

	public CrawlerTask(CrawlerJob job, Map<String, Boolean> checkedUrls) {
		_job = job;
		_checkedURLs = checkedUrls;
	}

	@Override
	public List<String> exec(CrawlerArgument url) {
		// Ignore poison
		if (url.isPoison()) {
			return null;
		}
		
		System.out.println("Task started. Parsing url: "
				+ url.getHttpUrl().getHost() + url.getHttpUrl().getPath()
				+ " Deep: " + url.getDeep());
		try {
			if (url.getDeep() <= MAX_DEEP) {
				URLParser parser = new URLParser(url.getHttpUrl(), _checkedURLs);
				for (HttpURL newUrl : parser.get_urls()) {
					_job.getArgPool().put(
							new CrawlerArgument(newUrl, url.getDeep() + 1,
									false));
				}
				System.out.println("Task finished ("
						+ url.getHttpUrl().getHost()
						+ url.getHttpUrl().getPath() + ")");
				return parser.get_mailTos();
			} else {
				_job.getArgPool().put(
						new CrawlerArgument(true));
				return null;
			}
		} catch (RemoteException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
package alpv.mwp.crawler;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

import alpv.mwp.Task;

public class CrawlerTask implements Task<CrawlerArgument, List<String>> {

	private static final long serialVersionUID = 3659366838266519515L;
	private CrawlerJob _job;
	private Map<String, Boolean> _checkedURLs;

	public CrawlerTask(CrawlerJob job, Map<String, Boolean> checkedUrls) {
		_job = job;
		_checkedURLs = checkedUrls;
	}

	@Override
	public List<String> exec(CrawlerArgument url) {
		System.out.println("Task started. Parsing url: "
				+ url.getHttpUrl().getHost() + url.getHttpUrl().getPath()
				+ " Deep: " + url.getDeep());
		try {
			if (url.getDeep() <= 1) {
				URLParser parser = new URLParser(url.getHttpUrl(), _checkedURLs);
				for (HttpURL newUrl : parser.get_urls()) {
					_job.getArgPool().put(new CrawlerArgument(newUrl, url.getDeep() + 1,false));
				}
				System.out.println("Task finished ("+ url.getHttpUrl().getHost()+ url.getHttpUrl().getPath() + ")");
				return parser.get_mailTos();
			} else {
				_job.getArgPool().put(new CrawlerArgument("http://foo.bar",url.getDeep() + 1, true));
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
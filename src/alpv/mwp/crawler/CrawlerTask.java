package alpv.mwp.crawler;

import java.rmi.RemoteException;
import java.util.List;

import alpv.mwp.Task;

public class CrawlerTask implements Task<HttpURL, List<String>> {

	private static final long serialVersionUID = 3659366838266519515L;
	private CrawlerJob _job;

	public CrawlerTask(CrawlerJob job) {
		_job = job;
	}

	@Override
	public List<String> exec(HttpURL a) {
		URLParser parser = new URLParser(a);
		for (HttpURL url : parser.get_urls()) {
			try {
				_job.getArgPool().put(url);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
		return parser.get_mailTos();
	}
}
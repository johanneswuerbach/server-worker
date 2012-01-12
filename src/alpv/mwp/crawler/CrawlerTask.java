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
		System.out.println("Task started. Parsing url: " + a.getHost() + a.getPath());
		URLParser parser = new URLParser(a);
		for (HttpURL url : parser.get_urls()) {
			try {
				_job.getArgPool().put(url);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
		System.out.println("Task finished (" + a.getHost() + a.getPath() + ")");
		return parser.get_mailTos();
	}
}
package alpv.mwp.crawler;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;

import alpv.mwp.Client;
import alpv.mwp.Job;
import alpv.mwp.RemoteFuture;

public class CrawlerClient extends Client {

	public CrawlerClient(String host, int port) throws RemoteException,
			NotBoundException {
		super(host, port);
	}

	public void execute() throws RemoteException {
		String url = "http://www.fu-berlin.de/einrichtungen";
		Job<HttpURL, List<String>, List<String>> job = new CrawlerJob(new HttpURLImpl(url));
		RemoteFuture<List<String>> remoteFuture = _server.doJob(job);
		remoteFuture.get();
	}
}

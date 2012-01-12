package alpv.mwp.crawler;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;

import alpv.mwp.Client;
import alpv.mwp.Job;
import alpv.mwp.RemoteFuture;

public final class CrawlerClient extends Client {
	
	public CrawlerClient(String host, int port) throws RemoteException,
			NotBoundException {
		super(host, port);
	}

	public void execute() throws RemoteException {
		try {
			String url = "http://www.fu-berlin.de/einrichtungen";
			Job<CrawlerArgument, List<String>, List<String>> job;
			job = new CrawlerJob(new CrawlerArgument(url, 0, false));
			RemoteFuture<List<String>> remoteFuture = _server.doJob(job);
			List<String> mails = remoteFuture.get();
			System.out.println("Number of unique mails: " + mails.size());
			for (String mail : mails) {
				System.out.println(mail);
			}
			System.exit(0);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}

package alpv.mwp.crawler;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

import alpv.mwp.Job;
import alpv.mwp.RemoteFuture;
import alpv.mwp.Server;

public class CrawlerClient {
	private Server _server;

	public CrawlerClient(String host, int port) throws RemoteException,
			NotBoundException {
		System.out.println("Client: looking for server " + host + ":" + port);
		Registry registry = LocateRegistry.getRegistry(host, port);
		_server = (Server) (registry.lookup("mwp"));
		System.out.println("Client: starting");
	}

	public void execute() throws RemoteException {
		String url = "http://www.fu-berlin.de/einrichtungen";
		Job<HttpURL, String, List<String>> job = new CrawlerJob(new HttpURLImpl(url));
		RemoteFuture<List<String>> remoteFuture = _server.doJob(job);
	}
}

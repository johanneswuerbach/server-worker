package alpv.mwp.testOne;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import alpv.mwp.Job;
import alpv.mwp.RemoteFuture;
import alpv.mwp.Server;

public class PowClient {
	private Server _server;

	public PowClient(String host, int port) throws RemoteException,
			NotBoundException {
		System.out.println("Client: looking for server " + host + ":" + port);
		Registry registry = LocateRegistry.getRegistry(host, port);
		_server = (Server) (registry.lookup("mwp"));
		System.out.println("Client: starting");
	}

	public void execute() {
		try {
			Integer[] numbers = { 2, 3, 4 };
			Job<Integer, Integer, Integer> job = new PowJob(numbers);

			RemoteFuture<Integer> remoteFuture = _server.doJob(job);
			System.out.println(remoteFuture.get());
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
}

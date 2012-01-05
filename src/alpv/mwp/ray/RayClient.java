package alpv.mwp.ray;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import alpv.mwp.Job;
import alpv.mwp.RemoteFuture;
import alpv.mwp.Server;

public class RayClient {
	private Server _server;

	public RayClient(String host, int port) throws RemoteException,
			NotBoundException {
		System.out.println("Client: looking for server " + host + ":" + port);
		Registry registry = LocateRegistry.getRegistry(host, port);
		_server = (Server) (registry.lookup("mwp"));
		System.out.println("Client: starting");
	}

	public void execute() {
		try {
			Job<Integer, RayResult, Boolean> job = new RayJob();

			RemoteFuture<Boolean> remoteFuture = _server.doJob(job);
			System.out.println(remoteFuture.get());
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
}

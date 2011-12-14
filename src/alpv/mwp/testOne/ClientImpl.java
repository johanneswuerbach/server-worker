package alpv.mwp.testOne;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;

import alpv.mwp.Job;
import alpv.mwp.Master;
import alpv.mwp.RemoteFuture;
import alpv.mwp.Server;

public class ClientImpl {
	private Server _server;

	public ClientImpl(String host, int port) throws RemoteException, NotBoundException {
		System.out.println("Client: looking for server "+host+":"+port);
		Registry registry = LocateRegistry.getRegistry(host, port);
		_server = (Server) (registry.lookup("mwp"));
		System.out.println("Client: starting");
	}
	
	public void execute(){
		Integer[] numbers = {2,3,4};
		Job<Integer, Integer, Integer> job = new PowJob(numbers);
		try {
			RemoteFuture<Integer> remoteFuture = _server.doJob(job);
			System.out.println(remoteFuture.get());
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	
}

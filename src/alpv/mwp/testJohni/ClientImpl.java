package alpv.mwp.testJohni;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;

import alpv.mwp.Job;
import alpv.mwp.JobImpl;
import alpv.mwp.RemoteFuture;
import alpv.mwp.Server;

public class ClientImpl {
	private Server _server;

	public ClientImpl(String host, int port) throws RemoteException, NotBoundException {
		Registry registry = LocateRegistry.getRegistry(host, port);
		_server = (Server) (registry.lookup("mwp"));
	}
	
	public void execute(){
		List<Integer> numbers = new ArrayList<Integer>();
		numbers.add(2);
		numbers.add(22);
		numbers.add(222);
		numbers.add(2222);
		JobImpl<List<Integer>,Integer,Integer> job = new JobAdd(numbers);
		try {
			RemoteFuture<Integer> remoteFuture = _server.doJob(job);
			System.out.println(remoteFuture.get());
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}

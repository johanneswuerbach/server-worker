package alpv.mwp.testOne;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import alpv.mwp.Client;
import alpv.mwp.Job;
import alpv.mwp.RemoteFuture;

public class PowClient extends Client {

	public PowClient(String host, int port) throws RemoteException,
			NotBoundException {
		super(host, port);
	}

	public void execute() throws RemoteException {
		Integer[] numbers = { 2, 3, 4 };
		Job<Integer, Integer, Integer> job = new PowJob(numbers);

		RemoteFuture<Integer> remoteFuture = _server.doJob(job);
		System.out.println(remoteFuture.get());
	}
}

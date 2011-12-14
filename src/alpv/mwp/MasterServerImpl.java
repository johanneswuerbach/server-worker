package alpv.mwp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;

public class MasterServerImpl implements Master, Server, Serializable {

	private static final long serialVersionUID = 5199119579575954593L;
	private final Registry _registry;
	private final int _numberOfWorkers;
	private static final String NAME = "mwp";
	private static List<Worker> _workers = new ArrayList<Worker>();

	public MasterServerImpl(int port, int numberOfWorkers)
			throws RemoteException {
		System.out.println("Server: init");
		_numberOfWorkers = numberOfWorkers;
		_registry = LocateRegistry.createRegistry(port);
		_registry.rebind(NAME, this);
		try {
			String address = (InetAddress.getLocalHost()).toString();
			System.out.println("Server running @ " + address + ":" + port);
		} catch (UnknownHostException e) {
			System.err.println("Can't determine adress.");
		}
		run();
	}

	private void run() {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		while (true) {
			System.out.println("running");
			try {
			String line = br.readLine();
			if (line.equals("q")) {
				System.out.println("bye");
				break;
			}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	@Override
	public void registerWorker(Worker w) throws RemoteException {
		System.out.println("Server: Worker registert");
		_workers.add(w);
	}

	@Override
	public void unregisterWorker(Worker w) throws RemoteException {
		System.out.println("Server: Worker unregistert");
		_workers.remove(w);
	}

	@Override
	public <Argument, Result, ReturnObject> RemoteFuture<ReturnObject> doJob(
			Job<Argument, Result, ReturnObject> job) throws RemoteException {
		PoolImpl<Argument> argumentPool = new PoolImpl<Argument>();
		job.split(argumentPool, _numberOfWorkers);
		PoolImpl<Result> resultPool = new PoolImpl<Result>();
		for (Worker w : _workers) {
			w.start(job.getTask(), argumentPool, resultPool);
		}
		return job.getFuture();
	}
}

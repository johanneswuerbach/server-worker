package alpv.mwp;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public abstract class Client {
	protected Server _server;

	public Client(String host, int port) throws RemoteException,
			NotBoundException {
		System.out.println("Client: looking for server " + host + ":" + port);
		Registry registry = LocateRegistry.getRegistry(host, port);
		_server = (Server) (registry.lookup("mwp"));
		System.out.println("Client: starting");
	}

	public abstract void execute() throws RemoteException;
}

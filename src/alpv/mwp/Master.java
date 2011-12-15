package alpv.mwp;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Master extends Remote {
	/**
	 * called by a worker to register itself to the master
	 * @param w
	 * @throws RemoteException
	 */
	public void registerWorker(Worker w) throws RemoteException;
	/**
	 * called by a worker to unregister itself or by the master to remove unresponsive worker
	 * @param w
	 * @throws RemoteException
	 */
	public void unregisterWorker(Worker w) throws RemoteException;
	
}

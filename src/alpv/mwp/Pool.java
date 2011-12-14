package alpv.mwp;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Pool<T> extends Remote {
	/**
	 * deposit t in the pool
	 * @param t
	 * @throws RemoteException
	 */
	void put(T t) throws RemoteException;
	/**
	 * retrieve the first element from the pool or null if there is none
	 * @return
	 * @throws RemoteException
	 */
	T get() throws RemoteException;
	/**
	 * returns the number of items in the pool
	 * @return
	 * @throws RemoteException
	 */
	int size() throws RemoteException;
}

package alpv.mwp.crawler;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * History interface
 */
public interface History<T> extends Remote {
	
	/**
	 * Insert an item into history
	 */
	public void put(T t) throws RemoteException;
	
	/**
	 * Is an item already in our history?
	 */
	public boolean containsKey(T t) throws RemoteException;
}

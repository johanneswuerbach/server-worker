package alpv.mwp;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemoteFuture<ReturnObject> extends Remote {
	/**
	 * this is a method used by the client to retrieve the calculated result. it
	 * has to block until a result is available
	 * 
	 * the client-implementation of this object can decide the syntax of this
	 * method (e.g. if it may be called only once or many times - and weather it
	 * returns 1 final result or partial results)
	 * 
	 * @return
	 * @throws RemoteException
	 */
	ReturnObject get() throws RemoteException;

}

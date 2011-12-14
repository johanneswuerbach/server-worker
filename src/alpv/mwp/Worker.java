package alpv.mwp;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Worker extends Remote {
	/**
	 * starts a new task on a worker; the method has to return as soon as
	 * possible - don't execute the task in here
	 * 
	 * @param t
	 * @param argpool
	 * @param respool
	 * @throws RemoteException
	 */
	<Argument, Result> void start(Task<Argument, Result> t,
			Pool<Argument> argpool, Pool<Result> respool)
			throws RemoteException;

}

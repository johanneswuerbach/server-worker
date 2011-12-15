package alpv.mwp;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Server extends Remote {
	/**
	 * this method is called by a client; it has to return a RemoteFuture
	 * (created by the job-object) as fast as possible
	 * 
	 * @param j
	 * @return
	 * @throws RemoteException
	 */
	<Argument, Result, ReturnObject> RemoteFuture<ReturnObject> doJob(
			Job<Argument, Result, ReturnObject> j) throws RemoteException;

}

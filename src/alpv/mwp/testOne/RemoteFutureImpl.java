package alpv.mwp.testOne;

import java.rmi.RemoteException;

import alpv.mwp.Job;
import alpv.mwp.RemoteFuture;

public class RemoteFutureImpl implements RemoteFuture<Integer>{

	private PowJob _job;

	public RemoteFutureImpl(PowJob jobAdd) {
		_job = jobAdd;
	}

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
	public Integer get() throws RemoteException {
		while(_job.getResult()==null){
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				//ignore 
			}
		}
		return _job.getResult();
	}
	
	
}

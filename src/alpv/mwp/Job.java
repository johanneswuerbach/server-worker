package alpv.mwp;

import java.io.Serializable;

public interface Job<Argument, Result, ReturnObject> extends Serializable {

	/**
	 * return a task passed to the workers by the master
	 * 
	 * @return
	 */
	Task<Argument, Result> getTask();

	/**
	 * returns the RemoteFuture Object, which should be returned to the client
	 * ATTENTION! First call the split() Method - the job-implementation may
	 * want to return a different RemoteFuture depending on the number of
	 * workers
	 * 
	 * @return
	 */
	RemoteFuture<ReturnObject> getFuture();

	/**
	 * splits the work into arguments and fills the argument-pool
	 * 
	 * @param argPool
	 * @param workerCount
	 */
	void split(Pool<Argument> argPool, int workerCount);

	/**
	 * merge is called right after the getFuture() method. this gives the
	 * job-implementation the opportunity to return chunk-results via the
	 * RemoteFuture-object or wait for the pool to fill up and then merge the
	 * results
	 * 
	 * @param resPool
	 */
	void merge(Pool<Result> resPool);

}

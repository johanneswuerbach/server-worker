package alpv.mwp;

import java.rmi.RemoteException;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * This is nothing else then a java.util.concurrent.ArrayBlockingQueue.
 * 
 */
public class PoolFinishedImpl<T> extends PoolImpl<T> {

	private int _workerCount;
	private AtomicInteger _workerFinished;
	
	public PoolFinishedImpl() {
		this(0);
	}
	
	public PoolFinishedImpl(int workerCount) {
		super();
		_workerCount = workerCount;
		_workerFinished = new AtomicInteger();
	}
	
	@Override
	public void put(T t) throws RemoteException {
		super.put(t);
		_workerFinished.set(0);
	}

	/**
	 * See {@link java.util.concurrent.ArrayBlockingQueue#poll()
	 * ArrayBlockingQueue.poll()}
	 * Count hits if empty
	 */
	public T get() throws RemoteException {
		T result = super.get();
		if(result == null) {
			_workerFinished.incrementAndGet();
		}
		return result;
	}

	/**
	 * See {@link java.util.concurrent.ArrayBlockingQueue#size()
	 * ArrayBlockingQueue.size()}
	 * Returns -1 if all workers are finished
	 */
	public int size() throws RemoteException {
		if(_workerFinished != null && _workerFinished.get() >= _workerCount) {
			return -1;
		}
		return super.size();
	}

}

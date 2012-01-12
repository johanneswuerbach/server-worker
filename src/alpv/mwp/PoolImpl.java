package alpv.mwp;

import java.rmi.RemoteException;
import java.util.ArrayDeque;

/**
 * This is nothing else then a java.util.concurrent.ArrayBlockingQueue.
 * 
 */
public class PoolImpl<T> implements Pool<T> {

	protected final ArrayDeque<T> _queue;
	
	public PoolImpl() {
		_queue = new ArrayDeque<T>();
	}

	/**
	 * See {@link java.util.concurrent.ArrayBlockingQueue#put(T)
	 * ArrayBlockingQueue.put(T)}
	 */
	public synchronized void put(T t) throws RemoteException {
		_queue.add(t);
	}

	/**
	 * See {@link java.util.concurrent.ArrayBlockingQueue#poll()
	 * ArrayBlockingQueue.poll()}
	 */
	public synchronized T get() throws RemoteException {
		T result = _queue.poll();
		return result;
	}

	/**
	 * See {@link java.util.concurrent.ArrayBlockingQueue#size()
	 * ArrayBlockingQueue.size()}
	 * Returns -1 if all workers are finished
	 */
	public synchronized int size() throws RemoteException {
		return _queue.size();
	}

}

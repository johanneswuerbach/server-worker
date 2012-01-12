package alpv.mwp;

import java.rmi.RemoteException;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * This is nothing else then a java.util.concurrent.ArrayBlockingQueue.
 * 
 */
public class PoolImpl<T> implements Pool<T> {

	protected final ArrayBlockingQueue<T> _queue;
	protected final static int CAPACITY = 2000;
	
	public PoolImpl() {
		_queue = new ArrayBlockingQueue<T>(CAPACITY);
	}

	/**
	 * See {@link java.util.concurrent.ArrayBlockingQueue#put(T)
	 * ArrayBlockingQueue.put(T)}
	 */
	public void put(T t) throws RemoteException {
		try {
			_queue.put(t);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * See {@link java.util.concurrent.ArrayBlockingQueue#poll()
	 * ArrayBlockingQueue.poll()}
	 */
	public T get() throws RemoteException {
		T result = _queue.poll();
		return result;
	}

	/**
	 * See {@link java.util.concurrent.ArrayBlockingQueue#size()
	 * ArrayBlockingQueue.size()}
	 * Returns -1 if all workers are finished
	 */
	public int size() throws RemoteException {
		return _queue.size();
	}

}

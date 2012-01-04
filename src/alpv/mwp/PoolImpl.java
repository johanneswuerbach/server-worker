package alpv.mwp;

import java.rmi.RemoteException;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * This is nothing else then a java.util.concurrent.ArrayBlockingQueue.
 * 
 */
public class PoolImpl<T> implements Pool<T> {

	private final ArrayBlockingQueue<T> _queue;
	private final static int CAPACITY = 200;

	public PoolImpl() {
		super();
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
		return _queue.poll();
	}

	/**
	 * See {@link java.util.concurrent.ArrayBlockingQueue#size()
	 * ArrayBlockingQueue.size()}
	 */
	public int size() throws RemoteException {
		return _queue.size();
	}

}

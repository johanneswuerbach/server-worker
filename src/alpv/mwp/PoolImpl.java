package alpv.mwp;

import java.rmi.RemoteException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * This is nothing else then a java.util.concurrent.ArrayBlockingQueue.
 * 
 */
public class PoolImpl<T> implements Pool<T> {

	private final ArrayBlockingQueue<T> _queue;
	private final static int CAPACITY = 200;
	private int _workerCount;
	private AtomicInteger _workerFinished;

	public PoolImpl(int workerCount) {
		super();
		_workerCount = workerCount;
		_workerFinished = new AtomicInteger();
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
		if(result == null) {
			_workerFinished.incrementAndGet();
		}
		return result;
	}

	/**
	 * See {@link java.util.concurrent.ArrayBlockingQueue#size()
	 * ArrayBlockingQueue.size()}
	 */
	public int size() throws RemoteException {
		System.out.println("Finished: " + _workerFinished.get() + "/" + _workerCount);
		if(_workerFinished.get() >= _workerCount) {
			return -1;
		}
		return _queue.size();
	}

}

package alpv.mwp;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

/**
 * This is nothing else then a java.util.concurrent.ArrayBlockingQueue.
 * 
 */
public class PoolFinishedImpl<T> extends PoolImpl<T> {

	private static final long serialVersionUID = 661421034960737172L;
	private int _workerCount;
	private int _workerFinished;
	private int _poisonCount;

	public PoolFinishedImpl()  throws RemoteException {
		this(new ArrayList<Worker>());
	}

	public PoolFinishedImpl(List<Worker> _workers) throws RemoteException {
		super();
		_workerCount = _workers.size();
		_workerFinished = 0;
		_poisonCount = 0;
	}

	@Override
	public synchronized void put(T t) throws RemoteException {
		_workerFinished = 0;
		if (t instanceof Poison && ((Poison) t).isPoison()) {
			_poisonCount++;
		}
		super.put(t);
	}

	/**
	 * See {@link java.util.concurrent.ArrayBlockingQueue#poll()
	 * ArrayBlockingQueue.poll()} Count hits if empty
	 */
	public synchronized T get() throws RemoteException {
		T result = super.get();
		if (result == null) {
			_workerFinished++;
		}
		return result;
	}

	/**
	 * See {@link java.util.concurrent.ArrayBlockingQueue#size()
	 * ArrayBlockingQueue.size()} Returns -1 if all workers are finished
	 */
	public synchronized int size() throws RemoteException {
		if (_workerFinished >= _workerCount || _poisonCount >= _workerCount) {
			return -1;
		}
		return super.size();
	}

}

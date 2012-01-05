package alpv.mwp.testOne;

import java.rmi.RemoteException;

import alpv.mwp.Job;
import alpv.mwp.Pool;
import alpv.mwp.Task;

public class PowJob implements Job<Integer, Integer, Integer> {

	private static final long serialVersionUID = 267504255130640656L;
	private Integer[] _numbers;
	protected PowTask _task;
	protected Integer _argument;
	protected PowRemoteFuture _remoteFuture;
	public PowJob(Integer[] numbers) throws RemoteException {
		_task = new PowTask();
		_numbers = numbers;
		_remoteFuture = new PowRemoteFuture();
	}

	@Override
	public void split(Pool<Integer> argPool, int workerCount) {
		for (Integer number : _numbers) {
			try {
				argPool.put(number);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}
	
	public PowRemoteFuture getFuture() {
		return _remoteFuture;
	}

	public Task<Integer, Integer> getTask() {
		return _task;
	}

	@Override
	public void merge(Pool<Integer> resPool) {
		Integer result = 0;
		try {
			Integer i;
			while ((i = resPool.get()) != null) {
				result += i;
			}
			_remoteFuture.set(result);
		} catch (RemoteException e) {
			// ignore
		}
		
	}
}

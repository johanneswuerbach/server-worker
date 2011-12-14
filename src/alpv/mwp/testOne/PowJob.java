package alpv.mwp.testOne;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import alpv.mwp.Job;
import alpv.mwp.Pool;
import alpv.mwp.RemoteFuture;
import alpv.mwp.Task;

public class PowJob implements Job<Integer, Integer, Integer> {

	private static final long serialVersionUID = 267504255130640656L;
	private PowTask _task;
	private Integer[] _numbers;
	RemoteFuture<Integer> _remoteFuture;
	private Integer _result;

	public PowJob(Integer[] numbers) {
		_task = new PowTask();
		_numbers = numbers;
		_remoteFuture = new RemoteFutureImpl(this);
	}

	public PowJob(int i, int j, int k) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public PowTask getTask() {
		return _task;
	}

	@Override
	public RemoteFuture<Integer> getFuture() {
		return _remoteFuture;
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

	@Override
	public void merge(Pool<Integer> resPool) {
		try {
			Integer sum = 0;
			Integer i;
			while ((i = resPool.get()) != null) {
				sum += i;
			}
			_result = sum;
		} catch (RemoteException e) {
			// ignore
		}
	}

	public Integer getResult() {
		return _result;
	}

}

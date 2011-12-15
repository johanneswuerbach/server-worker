package alpv.mwp.testOne;

import java.rmi.RemoteException;

import alpv.mwp.JobImpl;
import alpv.mwp.Pool;

public class PowJob extends JobImpl<Integer, Integer, Integer> {

	private static final long serialVersionUID = 267504255130640656L;
	private Integer[] _numbers;

	public PowJob(Integer[] numbers) throws RemoteException {
		super();
		_task = new PowTask();
		_numbers = numbers;
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
		Integer result = 0;
		try {
			Integer i;
			while ((i = resPool.get()) != null) {
				result += i;
			}
		} catch (RemoteException e) {
			// ignore
		}
		_remoteFuture.setReturnObject(result);
	}
}

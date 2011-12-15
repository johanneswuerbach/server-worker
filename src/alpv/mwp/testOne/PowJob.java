package alpv.mwp.testOne;

import java.rmi.RemoteException;

import alpv.mwp.JobImpl;
import alpv.mwp.Pool;


public class PowJob extends JobImpl<Integer, Integer, Integer> {

	private static final long serialVersionUID = 267504255130640656L;
	private Integer[] _numbers;

	public PowJob(Integer[] numbers) {
		super();
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
		_remoteFuture.setReturnObject(_result);
	}
}

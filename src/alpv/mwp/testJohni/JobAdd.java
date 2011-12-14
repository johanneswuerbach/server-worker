package alpv.mwp.testJohni;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import alpv.mwp.Job;
import alpv.mwp.Pool;
import alpv.mwp.RemoteFuture;
import alpv.mwp.Task;

public class JobAdd implements Job<List<Integer>, Integer, Integer> {

	private static final long serialVersionUID = 267504255130640656L;
	private TaskAdd _task;
	private List<Integer> _numbers;
	RemoteFuture<Integer> _remoteFuture;
	private Integer _result;

	public JobAdd(List<Integer> numbers) {
		_task = new TaskAdd();
		_numbers = numbers;
		_remoteFuture = new RemoteFutureImpl(this);
	}

	@Override
	public TaskAdd getTask() {
		return _task;
	}

	@Override
	public RemoteFuture<Integer> getFuture() {
		return _remoteFuture;
	}

	@Override
	public void split(Pool<List<Integer>> argPool, int workerCount) {

		ArrayList<ArrayList<Integer>> pseudoPool = new ArrayList<ArrayList<Integer>>();
		for (int i = 0; i < workerCount; i++) {
			pseudoPool.add(new ArrayList<Integer>());
		}

		for (int i = 0; i < _numbers.size(); i++) {
			pseudoPool.get(i % workerCount).add(_numbers.get(i));
		}

		for (ArrayList<Integer> list : pseudoPool) {
			try {
				argPool.put(list);
			} catch (RemoteException e) {
				// nop
			}
		}
	}

	@Override
	public void merge(Pool<Integer> resPool) {
		try{
			Integer sum = 0;
			Integer i;
			while((i = resPool.get()) != null){
				sum += i;
			}
			_result = sum;
		}
		catch (RemoteException e){
			//ignore
		}
	}
	
	public Integer getResult(){
		return _result;
	}
}

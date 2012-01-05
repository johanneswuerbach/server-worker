package alpv.mwp.ray;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.rmi.RemoteException;

import java.util.PriorityQueue;

import alpv.mwp.Job;
import alpv.mwp.Pool;

public class RayJob implements Job<Integer, RayResult, RayComplete> {

	private static final long serialVersionUID = 5288048324055927759L;
	public static final int THREAD_NUMBER_OF_LINES = 40;
	public static final int HEIGHT = 800; // see Example
	public static final int WIDTH = 800; // see Example
	private RayTask _task;
	private RayRemoteFuture _remoteFuture;
	private boolean _merging = false;

	public RayJob() throws RemoteException {
		_task = new RayTask();
	}

	@Override
	public void split(Pool<Integer> argPool, int workerCount) {
		for (int i = 0; i < HEIGHT; i += THREAD_NUMBER_OF_LINES) {
			try {
				argPool.put(i);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}

		Thread collector = new Thread(new Runnable() {
			public void run() {
				while (!_merging) {
					// _remoteFuture.set(collect(_tempPool, false));
				}
			}
		});
		collector.start();
	}

	@Override
	public void merge(Pool<RayResult> resPool) {
		_merging = true;
		_remoteFuture.set(collect(resPool, true));
	}

	private RayComplete collect(Pool<RayResult> resPool, boolean isFinished) {

		ByteArrayOutputStream baos = null;

		try {
			// collect files
			baos = new ByteArrayOutputStream();

			// write data parts
			RayResult result;
			PriorityQueue<RayResult> results = new PriorityQueue<RayResult>(
					resPool.size());
			while ((result = resPool.get()) != null) {
				results.add(result);
			}
			while ((result = results.poll()) != null) {
				ByteArrayOutputStream outputStream = result.getStream();
				outputStream.writeTo(baos);
				outputStream.flush();
			}

		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return new RayComplete(baos, isFinished);
	}

	public RayRemoteFuture getFuture() {
		if(_remoteFuture == null) {
			try {
				_remoteFuture = new RayRemoteFuture();
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
		return _remoteFuture;
	}

	public RayTask getTask() {
		return _task;
	}

}

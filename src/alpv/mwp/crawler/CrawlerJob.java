package alpv.mwp.crawler;

import java.rmi.RemoteException;

import java.util.HashMap;
import java.util.List;

import alpv.mwp.Job;
import alpv.mwp.Pool;
import alpv.mwp.RemoteFuture;
import alpv.mwp.Task;
import alpv.mwp.ray.RayRemoteFuture;

public class CrawlerJob implements Job<HttpURL, List<String>, List<String>> {

	private static final long serialVersionUID = 3529581498823216957L;
	private HttpURLImpl _url;
	private Pool<HttpURL> _argPool;
	private CrawlerRemoteFuture _remoteFuture;
	private Task<HttpURL, List<String>> _task;

	public CrawlerJob(HttpURLImpl httpURLImpl) {
		_url = httpURLImpl;
		_task = new CrawlerTask(this, new HashMap<String, Boolean>());
	}

	@Override
	public Task<HttpURL, List<String>> getTask() {
		return _task;
	}

	@Override
	public RemoteFuture<List<String>> getFuture() {
		if (_remoteFuture == null) {
			try {
				_remoteFuture = new CrawlerRemoteFuture();
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
		return _remoteFuture;
	}

	@Override
	public void split(Pool<HttpURL> argPool, int workerCount) {
		try {
			_argPool = argPool; // remember the argPool to enable the tasks to put interim results into the argPool
			_argPool.put(_url); // put the first url
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void merge(Pool<List<String>> resPool) {
		// TODO Auto-generated method stub

	}
	
	public Pool<HttpURL> getArgPool() {
		return _argPool;
	}

	public boolean alreadyCheckedURLs(HttpURL url) {
		// TODO Auto-generated method stub
		return false;
	}

}

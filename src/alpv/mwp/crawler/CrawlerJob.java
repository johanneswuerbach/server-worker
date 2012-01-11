package alpv.mwp.crawler;

import java.rmi.RemoteException;

import java.util.List;

import alpv.mwp.Job;
import alpv.mwp.Pool;
import alpv.mwp.PoolImpl;
import alpv.mwp.RemoteFuture;
import alpv.mwp.Task;
import alpv.mwp.ray.RayResult;

public class CrawlerJob implements Job<HttpURL, String, List<String>> {

	private static final long serialVersionUID = 3529581498823216957L;
	private HttpURLImpl _url;
	private Pool<HttpURL> _argPool;

	public CrawlerJob(HttpURLImpl httpURLImpl) {
		_url = httpURLImpl;
	}

	@Override
	public Task<HttpURL, String> getTask() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RemoteFuture<List<String>> getFuture() {
		// TODO Auto-generated method stub
		return null;
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
	public void merge(Pool<String> resPool) {
		// TODO Auto-generated method stub

	}
	
	public Pool<HttpURL> getArgPool() {
		return _argPool;
	}

}

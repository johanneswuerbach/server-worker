package alpv.mwp.crawler;

import java.rmi.RemoteException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import alpv.mwp.Job;
import alpv.mwp.Pool;
import alpv.mwp.RemoteFuture;
import alpv.mwp.Task;
import alpv.mwp.ray.RayRemoteFuture;

public class CrawlerJob implements
		Job<CrawlerArgument, List<String>, List<String>> {

	private static final long serialVersionUID = 3529581498823216957L;
	private CrawlerArgument _url;
	private Pool<CrawlerArgument> _argPool;
	private CrawlerRemoteFuture _remoteFuture;
	private Task<CrawlerArgument, List<String>> _task;

	public CrawlerJob(CrawlerArgument httpURLImpl) {
		_url = httpURLImpl;
		_task = new CrawlerTask(this, new HashMap<String, Boolean>());
	}

	@Override
	public Task<CrawlerArgument, List<String>> getTask() {
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
	public void split(Pool<CrawlerArgument> argPool, int workerCount) {
		try {
			_argPool = argPool; // remember the argPool to enable the tasks to
								// put interim results into the argPool
			_argPool.put(_url); // put the first url
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void merge(Pool<List<String>> resPool) {
		List<String> result = new ArrayList<String>();
		try {
			for (String s : resPool.get()) {
				if(!result.contains(s)){
					System.out.println(s);
					result.add(s);
				}
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		_remoteFuture.set(result);
	}

	public Pool<CrawlerArgument> getArgPool() {
		return _argPool;
	}

	public boolean alreadyCheckedURLs(HttpURL url) {
		// TODO Auto-generated method stub
		return false;
	}

}

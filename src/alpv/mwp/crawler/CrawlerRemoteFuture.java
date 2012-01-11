package alpv.mwp.crawler;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import alpv.mwp.RemoteFuture;

public class CrawlerRemoteFuture extends UnicastRemoteObject implements
		RemoteFuture<List<String>>, Serializable {

	private static final long serialVersionUID = -3480527838775572272L;
	List<String> _returnObject;

	public CrawlerRemoteFuture() throws RemoteException {
		_returnObject = null;
	}

	public List<String> get() throws RemoteException {
		while (_returnObject == null) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// ignore
			}
		}
		return _returnObject;
	}

	public void set(List<String> returnObject) {
		_returnObject = returnObject;
	}

}

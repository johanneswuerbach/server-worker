package alpv.mwp.crawler;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;

/**
 * Synchronized and shared URL history
 */
public class CrawlerHistory extends UnicastRemoteObject implements History<String> {

	private static final long serialVersionUID = 346237642520015853L;
	
	private Map<String, Boolean> _map;

	public CrawlerHistory() throws RemoteException {
		super();
		_map = new HashMap<String, Boolean>();
	}
	
	public synchronized void put(String url) {
		_map.put(url, null);
	}
	
	public synchronized boolean containsKey(String url) {
		return _map.containsKey(url);
	}
}

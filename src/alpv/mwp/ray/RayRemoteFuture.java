package alpv.mwp.ray;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import alpv.mwp.RemoteFuture;

public class RayRemoteFuture extends UnicastRemoteObject implements
		RemoteFuture<RayComplete>, Serializable {

	private static final long serialVersionUID = -3480527838775572272L;
	RayComplete _returnObject;
	RayComplete _alreadyReturned;
	boolean _isCompleted;

	public RayRemoteFuture() throws RemoteException {
		_returnObject = null;
	}

	public RayComplete get() throws RemoteException {
		while (_returnObject == null || _returnObject == _alreadyReturned) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// ignore
			}
		}
		_alreadyReturned = _returnObject;
		return _alreadyReturned;
	}

	public void set(RayComplete returnObject) {
		_returnObject = returnObject;
	}

}

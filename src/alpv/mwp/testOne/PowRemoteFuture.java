package alpv.mwp.testOne;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import alpv.mwp.RemoteFuture;

public class PowRemoteFuture extends UnicastRemoteObject implements
		RemoteFuture<Integer>, Serializable {

	private static final long serialVersionUID = -3480527838775572272L;
	Integer _returnObject;
	boolean _ready;

	public PowRemoteFuture() throws RemoteException {
		_returnObject = null;
		_ready = false;
	}

	public Integer get() throws RemoteException {
		while (!_ready) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// ignore
			}
		}
		return _returnObject;
	}

	public void set(Integer returnObject) {
		_returnObject = returnObject;
		_ready = true;
	}

}

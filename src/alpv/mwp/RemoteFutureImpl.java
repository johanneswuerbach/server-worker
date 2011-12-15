package alpv.mwp;

import java.rmi.RemoteException;

public class RemoteFutureImpl<ReturnObject> implements RemoteFuture<ReturnObject>{
	
	ReturnObject _returnObject;
	boolean _ready;
	
	public RemoteFutureImpl(){
		_returnObject = null;
		_ready = false;
	}

	public ReturnObject get() throws RemoteException {
		while(!_ready){
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				//ignore 
			}
		}
		return _returnObject;
	}
	
	public void setReturnObject(ReturnObject returnObject){
		_returnObject = returnObject;
		_ready = true;
	}

}

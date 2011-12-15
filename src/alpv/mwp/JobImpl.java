package alpv.mwp;

public abstract class JobImpl<Argument, Result, ReturnObject> implements
		Job<Argument, Result, ReturnObject> {

	private static final long serialVersionUID = -8808992006129484149L;
	protected Task<Argument, Result> _task;
	protected Argument _argument;
	protected RemoteFutureImpl<ReturnObject> _remoteFuture;

	public RemoteFuture<ReturnObject> getFuture() {
		if (_remoteFuture == null) {
			_remoteFuture = new RemoteFutureImpl<ReturnObject>();
		}
		return _remoteFuture;
	}

	public Task<Argument, Result> getTask() {
		return _task;
	}
}

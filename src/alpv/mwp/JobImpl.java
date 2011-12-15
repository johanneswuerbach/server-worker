package alpv.mwp;

public abstract class JobImpl<Argument, Result, ReturnObject> implements
		Job<Argument, Result, ReturnObject> {

	private static final long serialVersionUID = -8808992006129484149L;
	protected Task<Argument, Result> _task;
	protected Argument _argument;
	protected RemoteFutureImpl<ReturnObject> _remoteFuture;

	public JobImpl() {
		_remoteFuture = new RemoteFutureImpl<ReturnObject>();
	}

	public RemoteFuture<ReturnObject> getFuture() {
		return _remoteFuture;
	}

	public Task<Argument, Result> getTask() {
		return _task;
	}
}

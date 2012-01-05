package alpv.mwp.ray;

import java.io.ByteArrayOutputStream;
import java.rmi.RemoteException;

import alpv.mwp.Task;

public class RayTask implements Task<Integer, RayResult> {

	private static final long serialVersionUID = 3659366838266519515L;
	private RayJob _rayJob;

	public RayTask(RayJob rayJob) {
		_rayJob = rayJob;
	}

	@Override
	public RayResult exec(final Integer lineNumber) {
		ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();
        final RendererFrontend rendfe = new RendererFrontend(byteOutputStream);
        rendfe.setWindowStrip(lineNumber,lineNumber+RayJob.THREAD_NUMBER_OF_LINES);
        rendfe.render();
        RayResult result = new RayResult(lineNumber, byteOutputStream);
        try {
        	_rayJob.getTempPool().put(result);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return result;
	}
}

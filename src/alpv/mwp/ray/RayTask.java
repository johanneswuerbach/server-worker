package alpv.mwp.ray;

import java.io.ByteArrayOutputStream;

import alpv.mwp.Task;

public class RayTask implements Task<Integer, ByteArrayOutputStream> {

	private static final long serialVersionUID = 3659366838266519515L;

	@Override
	public ByteArrayOutputStream exec(final Integer lineNumber) {
		ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();
        final RendererFrontend rendfe = new RendererFrontend(byteOutputStream);
        rendfe.setWindowStrip(lineNumber,lineNumber+RayJob.THREAD_NUMBER_OF_LINES);
        rendfe.render();
		return byteOutputStream;
	}
}

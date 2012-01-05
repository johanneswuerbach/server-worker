package alpv.mwp.ray;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;

/**
 * Transport an array of bytes and status of rendering
 */
public class RayComplete implements Serializable {

	private static final long serialVersionUID = -2654738529916453195L;
	private final boolean _finished;
	private final byte[] _stream;

	public RayComplete(ByteArrayOutputStream stream, boolean finished) {
		_finished = finished;
		_stream = stream.toByteArray();
	}

	public ByteArrayOutputStream getStream() {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			baos.write(_stream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return baos;
	}

	public boolean isFinished() {
		return _finished;
	}

}

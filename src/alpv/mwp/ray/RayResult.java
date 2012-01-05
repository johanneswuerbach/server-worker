package alpv.mwp.ray;

import java.io.ByteArrayOutputStream;

/**
 * Transport current bytestream and number of line
 */
public class RayResult implements Comparable<RayResult> {

	private final int _lineNumber;
	private final ByteArrayOutputStream _stream;

	public RayResult(int lineNumber, ByteArrayOutputStream stream) {
		_lineNumber = lineNumber;
		_stream = stream;
	}

	public int getLineNumber() {
		return _lineNumber;
	}

	public ByteArrayOutputStream getStream() {
		return _stream;
	}

	@Override
	public int compareTo(RayResult paramT) {
		Integer i = _lineNumber;
		return i.compareTo(paramT.getLineNumber()) * (-1);
	}

}

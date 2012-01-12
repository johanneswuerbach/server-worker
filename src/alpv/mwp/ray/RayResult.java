package alpv.mwp.ray;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;

/**
 * Transport current bytestream and number of line
 */
public class RayResult implements Comparable<RayResult>, Serializable {

	private static final long serialVersionUID = -109664551400514658L;
	private final int _lineNumber;
	private final byte[] _bytes;

	public RayResult(int lineNumber, ByteArrayOutputStream stream) {
		_lineNumber = lineNumber;
		_bytes = stream.toByteArray();
	}

	public int getLineNumber() {
		return _lineNumber;
	}

	public byte[] getBytes() {
		return _bytes;
	}

	@Override
	public int compareTo(RayResult paramT) {
		Integer i = _lineNumber;
		return i.compareTo(paramT.getLineNumber()) * (-1);
	}

}

package alpv.mwp.ray;

import java.io.ByteArrayOutputStream;

public class RayResult implements Comparable<RayResult>{
	
	private int lineNumber;
	private ByteArrayOutputStream stream;
	
	public RayResult(int lineNumber, ByteArrayOutputStream stream){
		this.lineNumber = lineNumber;
		this.stream = stream;
	}

	public int getLineNumber() {
		return lineNumber;
	}

	public void setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
	}

	public ByteArrayOutputStream getStream() {
		return stream;
	}

	public void setStream(ByteArrayOutputStream stream) {
		this.stream = stream;
	}

	@Override
	public int compareTo(RayResult paramT) {
		Integer i = lineNumber;
		return i.compareTo(paramT.getLineNumber()) * (-1);
	} 

}

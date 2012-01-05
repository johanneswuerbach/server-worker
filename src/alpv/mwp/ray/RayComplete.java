package alpv.mwp.ray;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;

public class RayComplete implements Serializable {
	
	private static final long serialVersionUID = -2654738529916453195L;
	private boolean finished;
	private byte[] stream;
	
	public RayComplete(ByteArrayOutputStream stream, boolean finished){
		this.finished = finished;
		this.stream = stream.toByteArray();
	}

	public ByteArrayOutputStream getStream() {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			baos.write(stream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return baos;
	}
	
	public boolean isFinished() {
		return finished;
	}

}

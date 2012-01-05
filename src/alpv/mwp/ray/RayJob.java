package alpv.mwp.ray;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

import alpv.mwp.JobImpl;
import alpv.mwp.Pool;

public class RayJob extends JobImpl<Integer, RayResult, Boolean> {

	private static final long serialVersionUID = 5288048324055927759L;
	public static final int THREAD_NUMBER_OF_LINES = 40;
	private static final int HEIGHT = 800; // see Example
	private static final int WIDTH = 800; // see Example
	
	public RayJob(){
		_task = new RayTask();
	}

	@Override
	public void split(Pool<Integer> argPool, int workerCount) {
		for (int i = 0; i < HEIGHT; i += THREAD_NUMBER_OF_LINES) {
			try {
				argPool.put(i);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void merge(Pool<RayResult> resPool) {
		try {
			// temporary File for the result picture
			File outF = File.createTempFile("alpiv", ".pix");
			OutputStream outs = new FileOutputStream(outF);

			writeHeader(outs);

			// write data parts
			RayResult result;
			PriorityQueue<RayResult> results = new PriorityQueue<RayResult>(resPool.size());
			while ((result = resPool.get()) != null) {
				results.add(result);
			}
			while((result = results.poll()) != null){
				ByteArrayOutputStream outputStream = result.getStream();
				outputStream.writeTo(outs);
				outputStream.flush();
			}

			// done with writing
			outs.close();

			showPicture(outF);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		_remoteFuture.setReturnObject(true);
	}

	private void writeHeader(OutputStream outs) throws IOException {
		String hdr = "RGB\n" + WIDTH + " " + HEIGHT + " 8 8 8\n";
		BufferedWriter wOut = new BufferedWriter(new OutputStreamWriter(
				outs));
		wOut.write(hdr, 0, hdr.length());
		wOut.flush();
	}

	private void showPicture(File outF) {
		try {
			// rendfe1.showPicture(outF.getCanonicalPath());
			GUI.display(outF.getCanonicalPath());
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		outF.delete();
	}
}

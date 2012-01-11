package alpv.mwp.ray;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import alpv.mwp.Client;
import alpv.mwp.Job;
import alpv.mwp.RemoteFuture;

public class RayClient extends Client {

	public RayClient(String host, int port) throws RemoteException,
			NotBoundException {
		super(host, port);
	}

	public void execute() throws RemoteException {
		Job<Integer, RayResult, RayComplete> job = new RayJob();
		RemoteFuture<RayComplete> remoteFuture = _server.doJob(job);
		showPicture(remoteFuture);
	}

	private void showPicture(RemoteFuture<RayComplete> remoteFuture) {

		try {
			RayComplete complete;

			File outF = File.createTempFile("alpiv", ".pix");
			String hdr = "RGB\n" + RayJob.WIDTH + " " + RayJob.HEIGHT
					+ " 8 8 8\n";

			do {
				// Receive parts until rendering finished
				complete = remoteFuture.get();

				System.out.println("Received new part.");

				OutputStream outs = new FileOutputStream(outF);

				// write header
				BufferedWriter wOut = new BufferedWriter(
						new OutputStreamWriter(outs));
				wOut.write(hdr, 0, hdr.length());
				wOut.flush();

				// write strips
				complete.getStream().writeTo(outs);
				complete.getStream().flush();

				// done with writing
				outs.close();

				GUI.display(outF.getCanonicalPath());
				Thread.sleep(100);

			} while (!complete.isFinished());

		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

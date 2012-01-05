package alpv.mwp.ray;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import alpv.mwp.Job;
import alpv.mwp.RemoteFuture;
import alpv.mwp.Server;

public class RayClient {
	private Server _server;

	public RayClient(String host, int port) throws RemoteException,
			NotBoundException {
		System.out.println("Client: looking for server " + host + ":" + port);
		Registry registry = LocateRegistry.getRegistry(host, port);
		_server = (Server) (registry.lookup("mwp"));
		System.out.println("Client: starting");
	}

	public void execute() {
		try {
			Job<Integer, RayResult, RayComplete> job = new RayJob();

			RemoteFuture<RayComplete> remoteFuture = _server.doJob(job);

			showPicture(remoteFuture);

		} catch (RemoteException e) {
			e.printStackTrace();
		}
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

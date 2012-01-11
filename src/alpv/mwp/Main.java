package alpv.mwp;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import alpv.mwp.crawler.CrawlerClient;
import alpv.mwp.ray.RayClient;

public class Main {
	private static final String USAGE = String
			.format("usage: java -jar UB%%X_%%NAMEN server PORT NUMBER_OF_WORKERS%n"
					+ "         (to start a server)%n"
					+ "or:    java -jar UB%%X_%%NAMEN client SERVERIPADDRESS SERVERPORT%n"
					+ "         (to start a client)%n"
					+ "or:    java -jar UB%%X_%%NAMEN worker SERVERIPADDRESS SERVERPORT%n"
					+ "         (to start a worker)");

	/**
	 * Starts a server/client according to the given arguments.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		try {
			int i = 0;
			if (args.length == 0) {
				startAll();
			}else if (args[i].equals("server")) {
				new MasterServerImpl(Integer.parseInt(args[++i]));
			} else if (args[i].equals("client")) {
				CrawlerClient client = new CrawlerClient(args[++i],
						Integer.parseInt(args[++i]));
				client.execute();
			} else if (args[i].equals("worker")) {
				WorkerImpl worker = new WorkerImpl(args[++i],
						Integer.parseInt(args[++i]));
				Thread workerThread = new Thread(worker);
				workerThread.start();
			} else
				throw new IllegalArgumentException();
		} catch (ArrayIndexOutOfBoundsException e) {
			e.printStackTrace();
			System.err.println(USAGE);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			System.err.println(USAGE);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			System.err.println(USAGE);
		} catch (RemoteException e) {
			e.printStackTrace();
			System.err.println("Can't connect. (RemoteException)");
		} catch (NotBoundException e) {
			e.printStackTrace();
			System.err.println("Can't connect. (NotBoundException)");
		}
	}

	private static void startAll() {
		Thread server = new Thread(new Runnable() {
			public void run() {
				String[] args = {"server",  "1337"};
				main(args);
			}
		});
		server.start();
		try {Thread.sleep(100);} catch (InterruptedException e) {}
		Thread worker = new Thread(new Runnable() {
			public void run() {
				String[] args = {"worker", "127.0.0.1",  "1337"};
				main(args);
			}
		});
		worker.start();
		try {Thread.sleep(100);} catch (InterruptedException e) {}
		Thread client = new Thread(new Runnable() {
			public void run() {
				String[] args = {"client", "127.0.0.1",  "1337"};
				main(args);
			}
		});
		client.start();
	}
}
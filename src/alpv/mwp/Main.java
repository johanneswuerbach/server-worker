package alpv.mwp;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import alpv.mwp.crawler.CrawlerClient;
import alpv.mwp.ray.RayClient;
import alpv.mwp.testOne.PowClient;

public class Main {

	private static final String DEFAULT_JOB = "crawler";
	private static final String DEFAULT_HOST = "127.0.0.1";
	private static final String DEFAULT_PORT = "31337";
	private static final int DEFAULT_WORKERS = 2;

	private static final String USAGE = String
			.format("usage: java -jar UB%%X_%%NAMEN server PORT NUMBER_OF_WORKERS%n"
					+ "         (to start a server)%n"
					+ "or:    java -jar UB%%X_%%NAMEN client SERVERIPADDRESS SERVERPORT JOB%n"
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
				// startAll();
				System.err.println(USAGE);
			} else if (args[i].equals("server")) {
				new MasterServerImpl(Integer.parseInt(args[++i]));
			} else if (args[i].equals("client")) {

				String host = args[++i];
				int port = Integer.parseInt(args[++i]);

				Client client = null;
				if (args.length == 4) {
					client = startJob(args[++i], host, port);
				}
				if (client == null) {
					startJob(DEFAULT_JOB, host, port);
				}
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

	private static Client startJob(String job, String host, int port)
			throws RemoteException, NotBoundException {
		Client client = null;
		if (job.equals("pow")) {
			client = new PowClient(host, port);
			client.execute();
		} else if (job.equals("ray")) {
			client = new RayClient(host, port);
			client.execute();
		} else if (job.equals("crawler")) {
			client = new CrawlerClient(host, port);
			client.execute();
		}
		return client;
	}

	public static void startAll() {
		
		Thread server = new Thread(new Runnable() {
			public void run() {
				String[] args = { "server", DEFAULT_PORT };
				main(args);
			}
		});
		server.start();
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
		}
		Runnable runnableWorker = new Runnable() {
			public void run() {
				String[] args = { "worker", DEFAULT_HOST, DEFAULT_PORT };
				main(args);
			}
		};
		Thread[] workers = new Thread[DEFAULT_WORKERS];
		for (int i = 0; i < workers.length; i++) {
			workers[i] = new Thread(runnableWorker);
			workers[i].start();
		}
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
		}
		Thread client = new Thread(new Runnable() {
			public void run() {
				String[] args = { "client", DEFAULT_HOST, DEFAULT_PORT };
				main(args);
			}
		});
		client.start();
		
		// Shutdown everthing
		try {
			while (client.isAlive()) {
				Thread.sleep(500);
			}
			for (Thread worker : workers) {
				worker.join();
			}
			System.exit(0);
		} catch (InterruptedException e) {
			// Ignore
		}
	}
}
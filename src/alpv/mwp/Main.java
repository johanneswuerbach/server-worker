package alpv.mwp;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import alpv.mwp.crawler.CrawlerClient;
import alpv.mwp.ray.RayClient;
import alpv.mwp.testOne.PowClient;

public class Main {
	
	private static final String DEFAULT_JOB = "crawler";
	
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
				startAll();
			}else if (args[i].equals("server")) {
				new MasterServerImpl(Integer.parseInt(args[++i]));
			} else if (args[i].equals("client")) {
				
				String host = args[++i];
				int port = Integer.parseInt(args[++i]);
				
				Client client = null;
				if (args.length == 4) {
					client = startJob(args[++i], host, port);
				}
				if(client == null) {
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
	
	private static Client startJob(String job, String host, int port) throws RemoteException, NotBoundException {
		Client client = null;
		if(job.equals("pow")) {
			client = new PowClient(host, port);
			client.execute();
		}
		else if(job.equals("ray")) {
			client = new RayClient(host, port);
			client.execute();
		}
		else if(job.equals("crawler")) {
			client = new CrawlerClient(host, port);
			client.execute();
		}
		return client;
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
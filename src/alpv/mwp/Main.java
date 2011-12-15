package alpv.mwp;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import alpv.mwp.testOne.ClientImpl;

public class Main {
	private static final String	USAGE	= String.format("usage: java -jar UB%%X_%%NAMEN server PORT NUMBER_OF_WORKERS%n" +
														"         (to start a server)%n" +
														"or:    java -jar UB%%X_%%NAMEN client SERVERIPADDRESS SERVERPORT%n" +
														"         (to start a client)%n"+
														"or:    java -jar UB%%X_%%NAMEN worker SERVERIPADDRESS SERVERPORT%n" +
														"         (to start a worker)");

	/**
	 * Starts a server/client according to the given arguments. 
	 * @param args
	 */
	public static void main(String[] args) {
		
		try {
			int i = 0;
			if(args.length == 0) {
				throw new IllegalArgumentException();
			}

			if(args[i].equals("server")) {
				new MasterServerImpl( Integer.parseInt(args[++i]), Integer.parseInt(args[++i]));
			}
			else if(args[i].equals("client")) {
				ClientImpl client = new ClientImpl(args[++i], Integer.parseInt(args[++i]));
				client.execute();
			}
			else if(args[i].equals("worker")) {
				WorkerImpl worker = new WorkerImpl(args[++i], Integer.parseInt(args[++i]));
				Thread workerThread = new Thread (worker);
				workerThread.start();
			}
			else
				throw new IllegalArgumentException();
		}
		catch(ArrayIndexOutOfBoundsException e) {
			System.err.println(USAGE);
		}
		catch(NumberFormatException e) {
			System.err.println(USAGE);
		}
		catch(IllegalArgumentException e) {
			System.err.println(USAGE);
		} catch (RemoteException e) {
			e.printStackTrace();
			System.err.println("Can't connect. (RemoteException)");
		} catch (NotBoundException e) {
			e.printStackTrace();
			System.err.println("Can't connect. (NotBoundException)");
		}
	}
}
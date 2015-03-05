package node;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Logger;

public class CLIListner implements Runnable {
	private Thread thread;
	private static final Logger log = Logger.getLogger(CLIListner.class
			.getName());
	public final static CLIListner INSTANCE = new CLIListner();

	private CLIListner() {
	}

	public void run() {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String command = null;
		while (true) {
			System.out.print("node>>> ");
			try {
				command = br.readLine();
			} catch (IOException ioe) {
				System.out.println("Error getting input command. Retry");
				break;
			}
		System.out.println(command +" sdflsjdlfs");
		}
	}

	public void start() {
		log.info("Starting command line interface listner");
		if (thread == null) {
			thread = new Thread(this);
			thread.start();
		}
	}

}

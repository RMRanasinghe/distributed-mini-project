package node;

import java.util.logging.Logger;

public class CLIListner implements Runnable {
	private Thread thread;
	private static final Logger log = Logger.getLogger(CLIListner.class
			.getName());
	public final static CLIListner INSTANCE = new CLIListner();

	private CLIListner() {
	}

	public void run() {
		System.out.println("TODOOOOOO");
	}

	public void start() {
		log.info("Starting command line interface listner");
		if (thread == null) {
			thread = new Thread(this);
			thread.start();
		}
	}

}

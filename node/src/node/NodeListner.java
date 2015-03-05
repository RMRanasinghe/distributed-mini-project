package node;

import java.util.logging.Logger;

public class NodeListner implements Runnable{
	private Thread thread;
	private static final Logger log = Logger.getLogger(NodeListner.class
			.getName());
	public final static NodeListner INSTANCE = new NodeListner();

	private NodeListner() {
	}

	public void run() {
		System.out.println("TODOOO");
	}

	public void start() {
		log.info("Starting other node listner");
		if (thread == null) {
			thread = new Thread(this);
			thread.start();
		}
	}
}

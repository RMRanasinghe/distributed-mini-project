package node;

import java.util.LinkedList;
import java.util.Properties;
import java.util.Queue;

public class BoundedMessageIDBuffer {
	private int maxSize;
	private Queue<Integer> MessageIDSet;
	private PropertyLoader propertyLoader = new PropertyLoader();
	private Properties properties = propertyLoader.getProperties();
	public final static BoundedMessageIDBuffer INSTANCE = new BoundedMessageIDBuffer();

	private BoundedMessageIDBuffer() {
		maxSize = Integer.parseInt(properties
				.getProperty("message.id.buffer.size"));
		MessageIDSet = new LinkedList<Integer>();
	}

	public void add(int id) {
		if (!contains(id)) {
			MessageIDSet.add(id);
			if (MessageIDSet.size() >= maxSize) {
				forgetOldestMessageId();
			}
		}
	}

	public boolean contains(int id) {
		if (MessageIDSet.contains(id)) {
			return true;
		} else {
			return false;
		}
	}

	private void forgetOldestMessageId() {
		MessageIDSet.remove();
	}
}

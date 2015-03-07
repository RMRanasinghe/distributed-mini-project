package node;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Properties;
import java.util.Set;

public class BoundedMessageIDBuffer {
	private int maxSize;
	private Set<String> MessageIDSet;
	private PropertyLoader propertyLoader = new PropertyLoader();
	private Properties properties = propertyLoader.getProperties();
	public final static BoundedMessageIDBuffer INSTANCE = new BoundedMessageIDBuffer();

	private BoundedMessageIDBuffer() {
		maxSize = Integer.parseInt(properties
				.getProperty("message.id.buffer.size"));
		MessageIDSet = new LinkedHashSet<String>();
	}

	public boolean add(String id) {
		boolean ret = MessageIDSet.add(id);
		if (ret && MessageIDSet.size() >= maxSize) {
			forgetOldestMessageId();
		}
		return ret;
	}

	private void forgetOldestMessageId() {
		Iterator<String> iter = MessageIDSet.iterator();
		iter.next();
		iter.remove();
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return MessageIDSet.toString();
	}
}

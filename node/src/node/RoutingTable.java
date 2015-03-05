package node;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class RoutingTable {
	public final static RoutingTable INSTANCE = new RoutingTable();

	private Set<RoutingTableEntry> routingTable = null;

	private RoutingTable() {
		routingTable = new HashSet<RoutingTableEntry>();
	}

	public void add(RoutingTableEntry entry) {
		routingTable.add(entry);
	}

	public void add(Set<RoutingTableEntry> entries) {
		routingTable.addAll(entries);
	}

	public Set<RoutingTableEntry> get() {
		return routingTable;
	}

	public void remove(String ip) {
		Iterator<RoutingTableEntry> itr = routingTable.iterator();
		RoutingTableEntry entry = null;
		
		while (itr.hasNext()) {
			entry = itr.next();
			if (entry.IP.equals(ip)) {
				break;
			}
		}
		if (entry != null) {
			routingTable.remove(entry);
		}
	}

	public void remove(String ip, int port) {
		Iterator<RoutingTableEntry> itr = routingTable.iterator();
		RoutingTableEntry entry = null;
		
		while (itr.hasNext()) {
			entry = itr.next();
			if (entry.IP.equals(ip) && entry.port == port) {
				break;
			}
		}
		if (entry != null) {
			routingTable.remove(entry);
		}
	}
	
	public void remove(String ip, int port, String username) {
		Iterator<RoutingTableEntry> itr = routingTable.iterator();
		RoutingTableEntry entry = null;
		
		while (itr.hasNext()) {
			entry = itr.next();
			if (entry.IP.equals(ip) && entry.port == port && entry.username.equals(username)) {
				break;
			}
		}
		if (entry != null) {
			routingTable.remove(entry);
		}
	}

}

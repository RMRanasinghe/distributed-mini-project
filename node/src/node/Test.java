package node;

import java.util.Hashtable;
import java.util.Properties;

public class Test {
	public static Hashtable<String, Integer> messages = new Hashtable<String, Integer>();
	public static Hashtable<String, Integer> minhops = new Hashtable<String, Integer>();
	PropertyLoader propertyLoader = new PropertyLoader();
	Properties properties = propertyLoader.getProperties();
	private QueryExecutor qe = new QueryExecutor();

	public void start() throws InterruptedException {
		String queryList = properties.getProperty("node.querylist");
		String[] queryArray = queryList.split(",");
		for (String query : queryArray) {
			query = query.replace(" ", "_");
			qe.fileSearch(query);
			Thread.sleep(1000);
		}
	}
	
	public void print(){
		for(String message:messages.keySet()){
			System.out.println(message + "  " + messages.get(message));
		}
		System.out.println();
		for(String hop:minhops.keySet()){
			System.out.println(hop + "  " + minhops.get(hop));
		}
	}
	
	public void clean(){
		messages = new Hashtable<String, Integer>();
		minhops = new Hashtable<String, Integer>();
	}
}

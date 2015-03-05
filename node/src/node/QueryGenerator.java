package node;

public class QueryGenerator {
	public synchronized String getBSRegister(String IP, int port, String username) {
		return addLength("REG " + IP + " " + port + " " + username);
	}
	public synchronized String getBSUnRegister(String IP, int port, String username) {
		return addLength("UNREG " + IP + " " + port + " " + username);
	}
	public synchronized String getJoin(String IP, int port) {
		return addLength("JOIN " + IP + " " + port);
	}
	public synchronized String getLeave(String IP, int port) {
		return addLength("LEAVE " + IP + " " + port);
	}
	public synchronized String getSearch(String IP, int port,String filename,int hops) {
		return addLength("SER " + IP + " " + port + " " + filename + " " + hops);
	}
	private String addLength(String str) {
		int length = str.length() + 6;
		if (length < 10) {
			return "000" + length + " " + str;
		} else if (length < 100) {
			return "00" + length + " " + str;
		} else {
			return "0" + length + " " + str;
		}
	}
}

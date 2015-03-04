package node;

public class QueryGenerator {
	public synchronized String getBSRegister(String IP, int port, String username) {
		return addLength("REG " + IP + " " + port + " " + username);
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

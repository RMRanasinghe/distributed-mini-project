package node;

import java.util.List;

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
	public synchronized String getJoinOK(int value) {
		return addLength("JOINOK " + value);
	}
	public synchronized String getLeave(String IP, int port) {
		return addLength("LEAVE " + IP + " " + port);
	}
	public synchronized String getLeaveOK(int value) {
		return addLength("LEAVEOK " + value);
	}
	public synchronized String getSearch(String IP, int port,String filename,int hops) {
		return addLength("SER " + IP + " " + port + " " + filename + " " + hops);
	}
	public synchronized String getSearchOK(String IP, int port,List<String> filenames,int hops) {
		StringBuffer fileNameString = new StringBuffer();
		String prefix="";
		for(String file:filenames){
			fileNameString.append(prefix);
			prefix=" ";
			fileNameString.append(file);
		}
		return addLength("SEROK " + filenames.size() + " "+ IP + " " + port + " " + hops + " " + fileNameString.toString() );
	}
	public synchronized String getError(String error) {
		return addLength("ERROR " + error);
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

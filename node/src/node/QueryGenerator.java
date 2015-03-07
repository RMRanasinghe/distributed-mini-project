package node;

import java.util.List;

public class QueryGenerator {
	public String getBSRegister(String IP, int port, String username) {
		return addLength("REG " + IP + " " + port + " " + username);
	}
	public String getBSUnRegister(String IP, int port, String username) {
		return addLength("UNREG " + IP + " " + port);
	}
	public String getJoin(String IP, int port) {
		return addLength("JOIN " + IP + " " + port);
	}
	public String getJoinOK(int value) {
		return addLength("JOINOK " + value);
	}
	public String getLeave(String IP, int port) {
		return addLength("LEAVE " + IP + " " + port);
	}
	public String getLeaveOK(int value) {
		return addLength("LEAVEOK " + value);
	}
	public String getSearch(String IP, int port,String filename,int hops,int messageId) {
		return addLength("SER " + IP + " " + port + " " + filename + " " + hops + " " + messageId);
	}
	public String getSearchOK(String IP, int port,List<String> filenames,int hops) {
		StringBuffer fileNameString = new StringBuffer();
		String prefix="";
		for(String file:filenames){
			fileNameString.append(prefix);
			prefix=" ";
			fileNameString.append(file);
		}
		return addLength("SEROK " + filenames.size() + " "+ IP + " " + port + " " + hops + " " + fileNameString.toString() );
	}
	public String getError(String error) {
		return addLength("ERROR " + error);
	}
	private String addLength(String str) {
		int length = str.length() + 6;
		if (length < 10) {
			return "000" + length + " " + str+"\n";
		} else if (length < 100) {
			return "00" + length + " " + str+"\n";
		} else {
			return "0" + length + " " + str+"\n";
		}
	}
}

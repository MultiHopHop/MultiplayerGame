package Communicator;

public class PackageData {
	private String output;
	
	public PackageData(int playerIndex, String move) {	
		StringBuilder sb = new StringBuilder();
		sb.append("player");
		sb.append(playerIndex);
		sb.append("&");
		sb.append(move);
		
		this.output = sb.toString();
	}
	
	public String getOutput() {
		return output;
	}

	public static void main(String[] args) {
		PackageData p = new PackageData(2, "LEFT");
		System.out.println(p.getOutput());
	}
}


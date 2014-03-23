package Communicator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParseData {
	private int playerIndex;
	private String move;

	/**
	 * Parse an input string into playerIndex and move;
	 * @param input, e.g. "player0&UP"
	 */
	public ParseData(String input) {
		Pattern pattern = Pattern.compile("(^player[0-3])|"
				+ "(UP|DOWN|LEFT|RIGHT)");

		Matcher matcher = pattern.matcher(input);
		while (matcher.find()) {
			if (matcher.group().matches("player[0-3]")) {
				playerIndex = Character.getNumericValue(matcher.group().charAt(
						6));
				continue;
			}
			if (matcher.group().matches("(UP|DOWN|LEFT|RIGHT)")) {
				move = matcher.group();
			}
		}
	}

	public int getPlayerIndex() {
		return playerIndex;
	}

	public String getMove() {
		return move;
	}

	public static void main(String[] args) {
		String input = "player3&UP";
		ParseData a = new ParseData(input);
		System.out.println(a.playerIndex);
		System.out.println(a.move);
	}
}

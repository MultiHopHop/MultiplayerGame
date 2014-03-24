package Game;

import java.awt.Color;
import java.util.ArrayList;

public class Board {
	private final int SIZE = 8;
	private Square[][] squares;
	private ArrayList<Player> players;
	private Timer timer;
	private Player p1;

	/**
	 * To display the board, call board.getSquares[i][j].getPlayer.getColor()
	 * for color of the square.
	 */
	public Board() {
		// initialize squares
		squares = new Square[SIZE][SIZE];
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				squares[i][j] = new Square(VisibleState.UNTOUCHED);
			}
		}

		// initialize players
		players = new ArrayList<Player>();
		Coord coord1 = new Coord(0, 0);
		p1 = new Player(Color.YELLOW, coord1);
		squares[0][0].setVisibleState(p1);
		players.add(p1);

		Coord coord2 = new Coord(0, SIZE - 1);
		Player p2 = new Player(Color.BLUE, coord2);
		squares[0][SIZE - 1].setVisibleState(p2);
		players.add(p2);

		Coord coord3 = new Coord(SIZE - 1, 0);
		Player p3 = new Player(Color.RED, coord3);
		squares[SIZE - 1][0].setVisibleState(p3);
		players.add(p3);

		Coord coord4 = new Coord(SIZE - 1, SIZE - 1);
		Player p4 = new Player(Color.GREEN, coord4);
		squares[SIZE - 1][SIZE - 1].setVisibleState(p4);
		players.add(p4);

		// initialize timer
		timer = new Timer();
	}

	public void update(Player player, String move) {
		int x = player.getCoord().getX();
		int y = player.getCoord().getY();

		switch (move) {
		case "UP":
			if (y > 0)
				update(player, new Coord(x, y - 1));
			break;
		case "DOWN":
			if (y < SIZE -1)
				update(player, new Coord(x, y + 1));
			break;
		case "RIGHT":
			if (x < SIZE - 1)
				update(player, new Coord(x + 1, y));
			break;
		case "LEFT":
			if (x > 0)
				update(player, new Coord(x - 1, y));
			break;
		}
	}

	public void update(Player player, Coord newCoord) {
		int newX = newCoord.getX();
		int newY = newCoord.getY();

		int lastX = player.getCoord().getX();
		int lastY = player.getCoord().getY();

		// On the client side, interval of update = default interval /
		// player.speed
		if (player.getSpeed() != 1 && player.getTime() > 5000)
			player.resetSpeed();
		synchronized (player) {
			synchronized (squares[newX][newY]) {
				if (squares[newX][newY].getVisibleState() == VisibleState.POWERUP
						&& !squares[newX][newY].getLock()) {
					player.setSpeed(2);
				}

				if (squares[newX][newY].setVisibleState(player)) {
					squares[lastX][lastY].resetLock();
					player.setMoveStatus(true);
					player.setCoord(newCoord);

					System.out.println("update player coord");
				}
			}
		}
		
	}

	// check validity of previous move
	public boolean checkMove(Player player) {
		return player.getMoveStatus();
	}

	// immutable method
	public Square[][] getSquares() {
		Square[][] clone = new Square[SIZE][SIZE];
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				clone[i][j] = new Square(this.squares[i][j].getVisibleState(),
						this.squares[i][j].getPlayer());
			}
		}
		return clone;
	}

	// immutable method
	public ArrayList<Player> getPlayers() {
		ArrayList<Player> clone = new ArrayList<Player>(players.size());
		for (Player player : players)
			clone.add(new Player(player.getColor(), player.getCoord()));
		return clone;
	}

	// in milliseconds
	public long getTime() {
		return timer.checkTime();
	}

	public void spawnPowerup() {
		while (true) {
			int x = 0 + (int) (Math.random() * (SIZE));
			int y = 0 + (int) (Math.random() * (SIZE));
			System.out.println("X, Y "+x+" "+y);
			if (!squares[x][y].getLock()) {
				squares[x][y].spawnPowerup();
				break;
			}
		}
	}
}

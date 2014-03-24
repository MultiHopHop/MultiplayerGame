package Game;

import java.awt.Color;

public class Player {
	private Color color;
	private Coord coord, lastCoord;
	private boolean moveStatus;
	private int speed;
	private Timer timer;
	
	public Player(Color color, Coord coord) {
		this.color = color;
		this.coord = coord;
		this.speed = 1;
	}

	public Coord getCoord() {
		return coord;
	}

	public Coord getLastCoord() {
		return lastCoord;
	}

	public void setCoord(Coord coord) {
		this.coord = coord;
	}

	public void setLastCoord(Coord lastCoord) {
		this.lastCoord = lastCoord;
	}

	public boolean getMoveStatus() {
		return moveStatus;
	}

	public void setMoveStatus(boolean moveStatus) {
		this.moveStatus = moveStatus;
	}
	
	public Color getColor() {
		return this.color;
	}
	
	public void setSpeed(int speed) {
		this.speed = speed;
		timer = new Timer();
	}
	
	public int getSpeed() {
		return speed;
	}
	
	public void resetSpeed() {
		this.speed = 1;
	}
	
	public long getTime() {
		if (timer.equals(null)) return 0;
		return timer.checkTime();
	}
}

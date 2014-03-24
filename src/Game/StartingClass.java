package Game;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class StartingClass extends Applet implements Runnable, KeyListener {
	private Board board;
	private ArrayList<Player> players;
	private long time;
	private static String input;
	private Image image;
	private Graphics second;
	private Square[][] squares;
	private int k, interval;

	public void init() {
		setSize(320, 320);
		setBackground(Color.GRAY);
		setFocusable(true);
		addKeyListener(this);
	}

	public void start() {
		board = new Board();
		players = board.getPlayers();
		input = "";
		k = 1;
		interval = 5000;
		System.out.println("Board initiated");
		Thread thread = new Thread(this);
		thread.start();
	}

	public void stop() {
		super.stop();
	}

	public void destroy() {
		super.destroy();
	}

	public void run() {

		while (true) {
			if ((time = board.getTime()) - interval*k > 0) {
				board.spawnPowerup();
				k ++;
				System.out.println("time: "+time);
			}
			if (!input.equals("")) {
				System.out.println("input: "+input);
				board.update(players.get(0), input);
				System.out.println("Current Coord: "+players.get(0).getCoord().getX()+" "+
				players.get(0).getCoord().getY());
				input = "";
			}
			repaint();
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void update(Graphics g) {
		if (image == null) {
			image = createImage(this.getWidth(), this.getHeight());
			second = image.getGraphics();
		}

		second.setColor(getBackground());
		second.fillRect(0, 0, getWidth(), getHeight());
		second.setColor(getForeground());
		paint(second);

		g.drawImage(image, 0, 0, this);
	}

	public void paint(Graphics g) {
		squares = board.getSquares();
		for (int i = 0; i < squares.length; i++) {
			for (int j = 0; j < squares[i].length; j++) {
				int mod_i = 40 * i;
				int mod_j = 40 * j;
				if (squares[i][j].getVisibleState() == VisibleState.TOUCHED) {
					g.setColor(squares[i][j].getPlayer().getColor());
					g.fillRect(mod_i, mod_j, 40, 40);
				} else if (squares[i][j].getVisibleState() == VisibleState.POWERUP) {
					g.setColor(Color.ORANGE);
					g.fillRect(mod_i, mod_j, 40, 40);
				}
			}
		}
	}

	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_UP:
			input = "UP";
			System.out.println("press up");
			break;

		case KeyEvent.VK_DOWN:
			input = "DOWN";
			break;

		case KeyEvent.VK_LEFT:
			input = "LEFT";
			break;

		case KeyEvent.VK_RIGHT:
			input = "RIGHT";
			break;
		}
	}

	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}

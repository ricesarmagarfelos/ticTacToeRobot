package ticTacToe;

import java.awt.event.MouseEvent;
import acm.graphics.*;
import acm.program.*;

public class MainTicToe extends GraphicsProgram {

	private static final int APPLICATION_EDGE = 600;

	private static final int LINE_LENGTH = APPLICATION_EDGE / 3;

	private static final int XO_OFFSET = LINE_LENGTH / 4;

	private static final int XO_LENGTH = LINE_LENGTH / 2;

	private static final int BOXES_PER_ROW = 3;

	private static final int TOP_LINE = LINE_LENGTH;
	private static final int BOT_LINE = LINE_LENGTH * 2;

	public void run() {
		createBoard();
		addMouseListeners();

	}

	private void createBoard() {
		setSize(APPLICATION_EDGE, APPLICATION_EDGE);
		makeLines();
	}

	private void makeLines() {
		int arraySetter = 0;
		for (int j = 0; j < 3; j++) {
			for (int i = 0; i < 3; i++) {
				GRect square = new GRect(LINE_LENGTH * i, LINE_LENGTH * j,
						LINE_LENGTH, LINE_LENGTH);
				add(square);

				switch (arraySetter) {
				case 0:
					a[i] = square;
					break;
				case 1:
					b[i] = square;
					break;
				case 2:
					c[i] = square;
					break;
				default:
					break;
				}
			}
			arraySetter++;
		}
	}

	public void mouseClicked(MouseEvent e) {
		GObject clickedRect = getElementAt(e.getX(), e.getY());
		GObject clicked = null;
		for (int i = 0; i < BOXES_PER_ROW; i++) {
			if (e.getY() < TOP_LINE && clickedRect == a[i]) {
				clicked = a[i];
				a[i] = null;

				if (playerTurn) {
					topXO[i] = 1;
				} else {
					topXO[i] = 2;
				}
			}

			if ((e.getY() > TOP_LINE && e.getY() < BOT_LINE)
					&& clickedRect == b[i]) {
				clicked = b[i];
				b[i] = null;

				if (playerTurn) {
					midXO[i] = 1;
				} else {
					midXO[i] = 2;
				}
			}

			if (e.getY() > BOT_LINE && clickedRect == c[i]) {
				clicked = c[i];
				c[i] = null;

				if (playerTurn) {
					botXO[i] = 1;
				} else {
					botXO[i] = 2;
				}
			}
		}

		if (playerTurn && clicked != null) {
			placeX(clickedRect.getX(), clickedRect.getY());
			spaceRemaining--;
		} else if (clicked != null) {
			// eventually do the computer move
			placeO(clickedRect.getX(), clickedRect.getY());
			spaceRemaining--;
		}

		if (checkWin()) {
			GLabel win = new GLabel("Win");
			win.setLocation(getWidth()/ 2, getHeight()/ 2);
			
			add(win);
		} else if (spaceRemaining == 0) {
			GLabel tie = new GLabel("Game Over");
			tie.setLocation(getWidth() / 2, getHeight() / 2);
			
			add(tie);
		}

	}

	private void placeX(double x, double y) {
		GLine diagonalDown = new GLine(x + XO_OFFSET, y + XO_OFFSET, x
				+ XO_LENGTH + XO_OFFSET, y + XO_LENGTH + XO_OFFSET);
		GLine diagonalUp = new GLine(x + XO_OFFSET, y + XO_LENGTH + XO_OFFSET,
				x + XO_LENGTH + XO_OFFSET, y + XO_OFFSET);

		add(diagonalDown);
		add(diagonalUp);
		diagonalDown.sendToBack();
		diagonalUp.sendToBack();
		playerTurn = false;
	}

	private void placeO(double x, double y) {
		GOval o = new GOval(x + XO_OFFSET, y + XO_OFFSET, XO_LENGTH, XO_LENGTH);
		add(o);
		o.sendToBack();

		playerTurn = true;

	}

	private boolean checkWin() {
		if (checkHorizontal() || checkVertical() || checkDiagonal()) return true;

		return false;
	}

	private boolean checkHorizontal() {
		for (int k = 0; k < BOXES_PER_ROW; k++) {
			if (k == 0 && topXO[0] == 1 && topXO[1] == 1 && topXO[2] == 1) return true;
			if (k == 1 && midXO[0] == 1 && midXO[1] == 1 && midXO[2] == 1) return true;
			if (k == 2 && botXO[0] == 1 && botXO[1] == 1 && botXO[2] == 1) return true;
		}

		return false;
	}

	private boolean checkVertical() {
		for (int k = 0; k < BOXES_PER_ROW; k++) {
			if (k == 0 && topXO[0] == 1 && midXO[0] == 1 && botXO[0] == 1) return true;
			if (k == 1 && topXO[1] == 1 && midXO[1] == 1 && botXO[1] == 1) return true;
			if (k == 2 && topXO[2] == 1 && midXO[2] == 1 && botXO[2] == 1) return true;
		}

		return false;
	}

	private boolean checkDiagonal() {
		if (topXO[0] == 1 && midXO[1] == 1 && botXO[2] == 1) return true;
		if (topXO[2] == 1 && midXO[1] == 1 && botXO[0] == 1) return true;

		return false;
	}

	private boolean playerTurn = true;
	private int spaceRemaining = 9;
	private GObject[] a = new GObject[BOXES_PER_ROW];
	private GObject[] b = new GObject[BOXES_PER_ROW];
	private GObject[] c = new GObject[BOXES_PER_ROW];

	private int[] topXO = new int[BOXES_PER_ROW];
	private int[] midXO = new int[BOXES_PER_ROW];
	private int[] botXO = new int[BOXES_PER_ROW];
	// 1 is for X, 2 is for O, default 0 is empty box
}

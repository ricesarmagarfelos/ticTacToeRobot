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

	public void run() {
		createBoard();
		addMouseListeners();

	}

	private void createBoard() {
		setSize(APPLICATION_EDGE, APPLICATION_EDGE);
		makeLines();
	}

	private void makeLines() {
		for (int j = 0; j < BOXES_PER_ROW; j++) {
			for (int i = 0; i < BOXES_PER_ROW; i++) {
				GRect square = new GRect(LINE_LENGTH * i, LINE_LENGTH * j,
						LINE_LENGTH, LINE_LENGTH);
				add(square);

				board[j][i] = square;
			}
		}
	}

	public void mouseClicked(MouseEvent e) {
		GObject clickedRect = getElementAt(e.getX(), e.getY());
		GObject clicked = null;
		
		int i = 0, j = 0;
		// i tracks the row, j tracks the column

		for (i = 0; i < BOXES_PER_ROW; i++) {
			for (j = 0; j < BOXES_PER_ROW; j++) {
				if (board[i][j] == clickedRect) {
					clicked = board[i][j];
					// temporarily track the clicked rectangle

					board[i][j] = null;
					// set the clicked rectangle null for 
					// future attempts to place an x/o in
					// the rectangle
				}
			}
		}

		// if the square has already been clicked and had a x/o placed there,
		// clicked will be null
		if (playerTurn && clicked != null) {
			placeX(clickedRect.getX(), clickedRect.getY());
			boxesRemaining--;
			xOTracker[i][j] = 1;
		} else if (clicked != null) {
			// eventually do the computer move
			placeO(clickedRect.getX(), clickedRect.getY());
			boxesRemaining--;
			xOTracker[i][j] = 2;
		}

		if (checkWin()) {
			GLabel win = new GLabel("Win");
			win.setLocation(getWidth() / 2, getHeight() / 2);

			add(win);
		} else if (boxesRemaining == 0) {
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
		if (checkHorizontal() || checkVertical() || checkDiagonal())
			return true;

		return false;
	}

	private boolean checkHorizontal() {
		for (int k = 0; k < BOXES_PER_ROW; k++) {
			if (k == 0 && topXO[0] == 1 && topXO[1] == 1 && topXO[2] == 1)
				return true;
			if (k == 1 && midXO[0] == 1 && midXO[1] == 1 && midXO[2] == 1)
				return true;
			if (k == 2 && botXO[0] == 1 && botXO[1] == 1 && botXO[2] == 1)
				return true;
		}

		return false;
	}

	private boolean checkVertical() {
		for (int k = 0; k < BOXES_PER_ROW; k++) {
			if (k == 0 && topXO[0] == 1 && midXO[0] == 1 && botXO[0] == 1)
				return true;
			if (k == 1 && topXO[1] == 1 && midXO[1] == 1 && botXO[1] == 1)
				return true;
			if (k == 2 && topXO[2] == 1 && midXO[2] == 1 && botXO[2] == 1)
				return true;
		}

		return false;
	}

	private boolean checkDiagonal() {
		if (topXO[0] == 1 && midXO[1] == 1 && botXO[2] == 1)
			return true;
		if (topXO[2] == 1 && midXO[1] == 1 && botXO[0] == 1)
			return true;

		return false;
	}

	private boolean playerTurn = true;
	private int boxesRemaining = 9;

	private GObject[][] board = new GObject[BOXES_PER_ROW][BOXES_PER_ROW];
	private int[][] xOTracker = new int[BOXES_PER_ROW][BOXES_PER_ROW];

	private int[] topXO = new int[BOXES_PER_ROW];
	private int[] midXO = new int[BOXES_PER_ROW];
	private int[] botXO = new int[BOXES_PER_ROW];
	// 1 is for X, 2 is for O, default 0 is empty box
}

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
	
	TBot bot = new TBot();

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

		// i tracks rows, j tracks columns
		for (int i = 0; i < BOXES_PER_ROW; i++) {
			for (int j = 0; j < BOXES_PER_ROW; j++) {
				if (board[i][j] == clickedRect) {
					clicked = board[i][j];
					// temporarily track the clicked rectangle

					board[i][j] = null;
					// set the clicked rectangle null for
					// future attempts to place an x/o in
					// the rectangle
					
					if (playerTurn) {
						xOTracker[i][j] = 1;
						// 1 is for X
					} else {
						xOTracker[i][j] = 2;
						// 2 is for O
					}
				}
			}
		}

		// if the square has already been clicked and had a x/o placed there,
		// clicked will be null
		if (playerTurn && clicked != null) {
			placeX(clickedRect.getX(), clickedRect.getY());
			boxesRemaining--;
		} else if (clicked != null) {
			// eventually do the computer move
			// this will change to be part of the playerClick as a response
			placeO(clickedRect.getX(), clickedRect.getY());
			boxesRemaining--;
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
		return (checkHorizontal() || checkVertical() || checkDiagonal());
	}

	private boolean checkHorizontal() {
		for (int k = 0; k < BOXES_PER_ROW; k++) {
			if (xOTracker[k][0] == 1 && 
				xOTracker[k][1] == 1 &&
				xOTracker[k][2] == 1)
				return true;
		}

		return false;
	}

	private boolean checkVertical() {
		for (int k = 0; k < BOXES_PER_ROW; k++) {
			if (xOTracker[0][k] == 1 &&
				xOTracker[1][k] == 1 &&
				xOTracker[2][k] == 1)
				return true;
		}

		return false;
	}

	private boolean checkDiagonal() {
		if (xOTracker[0][0] == 1 && xOTracker[1][1] == 1 && xOTracker[2][2] == 1) return true;
		// top-left to bottom-right
		
		if (xOTracker[2][0] == 1 && xOTracker[1][1] == 1 && xOTracker[0][2] == 1) return true;
		// bottom-left to top-right

		return false;
	}

	private boolean playerTurn = true;
	private int boxesRemaining = 9;

	private GObject[][] board = new GObject[BOXES_PER_ROW][BOXES_PER_ROW];
	private int[][] xOTracker = new int[BOXES_PER_ROW][BOXES_PER_ROW];
}

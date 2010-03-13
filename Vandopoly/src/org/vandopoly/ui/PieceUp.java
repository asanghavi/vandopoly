package org.vandopoly.ui;

import java.awt.Point;

import javax.swing.JLayeredPane;

/*
 * Represents a piece on the right side of the board moving in the down direction
 * 
 * @author James Kasten
 */
public class PieceUp extends PieceState {
	
	private static PieceUp INSTANCE = null;
	
	protected PieceUp() {
		// Exists to disable instantiation
	}
	
	public static PieceUp Instance() {
		if (INSTANCE == null) {
			INSTANCE = new PieceUp();
		}
		
		return INSTANCE;
	}
	
	public void move(final Piece piece, int currentSpace, int newSpace) {
		final int startX = piece.pixelX_;
		final int startY = piece.pixelY_;
		
		// Eventually move out to main function - due to race conditions
		if (newSpace >= 20) {
			piece.pixelY_ = (int)(piece.pixelY_ - ((20 - currentSpace) * DisplayAssembler.getSpaceWidth() + 
					(DisplayAssembler.getSpaceWidth() * .5)));
			piece.pixelX_ = piece.pixelX_ + (int)(DisplayAssembler.getSpaceWidth());
		}
		else
			piece.pixelY_ = piece.pixelY_ - ((newSpace - currentSpace) * DisplayAssembler.getSpaceWidth());
		final int curSpace = currentSpace;
		final int nSpace = newSpace;
		
		try {
			// Thread used for animation of piece
			new Thread() {
				public void run() {
					int cSpace = curSpace;
					int cX = startX;
					int cY = startY;
					
					while(cSpace != nSpace) {
						
						cY = moveSquare(piece, cX, cY, 1);
						
						cSpace++;
						
						if (cSpace >= 20) {
							cY = moveSquare(piece, cX, cY, .5);
							
							for (int i = 0; i < DisplayAssembler.getSpaceWidth(); i++) {
								DisplayAssembler.getInstance().addComponent(piece.getIcon(), 
									new Point(++cX, cY), JLayeredPane.MODAL_LAYER);
								
								try {
									Thread.sleep(1);
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
							}
							
							piece.changeState(PieceRight.Instance());
							piece.getState().move(piece, cSpace, nSpace);
							break;
						}
					}
					
				}
			}.start();
		} catch (ClassCastException e) {
			System.err.println("Dice expected object to method updateDice");
		}
	}
	
	private int moveSquare(Piece piece, int currentX, int currentY, double percent) {
		for (int i = 0; i < DisplayAssembler.getSpaceWidth() * percent; i++) {
			DisplayAssembler.getInstance().addComponent(piece.getIcon(), 
				new Point(currentX, --currentY), JLayeredPane.MODAL_LAYER);
			
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return currentY;
	}
}

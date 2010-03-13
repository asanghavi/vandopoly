
package org.vandopoly.ui;

import java.awt.Point;

import javax.swing.JLayeredPane;

/*
 * Represents a piece on the right side of the board moving in the down direction
 * 
 * @author James Kasten
 */
public class PieceRight extends PieceState {
	
	private static PieceRight INSTANCE = null;
	
	protected PieceRight() {
		// Exists to disable instantiation
	}
	
	public static PieceRight Instance() {
		if (INSTANCE == null) {
			INSTANCE = new PieceRight();
		}
		
		return INSTANCE;
	}
	
	public void move(final Piece piece, int currentSpace, int newSpace) {
		final int startX = piece.pixelX_;
		final int startY = piece.pixelY_;
		
		// Calculate first to try to avoid race condition problems with rolling twice before the animation
		// is over
		if (newSpace >= 30) {
			piece.pixelX_ = (int)(piece.pixelX_ + ((30 - currentSpace) * DisplayAssembler.getSpaceWidth() + 
					DisplayAssembler.getSpaceWidth()));
			piece.pixelY_ = piece.pixelY_ + (int)(DisplayAssembler.getSpaceWidth() * .5);
		}
		else
			piece.pixelX_ = piece.pixelX_ + ((newSpace - currentSpace) * DisplayAssembler.getSpaceWidth());
		
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
						
						cX = moveSquare(piece, cX, cY);
						cSpace++;
						
						if (cSpace >= 30) {
							cX = moveSquare(piece, cX, cY);
							
							for (int i = 0; i < (int)(DisplayAssembler.getSpaceWidth() * .5); i++) {
								DisplayAssembler.getInstance().addComponent(piece.getIcon(), 
									new Point(piece.pixelX_, ++cY), JLayeredPane.MODAL_LAYER);
								
								try {
									Thread.sleep(1);
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
							}
							piece.changeState(PieceDown.Instance());
							piece.getState().move(piece, cSpace, nSpace % 40);
							break;
						}
					}
					
				}
			}.start();
		} catch (ClassCastException e) {
			System.err.println("Dice expected object to method updateDice");
		}
	}
	
	private int moveSquare(Piece piece, int currentX, int currentY) {
		for (int i = 0; i < DisplayAssembler.getSpaceWidth(); i++) {
			DisplayAssembler.getInstance().addComponent(piece.getIcon(), 
				new Point(++currentX, currentY), JLayeredPane.MODAL_LAYER);
			
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return currentX;
	}
}
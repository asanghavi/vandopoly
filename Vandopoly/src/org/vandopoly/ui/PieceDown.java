/*****************************************************************************
 *   Copyright 2010 Vandopoly Team                                           *
 *   Licensed under the Apache License, Version 2.0 (the "License");         *
 *   you may not use this file except in compliance with the License.        *
 *   You may obtain a copy of the License at                                 *
 *                                                                           *
 *   http://www.apache.org/licenses/LICENSE-2.0                              *
 *                                                                           *
 *   Unless required by applicable law or agreed to in writing, software     *
 *   distributed under the License is distributed on an "AS IS" BASIS,       *
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.*
 *   See the License for the specific language governing permissions and     *
 *   limitations under the License.                                          *
 ****************************************************************************/

package org.vandopoly.ui;

import java.awt.Point;

import javax.swing.JLayeredPane;

/*
 * Represents a piece on the right side of the board moving in the down direction
 * 
 * @author James Kasten
 */
public class PieceDown extends PieceState {
	
	private static PieceDown INSTANCE = null;
	
	protected PieceDown() {
		// Exists to disable instantiation
	}
	
	public static PieceDown Instance() {
		if (INSTANCE == null) {
			INSTANCE = new PieceDown();
		}
		
		return INSTANCE;
	}
	
	public void move(final Piece piece, int currentSpace, int newSpace) {
		assert(newSpace < 40);
		final int startX = piece.pixelX_;
		final int startY = piece.pixelY_;
		
		// Eventually move out to main function - due to race conditions
		if (newSpace < 30) {
			piece.pixelY_ = (int)(piece.pixelY_ + ((40 - currentSpace) * DisplayAssembler.getSpaceWidth() + 
					(DisplayAssembler.getSpaceWidth() * .4)));
			piece.pixelX_ = piece.pixelX_ - (int)(DisplayAssembler.getSpaceWidth());
		}
		else
			piece.pixelY_ = piece.pixelY_ + ((newSpace - currentSpace) * DisplayAssembler.getSpaceWidth());
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
						
						cY = moveSquare(piece, cX, cY);
						
						cSpace = (cSpace + 1) % 40;
						
						if (cSpace == 0) {
							
							for (int i = 0; i < DisplayAssembler.getSpaceWidth() * .4; i++) {
								DisplayAssembler.getInstance().addComponent(piece.getIcon(), 
									new Point(cX, ++cY), JLayeredPane.MODAL_LAYER);
								
								try {
									Thread.sleep(1);
								} catch(InterruptedException e) {
									e.printStackTrace();
								}
							}
							
							for (int i = 0; i < DisplayAssembler.getSpaceWidth(); i++) {
								DisplayAssembler.getInstance().addComponent(piece.getIcon(), 
									new Point(--cX, cY), JLayeredPane.MODAL_LAYER);
								
								try {
									Thread.sleep(1);
								} catch(InterruptedException e) {
									e.printStackTrace();
								}
							}
							
							piece.changeState(PieceLeft.Instance());
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
	
	private int moveSquare(Piece piece, int currentX, int currentY) {
		for (int i = 0; i < DisplayAssembler.getSpaceWidth(); i++) {
			DisplayAssembler.getInstance().addComponent(piece.getIcon(), 
				new Point(currentX, ++currentY), JLayeredPane.MODAL_LAYER);
			
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return currentY;
	}
}
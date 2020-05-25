import javax.imageio.*;
import java.awt.image.*;
import java.io.*;
import java.nio.*;

/**
 * <code>Piece</code> class. Types of pieces and their possible doneMoveStack.
 *
 * @author Chris W. Bao, Ben C. Megan
 * @version 0.9.7
 * @since 4 APR 2020
 */
class Piece {
	// CONSTANTS //
	static final int WHITE		   = 1;
	static final int BLACK		   = 2;
	
	static final int EMPTY         = 0;
	static final int WHITE_PAWN    = 1;
	static final int WHITE_BISHOP  = 2;
	static final int WHITE_KNIGHT  = 3;
	static final int WHITE_ROOK    = 4;
	static final int WHITE_QUEEN   = 5;
	static final int WHITE_KING    = 6;
	static final int BLACK_PAWN    = 7;
	static final int BLACK_BISHOP  = 8;
	static final int BLACK_KNIGHT  = 9;
	static final int BLACK_ROOK    = 10;
	static final int BLACK_QUEEN   = 11;
	static final int BLACK_KING    = 12;
	
	static final int LABEL_ONE     = 1;
	static final int LABEL_TWO     = 2;
	static final int LABEL_THREE   = 3;
	static final int LABEL_FOUR    = 4;
	static final int LABEL_FIVE    = 5;
	static final int LABEL_SIX     = 6;
	static final int LABEL_SEVEN   = 7;
	static final int LABEL_EIGHT   = 8;

	static final int LABEL_A   = 9;
	static final int LABEL_B   = 10;
	static final int LABEL_C   = 11;
	static final int LABEL_D   = 12;
	static final int LABEL_E   = 13;
	static final int LABEL_F   = 14;
	static final int LABEL_G   = 15;
	static final int LABEL_H   = 16;
	
	static final String[] NAMES = {"Classics", "Letters"};
	static final int NUM_SETS = 2;
	
	// IMAGES 
	static BufferedImage[][] pieces; // 1st index -> set, 2nd index -> type
	// indexes are the same as associated constants
	static BufferedImage[] labels;
	
	// FIELDS
	int teamColor;
	int type;
	boolean hasMoved;
	
	// CONSTRUCTORS //
	Piece(int type) {
		this.type = type;
		if(type == 0)
			this.teamColor = EMPTY;
		else if(type < 7)
			this.teamColor = WHITE;
		else
			this.teamColor = BLACK;
		this.hasMoved = false;
	}
	
	// METHODS //
	static void loadImages(Frame frame) {
		int loaded = 0;
		int total = 40;
		// Chess sets
		pieces = new BufferedImage[NUM_SETS][BLACK_KING+1];
		int i = 0, j = 1;
		try {
			for(; i < NUM_SETS; i++) {
				for(j = 1; j <= BLACK_KING; j++) {
					File f = new File(String.format("img/%s/%d.png", NAMES[i].toLowerCase(), j));
					pieces[i][j] = ImageIO.read(f);
					loaded++;
				}
			}
		} catch(IOException e) {
			System.out.println(String.format("Image loading failed: set %d, piece %d", i+1, j));
		}
		
		// Labels
		labels = new BufferedImage[LABEL_H+1];
		i = 1;
		try {
			for(; i <= LABEL_H; i++) {
				File f = new File(String.format("img/labels/%d.png", i));
				labels[i] = ImageIO.read(f);
				loaded++;
			}
		} catch(IOException e) {
			System.out.println(String.format("Image loading failed: label %d", i));
		}
		
		System.out.println(String.format("%02.2f%% of images loaded.", (float)loaded/total*100));
	}
}

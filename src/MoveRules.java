/**
 * <code>MoveRules</code> class. This is not instantiated, only providing methods to determine if moves are valid.
 *
 * @author Chris W. Bao, Ben C. Megan
 * @version 0.1.5
 * @since 9 APR 2020
 */
abstract class MoveRules {
	// TODO: implement move rules for:
	/*
	- Pawn (and capture)
	- Knight
	- Bishop
	- Rook
	- Queen
	- King
	- Castle
	- Check/Checkmate
	- En Passant
	- Promotion
	 */
	/**
	 * Gets possible moves of a specific piece
	 * Calls a specific helper method based on the piece type
	 * @param board the current pieces on the board
	 * @param pieceRank the rank of the piece to move
	 * @param pieceFile the file of the piece to move
	 * @return the possible moves of the piece
	 */
	static boolean[][] getPossibleMoves(Piece[][] board, int pieceRank, int pieceFile) {
		Piece toMove = board[pieceRank][pieceFile];
		boolean[][] possibleMoves = new boolean[10][10];
		int pieceType = toMove.type;
		if(pieceType == Piece.BLACK_PAWN || pieceType == Piece.WHITE_PAWN)
			possibleMoves = getPawnMoves(board, pieceRank, pieceFile);
		else if(pieceType == Piece.BLACK_KNIGHT || pieceType == Piece.WHITE_KNIGHT)
			possibleMoves = getKnightMoves(board, pieceRank, pieceFile);
		else if(pieceType == Piece.BLACK_BISHOP || pieceType == Piece.WHITE_BISHOP)
			possibleMoves = getBishopMoves(board, pieceRank, pieceFile);
		else if(pieceType == Piece.BLACK_ROOK || pieceType == Piece.WHITE_ROOK)
		    possibleMoves = getRookMoves(board, pieceRank, pieceFile);
        else if(pieceType == Piece.BLACK_QUEEN || pieceType == Piece.WHITE_QUEEN)
            possibleMoves = getQueenMoves(board, pieceRank, pieceFile);
        else if(pieceType == Piece.BLACK_KING || pieceType == Piece.WHITE_KING)
            possibleMoves = getKingMoves(board, pieceRank, pieceFile);
		// TODO: Call helper methods (which will be written) to determine possible moves.
		return possibleMoves;
	}

	/**
	 * Finds possible moves of a pawn
	 * @param board the current pieces on the board
	 * @param rank the rank of the pawn
	 * @param file the file of the pawn
	 * @return the possible moves of the pawn
	 */
	static boolean[][] getPawnMoves(Piece[][] board, int rank, int file) {
		boolean[][] possibleMoves = new boolean[10][10]; // default value is false
		int pawnColor = board[rank][file].teamColor;
		if(pawnColor == Piece.BLACK) { // separation of black and white possible moves is only needed for pawns
			if(board[rank + 1][file].teamColor == Piece.EMPTY) { // move one square forward
				possibleMoves[rank + 1][file] = true;
				if(rank == 2 && board[rank + 2][file].teamColor == Piece.EMPTY) // move two squares forward
					possibleMoves[rank + 2][file] = true;
			}
			if(file != 1 && board[rank + 1][file - 1].teamColor != Piece.EMPTY
					&& pawnColor != board[rank + 1][file - 1].teamColor) // capture down and left
				possibleMoves[rank + 1][file - 1] = true;
			if(file != 8 && board[rank + 1][file + 1].teamColor != Piece.EMPTY
					&& pawnColor != board[rank + 1][file + 1].teamColor) // capture down and right
				possibleMoves[rank + 1][file + 1] = true;
		}
		else { // white pawn
			if(board[rank - 1][file].teamColor == Piece.EMPTY) { // move one square forward
				possibleMoves[rank - 1][file] = true;
				if(rank == 7 && board[rank - 2][file].teamColor == Piece.EMPTY) // move two squares forward
					possibleMoves[rank - 2][file] = true;
			}
			if(file != 1 && board[rank - 1][file - 1].teamColor != Piece.EMPTY
					&& pawnColor != board[rank - 1][file - 1].teamColor) // capture up and left
				possibleMoves[rank - 1][file - 1] = true;
			if(file != 8 && board[rank - 1][file + 1].teamColor != Piece.EMPTY
					&& pawnColor != board[rank - 1][file + 1].teamColor) // capture up and right
				possibleMoves[rank - 1][file + 1] = true;
		}
		return possibleMoves;
	}

	/**
	 * Finds possible moves of a knight
	 * @param board the current pieces on the board
	 * @param rank the rank of the knight
	 * @param file the file of the knight
	 * @return the possible moves of the knight
	 */
	static boolean[][] getKnightMoves(Piece[][] board, int rank, int file) {
		boolean[][] possibleMoves = new boolean[10][10];
		int knightColor = board[rank][file].teamColor;
		int[] xOffsets = new int[] {-2, -2, -1, -1, +1, +1, +2, +2};
		int[] yOffsets = new int[] {-1, +1, -2, +2, -2, +2, -1, +1};
		for(int i = 0; i < 8; i++) {
			int newRank = rank + xOffsets[i];
			int newFile = file + yOffsets[i];
			if(newRank >= 1 && newRank <= 8
				&& newFile >= 1 && newFile <= 8
				&& board[newRank][newFile].teamColor != knightColor)
				possibleMoves[newRank][newFile] = true;
		}

		return possibleMoves;
	}

	/**
	 * Finds possible moves of a bishop
	 * @param board the current pieces on the board
	 * @param rank the rank of the bishop
	 * @param file the file of the bishop
	 * @return the possible moves of the bishop
	 */
	static boolean[][] getBishopMoves(Piece[][] board, int rank, int file) {
		boolean[][] possibleMoves = new boolean[10][10];

		int rankTo = rank;
		int fileTo = file;
		int bishopColor = board[rank][file].teamColor;
		int[] xOffsets = new int[] {+1, +1, -1, -1};
		int[] yOffsets = new int[] {+1, -1, +1, -1};
		for(int i = 0; i < 4; i++) {
			rankTo = rank;
			fileTo = file;
			while(true) { // up and right
				rankTo += yOffsets[i];
				fileTo += xOffsets[i];
				if(rankTo >= 1 && rankTo <= 8 && fileTo >= 1 && fileTo <= 8) {
					if(board[rankTo][fileTo].teamColor + bishopColor == 3) { // OPPOSITE COLORS
						possibleMoves[rankTo][fileTo] = true;
						break;
					} else if(board[rankTo][fileTo].teamColor == Piece.EMPTY) { // EMPTY SQUARE
						possibleMoves[rankTo][fileTo] = true;
					} else { // SAME COLOR
						break;
					}
				} else {
					break;
				}
			}
		}
		return possibleMoves;
	}

    /**
     * Finds possible moves of a rook
     * @param board the current pieces on the board
     * @param rank the rank of the rook
     * @param file the file of the rook
     * @return the possible moves of the rook
     */
    static boolean[][] getRookMoves(Piece[][] board, int rank, int file) {
        boolean[][] possibleMoves = new boolean[10][10];

        int rankTo = rank;
        int fileTo = file;
        int rookColor = board[rank][file].teamColor;
        int[] xOffsets = new int[] {+1, -1, +0, +0};
        int[] yOffsets = new int[] {+0, +0, +1, -1};
        for(int i = 0; i < 4; i++) {
            rankTo = rank;
            fileTo = file;
            while(true) {
                rankTo += yOffsets[i];
                fileTo += xOffsets[i];
                if(rankTo >= 1 && rankTo <= 8 && fileTo >= 1 && fileTo <= 8) {
                    if(board[rankTo][fileTo].teamColor + rookColor == 3) { // OPPOSITE COLORS
                        possibleMoves[rankTo][fileTo] = true;
                        break;
                    } else if(board[rankTo][fileTo].teamColor == Piece.EMPTY) { // EMPTY SQUARE
                        possibleMoves[rankTo][fileTo] = true;
                    } else { // SAME COLOR
                        break;
                    }
                } else {
                    break;
                }
            }
        }
        return possibleMoves;
    }

    /**
     * Finds possible moves of a queen
     * @param board the current pieces on the board
     * @param rank the rank of the queen
     * @param file the file of the queen
     * @return the possible moves of the queen
     */
    static boolean[][] getQueenMoves(Piece[][] board, int rank, int file) {
        boolean[][] possibleMoves = new boolean[10][10];
        boolean[][] bishopMoves   = getBishopMoves(board, rank, file);
        boolean[][] rookMoves     = getRookMoves(board, rank, file);
        for(int rankTo = 1; rankTo <= 8; rankTo++) {
            for(int fileTo = 1; fileTo <= 8; fileTo++) {
                if(bishopMoves[rankTo][fileTo] || rookMoves[rankTo][fileTo])
                    possibleMoves[rankTo][fileTo] = true;
            }
        }
        return possibleMoves;
    }

    /**
     * Finds possible moves of a king
     * @param board the current pieces on the board
     * @param rank the rank of the king
     * @param file the file of the king
     * @return the possible moves of the king
     */
    static boolean[][] getKingMoves(Piece[][] board, int rank, int file) {
        boolean[][] possibleMoves = new boolean[10][10];

        int kingColor = board[rank][file].teamColor;
        int[] xOffsets = new int[] {-1, -1, -1, +0, +0, +1, +1, +1};
        int[] yOffsets = new int[] {-1, +0, +1, -1, +1, -1, +0, +1};
        for(int i = 0; i < 8; i++) {
            int rankTo = rank + yOffsets[i];
            int fileTo = file + xOffsets[i];
            if(rankTo >= 1 && rankTo <= 8 && fileTo >= 1 && fileTo <= 8
                && board[rankTo][fileTo].teamColor != kingColor)
                possibleMoves[rankTo][fileTo] = true;
        }

        return possibleMoves;
    }
}

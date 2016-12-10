package com.tiy;

/**
 * Created by erronius on 12/9/2016.
 */
public class BigBoard {

    SmallBoard[][] boardArray;
    SmallBoard statusBoard;

    private boolean tiedSquareCountsForBoth;

    public BigBoard () {
        tiedSquareCountsForBoth = true;
        statusBoard = new SmallBoard();
        boardArray = new SmallBoard[3][3];
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                boardArray[row][col] = new SmallBoard();
            }
        }
    }

    public boolean tiedSquaresCountForBoth () {
        return tiedSquareCountsForBoth;
    }

    public void changeTiedSquareRule () {
        tiedSquareCountsForBoth = !tiedSquareCountsForBoth;
        System.out.print("Wildcard Rule is now ");
        if (tiedSquareCountsForBoth) {
            System.out.println("ON");
        } else {
            System.out.println("OFF");
        }
    }

    public SmallBoard get (int row, int col) {
        return boardArray[row][col];
    }

    public String[] toStringArray () {
        String[] response = new String[19];

        int line = 0;
        for (; line < 5; line++) {
            response[line] = boardArray[0][0].toStringArray()[line] + "||" + boardArray[0][1].toStringArray()[line] + "||" + boardArray[0][2].toStringArray()[line];
        }
        response[line]  = "-------------------";
        line++;
        response[line]  = "-------------------";
        line++;
        for (; line < 12; line++) {
            response[line] = boardArray[1][0].toStringArray()[line-7] + "||" + boardArray[1][1].toStringArray()[line-7] + "||" + boardArray[1][2].toStringArray()[line-7];
        }
        response[line] = "-------------------";
        line++;
        response[line] = "-------------------";
        line++;
        for (; line < 19; line++) {
            response[line] = boardArray[2][0].toStringArray()[line-14] + "||" + boardArray[2][1].toStringArray()[line-14] + "||" + boardArray[2][2].toStringArray()[line-14];
        }
        response[2] += "    Key:";
        response[3] += "      123";
        response[4] += "      456";
        response[5] += "      789";

        response[8] += "    Overall Status Board";
        for (int line2 = 9; line2 < 14; line2++) {
            response[line2] += "      " + statusBoard.toStringArray()[line2-9];
        }
        return response;
    }

    public String toString () {
        String response = "";
        for (String line : this.toStringArray()) {
            response += line + "\n";
        }
        return response;
    }

    public boolean placeToken (int bigRow, int bigCol, int smallRow, int smallCol, char token) {
        if (boardArray[bigRow][bigCol].placeToken(smallRow, smallCol, token)) { //Attempts to place the token in the corresponding SmallBoard
            return true;
        }
        return false;
    }

    /**
     *
     * @return -1 if tied; 1 if 1, 0 if still going
     */

    public int statusUpdate () {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                char statusToken = boardArray[row][col].getStatusToken();
                if (statusToken == 'T' && tiedSquareCountsForBoth) {
                    statusBoard.placeToken(row, col, 'W');
                } else {
                    statusBoard.placeToken(row, col, statusToken);
                }
            }
        }
        char overallStatusToken = statusBoard.getStatusToken();
        if (overallStatusToken == 'T') {
            return -1;
        } else if (overallStatusToken != ' ') { //Not tied and not empty; must be a win
            return 1;
        }
        return 0;
    }

    //Clears entire board! Use with caution.
    public void clear () {
        System.out.println("Board cleared.");
        for (SmallBoard[] boardRow : boardArray) {
            for (SmallBoard smallBoard : boardRow) {
                smallBoard.clear();
            }
        }
    }
}

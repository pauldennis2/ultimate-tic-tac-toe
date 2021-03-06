package com.tiy;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by pauldennis on 12/11/2016.
 */
public class SmartBot extends Player {

    public static final String[] SMART_BOT_NAMES = {"Babbage", "Turing", "Hopper", "Torvalds", "Knuth", "Lovelace"};

    public static final int WINNING_STATUS_BOARD = 25;
    public static final int GAME_WINNING_MOVE = 50;

    public static final int CONTROL_MIDDLE_BOARD = 8;
    public static final int CONTROL_CORNER_BOARD = 6;
    public static final int CONTROL_EDGE_BOARD = 4;

    public static final int WINNING_MIDDLE_BOARD = 4;
    public static final int WINNING_CORNER_BOARD = 3;
    public static final int WINNING_EDGE_BOARD = 2;

    public static final int GAME_WON = 100000;

    public static final int THIS_ALGORITHM_BECOMES_SKYNET = Integer.MIN_VALUE;

    private int smartness;
    private char opponentToken;

    private BigBoard bigBoard;

    /**
     *
     * @param token the AI's token
     * @param smartness Not used yet. Meant to represent the depth of the search in turns.
     * @param opponentToken opponent's token, so we can evaluate boards
     * @param bigBoard a reference to the BigBoard the AI is on, so it can always see the whole picture
     */
    public SmartBot (char token, int smartness, char opponentToken, BigBoard bigBoard) {
        super(SMART_BOT_NAMES[new Random().nextInt(6)] + "Bot", token, PlayerType.SMARTBOT);
        this.smartness = smartness;
        this.opponentToken = opponentToken;
        this.bigBoard = bigBoard;
        if (bigBoard == null) {
            throw new AssertionError("SmartBot cannot be instantiated without an active BigBoard");
        }
    }

    /**
     * Not yet implemented. Required to extend abstract class Player
     * @param smallBoard the SmallBoard in which the AI must move
     * @return the location of the move in the given SmallBoard
     */
    public int getMove (SmallBoard smallBoard) {
        //return findBestMove(bigBoard, smartness);
        return -5;
    }

    /**
     * Not yet implemented. Required to extend abstract class Player
     * @param bigBoard the BigBoard to move in. Redundant in SmartBot since we already have a reference.
     * @return the SmallBoard to move in
     */
    public int getMove (BigBoard bigBoard) {
        //return findBestMove (bigBoard, smartness);
        return -5;
    }

    /**
     * Not yet fully implemented. Helper method to getMove, containing the actual logic. Probably will run recursively
     * until depth is 0.
     * @param bigBoard the BigBoard to find the best move in
     * @param depth remaining depth to search (in turns)
     * @return MoveLocation of our best move in the given BigBoard
     */
    private MoveLocation findBestMove (BigBoard bigBoard, int depth) {
        Node<BigBoard> root = new Node<BigBoard>(null, bigBoard);
        root.setChildren(bigBoard.getPossibleMoves(this.getToken()));
        for (Node<BigBoard> child : root.getChildren()) {
            child.setChildren(child.getMe().getPossibleMoves(opponentToken));
        }
        return null;
    }

    /**
     * The heuristic algorithm that evaluates the board. Rules:
     * <ul>
     * <li>This algorithm is symmetric - we are playing a zero-sum game, so what's good for our opponent is bad for us
     * and vice-versa.</li>
     * <li>This algorithm does not attempt to evaluate future moves, except to the extent of evaluating possible
     * winning moves. That's the job of the Node/Tree structure and findBestMove()</li>
     * </ul>
     * @param bigBoard the board to evaluate.
     * @return The score associated with this board. A positive score indicates an edge for the AI doing the evaluating,
     * a negative score is an edge for the opponent (who could also be an AI - doesn't matter).
     */
    public int evaluateBoard (BigBoard bigBoard) {
        int score = 0;
        SmallBoard statusBoard = bigBoard.getStatusBoard();

        boolean wildcards = bigBoard.tiedSquaresCountForBoth();
        char statusBoardStatusToken;
        if (wildcards) {
            //check to make sure order doesn't matter
            statusBoardStatusToken = statusBoard.getStatusTokenWithWildCards(this.getToken(), opponentToken);
        } else {
            statusBoardStatusToken = statusBoard.getStatusToken();
        }

        //Check to see if the game is won or lost. To save CPU cycles we'll return right away if so.
        if (statusBoardStatusToken == this.getToken()) {
            return GAME_WON;
        } else if (statusBoardStatusToken == opponentToken) {
            return -GAME_WON;
        }


        int statusBoardScore = evaluateStatusBoard(bigBoard);
        int controlScore = evaluateBoardControl(bigBoard);
        int winningScore = evaluateBoardWinning(bigBoard);

        score += controlScore;
        score += winningScore;
        score += statusBoardScore;

        System.out.println("control = " + controlScore);
        System.out.println("winning = " + winningScore);
        System.out.println("statusBoard = " + statusBoardScore);
        return score;
    }

    public int evaluateStatusBoard (BigBoard bigBoard) {
        //Check to see if anyone has a winning move on the statusBoard.
        // If so, check further to see if they have a winning move in the specific smallBoard they would need to win
        //If opponent has a winning move in the smallBoard we need to win, take the points away
        SmallBoard statusBoard = bigBoard.getStatusBoard();
        int statusBoardScore = 0;
        int myStatusBoardWinningMove = tokenHasWinningMove(statusBoard, this.getToken());
        int opponentStatusBoardWinningMove = tokenHasWinningMove(statusBoard, opponentToken);
        if (myStatusBoardWinningMove > 0) {
            statusBoardScore += WINNING_STATUS_BOARD;
            int row = (myStatusBoardWinningMove - 1) / 3;
            int col = (myStatusBoardWinningMove - 1) % 3;
            SmallBoard boardNeededForWin = bigBoard.get(row, col);
            int mySmallBoardWinningMove = tokenHasWinningMove(boardNeededForWin, this.getToken());
            int opponentSmallBoardBlockingMove = tokenHasWinningMove(boardNeededForWin, opponentToken);
            if (mySmallBoardWinningMove > 0) {
                statusBoardScore += GAME_WINNING_MOVE;
            }
            if (opponentSmallBoardBlockingMove > 0) {
                statusBoardScore -= WINNING_STATUS_BOARD;
            }
        }

        if (opponentStatusBoardWinningMove > 0) {
            statusBoardScore -= WINNING_STATUS_BOARD;
            int row = (opponentStatusBoardWinningMove - 1) / 3;
            int col = (opponentStatusBoardWinningMove - 1) % 3;
            SmallBoard boardNeededForWin = bigBoard.get(row, col);
            int mySmallBoardBlockingMove = tokenHasWinningMove(boardNeededForWin, this.getToken());
            int opponentSmallBoardWinningMove = tokenHasWinningMove(boardNeededForWin, opponentToken);
            if (mySmallBoardBlockingMove > 0) {
                statusBoardScore += GAME_WINNING_MOVE;
            }
            if (opponentSmallBoardWinningMove > 0) {
                statusBoardScore -= WINNING_STATUS_BOARD;
            }
        }
        return statusBoardScore;
    }
    //Control means boards we have won
    public int evaluateBoardControl (BigBoard bigBoard) {
        int score = 0;
        SmallBoard statusBoard = bigBoard.getStatusBoard();

        /*if (statusBoard.get(1, 1) == this.getToken()) {
            score += CONTROL_MIDDLE_BOARD;
        } else if (statusBoard.get(1, 1) == opponentToken) {
            score -= CONTROL_MIDDLE_BOARD;
        }*/
        char middleBoardStatusToken = bigBoard.get(1, 1).getStatusToken();
        if (middleBoardStatusToken == this.getToken()) {
            score += CONTROL_MIDDLE_BOARD;
        } else if (middleBoardStatusToken == opponentToken) {
            score -= CONTROL_MIDDLE_BOARD;
        }

        for (SmallBoard smallBoard : bigBoard.getCornerBoards()) {
            if (smallBoard.getStatusToken() == this.getToken()) {
                score += CONTROL_CORNER_BOARD;
            } else if (smallBoard.getStatusToken() == opponentToken) {
                score -= CONTROL_CORNER_BOARD;
            }
        }
        for (SmallBoard smallBoard : bigBoard.getEdgeBoards()) {
            if (smallBoard.getStatusToken() == this.getToken()) {
                score += CONTROL_EDGE_BOARD;
            } else if (smallBoard.getStatusToken() == opponentToken) {
                score -= CONTROL_EDGE_BOARD;
            }
        }
        return score;
    }
    //Winning means boards that we could win with one move
    public int evaluateBoardWinning (BigBoard bigBoard) {
        int score = 0;
        SmallBoard statusBoard = bigBoard.getStatusBoard();
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (statusBoard.get(row, col) == ' ') { //We only want to evaluate un-won boards
                    SmallBoard smallBoard = bigBoard.get(row, col);
                    if (tokenHasWinningMove(smallBoard, this.getToken()) > 0) {
                        int boardType = getBoardType(row, col);
                        switch (boardType) {
                            case 0://middle
                                score += WINNING_MIDDLE_BOARD;
                                break;
                            case 1://Edge
                                score += WINNING_EDGE_BOARD;
                                break;
                            case 2://Corner
                                score += WINNING_CORNER_BOARD;
                                break;
                            default:
                                System.out.println("Error in SmartBot.getBoardType()");
                                break;
                        }
                    }//@// TODO: 12/12/2016 clean this up
                    if (tokenHasWinningMove(smallBoard, opponentToken) > 0) {
                        int boardType = getBoardType(row, col);
                        switch (boardType) {
                            case 0://middle
                                score -= WINNING_MIDDLE_BOARD;
                                break;
                            case 1://Edge
                                score -= WINNING_EDGE_BOARD;
                                break;
                            case 2://Corner
                                score -= WINNING_CORNER_BOARD;
                                break;
                            default:
                                System.out.println("Error in SmartBot.getBoardType()");
                                break;
                        }
                    }
                    // make it multiply by whether its opponent is winning or not
                }
            }
        }
        return score;
    }

    /*  If the player does have a winning move, returns the move in the form:
            123
            456
            789
        Else returns -1
        Will return the first winning move it finds starting at top left and reading down, the middle row and down, etc.
    */
    public static int tokenHasWinningMove (SmallBoard smallBoard, char token) {
        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 3; column++) {
                if (tokenHasWinningMoveAt(row, column, smallBoard, token)) {
                    int response = row*3 + column + 1;
                    return response;
                }
            }
        }
        return -1;
    }

    /**
     *
     * @param row - row of the move in question
     * @param column - column of the move in question
     * @return True if the token can win with the given move; false otherwise;
     */
    public static boolean tokenHasWinningMoveAt (int row, int column, SmallBoard smallBoard, char token) {
        char[][] board = smallBoard.getBoard();
        char[][] theoreticalBoard = {{board[0][0], board[0][1], board[0][2]},
                {board[1][0], board[1][1], board[1][2]},
                {board[2][0], board[2][1], board[2][2]}};

        if (theoreticalBoard[row][column] == ' ') {
            theoreticalBoard[row][column] = token;
        }
        SmallBoard theoreticalSmallBoard = new SmallBoard(theoreticalBoard);

        if (theoreticalSmallBoard.getStatusToken() == token) {
            return true;
        } else {
            return false;
        }
    }
    //Returns 0 for middle, 1 for edge, 2 for corner
    public static int getBoardType (int row, int col) {
        if (row == 1 && col == 1) {
            return 0;
        }
        //Edge boards are 0,1 1,0 1,2 2,1
        if (row == 1 || col == 1) {
            return 1;
        }
        //Corner boards are 0,0 0,2 2,0 2,2
        return 2;
    }
}

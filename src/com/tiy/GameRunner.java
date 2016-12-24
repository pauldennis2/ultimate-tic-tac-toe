package com.tiy;

import java.util.Random;

/**
 * Runner class for the Ultimate Tic Tac Toe Game.
 * Created by pauldennis around 12/9/16
 */
public class GameRunner {

    SafeScanner scanner;

    BigBoard bigBoard;
    SmallBoard smallBoard;

    Player player1;
    Player player2;

    int numHumPlayers;

    public static void main(String[] args) {
        GameRunner runner = new GameRunner();
        //runner.testSmartBotEvaluation();
        runner.playGame();
        //runner.aiTest();
    }

    public GameRunner() {
        scanner = new SafeScanner(System.in);
    }

    /**
     * This method initializes the Player objects and passes off to playUltimateTicTacToe().
     */
    public void playGame () {
        System.out.println("Welcome to Ultimate Tic Tac Toe.");
        boolean boring = false;

        System.out.println("Input number of human players (0, 1, or 2):");
        numHumPlayers = scanner.nextIntInRange(0, 2);


        switch (numHumPlayers) {
            case 0:
                player1 = new DumbBot('X');
                player2 = new DumbBot('O');
                break;
            case 1:
                System.out.println("Player 1 please enter name:");
                player1 = new HumanPlayer(scanner.nextStringSafe(), 'X');
                player2 = new DumbBot('O');
                break;
            case 2:
                System.out.println("Player 1 please enter name:");
                player1 = new HumanPlayer(scanner.nextStringSafe(), 'X');
                System.out.println("Player 2 please enter name:");
                player2 = new HumanPlayer(scanner.nextStringSafe(), 'O');
                break;
            default:
                System.out.println("Error");
                player1 = null;
                player2 = null;
                break;
        }
        //Reminder: 'W' and 'T' are not allowed as tokens. Any other characters should be OK if we want to go back
        //And allow the user to pick their token.
        playUltimateTicTacToe();
    }

    /**
     * This is the main game method, including the main game loop.
     */
    public void playUltimateTicTacToe () {
        System.out.println("Welcome to ULTIMATE Tic Tac Toe.");
        System.out.println("You've made a much more interesting choice.");
        System.out.println("Player 1: " + player1.getName() + " (" + player1.getToken() + ")");
        System.out.println("Player 2: " + player2.getName() + " (" + player2.getToken() + ")");

        bigBoard = new BigBoard();
        GameRecord record = new GameRecord(player1, player2);

        int currentSmallBoard = 0;
        Player activePlayer = player1;
        Player nonActivePlayer = player2; //ApNap!
        boolean playing = true;
        while (playing) {
            System.out.println(activePlayer.getName() + "'s turn.");
            if (numHumPlayers > 0) {
                System.out.println(bigBoard);
            }
            if (currentSmallBoard == 0) {//can move anywhere
                currentSmallBoard = activePlayer.getMove(bigBoard);
            }
            if (currentSmallBoard == -1) {
                System.out.println("Have a nice day!");
                return;
            }
            //currentSmallBoard should be between 1 and 9
            int row = (currentSmallBoard - 1) / 3;
            int col = (currentSmallBoard - 1) % 3;

            SmallBoard activeSmallBoard = bigBoard.get(row, col);
            System.out.println("You are on board " + currentSmallBoard);
            int smallBoardMoveLoc = activePlayer.getMove(activeSmallBoard);
            if (smallBoardMoveLoc == -1) {
                System.out.println("Have a nice day!");
                return;
            }
            int smallRow = (smallBoardMoveLoc - 1) / 3;
            int smallCol = (smallBoardMoveLoc - 1) % 3;

            bigBoard.placeToken(row, col, smallRow, smallCol, activePlayer.getToken());
            record.addTurn(row, col, smallRow, smallCol);


            switch (bigBoard.statusUpdate()) {
                case -1:
                    System.out.println("Game is tied.");
                    record.addResult("tied");
                    record.writeToFile();
                    playing = false;
                    break;
                case 0:
                    //logic to determine next small board
                    currentSmallBoard = smallBoardMoveLoc;
                    if (bigBoard.get(smallRow, smallCol).getStatusToken() != ' ') {//if the board is won or tied
                        currentSmallBoard = 0; //This means they can pick anywhere
                    }
                    break;
                case 1:
                    System.out.println(activePlayer.getName() + " wins!");
                    record.addResult(activePlayer.getName() + " won");
                    record.writeToFile();
                    playing = false;
                    break;
            } //End switch(bigBoard.statusupdate())
            //Swap activePlayer
            activePlayer = nonActivePlayer;
            if (activePlayer.equals(player1)) {
                nonActivePlayer = player2;
            } else {
                nonActivePlayer = player1;
            }
            if (numHumPlayers == 0) {
                System.out.println(bigBoard);
            }
        }//End main game loop
    }

    /**
     * Written before we discussed unit tests, this is a way to see if the computer can correctly detect wildcard wins.
     * Does not quite work yet. The method prints out some random boards and allows the tester to see if the
     * getStatusTokenWithWildcards method returns the correct value (as interpreted by the human tester).
     */
    public void testWildCardDetection () {
        System.out.println("Testing wildcard detection levels with 6 random examples");
        Random random = new Random();
        SmallBoard[] boards = new SmallBoard[6];
        for (int i = 0; i < 6; i++) {
            char[][] board = new char[3][3];
            for (int row = 0; row < 3; row++) {
                for (int col = 0; col < 3; col++) {
                    int randomNum = random.nextInt(3);
                    switch (randomNum) {
                        case 0:
                            if (random.nextBoolean()) {
                                board[row][col] = 'W';
                            } else {
                                board[row][col] = ' ';
                            }
                            break;
                        case 1:
                            board[row][col] = 'X';
                            break;
                        case 2:
                            board[row][col] = 'O';
                            break;
                    }
                    boards[i] = new SmallBoard(board);

                }
            }
            System.out.println("Board " + i + ":");
            System.out.println(boards[i]);
            System.out.println("Returns:" + boards[i].getStatusTokenWithWildCards('X', 'O'));
        }
    }

    /**
     * As testWildCardDetection(), written before we discussed unit tests. The method prints out random BigBoards and/or
     * SmallBoards with what the AI thinks the score of that board should be. This allows a human tester to compare
     * these values with what they would expect the value to be, based on knowledge of the heuristic.
     */
    public void testSmartBotEvaluation () {
        BigBoard big = new BigBoard();
        SmartBot smarty = new SmartBot('O', 0,'X', big);
        System.out.println(smarty.getName() + " (O) will evaluate random boards.");
        for (int i = 0; i < 4; i++) {
            BigBoard bigBoard = BigBoard.createRandomBoard(85);
            System.out.println(bigBoard);
            System.out.println(smarty.getName() + "'s evaluation of this board:" + smarty.evaluateBoard(bigBoard));
        }
        /*for (int i = 0; i < 6; i++) {
            SmallBoard smallBoard = GameRunner.createRandomSmallBoard(7);
            System.out.println(smallBoard);
            System.out.println(smarty.getName() + "'s evaluation of this board:");
            System.out.println("X has winning move at :" + SmartBot.tokenHasWinningMove(smallBoard, 'X') + " (-1 if no winning move)");
            System.out.println("O has winning move at :" + SmartBot.tokenHasWinningMove(smallBoard, 'O') + " (-1 if no winning move)");
        }*/
    }

    /**
     * This method is equivalent to the playGame -> playUltimateTicTacToe. It starts a game between two AI players.
     * This is a testing/sandbox method that is not part of the main program structure. Work in progress.
     */
    public void aiTest() {
        System.out.println("Starting a game with 2 AI players.");
        bigBoard = new BigBoard();
        player1 = new DumbBot('X');
        player2 = new SmartBot('O', 0, 'X', bigBoard);
        System.out.println("Player 1: " + player1.getName() + " (" + player1.getToken() + ")");
        System.out.println("Player 2: " + player2.getName() + " (" + player2.getToken() + ")");
        boolean playing = true;
        int currentSmallBoardNum = 0;
        while (playing) {
            //For simplicity we'll just run two turns per loop, so we always know whether we're smart or dumb
            //Later we make this prettier with the activePlayer / nonActivePlayer structure
            System.out.println(player1.getName() + "'s turn.");
            if (currentSmallBoardNum == 0) {//can move anywhere
                currentSmallBoardNum = player1.getMove(bigBoard);
            }
            //currentSmallBoardNum should be between 1 and 9
            int row = (currentSmallBoardNum - 1) / 3;
            int col = (currentSmallBoardNum - 1) % 3;

            SmallBoard activeSmallBoard = bigBoard.get(row, col);
            int smallBoardMoveLoc = player1.getMove(activeSmallBoard);
            int smallRow = (smallBoardMoveLoc - 1) / 3;
            int smallCol = (smallBoardMoveLoc - 1) % 3;

            bigBoard.placeToken(row, col, smallRow, smallCol, player1.getToken());


            switch (bigBoard.statusUpdate()) {
                case -1:
                    System.out.println("Game is tied.");
                    playing = false;
                    break;
                case 0:
                    //logic to determine next small board
                    currentSmallBoardNum = smallBoardMoveLoc;
                    if (bigBoard.get(smallRow, smallCol).getStatusToken() != ' ') {//if the board is won or tied
                        currentSmallBoardNum = 0; //This means they can pick anywhere
                    }
                    break;
                case 1:
                    System.out.println(player1.getName() + " wins!");
                    System.out.println(bigBoard);
                    playing = false;
                    break;
            } //End switch(bigBoard.statusUpdate())

            //Player 2's turn
            if (currentSmallBoardNum == 0) {
                //This is a harder case, we'll tackle this second
                Node<BigBoard> root = new Node<BigBoard>(null, bigBoard);
                //root.setChildren(bigBoard.getPossibleMoves());
            }
            //Current board selected
            Node<BigBoard> root = new Node<BigBoard>(null, bigBoard);
            //root.setChildren(bigBoard.getPossibleMoves(currentSmallBoardNum, player2.getToken()));
            root.setChildren(bigBoard.getPossibleMoves(player2.getToken()));

        }//End main game loop
    }


}

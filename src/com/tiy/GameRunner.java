package com.tiy;

public class GameRunner {

    SafeScanner scanner;

    BigBoard bigBoard;
    SmallBoard smallBoard;

    Player player1;
    Player player2;

    int numHumPlayers;

    public static void main(String[] args) {
        new GameRunner().playGame();
    }

    public GameRunner() {
        scanner = new SafeScanner(System.in);
    }


    public void playGame () {
        System.out.println("Welcome to (Ultimate) Tic Tac Toe.");
        System.out.println("Puss out and play regular TTT?");
        boolean boring = scanner.nextYesNoAnswer();

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
        if (boring) {
            playRegularTicTacToe();
        } else {
            playUltimateTicTacToe();
        }
    }

    public void playRegularTicTacToe () {
        System.out.println("Welcome to Regular Old Tic Tac Toe.");
        System.out.println("Player 1: " + player1.getName() + " (" + player1.getToken() + ")");
        System.out.println("Player 2: " + player2.getName() + " (" + player2.getToken() + ")");
        smallBoard = new SmallBoard();
        System.out.println(smallBoard);
    }

    public void playUltimateTicTacToe () {
        System.out.println("Welcome to ULTIMATE Tic Tac Toe.");
        System.out.println("You've made a much more interesting choice.");
        System.out.println("Player 1: " + player1.getName() + " (" + player1.getToken() + ")");
        System.out.println("Player 2: " + player2.getName() + " (" + player2.getToken() + ")");

        bigBoard = new BigBoard();
        GameRecord record = new GameRecord(player1, player2);

        int currentSmallBoard = 0;
        Player activePlayer = player1;
        Player nonActivePlayer = player2;
        while (true) {
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
            int smallBoardMoveLoc = player1.getMove(activeSmallBoard);
            if (smallBoardMoveLoc == -1) {
                System.out.println("Have a nice day!");
                return;
            }
            int smallRow = (smallBoardMoveLoc - 1) / 3;
            int smallCol = (smallBoardMoveLoc - 1) % 3;

            bigBoard.placeToken(row, col, smallRow, smallCol, activePlayer.getToken());
            record.addTurn(row, col, smallRow, smallCol);
            //Swap activePlayer
            activePlayer = nonActivePlayer;
            if (activePlayer.equals(player1)) {
                nonActivePlayer = player2;
            } else {
                nonActivePlayer = player1;
            }

            switch (bigBoard.statusUpdate()) {
                case -1:
                    System.out.println("Game is tied.");
                    record.addResult("tied");
                    record.writeToFile();
                    break;
                case 0:
                    //logic to determine next small board
                    currentSmallBoard = smallBoardMoveLoc;
                    if (bigBoard.get(smallRow, smallCol).getStatusToken() != ' ') {//if the board is not won or tied
                        currentSmallBoard = 0; //This means they can pick anywhere
                    }
                    break;
                case 1:
                    System.out.println(activePlayer.getName() + " wins!");
                    record.addResult(activePlayer.getName() + " won");
                    record.writeToFile();
                    break;
            }
        }
    }
}

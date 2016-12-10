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

        int currentSmallBoard = 0;
        System.out.println(player1.getName() + "'s turn.");
        if (currentSmallBoard == 0) {//can move anywhere
            currentSmallBoard = player1.getMove(bigBoard);
        }
        if (currentSmallBoard == -1) {
            System.out.println("Have a nice day!");
            return;
        }
        //currentSmallBoard should be between 1 and 9
        int row = (currentSmallBoard - 1) / 3;
        int col = (currentSmallBoard - 1) % 3;

        SmallBoard active = bigBoard.get(row, col);
        int playerMove = player1.getMove(active);
        if (playerMove == -1) {
            System.out.println("Have a nice day!");
            return;
        }
        playerMove--;
        int smallRow = playerMove / 3;
        int smallCol = playerMove % 3;

        bigBoard.placeToken(row, col, smallRow, smallCol, player1.getToken());
        System.out.println(bigBoard);
    }
}

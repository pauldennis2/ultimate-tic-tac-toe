package com.tiy;

import java.util.Scanner;

/**
 * Created by erronius on 12/9/2016.
 */
public class HumanPlayer extends Player {

    SafeScanner scanner;

    Scanner moveInputScanner; //we need a non-sanitized scanner to handle user input so that we can properly exit and etc

    private int numTimesHelpTriggered;

    private static boolean rulesDisplayed = true;//Yes this really is static

    public HumanPlayer (String name, char token) {
        super(name, token, "human");
        scanner = new SafeScanner(System.in);
        moveInputScanner = new Scanner(System.in);
    }

    public int getMove (SmallBoard smallBoard) {
        System.out.println(smallBoard);
        System.out.println("Please enter square number, or keyword (rules, help, exit)");
        String response = null;
        int row = 0;
        int col = 0;
        do {
            try {
                response = moveInputScanner.nextLine();
                int userMove = Integer.parseInt(response);
                if (userMove > 9 || userMove < 1) {
                    System.out.println("Great. You broke it.");
                    assert(false);
                }
                userMove--; //computerized to 0-8;

                //Check with the board to make sure it's not full.
                row = userMove/3;
                col = userMove%3;
                if (smallBoard.get(row, col) != ' ') {
                    System.out.println("Can't move there - already taken.");
                }
            } catch (NumberFormatException ex) {
                if (response.equals("exit")) {
                    return -1;
                } else if (response.equals("rules")){
                    this.printGameRules();
                } else { //We interpret anything else as a cry for help
                    this.printGameHelpInfo();
                }
            }
        } while (smallBoard.get(row, col) != ' ');
        int answer = row*3 + col + 1;
        return answer;
    }

    public int getMove (BigBoard bigBoard) {
        if (!rulesDisplayed) {
            this.printGameHelpInfo();
            this.printGameRules();
        }
        System.out.println("You can move anywhere. Please tell me the big board number first.");
        System.out.println("Please enter board number, or keyword (rules, help, exit)");
        String response = null;
        int row = 0;
        int col = 0;
        char statusTokenOfDesiredBoard;
        do {
            try {
                response = moveInputScanner.nextLine();
                int userMove = Integer.parseInt(response);
                if (userMove > 9 || userMove < 1) {
                    System.out.println("Great. You broke it.");
                    assert(false);
                }
                userMove--; //computerized to 0-8;

                //Check with the board to make sure it's not full.
                row = userMove/3;
                col = userMove%3;

            } catch (NumberFormatException ex) {
                if (response.equals("exit")) {
                    return -1;
                } else if (response.equals("rules")){
                    this.printGameRules();
                } else { //We interpret anything else as a cry for help
                    this.printGameHelpInfo();
                }
            }
            statusTokenOfDesiredBoard = bigBoard.get(row, col).getStatusToken();
        } while (statusTokenOfDesiredBoard == 'T' || statusTokenOfDesiredBoard == 'W'); //if the board selected is full, send them back

        int answer = row*3 + col + 1;
        return answer;
    }

    public void printGameHelpInfo() {
        System.out.println("Welcome to Ultimate Tic Tac Toe instructions/help info.");
        System.out.println("Navigating this program:");
        System.out.println("You will be asked to enter your move as a number 1-9.");
        System.out.println("The number corresponds to the square/block you want to move, as follows");
        System.out.println("\t123\n\t456\n\t789");
        System.out.println("If you enter \"exit\" (without quotes) the program will exit.");
        System.out.println("If you enter \"help\" you will see this message again.");
        System.out.println("If you enter \"rules\" you will see the rules.");
        numTimesHelpTriggered++;
        if (numTimesHelpTriggered > 3) {
            System.out.println("You seem to be having trouble. Please contact Paul <Address Redacted> for help.");
        }
    }

    public void printGameRules() {
        System.out.println("Welcome to Ultimate Tic Tac Toe rules.");
        System.out.println("Ultimate Tic-Tac-Toe is a variant of Tic-Tac-Toe (TTT).");
        System.out.println("The Ultimate TTT board consists of 9 squares, each containing a regular TTT board (\"small board\" or just \"board\").");
        System.out.println("Here it is:");
        System.out.println(new BigBoard());
        System.out.println("The objective is to connect 3 by winning the small boards.");
        System.out.println("The first player can move anywhere; however, after that players are restricted");
        System.out.println("to moving in the square whose position on the big board corresponds to");
        System.out.println("the position of their opponent's move in the small board.");
        System.out.println("Source: https://mathwithbaddrawings.com/2013/06/16/ultimate-tic-tac-toe/");
        System.out.println();
        scanner.waitForInput();
        if (rulesDisplayed) {
            System.out.println("Display example?");
            if (scanner.nextYesNoAnswer()) {
                showRulesExample();
            }
        } else {
            showRulesExample();
        }
        System.out.println("Additional rules:");
        System.out.println("If your opponent's move places you in a board that's already won, you may move anywhere");
        System.out.println("on the big board (including in the small won board, though that is probably a bad idea).");
        System.out.println("Optional Rule: Wildcard");
        System.out.println("If this rule is on, any small board that is full (all nine spots) counts for both players.");
        System.out.println("This can theoretically generate a tie. In CPU VS Human matches this goes to the computer as a win.");
        System.out.println("Otherwise it is a tie, even stevens, fair and square, equal.");
        System.out.println("If Wildcard Rule is on, tied squares will display with a 'W'.");
        System.out.print("Wildcard Rule is currently: something");
        /*if (bigBoard.tiedSquaresCountForBoth()) {
            System.out.println("ON");
        } else {
            System.out.println("OFF");
        }
        System.out.println("Would you like to change this setting?");
        if (scanner.continueNoChanges()) {
        } else {
            bigBoard.changeTiedSquareRule();
            System.out.println("Rule changed");
        }*/
        scanner.waitForInput();
        rulesDisplayed = true;
    }

    public void showRulesExample () {
        BigBoard displayBoard = new BigBoard();
        System.out.println("If Player 1 places their X here:");
        displayBoard.placeToken(0,1,1,1, 'X');
        System.out.println(displayBoard);
        System.out.println("Player 2 could move anywhere in the middle box:");

        displayBoard.placeToken(1,1,0,0, 'O');
        displayBoard.placeToken(1,1,0,1, 'O');
        displayBoard.placeToken(1,1,0,2, 'O');
        displayBoard.placeToken(1,1,1,0, 'O');
        displayBoard.placeToken(1,1,1,1, 'O');
        displayBoard.placeToken(1,1,1,2, 'O');
        displayBoard.placeToken(1,1,2,0, 'O');
        displayBoard.placeToken(1,1,2,1, 'O');
        displayBoard.placeToken(1,1,2,2, 'O');

        System.out.println(displayBoard);

        scanner.waitForInput();

        displayBoard.clear();
        displayBoard.placeToken(0,1,1,1, 'X');

        System.out.println("Let's say Player 2 places their token in the top left square of the middle board.");
        displayBoard.placeToken(1,1,0,0, 'O');
        System.out.println("Now Player 1 must move in the top left board.");
        displayBoard.placeToken(0,0,0,0, 'X');
        displayBoard.placeToken(0,0,0,1, 'X');
        displayBoard.placeToken(0,0,0,2, 'X');
        displayBoard.placeToken(0,0,1,0, 'X');
        displayBoard.placeToken(0,0,1,1, 'X');
        displayBoard.placeToken(0,0,1,2, 'X');
        displayBoard.placeToken(0,0,2,0, 'X');
        displayBoard.placeToken(0,0,2,1, 'X');
        displayBoard.placeToken(0,0,2,2, 'X');
        System.out.println(displayBoard);
        scanner.waitForInput();
    }


}

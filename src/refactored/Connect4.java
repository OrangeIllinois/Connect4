package refactored;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Random;
import java.util.Scanner;

public class Connect4 {

    int numCol = 7;

    int numRow = 6;

    static final int NUM = 4;

    static final int EMPTY = 0;

    static final int RED = 1;

    static final int BLUE = 2;

    static final int DRAW = 3;

    int[][] board = new int[numRow][numCol];

    int turn = RED;

    /*
        Checks whether there are any valid moves
        :return: True if the player is able to put a piece there; false otherwise.
     */
    boolean isFull() {

        for (int i = 0; i < numCol; i++) {

            if (canPut(turn, i)) {

                return true;
            }
        }

        return false;
    }

    // solved code smell: Duplicate Code
    // Reason: canPut and put has many common codes
    // Solution: create a helper method putHelper and let
    // canPut and put invoke this method
    boolean putHelper(int player, int col, boolean actualPut) {

        int i;

        if (col < 0 || col >= numCol) {

            return false;
        }


        for (i = numRow - 1; i >= 0; i--) {

            if (board[i][col] == EMPTY) {

                break;
            }
        }

        if (i < 0) {

            return false;
        }

        if (actualPut) {
            board[i][col] = player;
        }

        return true;
    }

    /*
        Checks to see whether or not a piece can be placed within column 'col
        :param player<int>: Player 1 or player 2
        :param col<int>: Column to attempt to place a piece
        :return<bool>: True if the piece can be placed; false otherwise
     */
    boolean canPut(int player, int col) {

        return putHelper(player, col, false);
    }

    /*
        Actually places the piece column 'col'
        :param player<int>: Player 1 or player 2
        :param col<int>: Column to place the piece
        :return<bool>: True if the piece can be placed; false otherwise
        :comments: This function should always return true
     */
    boolean put(int player, int col) {

        return putHelper(player, col, true);
    }

    boolean isInRagne(int row, int col) {

        return row >= 0 && row < numRow && col >= 0 && col < numCol;
    }

    // solved code smell: Duplicate Code, Long Method and Too hard to understand
    // Reason: many duplicated code in method checkWinner
    //         This method is also too long and too complicated to understand
    // Solution: create a helper method check and invoke
    // this method in checkWinner

    boolean check(int i, int j, int dr, int dc) {
        int player = board[i][j];

        int count = 1;

        for (int newRow = i + dr, newCol = j + dc; isInRagne(newRow, newCol); newRow += dr, newCol += dc) {

            if (board[newRow][newCol] == player) {

                count++;

            } else {

                break;
            }
        }

        return count >= NUM;
    }

    int checkWinner() {

        for (int i = 0; i < numRow; i++) {


            for (int j = 0; j < numCol; j++) {

                int player = board[i][j];

                if (player == EMPTY) {

                    continue;
                }

                // check in different directions
                int[] drs = new int[]{0, 1, 1, 1};

                int[] dcs = new int[]{1, 1, 0, -1};

                for (int k = 0; k < 4; k++) {

                    int dr = drs[k];
                    int dc = dcs[k];

                    if (check(i, j, dr, dc)) {

                        return board[i][j];
                    }
                }
            }
        }

        if (!isFull()) { // Check if the board is full
            return DRAW;
        }


        return EMPTY;
    }

    void printBoard() {
        for (int i = 0; i < numRow; i++) {

            for (int j = 0; j < numCol; j++) {

                if (board[i][j] == EMPTY) {
                    System.out.print("*");

                } else if (board[i][j] == RED) {

                    System.out.print("R");
                } else {

                    System.out.print("B");
                }
            }

            System.out.println();
        }

        System.out.println("1234567");

        System.out.println();
    }

//    void runWith2Player() {
//
//        Scanner in = new Scanner(System.in);
//
//        while (true) {
//            printBoard();
//
//            int win = checkWinner();
//
//            if (win == RED) {
//
//                System.out.println("Red wins!");
//
//                break;
//
//            } else if (win == BLUE) {
//
//                System.out.println("Blue wins!");
//
//                break;
//
//            } else if (win == DRAW) {
//
//                System.out.println("It is draw!");
//
//                break;
//
//
//            }
//
//            System.out.print((turn == RED ? "Red" : "Blue") + " Player, ");
//            System.out.print("Input the column to put: ");
//
//            String line = in.nextLine();
//
//            int col = Integer.parseInt(line) - 1;
//
//            if (!put(turn, col)) {
//
//
//                System.out.println("Invalid");
//
//            } else {
//
//                if (turn == RED) {
//
//                    turn = BLUE;
//                } else {
//
//                    turn = RED;
//                }
//
//
//            }
//        }
//
//    }

    int getInput(boolean computer) {
        if (computer) {

            // solved code smell: Temporary Fields
            // change random instance variable to local variable
            Random random = new Random();

            while (true) {
                int col = random.nextInt(numCol);

                if (canPut(turn, col)) {

                    System.out.print((turn == RED ? "Red" : "Blue") + " Player, ");
                    System.out.println("Input the column to put: " + col);
                    return col;
                }
            }


        } else {

            Scanner in = new Scanner(System.in);

            System.out.print((turn == RED ? "Red" : "Blue") + " Player, ");
            System.out.print("Input the column to put: ");

            String line = in.nextLine();

            int col = Integer.parseInt(line) - 1;

            if (canPut(turn, col)) {

                return col;

            } else {

                System.out.println("input is not valid");
                return getInput(computer);
            }

        }
    }

    // solved code smell: Duplicate Code, Long Method and Too hard to understand
    // Reason: runWith2Player and runWithComputer has many duplication
    //         This method is also too long and too complicated to understand
    // Solution: create a new method getInput to get the input column either by user or by computer
    // the common logic is implemented in withComputer
    void run(boolean withComputer) {
        while (true) {
            printBoard();

            int win = checkWinner();

            if (win == RED) {

                System.out.println("Red wins!");

                break;

            } else if (win == BLUE) {

                System.out.println("Blue wins!");

                break;

            } else if (win == DRAW) {

                System.out.println("It is draw!");

                break;


            }

            int col = getInput(withComputer && turn == BLUE);

            put(turn, col);

            if (turn == RED) {

                turn = BLUE;
            } else {

                turn = RED;
            }


        }

    }


//    void readBoard(String fn) throws FileNotFoundException {
//
//        Scanner in = new Scanner(new FileReader(fn));
//
//        for (int i = 0; i < numRow; i++) {
//
//            String line = in.nextLine();
//
//            for (int j = 0; j < numCol; j++) {
//
//                if (line.charAt(j) == '*') {
//
//                    board[i][j] = EMPTY;
//                } else if (line.charAt(j) == 'B') {
//                    board[i][j] = BLUE;
//                } else if (line.charAt(j) == 'R') {
//
//                    board[i][j] = RED;
//                }
//            }
//        }
//    }

    /*
        Main loop; runs while the game is active (i.e. a player hasn't won yet)
     */
    void run() {
        System.out.println("Welcome to connect 4 game!");
        Scanner in = new Scanner(System.in);
        System.out.print("Do you want to play with computer (y/n): ");

        String line = in.nextLine();

        run(line.startsWith("y"));
    }
//
//    void test1() {
//
//        try {
//            readBoard("notWin1.txt");
//
//            System.out.println(checkWinner());
//
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//    }

    public static void main(String[] args) {

        Connect4 connect4 = new Connect4();
        connect4.run();

//         connect4.test1();
    }
}

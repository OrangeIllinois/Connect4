package old;

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

    Random random = new Random();

    boolean canPut(int player, int col) {

        if (col < 0 || col >= numCol) {

            return false;
        }

        int i=numRow - 1;

        for( ; i >= 0; i--)
        {
            if (board[i][col] == EMPTY) {

                break;
            }
        }

        if (i < 0) {

            return false;
        }

        return true;
    }


    boolean canMove() {

        for (int i = 0; i < numCol; i++) {

            if (canPut(turn, i)) {

                return true;
            }
        }

        return false;
    }

    boolean put(int player, int col) {

        if (col < 0 || col >= numCol) {

            return false;
        }

        int i=numRow - 1;

        for( ; i >= 0; i--)
        {
            if (board[i][col] == EMPTY) {

                break;
            }
        }

        if (i < 0) {

            return false;
        }

        board[i][col] = player;

        return true;
    }

    boolean isInRagne(int row, int col) {

        return row >=0 && row < numRow && col >= 0 && col < numCol;
    }


    int checkWinner() {

        for (int i = 0; i < numRow; i++) {


            for (int j = 0; j < numCol; j++) {

                int player = board[i][j];

                if (player == EMPTY) {

                    continue;
                }

                int count = 1;

                int dc = 1;

                int newRow = i;


                for (int newCol = j + 1; newCol < numCol; newCol += dc) {

                    if (board[newRow][newCol] == player) {

                        count++;

                    }else{

                        break;
                    }
                }

                if (count >= NUM) {

                    return player;
                }

                count = 1;

                dc = 1;

                int dr = 1;


                int newCol;

                for ( newCol = j + dc,  newRow = i + dr; newCol < numCol && newRow < numRow; newCol += dc, newRow += dr) {

                    if (board[newRow][newCol] == player) {

                        count++;

                    }else{

                        break;
                    }
                }

                if (count >= NUM) {

                    return player;
                }


                count = 1;

                dc = 0;

                dr = 1;


                for ( newCol = j + dc,  newRow = i + dr; newCol < numCol && newRow < numRow; newCol += dc, newRow += dr) {

                    if (board[newRow][newCol] == player) {

                        count++;

                    }else{

                        break;
                    }
                }

                if (count >= NUM) {

                    return player;
                }


                count = 1;

                dc = -1;

                dr = 1;


                for ( newCol = j + dc,  newRow = i + dr; newCol >= 0 && newRow < numRow; newCol += dc, newRow += dr) {

                    if (board[newRow][newCol] == player) {

                        count++;

                    }else{

                        break;
                    }
                }

                if (count >= NUM) {

                    return player;
                }


            }
        }

        if(!canMove( ))
        {
            return DRAW;
        }


        return EMPTY;
    }

    void printBoard()
    {
        for (int i = 0; i < numRow; i++) {

            for (int j = 0; j < numCol; j++) {

                if(board[i][j] ==EMPTY)
                {
                    System.out.print("*");

                } else if (board[i][j] == RED) {

                    System.out.print("R");
                } else{

                    System.out.print("B");
                }
            }

            System.out.println();
        }

        System.out.println("1234567");
    }

    void runWith2Player() {

        Scanner in = new Scanner(System.in);

        while (true)
        {
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

            System.out.print( (turn == RED ? "Red": "Blue") + " Player, ");
            System.out.print("Input the column to put: ");

            String line = in.nextLine();

            int col = Integer.parseInt(line) - 1;

            if (!put(turn, col)) {


                System.out.println("Invalid");

            }else{

                    if (turn == RED) {

                        turn = BLUE;
                    } else {

                        turn = RED;
                    }


            }
        }

    }

    void runWithComputer() {


        Scanner in = new Scanner(System.in);

        while (true)
        {
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

            if (turn == BLUE) {

                int col = random.nextInt(numCol);

                while (true)
                {
                    if (put(turn, col)) {

                        System.out.println("Blue put into column " + col);
                        break;

                    }
                }

                turn = RED;



            }else {

                System.out.print((turn == RED ? "Red" : "Blue") + " Player, ");
                System.out.print("Input the column to put: ");

                String line = in.nextLine();

                int col = Integer.parseInt(line) - 1;

                if (!put(turn, col)) {


                    System.out.println("Invalid");

                } else {

                    if (turn == RED) {

                        turn = BLUE;
                    } else {

                        turn = RED;
                    }

                }
            }

        }

    }


    void readBoard(String fn) throws FileNotFoundException {

        Scanner in = new Scanner(new FileReader(fn));

        for (int i = 0; i < numRow; i++) {

            String line = in.nextLine();

            for (int j = 0; j < numCol; j++) {

                if (line.charAt(j) == '*') {

                    board[i][j] = EMPTY;
                }

                else if(line.charAt(j) == 'B')
                {
                    board[i][j] = BLUE;
                } else if (line.charAt(j) == 'R') {

                    board[i][j] = RED;
                }
            }
        }
    }

    void run() {

        System.out.println("Welcome to connect 4 game!");
        Scanner in = new Scanner(System.in);
        System.out.print("Do you want to play with computer (y/n): ");

        String line = in.nextLine();

        if (line.startsWith("y")) {

            runWithComputer();;
        }else{

            runWith2Player();
        }
    }

    void test1() {

        try {
            readBoard("notWin1.txt");

            System.out.println(checkWinner());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {


        Connect4 connect4 = new Connect4();
        connect4.run();

//         connect4.test1();
    }
}

import java.util.Scanner;

public class TicTacToe {

    private static final int ROWS = 3;
    private static final int COLS = 3;
    private static String[][] board = new String[ROWS][COLS];
    private static String currentPlayer = "X";

    public class SafeInput {
        public static int getRangedInt(Scanner console, String prompt, int low, int high) {
            int input;
            do {
                System.out.print(prompt);
                input = console.nextInt();
            } while (input < low || input > high);
            return input;
        }

        public static boolean getYNConfirm(Scanner console, String prompt) {
            System.out.print(prompt);
            String response = console.next();
            return response.equalsIgnoreCase("Y");
        }
    }


    public static void main(String[] args) {
        Scanner console = new Scanner(System.in);
        boolean playAgain;

        do {
            clearBoard();
            boolean gameOver = false;
            int moveCount = 0;

            while (!gameOver && moveCount < ROWS * COLS) {
                displayBoard();
                System.out.println("Player " + currentPlayer + ", it's your turn.");
                int row = SafeInput.getRangedInt(console, "Enter row (1-3): ", 1, 3) - 1;
                int col = SafeInput.getRangedInt(console, "Enter col (1-3): ", 1, 3) - 1;

                while (!isValidMove(row, col)) {
                    System.out.println("Invalid move. Try again.");
                    row = SafeInput.getRangedInt(console, "Enter row (1-3): ", 1, 3) - 1;
                    col = SafeInput.getRangedInt(console, "Enter col (1-3): ", 1, 3) - 1;
                }

                board[row][col] = currentPlayer;
                moveCount++;

                if (moveCount >= 5) {
                    if (isWin(currentPlayer)) {
                        displayBoard();
                        System.out.println("Player " + currentPlayer + " wins!");
                        gameOver = true;
                    } else if (isTie()) {
                        displayBoard();
                        System.out.println("It's a tie!");
                        gameOver = true;
                    }
                }

                if (!gameOver) {
                    togglePlayer();
                }
            }

            playAgain = SafeInput.getYNConfirm(console, "Do you want to play again? (Y/N): ");
        } while (playAgain);

        console.close();
    }

    private static void clearBoard() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                board[i][j] = " ";
            }
        }
    }

    private static void displayBoard() {
        System.out.println("  1 2 3");
        for (int i = 0; i < ROWS; i++) {
            System.out.print((i + 1) + " ");
            for (int j = 0; j < COLS; j++) {
                System.out.print(board[i][j]);
                if (j < COLS - 1) System.out.print("|");
            }
            System.out.println();
            if (i < ROWS - 1) System.out.println("  -----");
        }
    }

    private static boolean isValidMove(int row, int col) {
        return board[row][col].equals(" ");
    }

    private static boolean isWin(String player) {
        return isRowWin(player) || isColWin(player) || isDiagonalWin(player);
    }

    private static boolean isRowWin(String player) {
        for (int row = 0; row < ROWS; row++) {
            if (board[row][0].equals(player) && board[row][1].equals(player) && board[row][2].equals(player)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isColWin(String player) {
        for (int col = 0; col < COLS; col++) {
            if (board[0][col].equals(player) && board[1][col].equals(player) && board[2][col].equals(player)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isDiagonalWin(String player) {
        return (board[0][0].equals(player) && board[1][1].equals(player) && board[2][2].equals(player)) ||
                (board[0][2].equals(player) && board[1][1].equals(player) && board[2][0].equals(player));
    }

    private static boolean isTie() {
        for (int i = 0; i < ROWS; i++) {
            if (board[i][0].equals(" ")) {
                return false;
            }
            if (board[i][1].equals(" ")) {
                return false;
            }
            if (board[i][2].equals(" ")) {
                return false;
            }
        }
        return true;
    }

    private static void togglePlayer() {
        currentPlayer = currentPlayer.equals("X") ? "O" : "X";
    }
}

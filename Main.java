import java.util.*;

public class Main {

    private static int n = 0;
    private static int m = 0;

    //create first layer based on console input
    private static int[][] firstLayer;

    //create second layer
    private static int[][] secondLayer;

    //create list of bricks' ID numbers
    private static List<Integer> bricksIdList;
    private static final Scanner cin = new Scanner(System.in);


    public static void main(String[] args) {

        inputMatrixDimensions();

        firstLayer = new int[n][m];
        secondLayer = new int[n][m];
        bricksIdList = new ArrayList<>();
        int numberOfBricks = m * n / 2;
        for (int i = 0; i < numberOfBricks; i++) {
            bricksIdList.add(i + 1);
        }

        inputMatrix();
        System.out.println();

        List<Brick> brickList = getBricksFromMatrix(firstLayer);

        if (brickList == null) {
            System.out.println("Invalid input.");
            return;
        }

        if (isSolutionExists(0) && getBricksFromMatrix(secondLayer) != null) {
            printMatrix(secondLayer);
        } else {
            System.out.println(-1 + " No solution exists.");
        }
    }

    //test if a brick can be placed horizontally from position
    private static boolean isHorizontalValid(int row, int col) {
        return (col < m - 1) && (firstLayer[row][col] != firstLayer[row][col + 1]) && (secondLayer[row][col + 1] == 0) && (secondLayer[row][col] == 0);
    }

    private static boolean isVerticalValid(int row, int col) {
        return (row < n - 1) && (firstLayer[row][col] != firstLayer[row + 1][col]) && (secondLayer[row + 1][col] == 0) && (secondLayer[row][col] == 0);
    }

    //put the bricks into the second layer
    private static boolean isSolutionExists(int row) {
        for (int i = row; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (secondLayer[i][j] != 0) {
                    continue;
                }
                if (isHorizontalValid(i, j)) {
                    secondLayer[i][j] = secondLayer[i][j + 1] = bricksIdList.get(0);
                    bricksIdList.remove(0);
                    if (!isSolutionExists(i)) {
                        bricksIdList.add(0, secondLayer[i][j]);
                        secondLayer[i][j] = secondLayer[i][j + 1] = 0;
                    } else {
                        return true;
                    }
                }
                if (isVerticalValid(i, j)) {
                    secondLayer[i][j] = secondLayer[i + 1][j] = bricksIdList.get(0);
                    bricksIdList.remove(0);
                    if (!isSolutionExists(i)) {
                        bricksIdList.add(0, secondLayer[i][j]);
                        secondLayer[i][j] = secondLayer[i + 1][j] = 0;
                    } else {
                        return true;
                    }
                }
                return false;
            }
        }
        return true;
    }

    //print the matrix to the console
    private static void printMatrix(int[][] board) {
        for (int[] ints : board) {
            for (int anInt : ints) {
                String string = String.valueOf(anInt);
                int count = string.length();
                int a = 3 - count;
                String separator = " ";
                for (int i = 0; i < a; i++) {
                    separator += " ";
                }
                System.out.print(separator + anInt);
            }
            System.out.println();
        }
    }

    //build the matrix according to console input
    private static void inputMatrix() {
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < m; col++) {
                firstLayer[row][col] = cin.nextInt();
            }
        }
    }

    //create a list of bricks if bricks' sizes are correct
    private static List<Brick> getBricksFromMatrix(int[][] board) {
        List<Brick> brickList = new ArrayList<>();
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < m; col++) {
                int id = board[row][col];
                Position newPosition = new Position(row, col);
                if (brickList.isEmpty()) {
                    brickList.add(new Brick(id, newPosition));
                    continue;
                }
                boolean isBrickCompleted = false;
                for (Brick element : brickList) {
                    if (element.getId() == id) {
                        if (element.getPositionB() == null) {
                            element.setPositionB(newPosition);
                            isBrickCompleted = true;
                        } else {
                            return null;
                        }
                    }
                }
                if (!isBrickCompleted) {
                    brickList.add(new Brick(id, newPosition));
                }
            }
        }
        for (Brick brick : brickList) {
            Position positionA = brick.getPositionA();
            Position positionB = brick.getPositionB();
            if (positionA == null || positionB == null) {
                return null;
            }
        }
        return brickList;
    }

    private static void inputMatrixDimensions() {
        while (!isInputValid(n) || !isInputValid(m)) {
            try {
                n = cin.nextInt();
                m = cin.nextInt();
            } catch (Exception e) {
                System.out.println("Please enter a valid number.");
            }
            if (isInputValid(n) && isInputValid(m)) {
                return;
            }
            n = m = 0;
        }
    }

    //check if the input value is correct
    private static boolean isInputValid(int a) {
        return (a > 0 && a <= 100) && a % 2 == 0;
    }

}
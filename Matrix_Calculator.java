import java.util.InputMismatchException;
import java.util.Scanner;

class MatrixCalculator {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("< WELCOME TO THE MATRIX CALCULATOR >");

        MatrixOperations matrixOperations = new MatrixOperations();

        int choice;
        do {
            printMenu();
            System.out.println("ENTER YOUR CHOICE BETWEEN (1-5)");
            choice = getUserChoice(sc);

            if (isValidChoice(choice)) {
                performOperation(matrixOperations, choice, sc);
            } else {
                System.out.println("< PLEASE ENTER A VALID CHOICE FROM THE MENU >");
            }

            System.out.println("1) FOR CONTINUE...\n2) FOR EXIT");
        } while (sc.nextInt() == 1);
    }

    private static void printMenu() {
        System.out.println("--> HERE YOU CAN CALCULATE...");
        System.out.println(" 1) ADDITION (+) \n 2) SUBTRACTION (-) \n 3) MULTIPLICATION (*) \n 4) TRANSPOSE (T) \n 5) INVERSE (I)");
    }

    private static int getUserChoice(Scanner sc) {
        while (!sc.hasNextInt()) {
            System.out.println("< PLEASE ENTER A VALID INTEGER >");
            sc.next(); // consume the invalid input
        }
        return sc.nextInt();
    }

    private static boolean isValidChoice(int choice) {
        return choice >= 1 && choice <= 5;
    }

    private static void performOperation(MatrixOperations matrixOperations, int choice, Scanner sc) {
        switch (choice) {
            case 1:
                matrixOperations.performSum(sc);
                break;
            case 2:
                matrixOperations.performSubtraction(sc);
                break;
            case 3:
                matrixOperations.performMultiplication(sc);
                break;
            case 4:
                matrixOperations.performTranspose(sc);
                break;
            case 5:
                matrixOperations.performInverse(sc);
                break;
        }
    }
}

class MatrixOperations {
    private Scanner sc = new Scanner(System.in);

    void performSum(Scanner sc) {
        Matrix inputMatrix1 = getInputMatrix("MATRIX 1", sc);
        Matrix inputMatrix2 = getInputMatrix("MATRIX 2", sc);

        Matrix result = inputMatrix1.add(inputMatrix2);

        if (result != null) {
            System.out.println("ANS OF ADDITION:");
            result.display();
        } else {
            System.out.println("< SUM IS NOT POSSIBLE >");
        }
    }

    void performSubtraction(Scanner sc) {
        Matrix inputMatrix1 = getInputMatrix("MATRIX 1", sc);
        Matrix inputMatrix2 = getInputMatrix("MATRIX 2", sc);

        Matrix result = inputMatrix1.subtract(inputMatrix2);

        if (result != null) {
            System.out.println("ANSWER OF SUBTRACTION:");
            result.display();
        } else {
            System.out.println("< SUBTRACTION IS NOT POSSIBLE >");
        }
    }

    void performMultiplication(Scanner sc) {
        Matrix inputMatrix1 = getInputMatrix("MATRIX 1", sc);
        Matrix inputMatrix2 = getInputMatrix("MATRIX 2", sc);

        Matrix result = inputMatrix1.multiply(inputMatrix2);

        if (result != null) {
            System.out.println("ANSWER OF MULTIPLICATION:");
            result.display();
        } else {
            System.out.println("< MULTIPLICATION IS NOT POSSIBLE >");
        }
    }

    void performTranspose(Scanner sc) {
        Matrix inputMatrix = getInputMatrix("MATRIX", sc);

        Matrix result = inputMatrix.transpose();

        System.out.println("TRANSPOSE OF THE MATRIX:");
        result.display();
    }

    void performInverse(Scanner sc) {
        Matrix inputMatrix = getInputMatrix("MATRIX", sc);

        if (inputMatrix.isSquare()) {
            Matrix result = inputMatrix.inverse();

            if (result != null) {
                System.out.println("INVERSE OF THE MATRIX:");
                result.display();
            } else {
                System.out.println("< MATRIX IS NOT INVERTIBLE >");
            }
        } else {
            System.out.println("< MATRIX MUST BE SQUARE TO FIND INVERSE >");
        }
    }

    private Matrix getInputMatrix(String matrixName, Scanner sc) {
        System.out.println(" < FOR " + matrixName + " > ");
        System.out.println("ENTER THE NUMBER OF ROWS: ");
        int rows = getPositiveInt(sc);
        System.out.println("ENTER THE NUMBER OF COLUMNS: ");
        int columns = getPositiveInt(sc);

        int[][] elements = new int[rows][columns];

        System.out.println("ENTER THE " + matrixName + ": ");
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                try {
                    elements[i][j] = sc.nextInt();
                } catch (InputMismatchException e) {
                    System.out.println("< PLEASE ENTER A VALID INTEGER >");
                    sc.next(); // consume the invalid input
                    j--; // Decrement j to re-enter the current element
                }
            }
        }

        return new Matrix(elements);
    }

    private int getPositiveInt(Scanner sc) {
        int value;
        do {
            while (!sc.hasNextInt()) {
                System.out.println("< PLEASE ENTER A VALID INTEGER >");
                sc.next(); // consume the invalid input
            }
            value = sc.nextInt();
            if (value <= 0) {
                System.out.println("< PLEASE ENTER A POSITIVE INTEGER >");
            }
        } while (value <= 0);
        return value;
    }
}

class Matrix {
    private int rows;
    private int columns;
    private int[][] elements;

    public Matrix(int[][] elements) {
        this.rows = elements.length;
        this.columns = elements[0].length;
        this.elements = elements;
    }

    Matrix add(Matrix other) {
        if (this.rows == other.rows && this.columns == other.columns) {
            int[][] result = new int[rows][columns];
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < columns; j++) {
                    result[i][j] = this.elements[i][j] + other.elements[i][j];
                }
            }
            return new Matrix(result);
        } else {
            return null;
        }
    }

    Matrix subtract(Matrix other) {
        if (this.rows == other.rows && this.columns == other.columns) {
            int[][] result = new int[rows][columns];
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < columns; j++) {
                    result[i][j] = this.elements[i][j] - other.elements[i][j];
                }
            }
            return new Matrix(result);
        } else {
            return null;
        }
    }

    Matrix multiply(Matrix other) {
        if (this.columns == other.rows) {
            int[][] result = new int[this.rows][other.columns];
            for (int i = 0; i < this.rows; i++) {
                for (int j = 0; j < other.columns; j++) {
                    int sum = 0;
                    for (int k = 0; k < this.columns; k++) {
                        sum += this.elements[i][k] * other.elements[k][j];
                    }
                    result[i][j] = sum;
                }
            }
            return new Matrix(result);
        } else {
            return null;
        }
    }

    Matrix transpose() {
        int[][] result = new int[columns][rows];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                result[j][i] = elements[i][j];
            }
        }
        return new Matrix(result);
    }

   Matrix inverse() {
        // Check if the matrix is square
        if (!isSquare()) {
            return null;
        }

        int n = rows; // n x n matrix

        // Augment the matrix with the identity matrix
        double[][] augmentedMatrix = new double[n][2 * n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                augmentedMatrix[i][j] = elements[i][j];
                augmentedMatrix[i][j + n] = (i == j) ? 1.0 : 0.0;
            }
        }

        // Apply Gauss-Jordan elimination
        for (int i = 0; i < n; i++) {
            double pivot = augmentedMatrix[i][i];

            // Make the diagonal element 1
            for (int j = 0; j < 2 * n; j++) {
                augmentedMatrix[i][j] /= pivot;
            }

            // Make other elements in the column 0
            for (int k = 0; k < n; k++) {
                if (k != i) {
                    double factor = augmentedMatrix[k][i];
                    for (int j = 0; j < 2 * n; j++) {
                        augmentedMatrix[k][j] -= factor * augmentedMatrix[i][j];
                    }
                }
            }
        }

        // Extract the inverse matrix from the augmented matrix
        double[][] inverseElements = new double[n][n];
        for (int i = 0; i < n; i++) {
            System.arraycopy(augmentedMatrix[i], n, inverseElements[i], 0, n);
        }

        return new Matrix(inverseElements);
    }


    boolean isSquare() {
        return rows == columns;
    }

    void display() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                System.out.print(elements[i][j] + " ");
            }
            System.out.println();
        }
    }
}

import java.util.InputMismatchException;
import java.util.Scanner;

// Class to handle matrix operations
class MatrixCalculator {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("< WELCOME TO THE MATRIX CALCULATOR >");

        // Create an instance of MatrixOperations
        MatrixOperations matrixOperations = new MatrixOperations();

        int choice;
        do {
            // Display the menu and get user choice
            printMenu();
            System.out.println("ENTER YOUR CHOICE BETWEEN (1-5)");
            choice = getUserChoice(sc);

            // Check if the choice is valid, then perform the selected operation
            if (isValidChoice(choice)) {
                performOperation(matrixOperations, choice, sc);
            } else {
                System.out.println("< PLEASE ENTER A VALID CHOICE FROM THE MENU >");
            }

            System.out.println("1) FOR CONTINUE...\n2) FOR EXIT");
        } while (sc.nextInt() == 1);
    }

    // Display the menu options
    private static void printMenu() {
        System.out.println("--> HERE YOU CAN CALCULATE...");
        System.out.println(" 1) ADDITION (+) \n 2) SUBTRACTION (-) \n 3) MULTIPLICATION (*) \n 4) TRANSPOSE (T) \n 5) INVERSE (I)");
    }

    // Get the user's choice, handle invalid inputs
    private static int getUserChoice(Scanner sc) {
        while (!sc.hasNextInt()) {
            System.out.println("< PLEASE ENTER A VALID INTEGER >");
            sc.next(); // consume the invalid input
        }
        return sc.nextInt();
    }

    // Check if the user's choice is within the valid range
    private static boolean isValidChoice(int choice) {
        return choice >= 1 && choice <= 5;
    }

    // Perform the matrix operation based on the user's choice
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

// Class to handle different matrix operations
class MatrixOperations {
    private Scanner sc = new Scanner(System.in);

    // Perform matrix addition
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

    // Perform matrix subtraction
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

    // Perform matrix multiplication
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

    // Perform matrix transpose
    void performTranspose(Scanner sc) {
        Matrix inputMatrix = getInputMatrix("MATRIX", sc);

        Matrix result = inputMatrix.transpose();

        System.out.println("TRANSPOSE OF THE MATRIX:");
        result.display();
    }

    // Perform matrix inversion
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

    // Get user input for matrix dimensions and elements
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

    // Get a positive integer from the user
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

// Class to represent a matrix and perform basic operations
class Matrix {
    private int rows;
    private int columns;
    private int[][] elements;

    // Constructor to initialize the matrix with given elements
    public Matrix(int[][] elements) {
        this.rows = elements.length;
        this.columns = elements[0].length;
        this.elements = elements;
    }

    // Perform matrix addition
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

    // Perform matrix subtraction
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

    // Perform matrix multiplication
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

    // Perform matrix transpose
    Matrix transpose() {
        int[][] result = new int[columns][rows];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                result[j][i] = elements[i][j];
            }
        }
        return new Matrix(result);
    }

    // Perform matrix inversion using Gauss-Jordan elimination
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

    // Check if the matrix is square
    boolean isSquare() {
        return rows == columns;
    }

    // Display the matrix
    void display() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                System.out.print(elements[i][j] + " ");
            }
            System.out.println();
        }
    }
}

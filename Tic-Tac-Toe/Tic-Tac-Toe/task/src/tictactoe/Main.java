package tictactoe;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    static char[] field;

    static void printMinus() {
        System.out.println("---------");
    }

    static String readInput() {
        Scanner sc = new Scanner(System.in);
        return sc.nextLine().trim();
    }

    static void printField() {
        printMinus();
        int i = 0;
        for (char ch : field) {
            if (i % 3 == 0) {
                System.out.print("| ");
            }
            if (ch != '_') {
                System.out.print(ch);
            } else {
                System.out.print(" ");
            }
            System.out.print(" ");
            if (i % 3 == 2) {
                System.out.println("|");
            }
            i++;
        }
        printMinus();
    }

    static boolean isImpossible() {
        // x - o > 1
        int countX = 0;
        int countO = 0;
        for (char c : field) {
            countX += c == 'X' ? 1 : 0;
            countO += c == 'O' ? 1 : 0;
        }

        return (Math.abs(countO - countX) > 1 || (wins('X') && wins('O')));
    }

    static boolean wins(char c) {
        for (int i = 0; i < 3; i++) {
            // row
            if (field[3 * i] == field[3 * i + 1] &&
                    field[3 * i + 2] == field[3 * i] && field[3 * i] == c)
                return true;
            // column
            if (field[i] == field[i + 3] && field[i + 3] == field[i + 6] &&
                    field[i] == c)
                return true;
        }
        return (field[0] == field[4] && field[0] == field[8] && field[0] == c) ||
                (field[2] == field[4] && field[2] == field[6] && field[2] == c);
    }

    static boolean draw() {
        for (char c : field) {
            if (c == '_')
                return false;
        }
        return true;
    }

    static void analyzeField() {
        if (isImpossible()) {
            System.out.println("Impossible");
            return ;
        }
        if (wins('X')) {
            System.out.println("X wins");
            return;
        }
        if (wins('O')) {
            System.out.println("O wins");
            return;
        }

        if (draw()) {
            System.out.println("Draw");
            return;
        }
        System.out.println("Game not finished");
    }

    static boolean isFilled() {
        for (char c: field) {
            if (c == '_')
                return false;
        }
        return true;
    }

    static char isFinished(char user) {
        if (wins(user))
            return user;
        if (isFilled())
            return 'd';
        return 'u';
    }

    static void readCoordinates(char user) {
        System.out.println("Enter the coordinates: ");
        Scanner sc = new Scanner(System.in);
        try {
            int x = sc.nextInt();
            int y = sc.nextInt();
            if (x < 1 || x > 3 || y < 1 || y > 3) {
                System.out.println("Coordinates should be from 1 to 3!");
                readCoordinates(user);
            } else {
                int fx = 3 - y;
                int fy = x - 1;
                if (field[fx * 3 + fy] != '_') {
                    System.out.println("This cell is occupied! Choose another one");
                    readCoordinates(user);
                } else {
                    field[fx * 3 + fy] = user;
                }
            }

        } catch (InputMismatchException e) {
            System.out.println("You should enter numbers!");
            readCoordinates(user);
        }
    }

    public static void main(String[] args) {
        field = "_________".toCharArray();
        char user = 'X';
        char isFinished = isFinished(user);
        while (isFinished == 'u') {
            readCoordinates(user);
            printField();
            isFinished = isFinished(user);
            user = user == 'X' ? 'O' : 'X';
        }
        switch (isFinished) {
            case 'X': System.out.println("X wins"); break;
            case 'O': System.out.println("O wins"); break;
            case 'd': System.out.println("Draw");
        }
    }
}

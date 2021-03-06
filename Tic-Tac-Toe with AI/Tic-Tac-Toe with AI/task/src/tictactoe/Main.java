package tictactoe;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class Coordinates {
    int x, y;

    Coordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    int getX() {
        return x;
    }

    int getY() {
        return y;
    }
}

class TicTacToeField {
    char[] field;

    TicTacToeField(char[] values) {

        field = Arrays.copyOf(values, values.length);
        if (isImpossible()) {
            System.exit(10);
        }
    }

    TicTacToeField() {
        field = "_________".toCharArray();
    }

    protected TicTacToeField copy() {
        return new TicTacToeField(field);
    }

    protected void printField() {
        System.out.println("---------");
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
        System.out.println("---------");
    }

    protected boolean isImpossible() {
        int countX = 0;
        int countO = 0;
        for (char c : field) {
            countX += c == 'X' ? 1 : 0;
            countO += c == 'O' ? 1 : 0;
        }
        return Math.abs(countX - countO) > 1;
    }

    protected boolean isFinished(boolean printFlag) {
        for (char c : field) {
            if (c == '_') {
                if (printFlag)
                    System.out.println("Game not finished");
                return false;
            }
        }
        return true;
    }

    protected boolean wins(char currentUser) {
        for (int i = 0; i < 3; i++) {
            // row
            if (field[3 * i] == field[3 * i + 1] &&
                    field[3 * i + 2] == field[3 * i] && field[3 * i] == currentUser)
                return true;
            // column
            if (field[i] == field[i + 3] && field[i + 3] == field[i + 6] &&
                    field[i] == currentUser)
                return true;
        }
        return (field[0] == field[4] && field[0] == field[8] && field[0] == currentUser) ||
                (field[2] == field[4] && field[2] == field[6] && field[2] == currentUser);
    }

    protected boolean isDraw(boolean printFlag) {
        int countX = 0;
        int countO = 0;
        for (char c : field) {
            countO += c == 'O' ? 1 : 0;
            countX += c == 'X' ? 1 : 0;
        }
        if (isFinished(printFlag) && countX - 1 == countO) {
            if (printFlag)
                System.out.println("Draw");
            return true;
        } else {
            return false;
        }
    }

    protected boolean hasEnded(boolean printFlag) {
        if (wins('X')) {
            if (printFlag)
                System.out.println("X wins");
            return true;
        }
        if (wins('O')) {
            if (printFlag)
                System.out.println("O wins");
            return true;
        }
        return isDraw(printFlag);
    }

    protected boolean isXYAvailable(int x, int y) {
        return field[x * 3 + y] == '_';
    }

    protected void setXY(Coordinates xy, char value) {
        field[xy.getX() * 3 + xy.getY()] = value;
    }

    protected void setIndex(int index, char value) {
        field[index] = value;
    }

    public void setXY(int x, int y, char value) {
        field[x * 3 + y] = value;
    }

    public List<Integer> getEmptyCells() {
        return IntStream.range(0, field.length)
                .filter(i -> field[i] == '_')
                .boxed()
                .collect(Collectors.toList());
    }
}

class User {
    char value;

    User(char value) {
        this.value = value;
    }

    Coordinates findMove(TicTacToeField board) {
        return new Coordinates(0, 0);
    }

    protected char getValue() {
        return value;
    }
}

class Person extends User {
    Person(char value) {
        super(value);
    }

    @Override
    Coordinates findMove(TicTacToeField board) {
        System.out.println("Enter the coordinates: ");
        Scanner sc = new Scanner(System.in);
        String text;
        try {
            text = sc.nextLine().trim();
            if (text.equals("exit")) {
                System.exit(10);
            } else {
                int[] numbers = Arrays.stream(text.split(" ")).mapToInt(Integer::parseInt).toArray();
                int x = numbers[0];
                int y = numbers[1];
                if (x < 1 || x > 3 || y < 1 || y > 3) {
                    System.out.println("Coordinates should be from 1 to 3!");
                    return findMove(board);
                } else {
                    int fx = 3 - y;
                    int fy = x - 1;
                    if (!board.isXYAvailable(fx, fy)) {
                        System.out.println("This cell is occupied! Choose another one!");
                        return findMove(board);
                    } else {
                        return new Coordinates(fx, fy);
                    }
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("You should enter numbers!");
            return findMove(board);
        }

        return null;
    }
}

class AIUser extends User {
    AIUser(char value) {
        super(value);
    }

    Coordinates findRandomCoordinates(TicTacToeField board) {
        int x, y;
        Random r = new Random();
        do {
            x = r.nextInt(3);
            y = r.nextInt(3);
        } while (!board.isXYAvailable(x, y));
        return new Coordinates(x, y);
    }

    @Override
    Coordinates findMove(TicTacToeField board) {
        return super.findMove(board);
    }
}

class EasyLevel extends AIUser {
    EasyLevel(char value) {
        super(value);
    }

    @Override
    Coordinates findMove(TicTacToeField board) {
        System.out.println("Making move level \"easy\"");
        return findRandomCoordinates(board);
    }
}

class MediumLevel extends AIUser {
    MediumLevel(char value) {
        super(value);
    }

    @Override
    Coordinates findMove(TicTacToeField board) {
        System.out.println("Making move level \"medium\"");
        // can I win
        TicTacToeField copy;
        for (int i = 0; i < 9; i++) {
            copy = board.copy();
            if (copy.isXYAvailable(i / 3, i % 3)) {
                copy.setXY(i / 3, i % 3, value);
                if (copy.wins(value)) {
                    return new Coordinates(i / 3, i % 3);
                }
            }

        }
        // can the opponent win
        for (int i = 0; i < 9; i++) {
            copy = board.copy();
            if (copy.isXYAvailable(i / 3, i % 3)) {
                copy.setXY(i / 3, i % 3, value == 'O' ? 'X' : 'O');
                if (copy.wins(value == 'O' ? 'X' : 'O')) {
                    return new Coordinates(i / 3, i % 3);
                }
            }
        }

        return findRandomCoordinates(board);
    }
}

class HardLevel extends AIUser {

    HardLevel(char value) {
        super(value);
        player = value;
        opponent = player == 'X' ? 'O' : 'X';
        choiceIndex = -1;
    }

    char player;
    char opponent;
    int choiceIndex;

    @Override
    Coordinates findMove(TicTacToeField board) {
        System.out.println("Making move level \"hard\"");
        minMax(board, 0, player);
        return new Coordinates(choiceIndex / 3, choiceIndex % 3);
    }

    private int calculateScore(TicTacToeField board, int depth) {
        if (board.wins(player))
            return 10 - depth;
        if (board.wins(opponent))
            return depth - 10;
        return 0;
    }

    private int minMax(TicTacToeField board, int depth, char user) {
        if (board.hasEnded(false))
            return calculateScore(board, depth);
        depth++;
        List<Integer> empty = board.getEmptyCells();
        int[] scores = new int[empty.size()];
        // populate scores
        int idx = 0;
        for (int i: empty) {
            TicTacToeField aux = board.copy();
            aux.setIndex(i, user);
            scores[idx++] = minMax(aux, depth, user == player ? opponent : player);
        }
        // do max - maximize my win
        if (user == player) {
            int maxIdx = 0;
            for (int i = 1; i < scores.length; i++) {
                if (scores[i] > scores[maxIdx]){
                    maxIdx = i;
                }
            }
            choiceIndex = empty.get(maxIdx);
            return scores[maxIdx];
        } else { // do min - minimize my loss
            int minIdx = 0;
            for (int i = 1; i < scores.length; i++) {
                if (scores[i] < scores[minIdx]) {
                    minIdx = i;
                }
            }
            choiceIndex = empty.get(minIdx);
            return scores[minIdx];
        }
    }


}

class GameArbiter {
    TicTacToeField board;
    User user1, user2;
    User current;

    GameArbiter(TicTacToeField b, User u1, User u2) {
        this.board = b;
        this.user1 = u1;
        this.user2 = u2;
        this.current = user1;
    }

    protected void changeUser() {
        this.current = current == user1 ? user2 : user1;
    }

    protected void makeMove() {
        Coordinates pair = current.findMove(board);
        board.setXY(pair, current.getValue());
    }
}

class GameWithMenu {
    static Set<String> users = new HashSet<>(Arrays.asList("user", "easy", "medium", "hard"));
    GameArbiter ga;

    protected void createMenu() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Input command: ");
        String[] command = sc.nextLine().trim().split(" ");
        if (command.length == 1 && command[0].equals("exit")) {
            return;
        }
        if (command.length != 3 || !command[0].equals("start") || !users.contains(command[1]) || !users.contains(command[2])) {
            System.out.println("Bad parameters!");
            createMenu();
        } else {
            // all parameters are correct
            TicTacToeField board = new TicTacToeField();
            board.printField();

            User user1 = setUser(command[1], 'X');
            User user2 = setUser(command[2], 'O');
            this.ga = new GameArbiter(board, user1, user2);
            playGame();
        }
    }

    private User setUser(String x, char value) {
        switch (x.charAt(0)) {
            case 'u':
                return new Person(value);
            case 'e':
                return new EasyLevel(value);
            case 'm':
                return new MediumLevel(value);
            case 'h':
                return new HardLevel(value);
            default:
                throw new IllegalStateException("Unexpected value for user: " + x);
        }
    }

    private void playGame() {
        while (!ga.board.hasEnded(true)) {
            ga.makeMove();
            ga.board.printField();
            ga.changeUser();
        }
    }
}

public class Main {

    public static void main(String[] args) {
        GameWithMenu game = new GameWithMenu();
        game.createMenu();
    }
}

package Controllers;

import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Snake {
    private volatile Direction headDirection;
    private final int M;
    private final int N;
    protected Cell[][] map;
    private Coords food;
    private LinkedList<Coords> snake;

    public Snake(int M, int N) {
        headDirection = Direction.RIGHT;
        this.M = M;
        this.N = N;
        map = new Cell[M][N];
        clearField();
        snake = new LinkedList<>();
        snake.addFirst(Coords.randomCoords(0, 0, M, N));
        Coords head = getHead();
        map[head.getY()][head.getX()] = Cell.SNAKE;
        regenFood();
    }

    private Coords getHead() {
        return snake.getFirst();
    }

    private Coords getTail() {
        return snake.getLast();
    }

    public boolean makeStep() {
        if (isCrashedIntoTheBoardOnNextStep()) {
            return true;
        }
        moveHead();
        removeTail();
        return false;
    }

    private void moveHead() {
        snake.addFirst(new Coords(getHead().getY() + headDirection.getY(), getHead().getX() + headDirection.getX()));
        map[getHead().getY()][getHead().getX()] = Cell.SNAKE;
    }

    private void removeTail() {
        Coords tail = getTail();
        map[tail.getY()][tail.getX()] = Cell.NOTHING;
        snake.removeLast();
    }

    private void regenFood() {
        food = Coords.randomCoords(0, 0, M, N);
        while (map[food.getY()][food.getX()] != Cell.NOTHING) {
            food = Coords.randomCoords(0, 0, M, N);
        }
        map[food.getY()][food.getX()] = Cell.randomFood();
    }

    private void clearField() {
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                map[i][j] = Cell.NOTHING;
            }
        }
    }

    private boolean isCrashedIntoTheBoardOnNextStep() {
        Coords head = getHead();
        boolean crashedLeft = head.getX() == 0 && headDirection == Direction.LEFT;
        boolean crashedRight = head.getX() == N - 1 && headDirection == Direction.RIGHT;
        boolean crashedTop = head.getY() == 0 && headDirection == Direction.DOWN;
        boolean crashedBottom = head.getY() == M - 1 && headDirection == Direction.DOWN;
        return crashedLeft || crashedBottom || crashedTop || crashedRight;
    }

    public void setDirection(Direction newDirection) {
        if (headDirection.getOppositeDirection() == newDirection) {
            return;
        }
        headDirection = newDirection;
    }
}

enum Direction {
    UP(0, -1),
    DOWN(0, 1),
    LEFT(-1, 0),
    RIGHT(1, 0);
    private final int x;
    private final int y;

    Direction(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Direction getOppositeDirection() {
        Direction result = DOWN;
        switch (this) {
            case UP -> result = DOWN;
            case DOWN -> result = UP;
            case LEFT -> result = RIGHT;
            case RIGHT -> result = LEFT;
        }
        return result;
    }
}

class Coords {
    private int x;
    private int y;

    public Coords(int y, int x) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public static Coords randomCoords(int ymin, int xmin, int ymax, int xmax) {
        Random random = new Random();
        int x = ThreadLocalRandom.current().nextInt(xmin, xmax);
        int y = ThreadLocalRandom.current().nextInt(ymin, ymax);
        return new Coords(y, x);
    }
}

enum Cell {
    NOTHING,
    WALL,
    BANANA,
    APPLE,
    SWEET,
    SNAKE;

    public static Cell randomFood() {
        int n = ThreadLocalRandom.current().nextInt(0, 3);
        return switch (n) {
            case 0 -> APPLE;
            case 1 -> BANANA;
            case 2 -> SWEET;
            default -> BANANA;
        };
    }
}
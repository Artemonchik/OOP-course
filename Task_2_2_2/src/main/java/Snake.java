import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import static java.lang.Integer.max;
import static java.lang.Integer.min;

public class Snake {
    private volatile Direction headDirection;
    private volatile Direction newDirection;
    private final int M;
    private final int N;
    protected Cell[][] map;
    private Coords food;
    private LinkedList<Coords> snake;
    private int numberOfWalls = 5;
    HashMap<Cell, Integer> score = new HashMap<>();

    public Snake(int M, int N) {
        newDirection = headDirection = Direction.RIGHT;
        this.M = M;
        this.N = N;
        map = new Cell[M][N];
        clearField();
        initScore();
        makeWalls();
        snake = new LinkedList<>();
        snake.addFirst(Coords.randomCoords(0, 0, M, N));
        Coords head = getHead();
        map[head.getY()][head.getX()] = Cell.SNAKE;
        regenFood();
    }

    private void makeWalls() {
        for (int i = 0; i < numberOfWalls / 2; i++) {
            Coords coords = Coords.randomCoords(0, 0, M - 1,  N- 1);
            Coords coords1 = Coords.randomCoords(0, 0, M - 1, N - 1);
            int y, x1, x2;
            y = coords.getY();
            x1 = min(coords.getX(), coords1.getX());
            x2 = max(coords.getX(), coords1.getX());
//            System.out.format("%d %d %d\n",y , x1, x2);
            for(int j = x1; j < x2; j++){
                map[y][j] = Cell.WALL;
            }
        }
        for (int i = 0; i < numberOfWalls - numberOfWalls / 2; i++) {
            Coords coords = Coords.randomCoords(0, 0, M - 1,  N- 1);
            Coords coords1 = Coords.randomCoords(0, 0, M - 1, N - 1);
            int x, y1, y2;
            x = coords.getX();
            y1 = min(coords.getY(), coords1.getY());
            y2 = max(coords.getY(), coords1.getY());
//            System.out.format("%d %d %d\n",y , x1, x2);
            for(int j = y1; j < y2; j++){
                map[j][x] = Cell.WALL;
            }
        }
    }

    private Coords getHead() {
        return snake.getFirst();
    }

    private Coords getTail() {
        return snake.getLast();
    }

    public boolean makeStep() {
        headDirection = newDirection;
        if (isCrashedIntoTheBoardOnNextStep()) {
            return true;
        }
        if (isCrashedIntoItselfs()) {
            return true;
        }
        if(isCrashedIntoWall()){
            return true;
        }
        Coords nextCoords = new Coords(getHead().getY() + headDirection.getY(), getHead().getX() + headDirection.getX());
        Cell nextCell = map[nextCoords.getY()][nextCoords.getX()];
        if (nextCell == Cell.APPLE ||
                nextCell == Cell.BANANA ||
                nextCell == Cell.SWEET) {
            score.put(nextCell, score.get(nextCell) + 1);
            moveHead();
            regenFood();
        } else if (nextCell == Cell.NOTHING) {
            moveHead();
            removeTail();
        }


        return false;
    }

    private boolean isCrashedIntoWall() {
        Coords head = getHead();
        if (map[head.getY() + headDirection.getY()][head.getX() + headDirection.getX()] == Cell.WALL) {
            return true;
        }
        return false;
    }

    private boolean isCrashedIntoItselfs() {
        Coords head = getHead();
        if (map[head.getY() + headDirection.getY()][head.getX() + headDirection.getX()] == Cell.SNAKE) {
            return true;
        }
        return false;
    }

    private void moveHead() {
        map[getHead().getY()][getHead().getX()] = Cell.SNAKE;
        snake.addFirst(new Coords(getHead().getY() + headDirection.getY(), getHead().getX() + headDirection.getX()));
        map[getHead().getY()][getHead().getX()] = Cell.SNAKE_HEAD;
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
        boolean crashedTop = head.getY() == 0 && headDirection == Direction.UP;
        boolean crashedBottom = head.getY() == M - 1 && headDirection == Direction.DOWN;
        return crashedLeft || crashedBottom || crashedTop || crashedRight;
    }

    private void initScore() {
        for (Cell cell : Cell.values()) {
            score.put(cell, 0);
        }
    }

    public void setDirection(Direction newDirection) {
        if (this.headDirection.getOppositeDirection() == newDirection && !(snake.size() == 1)) {
            return;
        }
        this.newDirection = newDirection;
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
    SNAKE,
    SNAKE_HEAD,
    SNAKE_TAIL;

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
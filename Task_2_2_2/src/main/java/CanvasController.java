import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;


public class CanvasController {
    private final Canvas canvas;
    public static final Color appleColor = Color.RED;
    public static final Color bananaColor = Color.YELLOW;
    public static final Color sweetColor = Color.PURPLE;
    public static final Color nothingColor = Color.color(255.0 / 255, 239.0 / 255, 173.0 / 255);
    public static final Color snakeColor = Color.GREEN;
    public static final Color wallColor = Color.GRAY;

    public CanvasController(Canvas canvas) {
        this.canvas = canvas;
    }

    public double getWidth() {
        return canvas.getWidth();
    }

    public double getHeight() {
        return canvas.getHeight();
    }

    public double drawField(Cell[][] map) {
        int M = map.length;
        int N = map[0].length;

        GraphicsContext context = canvas.getGraphicsContext2D();
        double width = getWidth() / N;
        double height = getHeight() / M;
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                double x = getWidth() / N * j;
                double y = getHeight() / M * i;
                drawCell(map[i][j], x, y, width, height);
            }
        }
        return 0;
    }

    public void drawCell(Cell cell, double x, double y, double w, double h) {
        switch (cell) {
            case WALL -> drawRect(x,y,w,h,1, wallColor, Color.BLACK);
            case APPLE -> drawRect(x, y, w, h, 1, appleColor, Color.BLACK);
            case BANANA -> drawRect(x, y, w, h, 1, bananaColor, Color.BLACK);
            case SWEET -> drawRect(x, y, w, h, 1, sweetColor, Color.BLACK);
            case NOTHING -> drawRect(x, y, w, h, 0.5, nothingColor, Color.BLACK);
            case SNAKE -> drawRect(x, y, w, h, 1, snakeColor, Color.BLACK);
            case SNAKE_HEAD -> {
                double leftEyeX = x + w / 4;
                double rightEyeX = x + w * 3 / 4 - 5;
                double leftEyeY, rightEyeY;
                leftEyeY = rightEyeY = y + 10;
                drawRect(x, y, w, h, 1, snakeColor, Color.BLACK);
                canvas.getGraphicsContext2D().setFill(Color.BLACK);
                canvas.getGraphicsContext2D().fillOval(leftEyeX, leftEyeY, 5, 5);
                canvas.getGraphicsContext2D().fillOval(rightEyeX, rightEyeY, 5, 5);
            }
        }
    }

    public void drawRect(double x, double y, double w, double h, double borderWidth, Color fillColor, Color borderColor) {
        GraphicsContext context = canvas.getGraphicsContext2D();
        context.setFill(borderColor);
        context.fillRect(x, y, w, h);
        context.setFill(fillColor);
        context.fillRect(x + borderWidth, y + borderWidth, w - borderWidth, h - borderWidth);
    }
}

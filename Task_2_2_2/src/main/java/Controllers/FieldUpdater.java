package Controllers;

import javafx.scene.canvas.Canvas;

public class FieldUpdater extends Thread {
    private Snake snake;
    private CanvasController canvasController;
    private long updateTime;
    volatile private GameState state;

    public FieldUpdater(Snake snake, CanvasController canvasController, long updateTime, GameState state) {
        this.snake = snake;
        this.canvasController = canvasController;
        this.updateTime = updateTime;
        this.state = state;
    }

    @Override
    public void run() {
        while (state.value == GameStateEnum.RUN) {
            snake.makeStep();
            canvasController.drawField(snake.map);
            try {
                Thread.sleep(updateTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class GameState {
    volatile public GameStateEnum value = GameStateEnum.RUN;
}

enum GameStateEnum {
    RUN,
    STOP,
    PAUSE;
}
public class FieldUpdater extends Thread {
    private Snake snake;
    private CanvasController canvasController;
    private long updateTime;
    volatile private GameState state;
    private ProgressController progressController;

    public FieldUpdater(Snake snake, CanvasController canvasController, ProgressController progressController, long updateTime, GameState state) {
        this.snake = snake;
        this.canvasController = canvasController;
        this.updateTime = updateTime;
        this.state = state;
        this.progressController = progressController;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(updateTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(state.value == GameStateEnum.PAUSE){
                continue;
            }
            if(snake.makeStep()){
                progressController.onLose();
                break;
            };
            canvasController.drawField(snake.map);
            progressController.updateScore(snake.score.get(Cell.APPLE), snake.score.get(Cell.BANANA), snake.score.get(Cell.SWEET));
        }
    }
}

class GameState {
    volatile public GameStateEnum value = GameStateEnum.PAUSE;
}

enum GameStateEnum {
    RUN,
    STOP,
    PAUSE;
}
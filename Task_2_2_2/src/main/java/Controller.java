import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.event.ActionEvent;


public class Controller {
    private static final int M = 20;
    private static final int N = 20;
    private Snake snake;
    @FXML
    private Canvas field;

    @FXML
    Text text;
    @FXML
    Text appleIndicatorText;
    @FXML
    Text bananaIndicatorText;
    @FXML
    Text sweetIndicatorText;

    @FXML
    Rectangle appleIndicator;
    @FXML
    Rectangle bananaIndicator;
    @FXML
    Rectangle sweetIndicator;

    @FXML
    Text gameStatusText;
    @FXML
    Button startButton;
    @FXML
    Button pauseButton;
    private ProgressController progressController;
    private final GameState gameState = new GameState();
    public void initialize() {
        field.setFocusTraversable(true);
        snake = new Snake(M, N);
        CanvasController fieldController = new CanvasController(field);
        progressController = new ProgressController(this, appleIndicatorText, bananaIndicatorText,
                sweetIndicatorText, appleIndicator, bananaIndicator,
                sweetIndicator);
        progressController.setColors(CanvasController.appleColor, CanvasController.bananaColor, CanvasController.sweetColor);
        progressController.setButtons(startButton, pauseButton);
        progressController.setStatusField(gameStatusText);
        progressController.setGameState(gameState);
        fieldController.drawField(snake.map);

        FieldUpdater fieldUpdater = new FieldUpdater(snake, fieldController, progressController, 100, gameState);
        fieldUpdater.start();
    }

    @FXML
    private void keyHandler(KeyEvent e) {
        if(gameState.value == GameStateEnum.PAUSE){
            startButtonHandler(null);
        }
        if (e.getCode() == KeyCode.LEFT) {
            snake.setDirection(Direction.LEFT);
        } else if (e.getCode() == KeyCode.RIGHT) {
            snake.setDirection(Direction.RIGHT);
        } else if (e.getCode() == KeyCode.UP) {
            snake.setDirection(Direction.UP);
        } else if (e.getCode() == KeyCode.DOWN) {
            snake.setDirection(Direction.DOWN);
        } else if (e.getCode() == KeyCode.SPACE){
            startButtonHandler(null);
        }
    }
    @FXML
    private void pauseButtonHandler(ActionEvent event){
        progressController.pauseButtonHandler(event);
    }

    @FXML
    private void startButtonHandler(ActionEvent event){
        progressController.startButtonHandler(event);
    }
}

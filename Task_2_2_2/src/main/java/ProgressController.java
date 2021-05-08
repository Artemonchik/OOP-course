import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import static java.lang.Thread.sleep;

public class ProgressController {
    Text appleIndicatorText, bananaIndicatorText, sweetIndicatorText;
    Rectangle appleIndicator, bananaIndicator, sweetIndicator;
    Color appleColor, bananaColor, sweetColor;

    private Text gameStatusText;
    private Button startButton, pauseButton;
    private GameState gameState;
    private Controller controller;

    public ProgressController(Controller controller,Text appleIndicatorText, Text bananaIndicatorText, Text sweetIndicatorText, Rectangle appleIndicator, Rectangle bananaIndicator, Rectangle sweetIndicator) {
        this.controller = controller;
        this.appleIndicatorText = appleIndicatorText;
        this.bananaIndicatorText = bananaIndicatorText;
        this.sweetIndicatorText = sweetIndicatorText;
        this.appleIndicator = appleIndicator;
        this.bananaIndicator = bananaIndicator;
        this.sweetIndicator = sweetIndicator;
    }

    public void setColors(Color appleColor, Color bananaColor, Color sweetColor) {
        this.appleColor = appleColor;
        this.bananaColor = bananaColor;
        this.sweetColor = sweetColor;

        appleIndicator.setFill(appleColor);
        bananaIndicator.setFill(bananaColor);
        sweetIndicator.setFill(sweetColor);
    }

    public void updateScore(int appleScore, int bananaScore, int sweetScore) {
        appleIndicatorText.setText((String.format("%d apples", appleScore)));
        bananaIndicatorText.setText((String.format("%d bananas", bananaScore)));
        sweetIndicatorText.setText((String.format("%d sweets", sweetScore)));
    }

    public void setStatusField(Text gameStatusText) {
        this.gameStatusText = gameStatusText;
    }

    public void setButtons(Button startButton, Button pauseButton) {
        this.startButton = startButton;
        this.pauseButton = pauseButton;
        startButton.setFocusTraversable(false);
        pauseButton.setFocusTraversable(false);
        pauseButton.setDisable(true);
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public void pauseButtonHandler(ActionEvent event) {
        pauseButton.setDisable(true);
        startButton.setDisable(false);
        this.gameState.value = GameStateEnum.PAUSE;
        this.gameStatusText.setText("On pause");
    }

    public void startButtonHandler(ActionEvent event) {
        pauseButton.setDisable(false);
        startButton.setDisable(true);
        gameStatusText.setText("Play!!!");
        this.gameState.value = GameStateEnum.RUN;

    }

    public void onLose() {
        pauseButton.setDisable(true);
        startButton.setDisable(false);
        gameStatusText.setText("Oh, you lose try again");
        controller.initialize();
        gameState.value = GameStateEnum.PAUSE;
    }
}

package Controllers;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;

import java.awt.*;

public class Controller {
    private static final int M = 20;
    private static final int N = 20;
    private Snake snake;
    @FXML
    private Canvas field;

    @FXML
    Text text;

    public void initialize() {
        snake = new Snake(M, N);
        CanvasController fieldController = new CanvasController(field);
        fieldController.drawField(snake.map);
        FieldUpdater fieldUpdater = new FieldUpdater(snake, fieldController, 1000, new GameState());
        fieldUpdater.start();
    }

    @FXML
    private void keyHandler(KeyEvent e){
        System.out.println("Key pressing was initiated");
        if (e.getCode() == KeyCode.LEFT){
            snake.setDirection(Direction.LEFT);
        }else if(e.getCode() == KeyCode.RIGHT){
            snake.setDirection(Direction.RIGHT);
        }else if(e.getCode() == KeyCode.UP){
            snake.setDirection(Direction.UP);
        }else if(e.getCode() == KeyCode.DOWN){
            snake.setDirection(Direction.DOWN);
        }
    }
}

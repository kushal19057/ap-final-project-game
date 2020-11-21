import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Game {
    private AnchorPane gamePane;
    private Scene gameScene;
    private Stage gameStage;
    private Circle ball;
    private Circle obstacle;
    private int centerX, centerY, radius, verticalSpeed, verticalAcc;
    private int obstacleX, obstacleY, obstacleRadius;
    private Stage menuStage;

    public Game() {
        initStage();
        createKeyListener();
        this.centerX = 280;
        this.obstacleX = 280;
        this.obstacleY = -50;
        this.obstacleRadius = 60;
        this.centerY = 700;
        this.radius = 50;
        this.verticalSpeed = 0;
        verticalAcc = 4;
        this.ball = new Circle(centerX, centerY, radius, Color.LIGHTSKYBLUE);
        this.obstacle = new Circle(obstacleX, obstacleY, obstacleRadius, Color.BLACK);
        gamePane.getChildren().add(obstacle);
        gamePane.getChildren().add(ball);
        startGame();
    }

    private void startGame() {
        Timeline loop = new Timeline(new KeyFrame(Duration.millis(Constants.UPDATE_PERIOD), evt-> {
            if(centerY < (Constants.GAME_HEIGHT * 2) /5) {
                int distanceToMove = Math.abs(verticalSpeed);
                verticalSpeed /= 5;
                // verticalPosition = centerY + verticalSpeed / 2;
                obstacleY += distanceToMove;
            }

            // checkCollision
            verticalSpeed += verticalAcc;
            int verticalPosition = centerY + verticalSpeed;
            if(verticalPosition + radius >= Constants.GAME_HEIGHT) {
                verticalPosition = Constants.GAME_HEIGHT - radius;
                verticalSpeed = 0;
            }

            centerY = verticalPosition;
            ball.setCenterY(centerY);
            obstacle.setCenterY(obstacleY);
        }));

        loop.setCycleCount(Timeline.INDEFINITE);
        loop.play();
    }

    private void initStage() {
        this.gamePane = new AnchorPane();
        this.gameScene = new Scene(gamePane, Constants.GAME_WIDTH, Constants.GAME_HEIGHT);
        gameStage = new Stage();
        gameStage.setScene(gameScene);
    }

    public void createNewGame(Stage menuStage) {
        this.menuStage = menuStage;
        this.menuStage.hide();
        gameStage.show();
    }

    private void createKeyListener() {
        gameScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if( keyEvent.getCode() == KeyCode.SPACE) {
                    System.out.println("Space bar pressed");
                    // jump the idiotic ball
                    verticalSpeed = -30;
                }
            }
        });
    }
}

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class Game {
    private int currentScore;
    private int highScore;
    private ScoreLabel scoreLabel;
    private ScoreLabel highScoreLabel;
    private AnchorPane gamePane;
    private Scene gameScene;
    private Stage gameStage;
    private Circle ball;


    private int centerX, radius;
    private Stage menuStage;
    private int currentPosition;
    private int currentVelocity;

    public Game() {
        initStage();
        createKeyListener();
        this.centerX = 384;
        this.currentPosition = 600;
        this.radius = 50;
        this.currentVelocity = 0;
        this.ball = new Circle(centerX, currentPosition, radius, Color.LIGHTSKYBLUE);
        gamePane.getChildren().add(ball);
        createBackground();
        scoreLabel = new ScoreLabel("POINTS: 00");
        scoreLabel.setLayoutX(20);
        scoreLabel.setLayoutY(20);
        highScoreLabel = new ScoreLabel("HIGHEST: 100");
        highScoreLabel.setLayoutY(20);
        highScoreLabel.setLayoutX(Constants.GAME_WIDTH - 270);
        gamePane.getChildren().addAll(scoreLabel, highScoreLabel);

        startGame();
    }

    private void startGame() {

        Timeline loop = new Timeline(new KeyFrame(Duration.millis(Constants.UPDATE_PERIOD), evt-> {
            int updatedVelocity = currentVelocity + Constants.GRAVITY;
            int updatedPosition = currentPosition + updatedVelocity ;
//            int distanceAboveMidpoint = 0;

//            if(updatedPosition < Constants.GAME_HEIGHT / 2 ) {
//                distanceAboveMidpoint = Constants.GAME_HEIGHT/2 - updatedPosition;
//                updatedPosition = Constants.GAME_HEIGHT / 2;
//            }
            if(updatedPosition + radius >= Constants.GAME_HEIGHT) {
                updatedPosition = Constants.GAME_HEIGHT - radius;
            }
            //scrollObstaclesDown(distanceAboveMidpoint);
            currentVelocity = updatedVelocity;
            ball.setCenterY(updatedPosition);
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
        gameStage.initModality(Modality.APPLICATION_MODAL);
        gameStage.setTitle("Color Switch");
        gameStage.show();
    }

    private void createKeyListener() {
        gameScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if( keyEvent.getCode() == KeyCode.SPACE) {
                    System.out.println("Space bar pressed");
                    // jump the idiotic ball
                    currentVelocity = -10;
                }
                if( keyEvent.getCode() == KeyCode.P) {
                    System.out.println("pause pressed");
                    // halt the game
                }
            }
        });
    }

    private void createBackground() {
        Image backgroundImage = new Image("resources/black.png", 256, 256, false, true);
        BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, null);
        gamePane.setBackground(new Background(background));
    }
}

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import java.util.ArrayList;
import java.util.List;

public class PauseMenu extends Application {

    private Stage PauseMenuStage;
    private Scene PauseMenuScene;
    private AnchorPane PauseMenuPane;

    private Stage gameStage;
    private Scene gameScene;
    private AnchorPane gamePane;

    private Stage loadGameStage;
    private Scene loadGameScene;
    private AnchorPane loadGamePane;

    private List<ColorSwitchButton> menuButtons;

    private Circle ball;
    private int centerX;
    private int centerY;
    private int radius;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            initPauseMenu();
            primaryStage = PauseMenuStage;
            primaryStage.show();
            initGameStage();
            initLoadGameStage(PauseMenuStage);
            createKeyListener();
            radius = 100;
            centerX = 280;
            centerY = 700;
            this.ball = new Circle(centerX, centerY, radius, Color.LIGHTSKYBLUE);
            gamePane.getChildren().add(ball);

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    private void initGameStage() {
        gamePane = new AnchorPane();
        gameScene = new Scene(gamePane, Constants.GAME_WIDTH, Constants.GAME_HEIGHT);
        gameStage = new Stage();
        gameStage.setTitle("Color Switcher");
        gameStage.setScene(gameScene);
    }

    private void initLoadGameStage(Stage parent) {
        loadGamePane = new AnchorPane();
        loadGameScene = new Scene(loadGamePane, Constants.MAIN_MENU_OPTION_WIDTH, Constants.MAIN_MENU_OPTION_HEIGHT);
        loadGameStage = new Stage();
        loadGameStage.setTitle("Load Game");
        loadGameStage.setScene(loadGameScene);
        loadGameStage.initModality(Modality.APPLICATION_MODAL);
        loadGameStage.initOwner(parent);
        loadGameStage.initStyle(StageStyle.UTILITY);
    }

    private void initPauseMenu() {
        PauseMenuPane = new AnchorPane();
        PauseMenuScene = new Scene(PauseMenuPane, Constants.MENU_WIDTH, Constants.MENU_HEIGHT);
        PauseMenuStage = new Stage();
        PauseMenuStage.setTitle("Pause Menu");
        PauseMenuStage.setScene(PauseMenuScene);
        menuButtons = new ArrayList<>();
        createButtons();
        createBackground();
        createLogo();
    }

    private void createButtons() {
        createSaveAndReturn();
        createRestartGame();
        createReturntoGame();
        createSaveAndExit();
    }

    private void createSaveAndReturn() {
        ColorSwitchButton startButton = new ColorSwitchButton("SAVE & RETURN TO MAIN MENU");
        addMenuButton(SaveAndReturnButton);

        startButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                //CODE FOR SAVING IS REQUIRED. CODE TO RETURN IS REQUIRED SINCE IT IS DIFFERENT FROM Starting a new game.
                pauseMenuStage.hide();
                //playing = true;
                gameStage.show();
                //returnToGame();
                startGame();
            }
        });
    }

    private void createRestartGame() {
        ColorSwitchButton RestartGameButton = new ColorSwitchButton("RESTART GAME");
        addMenuButton(RestartGameButton);

        loadGameButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                GameStage.show();
                startGame();
            }
        });
    }

    private void createReturntoGame() {
        ColorSwitchButton helpButton = new ColorSwitchButton("RETURN TO GAME");
        addMenuButton(ReturntoGameButton);

        helpButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
               pauseMenuStage.hide();
            }
        });
    }

    private void createSaveAndExitButton() {
        ColorSwitchButton exitButton = new ColorSwitchButton("SAVE AND EXIT");
        addMenuButton(exitButton);

        exitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            //CODE FOR SAVING THE GAME?
            public void handle(ActionEvent actionEvent) {
                mainMenuStage.close();
            }
        });
    }

    private void addMenuButton(ColorSwitchButton button) {
        button.setLayoutX(Constants.MENU_BUTTONS_START_X);
        button.setLayoutY(Constants.MENU_BUTTONS_START_Y + menuButtons.size() * 100);
        menuButtons.add(button);
        mainMenuPane.getChildren().add(button);
    }

    private void createBackground() {
        Image backgroundImage = new Image("resources/black.png", 256, 256, false, true);
        BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, null);
        mainMenuPane.setBackground(new Background(background));
    }

    private void createLogo() {
        ImageView logo = new ImageView("resources/colorswitchlogo.png");
        logo.setLayoutX(185);
        logo.setLayoutY(30);
        mainMenuPane.getChildren().add(logo);
    }

    private void createKeyListener() {
            gameScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent keyEvent) {
                    if (keyEvent.getCode() == KeyCode.SPACE) {
                        System.out.println("Space Bar pressed");
                    }
                }
            });
    }
    
}

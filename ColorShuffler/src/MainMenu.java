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

public class MainMenu extends Application {

    private Stage mainMenuStage;
    private Scene mainMenuScene;
    private AnchorPane mainMenuPane;

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
            initMainMenu();
            primaryStage = mainMenuStage;
            primaryStage.show();
            initGameStage();
            initLoadGameStage(mainMenuStage);
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

    private void initMainMenu() {
        mainMenuPane = new AnchorPane();
        mainMenuScene = new Scene(mainMenuPane, Constants.MENU_WIDTH, Constants.MENU_HEIGHT);
        mainMenuStage = new Stage();
        mainMenuStage.setTitle("Main Menu");
        mainMenuStage.setScene(mainMenuScene);
        menuButtons = new ArrayList<>();
        createButtons();
        createBackground();
        createLogo();
    }

    private void createButtons() {
        createStartButton();
        createLoadGameButton();
        createHelpButton();
        createExitButton();
    }

    private void createStartButton() {
        ColorSwitchButton startButton = new ColorSwitchButton("NEW GAME");
        addMenuButton(startButton);

        startButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                mainMenuStage.hide();
                //playing = true;
                gameStage.show();
                startGame();
            }
        });
    }

    private void createLoadGameButton() {
        ColorSwitchButton loadGameButton = new ColorSwitchButton("LOAD GAME");
        addMenuButton(loadGameButton);

        loadGameButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                loadGameStage.show();
            }
        });
    }

    private void createHelpButton() {
        ColorSwitchButton helpButton = new ColorSwitchButton("HELP");
        addMenuButton(helpButton);

        helpButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

            }
        });
    }

    private void createExitButton() {
        ColorSwitchButton exitButton = new ColorSwitchButton("EXIT");
        addMenuButton(exitButton);

        exitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
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
    private void startGame() {
        Timeline loop = new Timeline(new KeyFrame(Duration.millis(Constants.UPDATE_PERIOD), evt-> {

        }));

        loop.setCycleCount(Timeline.INDEFINITE);
        loop.play();
    }
}

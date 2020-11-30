package application;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.layout.*;

import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


import java.util.ArrayList;
import java.util.List;

public class MainMenu extends Application {

    private Stage mainMenuStage;
    private Scene mainMenuScene;
    private AnchorPane mainMenuPane;

    private Game gameManager;

    private Stage loadGameStage;
    private Scene loadGameScene;
    private AnchorPane loadGamePane;
    
    private Stage helpStage;
    private Scene helpScene;
    private AnchorPane helpPane;
    
    // try to show a double pendulum in the main menu. if time permits.
    
//    private Circle ball;
//    private int centerX = 280, centerY = 220;
//    private int radius = 50;
//    private int xStep = 8, yStep = 15;

    private List<ColorSwitchButton> menuButtons;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            initMainMenu();
            primaryStage = mainMenuStage;
            primaryStage.show();
            initLoadGameStage(mainMenuStage);
            initHelpStage(mainMenuStage);

        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    private void initHelpStage(Stage parent) {
    	helpPane = new AnchorPane();
    	helpScene = new Scene(helpPane, Constants.MAIN_MENU_OPTION_WIDTH, Constants.MAIN_MENU_OPTION_HEIGHT);
    	helpStage = new Stage();
    	helpStage.setTitle("Help");
    	helpStage.setScene(helpScene);
    	helpStage.initModality(Modality.APPLICATION_MODAL);
    	helpStage.initOwner(parent);
    	helpStage.initStyle(StageStyle.UTILITY);
    	Text text = new Text("Here we will display rules of the game, misc. facts and a short description of the creators.");
    	text.setFont(Font.font("Arial", FontWeight.BOLD, 36));
    	text.setWrappingWidth(200);
    	text.setX(50);
    	text.setY(50);
    	helpPane.getChildren().add(text);
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
        Text text = new Text("Here we will display prev. saved games");
        text.setFont(Font.font("Arial", FontWeight.BOLD, 36));
        text.setWrappingWidth(200);
        text.setX(50);
        text.setY(50);
        loadGamePane.getChildren().add(text);
    }

    private void initMainMenu() {
        mainMenuPane = new AnchorPane();
        mainMenuScene = new Scene(mainMenuPane, Constants.MENU_WIDTH, Constants.MENU_HEIGHT);
        mainMenuStage = new Stage();
        mainMenuStage.setTitle("Main Menu");
        mainMenuStage.setScene(mainMenuScene);
        menuButtons = new ArrayList<>();
        //createBall();
        createButtons();
        createBackground();
        createLogo();
    }
    
//    private void createBall() {
//    	ball = new Circle(centerX, centerY, radius, Color.HOTPINK);
//    	ball.setEffect(new DropShadow());
//    	mainMenuPane.getChildren().addAll(ball);
//    	Timeline loop = new Timeline(new KeyFrame(Duration.millis(Constants.UPDATE_PERIOD), evt -> {
//    		centerX += xStep;
//    		centerY += yStep;
//    		if(centerX + radius > Constants.MENU_WIDTH || centerX - radius < 0) {
//    			xStep = -xStep;
//    		}
//    		if(centerY + radius > Constants.MENU_HEIGHT || centerY - radius < 0) {
//    			yStep = -yStep;
//    		}
//    		ball.setCenterX(centerX);
//    		ball.setCenterY(centerY);
//    	}));
//    	loop.setCycleCount(Timeline.INDEFINITE);
//    	loop.play();
//    	
//    }

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
                gameManager = new Game();
                gameManager.createNewGame(mainMenuStage);
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
            	helpStage.show();
            }
        });
    }

    private void createExitButton() {
        ColorSwitchButton exitButton = new ColorSwitchButton("EXIT");
        addMenuButton(exitButton);

        exitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
            	System.out.println("exiting main menu");
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
        Image backgroundImage = new Image("resources/deep_blue.png", 256, 256, false, true);
        BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, null);
        mainMenuPane.setBackground(new Background(background));
    }

    private void createLogo() {
        ImageView logo = new ImageView("resources/colorswitchlogo.png");
        logo.setLayoutX(185);
        logo.setLayoutY(30);
        mainMenuPane.getChildren().add(logo);
    }
}
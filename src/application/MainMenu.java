package application;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;


public class MainMenu extends Application {

    private Stage mainMenuStage;
    private Scene mainMenuScene;
    private AnchorPane mainMenuPane;
    private Game gameManager;

    private List<ColorSwitchButton> menuButtons;
    
    private MainMenuAnimation animation;
    private static MediaPlayer mediaPlayer;
    
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            mainMenuStage = primaryStage;    
            initMainMenu();
            gameManager = new Game();
            primaryStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    private void initMainMenu() {
    		addMusic();
            mainMenuPane = new AnchorPane();
            mainMenuScene = new Scene(mainMenuPane, Constants.MENU_WIDTH, Constants.MENU_HEIGHT);
            mainMenuStage.setTitle("Main Menu");
            mainMenuStage.setScene(mainMenuScene);
            menuButtons = new ArrayList<>();
            createButtons();
            createBackground();
            createLogo();
            createAnimation();
    }
    
    public void addMusic() {
        try {
        	String musicFile = "src/resources/background_music.mp3";
        	Media sound = new Media(new File(musicFile).toURI().toString());
            mediaPlayer = new MediaPlayer(sound);
            mediaPlayer.setAutoPlay(true);
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            mediaPlayer.play();
        } catch(Exception e) {
        	e.printStackTrace();
        }
    }
    
    private void createAnimation() {
        Constants.map.put(0,Color.AQUA);
        Constants.map.put(1,Color.HOTPINK);
        Constants.map.put(2,Color.YELLOW);
        Constants.map.put(3,Color.INDIGO);
        
        animation = new MainMenuAnimation(mainMenuPane);
        animation.addElementsToGamePane(mainMenuPane);

        KeyFrame kf = new KeyFrame(Duration.millis(Constants.UPDATE_PERIOD), new TimeHandler());
        Timeline timeline = new Timeline(kf);
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }


    private class TimeHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            animation.transformElements();
        }
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
                gameManager = new Game();
                gameManager.startGame();
            }
        });
    }
    
    private void createHelpButton() {
    	ColorSwitchButton helpButton = new ColorSwitchButton("HELP");
    	addMenuButton(helpButton);
    	
    	helpButton.setOnAction(new EventHandler<ActionEvent>() {
    		@Override
    		public void handle(ActionEvent actionEvent) {
    			VBox layout = new VBox(10);
    			layout.setStyle("-fx-background-color: #000000;");
    			layout.setPadding(new Insets(20,20,20,20));
    			Button button = new Button("Back to main menu");
    			Text text = new Text("The objective of the game is to collect as many stars as possible by manoeuvring the obstacles.\r\n"
    					+ "If you hit any obstacle, you can continue with your earned stars or the game ends and you have to start from the beginning.\r\n"
    					+ "This is a high score game.\r\n");
    			text.setFont(Font.font("Arial", FontWeight.BOLD, 24));
    			text.setFill(Color.HOTPINK);
    			text.setWrappingWidth((Constants.MENU_WIDTH * 5)/6);
    			button.setOnAction(e-> {
    				mainMenuScene.setRoot(mainMenuPane);
    			});
    			layout.getChildren().addAll(text, button);
    			mainMenuScene.setRoot(layout);
    		}
    	});
    }
    
    private void createLoadGameButton() {
        ColorSwitchButton loadGameButton = new ColorSwitchButton("LOAD GAME");
        addMenuButton(loadGameButton);

        loadGameButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                VBox layout = new VBox(10);
                layout.setPadding(new Insets(20, 20, 20, 20));
                DataBaseGame savedGames = new DataBaseGame();
                List<String> listOfGames= savedGames.updateFiles();
                ListView<String> listView = new ListView<>();
                listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
                for(String current : listOfGames) {
                    listView.getItems().add(current);
                }
                Button button = new Button("Load Game");
                Button menuButton = new Button("Back to main menu");
                button.setOnAction(e -> {
                    String game = null;
                    ObservableList<String> selectedGames;
                    selectedGames = listView.getSelectionModel().getSelectedItems();
                    
                    for(String m : selectedGames) {
                        game = m;
                    }
                    
                    if(game != null) {
                        mainMenuScene.setRoot(mainMenuPane);
                        gameManager.deserialise(game);
                    }
                });
                menuButton.setOnAction(e -> {
                    mainMenuScene.setRoot(mainMenuPane);
                });
                layout.getChildren().addAll(listView, button, menuButton);
                mainMenuScene.setRoot(layout);
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
        button.setLayoutX(Constants.MENU_BUTTONS_START_X );
        button.setLayoutY(Constants.MENU_BUTTONS_START_Y + menuButtons.size() * 60);
        menuButtons.add(button);
        mainMenuPane.getChildren().add(button);
    }

    private void createBackground() {
        Image backgroundImage = new Image(Constants.MAIN_MENU_BACKGROUND_PATH, 256, 256, false, true);
        BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, null);
        mainMenuPane.setBackground(new Background(background));
    }

    private void createLogo() {
        ImageView logo = new ImageView(Constants.COLOR_SWITCH_LOGO_PATH);
        logo.setLayoutX(160);
        logo.setLayoutY(30);
        mainMenuPane.getChildren().add(logo);
    }
}

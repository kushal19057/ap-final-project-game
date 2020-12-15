package application;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.ParallelTransition;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
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

import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;

import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;


public class MainMenu extends Application {

    private Stage mainMenuStage;
    private Scene mainMenuScene;
    private AnchorPane mainMenuPane;
    private Game gameManager;

    private List<ColorSwitchButton> menuButtons;
    
    private List<Arc> listOfOuterArcs = new ArrayList<>();
    private List<Rotate> listOfOuterRotate = new ArrayList<>();
    private List<Arc> listOfMiddleArcs = new ArrayList<>();
    private List<Rotate> listOfMiddleRotate = new ArrayList<>();
    private List<Arc> listOfInnerArcs = new ArrayList<>();
    private List<Rotate> listOfInnerRotate = new ArrayList<>();
    
    private Circle circleParallelDown = new Circle(300, 350, 50);
    private Circle circleParallelUp = new Circle(300, 350, 50);
    
    private FadeTransition fadeTransitionDown = new FadeTransition(Duration.millis(2000), circleParallelDown);
    private FadeTransition fadeTransitionUp = new FadeTransition(Duration.millis(2000), circleParallelUp);
    
    private TranslateTransition translateTransitionDown = new TranslateTransition(Duration.millis(2000), circleParallelDown);
    private TranslateTransition translateTransitionUp = new TranslateTransition(Duration.millis(2000), circleParallelUp);
    
    private ScaleTransition scaleTransitionDown = new ScaleTransition(Duration.millis(2000), circleParallelDown);
    private ScaleTransition scaleTransitionUp = new ScaleTransition(Duration.millis(2000), circleParallelUp);
    
    private ParallelTransition parallelTransitionDown = new ParallelTransition();
    private ParallelTransition parallelTransitionUp = new ParallelTransition();
    
    private static MediaPlayer mediaPlayer;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            
            String path = "C:\\Users\\Rohan\\git\\p-final-project-game\\src\\resources";  //MUSIC Code updated not sure of what to add in directory though will finalise later
        	Media media = new Media(new File(path).toURI().toString());  
            
        	MediaPlayer mediaPlayer = new MediaPlayer(media);  
            
        	mediaPlayer.setAutoPlay(true); 
            mainMenuStage = primaryStage;
            
            initMainMenu();
            gameManager = new Game();
            primaryStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    private void initMainMenu() {

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
//        try {
//            Media sound = new Media(getClass().getResource("/resources/background_music.mp3").toString());
//            mediaPlayer = new MediaPlayer(sound);
//            mediaPlayer.setAutoPlay(true);
//            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
//            mediaPlayer.setStartTime(Duration.seconds(0));
//            mediaPlayer.setStopTime(Duration.seconds(50));
//            mediaPlayer.play();
//        } catch(Exception e) {
//        }
    }
    
    private void createAnimation() {
        Constants.map.put(0,Color.AQUA);
        Constants.map.put(1,Color.HOTPINK);
        Constants.map.put(2,Color.YELLOW);
        Constants.map.put(3,Color.INDIGO);
        
        mainMenuPane.getChildren().add(circleParallelDown);
        mainMenuPane.getChildren().add(circleParallelUp);
        circleParallelDown.setFill(Color.AZURE);
        circleParallelUp.setFill(Color.BEIGE);

        fadeTransitionDown.setFromValue(1.0f);
        fadeTransitionDown.setToValue(0.3f);
        fadeTransitionDown.setCycleCount(2);
        fadeTransitionDown.setAutoReverse(true);
        
        fadeTransitionUp.setFromValue(1.0f);
        fadeTransitionUp.setToValue(0.3f);
        fadeTransitionUp.setCycleCount(2);
        fadeTransitionUp.setAutoReverse(true);

        translateTransitionDown.setFromY(0);
        translateTransitionDown.setToY(200);
        translateTransitionDown.setCycleCount(2);
        translateTransitionDown.setAutoReverse(true);
        
        translateTransitionUp.setFromY(0);
        translateTransitionUp.setToY(-200);
        translateTransitionUp.setCycleCount(2);
        translateTransitionUp.setAutoReverse(true);

        scaleTransitionDown.setToX(2f);
        scaleTransitionDown.setToY(2f);
        scaleTransitionDown.setCycleCount(2);
        scaleTransitionDown.setAutoReverse(true);
        
        scaleTransitionUp.setToX(2f);
        scaleTransitionUp.setToY(2f);
        scaleTransitionUp.setCycleCount(2);
        scaleTransitionUp.setAutoReverse(true);
    
        parallelTransitionDown.getChildren().addAll(
            fadeTransitionDown,
            translateTransitionDown,
            scaleTransitionDown
         );
        
        parallelTransitionUp.getChildren().addAll(
                fadeTransitionUp,
                translateTransitionUp,
                scaleTransitionUp
             );
        parallelTransitionDown.setCycleCount(Timeline.INDEFINITE);
        parallelTransitionUp.setCycleCount(Timeline.INDEFINITE);
        
        parallelTransitionDown.play();
        parallelTransitionUp.play();
        
        for(int i=0;i<4;i++) {
            Arc arc = new Arc(300, 350, 150, 150, i * 90, 60);
            listOfOuterArcs.add(arc);
            arc.setFill(Color.TRANSPARENT);
            arc.setStroke(Constants.map.get(i));
            arc.setStrokeWidth(20);
            arc.setType(ArcType.OPEN);
            mainMenuPane.getChildren().add(arc);
        }
        for(int i=0;i<4;i++) {
            Arc arc = new Arc(300, 350, 120, 120, i * 90, 60);
            listOfMiddleArcs.add(arc);
            arc.setFill(Color.TRANSPARENT);
            arc.setStroke(Constants.map.get(i));
            arc.setStrokeWidth(20);
            arc.setType(ArcType.OPEN);
            mainMenuPane.getChildren().add(arc);
        }
        for(int i=0;i<4;i++) {
            Arc arc = new Arc(300, 350, 90, 90, i * 90, 60);
            listOfInnerArcs.add(arc);
            arc.setFill(Color.TRANSPARENT);
            arc.setStroke(Constants.map.get(i));
            arc.setStrokeWidth(20);
            arc.setType(ArcType.OPEN);
            mainMenuPane.getChildren().add(arc);
        }
        
        for(int i=0;i<4;i++) {
            Rotate rotate = new Rotate();
            listOfOuterRotate.add(rotate);
            rotate.setPivotX(300);
            rotate.setPivotY(350);
            rotate.setAngle(3.5);
        }
        for(int i=0;i<4;i++) {
            Rotate rotate = new Rotate();
            listOfMiddleRotate.add(rotate);
            rotate.setPivotX(300);
            rotate.setPivotY(350);
            rotate.setAngle(3);
        }
        for(int i=0;i<4;i++) {
            Rotate rotate = new Rotate();
            listOfInnerRotate.add(rotate);
            rotate.setPivotX(300);
            rotate.setPivotY(350);
            rotate.setAngle(2.5);
        }
        KeyFrame kf = new KeyFrame(Duration.millis(Constants.UPDATE_PERIOD), new TimeHandler());
        Timeline timeline = new Timeline(kf);
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }


    private class TimeHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            for(int i=0;i<4;i++) {
                listOfInnerArcs.get(i).getTransforms().add(listOfInnerRotate.get(i));
                listOfMiddleArcs.get(i).getTransforms().add(listOfMiddleRotate.get(i));
                listOfOuterArcs.get(i).getTransforms().add(listOfOuterRotate.get(i));
            }
        }
    }

    private void createButtons() {
        createStartButton();
        createLoadGameButton();
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

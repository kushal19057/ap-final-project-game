package application;

import java.util.ArrayList;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class CollideMenu{
    private Game gameManager;
    private transient Stage collideMenuStage;
    private transient Scene collideMenuScene;
    private transient AnchorPane collideMenuPane;
    
    private transient List<ColorSwitchButton> collideMenuButtons;
    private transient ColorSwitchButton reviveButton;
    private transient ColorSwitchButton restartButton;
    private transient ColorSwitchButton mainMenuButton;
    private transient ColorSwitchButton exitButton;
    
    public CollideMenu(Game gameManager) {
        this.gameManager = gameManager;
        initCollideMenuStage();
        collideMenuButtons = new ArrayList<>();
        createButtons();
        createBackground();
        
    }
    
    private void createButtons() {
        createReviveButton();
        createRestartButton();
        createBackToMainMenuButton();
        createExitButton();
    }
    
    private void createReviveButton() {
        reviveButton = new ColorSwitchButton("REVIVE( -2 STARS)");   
        addCollideMenuButton(reviveButton);
        reviveButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                gameManager.updateScoreOnRevival();
                collideMenuStage.close();
            }
        });
        
    }
    
    private void createRestartButton() {
        restartButton = new ColorSwitchButton("RESTART GAME");    
        addCollideMenuButton(restartButton);
        restartButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override 
            public void handle(ActionEvent actionEvent) {
                collideMenuStage.close();
                gameManager.closeStage();
                gameManager = new Game();
                gameManager.createNewGame();
                reviveButton.setDisable(false);
            }
        });
    }
    
    private void createBackToMainMenuButton() {
        mainMenuButton = new ColorSwitchButton("MAIN MENU");
        addCollideMenuButton(mainMenuButton);
        mainMenuButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent){
                collideMenuStage.close();
                gameManager.closeStage();
                reviveButton.setDisable(false);
            }
        });
    }
    
    private void createExitButton() {
        exitButton = new ColorSwitchButton("EXIT GAME");
        addCollideMenuButton(exitButton);
        exitButton.setOnAction(new EventHandler<ActionEvent>() {   
            @Override
            public void handle(ActionEvent arg0) {
                System.exit(0);
            }
        });
    }
    
    private void initCollideMenuStage() {
        collideMenuPane = new AnchorPane();
        collideMenuScene = new Scene(collideMenuPane, Constants.MENU_WIDTH/2, Constants.MENU_HEIGHT/2);
        collideMenuStage = new Stage();
        collideMenuStage.setTitle("Collision !");
        collideMenuStage.setScene(collideMenuScene);
        collideMenuStage.initModality(Modality.APPLICATION_MODAL);
        collideMenuStage.initStyle(StageStyle.UTILITY);
    }
    
    private void addCollideMenuButton(ColorSwitchButton button) {
        button.setLayoutX(30);
        button.setLayoutY(40 +  collideMenuButtons.size() * 70);
        collideMenuButtons.add(button);
        collideMenuPane.getChildren().add(button);
    }
    
    private void createBackground() {
        Image backgroundImage = new Image("resources/deep_blue.png");
        BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, null);
        collideMenuPane.setBackground(new Background(background));
    }

    
    public void showCollideMenu() {
        collideMenuStage.show();
    }
    
    public void reviveOption() {
        showCollideMenu();
    }
    
    public void restartOption() {
       // disable revive button
        reviveButton.setDisable(true);
        reviveOption();
    }
}

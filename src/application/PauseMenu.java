package application;

import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.util.List;
import java.io.IOException;
import java.util.ArrayList;

public class PauseMenu {
	private Game gameManager;
	private Stage pauseMenuStage;
    private Scene pauseMenuScene;
    private AnchorPane pauseMenuPane;
    
    private List<ColorSwitchButton> pauseMenuButtons;
    
	public PauseMenu(Game gameManager) {
		this.gameManager = gameManager;
		initPauseMenuStage();
		pauseMenuButtons = new ArrayList<>();
		createButtons();
		createBackground();
		createLogo();
		
	}
	
	private void createButtons() {
		createResumeButton();
		createRestartButton();
		createSaveAndBackToMainMenuButton();
		createExitWithoutSavingButton();
	}
	
	private void createResumeButton() {
		ColorSwitchButton resumeButton = new ColorSwitchButton("RESUME GAME");
		addPauseMenuButton(resumeButton);
		
		resumeButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				pauseMenuStage.close();
				gameManager.resumeGame();
			}
		});
		
	}
	
	private void createRestartButton() {
	    ColorSwitchButton restartButton = new ColorSwitchButton("RESTART GAME");
	    addPauseMenuButton(restartButton);
	    
	    restartButton.setOnAction(new EventHandler<ActionEvent>() {
	        @Override 
	        public void handle(ActionEvent actionEvent) {
	            pauseMenuStage.close();
	            gameManager.closeStage();
	            new Game();
	        }
	    });
	}
	
	private void createSaveAndBackToMainMenuButton() {
		ColorSwitchButton saveAndBackToMainMenuButton = new ColorSwitchButton("SAVE & MENU");
		addPauseMenuButton(saveAndBackToMainMenuButton);
		
		saveAndBackToMainMenuButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent){
				pauseMenuPane.setFocusTraversable(false);
				pauseMenuStage.close();
				gameManager.closeStage();
			}
		});
	}
	
	private void createExitWithoutSavingButton() {
		ColorSwitchButton exitWithoutSavingButton = new ColorSwitchButton("EXIT GAME");
		addPauseMenuButton(exitWithoutSavingButton);
		exitWithoutSavingButton.setOnAction(new EventHandler<ActionEvent>() {	
			@Override
			public void handle(ActionEvent arg0) {
				System.exit(0);
			}
		});
	}
	
	private void initPauseMenuStage() {
    	pauseMenuPane = new AnchorPane();
    	pauseMenuScene = new Scene(pauseMenuPane, Constants.MENU_WIDTH/2, Constants.MENU_HEIGHT/2);
    	pauseMenuStage = new Stage();
    	pauseMenuStage.setTitle("Pause Menu");
    	pauseMenuStage.setScene(pauseMenuScene);
    	pauseMenuStage.initModality(Modality.APPLICATION_MODAL);
    	pauseMenuStage.initStyle(StageStyle.UTILITY);
    }
	
	private void addPauseMenuButton(ColorSwitchButton button) {
        button.setLayoutX(30);
        button.setLayoutY(40 +  pauseMenuButtons.size() * 70);
        pauseMenuButtons.add(button);
        pauseMenuPane.getChildren().add(button);
    }
	
    private void createBackground() {
        Image backgroundImage = new Image("resources/backgroundCastles.png");
        BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, null);
        pauseMenuPane.setBackground(new Background(background));
    }

    private void createLogo() {
//        ImageView logo = new ImageView("resources/colorswitchlogo.png");
//        logo.setLayoutX(65);
//        logo.setLayoutY(30);
//        pauseMenuPane.getChildren().add(logo);
    }
    
    public void showPauseMenu() {
    	pauseMenuPane.setFocusTraversable(true);
    	pauseMenuStage.show();
    }
 }
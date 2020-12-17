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
import javafx.scene.layout.BackgroundSize;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

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
		
	}
	
	private void createButtons() {
		createResumeButton();
		createRestartButton();
		createSaveAndBackToMainMenuButton();
		createExitWithoutSavingButton();
		pauseMenuStage.setOnCloseRequest((event) -> {
		    System.out.println("Closing Stage");
		    pauseMenuStage.close();
		    gameManager.resumeGame();
		});
	}
	
	private void createResumeButton() {
		ColorSwitchButton resumeButton = new ColorSwitchButton("RESUME GAME");
		addPauseMenuButton(resumeButton);
		
		resumeButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				pauseMenuStage.close();
				//gameManager.resumeGame();
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
	            gameManager = new Game();
	            gameManager.createNewGame();
	        }
	    });
	}
	
	private void createSaveAndBackToMainMenuButton() {
		ColorSwitchButton saveAndBackToMainMenuButton = new ColorSwitchButton("SAVE & MENU");
		addPauseMenuButton(saveAndBackToMainMenuButton);
		
		saveAndBackToMainMenuButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent){
			    gameManager.serialize();
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
    	pauseMenuScene = new Scene(pauseMenuPane, Constants.MENU_HEIGHT/2, Constants.MENU_HEIGHT/2);
    	pauseMenuStage = new Stage();
    	pauseMenuStage.setTitle("Pause Menu");
    	pauseMenuStage.setScene(pauseMenuScene);
    	pauseMenuStage.initModality(Modality.APPLICATION_MODAL);
    	pauseMenuStage.initStyle(StageStyle.UTILITY);
    }
	
	private void addPauseMenuButton(ColorSwitchButton button) {
        button.setLayoutX(75);
        button.setLayoutY(60 +  pauseMenuButtons.size() * 70);
        pauseMenuButtons.add(button);
        pauseMenuPane.getChildren().add(button);
    }
	
    private void createBackground() {
        Image backgroundImage = new Image("resources/deep_blue.png");
        BackgroundImage background = new BackgroundImage(backgroundImage, null, null, null,new BackgroundSize(100,100,true,true,false,true));
        pauseMenuPane.setBackground(new Background(background));
    }

    
    public void showPauseMenu() {
    	pauseMenuStage.show();
    }
 }
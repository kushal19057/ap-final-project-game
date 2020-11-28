package application;

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
import java.util.ArrayList;

public class PauseMenu {
	private Stage pauseMenuStage;
    private Scene pauseMenuScene;
    private AnchorPane pauseMenuPane;
    
    private List<ColorSwitchButton> pauseMenuButtons;
    
	public PauseMenu(Stage gameStage) {
		initPauseMenuStage();
		pauseMenuStage.show();
		pauseMenuButtons = new ArrayList<>();
		createButtons();
		createBackground();
		createLogo();
		
	}
	
	private void createButtons() {
		createResumeButton();
		createSaveButton();
		createSaveAndBackToMainMenuButton();
		createExitWithoutSavingButton();
	}
	
	private void createResumeButton() {
		ColorSwitchButton resumeButton = new ColorSwitchButton("RESUME GAME");
		addPauseMenuButton(resumeButton);
	}
	
	private void createSaveButton() {
		ColorSwitchButton saveButton = new ColorSwitchButton("SAVE GAME");
		addPauseMenuButton(saveButton);
	}
	
	private void createSaveAndBackToMainMenuButton() {
		ColorSwitchButton saveAndBackToMainMenuButton = new ColorSwitchButton("TO MAIN MENU");
		addPauseMenuButton(saveAndBackToMainMenuButton);
	}
	
	private void createExitWithoutSavingButton() {
		ColorSwitchButton exitWithoutSavingButton = new ColorSwitchButton("EXIT GAME");
		addPauseMenuButton(exitWithoutSavingButton);
	}
	
	private void initPauseMenuStage() {
    	pauseMenuPane = new AnchorPane();
    	pauseMenuScene = new Scene(pauseMenuPane, Constants.MAIN_MENU_OPTION_WIDTH, Constants.MAIN_MENU_OPTION_HEIGHT);
    	pauseMenuStage = new Stage();
    	pauseMenuStage.setTitle("Pause Menu");
    	pauseMenuStage.setScene(pauseMenuScene);
    	pauseMenuStage.initModality(Modality.APPLICATION_MODAL);
    	pauseMenuStage.initStyle(StageStyle.UTILITY);
    }
	
	private void addPauseMenuButton(ColorSwitchButton button) {
        button.setLayoutX(Constants.MENU_BUTTONS_START_X - 110);
        button.setLayoutY(Constants.MENU_BUTTONS_START_Y - 50 + pauseMenuButtons.size() * 100);
        pauseMenuButtons.add(button);
        pauseMenuPane.getChildren().add(button);
    }
	
    private void createBackground() {
        Image backgroundImage = new Image("resources/deep_blue.png", 256, 256, false, true);
        BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, null);
        pauseMenuPane.setBackground(new Background(background));
    }

    private void createLogo() {
        ImageView logo = new ImageView("resources/colorswitchlogo.png");
        logo.setLayoutX(65);
        logo.setLayoutY(30);
        pauseMenuPane.getChildren().add(logo);
    }	
}
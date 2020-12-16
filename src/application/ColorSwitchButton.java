package application;

import java.io.File;
import java.io.Serializable;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class ColorSwitchButton extends Button implements Serializable {
	private static final long serialVersionUID = 1L;
    public ColorSwitchButton(String text) {
        setText(text);
        setButtonFont();
        setPrefWidth(250);
        setPrefHeight(50);
        setStyle(Constants.BUTTON_FREE_STYLE);
        initialiseButtonListeners();
    }

    private void setButtonFont() {
        setFont(Font.font("Verdana", FontWeight.BOLD, 20));
    }

    private void setButtonPressedStyle() {
        setStyle(Constants.BUTTON_PRESSED_STYLE);
        setPrefHeight(45);
        setLayoutY(getLayoutY() + 4);
    }

    private void setButtonReleasedStyle() {
        setStyle(Constants.BUTTON_FREE_STYLE);
        setPrefHeight(49);
        setLayoutY(getLayoutY() - 4);
    }

    private void initialiseButtonListeners() {
        setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                    setButtonPressedStyle();
                }
                // play music when button clicked
                try {
                	String file = "src/resources/click5.mp3";
                	Media sound = new Media(new File(file).toURI().toString());
                	MediaPlayer mediaPlayer = new MediaPlayer(sound);
                	mediaPlayer.play();
                } catch(Exception e) {
                	e.printStackTrace();
                }
            }
        });

        setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                    setButtonReleasedStyle();
                }
            }
        });

        setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                setEffect(new DropShadow());
            }
        });

        setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                setEffect(null);
            }
        });
    }
}
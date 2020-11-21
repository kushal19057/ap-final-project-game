import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.text.Font;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ScoreLabel extends Label {
    public ScoreLabel(String text) {
        setPrefWidth(250);
        setPrefHeight(50);
        setAlignment(Pos.CENTER);
        setPadding(new Insets(10, 10, 10, 10));
        setStyle(Constants.BUTTON_FREE_STYLE);
        setLabelFont();
        setText(text);
    }

    private void setLabelFont() {
        try {
        setFont(Font.loadFont(new FileInputStream(new File(Constants.FONT_PATH)), 25));
        } catch(FileNotFoundException e) {
        setFont(Font.font("Verdana", 25));
        }
    }
}

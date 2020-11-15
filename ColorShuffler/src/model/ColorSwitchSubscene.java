package model;

import javafx.animation.TranslateTransition;
import javafx.scene.SubScene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.transform.Translate;
import javafx.util.Duration;

public class ColorSwitchSubscene extends SubScene {
    private final static String FONT_PATH = "src/model/resources/kenvector_future.ttf";
    private final static String BACKGROUND_IMAGE = "model/resources/yellow_panel.png";

    private boolean isHidden;

    public ColorSwitchSubscene() {
        super(new AnchorPane(), 600, 400);
        prefWidth(600);
        prefHeight(400);

        BackgroundImage image = new BackgroundImage(new Image(BACKGROUND_IMAGE, 600, 400, false, true), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, null);
        AnchorPane root2 = (AnchorPane) this.getRoot();
        root2.setBackground(new Background(image));

        isHidden = true;
        setLayoutX(1024);
        setLayoutY(220);
    }

    public void moveSubScene() {
        TranslateTransition transition = new TranslateTransition();
        transition.setDuration(Duration.seconds(0.2));
        transition.setNode(this);

        if(isHidden) {
            transition.setToX(-676);
            isHidden = false;
        } else {
            transition.setToX(0);
            isHidden = true;
        }

        transition.play();
    }
}

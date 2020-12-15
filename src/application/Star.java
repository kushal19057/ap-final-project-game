package application;

import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import java.io.Serializable;

public class Star extends Actor implements Serializable {
	private transient ImageView starImage;
	
	public Star(double x, double y) {
		super(x, y);
	}
	
	public Star(double y) {
		this(Constants.GAME_WIDTH/2 - 25, y);
		initialise();
	}
	
	private void initialise() {
	    starImage = new ImageView(Constants.STAR_PATH);
        starImage.setLayoutX(currentPositionX);
        starImage.setLayoutY(currentPositionY);
        starImage.setFitHeight(50);
        starImage.setFitWidth(50);
        starImage.setPreserveRatio(true);
	}
	
	public void reinitialise() {
	    initialise();
	}
	
	@Override
	public void setPositionY(double y) {
		super.setPositionY(y);
		starImage.setLayoutY(y);
	}
	
	// remove from pane
	public void removeFromPane(AnchorPane pane) {
		pane.getChildren().remove(starImage);
	}
	
	public void addElementsToGamePane(AnchorPane pane) {
		pane.getChildren().add(starImage);
	}
}

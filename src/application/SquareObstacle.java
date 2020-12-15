package application;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.transform.Rotate;

public class SquareObstacle extends Obstacle implements Serializable {
    private transient List<Rectangle> listOfRectangles;
    private transient List<Rotate> listOfRotates;
    public static final double LENGTH = 300;
    public static final double WIDTH = 30;
    
    public SquareObstacle(double y) {
        super(Constants.GAME_WIDTH/2, y);
        initialise();
    }
    
    private void initialise() {
        listOfRectangles = new ArrayList<>();
        listOfRotates = new ArrayList<>();
        initRectangles();
        initRotate();
    }
    
    @Override
    public void reinitialise() {
        initialise();
    }
    
    private void initRectangles() {
        Rectangle down = new Rectangle();
        down.setX(currentPositionX - LENGTH/2);
        down.setY(currentPositionY + LENGTH/2);
        down.setWidth(LENGTH);
        down.setHeight(WIDTH);
        Rectangle up = new Rectangle();
        up.setX(currentPositionX - LENGTH/2 );
        up.setY(currentPositionY - LENGTH/2 - WIDTH/2);
        up.setWidth(LENGTH);
        up.setHeight(WIDTH);
        Rectangle left = new Rectangle();
        left.setX(currentPositionX - LENGTH/2 - WIDTH);
        left.setY(currentPositionY - LENGTH/2 + WIDTH/3);
        left.setWidth(WIDTH);
        left.setHeight(LENGTH);
        Rectangle right = new Rectangle();
        right.setX(currentPositionX + LENGTH/2);
        right.setY(currentPositionY - LENGTH/2 + WIDTH/3);
        right.setWidth(WIDTH);
        right.setHeight(LENGTH);
        listOfRectangles.add(down);
        listOfRectangles.add(up);
        listOfRectangles.add(left);
        listOfRectangles.add(right);
        
        for(int i=0; i<listOfRectangles.size(); i++) {
            Rectangle r = listOfRectangles.get(i);
            r.setFill(Constants.map.get(i));
            r.setStroke(Color.ORANGE);
            r.setStrokeWidth(5);
            r.setArcHeight(40);
            r.setArcWidth(40);
        }
    }
    
    public void initRotate() {
        for(int i=0;i<4; i++) {
           Rotate r = new Rotate();
           listOfRotates.add(r);
           r.setPivotX(currentPositionX);
           r.setPivotY(currentPositionY);
           r.setAngle(angle);
        }
    }
    
    @Override
    public void rotate() {
        for(int i=0;i<4;i++) {
            listOfRectangles.get(i).getTransforms().add(listOfRotates.get(i));
        }
    }
    
    @Override
    public void addElementsToGamePane(AnchorPane gamePane) {
        for(Rectangle r : listOfRectangles) {
            gamePane.getChildren().add(r);
        }
    }

    public void setPositionY(double currentPositonY) {
        super.setPositionY(currentPositonY);
        listOfRectangles.get(0).setY(currentPositionY + LENGTH/2);
        listOfRectangles.get(1).setY(currentPositionY - LENGTH/2 - WIDTH/2);
        listOfRectangles.get(2).setY(currentPositionY - LENGTH/2 + WIDTH/3);
        listOfRectangles.get(3).setY(currentPositionY - LENGTH/2 + WIDTH/3);
        for(Rotate r : listOfRotates) {
            r.setPivotY(currentPositonY);
        }
    }
    
    public List<? extends Shape> getElements() {
        return this.listOfRectangles;
    }
    
    public void removeFromPane(AnchorPane pane) {
        for(Rectangle r : listOfRectangles) {
            pane.getChildren().remove(r);
        }
    }
}

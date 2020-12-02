package application;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Shape;
import javafx.scene.transform.Rotate;

public class DoubleCircleObstacle extends Obstacle {
    private List<Arc> listOfLeftArcs;
    private List<Arc> listOfRightArcs;
    private List<Rotate> listOfLeftRotate;
    private List<Rotate> listOfRightRotate;
    private double angle;
    
    private static final double ARC_RADIUS = 150d;
    private static final double ARC_LENGTH = 50d;
    public static final double STROKE_WIDTH = 20;

    public DoubleCircleObstacle(double y) {
        super(Constants.GAME_WIDTH/2, y);
        listOfLeftArcs = new ArrayList<>();
        listOfRightArcs = new ArrayList<>();
        listOfLeftRotate = new ArrayList<>();
        listOfRightRotate = new ArrayList<>();
        angle = 2;
        initArcs();
        initRotate();        
    }
    
    private void initArcs() {
        for(int i=0;i<4;i++) {
            Arc arc = new Arc(currentPositionX - ARC_RADIUS, currentPositionY, ARC_RADIUS, ARC_RADIUS, i *90, ARC_LENGTH);
            listOfLeftArcs.add(arc);
            arc.setFill(Color.TRANSPARENT);
            arc.setStroke(Constants.map.get(i));
            arc.setStrokeWidth(STROKE_WIDTH);
            arc.setType(ArcType.OPEN);  
        }
        for(int i=0;i<4;i++) {
            Arc arc = new Arc(currentPositionX + ARC_RADIUS, currentPositionY, ARC_RADIUS, ARC_RADIUS, i *90, ARC_LENGTH);
            listOfRightArcs.add(arc);
            arc.setFill(Color.TRANSPARENT);
            arc.setStroke(Constants.map.get(i));
            arc.setStrokeWidth(STROKE_WIDTH);
            arc.setType(ArcType.OPEN);  
        }
    }
    
    private void initRotate() {
        for(int i=0;i<4;i++) {
            Rotate rotate = new Rotate();
            listOfLeftRotate.add(rotate);
            rotate.setPivotX(currentPositionX - ARC_RADIUS);
            rotate.setPivotY(currentPositionY);
            rotate.setAngle(angle);
        }
        for(int i=0;i<4;i++) {
            Rotate rotate = new Rotate();
            listOfRightRotate.add(rotate);
            rotate.setPivotX(currentPositionX + ARC_RADIUS);
            rotate.setPivotY(currentPositionY);
            rotate.setAngle(-2 * angle);
        }
    }

    @Override
    public void addElementsToGamePane(AnchorPane pane) {
        // TODO Auto-generated method stub
        for(Arc arc : listOfLeftArcs) {
            pane.getChildren().add(arc);
        }
        for(Arc arc : listOfRightArcs) {
            pane.getChildren().add(arc);
        }

    }

    @Override
    public void rotate() {
        // TODO Auto-generated method stub
        for(int i=0;i<4;i++) {
            listOfLeftArcs.get(i).getTransforms().add(listOfLeftRotate.get(i));
            listOfRightArcs.get(i).getTransforms().add(listOfRightRotate.get(i));
        }

    }

    @Override
    public List<? extends Shape> getElements() {
        // TODO Auto-generated method stub
        ArrayList<Arc> arr = new ArrayList<>();
        arr.addAll(listOfLeftArcs);
        arr.addAll(listOfRightArcs);
        return arr;
    }

    @Override
    public void removeFromPane(AnchorPane gamePane) {
        // TODO Auto-generated method stub
        for(Arc arc : listOfLeftArcs) {
            gamePane.getChildren().remove(arc);
        }
        for(Arc arc : listOfRightArcs) {
            gamePane.getChildren().remove(arc);
        }

    }
    
    public void setPositionY(double currentPositionY) {
        super.setPositionY(currentPositionY);
        for(Arc arc : listOfLeftArcs) {
            arc.setCenterY(currentPositionY);
        }
        for(Arc arc : listOfRightArcs) {
            arc.setCenterY(currentPositionY);
        }
        for(Rotate r : listOfLeftRotate) {
            r.setPivotY(currentPositionY);
        }
        for(Rotate r : listOfRightRotate) {
            r.setPivotY(currentPositionY);
        }
    }

}

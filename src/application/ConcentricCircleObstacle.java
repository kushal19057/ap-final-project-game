package application;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Shape;
import javafx.scene.transform.Rotate;

public class ConcentricCircleObstacle extends Obstacle {
    private List<Arc> listOfInnerArcs;
    private List<Arc> listOfOuterArcs;
    private List<Rotate> listOfInnerRotate;
    private List<Rotate> listOfOuterRotate;
    private double angle;
    
    private static final double INNER_ARC_RADIUS = 150d;
    private static final double OUTER_ARC_RADIUS = 175;
    private static final double ARC_LENGTH = 60d;
    public static final double STROKE_WIDTH = 20;

    public ConcentricCircleObstacle(double y) {
        super(Constants.GAME_WIDTH/2, y);
        listOfInnerArcs = new ArrayList<>();
        listOfOuterArcs = new ArrayList<>();
        listOfInnerRotate = new ArrayList<>();
        listOfOuterRotate = new ArrayList<>();
        angle = 1.8;
        initArcs();
        initRotate();        
    }
    
    private void initArcs() {
        for(int i=0;i<4;i++) {
            Arc arc = new Arc(currentPositionX, currentPositionY, INNER_ARC_RADIUS, INNER_ARC_RADIUS, i *90, ARC_LENGTH);
            listOfInnerArcs.add(arc);
            arc.setFill(Color.TRANSPARENT);
            arc.setStroke(Constants.map.get(i));
            arc.setStrokeWidth(STROKE_WIDTH);
            arc.setType(ArcType.OPEN);  
        }
        for(int i=0;i<4;i++) {
            Arc arc = new Arc(currentPositionX, currentPositionY, OUTER_ARC_RADIUS, OUTER_ARC_RADIUS, i *90, ARC_LENGTH);
            listOfOuterArcs.add(arc);
            arc.setFill(Color.TRANSPARENT);
            arc.setStroke(Constants.map.get(i));
            arc.setStrokeWidth(STROKE_WIDTH);
            arc.setType(ArcType.OPEN);  
        }
    }
    
    private void initRotate() {
        for(int i=0;i<4;i++) {
            Rotate rotate = new Rotate();
            listOfInnerRotate.add(rotate);
            rotate.setPivotX(currentPositionX);
            rotate.setPivotY(currentPositionY);
            rotate.setAngle(2*angle);
        }
        for(int i=0;i<4;i++) {
            Rotate rotate = new Rotate();
            listOfOuterRotate.add(rotate);
            rotate.setPivotX(currentPositionX);
            rotate.setPivotY(currentPositionY);
            rotate.setAngle(angle);
        }
    }

    @Override
    public void addElementsToGamePane(AnchorPane pane) {
        // TODO Auto-generated method stub
        for(Arc arc : listOfInnerArcs) {
            pane.getChildren().add(arc);
        }
        for(Arc arc : listOfOuterArcs) {
            pane.getChildren().add(arc);
        }

    }

    @Override
    public void rotate() {
        // TODO Auto-generated method stub
        for(int i=0;i<4;i++) {
            listOfInnerArcs.get(i).getTransforms().add(listOfInnerRotate.get(i));
            listOfOuterArcs.get(i).getTransforms().add(listOfOuterRotate.get(i));
        }

    }

    @Override
    public List<? extends Shape> getElements() {
        // TODO Auto-generated method stub
        ArrayList<Arc> arr = new ArrayList<>();
        arr.addAll(listOfInnerArcs);
        arr.addAll(listOfOuterArcs);
        return arr;
    }

    @Override
    public void removeFromPane(AnchorPane gamePane) {
        // TODO Auto-generated method stub
        for(Arc arc : listOfInnerArcs) {
            gamePane.getChildren().remove(arc);
        }
        for(Arc arc : listOfOuterArcs) {
            gamePane.getChildren().remove(arc);
        }

    }
    
    public void setPositionY(double currentPositionY) {
        super.setPositionY(currentPositionY);
        for(Arc arc : listOfInnerArcs) {
            arc.setCenterY(currentPositionY);
        }
        for(Arc arc : listOfOuterArcs) {
            arc.setCenterY(currentPositionY);
        }
        for(Rotate r : listOfInnerRotate) {
            r.setPivotY(currentPositionY);
        }
        for(Rotate r : listOfOuterRotate) {
            r.setPivotY(currentPositionY);
        }
    }

}

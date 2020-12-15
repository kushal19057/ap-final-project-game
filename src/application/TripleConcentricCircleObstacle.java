package application;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Shape;
import javafx.scene.transform.Rotate;

public class TripleConcentricCircleObstacle extends Obstacle implements Serializable {
    private List<Arc> listOfInnerArcs;
    private List<Arc> listOfMiddleArcs;
    private List<Arc> listOfOuterArcs;
    
    private List<Rotate> listOfInnerRotate;
    private List<Rotate> listOfMiddleRotate;
    private List<Rotate> listOfOuterRotate;
    
    private static final double INNER_ARC_RADIUS = 150d;
    private static final double MIDDLE_ARC_RADIUS = 175;
    private static final double OUTER_ARC_RADIUS = 200;
    private static final double ARC_LENGTH = 60d;
    public static final double STROKE_WIDTH = 20;



    public TripleConcentricCircleObstacle(double y) {
        super(Constants.GAME_WIDTH/2, y);
        initialise();
    
    }
    
    private void initialise() {
        listOfInnerArcs = new ArrayList<>();
        listOfMiddleArcs = new ArrayList<>();
        listOfOuterArcs = new ArrayList<>();

        listOfInnerRotate = new ArrayList<>();
        listOfMiddleRotate = new ArrayList<>();
        listOfOuterRotate = new ArrayList<>();

        initArcs();
        initRotate();    
    }
    
    private void initArcs() {
        for(int i=0;i<4;i++) 
        {
            Arc arc = new Arc(currentPositionX, currentPositionY, INNER_ARC_RADIUS, INNER_ARC_RADIUS, i *90, ARC_LENGTH);
            listOfInnerArcs.add(arc);
            arc.setFill(Color.TRANSPARENT);
            arc.setStroke(Constants.map.get(i));
            arc.setStrokeWidth(STROKE_WIDTH);
            arc.setType(ArcType.OPEN);  
        }
        for(int i=0;i<4;i++) 
        {
            Arc arc = new Arc(currentPositionX, currentPositionY, MIDDLE_ARC_RADIUS, MIDDLE_ARC_RADIUS, i *90, ARC_LENGTH);
            listOfMiddleArcs.add(arc);
            arc.setFill(Color.TRANSPARENT);
            arc.setStroke(Constants.map.get(i));
            arc.setStrokeWidth(STROKE_WIDTH);
            arc.setType(ArcType.OPEN);  
        }
        for(int i=0;i<4;i++)
         {
            Arc arc = new Arc(currentPositionX, currentPositionY, OUTER_ARC_RADIUS, OUTER_ARC_RADIUS, i *90, ARC_LENGTH);
            listOfOuterArcs.add(arc);
            arc.setFill(Color.TRANSPARENT);
            arc.setStroke(Constants.map.get(i));
            arc.setStrokeWidth(STROKE_WIDTH);
            arc.setType(ArcType.OPEN);  
        }
    }
    
    private void initRotate() {
        for(int i=0;i<4;i++)                         //rotation for the inner circle
        {
            Rotate rotate = new Rotate();
            listOfInnerRotate.add(rotate);
            rotate.setPivotX(currentPositionX);
            rotate.setPivotY(currentPositionY);
            rotate.setAngle(1.2*angle);
        }
        for(int i=0;i<4;i++)                         //rotation for the middle circle
         {
            Rotate rotate = new Rotate();
            listOfMiddleRotate.add(rotate);
            rotate.setPivotX(currentPositionX);
            rotate.setPivotY(currentPositionY);
            rotate.setAngle(1.2*angle);
        }
        for(int i=0;i<4;i++)                         // rotation for the outer circle
         {
            Rotate rotate = new Rotate();
            listOfOuterRotate.add(rotate);
            rotate.setPivotX(currentPositionX);
            rotate.setPivotY(currentPositionY);
            rotate.setAngle(1.2*angle);
        }
    }

    @Override
    public void addElementsToGamePane(AnchorPane pane) {
        // TODO Auto-generated method stub
        for(Arc arc : listOfInnerArcs) {
            pane.getChildren().add(arc);
        }
        for(Arc arc : listOfMiddleArcs) {
            pane.getChildren().add(arc);
        }
        for(Arc arc : listOfOuterArcs) {
            pane.getChildren().add(arc);
        }

    }

    @Override
    public void rotate() {
        // TODO Auto-generated method stub
        for(int i=0;i<4;i++)
         {
            listOfInnerArcs.get(i).getTransforms().add(listOfInnerRotate.get(i));
            listOfMiddleArcs.get(i).getTransforms().add(listOfMiddleRotate.get(i));
            listOfOuterArcs.get(i).getTransforms().add(listOfOuterRotate.get(i));
        }

    }

    @Override
    public List<? extends Shape> getElements() {
        // TODO Auto-generated method stub
        ArrayList<Arc> arr = new ArrayList<>();
        arr.addAll(listOfInnerArcs);
        arr.addAll(listOfMiddleArcs);
        arr.addAll(listOfOuterArcs);
        return arr;
    }

    @Override
    public void removeFromPane(AnchorPane gamePane) {
        // TODO Auto-generated method stub
        for(Arc arc : listOfInnerArcs) {
            gamePane.getChildren().remove(arc);
        }
        for(Arc arc : listOfMiddleArcs) {
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
        for(Arc arc : listOfMiddleArcs) {
            arc.setCenterY(currentPositionY);
        }
        for(Arc arc : listOfOuterArcs) {
            arc.setCenterY(currentPositionY);
        }
        for(Rotate r : listOfInnerRotate) {
            r.setPivotY(currentPositionY);
        }
        for(Rotate r : listOfMiddleRotate) {
            r.setPivotY(currentPositionY);
        }
        for(Rotate r : listOfOuterRotate) {
            r.setPivotY(currentPositionY);
        }
    }
    
    @Override
    public void reinitialise() {
    	initialise();
    }


}
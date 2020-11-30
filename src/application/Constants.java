package application;

import java.util.HashMap;

import javafx.scene.paint.Paint;

public class Constants {
    public static final int MENU_WIDTH = 600;
    public static final int MENU_HEIGHT = 800;

    public static final int MENU_BUTTONS_START_X = 200;
    public static final int MENU_BUTTONS_START_Y = 250;

    public static final int GAME_WIDTH = 768;
    public static final int GAME_HEIGHT = 1024;

    public static final int MAIN_MENU_OPTION_WIDTH = 400;
    public static final int MAIN_MENU_OPTION_HEIGHT = 600;

    public static final double UPDATE_PERIOD = 25;
    public static final int GRAVITY = 3; 
    public static final int VELOCITY_JUMP = -8; 

    public static final String FONT_PATH = "src/resources/kenvector_future_thin.ttf";
    public static final String STAR_PATH = "resources/star_silver.png";

    public static final String BUTTON_PRESSED_STYLE = "-fx-background-color: #f0ffff;";
    public static final String BUTTON_FREE_STYLE = "-fx-background-color: #f0f8ff;";
    
    public static final int BALL_RADIUS = 20;
    public static final int STAR_RADIUS = 12;
    
    public static final int DISTANCE_BETWEEN_OBSTACLES = 600;
    
    public static final int START_DELTA_BALL = 100;
    
    public static final int HEIGHT_AFTER_WHICH_OBSTACLE_GENERATE = 200;
    
    public final static HashMap<Integer,Paint> map = new HashMap<>();

}
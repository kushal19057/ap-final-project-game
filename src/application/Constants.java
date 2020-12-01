package application;

import java.util.HashMap;

import javafx.scene.paint.Paint;

public final class Constants {
    public static final double MENU_WIDTH = 600;
    public static final double MENU_HEIGHT = 800;

    public static final double MENU_BUTTONS_START_X = 180;
    public static final double MENU_BUTTONS_START_Y = 600;

    public static final double GAME_WIDTH = 768;
    public static final double GAME_HEIGHT = 1024;

    public static final double UPDATE_PERIOD = 30;
    public static final double GRAVITY = 3; 
    public static final double VELOCITY_JUMP = -8; 

    public static final String FONT_PATH = "src/resources/kenvector_future_thin.ttf";
    public static final String STAR_PATH = "resources/star_silver.png";

    public static final String BUTTON_PRESSED_STYLE = "-fx-background-color: #f099a6;";
    public static final String BUTTON_FREE_STYLE = "-fx-background-color: #f0f8ff;";
    
    public static final double BALL_RADIUS = 20;
    public static final double STAR_RADIUS = 30;
    
    public static final double DISTANCE_BETWEEN_OBSTACLES = 600;
    
    public static final double START_DELTA_BALL = 100;
    
    public static final double HEIGHT_AFTER_WHICH_OBSTACLE_GENERATE = 100;
    
    public final static HashMap<Integer,Paint> map = new HashMap<>();
    
    //public static final String PATH_BAKGROUND_MUSIC = "resources/background_music.ogg";
    
    public static final int THRESHOLD_SCORE_REVIVAL = 2;
    
    public static final String FILE_START_STRING = "ColorSwitchSavedGame_";

}
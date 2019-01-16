package gui;

import javafx.scene.paint.Color;


//This class is formed in order to store every important value inside the game
public class GameValues {


    //Registration password length constraint
    public static int MAX_PASS_LENGTH = 6;

    //Values for pane
    public static int LOGIN_REGISTER_PANE_WIDTH = 300;
    public static int LOGIN_REGISTER_PANE_HEIGHT = 400;
    public static String LOGIN_FXML_PATH = "/fxml/login.fxml";
    public static String WEEKLY_LEADERBOARD_FXML_PATH = "/fxml/weekly_leaderboard.fxml";
    public static String ALL_TIME_LEADERBOARD_FXML_PATH = "/fxml/all_time_leaderboard.fxml";
    public static String MAINMENU_FXML_PATH = "/fxml/mainmenu.fxml";
    public static String REGISTRATION_FXML_PATH = "/fxml/registration.fxml";


    //Paths of images for levels
    public static String LEVEL1_GAME_PANE_IMAGE_PATH = "gui/game/gameimages/space7.jpg";
    public static String LEVEL2_GAME_PANE_IMAGE_PATH = "gui/game/gameimages/space4.jpg";
    public static String LEVEL3_GAME_PANE_IMAGE_PATH = "gui/game/gameimages/space11.jpg";
    public static String LEVEL4_GAME_PANE_IMAGE_PATH = "gui/game/gameimages/space10.jpg";


    //Values of GamePane
    public static int GAME_PANE_HEIGHT = 750;
    public static int GAME_PANE_WIDTH = 1200;


    //Paths of sound files
    public static String GAME_SOUND_PATH = "gui/game/gamesounds/game_music1.mp3";
    public static String GAME_LOSE_SOUND_PATH = "gui/game/gamesounds/commander_logar_speech1.mp3";
    public static String TIHULU_SOUND_PATH = "gui/game/gamesounds/bircisimyaklasiyor.mp3";
    public static String KUNA_SOUND_PATH = "gui/game/gamesounds/yakariz.mp3";

    //Fade Transition time in miliseconds
    public static int FADE_SCENE_TRANS_TIME = 6000;


    //Number of enemies to be spawned each level
    public static int LEVEL1_PLEKUMAT_NO = 10;
    public static int LEVEL2_PLEKUMAT_NO = 15;
    public static int LEVEL3_PLEKUMAT_NO = 5;

    public static int LEVEL1_BOSS_PLEKUMAT_NO = 0;
    public static int LEVEL2_BOSS_PLEKUMAT_NO = 1;
    public static int LEVEL3_BOSS_PLEKUMAT_NO = 3;

    //Timeline Durations in miliseconds
    public static int GAME_TIMELINE_DURATION = 10;
    public static int PLEKUMAT_FIRE_DURATION = 200;
    public static int BOSS_PLEKUMAT_FIRE_DURATION = 400;

    //Http request url's

    //BASE_HOST can be changed during evauation. First commented address is for evaluation, second address is for local testing

    public static String BASE_HOST = "http://144.122.71.144:8080/"; //"http://144.122.71.144:8080/" for evaluation ,  "http://localhost:8080/" for local testing

    public static String UPDATE_WEEKLY_TABLE_REQUEST_URL = BASE_HOST + "update_score_weekly";
    public static String UPDATE_ALL_TIME_TABLE_REQUEST_URL = BASE_HOST + "update_score_alltime";
    public static String REGISTRATION_URL = BASE_HOST + "registration";
    public static String WEEKLY_LEADERBOARD_REQUEST_URL = BASE_HOST + "get_weekly_leaderboard";
    public static String ALL_TIME_LEADERBOARD_REQUEST_URL = BASE_HOST + "get_all_time_leaderboard";
    public static String SIGN_IN_REQUEST_URL = BASE_HOST + "sign_in";


    //Properties of game Over Text in game Over window
    public static int LAYOUT_X = 350;
    public static int LAYOUT_Y = 375;
    public static int MIN_WIDTH = 1200;
    public static String FONT = "Arial";
    public static int FONT_SIZE = 40;
    public static String MESSAGE = "YOU LOST, GAME OVER!";
    public static Color FONT_COLOR = Color.CRIMSON;
    public static Color FONT_COLOR2 = Color.DARKOLIVEGREEN;

    //Waitin for player messages
    public static String WAITING_FOR_PLAYER = "Waiting for other player...";
    //Winning message
    public static String WIN_MESSAGE = "YOU WIN, BRAVO!";

    //Player Properties
    public static int MAX_HEALTH = 100;


    //Score Reduction From Enemy
    public static int SCORE_REDUCTION_PLEKUMAT = 2;
    public static int SCORE_REDUCTION_BOSS_PLEKUMAT = 5;


    //Random Occurences
    public static double PISMANIYE_FREQUENCY = 0.002;
    public static double ALIEN_ADD_FREQUENCY = 0.003;


    //SpawnBorderHeight value
    public static int SPAWN_BORDER_HEIGHT = 250;

    //Pismaniye Values
    public static double PISMANIYE_RADIUS = 6;
    public static Color PISMANIYE_COLOUR = Color.LIGHTGREEN;
    public static double PISMANIYE_DELTA_X = 0;
    public static double PISMANIYE_DELTA_Y = -1;
    public static int PISMANIYE_DAMAGE = 0;


    //Plekumat Values
    public static int PLEKUMAT_BULLET_RADIUS = 3;
    public static int PLEKUMAT_BULLET_DAMAGE = 5;
    public static double PLEKUMAT_BULLET_DELTA_X = 0;
    public static double PLEKUMAT_BULLET_DELTA_Y = -1;
    public static Color PLEKUMAT_BULLET_COLOUR = Color.RED;

    public static int PLEKUMAT_HEALTH = 20;
    public static int PLEKUMAT_VALUE = 10;
    public static double PLEKUMAT_RADIUS = 17;
    public static double PLEKUMAT_DELTA_X = 3;
    public static Color PLEKUMAT_COLOUR = Color.RED;
    public static boolean PLEKUMAT_DIRECTION = false;
    public static double PLEKUMAT_GUDUMLU_FIRE_FREQUENCY = 0.5;


    //Player Values
    public static double PLAYER_BULLET_RADIUS = 5;
    public static int PLAYER_WEAPON_LVL0_DAMAGE = 10;
    public static int PLAYER_WEAPON_LVL1_DAMAGE = 15;
    public static int PLAYER_WEAPON_LVL2_DAMAGE = 35;
    public static int PLAYER_WEAPON_LVL3_DAMAGE = 55;

    public static double PLAYER_WEAPON_LVL1_DELTA_X = 0;
    public static double PLAYER_WEAPON_LVL1_DELTA_Y = 10;
    public static double PLAYER_WEAPON_LVL1_BULLET_SEPERATION = 0.5;

    public static double PLAYER_WEAPON_LVL2_DELTA_X = 0;
    public static double PLAYER_WEAPON_LVL2_DELTA_Y = 10;
    public static double PLAYER_WEAPON_LVL2_BULLET_SEPERATION = 0.5;

    public static double PLAYER_WEAPON_LVL3_DELTA_X = 0.5;
    public static double PLAYER_WEAPON_LVL3_DELTA_Y = 10;
    public static double PLAYER_WEAPON_LVL3_BULLET_SEPERATION = 0.85;


    public static double PLAYER_BULLET_DELTA_X = 0;
    public static double PLAYER_BULLET_DELTA_Y = 10;
    public static Color PLAYER_BULLET_COLOUR = Color.BLUE;

    public static int PLAYER_MAX_HEALTH = 100;
    public static double PLAYER_RADIUS = 17;
    public static double PLAYER_SPAWN_CENTER_X = 600;
    public static double PLAYER_SPAWN_CENTER_Y = 750 - PLAYER_RADIUS;
    public static Color PLAYER_COLOUR = Color.BLUE;

    public static double PLAYER1_SPAWN_CENTER_X = 300;
    public static double PLAYER1_SPAWN_CENTER_Y = 750 - PLAYER_RADIUS;

    public static double PLAYER2_SPAWN_CENTER_X = 900;
    public static double PLAYER2_SPAWN_CENTER_Y = 750 - PLAYER_RADIUS;

    public static double MAX_DRAG = 3;
    public static double DRAG_CHANGE_RATE = 0.03;
    public static double DRAG_VALUE_HIT_BY_ENEMY = 0.3;

    //multiplayer opponent colour
    public static Color MULTIPLAYER_OPPONENT_COLOUR = Color.CYAN;
    public static Color MULTIPLAYER_OPPONENT_BULLET_COLOUR = Color.CYAN;

    //Default particle radius value
    public static double DEFAULT_PARTICULE_RADIUS = 5;

    //Boss Plekumat Values
    public static double BOSS_PLEKUMAT_BULLET_RADIUS = 6;
    public static int BOSS_PLEKUMAT_BULLET_DAMAGE = 20;
    public static double BOSS_PLEKUMAT_BULLET_DELTA_X = 0;
    public static double BOSS_PLEKUMAT_BULLET_DELTA_Y = 1;
    public static Color BOSS_PLEKUMAT_BULLET_COLOR = Color.CRIMSON;


    public static double BOSS_PLEKUMAT_RADIUS = 30;
    public static double BOSS_PLEKUMAT_DELTA_X = 1;
    public static boolean BOSS_PLEKUMAT_DIRECTION = false;
    public static int BOSS_PLEKUMAT_MAX_HEALTH = 100;
    public static int BOSS_PLEKUMAT_VALUE = 40;
    public static Color BOSS_PLEKUMAT_COLOUR = Color.CRIMSON;
    public static double BOSS_PLEKUMAT_GUDUMLU_FIRE_CHANCE = 0.9;
    public static double BOSS_PLEKUMAT_BULLET_SEPERATION_ANGLE = 20;


    //BirCisim Values
    public static double BIRCISIM_BULLET1_RADIUS = 10;
    public static int BIRCISIM_BULLET1_DAMAGE = 50;
    public static double BIRCISIM_BULLET2_RADIUS = 3;
    public static int BIRCISIM_BULLET2_DAMAGE = 10;
    public static double BIRCISIM_BULLET1_DELTA_X = 0;
    public static double BIRCISIM_BULLET1_DELTA_Y = 0.5;
    public static double BIRCISIM_BULLET2_DELTA_X = 0;
    public static double BIRCISIM_BULLET2_DELTA_Y = 1;

    public static double BIRCISIM_BULLET1_SEPERATION_ANGLE = 40;


    public static Color BIRCISIM_BULLET1_COLOUR = Color.CRIMSON;
    public static Color BIRCISIM_BULLET2_COLOUR = Color.CRIMSON;


    public static int BIRCISIM_FIRE1_COUNTER = 150;
    public static int BIRCISIM_FIRE2_COUNTER = 50;
    public static double BIRCISIM_SPAWN_X = 600;
    public static double BIRCISIM_SPAWN_Y = 100;
    public static double BIRCISIM_RADIUS = 50;
    public static double BIRCISIM_DELTA_X = 0;
    public static boolean BIRCISIM_DIRECTION = false;
    public static int BIRCISIM_MAX_HEALTH = 1000;
    public static int BIRCISIM_VALUE = 15000;
    public static Color BIRCISIM_COLOUR = Color.CRIMSON;



}

package GUI;

import javafx.scene.paint.Color;


//This class is formed in order to store every important value inside the game
public class GameValues {


    //Registration password length constraint
    public static int max_pass_legth = 6;

    //Values for pane
    public static int Login_Register_Pane_Width = 300;
    public static int Login_Register_Pane_Height = 400;
    public static String Login_FXML_Path = "/fxml/login.fxml";
    public static String Weekly_Leaderboard_FXML_Path = "/fxml/weekly_leaderboard.fxml";
    public static String All_Time_Leaderboard_FXML_Path = "/fxml/all_time_leaderboard.fxml";
    public static String Mainmenu_FXML_Path = "/fxml/mainmenu.fxml";
    public static String Registration_FXML_Path = "/fxml/registration.fxml";


    //Paths of images for levels
    public static String Level1_GamePane_Image_Path = "GUI/Game/Game_Images/space7.jpg";
    public static String Level2_GamePane_Image_Path = "GUI/Game/Game_Images/space4.jpg";
    public static String Level3_GamePane_Image_Path = "GUI/Game/Game_Images/space11.jpg";
    public static String Level4_GamePane_Image_Path = "GUI/Game/Game_Images/space10.jpg";


    //Values of Game_Pane
    public static int Game_Pane_Height = 750;
    public static int Game_Pane_Width = 1200;


    //Paths of sound files
    public static String Game_Sound_Path = "GUI/Game/Game_Sounds/game_music1.mp3";
    public static String Game_LoseSound_Path = "GUI/Game/Game_Sounds/commander_logar_speech1.mp3";

    //Fade Transition time in miliseconds
    public static int Fade_Scene_Trans_Time = 6000;


    //Number of enemies to be spawned each level
    public static int Level1_Plekumat_No = 10;
    public static int Level2_Plekumat_No = 15;
    public static int Level3_Plekumat_No = 5;
    public static int Level4_Plekumat_No = 12;

    public static int Level1_BossPlekumat_No = 0;
    public static int Level2_BossPlekumat_No = 1;
    public static int Level3_BossPlekumat_No = 3;
    public static int Level4_BossPlekumat_No = 12;

    //Timeline Durations in miliseconds
    public static int Game_Timeline_Duration = 10;
    public static int Plekumat_Fire_Duration = 200;
    public static int Boss_Plekumat_Fire_Duration = 400;

    //Http request url's
    public static String Update_Weekly_Table_Request_Url = "http://localhost:8080/update_score_weekly";
    public static String Update_AllTime_Table_Request_Url = "http://localhost:8080/update_score_alltime";
    public static String Registration_Url = "http://localhost:8080/registration";
    public static String Weekly_Leaderboard_Request_Url = "http://localhost:8080/get_weekly_leaderboard";
    public static String All_Time_Leaderboard_Request_Url = "http://localhost:8080/get_all_time_leaderboard";
    public static String Sign_In_Request_Url = "http://localhost:8080/sign_in";


    //Properties of Game Over Text in Game Over window
    public static int Layout_X = 350;
    public static int Layout_Y = 375;
    public static int Min_Width = 1200;
    public static String Font = "Arial";
    public static int Font_Size = 40;
    public static String Message = "YOU LOST, GAME OVER!";
    public static Color Font_Color = Color.CRIMSON;

    //Player Properties
    public static int Max_Health = 100;


    //Score Reduction From Enemy
    public static int Score_Reduction_Plekumat = 2;
    public static int Score_Reduction_Boss_Plekumat= 5;


    //Random Occurences
    public static double Pismaniye_Frequency = 0.002;
    public static double Alien_Add_Frequency = 0.003;


    //SpawnBorderHeight value
    public static int SpawnBorder_Height = 250;

    //Pismaniye Values
    public static double Pismaniye_Radius = 6;
    public static Color Pismaniye_Colour = Color.LIGHTGREEN;
    public static double Pismaniye_DeltaX = 0;
    public static double Pismaniye_DeltaY = -1;
    public static int Pismaniye_Damage = 0;


    //Plekumat Values
    public static int Plekumat_Bullet_Radius = 3;
    public static int Plekumat_Bullet_Damage = 5;
    public static double Plekumat_Bullet_DeltaX = 0;
    public static double Plekumat_Bullet_DeltaY = -1;
    public static Color Plekumat_Bullet_Colour = Color.RED;

    public static int Plekumat_Health = 20;
    public static int Plekumat_Value = 10;
    public static double Plekumat_Radius = 17;
    public static double Plekumat_DeltaX = 3;
    public static Color Plekumat_Colour = Color.RED;
    public static boolean Plekumat_Direction = false;
    public static double Plekumat_Gudumlu_Fire_Frequency = 0.5;


    //Player Values
    public static double Player_Bullet_Radius = 5;
    public static int Player_Weaponlvl0_Damage = 10;
    public static int Player_Weaponlvl1_Damage = 15;
    public static int Player_Weaponlvl2_Damage = 35;
    public static int Player_Weaponlvl3_Damage = 55;

    public static double Player_Weaponlvl1_DeltaX = 0;
    public static double Player_Weaponlvl1_DeltaY = 10;
    public static double Player_Weaponlvl1_Bullet_Seperation = 0.5;

    public static double Player_Weaponlvl2_DeltaX = 0;
    public static double Player_Weaponlvl2_DeltaY = 10;
    public static double Player_Weaponlvl2_Bullet_Seperation = 0.5;

    public static double Player_Weaponlvl3_DeltaX = 0.5;
    public static double Player_Weaponlvl3_DeltaY = 10;
    public static double Player_Weaponlvl3_Bullet_Seperation = 0.85;


    public static double Player_Bullet_DeltaX = 0;
    public static double Player_Bullet_DeltaY = 10;
    public static Color Player_Bullet_Colour = Color.BLUE;

    public static int Player_MaxHealth = 100;
    public static double Player_Radius = 17;
    public static double Player_Spawn_CenterX = 600;
    public static double Player_Spawn_CenterY = 750 - Player_Radius;
    public static Color Player_Colour = Color.BLUE;

    public static double max_drag = 3;
    public static double drag_change_rate = 0.03;
    public static double drag_value_hit_by_enemy = 0.3;

    //Default particle radius value
    public static double default_particule_radius = 5;

    //Boss Plekumat Values
    public static double Boss_Plekumat_Bullet_Radius = 6;
    public static int Boss_Plekumat_Bullet_Damage = 20;
    public static double Boss_Plekumat_Bullet_DeltaX = 0;
    public static double Boss_Plekumat_Bullet_DeltaY = 1;
    public static Color Boss_Plekumat_Bullet_Color = Color.CRIMSON;


    public static double Boss_Plekumat_Radius = 30;
    public static double Boss_Plekumat_DeltaX = 1;
    public static boolean Boss_Plekumat_Direction = false;
    public static int Boss_Plekumat_Max_Health = 100;
    public static int Boss_Plekumat_Value = 40;
    public static Color Boss_Plekumat_Colour = Color.CRIMSON;
    public static double Boss_Plekumat_Gudumlu_Fire_Chance = 0.9;
    public static double Boss_Plekumat_Bullet_Seperation_Angle = 20;


    //BirCisim Values
    public static double BirCisim_Bullet1_Radius = 10;
    public static int BirCisim_Bullet1_Damage = 50;
    public static double BirCisim_Bullet2_Radius = 3;
    public static int BirCisim_Bullet2_Damage = 10;
    public static double BirCisim_Bullet1_DeltaX = 0;
    public static double BirCisim_Bullet1_DeltaY = 1;
    public static double BirCisim_Bullet2_DeltaX = 0;
    public static double BirCisim_Bullet2_DeltaY = 2.5;

    public static double BirCisim_Bullet1_Seperation_Angle = 40;
    public static double BirCisim_Bullet2_Seperation_Angle = 20;


    public static Color BirCisim_Bullet1_Colour = Color.CRIMSON;
    public static Color BirCisim_Bullet2_Colour = Color.CRIMSON;


    public static double BirCisim_Radius = 50;
    public static double BirCisim_DeltaX = 0;
    public static boolean BirCisim_Direction = false;
    public static int BirCisim_Max_Health = 10000;
    public static int BirCisim_Value = 15000;
    public static Color BirCisim_Colour = Color.CRIMSON;



}

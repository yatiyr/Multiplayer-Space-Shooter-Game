package GUI.Game;

import GUI.Game.Particule.*;
import GUI.GameValues;
import GUI.ScoreboardUser.User;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONObject;
import java.io.IOException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

public class Engine {

    private Game_Pane pane;
    private Scene scene;
    private User user;
    private Stage primestage = new Stage();

    private Timeline game_timeline;
    private Timeline plekumat_fire;
    private Timeline boss_plekumat_fire;

    private IntegerProperty score = new SimpleIntegerProperty(0);

    private MediaPlayer game_music = new MediaPlayer(
            new Media(ClassLoader.getSystemResource(GameValues.Game_Sound_Path).toExternalForm()));

    /**private MediaPlayer lose_sound = new MediaPlayer(
            new Media(ClassLoader.getSystemResource(GameValues.Game_LoseSound_Path).toExternalForm())); **/

    private AudioClip lose_sound = new AudioClip(ClassLoader.getSystemResource(GameValues.Game_LoseSound_Path).toExternalForm());

    private Player player;
    private ArrayList<Particule> particules = new ArrayList<>();
    private ArrayList<Alien> aliens = new ArrayList<>();
    private ArrayList<Bullet> bullets = new ArrayList<>();

    private ArrayList<Alien> initially_added_aliens_per_level = new ArrayList<>();

    //Particule list will be added soon
    private ArrayList<Particule> particules_added = new ArrayList<>();

    //Particule list that will be removed soon
    private ArrayList<Particule> particules_removed = new ArrayList<>();

    public Engine() {

        primestage.setTitle("ALIEN SHOOTER");

        Initialize_Level(1);

        primestage.setResizable(false);

        primestage.show();

    }

    public void SetUser(User user) {
        this.user = user;
    }

    //This is for turning user object into a json
    private String userToJSONstring(User user) {

        Format formatter = new SimpleDateFormat("yyyy-MM-dd");
        String s = formatter.format(Calendar.getInstance().getTime());

        JSONObject o2 = new JSONObject();
        o2.accumulate("user_id",user.getId());
        o2.accumulate("score",score.get());
        o2.accumulate("username",user.getName());
        o2.accumulate("date",s);


        return o2.toString();
    }
    //This is for the transitions between game levels
    //to make game running smoother
    private void FadeSceneTrans(int level) {


        FadeTransition ft = new FadeTransition();
        ft.setDuration(Duration.millis(GameValues.Fade_Scene_Trans_Time));
        ft.setNode(pane);
        ft.setFromValue(1);
        ft.setToValue(0);
        ft.setOnFinished((ActionEvent event) ->
                Initialize_Level(level)
        );
        ft.play();


    }


    //This also works for making game smoother
    private void Clear_and_Fade(int level) {
        game_timeline.stop();
        boss_plekumat_fire.stop();
        plekumat_fire.stop();


        particules.clear();
        aliens.clear();
        bullets.clear();
        particules_removed.clear();
        particules_added.clear();
        initially_added_aliens_per_level.clear();
        FadeSceneTrans(level);

    }

    //This starts the specified level
    private int Initialize_Level(int level) {

        this.pane = new Game_Pane(level,score.get());
        this.scene = new Scene(pane,GameValues.Game_Pane_Width,GameValues.Game_Pane_Height);
        scene_setup(pane);
        String title = "ALIEN SHOOTER LEVEL " + level;
        this.primestage.setTitle(title);
        key_binds_setup();

        player = new Player();

        add(player);

        if(level == 1) {

            for(int i=0;i<GameValues.Level1_Plekumat_No;i++) {
                initially_added_aliens_per_level.add(new Plekumat());
            }
            for(int i=0;i<GameValues.Level1_BossPlekumat_No;i++) {
                initially_added_aliens_per_level.add(new Boss_Plekumat());
            }

        }
        else if(level == 2) {

            for(int i=0;i<GameValues.Level2_Plekumat_No;i++) {
                initially_added_aliens_per_level.add(new Plekumat());
            }
            for(int i=0;i<GameValues.Level2_BossPlekumat_No;i++) {
                initially_added_aliens_per_level.add(new Boss_Plekumat());
            }
        }

        else if(level == 3) {
            for(int i=0;i<GameValues.Level3_Plekumat_No;i++) {
                initially_added_aliens_per_level.add(new Plekumat());
            }
            for(int i=0;i<GameValues.Level3_BossPlekumat_No;i++) {
                initially_added_aliens_per_level.add(new Boss_Plekumat());
            }
        }
        else if(level == 4) {

            /**for(int i=0;i<GameValues.Level4_Plekumat_No;i++) {
                initially_added_aliens_per_level.add(new Plekumat());
            }
            for(int i=0;i<GameValues.Level4_BossPlekumat_No;i++) {
                initially_added_aliens_per_level.add(new Boss_Plekumat());
            } **/

            return 1;
        }

        game_music.setCycleCount(MediaPlayer.INDEFINITE);
        game_music.play();

        timeline_setup(level);

        return 0;
    }



    private void scene_setup(Game_Pane pane) {
        this.pane = pane;
        pane.bindScore(score);
        pane.SetEngine(this);
        scene.setRoot(pane);
        primestage.setScene(scene);
    }

    private void key_binds_setup () {

        scene.setOnKeyPressed( event -> {

            if(event.getCode() == KeyCode.LEFT) {

                player.move_set(0);

            }
            if(event.getCode() == KeyCode.RIGHT) {

                player.move_set(1);

            }
            if(event.getCode() == KeyCode.UP) {

                player.move_set(2);

            }
            if(event.getCode() == KeyCode.DOWN) {

                player.move_set(3);

            }
            if(event.getCode() == KeyCode.SPACE) {

                if(player.get_one_tap_fire())
                {
                    player.fire_bullet();
                    player.set_one_tap_fire(false);
                }
            }

            if(event.getCode() == KeyCode.R) {

                //When R button is pressed, game is
                //restarted with a clean state
                score.set(0);
                user.setScore(0);
                Clear_and_Fade(1);
            }

        });

        scene.setOnKeyReleased(event -> {

            if(event.getCode() == KeyCode.LEFT) {

                player.stop_set(0);

            }
            if(event.getCode() == KeyCode.RIGHT) {

                player.stop_set(1);

            }
            if(event.getCode() == KeyCode.UP) {

                player.stop_set(2);

            }
            if(event.getCode() == KeyCode.DOWN) {

                player.stop_set(3);

            }
            if(event.getCode() == KeyCode.SPACE) {

                player.set_one_tap_fire(true);
            }
        });
    }


    //We add particules to our arraylist
    //and also to the game pane
    private void add(Particule particule) {
        particules.add(particule);
        if(particule instanceof Player) {
            player = (Player) particule;
        }
        if(particule instanceof Alien) {
            aliens.add((Alien) particule);
        }
        if( particule instanceof Bullet )
            bullets.add((Bullet) particule);

        pane.getChildren().add(particule);
    }


    //We remove particules from arraylists
    //and we also remove them from the game pane
    private void remove(Particule particule) {
       particules.remove(particule);
       if( particule instanceof Alien) {
           aliens.remove(particule);
       }
       else if(particule instanceof Bullet) {
           bullets.remove(particule);
       }
       pane.getChildren().remove(particule);
    }

    //This is for putting particules which are going to be added after iterations in the timeline
    public void add_queue(Particule particule) {
        particules_added.add(particule);
    }

    //This is for putting particules which are going to be removed after iterations in the timeline
    private void remove_queue(Particule particule) {
        particules_removed.add(particule);
    }

    public Scene getScene() {
        return scene;
    }

    public Player getPlayer() {
        return player;
    }

    //We initiate timelines here, this is the core of the game
    private void timeline_setup(int level) {

        game_timeline = new Timeline(new KeyFrame(Duration.millis(GameValues.Game_Timeline_Duration), event -> {

            try {
                for (Particule particule : particules) {
                    particule.todo();

                    int init_size = initially_added_aliens_per_level.size();
                    int alien_no = aliens.size();
                    //Level Is Over
                    if (init_size == 0 && alien_no == 0) {
                        game_music.stop();

                        //lose_sound.setCycleCount(1);
                        lose_sound.play();


                        HttpClient httpClient = HttpClientBuilder.create().build();
                        HttpPost request = new HttpPost(GameValues.Update_Weekly_Table_Request_Url);
                        HttpPost request2 = new HttpPost(GameValues.Update_AllTime_Table_Request_Url);

                        //This turns user object into a json string to update the scoreboard
                        String obj = userToJSONstring(this.user);
                        StringEntity entity = new StringEntity(obj, ContentType.APPLICATION_JSON);
                        request.setEntity(entity);
                        request2.setEntity(entity);

                        System.out.println(obj);
                        try {
                            httpClient.execute(request2);
                            httpClient.execute(request);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }


                        Clear_and_Fade(level + 1);


                    }


                    if (particule instanceof Alien) {

                        if (((Alien) particule).getCurhealth() <= 0) {

                            score.set(score.get() + ((Alien) particule).getValue());
                            remove_queue(particule);
                        }

                    }
                    if (particule instanceof Player) {

                        if (((Live_Being) particule).getCurhealth() <= 0) {

                            game_music.stop();

                            //lose_sound.setCycleCount(1);
                            lose_sound.play();

                            game_timeline.stop();
                            boss_plekumat_fire.stop();
                            plekumat_fire.stop();

                            Label game_over = new Label();

                            game_over.setLayoutX(GameValues.Layout_X);
                            game_over.setLayoutY(GameValues.Layout_Y);
                            game_over.setMinWidth(GameValues.Min_Width);
                            game_over.setTextFill(GameValues.Font_Color);
                            game_over.setText(GameValues.Message);
                            game_over.setFont(new Font(GameValues.Font, GameValues.Font_Size));
                            pane.getChildren().add(game_over);
                            //YOU DIED MY FRIEND.
                            remove_queue(particule);
                        }
                    }
                }

            }
            catch (Exception e)
            {
                System.out.println("Level is Over");
            }

            //Collision detection
            for(Bullet bullet : bullets) {

                //We move our yummy pismaniyes and see whether
                //we have a collision with player or not
                if(bullet instanceof Izmit_Pismaniye) {
                    bullet.move();

                    //Upgrade the weapon of player
                    if(bullet.intersects(player.getBoundsInLocal())) {
                        player.setCurhealth(GameValues.Max_Health);
                        player.upgrade_weapon();
                        remove_queue(bullet);
                    }
                }

                //Plekumat bullets hitting player
                if(bullet instanceof Plekumat_Bullet) {

                    if(bullet.intersects(player.getBoundsInLocal())) {
                        player.setCurhealth(player.getCurhealth() - bullet.getDamage());
                        if(score.get() <= 0) {
                            score.set(0);
                        }
                        else {
                            score.set(score.get() - GameValues.Score_Reduction_Plekumat);
                        }

                        remove_queue(bullet);
                    }

                }
                //If Boss_Plekumat hits player, player
                //will take damage and player's weapon will
                //be downgraded
                if(bullet instanceof Boss_Plekumat_Bullet) {
                    if(bullet.intersects(player.getBoundsInLocal())) {
                        player.setCurhealth(player.getCurhealth() - bullet.getDamage());
                        player.downgrade_weapon();
                        if(score.get() <= 0) {
                            score.set(0);
                        }
                        else {
                            score.set(score.get() - GameValues.Score_Reduction_Boss_Plekumat);
                        }
                        remove_queue(bullet);
                    }
                }
                //If Player hits aliens
                if(bullet instanceof Player_Bullet) {

                    for(Alien alien : aliens) {

                        if(bullet.intersects(alien.getBoundsInLocal())) {

                            alien.setCurhealth(alien.getCurhealth() - bullet.getDamage());
                            remove_queue(bullet);
                        }
                    }
                }

                if(bullet.getCenterY() + bullet.getRadius() > GameValues.Game_Pane_Height)
                    remove_queue(bullet);

            }

            //We add pismaniyes to the game enviroment
            if(Math.random() < GameValues.Pismaniye_Frequency) {

                Random randx = new Random();
                double randomX = GameValues.Pismaniye_Radius + (GameValues.Game_Pane_Width - GameValues.Pismaniye_Radius) * randx.nextDouble();

                Random randy = new Random();
                double randomY = GameValues.Pismaniye_Radius + (GameValues.SpawnBorder_Height - GameValues.Pismaniye_Radius) * randy.nextDouble();

                Izmit_Pismaniye pismaniye  = new Izmit_Pismaniye(randomX,randomY);
                add_queue(pismaniye);

            }

            if(Math.random() < GameValues.Alien_Add_Frequency) {

                int init_size = initially_added_aliens_per_level.size();
                if(init_size > 0) {
                    Random new_random = new Random();
                    int random_ind = new_random.nextInt(init_size);
                    Alien enemy = initially_added_aliens_per_level.get(random_ind);
                    add_queue(enemy);
                    initially_added_aliens_per_level.remove(random_ind);
                }


            }

            //We have queued the ones to delete
            //now really delete them

            for(Particule particule : particules_removed) {
                remove(particule);
            }

            //We add the ones we determined
            for(Particule particule : particules_added) {
                add(particule);
            }

            //now clear queues
            particules_added.clear();
            particules_removed.clear();
        }));
        game_timeline.setCycleCount(Timeline.INDEFINITE);
        game_timeline.play();

        plekumat_fire = new Timeline(new KeyFrame(Duration.millis(GameValues.Plekumat_Fire_Duration), event -> {

            for(Particule particule : particules) {
                if(particule instanceof Plekumat) {
                    ((Plekumat)particule).fire();
                }
            }
        }));
        boss_plekumat_fire = new Timeline(new KeyFrame(Duration.millis(GameValues.Boss_Plekumat_Fire_Duration), event -> {

            for(Particule particule : particules) {
                if(particule instanceof Boss_Plekumat) {
                    ((Boss_Plekumat)particule).fire();
                }

            }

        }));

        plekumat_fire.setCycleCount(Timeline.INDEFINITE);
        plekumat_fire.play();
        boss_plekumat_fire.setCycleCount(Timeline.INDEFINITE);
        boss_plekumat_fire.play();

    }

    private void multiplayer_timeline_setup(int level) {



    }


}


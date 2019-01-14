package GUI.Game;

import GUI.Game.Multiplayer.MultiplayerConstants;
import GUI.Game.Multiplayer.Peer2PeerMessage;
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

import java.io.*;
import java.net.Socket;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.*;

public class Engine {

    private Game_Pane pane;
    private Scene scene;
    private User user;
    private Stage primestage = new Stage();

    //Timelines, they are the core of the game
    private Timeline game_timeline;
    private Timeline plekumat_fire;
    private Timeline boss_plekumat_fire;
    private Timeline multiplayer_timeline;

    private IntegerProperty score = new SimpleIntegerProperty(0);

    //This is the music played when the game runs
    private MediaPlayer game_music = new MediaPlayer(
            new Media(ClassLoader.getSystemResource(GameValues.Game_Sound_Path).toExternalForm()));

    //These are sounds to make game funnier
    private AudioClip lose_sound = new AudioClip(ClassLoader.getSystemResource(GameValues.Game_LoseSound_Path).toExternalForm());
    private AudioClip tihulu_sound = new AudioClip(ClassLoader.getSystemResource(GameValues.Tihulu_Sound_Path).toExternalForm());
    private AudioClip kuna_sound = new AudioClip(ClassLoader.getSystemResource(GameValues.Kuna_Sound_Path).toExternalForm());

    private Player player;
    private Multiplayer_Opponent multiplayer_opponent;
    private BirCisim birCisim;
    private ArrayList<Particule> particules = new ArrayList<>();
    private ArrayList<Alien> aliens = new ArrayList<>();
    private ArrayList<Bullet> bullets = new ArrayList<>();

    private ArrayList<Alien> initially_added_aliens_per_level = new ArrayList<>();

    //Particule list will be added soon
    private ArrayList<Particule> particules_added = new ArrayList<>();

    //Particule list that will be removed soon
    private ArrayList<Particule> particules_removed = new ArrayList<>();

    //These are queues for sending and receiving messages in multiplayer part
    //of the game
    private Queue<Peer2PeerMessage> messages_to_send = new LinkedList<>();
    private Queue<Peer2PeerMessage> messages_received = new LinkedList<>();
    private ObjectInputStream fromServer;
    private ObjectOutputStream toServer;
    private int playerno = 0;
    private Socket player_socket;
    private boolean isGameNotFinished = true;
    private boolean is_player_fired = false;
    private int counter = 0;

    private Label waiting_for_players;

    private void initialize_sender_receiver_threads() {

        try {

            //Add label for waiting the other player
            waiting_for_players = new Label();
            waiting_for_players.setLayoutX(GameValues.Layout_X);
            waiting_for_players.setLayoutY(GameValues.Layout_Y);
            waiting_for_players.setMinWidth(GameValues.Min_Width);
            waiting_for_players.setTextFill(GameValues.Font_Color2);
            waiting_for_players.setText(GameValues.waiting_for_player);
            waiting_for_players.setFont(new Font(GameValues.Font, GameValues.Font_Size));
            pane.getChildren().add(waiting_for_players);

            //Initialize socket and data connections between client and the server
            player_socket = new Socket(MultiplayerConstants.host,MultiplayerConstants.server_port);
            fromServer = new ObjectInputStream(player_socket.getInputStream());
            toServer = new ObjectOutputStream(player_socket.getOutputStream());

            //wait for server to tell client
            //Whether the player is first or second
            playerno = fromServer.readInt();

            //for initialization command from server
            fromServer.readInt();

        } catch (IOException e) {

            e.printStackTrace();
        }

        //Thread for receiving messages from other player
        //this puts messages to an array list to be consumed
        new Thread(() -> {

            try {
                while(isGameNotFinished) {
                    Peer2PeerMessage message = (Peer2PeerMessage) fromServer.readObject();
                    messages_received.add(message);
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

    }

    //Engine is called when play button
    //is pressed.It sets title, and
    //initializes level 1
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
        //If level is 4, then it means multiplayer is going to start
        else if(level == 4) {

            //We first initizlize our threads for communication
            //and wait response from the server
            initialize_sender_receiver_threads();

            //Remove waiting for players label since other player has arrived
            pane.getChildren().remove(waiting_for_players);

            //we remove the player initilized before
            remove(player);


            //Spawn places of player1 and player2 are determined by the server
            if(playerno == 1) {
                player = new Player(GameValues.Player1_Spawn_CenterX,GameValues.Player1_Spawn_CenterY);
                add(player);

                multiplayer_opponent = new Multiplayer_Opponent(GameValues.Player2_Spawn_CenterX,GameValues.Player2_Spawn_CenterY);
                add(multiplayer_opponent);

            }
            else {
                player = new Player(GameValues.Player2_Spawn_CenterX,GameValues.Player2_Spawn_CenterY);
                add(player);

                multiplayer_opponent = new Multiplayer_Opponent(GameValues.Player1_Spawn_CenterX,GameValues.Player1_Spawn_CenterY);
                add(multiplayer_opponent);

            }

            //Game music starts
            game_music.setCycleCount(MediaPlayer.INDEFINITE);
            game_music.play();

            // - Komutan Logar, Bir Cisim yaklasiyor efendim.
            // - Kimsin sen? Cik disari, cik!
            tihulu_sound.play();

            //Boss(birCisim) added
            birCisim = new BirCisim();
            add(birCisim);

            //Timeline for multiplayer part of the game
            //starts
            multiplayer_timeline_setup();

            return 1;
        }

        //Game music and timelines are started
        //for the single player part of the game
        game_music.setCycleCount(MediaPlayer.INDEFINITE);
        game_music.play();
        game_music.setVolume(30);
        timeline_setup(level);

        return 0;
    }

    //Setting up the scene
    private void scene_setup(Game_Pane pane) {
        this.pane = pane;
        pane.bindScore(score);
        pane.SetEngine(this);
        scene.setRoot(pane);
        primestage.setScene(scene);
    }

    //Here we setup the keys for controlling
    //the player,firing and restarting
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

                    //is_player_fired is needed
                    //for multiplayer part
                    //if is_player_fired is true
                    //player sends message to the
                    //other player implying that it has fired
                    is_player_fired = true;

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

    //we need to get multiplayer opponent for boss to understand its location and fire
    //accordingly
    public Multiplayer_Opponent getMultiplayer_opponent() {
        return multiplayer_opponent;
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


    //this is the timeline for multiplayer part of the game
    //Working principle is very similar with normal game timeline
    //However, here player checks messages arrived from the other player
    //and with those messages, moves of other player or win/lose conditions are determined
    private void multiplayer_timeline_setup() {

        multiplayer_timeline = new Timeline(new KeyFrame(Duration.millis(GameValues.Game_Timeline_Duration), event -> {

            try {
                //We first form the message to be sent to the other player via our server
                Peer2PeerMessage message = new Peer2PeerMessage(false,false,player.getCenterX(),player.getCenterY(),is_player_fired,score.getValue(),false,false);

                //We iterate the particules in the game.
                //Player,Opponent,BirCisim and their bullets are the particules
                for (Particule particule : particules) {

                    //Move particles
                    particule.todo();

                    if (particule instanceof Player) {

                        //If our player dies
                        if (((Live_Being) particule).getCurhealth() <= 0) {

                            game_music.stop();

                            //lose_sound.setCycleCount(1);
                            kuna_sound.play();

                            //Stop the timeline
                            multiplayer_timeline.stop();

                            //Save the score of our player
                            HttpClient httpClient = HttpClientBuilder.create().build();
                            HttpPost request = new HttpPost(GameValues.Update_Weekly_Table_Request_Url);
                            HttpPost request2 = new HttpPost(GameValues.Update_AllTime_Table_Request_Url);

                            String obj = userToJSONstring(this.user);
                            StringEntity entity = new StringEntity(obj, ContentType.APPLICATION_JSON);
                            request.setEntity(entity);
                            request2.setEntity(entity);

                            try {
                                httpClient.execute(request2);
                                httpClient.execute(request);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            //Gameover label is being shown
                            Label game_over = new Label();
                            game_over.setLayoutX(GameValues.Layout_X);
                            game_over.setLayoutY(GameValues.Layout_Y);
                            game_over.setMinWidth(GameValues.Min_Width);
                            game_over.setTextFill(GameValues.Font_Color);
                            game_over.setText(GameValues.Message);
                            game_over.setFont(new Font(GameValues.Font, GameValues.Font_Size));
                            pane.getChildren().add(game_over);
                            //YOU DIED MY FRIEND.

                            //Here we set the I_died part of the message true
                            //Thus,the other player will understand that this player
                            //has died.So opponent understands that he/she is victorious
                            message.setI_died(true);
                            remove_queue(particule);
                        }
                    }

                }

                //This counter is for firing the BirCisim(Boss Alien)
                //in the multiplayer
                counter = counter + 1;

                //Fire1 and Fire2 Counters are determined in gamevalues
                if(counter == GameValues.BirCisim_Fire2_counter) {
                    birCisim.fire2();
                }
                if(counter == GameValues.BirCisim_Fire1_counter) {
                    birCisim.fire1();
                    counter = 0;
                }

                //this field checks the messages arrived
                //if other player died, then game finishes,this player wins
                //if other player wins, then game finished,this player loses
                //if other player reports that it has fired,the illusion of
                //that player fires
                if(!messages_received.isEmpty()) {
                Peer2PeerMessage message_received = messages_received.poll();
                multiplayer_opponent.setCenterX(message_received.getPosition()[0]);
                multiplayer_opponent.setCenterY(message_received.getPosition()[1]);

                if(message_received.isI_fired()) {
                    multiplayer_opponent.fire_bullet();
                }

                if(message_received.isI_died()) {
                    remove(multiplayer_opponent);

                    game_music.stop();
                    kuna_sound.stop();

                    HttpClient httpClient = HttpClientBuilder.create().build();
                    HttpPost request = new HttpPost(GameValues.Update_Weekly_Table_Request_Url);
                    HttpPost request2 = new HttpPost(GameValues.Update_AllTime_Table_Request_Url);

                    //This turns user object into a json string to update the scoreboard
                    score.set(score.get() + GameValues.BirCisim_Value);
                    String obj = userToJSONstring(this.user);
                    StringEntity entity = new StringEntity(obj, ContentType.APPLICATION_JSON);
                    request.setEntity(entity);
                    request2.setEntity(entity);

                    try {
                        httpClient.execute(request2);
                        httpClient.execute(request);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    multiplayer_timeline.stop();

                    Label you_won = new Label();

                    you_won.setLayoutX(GameValues.Layout_X);
                    you_won.setLayoutY(GameValues.Layout_Y);
                    you_won.setMinWidth(GameValues.Min_Width);
                    you_won.setTextFill(GameValues.Font_Color);
                    you_won.setText(GameValues.win_message);
                    you_won.setFont(new Font(GameValues.Font, GameValues.Font_Size));
                    pane.getChildren().add(you_won);

                }
                if(message_received.isI_won_dude()) {

                    game_music.stop();
                    kuna_sound.stop();

                    HttpClient httpClient = HttpClientBuilder.create().build();
                    HttpPost request = new HttpPost(GameValues.Update_Weekly_Table_Request_Url);
                    HttpPost request2 = new HttpPost(GameValues.Update_AllTime_Table_Request_Url);

                    //This turns user object into a json string to update the scoreboard
                    String obj = userToJSONstring(this.user);
                    StringEntity entity = new StringEntity(obj, ContentType.APPLICATION_JSON);
                    request.setEntity(entity);
                    request2.setEntity(entity);

                    try {
                        httpClient.execute(request2);
                        httpClient.execute(request);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    multiplayer_timeline.stop();

                    Label lose = new Label();

                    lose.setLayoutX(GameValues.Layout_X);
                    lose.setLayoutY(GameValues.Layout_Y);
                    lose.setMinWidth(GameValues.Min_Width);
                    lose.setTextFill(GameValues.Font_Color);
                    lose.setText(GameValues.Message);
                    lose.setFont(new Font(GameValues.Font, GameValues.Font_Size));
                    pane.getChildren().add(lose);

                    }
                }

                //Collision detection
                for(Bullet bullet : bullets) {

                    //BirCisim bullets hitting player
                    if(bullet instanceof BirCisim_Bullet1 || bullet instanceof BirCisim_Bullet2) {

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

                        if(bullet.intersects(multiplayer_opponent.getBoundsInLocal())) {
                            remove_queue(bullet);
                        }

                    }

                    //If Player hits birCisim
                    if(bullet instanceof Player_Bullet) {

                        for(Alien alien : aliens) {

                            if(bullet.intersects(alien.getBoundsInLocal())) {

                                alien.setCurhealth(alien.getCurhealth() - bullet.getDamage());
                                score.set(score.get() + GameValues.Player_Weaponlvl1_Damage);
                                remove_queue(bullet);

                                if (alien.getCurhealth() <= 0) {

                                    score.set(score.get() + alien.getValue());
                                    remove_queue(alien);

                                    game_music.stop();
                                    kuna_sound.stop();

                                    multiplayer_timeline.stop();

                                    Label you_won = new Label();

                                    you_won.setLayoutX(GameValues.Layout_X);
                                    you_won.setLayoutY(GameValues.Layout_Y);
                                    you_won.setMinWidth(GameValues.Min_Width);
                                    you_won.setTextFill(GameValues.Font_Color);
                                    you_won.setText(GameValues.win_message);
                                    you_won.setFont(new Font(GameValues.Font, GameValues.Font_Size));
                                    pane.getChildren().add(you_won);

                                    message.setI_won_dude(true);

                                    toServer.writeObject(message);

                                }
                            }
                        }
                    }

                    //If bullet of multiplayer opponent hits to the birCisim
                    if(bullet instanceof Multiplayer_Opponent_Bullet) {

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

                //If is_player_fired for controlling the firing of opponent is true
                //we change it to false at the end of the timeline
                is_player_fired = false;

                //we write the modified message to the other side
                toServer.writeObject(message);
                messages_to_send.add(message);

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

            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }));
        multiplayer_timeline.setCycleCount(Timeline.INDEFINITE);
        multiplayer_timeline.play();
    }
}


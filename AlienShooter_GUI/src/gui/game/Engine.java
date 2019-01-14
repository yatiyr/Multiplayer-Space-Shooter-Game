package gui.game;

import gui.game.multiplayer.MultiplayerConstants;
import gui.game.multiplayer.Peer2PeerMessage;
import gui.game.particule.*;
import gui.GameValues;
import gui.scoreboarduser.User;
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

    private GamePane pane;
    private Scene scene;
    private User user;
    private Stage primestage = new Stage();

    //Timelines, they are the core of the game
    private Timeline gameTimeline;
    private Timeline plekumatFire;
    private Timeline bossPlekumatFire;
    private Timeline multiplayerTimeline;

    private IntegerProperty score = new SimpleIntegerProperty(0);

    //This is the music played when the game runs
    private MediaPlayer gameMusic = new MediaPlayer(
            new Media(ClassLoader.getSystemResource(GameValues.GAME_SOUND_PATH).toExternalForm()));

    //These are sounds to make game funnier
    private AudioClip loseSound = new AudioClip(ClassLoader.getSystemResource(GameValues.GAME_LOSE_SOUND_PATH).toExternalForm());
    private AudioClip tihuluSound = new AudioClip(ClassLoader.getSystemResource(GameValues.TIHULU_SOUND_PATH).toExternalForm());
    private AudioClip kunaSound = new AudioClip(ClassLoader.getSystemResource(GameValues.KUNA_SOUND_PATH).toExternalForm());

    private Player player;
    private MultiplayerOpponent multiplayerOpponent;
    private BirCisim birCisim;
    private ArrayList<Particule> particules = new ArrayList<>();
    private ArrayList<Alien> aliens = new ArrayList<>();
    private ArrayList<Bullet> bullets = new ArrayList<>();

    private ArrayList<Alien> initiallyAddedAliensPerLevel = new ArrayList<>();

    //particule list will be added soon
    private ArrayList<Particule> particulesAdded = new ArrayList<>();

    //particule list that will be removed soon
    private ArrayList<Particule> particulesRemoved = new ArrayList<>();

    //These are queues for sending and receiving messages in multiplayer part
    //of the game
    private Queue<Peer2PeerMessage> messagesToSend = new LinkedList<>();
    private Queue<Peer2PeerMessage> messagesReceived = new LinkedList<>();
    private ObjectInputStream fromServer;
    private ObjectOutputStream toServer;
    private int playerNo = 0;
    private Socket playerSocket;
    private boolean isGameNotFinished = true;
    private boolean isPlayerFired = false;
    private int counter = 0;

    private Label waitingForPlayers;

    private void initializeSenderReceiverThreads() {

        try {

            //Add label for waiting the other player
            waitingForPlayers = new Label();
            waitingForPlayers.setLayoutX(GameValues.LAYOUT_X);
            waitingForPlayers.setLayoutY(GameValues.LAYOUT_Y);
            waitingForPlayers.setMinWidth(GameValues.MIN_WIDTH);
            waitingForPlayers.setTextFill(GameValues.FONT_COLOR2);
            waitingForPlayers.setText(GameValues.WAITING_FOR_PLAYER);
            waitingForPlayers.setFont(new Font(GameValues.FONT, GameValues.FONT_SIZE));
            pane.getChildren().add(waitingForPlayers);

            //Initialize socket and data connections between client and the server
            playerSocket = new Socket(MultiplayerConstants.HOST,MultiplayerConstants.SERVER_PORT);
            fromServer = new ObjectInputStream(playerSocket.getInputStream());
            toServer = new ObjectOutputStream(playerSocket.getOutputStream());

            //wait for server to tell client
            //Whether the player is first or second
            playerNo = fromServer.readInt();

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
                    messagesReceived.add(message);
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
        initializeLevel(1);
        primestage.setResizable(false);
        primestage.show();

    }

    public void setUser(User user) {
        this.user = user;
    }

    //This is for turning user object into a json
    private String userToJsonString(User user) {

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
    private void fadeSceneTrans(int level) {


        FadeTransition ft = new FadeTransition();
        ft.setDuration(Duration.millis(GameValues.FADE_SCENE_TRANS_TIME));
        ft.setNode(pane);
        ft.setFromValue(1);
        ft.setToValue(0);
        ft.setOnFinished((ActionEvent event) ->
                initializeLevel(level)
        );
        ft.play();


    }

    //This also works for making game smoother
    private void clearAndFade(int level) {
        gameTimeline.stop();
        bossPlekumatFire.stop();
        plekumatFire.stop();


        particules.clear();
        aliens.clear();
        bullets.clear();
        particulesRemoved.clear();
        particulesAdded.clear();
        initiallyAddedAliensPerLevel.clear();
        fadeSceneTrans(level);

    }

    //This starts the specified level
    private int initializeLevel(int level) {

        this.pane = new GamePane(level,score.get());
        this.scene = new Scene(pane,GameValues.GAME_PANE_WIDTH,GameValues.GAME_PANE_HEIGHT);
        sceneSetup(pane);
        String title = "ALIEN SHOOTER LEVEL " + level;
        this.primestage.setTitle(title);
        keyBindsSetup();

        player = new Player();
        add(player);

        if(level == 1) {

            for(int i = 0; i<GameValues.LEVEL1_PLEKUMAT_NO; i++) {
                initiallyAddedAliensPerLevel.add(new Plekumat());
            }
            for(int i = 0; i<GameValues.LEVEL1_BOSS_PLEKUMAT_NO; i++) {
                initiallyAddedAliensPerLevel.add(new BossPlekumat());
            }

        }
        else if(level == 2) {

            for(int i = 0; i<GameValues.LEVEL2_PLEKUMAT_NO; i++) {
                initiallyAddedAliensPerLevel.add(new Plekumat());
            }
            for(int i = 0; i<GameValues.LEVEL2_BOSS_PLEKUMAT_NO; i++) {
                initiallyAddedAliensPerLevel.add(new BossPlekumat());
            }
        }
        else if(level == 3) {
            for(int i = 0; i<GameValues.LEVEL3_PLEKUMAT_NO; i++) {
                initiallyAddedAliensPerLevel.add(new Plekumat());
            }
            for(int i = 0; i<GameValues.LEVEL3_BOSS_PLEKUMAT_NO; i++) {
                initiallyAddedAliensPerLevel.add(new BossPlekumat());
            }
        }
        //If level is 4, then it means multiplayer is going to start
        else if(level == 4) {

            //We first initizlize our threads for communication
            //and wait response from the server
            initializeSenderReceiverThreads();

            //Remove waiting for players label since other player has arrived
            pane.getChildren().remove(waitingForPlayers);

            //we remove the player initilized before
            remove(player);


            //Spawn places of player1 and player2 are determined by the server
            if(playerNo == 1) {
                player = new Player(GameValues.PLAYER1_SPAWN_CENTER_X,GameValues.PLAYER1_SPAWN_CENTER_Y);
                add(player);

                multiplayerOpponent = new MultiplayerOpponent(GameValues.PLAYER2_SPAWN_CENTER_X,GameValues.PLAYER2_SPAWN_CENTER_Y);
                add(multiplayerOpponent);

            }
            else {
                player = new Player(GameValues.PLAYER2_SPAWN_CENTER_X,GameValues.PLAYER2_SPAWN_CENTER_Y);
                add(player);

                multiplayerOpponent = new MultiplayerOpponent(GameValues.PLAYER1_SPAWN_CENTER_X,GameValues.PLAYER1_SPAWN_CENTER_Y);
                add(multiplayerOpponent);

            }

            //game music starts
            gameMusic.setCycleCount(MediaPlayer.INDEFINITE);
            gameMusic.play();

            // - Komutan Logar, Bir Cisim yaklasiyor efendim.
            // - Kimsin sen? Cik disari, cik!
            tihuluSound.play();

            //Boss(birCisim) added
            birCisim = new BirCisim();
            add(birCisim);

            //Timeline for multiplayer part of the game
            //starts
            multiplayerTimelineSetup();

            return 1;
        }

        //game music and timelines are started
        //for the single player part of the game
        gameMusic.setCycleCount(MediaPlayer.INDEFINITE);
        gameMusic.play();
        gameMusic.setVolume(30);
        timelineSetup(level);

        return 0;
    }

    //Setting up the scene
    private void sceneSetup(GamePane pane) {
        this.pane = pane;
        pane.bindScore(score);
        pane.setEngine(this);
        scene.setRoot(pane);
        primestage.setScene(scene);
    }

    //Here we setup the keys for controlling
    //the player,firing and restarting
    private void keyBindsSetup() {

        scene.setOnKeyPressed( event -> {

            if(event.getCode() == KeyCode.LEFT) {

                player.moveSet(0);

            }
            if(event.getCode() == KeyCode.RIGHT) {

                player.moveSet(1);

            }
            if(event.getCode() == KeyCode.UP) {

                player.moveSet(2);

            }
            if(event.getCode() == KeyCode.DOWN) {

                player.moveSet(3);

            }
            if(event.getCode() == KeyCode.SPACE) {

                if(player.getOneTapFire())
                {
                    player.fireBullet();

                    //isPlayerFired is needed
                    //for multiplayer part
                    //if isPlayerFired is true
                    //player sends message to the
                    //other player implying that it has fired
                    isPlayerFired = true;

                    player.setOneTapFire(false);
                }
            }

            if(event.getCode() == KeyCode.R) {

                //When R button is pressed, game is
                //restarted with a clean state
                score.set(0);
                user.setScore(0);
                clearAndFade(1);
            }

        });

        scene.setOnKeyReleased(event -> {

            if(event.getCode() == KeyCode.LEFT) {

                player.stopSet(0);

            }
            if(event.getCode() == KeyCode.RIGHT) {

                player.stopSet(1);

            }
            if(event.getCode() == KeyCode.UP) {

                player.stopSet(2);

            }
            if(event.getCode() == KeyCode.DOWN) {

                player.stopSet(3);

            }
            if(event.getCode() == KeyCode.SPACE) {

                player.setOneTapFire(true);
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
    public void addQueue(Particule particule) {
        particulesAdded.add(particule);
    }

    //This is for putting particules which are going to be removed after iterations in the timeline
    private void removeQueue(Particule particule) {
        particulesRemoved.add(particule);
    }

    public Scene getScene() {
        return scene;
    }

    public Player getPlayer() {
        return player;
    }

    //we need to get multiplayer opponent for boss to understand its location and fire
    //accordingly
    public MultiplayerOpponent getMultiplayerOpponent() {
        return multiplayerOpponent;
    }

    //We initiate timelines here, this is the core of the game
    private void timelineSetup(int level) {

        gameTimeline = new Timeline(new KeyFrame(Duration.millis(GameValues.GAME_TIMELINE_DURATION), event -> {

            try {
                for (Particule particule : particules) {
                    particule.todo();

                    int init_size = initiallyAddedAliensPerLevel.size();
                    int alien_no = aliens.size();
                    //Level Is Over
                    if (init_size == 0 && alien_no == 0) {
                        gameMusic.stop();

                        //loseSound.setCycleCount(1);
                        loseSound.play();

                        HttpClient httpClient = HttpClientBuilder.create().build();
                        HttpPost request = new HttpPost(GameValues.UPDATE_WEEKLY_TABLE_REQUEST_URL);
                        HttpPost request2 = new HttpPost(GameValues.UPDATE_ALL_TIME_TABLE_REQUEST_URL);

                        //This turns user object into a json string to update the scoreboard
                        String obj = userToJsonString(this.user);
                        StringEntity entity = new StringEntity(obj, ContentType.APPLICATION_JSON);
                        request.setEntity(entity);
                        request2.setEntity(entity);

                        try {
                            httpClient.execute(request2);
                            httpClient.execute(request);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        clearAndFade(level + 1);
                    }
                    if (particule instanceof Alien) {

                        if (((Alien) particule).getCurhealth() <= 0) {

                            score.set(score.get() + ((Alien) particule).getValue());
                            removeQueue(particule);
                        }

                    }
                    if (particule instanceof Player) {

                        if (((LiveBeing) particule).getCurhealth() <= 0) {

                            gameMusic.stop();

                            //loseSound.setCycleCount(1);
                            loseSound.play();

                            gameTimeline.stop();
                            bossPlekumatFire.stop();
                            plekumatFire.stop();

                            Label game_over = new Label();

                            game_over.setLayoutX(GameValues.LAYOUT_X);
                            game_over.setLayoutY(GameValues.LAYOUT_Y);
                            game_over.setMinWidth(GameValues.MIN_WIDTH);
                            game_over.setTextFill(GameValues.FONT_COLOR);
                            game_over.setText(GameValues.MESSAGE);
                            game_over.setFont(new Font(GameValues.FONT, GameValues.FONT_SIZE));
                            pane.getChildren().add(game_over);
                            //YOU DIED MY FRIEND.
                            removeQueue(particule);
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
                if(bullet instanceof IzmitPismaniye) {
                    bullet.move();

                    //Upgrade the weapon of player
                    if(bullet.intersects(player.getBoundsInLocal())) {
                        player.setCurhealth(GameValues.MAX_HEALTH);
                        player.upgradeWeapon();
                        removeQueue(bullet);
                    }
                }
                //Plekumat bullets hitting player
                if(bullet instanceof PlekumatBullet) {

                    if(bullet.intersects(player.getBoundsInLocal())) {
                        player.setCurhealth(player.getCurhealth() - bullet.getDamage());
                        if(score.get() <= 0) {
                            score.set(0);
                        }
                        else {
                            score.set(score.get() - GameValues.SCORE_REDUCTION_PLEKUMAT);
                        }
                        removeQueue(bullet);
                    }

                }
                //If BossPlekumat hits player, player
                //will take damage and player's weapon will
                //be downgraded
                if(bullet instanceof BossPlekumatBullet) {
                    if(bullet.intersects(player.getBoundsInLocal())) {
                        player.setCurhealth(player.getCurhealth() - bullet.getDamage());
                        player.downgradeWeapon();
                        if(score.get() <= 0) {
                            score.set(0);
                        }
                        else {
                            score.set(score.get() - GameValues.SCORE_REDUCTION_BOSS_PLEKUMAT);
                        }
                        removeQueue(bullet);
                    }
                }
                //If Player hits aliens
                if(bullet instanceof PlayerBullet) {

                    for(Alien alien : aliens) {

                        if(bullet.intersects(alien.getBoundsInLocal())) {

                            alien.setCurhealth(alien.getCurhealth() - bullet.getDamage());
                            removeQueue(bullet);
                        }
                    }
                }

                if(bullet.getCenterY() + bullet.getRadius() > GameValues.GAME_PANE_HEIGHT)
                    removeQueue(bullet);

            }
            //We add pismaniyes to the game enviroment
            if(Math.random() < GameValues.PISMANIYE_FREQUENCY) {

                Random randx = new Random();
                double randomX = GameValues.PISMANIYE_RADIUS + (GameValues.GAME_PANE_WIDTH - GameValues.PISMANIYE_RADIUS) * randx.nextDouble();

                Random randy = new Random();
                double randomY = GameValues.PISMANIYE_RADIUS + (GameValues.SPAWN_BORDER_HEIGHT - GameValues.PISMANIYE_RADIUS) * randy.nextDouble();

                IzmitPismaniye pismaniye  = new IzmitPismaniye(randomX,randomY);
                addQueue(pismaniye);

            }
            if(Math.random() < GameValues.ALIEN_ADD_FREQUENCY) {

                int init_size = initiallyAddedAliensPerLevel.size();
                if(init_size > 0) {
                    Random new_random = new Random();
                    int random_ind = new_random.nextInt(init_size);
                    Alien enemy = initiallyAddedAliensPerLevel.get(random_ind);
                    addQueue(enemy);
                    initiallyAddedAliensPerLevel.remove(random_ind);
                }
            }
            //We have queued the ones to delete
            //now really delete them
            for(Particule particule : particulesRemoved) {
                remove(particule);
            }
            //We add the ones we determined
            for(Particule particule : particulesAdded) {
                add(particule);
            }
            //now clear queues
            particulesAdded.clear();
            particulesRemoved.clear();
        }));
        gameTimeline.setCycleCount(Timeline.INDEFINITE);
        gameTimeline.play();
        plekumatFire = new Timeline(new KeyFrame(Duration.millis(GameValues.PLEKUMAT_FIRE_DURATION), event -> {

            for(Particule particule : particules) {
                if(particule instanceof Plekumat) {
                    ((Plekumat)particule).fire();
                }
            }
        }));
        bossPlekumatFire = new Timeline(new KeyFrame(Duration.millis(GameValues.BOSS_PLEKUMAT_FIRE_DURATION), event -> {

            for(Particule particule : particules) {
                if(particule instanceof BossPlekumat) {
                    ((BossPlekumat)particule).fire();
                }

            }

        }));
        plekumatFire.setCycleCount(Timeline.INDEFINITE);
        plekumatFire.play();
        bossPlekumatFire.setCycleCount(Timeline.INDEFINITE);
        bossPlekumatFire.play();
    }


    //this is the timeline for multiplayer part of the game
    //Working principle is very similar with normal game timeline
    //However, here player checks messages arrived from the other player
    //and with those messages, moves of other player or win/lose conditions are determined
    private void multiplayerTimelineSetup() {

        multiplayerTimeline = new Timeline(new KeyFrame(Duration.millis(GameValues.GAME_TIMELINE_DURATION), event -> {

            try {
                //We first form the message to be sent to the other player via our server
                Peer2PeerMessage message = new Peer2PeerMessage(false,false,player.getCenterX(),player.getCenterY(), isPlayerFired,score.getValue(),false,false,false);

                //We iterate the particules in the game.
                //Player,Opponent,BirCisim and their bullets are the particules
                for (Particule particule : particules) {

                    //Move particles
                    particule.todo();

                    if (particule instanceof Player) {

                        //If our player dies
                        if (((LiveBeing) particule).getCurhealth() <= 0) {

                            gameMusic.stop();

                            //loseSound.setCycleCount(1);
                            kunaSound.play();

                            //Stop the timeline
                            multiplayerTimeline.stop();

                            //Save the score of our player
                            HttpClient httpClient = HttpClientBuilder.create().build();
                            HttpPost request = new HttpPost(GameValues.UPDATE_WEEKLY_TABLE_REQUEST_URL);
                            HttpPost request2 = new HttpPost(GameValues.UPDATE_ALL_TIME_TABLE_REQUEST_URL);

                            String obj = userToJsonString(this.user);
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
                            game_over.setLayoutX(GameValues.LAYOUT_X);
                            game_over.setLayoutY(GameValues.LAYOUT_Y);
                            game_over.setMinWidth(GameValues.MIN_WIDTH);
                            game_over.setTextFill(GameValues.FONT_COLOR);
                            game_over.setText(GameValues.MESSAGE);
                            game_over.setFont(new Font(GameValues.FONT, GameValues.FONT_SIZE));
                            pane.getChildren().add(game_over);
                            //YOU DIED MY FRIEND.

                            //Here we set the I_died part of the message true
                            //Thus,the other player will understand that this player
                            //has died.So opponent understands that he/she is victorious
                            message.setIDied(true);
                            removeQueue(particule);
                        }
                    }

                }

                //This counter is for firing the BirCisim(Boss Alien)
                //in the multiplayer
                counter = counter + 1;

                //Fire1 and Fire2 Counters are determined in gamevalues
                if(counter == GameValues.BIRCISIM_FIRE2_COUNTER) {
                    birCisim.fire2();
                }
                if(counter == GameValues.BIRCISIM_FIRE1_COUNTER) {
                    birCisim.fire1();
                    counter = 0;
                }

                //this field checks the messages arrived
                //if other player died, then game finishes,this player wins
                //if other player wins, then game finished,this player loses
                //if other player reports that it has fired,the illusion of
                //that player fires
                if(!messagesReceived.isEmpty()) {
                    Peer2PeerMessage messageReceived = messagesReceived.poll();
                    multiplayerOpponent.setCenterX(messageReceived.getPosition()[0]);
                    multiplayerOpponent.setCenterY(messageReceived.getPosition()[1]);

                    if(messageReceived.isIFired()) {
                        multiplayerOpponent.fireBullet();
                    }

                    if(messageReceived.isIDied()) {
                        remove(multiplayerOpponent);

                        gameMusic.stop();
                        kunaSound.stop();

                        HttpClient httpClient = HttpClientBuilder.create().build();
                        HttpPost request = new HttpPost(GameValues.UPDATE_WEEKLY_TABLE_REQUEST_URL);
                        HttpPost request2 = new HttpPost(GameValues.UPDATE_ALL_TIME_TABLE_REQUEST_URL);

                        //This turns user object into a json string to update the scoreboard
                        score.set(score.get() + GameValues.BIRCISIM_VALUE);
                        String obj = userToJsonString(this.user);
                        StringEntity entity = new StringEntity(obj, ContentType.APPLICATION_JSON);
                        request.setEntity(entity);
                        request2.setEntity(entity);

                        try {
                            httpClient.execute(request2);
                            httpClient.execute(request);
                        } catch (IOException e) {
                            e.printStackTrace();
                     }

                        multiplayerTimeline.stop();

                        Label you_won = new Label();

                        you_won.setLayoutX(GameValues.LAYOUT_X);
                        you_won.setLayoutY(GameValues.LAYOUT_Y);
                        you_won.setMinWidth(GameValues.MIN_WIDTH);
                        you_won.setTextFill(GameValues.FONT_COLOR);
                        you_won.setText(GameValues.WIN_MESSAGE);
                        you_won.setFont(new Font(GameValues.FONT, GameValues.FONT_SIZE));
                        pane.getChildren().add(you_won);

                    }
                    //If other player has won the game according to the server
                    if(messageReceived.isIWonDude()) {

                        gameMusic.stop();
                        kunaSound.stop();

                        HttpClient httpClient = HttpClientBuilder.create().build();
                        HttpPost request = new HttpPost(GameValues.UPDATE_WEEKLY_TABLE_REQUEST_URL);
                        HttpPost request2 = new HttpPost(GameValues.UPDATE_ALL_TIME_TABLE_REQUEST_URL);

                        //This turns user object into a json string to update the scoreboard
                        String obj = userToJsonString(this.user);
                        StringEntity entity = new StringEntity(obj, ContentType.APPLICATION_JSON);
                        request.setEntity(entity);
                        request2.setEntity(entity);

                        try {
                            httpClient.execute(request2);
                            httpClient.execute(request);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        multiplayerTimeline.stop();

                        Label lose = new Label();

                        lose.setLayoutX(GameValues.LAYOUT_X);
                        lose.setLayoutY(GameValues.LAYOUT_Y);
                        lose.setMinWidth(GameValues.MIN_WIDTH);
                        lose.setTextFill(GameValues.FONT_COLOR);
                        lose.setText(GameValues.MESSAGE);
                        lose.setFont(new Font(GameValues.FONT, GameValues.FONT_SIZE));
                        pane.getChildren().add(lose);

                    }
                    //If server decides this player has won
                    if(messageReceived.isYouWon()) {

                        removeQueue(birCisim);

                        score.set(score.get() + birCisim.getValue());

                        HttpClient httpClient = HttpClientBuilder.create().build();
                        HttpPost request = new HttpPost(GameValues.UPDATE_WEEKLY_TABLE_REQUEST_URL);
                        HttpPost request2 = new HttpPost(GameValues.UPDATE_ALL_TIME_TABLE_REQUEST_URL);

                        //This turns user object into a json string to update the scoreboard
                        String obj = userToJsonString(this.user);
                        StringEntity entity = new StringEntity(obj, ContentType.APPLICATION_JSON);
                        request.setEntity(entity);
                        request2.setEntity(entity);

                        try {
                            httpClient.execute(request2);
                            httpClient.execute(request);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        gameMusic.stop();
                        kunaSound.stop();

                        multiplayerTimeline.stop();

                        Label you_won = new Label();

                        you_won.setLayoutX(GameValues.LAYOUT_X);
                        you_won.setLayoutY(GameValues.LAYOUT_Y);
                        you_won.setMinWidth(GameValues.MIN_WIDTH);
                        you_won.setTextFill(GameValues.FONT_COLOR);
                        you_won.setText(GameValues.WIN_MESSAGE);
                        you_won.setFont(new Font(GameValues.FONT, GameValues.FONT_SIZE));
                        pane.getChildren().add(you_won);


                    }

                }

                //Collision detection
                for(Bullet bullet : bullets) {

                    //BirCisim bullets hitting player
                    if(bullet instanceof BirCisimBullet1 || bullet instanceof BirCisimBullet2) {

                        if(bullet.intersects(player.getBoundsInLocal())) {
                            player.setCurhealth(player.getCurhealth() - bullet.getDamage());
                            if(score.get() <= 0) {
                                score.set(0);
                            }
                            else {
                                score.set(score.get() - GameValues.SCORE_REDUCTION_PLEKUMAT);
                            }

                            removeQueue(bullet);
                        }

                        if(bullet.intersects(multiplayerOpponent.getBoundsInLocal())) {
                            removeQueue(bullet);
                        }

                    }

                    //If Player hits birCisim
                    if(bullet instanceof PlayerBullet) {

                        for(Alien alien : aliens) {

                            if(bullet.intersects(alien.getBoundsInLocal())) {

                                alien.setCurhealth(alien.getCurhealth() - bullet.getDamage());
                                score.set(score.get() + GameValues.PLAYER_WEAPON_LVL1_DAMAGE);
                                removeQueue(bullet);

                                if (alien.getCurhealth() <= 0) {

                                    /*score.set(score.get() + alien.getValue());
                                    removeQueue(alien);

                                    gameMusic.stop();
                                    kunaSound.stop();

                                    multiplayerTimeline.stop();

                                    Label you_won = new Label();

                                    you_won.setLayoutX(GameValues.LAYOUT_X);
                                    you_won.setLayoutY(GameValues.LAYOUT_Y);
                                    you_won.setMinWidth(GameValues.MIN_WIDTH);
                                    you_won.setTextFill(GameValues.FONT_COLOR);
                                    you_won.setText(GameValues.WIN_MESSAGE);
                                    you_won.setFont(new Font(GameValues.FONT, GameValues.FONT_SIZE));
                                    pane.getChildren().add(you_won); */

                                    message.setIWonDude(true);

                                    toServer.writeObject(message);

                                }
                            }
                        }
                    }

                    //If bullet of multiplayer opponent hits to the birCisim
                    if(bullet instanceof MultiplayerOpponentBullet) {

                        for(Alien alien : aliens) {
                            if(bullet.intersects(alien.getBoundsInLocal())) {

                                alien.setCurhealth(alien.getCurhealth() - bullet.getDamage());
                                removeQueue(bullet);
                            }
                        }
                    }
                    if(bullet.getCenterY() + bullet.getRadius() > GameValues.GAME_PANE_HEIGHT)
                        removeQueue(bullet);
                }

                //If isPlayerFired for controlling the firing of opponent is true
                //we change it to false at the end of the timeline
                isPlayerFired = false;

                //we write the modified message to the other side
                toServer.writeObject(message);
                messagesToSend.add(message);

                //We have queued the ones to delete
                //now really delete them

                for(Particule particule : particulesRemoved) {
                    remove(particule);
                }
                //We add the ones we determined
                for(Particule particule : particulesAdded) {
                    add(particule);
                }
                //now clear queues
                particulesAdded.clear();
                particulesRemoved.clear();

            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }));
        multiplayerTimeline.setCycleCount(Timeline.INDEFINITE);
        multiplayerTimeline.play();
    }
}


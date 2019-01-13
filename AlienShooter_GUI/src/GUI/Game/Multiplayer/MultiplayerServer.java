package GUI.Game.Multiplayer;

import javafx.application.Application;
import javafx.stage.Stage;
import GUI.Game.Multiplayer.Peer2PeerMessage;
import GUI.Game.Multiplayer.MultiplayerConstants;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.server.ExportException;



/**
 * This is the server that players are going to connect
 */
public class MultiplayerServer extends Application {

    private int session_no = 1;


    @Override
    public void start(Stage primaryStage) throws Exception {


        new Thread( () -> {

            try {

                ServerSocket serverSocket = new ServerSocket(MultiplayerConstants.server_port);
                System.out.println(MultiplayerConstants.server_opening_message);

                while(true) {

                    System.out.println(": Waiting for players");

                    Socket player1 = serverSocket.accept();

                    System.out.println("Player1 has joined");


                    //Initializing input and output strings to exchange messages
                    ObjectOutputStream toPlayer1 = new ObjectOutputStream(player1.getOutputStream());
                    ObjectInputStream fromPlayer1 = new ObjectInputStream(player1.getInputStream());

                    toPlayer1.writeInt(MultiplayerConstants.PLAYER1);
                    toPlayer1.flush();


                    Socket player2 = serverSocket.accept();


                    System.out.println("Player2 has joined");

                    ObjectOutputStream toPlayer2 = new ObjectOutputStream(player2.getOutputStream());
                    ObjectInputStream fromPlayer2 = new ObjectInputStream(player2.getInputStream());

                    toPlayer2.writeInt(MultiplayerConstants.PLAYER2);
                    toPlayer2.flush();

                    System.out.println("New game is starting between these two players...");

                    new Thread(new HandleSession(player1,player2,toPlayer1,toPlayer2,fromPlayer1,fromPlayer2)).start();

                }

            }
            catch (Exception e) {
                System.out.println(e);
            }

        }).start();
    }

    class HandleSession implements Runnable {

        private Socket player1;
        private Socket player2;

        private ObjectOutputStream toPlayer1;
        private ObjectInputStream fromPlayer1;

        private ObjectOutputStream toPlayer2;
        private ObjectInputStream fromPlayer2;

        public HandleSession(Socket player1,Socket player2,ObjectOutputStream toPlayer1,ObjectOutputStream toPlayer2,ObjectInputStream fromPlayer1, ObjectInputStream fromPlayer2) {

            this.player1 = player1;
            this.player2 = player2;
            this.toPlayer1 = toPlayer1;
            this.toPlayer2 = toPlayer2;
            this.fromPlayer1 = fromPlayer1;
            this.fromPlayer2 = fromPlayer2;
        }

        public void run() {

            try {



                //Sends initial message to start the timelines of players
                toPlayer1.writeInt(1);
                toPlayer1.flush();
                toPlayer2.writeInt(1);
                toPlayer2.flush();

                while(true) {

                    //Reading messages
                    Peer2PeerMessage message_from_player1 = (Peer2PeerMessage) fromPlayer1.readObject();
                    Peer2PeerMessage message_from_player2 = (Peer2PeerMessage) fromPlayer2.readObject();


                    if(message_from_player1.isI_won_dude()) {
                        //simdilik sadece break olsun
                        break;
                    }
                    else if(message_from_player2.isI_won_dude()) {
                        //simdilik sadece break olsun
                        break;
                    }

                    //Exchanging arrived messages
                    toPlayer2.writeObject(message_from_player1);
                    toPlayer2.flush();
                    toPlayer1.writeObject(message_from_player2);
                    toPlayer1.flush();

                }


            }
            catch (Exception e) {
                System.out.println(e);
            }


        }

    }
}

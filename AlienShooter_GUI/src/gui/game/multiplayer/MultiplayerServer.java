package gui.game.multiplayer;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;


/**
 * This is the server that players are going to connect
 */
public class MultiplayerServer extends Application {

    private int sessionNo = 1;


    @Override
    public void start(Stage primaryStage) throws Exception {


        new Thread( () -> {

            try {

                ServerSocket serverSocket = new ServerSocket(MultiplayerConstants.SERVER_PORT);
                System.out.println(MultiplayerConstants.SERVER_OPENING_MESSAGE);

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
                    Peer2PeerMessage messageFromPlayer1 = (Peer2PeerMessage) fromPlayer1.readObject();
                    Peer2PeerMessage messageFromPlayer2 = (Peer2PeerMessage) fromPlayer2.readObject();

                    //Here, server decides which player is going to win

                    //Only Player1 sends a winning decleration
                    if(messageFromPlayer1.isIWonDude()&&!messageFromPlayer2.isIWonDude()) {

                        //message which is going to be sent to player1 is changed
                        //server modifies it and says player1 that he/she has won
                        messageFromPlayer2.setYouWon(true);

                        //Message going to Player2 contains Player1's decleration of winning
                        //Server lets Player1 to send it so Player2 understands that Player1 has won
                        toPlayer2.writeObject(messageFromPlayer1);
                        toPlayer2.flush();

                        //send message to the player1 including server's decision
                        toPlayer1.writeObject(messageFromPlayer2);
                        toPlayer1.flush();


                    }
                    //Only Player2 sends a winning decleration
                    else if(!messageFromPlayer1.isIWonDude()&&messageFromPlayer2.isIWonDude()) {

                        //message which is going to be sent to player2 is changed
                        //server modifies it and says player2 that he/she has won
                        messageFromPlayer1.setYouWon(true);

                        //send message to the player2 including server's decision
                        toPlayer2.writeObject(messageFromPlayer1);
                        toPlayer2.flush();

                        //Message going to Player1 contains Player2's decleration of winning
                        //Server lets Player2 to send it so Player1 understands that Player2 has won
                        toPlayer1.writeObject(messageFromPlayer2);
                        toPlayer1.flush();

                    }
                    //If both players declare that they have won, server needs to make a choice
                    else if(messageFromPlayer1.isIWonDude()&&messageFromPlayer2.isIWonDude()) {

                        //decision will be made randomly,since they are going to kill the enemy
                        //at the same time.Thus, making random decision is not going to affect fairness goal of server
                        Random random = new Random();
                        boolean randomBoolean = random.nextBoolean();

                        //If random boolean is true, player1 will be victorious
                        if(randomBoolean) {

                            //Server does not let Player2 to send his/her winning decleration
                            messageFromPlayer2.setIWonDude(false);
                            //Server modifies youWon to send Player1 that he/she has won
                            messageFromPlayer2.setYouWon(true);

                            //Message contains Player1's winning decleration
                            //Server lets this message to reach player2
                            toPlayer2.writeObject(messageFromPlayer1);
                            toPlayer2.flush();

                            //Server sends knowledge that player1 has won
                            toPlayer1.writeObject(messageFromPlayer2);
                            toPlayer1.flush();

                        }
                        //Else, player2 will be victorious
                        else {

                            //Server does not let Player1 to send his/her winning decleration
                            messageFromPlayer1.setIWonDude(false);
                            //Server modifies youWon to send Player2 that he/she has won
                            messageFromPlayer1.setYouWon(true);

                            //Message contains Player2's winning decleration
                            //Server lets this message to reach Player1
                            toPlayer1.writeObject(messageFromPlayer2);
                            toPlayer1.flush();

                            //Server sends knowledge that player2 has won
                            toPlayer2.writeObject(messageFromPlayer1);
                            toPlayer2.flush();

                        }

                    }
                    else {

                        //Exchanging arrived messages
                        toPlayer2.writeObject(messageFromPlayer1);
                        toPlayer2.flush();
                        toPlayer1.writeObject(messageFromPlayer2);
                        toPlayer1.flush();
                    }

                }


            }
            catch (Exception e) {
                System.out.println(e);
            }


        }

    }
}

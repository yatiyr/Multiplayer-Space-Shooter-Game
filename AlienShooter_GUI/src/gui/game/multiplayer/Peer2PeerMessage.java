package gui.game.multiplayer;
import java.io.Serializable;


/**This class is the message object which is going to be shared
 * Between two players
 */
public class Peer2PeerMessage implements Serializable {

    private boolean imHit = false;
    private boolean iHit = false;
    private double[] position = new double[2];
    private boolean iFired = false;
    private int myScore = 0;
    private boolean iWonDude = false;
    private boolean iDied = false;

    //This variable is very important
    //because it will only be determined
    //by server.If both of the players
    //declare that they have won, server
    //is going to decide which player is the winner
    private boolean youWon = false;

    public void setIDied(boolean iDied) {
        this.iDied = iDied;
    }

    public void setYouWon(boolean youWon) { this.youWon = youWon; }

    public void setIHit(boolean iHit) {
        this.iHit = iHit;
    }

    public void setMyScore(int myScore) {
        this.myScore = myScore;
    }

    public void setIWonDude(boolean iWonDude) {
        this.iWonDude = iWonDude;
    }

    public Peer2PeerMessage(boolean imHit, boolean iHit, double pos_x, double pos_y, boolean iFired, int myScore, boolean iWonDude, boolean iDied,boolean youWon) {

        this.imHit = imHit;
        this.iHit = iHit;
        this.iFired = iFired;
        this.myScore = myScore;
        this.position[0] = pos_x;
        this.position[1] = pos_y;
        this.iWonDude = iWonDude;
        this.iDied = iDied;
        this.youWon = youWon;

    }

    public boolean isYouWon() {return youWon;}

    public boolean isIDied() { return iDied;}

    public boolean isImHit() {
        return imHit;
    }

    public boolean isIHit() {
        return iHit;
    }

    public double[] getPosition() {
        return position;
    }

    public boolean isIFired() {
        return iFired;
    }

    public int getMyScore() {
        return myScore;
    }

    public boolean isIWonDude() {
        return iWonDude;
    }



}

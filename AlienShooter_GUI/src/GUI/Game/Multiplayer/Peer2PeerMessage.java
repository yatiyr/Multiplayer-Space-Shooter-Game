package GUI.Game.Multiplayer;
import java.io.Serializable;


/**This class is the message object which is going to be shared
 * Between two players
 */

public class Peer2PeerMessage implements Serializable {

    private boolean Im_hit = false;
    private boolean I_hit = false;
    private double[] position = new double[2];
    private boolean I_fired = false;
    private int my_score = 0;
    private boolean I_won_dude = false;
    private boolean I_died = false;

    public void setI_died(boolean i_died) {
        I_died = i_died;
    }

    public void setI_hit(boolean i_hit) {
        I_hit = i_hit;
    }

    public void setMy_score(int my_score) {
        this.my_score = my_score;
    }

    public void setI_won_dude(boolean i_won_dude) {
        I_won_dude = i_won_dude;
    }

    public Peer2PeerMessage(boolean Im_hit, boolean I_hit, double pos_x, double pos_y, boolean I_fired, int my_score, boolean I_won_dude, boolean I_died) {

        this.Im_hit = Im_hit;
        this.I_hit = I_hit;
        this.I_fired = I_fired;
        this.my_score = my_score;
        this.position[0] = pos_x;
        this.position[1] = pos_y;
        this.I_won_dude = I_won_dude;
        this.I_died = I_died;

    }

    public boolean isI_died() { return I_died;}

    public boolean isIm_hit() {
        return Im_hit;
    }

    public boolean isI_hit() {
        return I_hit;
    }

    public double[] getPosition() {
        return position;
    }

    public boolean isI_fired() {
        return I_fired;
    }

    public int getMy_score() {
        return my_score;
    }

    public boolean isI_won_dude() {
        return I_won_dude;
    }



}

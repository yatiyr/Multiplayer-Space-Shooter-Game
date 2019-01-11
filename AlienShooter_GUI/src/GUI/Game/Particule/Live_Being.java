package GUI.Game.Particule;

/*
Live being is derived from particule and it is the abstract class
of alien and player.
It has max health and current health values
 */
public abstract class Live_Being extends Particule {

    private int curhealth;


    public Live_Being(int curhealth,double radius) {
        super(radius);
        this.curhealth = curhealth;
    }


    public int getCurhealth() {

        return curhealth;
    }

    public void setCurhealth(int newhealth) {

        this.curhealth = newhealth;
    }

}

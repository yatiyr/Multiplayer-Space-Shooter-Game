package gui.game.particule;

import gui.game.Engine;
import gui.game.GamePane;
import gui.GameValues;

//Plekumats are small enemies and easier to kill, just like how Arif kills them in G.O.R.A
public class Plekumat extends Alien {

    public Plekumat() {


        super(GameValues.PLEKUMAT_HEALTH,GameValues.PLEKUMAT_VALUE,GameValues.PLEKUMAT_RADIUS,GameValues.PLEKUMAT_DELTA_X,GameValues.PLEKUMAT_DIRECTION);

        super.setFill(GameValues.PLEKUMAT_COLOUR);
    }

    public void fire() {

        Engine engine = ((GamePane)getParent()).getEngine();
        PlekumatBullet pb = new PlekumatBullet(getCenterX(),getCenterY()+getRadius());

        // Precision fire according to the chance of Gudumlu_Fire_Frequency
        if(Math.random() < GameValues.PLEKUMAT_GUDUMLU_FIRE_FREQUENCY) {
            double vecx = pb.getCenterX() - engine.getPlayer().getCenterX();
            double vecy = pb.getCenterY() - engine.getPlayer().getCenterY();
            double angle = Math.atan(vecx/vecy);

            pb.setDeltaX(-1*Math.sin(angle));
            pb.setDeltaY(-1*Math.cos(angle));

            engine.addQueue(pb);
        }
        else {

            pb.setDeltaX(GameValues.PLEKUMAT_BULLET_DELTA_X);
            pb.setDeltaY(GameValues.PLEKUMAT_BULLET_DELTA_Y);
            engine.addQueue(pb);
        }
    }

    @Override
    public void todo() {
        move();
    }

}

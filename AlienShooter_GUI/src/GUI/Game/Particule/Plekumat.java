package GUI.Game.Particule;

import GUI.Game.Engine;
import GUI.Game.Game_Pane;
import GUI.GameValues;

//Plekumats are small enemies and easier to kill, just like how Arif kills them in G.O.R.A
public class Plekumat extends Alien {

    public Plekumat() {


        super(GameValues.Plekumat_Health,GameValues.Plekumat_Value,GameValues.Plekumat_Radius,GameValues.Plekumat_DeltaX,GameValues.Plekumat_Direction);

        super.setFill(GameValues.Plekumat_Colour);
    }

    public void fire() {

        Engine engine = ((Game_Pane)getParent()).getEngine();
        Plekumat_Bullet pb = new Plekumat_Bullet(getCenterX(),getCenterY()+getRadius());

        // Precision fire according to the chance of Gudumlu_Fire_Frequency
        if(Math.random() < GameValues.Plekumat_Gudumlu_Fire_Frequency) {
            double vecx = pb.getCenterX() - engine.getPlayer().getCenterX();
            double vecy = pb.getCenterY() - engine.getPlayer().getCenterY();
            double angle = Math.atan(vecx/vecy);

            pb.setDeltaX(-1*Math.sin(angle));
            pb.setDeltaY(-1*Math.cos(angle));

            engine.add_queue(pb);
        }
        else {

            pb.setDeltaX(GameValues.Plekumat_Bullet_DeltaX);
            pb.setDeltaY(GameValues.Plekumat_Bullet_DeltaY);
            engine.add_queue(pb);
        }
    }

    @Override
    public void todo() {
        move();
    }

}

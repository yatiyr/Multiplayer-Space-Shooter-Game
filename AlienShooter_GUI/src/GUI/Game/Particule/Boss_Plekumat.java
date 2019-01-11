package GUI.Game.Particule;

import GUI.Game.Engine;
import GUI.Game.Game_Pane;
import GUI.GameValues;

public class Boss_Plekumat extends  Alien {

    public Boss_Plekumat() {


        super(GameValues.Boss_Plekumat_Max_Health,GameValues.Boss_Plekumat_Value,GameValues.Boss_Plekumat_Radius,GameValues.Boss_Plekumat_DeltaX,GameValues.Boss_Plekumat_Direction);

        super.setFill(GameValues.Boss_Plekumat_Colour);
    }

    //Boss plekumats fire in two ways;
    //In first way, they burst 3 bullets at the same time
    //and the bullet in the middle aims for the location of player
    //the other bullets go to the sides

    //Second way of firing is straightforward, it only fires one bullet directly below
    public void fire() {

        Engine engine = ((Game_Pane)getParent()).getEngine();
        Boss_Plekumat_Bullet bpb = new Boss_Plekumat_Bullet(getCenterX(),getCenterY()+getRadius());
        Boss_Plekumat_Bullet bpb2 = new Boss_Plekumat_Bullet(getCenterX(),getCenterY()+getRadius());
        Boss_Plekumat_Bullet bpb3 = new Boss_Plekumat_Bullet(getCenterX(),getCenterY()+getRadius());
        // 90% chance to fire to player
        // 10% chance to fire directly bottom
        if(Math.random() < GameValues.Boss_Plekumat_Gudumlu_Fire_Chance) {
            double vecx = bpb.getCenterX() - engine.getPlayer().getCenterX();
            double vecy = bpb.getCenterY() - engine.getPlayer().getCenterY();
            double angle = Math.atan(vecx/vecy);

            bpb2.setDeltaX(-1*Math.sin(angle+GameValues.Boss_Plekumat_Bullet_Seperation_Angle));
            bpb2.setDeltaY(-1*Math.cos(angle));

            bpb3.setDeltaX(-1*Math.sin(angle-GameValues.Boss_Plekumat_Bullet_Seperation_Angle));
            bpb3.setDeltaY(-1*Math.cos(angle));

            bpb.setDeltaX(-1*Math.sin(angle));
            bpb.setDeltaY(-1*Math.cos(angle));

            engine.add_queue(bpb2);
            engine.add_queue(bpb);
            engine.add_queue(bpb3);
        }
        else {

            bpb.setDeltaX(-1);
            bpb.setDeltaY(-1);
            engine.add_queue(bpb);
        }

    }

    @Override
    public void todo() {
        move();
    }
}

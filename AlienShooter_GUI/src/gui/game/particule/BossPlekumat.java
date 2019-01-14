package gui.game.particule;

import gui.game.Engine;
import gui.game.GamePane;
import gui.GameValues;

public class BossPlekumat extends  Alien {

    public BossPlekumat() {


        super(GameValues.BOSS_PLEKUMAT_MAX_HEALTH,GameValues.BOSS_PLEKUMAT_VALUE,GameValues.BOSS_PLEKUMAT_RADIUS,GameValues.BOSS_PLEKUMAT_DELTA_X,GameValues.BOSS_PLEKUMAT_DIRECTION);

        super.setFill(GameValues.BOSS_PLEKUMAT_COLOUR);
    }

    //Boss plekumats fire in two ways;
    //In first way, they burst 3 bullets at the same time
    //and the bullet in the middle aims for the location of player
    //the other bullets go to the sides

    //Second way of firing is straightforward, it only fires one bullet directly below
    public void fire() {

        Engine engine = ((GamePane)getParent()).getEngine();
        BossPlekumatBullet bpb = new BossPlekumatBullet(getCenterX(),getCenterY()+getRadius());
        BossPlekumatBullet bpb2 = new BossPlekumatBullet(getCenterX(),getCenterY()+getRadius());
        BossPlekumatBullet bpb3 = new BossPlekumatBullet(getCenterX(),getCenterY()+getRadius());
        // 90% chance to fire to player
        // 10% chance to fire directly bottom
        if(Math.random() < GameValues.BOSS_PLEKUMAT_GUDUMLU_FIRE_CHANCE) {
            double vecx = bpb.getCenterX() - engine.getPlayer().getCenterX();
            double vecy = bpb.getCenterY() - engine.getPlayer().getCenterY();
            double angle = Math.atan(vecx/vecy);

            bpb2.setDeltaX(-1*Math.sin(angle+GameValues.BOSS_PLEKUMAT_BULLET_SEPERATION_ANGLE));
            bpb2.setDeltaY(-1*Math.cos(angle));

            bpb3.setDeltaX(-1*Math.sin(angle-GameValues.BOSS_PLEKUMAT_BULLET_SEPERATION_ANGLE));
            bpb3.setDeltaY(-1*Math.cos(angle));

            bpb.setDeltaX(-1*Math.sin(angle));
            bpb.setDeltaY(-1*Math.cos(angle));

            engine.addQueue(bpb2);
            engine.addQueue(bpb);
            engine.addQueue(bpb3);
        }
        else {

            bpb.setDeltaX(-1);
            bpb.setDeltaY(-1);
            engine.addQueue(bpb);
        }

    }

    @Override
    public void todo() {
        move();
    }
}

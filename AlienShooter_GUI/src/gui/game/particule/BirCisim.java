package gui.game.particule;

import gui.game.Engine;
import gui.game.GamePane;
import gui.GameValues;

public class BirCisim extends Alien{


    public BirCisim() {


        super(GameValues.BIRCISIM_MAX_HEALTH,GameValues.BIRCISIM_VALUE,GameValues.BIRCISIM_RADIUS,GameValues.BIRCISIM_DELTA_X,GameValues.BIRCISIM_DIRECTION);

        super.setFill(GameValues.BIRCISIM_COLOUR);

        setCenterX(GameValues.BIRCISIM_SPAWN_X);
        setCenterY(GameValues.BIRCISIM_SPAWN_Y);
    }


    //Fires 6 big bullets and one of them is towards to the player1 and other is to the player2
    public void fire1() {

        Engine engine = ((GamePane)getParent()).getEngine();
        BirCisimBullet1 bpb_p1 = new BirCisimBullet1(getCenterX(),getCenterY()+getRadius());
        BirCisimBullet1 bpb2_p1 = new BirCisimBullet1(getCenterX(),getCenterY()+getRadius());
        BirCisimBullet1 bpb3_p1 = new BirCisimBullet1(getCenterX(),getCenterY()+getRadius());

        BirCisimBullet1 bpb_p2 = new BirCisimBullet1(getCenterX(),getCenterY()+getRadius());
        BirCisimBullet1 bpb2_p2 = new BirCisimBullet1(getCenterX(),getCenterY()+getRadius());
        BirCisimBullet1 bpb3_p2 = new BirCisimBullet1(getCenterX(),getCenterY()+getRadius());


        double vecx_p1 = bpb_p1.getCenterX() - engine.getPlayer().getCenterX();
        double vecy_p1 = bpb_p1.getCenterY() - engine.getPlayer().getCenterY();
        double angle_p1 = Math.atan(vecx_p1/vecy_p1);

        bpb2_p1.setDeltaX(-1*Math.sin(angle_p1+GameValues.BIRCISIM_BULLET1_SEPERATION_ANGLE));
        bpb2_p1.setDeltaY(-1*Math.cos(angle_p1));

        bpb3_p1.setDeltaX(-1*Math.sin(angle_p1-GameValues.BIRCISIM_BULLET1_SEPERATION_ANGLE));
        bpb3_p1.setDeltaY(-1*Math.cos(angle_p1));

        bpb_p1.setDeltaX(-1*Math.sin(angle_p1));
        bpb_p1.setDeltaY(-1*Math.cos(angle_p1));

        engine.addQueue(bpb2_p1);
        engine.addQueue(bpb_p1);
        engine.addQueue(bpb3_p1);




        double vecx_p2 = bpb_p1.getCenterX() - engine.getMultiplayerOpponent().getCenterX();
        double vecy_p2 = bpb_p1.getCenterY() - engine.getMultiplayerOpponent().getCenterY();
        double angle_p2 = Math.atan(vecx_p2/vecy_p2);

        bpb2_p2.setDeltaX(-1*Math.sin(angle_p2+GameValues.BIRCISIM_BULLET1_SEPERATION_ANGLE));
        bpb2_p2.setDeltaY(-1*Math.cos(angle_p2));

        bpb3_p2.setDeltaX(-1*Math.sin(angle_p2-GameValues.BIRCISIM_BULLET1_SEPERATION_ANGLE));
        bpb3_p2.setDeltaY(-1*Math.cos(angle_p2));

        bpb_p2.setDeltaX(-1*Math.sin(angle_p2));
        bpb_p2.setDeltaY(-1*Math.cos(angle_p2));

        engine.addQueue(bpb2_p2);
        engine.addQueue(bpb_p2);
        engine.addQueue(bpb3_p2);


    }

    //Fires two little bullets.One of them is towards player1 and other one is player2
    public void fire2() {

        Engine engine = ((GamePane)getParent()).getEngine();
        BirCisimBullet2 bpb_p1 = new BirCisimBullet2(getCenterX(),getCenterY()+getRadius());
        BirCisimBullet2 bpb_p2 = new BirCisimBullet2(getCenterX(),getCenterY()+getRadius());

        double vecx_p1 = bpb_p1.getCenterX() - engine.getMultiplayerOpponent().getCenterX();
        double vecy_p1 = bpb_p1.getCenterY() - engine.getMultiplayerOpponent().getCenterY();
        double angle_p1 = Math.atan(vecx_p1/vecy_p1);

        double vecx_p2 = bpb_p1.getCenterX() - engine.getPlayer().getCenterX();
        double vecy_p2 = bpb_p1.getCenterY() - engine.getPlayer().getCenterY();
        double angle_p2 = Math.atan(vecx_p2/vecy_p2);

        bpb_p1.setDeltaX(-1*Math.sin(angle_p1));
        bpb_p1.setDeltaY(-1*Math.cos(angle_p1));

        bpb_p2.setDeltaX(-1*Math.sin(angle_p2));
        bpb_p2.setDeltaY(-1*Math.cos(angle_p2));

        engine.addQueue(bpb_p1);
        engine.addQueue(bpb_p2);

    }

    @Override
    public void todo() {

    }
}

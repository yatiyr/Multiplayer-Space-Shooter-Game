package GUI.Game.Particule;

import GUI.Game.Engine;
import GUI.Game.Game_Pane;
import GUI.GameValues;

public class BirCisim extends Alien{


    public BirCisim() {


        super(GameValues.BirCisim_Max_Health,GameValues.BirCisim_Value,GameValues.BirCisim_Radius,GameValues.BirCisim_DeltaX,GameValues.BirCisim_Direction);

        super.setFill(GameValues.BirCisim_Colour);

        setCenterX(GameValues.BirCisim_SpawnX);
        setCenterY(GameValues.BirCisim_SpawnY);
    }


    //Second way of firing is straightforward, it only fires one bullet directly below
    public void fire1() {

        Engine engine = ((Game_Pane)getParent()).getEngine();
        BirCisim_Bullet1 bpb_p1 = new BirCisim_Bullet1(getCenterX(),getCenterY()+getRadius());
        BirCisim_Bullet1 bpb2_p1 = new BirCisim_Bullet1(getCenterX(),getCenterY()+getRadius());
        BirCisim_Bullet1 bpb3_p1 = new BirCisim_Bullet1(getCenterX(),getCenterY()+getRadius());

        BirCisim_Bullet1 bpb_p2 = new BirCisim_Bullet1(getCenterX(),getCenterY()+getRadius());
        BirCisim_Bullet1 bpb2_p2 = new BirCisim_Bullet1(getCenterX(),getCenterY()+getRadius());
        BirCisim_Bullet1 bpb3_p2 = new BirCisim_Bullet1(getCenterX(),getCenterY()+getRadius());


        double vecx_p1 = bpb_p1.getCenterX() - engine.getPlayer().getCenterX();
        double vecy_p1 = bpb_p1.getCenterY() - engine.getPlayer().getCenterY();
        double angle_p1 = Math.atan(vecx_p1/vecy_p1);

        bpb2_p1.setDeltaX(-1*Math.sin(angle_p1+GameValues.BirCisim_Bullet1_Seperation_Angle));
        bpb2_p1.setDeltaY(-1*Math.cos(angle_p1));

        bpb3_p1.setDeltaX(-1*Math.sin(angle_p1-GameValues.BirCisim_Bullet1_Seperation_Angle));
        bpb3_p1.setDeltaY(-1*Math.cos(angle_p1));

        bpb_p1.setDeltaX(-1*Math.sin(angle_p1));
        bpb_p1.setDeltaY(-1*Math.cos(angle_p1));

        engine.add_queue(bpb2_p1);
        engine.add_queue(bpb_p1);
        engine.add_queue(bpb3_p1);




        double vecx_p2 = bpb_p1.getCenterX() - engine.getMultiplayer_opponent().getCenterX();
        double vecy_p2 = bpb_p1.getCenterY() - engine.getMultiplayer_opponent().getCenterY();
        double angle_p2 = Math.atan(vecx_p2/vecy_p2);

        bpb2_p2.setDeltaX(-1*Math.sin(angle_p2+GameValues.BirCisim_Bullet1_Seperation_Angle));
        bpb2_p2.setDeltaY(-1*Math.cos(angle_p2));

        bpb3_p2.setDeltaX(-1*Math.sin(angle_p2-GameValues.BirCisim_Bullet1_Seperation_Angle));
        bpb3_p2.setDeltaY(-1*Math.cos(angle_p2));

        bpb_p2.setDeltaX(-1*Math.sin(angle_p2));
        bpb_p2.setDeltaY(-1*Math.cos(angle_p2));

        engine.add_queue(bpb2_p2);
        engine.add_queue(bpb_p2);
        engine.add_queue(bpb3_p2);


    }

    public void fire2() {

        Engine engine = ((Game_Pane)getParent()).getEngine();
        BirCisim_Bullet2 bpb_p1 = new BirCisim_Bullet2(getCenterX(),getCenterY()+getRadius());
        BirCisim_Bullet2 bpb_p2 = new BirCisim_Bullet2(getCenterX(),getCenterY()+getRadius());

        double vecx_p1 = bpb_p1.getCenterX() - engine.getMultiplayer_opponent().getCenterX();
        double vecy_p1 = bpb_p1.getCenterY() - engine.getMultiplayer_opponent().getCenterY();
        double angle_p1 = Math.atan(vecx_p1/vecy_p1);

        double vecx_p2 = bpb_p1.getCenterX() - engine.getPlayer().getCenterX();
        double vecy_p2 = bpb_p1.getCenterY() - engine.getPlayer().getCenterY();
        double angle_p2 = Math.atan(vecx_p2/vecy_p2);

        bpb_p1.setDeltaX(-1*Math.sin(angle_p1));
        bpb_p1.setDeltaY(-1*Math.cos(angle_p1));

        bpb_p2.setDeltaX(-1*Math.sin(angle_p2));
        bpb_p2.setDeltaY(-1*Math.cos(angle_p2));

        engine.add_queue(bpb_p1);
        engine.add_queue(bpb_p2);


    }

    @Override
    public void todo() {

    }
}

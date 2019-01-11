package GUI.Game.Particule;

import GUI.GameValues;

public class BirCisim_Bullet2 extends Bullet{

    public BirCisim_Bullet2(double centerX,double centerY) {

        super(GameValues.BirCisim_Bullet2_Radius,GameValues.BirCisim_Bullet2_Damage,centerX,centerY,GameValues.BirCisim_Bullet2_DeltaX,GameValues.BirCisim_Bullet2_DeltaY);

        super.setFill(GameValues.BirCisim_Bullet2_Colour);

    }
}

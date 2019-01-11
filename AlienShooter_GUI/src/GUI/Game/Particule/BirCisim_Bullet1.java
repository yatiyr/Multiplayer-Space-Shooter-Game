package GUI.Game.Particule;

import GUI.GameValues;

public class BirCisim_Bullet1 extends Bullet{

    public BirCisim_Bullet1(double centerX,double centerY) {

        super(GameValues.BirCisim_Bullet1_Radius,GameValues.BirCisim_Bullet1_Damage,centerX,centerY,GameValues.BirCisim_Bullet1_DeltaX,GameValues.BirCisim_Bullet1_DeltaY);

        super.setFill(GameValues.BirCisim_Bullet1_Colour);

    }
}

package GUI.Game.Particule;

import GUI.GameValues;

//This pismaniye class upgrades weapon of our player
//and increases health.
public class Izmit_Pismaniye extends Bullet {

    public Izmit_Pismaniye(double centerX,double centerY) {

        super(GameValues.Pismaniye_Radius,GameValues.Pismaniye_Damage,centerX,centerY,GameValues.Pismaniye_DeltaX,GameValues.Pismaniye_DeltaY);

        super.setFill(GameValues.Pismaniye_Colour);

    }


}

package GUI.Game.Particule;

import GUI.GameValues;

//Little bullets fired from plekumats
public class Plekumat_Bullet extends Bullet {

    public Plekumat_Bullet(double centerX,double centerY) {

        super(GameValues.Plekumat_Bullet_Radius,GameValues.Plekumat_Bullet_Damage,centerX,centerY,GameValues.Plekumat_Bullet_DeltaX,GameValues.Plekumat_Bullet_DeltaY);

        super.setFill(GameValues.Plekumat_Bullet_Colour);

    }
}

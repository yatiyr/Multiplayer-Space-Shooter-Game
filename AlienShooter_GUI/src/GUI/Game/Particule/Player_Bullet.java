package GUI.Game.Particule;

import GUI.GameValues;

//This is the bullet of the player
public class Player_Bullet extends Bullet {

    public Player_Bullet(double centerX,double centerY) {


        super(GameValues.Player_Bullet_Radius,GameValues.Player_Weaponlvl0_Damage,centerX,centerY,GameValues.Player_Bullet_DeltaX,GameValues.Player_Bullet_DeltaY);

        super.setFill(GameValues.Player_Bullet_Colour);
    }

    public Player_Bullet(double radius,int damage,double centerX,double centerY,double deltaX,double deltaY) {

        super(radius,damage,centerX,centerY,deltaX,deltaY);
        super.setFill(GameValues.Player_Bullet_Colour);
    }
}

package GUI.Game.Particule;

import GUI.GameValues;

//This is the bullet of the player
public class Multiplayer_Opponent_Bullet extends Bullet {

    public Multiplayer_Opponent_Bullet(double centerX, double centerY) {


        super(GameValues.Player_Bullet_Radius,GameValues.Player_Weaponlvl0_Damage,centerX,centerY,GameValues.Player_Bullet_DeltaX,GameValues.Player_Bullet_DeltaY);

        super.setFill(GameValues.Multiplayer_Opponent_Bullet_Colour);
    }

    public Multiplayer_Opponent_Bullet(double radius, int damage, double centerX, double centerY, double deltaX, double deltaY) {

        super(radius,damage,centerX,centerY,deltaX,deltaY);
        super.setFill(GameValues.Multiplayer_Opponent_Bullet_Colour);
    }
}

package gui.game.particule;

import gui.GameValues;

//This is the bullet of the player
public class MultiplayerOpponentBullet extends Bullet {

    public MultiplayerOpponentBullet(double centerX, double centerY) {


        super(GameValues.PLAYER_BULLET_RADIUS,GameValues.PLAYER_WEAPON_LVL0_DAMAGE,centerX,centerY,GameValues.PLAYER_BULLET_DELTA_X,GameValues.PLAYER_BULLET_DELTA_Y);

        super.setFill(GameValues.MULTIPLAYER_OPPONENT_BULLET_COLOUR);
    }

    public MultiplayerOpponentBullet(double radius, int damage, double centerX, double centerY, double deltaX, double deltaY) {

        super(radius,damage,centerX,centerY,deltaX,deltaY);
        super.setFill(GameValues.MULTIPLAYER_OPPONENT_BULLET_COLOUR);
    }
}

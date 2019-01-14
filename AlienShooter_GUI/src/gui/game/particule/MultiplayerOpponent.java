package gui.game.particule;


import gui.game.GamePane;
import gui.GameValues;

//We control the player during the game
public class MultiplayerOpponent extends LiveBeing {


    private int upgradeLevel = 0;


    public void upgradeWeapon() {
        if(upgradeLevel >= 3) {
            upgradeLevel = 3;
        }
        else {
            upgradeLevel = upgradeLevel + 1;
        }
    }
    public void downgradeWeapon() {
        if(upgradeLevel <= 0) {
            upgradeLevel = 0;
        }
        else {
            upgradeLevel = upgradeLevel -1;
        }
    }
    public MultiplayerOpponent() {

        super(GameValues.PLAYER_MAX_HEALTH,GameValues.PLAYER_RADIUS);
        super.setCurhealth(GameValues.PLAYER_MAX_HEALTH);

        super.setFill(GameValues.MULTIPLAYER_OPPONENT_COLOUR);

        setCenterX(GameValues.PLAYER_SPAWN_CENTER_X);
        setCenterY(GameValues.PLAYER_SPAWN_CENTER_Y);
    }

    public MultiplayerOpponent(double centerX, double centerY) {


        super(GameValues.PLAYER_MAX_HEALTH,GameValues.PLAYER_RADIUS);
        super.setCurhealth(GameValues.PLAYER_MAX_HEALTH);

        super.setFill(GameValues.MULTIPLAYER_OPPONENT_COLOUR);

        setCenterX(centerX);
        setCenterY(centerY);

    }



    @Override
    public void todo() {

    }

    //This is what happens when user fires a bullet
    //It simply creates a bullet object which is
    //mainly derived from circle object
    public void fireBullet() {

        if(upgradeLevel == 0) {
            ((GamePane) getParent()).getEngine().addQueue(
                    new MultiplayerOpponentBullet(getCenterX(), getCenterY() - getRadius())
            );
        }
        else if(upgradeLevel == 1) {

            ((GamePane) getParent()).getEngine().addQueue(
                    new MultiplayerOpponentBullet(GameValues.PLAYER_BULLET_RADIUS,GameValues.PLAYER_WEAPON_LVL1_DAMAGE,getCenterX(), getCenterY() - getRadius(),GameValues.PLAYER_WEAPON_LVL1_DELTA_X + GameValues.PLAYER_WEAPON_LVL1_BULLET_SEPERATION,GameValues.PLAYER_WEAPON_LVL1_DELTA_Y)
            );
            ((GamePane) getParent()).getEngine().addQueue(
                    new MultiplayerOpponentBullet(GameValues.PLAYER_BULLET_RADIUS,GameValues.PLAYER_WEAPON_LVL1_DAMAGE,getCenterX(), getCenterY() - getRadius(),GameValues.PLAYER_WEAPON_LVL1_DELTA_X - GameValues.PLAYER_WEAPON_LVL1_BULLET_SEPERATION,GameValues.PLAYER_WEAPON_LVL1_DELTA_Y)
            );


        }
        else if(upgradeLevel == 2) {
            ((GamePane) getParent()).getEngine().addQueue(
                    new MultiplayerOpponentBullet(GameValues.PLAYER_BULLET_RADIUS,GameValues.PLAYER_WEAPON_LVL2_DAMAGE,getCenterX(), getCenterY() - getRadius(),GameValues.PLAYER_WEAPON_LVL2_DELTA_X + GameValues.PLAYER_WEAPON_LVL2_BULLET_SEPERATION,GameValues.PLAYER_WEAPON_LVL2_DELTA_Y)
            );
            ((GamePane) getParent()).getEngine().addQueue(
                    new MultiplayerOpponentBullet(GameValues.PLAYER_BULLET_RADIUS,GameValues.PLAYER_WEAPON_LVL2_DAMAGE,getCenterX(), getCenterY() - getRadius(),GameValues.PLAYER_WEAPON_LVL2_DELTA_X,GameValues.PLAYER_WEAPON_LVL2_DELTA_Y)
            );
            ((GamePane) getParent()).getEngine().addQueue(
                    new MultiplayerOpponentBullet(GameValues.PLAYER_BULLET_RADIUS,GameValues.PLAYER_WEAPON_LVL2_DAMAGE,getCenterX(), getCenterY() - getRadius(),GameValues.PLAYER_WEAPON_LVL2_DELTA_X - GameValues.PLAYER_WEAPON_LVL2_BULLET_SEPERATION,GameValues.PLAYER_WEAPON_LVL2_DELTA_Y)
            );

        }
        else if(upgradeLevel == 3) {

            ((GamePane) getParent()).getEngine().addQueue(
                    new MultiplayerOpponentBullet(GameValues.PLAYER_BULLET_RADIUS,GameValues.PLAYER_WEAPON_LVL3_DAMAGE,getCenterX(), getCenterY() - getRadius(),GameValues.PLAYER_WEAPON_LVL3_DELTA_X + 2*GameValues.PLAYER_WEAPON_LVL3_BULLET_SEPERATION,GameValues.PLAYER_WEAPON_LVL3_DELTA_Y)
            );
            ((GamePane) getParent()).getEngine().addQueue(
                    new MultiplayerOpponentBullet(GameValues.PLAYER_BULLET_RADIUS,GameValues.PLAYER_WEAPON_LVL3_DAMAGE,getCenterX(), getCenterY() - getRadius(),GameValues.PLAYER_WEAPON_LVL3_DELTA_X + GameValues.PLAYER_WEAPON_LVL3_BULLET_SEPERATION,GameValues.PLAYER_WEAPON_LVL3_DELTA_Y)
            );
            ((GamePane) getParent()).getEngine().addQueue(
                    new MultiplayerOpponentBullet(GameValues.PLAYER_BULLET_RADIUS,GameValues.PLAYER_WEAPON_LVL3_DAMAGE,getCenterX(), getCenterY() - getRadius(),GameValues.PLAYER_WEAPON_LVL3_DELTA_X,GameValues.PLAYER_WEAPON_LVL3_DELTA_Y)
            );
            ((GamePane) getParent()).getEngine().addQueue(
                    new MultiplayerOpponentBullet(GameValues.PLAYER_BULLET_RADIUS,GameValues.PLAYER_WEAPON_LVL3_DAMAGE,getCenterX(), getCenterY() - getRadius(),GameValues.PLAYER_WEAPON_LVL3_DELTA_X - GameValues.PLAYER_WEAPON_LVL3_BULLET_SEPERATION,GameValues.PLAYER_WEAPON_LVL3_DELTA_Y)
            );
            ((GamePane) getParent()).getEngine().addQueue(
                    new MultiplayerOpponentBullet(GameValues.PLAYER_BULLET_RADIUS,GameValues.PLAYER_WEAPON_LVL3_DAMAGE,getCenterX(), getCenterY() - getRadius(),GameValues.PLAYER_WEAPON_LVL3_DELTA_X - 2*GameValues.PLAYER_WEAPON_LVL3_BULLET_SEPERATION,GameValues.PLAYER_WEAPON_LVL3_DELTA_Y)
            );

        }

    }
}

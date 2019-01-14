package gui.game.particule;

import gui.GameValues;

//This pismaniye class upgrades weapon of our player
//and increases health.
public class IzmitPismaniye extends Bullet {

    public IzmitPismaniye(double centerX, double centerY) {

        super(GameValues.PISMANIYE_RADIUS,GameValues.PISMANIYE_DAMAGE,centerX,centerY,GameValues.PISMANIYE_DELTA_X,GameValues.PISMANIYE_DELTA_Y);

        super.setFill(GameValues.PISMANIYE_COLOUR);

    }


}

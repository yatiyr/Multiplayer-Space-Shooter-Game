package gui.game.particule;

import gui.GameValues;

public class BirCisimBullet1 extends Bullet{

    public BirCisimBullet1(double centerX, double centerY) {

        super(GameValues.BIRCISIM_BULLET1_RADIUS,GameValues.BIRCISIM_BULLET1_DAMAGE,centerX,centerY,GameValues.BIRCISIM_BULLET1_DELTA_X,GameValues.BIRCISIM_BULLET1_DELTA_Y);

        super.setFill(GameValues.BIRCISIM_BULLET1_COLOUR);

    }
}

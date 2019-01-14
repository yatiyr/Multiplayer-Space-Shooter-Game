package gui.game.particule;

import gui.GameValues;

public class BirCisimBullet2 extends Bullet{

    public BirCisimBullet2(double centerX, double centerY) {

        super(GameValues.BIRCISIM_BULLET2_RADIUS,GameValues.BIRCISIM_BULLET2_DAMAGE,centerX,centerY,GameValues.BIRCISIM_BULLET2_DELTA_X,GameValues.BIRCISIM_BULLET2_DELTA_Y);

        super.setFill(GameValues.BIRCISIM_BULLET2_COLOUR);

    }
}

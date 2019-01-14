package gui.game.particule;

import gui.GameValues;

//Little bullets fired from plekumats
public class PlekumatBullet extends Bullet {

    public PlekumatBullet(double centerX, double centerY) {

        super(GameValues.PLEKUMAT_BULLET_RADIUS,GameValues.PLEKUMAT_BULLET_DAMAGE,centerX,centerY,GameValues.PLEKUMAT_BULLET_DELTA_X,GameValues.PLEKUMAT_BULLET_DELTA_Y);

        super.setFill(GameValues.PLEKUMAT_BULLET_COLOUR);

    }
}

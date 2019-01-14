package gui.game.particule;

import gui.GameValues;

//Bullet of boss plekumat
public class BossPlekumatBullet extends Bullet{

    public BossPlekumatBullet(double centerX, double centerY) {

        super(GameValues.BOSS_PLEKUMAT_BULLET_RADIUS,GameValues.BOSS_PLEKUMAT_BULLET_DAMAGE,centerX,centerY,GameValues.BOSS_PLEKUMAT_BULLET_DELTA_X,GameValues.BOSS_PLEKUMAT_BULLET_DELTA_Y);

        super.setFill(GameValues.BOSS_PLEKUMAT_BULLET_COLOR);

    }

}

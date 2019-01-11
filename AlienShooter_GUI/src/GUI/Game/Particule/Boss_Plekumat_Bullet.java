package GUI.Game.Particule;

import GUI.GameValues;

//Bullet of boss plekumat
public class Boss_Plekumat_Bullet extends Bullet{

    public Boss_Plekumat_Bullet(double centerX,double centerY) {

        super(GameValues.Boss_Plekumat_Bullet_Radius,GameValues.Boss_Plekumat_Bullet_Damage,centerX,centerY,GameValues.Boss_Plekumat_Bullet_DeltaX,GameValues.Boss_Plekumat_Bullet_DeltaY);

        super.setFill(GameValues.Boss_Plekumat_Bullet_Color);

    }

}

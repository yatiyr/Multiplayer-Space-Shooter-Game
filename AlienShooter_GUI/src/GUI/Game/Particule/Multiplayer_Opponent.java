package GUI.Game.Particule;


import GUI.Game.Game_Pane;
import GUI.GameValues;

//We control the player during the game
public class Multiplayer_Opponent extends Live_Being {


    private int upgrade_level = 0;


    public void upgrade_weapon() {
        if(upgrade_level >= 3) {
            upgrade_level = 3;
        }
        else {
            upgrade_level = upgrade_level + 1;
        }
    }
    public void downgrade_weapon() {
        if(upgrade_level <= 0) {
            upgrade_level = 0;
        }
        else {
            upgrade_level = upgrade_level -1;
        }
    }
    public Multiplayer_Opponent() {

        super(GameValues.Player_MaxHealth,GameValues.Player_Radius);
        super.setCurhealth(GameValues.Player_MaxHealth);

        super.setFill(GameValues.Multiplayer_Opponent_Colour);

        setCenterX(GameValues.Player_Spawn_CenterX);
        setCenterY(GameValues.Player_Spawn_CenterY);
    }



    @Override
    public void todo() {

    }

    //This is what happens when user fires a bullet
    //It simply creates a bullet object which is
    //mainly derived from circle object
    public void fire_bullet() {

        if(upgrade_level == 0) {
            ((Game_Pane) getParent()).getEngine().add_queue(
                    new Player_Bullet(getCenterX(), getCenterY() - getRadius())
            );
        }
        else if(upgrade_level == 1) {

            ((Game_Pane) getParent()).getEngine().add_queue(
                    new Player_Bullet(GameValues.Player_Bullet_Radius,GameValues.Player_Weaponlvl1_Damage,getCenterX(), getCenterY() - getRadius(),GameValues.Player_Weaponlvl1_DeltaX + GameValues.Player_Weaponlvl1_Bullet_Seperation,GameValues.Player_Weaponlvl1_DeltaY)
            );
            ((Game_Pane) getParent()).getEngine().add_queue(
                    new Player_Bullet(GameValues.Player_Bullet_Radius,GameValues.Player_Weaponlvl1_Damage,getCenterX(), getCenterY() - getRadius(),GameValues.Player_Weaponlvl1_DeltaX - GameValues.Player_Weaponlvl1_Bullet_Seperation,GameValues.Player_Weaponlvl1_DeltaY)
            );


        }
        else if(upgrade_level == 2) {
            ((Game_Pane) getParent()).getEngine().add_queue(
                    new Player_Bullet(GameValues.Player_Bullet_Radius,GameValues.Player_Weaponlvl2_Damage,getCenterX(), getCenterY() - getRadius(),GameValues.Player_Weaponlvl2_DeltaX + GameValues.Player_Weaponlvl2_Bullet_Seperation,GameValues.Player_Weaponlvl2_DeltaY)
            );
            ((Game_Pane) getParent()).getEngine().add_queue(
                    new Player_Bullet(GameValues.Player_Bullet_Radius,GameValues.Player_Weaponlvl2_Damage,getCenterX(), getCenterY() - getRadius(),GameValues.Player_Weaponlvl2_DeltaX,GameValues.Player_Weaponlvl2_DeltaY)
            );
            ((Game_Pane) getParent()).getEngine().add_queue(
                    new Player_Bullet(GameValues.Player_Bullet_Radius,GameValues.Player_Weaponlvl2_Damage,getCenterX(), getCenterY() - getRadius(),GameValues.Player_Weaponlvl2_DeltaX - GameValues.Player_Weaponlvl2_Bullet_Seperation,GameValues.Player_Weaponlvl2_DeltaY)
            );

        }
        else if(upgrade_level == 3) {

            ((Game_Pane) getParent()).getEngine().add_queue(
                    new Player_Bullet(GameValues.Player_Bullet_Radius,GameValues.Player_Weaponlvl3_Damage,getCenterX(), getCenterY() - getRadius(),GameValues.Player_Weaponlvl3_DeltaX + 2*GameValues.Player_Weaponlvl3_Bullet_Seperation,GameValues.Player_Weaponlvl3_DeltaY)
            );
            ((Game_Pane) getParent()).getEngine().add_queue(
                    new Player_Bullet(GameValues.Player_Bullet_Radius,GameValues.Player_Weaponlvl3_Damage,getCenterX(), getCenterY() - getRadius(),GameValues.Player_Weaponlvl3_DeltaX + GameValues.Player_Weaponlvl3_Bullet_Seperation,GameValues.Player_Weaponlvl3_DeltaY)
            );
            ((Game_Pane) getParent()).getEngine().add_queue(
                    new Player_Bullet(GameValues.Player_Bullet_Radius,GameValues.Player_Weaponlvl3_Damage,getCenterX(), getCenterY() - getRadius(),GameValues.Player_Weaponlvl3_DeltaX,GameValues.Player_Weaponlvl3_DeltaY)
            );
            ((Game_Pane) getParent()).getEngine().add_queue(
                    new Player_Bullet(GameValues.Player_Bullet_Radius,GameValues.Player_Weaponlvl3_Damage,getCenterX(), getCenterY() - getRadius(),GameValues.Player_Weaponlvl3_DeltaX - GameValues.Player_Weaponlvl3_Bullet_Seperation,GameValues.Player_Weaponlvl3_DeltaY)
            );
            ((Game_Pane) getParent()).getEngine().add_queue(
                    new Player_Bullet(GameValues.Player_Bullet_Radius,GameValues.Player_Weaponlvl3_Damage,getCenterX(), getCenterY() - getRadius(),GameValues.Player_Weaponlvl3_DeltaX - 2*GameValues.Player_Weaponlvl3_Bullet_Seperation,GameValues.Player_Weaponlvl3_DeltaY)
            );

        }

    }
}

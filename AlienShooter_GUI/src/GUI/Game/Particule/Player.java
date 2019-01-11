package GUI.Game.Particule;


import GUI.Game.Game_Pane;
import GUI.GameValues;

//We control the player during the game
public class Player extends Live_Being {


    //These values determine which direction our player should move
    private boolean left = false;
    private boolean right = false;
    private boolean up = false;
    private boolean down = false;

    //These values determine the drag speed of player
    private double left_drag = 0;
    private double right_drag = 0;
    private double up_drag = 0;
    private double down_drag = 0;

    //In oder to prevent spraying(bursting) lots of fire bullets, this
    //value has been added
    private boolean one_tap_fire = true;
    private int upgrade_level = 0;

    public boolean get_one_tap_fire() {

        return one_tap_fire;
    }

    public void set_one_tap_fire(boolean bool) {

        this.one_tap_fire = bool;

    }

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
    public Player() {

        super(GameValues.Player_MaxHealth,GameValues.Player_Radius);
        super.setCurhealth(GameValues.Player_MaxHealth);

        super.setFill(GameValues.Player_Colour);

        setCenterX(GameValues.Player_Spawn_CenterX);
        setCenterY(GameValues.Player_Spawn_CenterY);
    }

    public void move_set(int direction) {

        //left movement
        if (direction == 0) {
            left = true;
        }
        //right movement
        else if (direction == 1) {
            right = true;
        }
        //up movement
        else if (direction == 2) {
            up = true;
        }
        //down movement
        else if (direction == 3) {
            down = true;
        }
    }

    public void stop_set(int a) {

        //left movement
        if (a == 0) {
            left = false;
        }
        //right movement
        else if (a == 1) {
            right = false;
        }
        //up movement
        else if (a == 2) {
            up = false;
        }
        //down movement
        else if (a == 3) {
            down = false;
        }
    }


    //In control of player's moves, I added drag speeds to ensure
    //that the moving physics are similar to space enviroment

    private void change_drag() {

        if((left || right)&&(!(left && right))) {
            if(left) {

                if(left_drag >= 0 && right_drag == 0) {

                    if(left_drag <= GameValues.max_drag) {
                        left_drag = left_drag + GameValues.drag_change_rate;
                        if (getCenterX() - getRadius() == 0) {
                            left_drag = 0;
                        }

                    }
                    else
                        left_drag = 3;

                }
                if(right_drag > 0) {
                    right_drag = right_drag - GameValues.drag_change_rate;
                    if(right_drag <= 0)
                        right_drag = 0;
                }

            }
            if(right) {

                if (right_drag >= 0 && left_drag == 0) {

                    if(right_drag <= GameValues.max_drag) {
                        right_drag = right_drag + GameValues.drag_change_rate;
                        if (getCenterX() + getRadius() == GameValues.Game_Pane_Width) {
                            right_drag = 0;
                        }

                    }
                    else
                        right_drag = GameValues.max_drag;
                }
                if(left_drag > 0) {

                    left_drag = left_drag - GameValues.drag_change_rate;
                    if(left_drag <= 0) {
                        left_drag = 0;
                    }
                }
            }
        }


        if((up || down)&&(!(up&&down))) {
            if(up) {

                if(up_drag >= 0) {

                    if(up_drag <= GameValues.max_drag) {

                        up_drag = up_drag + GameValues.drag_change_rate;
                        if (getCenterY() - getRadius() == 0) {
                            up_drag = 0;
                        }
                    }
                    else
                        up_drag = GameValues.max_drag;
                }
                if(down_drag > 0) {
                    down_drag = down_drag -GameValues.drag_change_rate;
                    if(down_drag <= 0) {
                        down_drag = 0;
                    }
                }
            }
            if(down) {

                if (down_drag >= 0) {

                    if(down_drag <= GameValues.max_drag) {

                        down_drag = down_drag + GameValues.drag_change_rate;
                        if (getCenterY() + getRadius() == GameValues.Game_Pane_Height ) {
                            down_drag = 0;
                        }

                    }
                    else {
                        down_drag = GameValues.max_drag;
                    }
                }
                if(up_drag > 0) {

                    up_drag = up_drag - GameValues.drag_change_rate;
                    if(up_drag <= 0) {
                        up_drag = 0;
                    }
                }
            }
        }
    }

    private void move() {

        if(left_drag > 0) {

            if (getCenterX() - getRadius() < left_drag) {
                setCenterX(getRadius());
            }
            else {
                setCenterX(getCenterX() - left_drag);
            }
        }
        if (right_drag > 0) {

            if (getCenterX() + getRadius() < GameValues.Game_Pane_Width - right_drag) {
                setCenterX(getCenterX() + right_drag);
            }
            else {
                setCenterX(GameValues.Game_Pane_Width - getRadius());
            }
        }


        if(up_drag > 0) {

            if (getCenterY() - getRadius() < up_drag) {
                setCenterY(getRadius());
            }
            else {
                setCenterY(getCenterY() - up_drag);
            }
        }

        if (down_drag > 0) {

            if (getCenterY() + getRadius() < GameValues.Game_Pane_Height - down_drag) {
                setCenterY(getCenterY() + down_drag);
            }
            else {
                setCenterY(GameValues.Game_Pane_Height - getRadius());
            }
        }

    }

    @Override
    public void todo() {
        change_drag();
        move();
    }

    //This is what happens when user fires a bullet
    //It simply creates a bullet object which is
    //mainly derived from circle object
    public void fire_bullet() {

        //This feature applies the law of action-reaction(Newton's third law)
        //when a user fires upwards, a virtual force directed downwards will be
        //applied to the player.This give a more space game experience for players.

        if(down_drag >= 0 && up_drag == 0) {
            down_drag = down_drag + GameValues.drag_value_hit_by_enemy;
        }
        else if(up_drag < GameValues.drag_value_hit_by_enemy && up_drag > 0) {
            up_drag = 0;
        }
        else if(up_drag >= GameValues.drag_value_hit_by_enemy) {
            up_drag = up_drag - GameValues.drag_value_hit_by_enemy;
        }

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

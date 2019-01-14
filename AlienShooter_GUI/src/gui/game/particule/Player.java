package gui.game.particule;


import gui.game.GamePane;
import gui.GameValues;

//We control the player during the game
public class Player extends LiveBeing {


    //These values determine which direction our player should move
    private boolean left = false;
    private boolean right = false;
    private boolean up = false;
    private boolean down = false;

    //These values determine the drag speed of player
    private double leftDrag = 0;
    private double rightDrag = 0;
    private double upDrag = 0;
    private double downDrag = 0;

    //In oder to prevent spraying(bursting) lots of fire bullets, this
    //value has been added
    private boolean oneTapFire = true;
    private int upgradeLevel = 0;

    public boolean getOneTapFire() {

        return oneTapFire;
    }

    public void setOneTapFire(boolean bool) {

        this.oneTapFire = bool;

    }

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
    public Player() {

        super(GameValues.PLAYER_MAX_HEALTH,GameValues.PLAYER_RADIUS);
        super.setCurhealth(GameValues.PLAYER_MAX_HEALTH);

        super.setFill(GameValues.PLAYER_COLOUR);

        setCenterX(GameValues.PLAYER_SPAWN_CENTER_X);
        setCenterY(GameValues.PLAYER_SPAWN_CENTER_Y);
    }

    public Player(double centerX, double centerY) {


        super(GameValues.PLAYER_MAX_HEALTH,GameValues.PLAYER_RADIUS);
        super.setCurhealth(GameValues.PLAYER_MAX_HEALTH);

        super.setFill(GameValues.PLAYER_COLOUR);

        setCenterX(centerX);
        setCenterY(centerY);

    }

    public void moveSet(int direction) {

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

    public void stopSet(int a) {

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

    private void changeDrag() {

        if((left || right)&&(!(left && right))) {
            if(left) {

                if(leftDrag >= 0 && rightDrag == 0) {

                    if(leftDrag <= GameValues.MAX_DRAG) {
                        leftDrag = leftDrag + GameValues.DRAG_CHANGE_RATE;
                        if (getCenterX() - getRadius() == 0) {
                            leftDrag = 0;
                        }

                    }
                    else
                        leftDrag = 3;

                }
                if(rightDrag > 0) {
                    rightDrag = rightDrag - GameValues.DRAG_CHANGE_RATE;
                    if(rightDrag <= 0)
                        rightDrag = 0;
                }

            }
            if(right) {

                if (rightDrag >= 0 && leftDrag == 0) {

                    if(rightDrag <= GameValues.MAX_DRAG) {
                        rightDrag = rightDrag + GameValues.DRAG_CHANGE_RATE;
                        if (getCenterX() + getRadius() == GameValues.GAME_PANE_WIDTH) {
                            rightDrag = 0;
                        }

                    }
                    else
                        rightDrag = GameValues.MAX_DRAG;
                }
                if(leftDrag > 0) {

                    leftDrag = leftDrag - GameValues.DRAG_CHANGE_RATE;
                    if(leftDrag <= 0) {
                        leftDrag = 0;
                    }
                }
            }
        }


        if((up || down)&&(!(up&&down))) {
            if(up) {

                if(upDrag >= 0) {

                    if(upDrag <= GameValues.MAX_DRAG) {

                        upDrag = upDrag + GameValues.DRAG_CHANGE_RATE;
                        if (getCenterY() - getRadius() == 0) {
                            upDrag = 0;
                        }
                    }
                    else
                        upDrag = GameValues.MAX_DRAG;
                }
                if(downDrag > 0) {
                    downDrag = downDrag -GameValues.DRAG_CHANGE_RATE;
                    if(downDrag <= 0) {
                        downDrag = 0;
                    }
                }
            }
            if(down) {

                if (downDrag >= 0) {

                    if(downDrag <= GameValues.MAX_DRAG) {

                        downDrag = downDrag + GameValues.DRAG_CHANGE_RATE;
                        if (getCenterY() + getRadius() == GameValues.GAME_PANE_HEIGHT) {
                            downDrag = 0;
                        }

                    }
                    else {
                        downDrag = GameValues.MAX_DRAG;
                    }
                }
                if(upDrag > 0) {

                    upDrag = upDrag - GameValues.DRAG_CHANGE_RATE;
                    if(upDrag <= 0) {
                        upDrag = 0;
                    }
                }
            }
        }
    }

    private void move() {

        if(leftDrag > 0) {

            if (getCenterX() - getRadius() < leftDrag) {
                setCenterX(getRadius());
            }
            else {
                setCenterX(getCenterX() - leftDrag);
            }
        }
        if (rightDrag > 0) {

            if (getCenterX() + getRadius() < GameValues.GAME_PANE_WIDTH - rightDrag) {
                setCenterX(getCenterX() + rightDrag);
            }
            else {
                setCenterX(GameValues.GAME_PANE_WIDTH - getRadius());
            }
        }


        if(upDrag > 0) {

            if (getCenterY() - getRadius() < upDrag) {
                setCenterY(getRadius());
            }
            else {
                setCenterY(getCenterY() - upDrag);
            }
        }

        if (downDrag > 0) {

            if (getCenterY() + getRadius() < GameValues.GAME_PANE_HEIGHT - downDrag) {
                setCenterY(getCenterY() + downDrag);
            }
            else {
                setCenterY(GameValues.GAME_PANE_HEIGHT - getRadius());
            }
        }

    }

    @Override
    public void todo() {
        changeDrag();
        move();
    }

    //This is what happens when user fires a bullet
    //It simply creates a bullet object which is
    //mainly derived from circle object
    public void fireBullet() {

        //This feature applies the law of action-reaction(Newton's third law)
        //when a user fires upwards, a virtual force directed downwards will be
        //applied to the player.This give a more space game experience for players.

        if(downDrag >= 0 && upDrag == 0) {
            downDrag = downDrag + GameValues.DRAG_VALUE_HIT_BY_ENEMY;
        }
        else if(upDrag < GameValues.DRAG_VALUE_HIT_BY_ENEMY && upDrag > 0) {
            upDrag = 0;
        }
        else if(upDrag >= GameValues.DRAG_VALUE_HIT_BY_ENEMY) {
            upDrag = upDrag - GameValues.DRAG_VALUE_HIT_BY_ENEMY;
        }

        if(upgradeLevel == 0) {
            ((GamePane) getParent()).getEngine().addQueue(
                    new PlayerBullet(getCenterX(), getCenterY() - getRadius())
            );
        }
        else if(upgradeLevel == 1) {

            ((GamePane) getParent()).getEngine().addQueue(
                    new PlayerBullet(GameValues.PLAYER_BULLET_RADIUS,GameValues.PLAYER_WEAPON_LVL1_DAMAGE,getCenterX(), getCenterY() - getRadius(),GameValues.PLAYER_WEAPON_LVL1_DELTA_X + GameValues.PLAYER_WEAPON_LVL1_BULLET_SEPERATION,GameValues.PLAYER_WEAPON_LVL1_DELTA_Y)
            );
            ((GamePane) getParent()).getEngine().addQueue(
                    new PlayerBullet(GameValues.PLAYER_BULLET_RADIUS,GameValues.PLAYER_WEAPON_LVL1_DAMAGE,getCenterX(), getCenterY() - getRadius(),GameValues.PLAYER_WEAPON_LVL1_DELTA_X - GameValues.PLAYER_WEAPON_LVL1_BULLET_SEPERATION,GameValues.PLAYER_WEAPON_LVL1_DELTA_Y)
            );


        }
        else if(upgradeLevel == 2) {
            ((GamePane) getParent()).getEngine().addQueue(
                    new PlayerBullet(GameValues.PLAYER_BULLET_RADIUS,GameValues.PLAYER_WEAPON_LVL2_DAMAGE,getCenterX(), getCenterY() - getRadius(),GameValues.PLAYER_WEAPON_LVL2_DELTA_X + GameValues.PLAYER_WEAPON_LVL2_BULLET_SEPERATION,GameValues.PLAYER_WEAPON_LVL2_DELTA_Y)
            );
            ((GamePane) getParent()).getEngine().addQueue(
                    new PlayerBullet(GameValues.PLAYER_BULLET_RADIUS,GameValues.PLAYER_WEAPON_LVL2_DAMAGE,getCenterX(), getCenterY() - getRadius(),GameValues.PLAYER_WEAPON_LVL2_DELTA_X,GameValues.PLAYER_WEAPON_LVL2_DELTA_Y)
            );
            ((GamePane) getParent()).getEngine().addQueue(
                    new PlayerBullet(GameValues.PLAYER_BULLET_RADIUS,GameValues.PLAYER_WEAPON_LVL2_DAMAGE,getCenterX(), getCenterY() - getRadius(),GameValues.PLAYER_WEAPON_LVL2_DELTA_X - GameValues.PLAYER_WEAPON_LVL2_BULLET_SEPERATION,GameValues.PLAYER_WEAPON_LVL2_DELTA_Y)
            );

        }
        else if(upgradeLevel == 3) {

            ((GamePane) getParent()).getEngine().addQueue(
                    new PlayerBullet(GameValues.PLAYER_BULLET_RADIUS,GameValues.PLAYER_WEAPON_LVL3_DAMAGE,getCenterX(), getCenterY() - getRadius(),GameValues.PLAYER_WEAPON_LVL3_DELTA_X + 2*GameValues.PLAYER_WEAPON_LVL3_BULLET_SEPERATION,GameValues.PLAYER_WEAPON_LVL3_DELTA_Y)
            );
            ((GamePane) getParent()).getEngine().addQueue(
                    new PlayerBullet(GameValues.PLAYER_BULLET_RADIUS,GameValues.PLAYER_WEAPON_LVL3_DAMAGE,getCenterX(), getCenterY() - getRadius(),GameValues.PLAYER_WEAPON_LVL3_DELTA_X + GameValues.PLAYER_WEAPON_LVL3_BULLET_SEPERATION,GameValues.PLAYER_WEAPON_LVL3_DELTA_Y)
            );
            ((GamePane) getParent()).getEngine().addQueue(
                    new PlayerBullet(GameValues.PLAYER_BULLET_RADIUS,GameValues.PLAYER_WEAPON_LVL3_DAMAGE,getCenterX(), getCenterY() - getRadius(),GameValues.PLAYER_WEAPON_LVL3_DELTA_X,GameValues.PLAYER_WEAPON_LVL3_DELTA_Y)
            );
            ((GamePane) getParent()).getEngine().addQueue(
                    new PlayerBullet(GameValues.PLAYER_BULLET_RADIUS,GameValues.PLAYER_WEAPON_LVL3_DAMAGE,getCenterX(), getCenterY() - getRadius(),GameValues.PLAYER_WEAPON_LVL3_DELTA_X - GameValues.PLAYER_WEAPON_LVL3_BULLET_SEPERATION,GameValues.PLAYER_WEAPON_LVL3_DELTA_Y)
            );
            ((GamePane) getParent()).getEngine().addQueue(
                    new PlayerBullet(GameValues.PLAYER_BULLET_RADIUS,GameValues.PLAYER_WEAPON_LVL3_DAMAGE,getCenterX(), getCenterY() - getRadius(),GameValues.PLAYER_WEAPON_LVL3_DELTA_X - 2*GameValues.PLAYER_WEAPON_LVL3_BULLET_SEPERATION,GameValues.PLAYER_WEAPON_LVL3_DELTA_Y)
            );

        }

    }
}

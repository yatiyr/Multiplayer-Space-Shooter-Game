package GUI.Game.Particule;

import GUI.GameValues;

import java.util.Random;

/*
This is the abstract class for Plekumats and Boss_Plekumats
 */
public abstract class Alien extends Live_Being{

    //By default, aliens can only move horizontally
    private double deltaX;
    private int value;

    //This determines the initial direction of alien
    private boolean direction;


    public Alien(int health,int value,double radius,double deltaX,boolean direction) {
        super(health,radius);

        Random randx = new Random();
        double randomX = getRadius() + (GameValues.Game_Pane_Width - getRadius()) * randx.nextDouble();

        Random randy = new Random();
        double randomY = getRadius() + (GameValues.SpawnBorder_Height - getRadius()) * randy.nextDouble();

        setCenterX(randomX);
        setCenterY(randomY);

        this.value = value;
        this.deltaX = deltaX;
        this.direction = direction;

    }

    public int getValue() {
        return value;
    }

    /*
    In each tick, aliens move horizontaly and if they touch to the sides
    then they change their direction to the other side
     */
    void move() {
        if(!direction) {
            if(getCenterX()-getRadius() >= deltaX) {
                setCenterX(getCenterX()-deltaX);
            }
            else {
                direction = true;
            }
        }
        else {
            if(getCenterX() + getRadius() < GameValues.Game_Pane_Width - deltaX) {
                setCenterX(getCenterX() + deltaX);
            }
            else {
                direction = false;

            }
        }
    }
}

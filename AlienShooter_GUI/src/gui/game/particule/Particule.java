package gui.game.particule;

import gui.GameValues;
import javafx.scene.shape.Circle;




/*
This is the main abstract class in the game
everything comes from this particule class.
It derives from a circle and it has a radius as attribute

todo() method is the thing which classes derived from a particule should do in every tick of the timeline

 */

public abstract class Particule extends Circle {

    private double radius = GameValues.DEFAULT_PARTICULE_RADIUS;

    public Particule() {

        super.setRadius(radius);
    }

    public Particule(double radius) {

        this.radius = radius;

        super.setRadius(radius);
    }

    public abstract void todo();

}

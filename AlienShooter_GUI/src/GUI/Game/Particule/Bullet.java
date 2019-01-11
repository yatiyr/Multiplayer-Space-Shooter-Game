package GUI.Game.Particule;


//Bullets are the particules which are fired by plekumats,boss_plekumats and players
public class Bullet extends Particule{

    private double deltaX;
    private double deltaY;
    private int damage;

    public Bullet(double radius,int damage,double centerX,double centerY,double deltaX,double deltaY) {
        super(radius);
        this.damage = damage;
        setCenterX(centerX);
        setCenterY(centerY);
        this.deltaX = deltaX;
        this.deltaY = deltaY;
    }

    void setDeltaX(double x) {

        this.deltaX = x;
    }

    void setDeltaY(double y) {
        this.deltaY = y;
    }

    public int getDamage() {
        return this.damage;
    }

    public void move() {
        setCenterX(getCenterX() - deltaX);
        setCenterY(getCenterY() - deltaY);
    }

    @Override
    public void todo() {
        move();
    }
}

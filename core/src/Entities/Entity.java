package Entities;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Entity extends Actor {
    private Rectangle hitbox;
    private float x;
    private float y;
    private float width;
    private float height;

    public Entity(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        this.hitbox = new Rectangle(x, y, width, height);
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
        this.hitbox.setPosition(x, y);
    }

    public void setSize(float width, float height) {
        this.width = width;
        this.height = height;
        this.hitbox.setSize(width, height);
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public static boolean isColliding(Entity e1, Entity e2) {
        return e1.hitbox.overlaps(e2.hitbox);
    }
    public static boolean isColliding(Entity e1, Rectangle e2){
        return e1.hitbox.overlaps(e2);
    }
    public static boolean isColliding(Rectangle e1, Rectangle e2){
        return e1.overlaps(e2);
    }
    public static boolean isColliding(Rectangle e1, Entity e2){
        return e1.overlaps(e2.hitbox);
    }
    @Override
    public void setX(float x) {
        this.x = x;
    }

    @Override
    public void setY(float y) {
        this.y = y;
    }

    public Rectangle getHitbox() {
        return hitbox;
    }
}

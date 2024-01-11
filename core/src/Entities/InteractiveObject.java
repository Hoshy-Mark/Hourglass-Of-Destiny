package Entities;

import com.badlogic.gdx.math.Rectangle;

public class InteractiveObject extends Entity{

    private Rectangle collisionRect;
    private Rectangle interactionRect;
    public InteractiveObject(float x, float y, float width, float height, String type) {
        super(x, y, width, height);
        collisionRect = new Rectangle(x, y, width, height);
        interactionRect = new Rectangle(x - 1, y- 1, width + 10, height + 10);
    }
    public Rectangle getInteractionRect() {
        return interactionRect;
    }

    public Rectangle getCollisionRect() {
        return collisionRect;
    }
}

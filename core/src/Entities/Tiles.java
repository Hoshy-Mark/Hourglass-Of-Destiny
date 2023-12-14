package Entities;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class Tiles {
    private boolean accessible;
    private Rectangle hitbox;
    private float x;
    private float y;
    private float width;
    private float height;
    private TextureRegion sprite;
    private String type;

    public Tiles(float x, float y, float width, float height, TextureRegion sprite, String type) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.hitbox = new Rectangle(x, y, width, height);
        this.type = type;

        if(type == "floor"){
            this.accessible = true;
        }
        else if(type == "wall"){
            this.accessible = false;
        }
    }

    public boolean isAccessible() {
        return accessible;
    }

    public String getType() {
        return type;
    }

    public Rectangle getHitbox() {
        return hitbox;
    }
}
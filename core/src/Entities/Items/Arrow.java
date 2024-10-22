package Entities.Items;

import Entities.Item;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Arrow extends Item {
    private int ammunition = 5;
    public Arrow(float x, float y, float width, float height, TextureRegion sprite, int ammunition) {
        super(x, y, width, height, sprite);
        this.ammunition = ammunition;
        setScale(0.6f);
    }
    public Arrow(float x, float y, float width, float height, TextureRegion sprite) {
        super(x, y, width, height, sprite);
        setScale(0.8f);
    }

    public int getAmmunition() {
        return ammunition;
    }

    public void setAmmunition(int ammunition) {
        this.ammunition = ammunition;
    }
}

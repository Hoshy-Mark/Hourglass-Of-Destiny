package Entities.Items;

import Entities.Item;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Ring extends Item {
    private String type;
    private int improve;
    public Ring(float x, float y, float width, float height, TextureRegion sprite, String Type) {
        super(x, y, width, height, sprite);

        this.type = Type;

        if(type == "life"){
            improve = 10;
        }

        if(type == "speed"){
            improve = 5;
        }
    }

    public int getImprove() {
        return improve;
    }

    public String getType() {
        return type;
    }
}

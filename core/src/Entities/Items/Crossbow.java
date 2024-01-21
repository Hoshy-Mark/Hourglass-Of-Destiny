package Entities.Items;

import Entities.Item;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class Crossbow extends Item {
    public Crossbow(float x, float y, float width, float height, Array<TextureRegion> sprites) {
        super(x, y, width, height, sprites, 4);
        setScale(0.8f);
    }
}

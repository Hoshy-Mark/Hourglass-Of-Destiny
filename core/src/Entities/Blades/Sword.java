package Entities.Blades;

import Entities.Blade;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class Sword extends Blade {
    public Sword(float x, float y, float width, float height, Array<TextureRegion> blades) {
        super(x, y, width, height, blades);
    }
}

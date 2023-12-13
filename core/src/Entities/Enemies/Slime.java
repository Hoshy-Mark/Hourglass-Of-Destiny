package Entities.Enemies;

import Entities.Enemy;
import Entities.Player;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class Slime extends Enemy {
    public Slime(float x, float y, float width, float height, Array<TextureRegion> sprites, Player player) {
        super(x, y, width, height, sprites, player);
        setLife(15);
        setDamage(1);
        setSpeed(20);
        setMaxViewDistance(20);
    }
}

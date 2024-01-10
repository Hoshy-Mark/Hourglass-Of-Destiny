package Entities.Enemies;

import Entities.Enemy;
import Entities.Player;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class Bat extends Enemy {
    public Bat(float x, float y, float width, float height, Array<TextureRegion> sprites, Player player) {
        super(x, y, width, height, sprites, player);
        setLife(10);
        setDamage(2);
        setSpeed(20);
        setMaxViewDistance(120);
    }
}

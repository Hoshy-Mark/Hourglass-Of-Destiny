package Entities.Guns;

import Entities.Bullet;
import Entities.Gun;
import Graphics.LoadSprites;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Crossbow extends Gun {
    List<Bullet> bullets = new ArrayList<Bullet>();
    HashMap<String, TextureRegion> sprites;
    LoadSprites loader;

    public Crossbow(float x, float y, float width, float height, Array<TextureRegion> sprites, LoadSprites loader) {
        super(x, y, width, height, sprites);
        this.loader = loader;
        this.sprites = new HashMap<>();
        spritesBullet();
    }

    public Bullet shoot() {
        Vector2 bulletPosition = new Vector2(this.getX(), this.getY());

        Vector2 bulletVelocity;

        if ("right".equals(this.getDirectionName())) {
            bulletVelocity = new Vector2(300, 0); // velocidade da bullet indo para a direita
        } else if ("left".equals(this.getDirectionName())) {
            bulletVelocity = new Vector2(-300, 0); // velocidade da bullet indo para a esquerda
        } else if ("up".equals(this.getDirectionName())) {
            bulletVelocity = new Vector2(0, 300); // velocidade da bullet indo para cima
        } else if ("down".equals(this.getDirectionName())) {
            bulletVelocity = new Vector2(0, -300); // velocidade da bullet indo para baixo
        } else if("upRight".equals(this.getDirectionName())) {
            bulletVelocity = new Vector2(300, 300); // velocidade da bullet indo para cima-direita
        } else if("upLeft".equals(this.getDirectionName())) {
            bulletVelocity = new Vector2(-300, 300); // velocidade da bullet indo para cima-esquerda
        } else if("downRight".equals(this.getDirectionName())) {
            bulletVelocity = new Vector2(300, -300); // velocidade da bullet indo para baixo-direita
        } else if("downLeft".equals(this.getDirectionName())) {
            bulletVelocity = new Vector2(-300, -300); // velocidade da bullet indo para baixo-esquerda
        } else {
            throw new IllegalArgumentException("Direção desconhecida: " + this.getDirectionName());
        }
        bullets.add(new Bullet(bulletPosition, bulletVelocity, sprites));
        return new Bullet(bulletPosition, bulletVelocity, sprites);
    }

    public void spritesBullet(){
        sprites.put("down",loader.getSprites("Arrows").get(4));
        sprites.put("up",loader.getSprites("Arrows").get(5));
        sprites.put("right",loader.getSprites("Arrows").get(6));
        sprites.put("left",loader.getSprites("Arrows").get(7));
        sprites.put("up-right",loader.getSprites("Arrows").get(1));
        sprites.put("down-right",loader.getSprites("Arrows").get(3));
        sprites.put("up-left",loader.getSprites("Arrows").get(0));
        sprites.put("down-left",loader.getSprites("Arrows").get(2));
    }

}
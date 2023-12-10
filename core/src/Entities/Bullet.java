package Entities;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import java.util.HashMap;

public class Bullet {
    Vector2 position;
    Vector2 velocity;
    TextureRegion currentSprite;
    HashMap<String, TextureRegion> sprites;
    int damage = 5;

    public Bullet(Vector2 position, Vector2 velocity, HashMap<String, TextureRegion> sprites) {
        this.position = position;
        this.velocity = velocity;
        this.sprites = sprites;

        updateSpriteBasedOnVelocity();
    }

    public void update(float delta) {
        position.add(velocity.x * delta, velocity.y * delta);

        updateSpriteBasedOnVelocity();
    }

    private void updateSpriteBasedOnVelocity() {
        if (velocity.x > 0) {
            if (velocity.y > 0) {
                currentSprite = sprites.get("up-right");
            } else if (velocity.y < 0) {
                currentSprite = sprites.get("down-right");
            } else {
                currentSprite = sprites.get("right");
            }
        } else if (velocity.x < 0) {
            if (velocity.y > 0) {
                currentSprite = sprites.get("up-left");
            } else if (velocity.y < 0) {
                currentSprite = sprites.get("down-left");
            } else {
                currentSprite = sprites.get("left");
            }
        } else {
            if (velocity.y > 0) {
                currentSprite = sprites.get("up");
            } else if (velocity.y < 0) {
                currentSprite = sprites.get("down");
            } else {
                // Não muda de sprite se a velocidade for (0, 0)
            }
        }
    }
    public void draw(Batch batch) {
        batch.draw(currentSprite, position.x, position.y);
    }
    public Vector2 getPosition() {
        return position;
    }

    public TextureRegion getCurrentSprite() {
        return currentSprite;
    }
    public int getDamage() {
        return damage;
    }
    public void setDamage(int damage) {
        this.damage = damage;
    }

}
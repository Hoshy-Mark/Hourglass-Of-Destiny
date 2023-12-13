package Entities;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import java.util.HashMap;

public class Bullet {
    Vector2 position; // Define sua posição
    Vector2 velocity; // Define a velocidade
    TextureRegion currentSprite; // Define seu Sprite atual
    HashMap<String, TextureRegion> sprites; // Todos os Sprites de bala
    int damage = 5; // O Dano da Bala

    public Bullet(Vector2 position, Vector2 velocity, HashMap<String, TextureRegion> sprites) {
        this.position = position;
        this.velocity = velocity;
        this.sprites = sprites;

        updateSpriteBasedOnVelocity();
    }

    // Define como a bala deve ser atualizada a cada quadro do jogo (delta tempo).
    // A posição da bala é alterada com base em sua velocidade e no tempo passado desde o último quadro.
    // Posteriormente atualiza o sprite com base na nova velocidade.
    public void update(float delta) {
        position.add(velocity.x * delta, velocity.y * delta);

        updateSpriteBasedOnVelocity();
    }

    // Define os sprites da bala com base em sua velocidade.
    // Dependendo de para onde a bala está se movendo (esquerda, direita, para cima, para baixo ou uma combinação deles),
    // um sprite diferente é selecionado.
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

    // Método para renderizar a bala na tela, desenha o sprite atual na posição atual da bala.
    public void draw(Batch batch) {
        batch.draw(currentSprite, position.x, position.y);
    }

    // Métodos getters e setters para obter/alterar a posição da bala, o sprite e o dano da bala.
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
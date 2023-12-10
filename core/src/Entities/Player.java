package Entities;

import Graphics.LoadSprites;
import Graphics.Map;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import Entities.Guns.*;

import java.util.ArrayList;

public class Player extends Entity {
    private Array<TextureRegion> sprites;
    private final float animationDuration = 0.15f;
    private final int tileSize = 16;
    private Array<TextureRegion> RightSprites = new  Array<TextureRegion>();
    private Array<TextureRegion> LeftSprites = new  Array<TextureRegion>();
    private Array<TextureRegion> DamageSprites = new  Array<TextureRegion>();
    private Animation<TextureRegion> animationRight = new Animation<TextureRegion>(0.1f, RightSprites);
    private Animation<TextureRegion> animationLeft = new Animation<TextureRegion>(0.1f, LeftSprites);
    private Animation<TextureRegion> currentAnimation = animationRight;
    private Sprite SpriteCurrent;
    private float animationTimer = 0;
    private float speed = 60; //Quantas unidades se movera por segundo
    private float speedBoost = 120; // velocidade amplificada ao pressionar Shift
    private boolean isDamaged = false;
    private int damage = 4;
    private float stateTime = 5;
    private double life = 100, maxLife = 100;
    private String lifeString; // String do atributo vida
    private float enemyStopTime = 1;
    private Sprite previousSprite;
    private Crossbow crossbowGun;
    private Entities.Items.Crossbow crossbowItem;
    private String ammunitionString;
    private float damagedDuration = 0.3f;
    private boolean playerDamaged = false;

    private int Ammunition = 50;

    private LoadSprites loader;
    private float shootElapsedTime = 0;
    private Crossbow crossbowGun1;


    public Player(float x, float y, float width, float height, Array<TextureRegion> sprites,  LoadSprites loader) {
        super(x, y, width, height);
        this.sprites = sprites;
        this.loader = loader;
        this.crossbowGun = null;
        for (int i = 0; i < 4; i++) {
            RightSprites.add(sprites.get(i));
            LeftSprites.add(sprites.get(i + 5));
        }

        DamageSprites.add(sprites.get(4));
        DamageSprites.add(sprites.get(9));

        animationRight = new Animation<TextureRegion>(animationDuration, RightSprites);
        animationLeft = new Animation<TextureRegion>(animationDuration, LeftSprites);
        currentAnimation = animationRight;

        if(RightSprites.size > 0) {
            SpriteCurrent = new Sprite(RightSprites.get(1));
            previousSprite = new Sprite(RightSprites.get(1)); //inicializa o previousSprite com o valor inicial de SpriteCurrent
        }
        updateLifeString();
    }

    public void act(float delta, Map map, ArrayList<Enemy> enemies, Crossbow crossbow) {

        super.act(delta);
        checkEnemyCollision(enemies);
        boolean moving = false;

        if (playerDamaged){
            if(currentAnimation == animationRight){
                SpriteCurrent = new Sprite(DamageSprites.get(0));
                SpriteCurrent.setPosition(getX(), getY());
                damagedDuration -= delta;
                if (damagedDuration <= 0) {
                    damagedDuration = 0.2f;
                    playerDamaged = false;
                }
            }
            else{
                SpriteCurrent = new Sprite(DamageSprites.get(1));
                SpriteCurrent.setPosition(getX(), getY());
                damagedDuration -= delta;
                if (damagedDuration <= 0) {
                    damagedDuration = 0.2f;
                    playerDamaged = false;
                }
            }
        }
        else {

            if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) {
                this.move("left", delta, map);
                if (crossbowGun != null) {
                    crossbowGun.setOrientation("left");
                }
                moving = true;
            } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
                this.move("right", delta, map);
                if (crossbowGun != null) {
                    crossbowGun.setOrientation("right");
                }
                moving = true;
            }

            if (Gdx.input.isKeyPressed(Input.Keys.UP )|| Gdx.input.isKeyPressed(Input.Keys.W)) {
                this.move("up", delta, map);
                moving = true;
                if (crossbowGun != null) {
                    crossbowGun.setOrientation("up");
                }
            } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)|| Gdx.input.isKeyPressed(Input.Keys.S))  {
                this.move("down", delta, map);
                moving = true;
                if (crossbowGun != null) {
                    crossbowGun.setOrientation("down");
                }
            }
            if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT) || Gdx.input.isKeyPressed(Input.Keys.SHIFT_RIGHT)) {
                speed = speedBoost;
            } else {
                speed = 60;
            }
            if (crossbow != null && Entity.isColliding(this, crossbow)) {
                this.crossbowGun = crossbow;
                crossbow = null;
            }

            if (crossbowItem != null && Entity.isColliding(this, crossbowItem)) {
                crossbowGun1 = new Entities.Guns.Crossbow(getX(), getY(), 16, 16, loader.getSprites("CrossbowGun"), loader);
                crossbowItem = null;
            }

            // Reseta o sprite para o sprite anterior se o tempo
            // de parada do inimigo for igual a zero
            if (enemyStopTime <= 0 && previousSprite != null) {
                SpriteCurrent = previousSprite;
            }
            if (!moving) {
                stateTime = 0; // se não estiver se movendo, reinicie o stateTime
            } else {
                stateTime += delta; // se estiver se movendo, continue a acumular stateTime
            }

            shootElapsedTime += delta;

            SpriteCurrent.setRegion((TextureRegion) currentAnimation.getKeyFrame(stateTime, true));

            // se certifica de que o sprite desenha na posição correta
            SpriteCurrent.setPosition(getX(), getY());
        }
    }

        public void move(String direction, float delta, Map map) {
            switch (direction) {
                case "up":
                    //move para cima
                    if (isAccessible(getX(), getY() + speed * delta, map)) {
                        setPosition(getX(), getY() + speed * delta);
                    }
                    break;
                case "down":
                    //move para baixo
                    if (isAccessible(getX(), getY() - speed * delta, map)) {
                        setPosition(getX(), getY() - speed * delta);
                    }
                    break;
                case "right":
                    currentAnimation = animationRight;
                    // move para a direita
                    if (isAccessible(getX() + speed * delta, getY(), map)) {
                        setPosition(getX() + speed * delta, getY());
                    }
                    break;
                case "left":
                    currentAnimation = animationLeft;
                    // move para a esquerda
                    if (isAccessible(getX() - speed * delta, getY(), map)) {
                        setPosition(getX() - speed * delta, getY());
                    }

                    break;
            }
        }
    public boolean isAccessible(float x, float y, Map map) {
        return map.getTileAt((int) (x / tileSize), (int) (y / tileSize)).isAccessible() &&
                map.getTileAt((int) (x / tileSize), (int) ((y + getHeight()) / tileSize)).isAccessible();
    }
    public void draw(Batch batch){
        SpriteCurrent.draw(batch);
        if(crossbowGun != null) {
            crossbowGun.draw(batch);
        }
    }

    public Sprite getSprite() {
        return SpriteCurrent;
    }
    public void checkEnemyCollision(ArrayList<Enemy> enemies) {
        for (Enemy enemy : enemies) {
            if (isColliding(this, enemy)) {
                this.life -= enemy.getDamage();

                if (life > 0) { // Se o jogador ainda tem vida
                    playerDamaged = true;
                    setPosition(getX() - 1, getY() - 1); // Move o jogador para trás (ajuste os -10 para conseguir o efeito desejado)
                    enemy.setSpeed(0.0f); // Para o inimigo
                    enemyStopTime = 1f; // Define o tempo que o inimigo ficará parado

                    // Guarda o sprite anterior e muda para o sprite de dano
                    previousSprite = SpriteCurrent;
                    if(currentAnimation == animationRight) {
                        SpriteCurrent = new Sprite(DamageSprites.get(0));
                    } else {
                        SpriteCurrent = new Sprite(DamageSprites.get(1));
                    }
                } else {
                    this.life = 0;
                }

                updateLifeString();  // Atualiza a vida em formato de String após perda de vida
            }
        }
    }

    // método para atualizar a representação em String da vida baseado na vida atual
    private void updateLifeString() {
        this.lifeString = "Life: " + (int) this.life + " / " + (int) maxLife;
    }

    private void updateAmmunationString(){
        this.ammunitionString = "Ammunition: " + (int) this.Ammunition;
    }


    public double getLife() {
        return life;
    }

    public void setEnemyStopTime(float enemyStopTime) {
        this.enemyStopTime = enemyStopTime;
    }

    public float getEnemyStopTime() {
        return enemyStopTime;
    }
    public void setLife(double life) {
        this.life = life;
    }
    public String getLifeString() {
        return lifeString;
    }
    public void receiveHealth(int healthRecovered){
        life += healthRecovered;
        if (life > maxLife){
            life = maxLife;
        }

        updateLifeString();
    }
    public Crossbow getCrossbow() {
        return crossbowGun;
    }
    public String getAmmunitionString() {
        return ammunitionString;
    }

    public void setAmmunition(int ammunition) {
        this.Ammunition = ammunition;
        updateAmmunationString();  // atualiza a representação em string da munição quando ela é definida
    }

    public int getAmmunition() {
        return Ammunition;
    }
    public float getShootElapsedTime() {
        return shootElapsedTime;
    }

    public void setShootElapsedTime(float shootElapsedTime) {
        this.shootElapsedTime = shootElapsedTime;
    }
}

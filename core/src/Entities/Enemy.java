package Entities;

import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.utils.Array;
public class Enemy extends Entity{

    Player player;
    private Array<TextureRegion> sprites;
    private Array<TextureRegion> LeftSprites;
    private Array<TextureRegion> RightSprites;
    private Array<TextureRegion> DamageSprites;
    private Animation<TextureRegion> animationRight;
    private Animation<TextureRegion> animationLeft;
    private Animation<TextureRegion> currentAnimation;
    private Sprite SpriteCurrent;
    private float animationDuration = 0.3f;

    private float animationTimer = 0;
    private float speed = 20;
    private boolean isDamaged = false;
    private int life = 15;
    private int damage = 1;
    float stateTime = 0;
    private float damagedDuration = 0.2f;

    public void setMaxViewDistance(float maxViewDistance) {
        this.maxViewDistance = maxViewDistance;
    }

    float maxViewDistance = 50f;  // Distância máxima para o inimigo ver o jogador

    public Enemy(float x, float y, float width, float height, Array<TextureRegion> sprites, Player player) {
        super(x, y, width, height);
        this.sprites = sprites;
        this.player = player;

        // Inicialização dos Arrays antes de usar
        RightSprites = new Array<TextureRegion>();
        LeftSprites = new Array<TextureRegion>();

        for(int i = 0; i<4;i++){
            RightSprites.add(sprites.get(i));
            LeftSprites.add(sprites.get(i+5));
        }

        SpriteCurrent = new Sprite(RightSprites.get(0));

        DamageSprites = new Array<TextureRegion>();
        DamageSprites.add(sprites.get(4));
        DamageSprites.add(sprites.get(9));

        animationRight = new Animation<TextureRegion>(animationDuration, RightSprites);
        animationLeft = new Animation<TextureRegion>(animationDuration, LeftSprites);

        currentAnimation = animationRight;
    }
    @Override
    public void act(float delta) {

        if(isDamaged) {
            if(currentAnimation == animationRight){
                SpriteCurrent = new Sprite(DamageSprites.get(0));
                damagedDuration -= delta;
                if (damagedDuration <= 0) {
                    damagedDuration = 0.2f;
                    isDamaged = false;
                }
            }
            else{
                SpriteCurrent = new Sprite(DamageSprites.get(1));
                damagedDuration -= delta;
                if (damagedDuration <= 0) {
                    damagedDuration = 0.2f;
                    isDamaged = false;
                }
            }
        } else {
            SpriteCurrent.setRegion((TextureRegion) currentAnimation.getKeyFrame(stateTime, true));
        }
        stateTime += delta;
        if (player.getEnemyStopTime() > 0) {
            player.setEnemyStopTime(player.getEnemyStopTime()-delta);
        } else if (getSpeed() == 0) {
            setSpeed(40);
        }

        float distX = Math.abs(player.getX() - getX());
        float distY = Math.abs(player.getY() - getY());
        float distance = (float) Math.sqrt(distX * distX + distY * distY);

        if (distance < maxViewDistance) {
            if (player.getX() < getX() && !isCollidingWithPlayer()) {
                moveLeft(delta);
            } else if (player.getX() > getX() && !isCollidingWithPlayer()) {
                moveRight(delta);
            }

            if(player.getY() < getY() && !isCollidingWithPlayer()){
                setPosition(getX(), getY() - speed * delta);
            } else if(player.getY() > getY() && !isCollidingWithPlayer()) {
                setPosition(getX(), getY() + speed * delta);
            }
        }
        SpriteCurrent.setPosition(getX(), getY());

        if (isCollidingWithPlayer()) {
            player.setLife(player.getLife() - damage);
        }
    }
    public void moveRight(float delta) {
        currentAnimation = animationRight;
        // move para a direita
        setPosition(getX() + speed * delta, getY());
    }

    public void moveLeft(float delta) {
        currentAnimation = animationLeft;
        // move para a esquerda
        setPosition(getX() - speed * delta, getY());
    }

    @Override
    public void draw(Batch batch, float alpha){
        SpriteCurrent.draw(batch);
    }

    public boolean isCollidingWithPlayer() {
        return isColliding(this, player);
    }

    public void setLife(int life) {
        this.life = life;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getLife() {
        return life;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public void draw(Batch batch){
        SpriteCurrent.setPosition(this.getX(), this.getY());
        SpriteCurrent.draw(batch);
    }
    public static boolean isCollidingEnemies(Enemy enemy1, Enemy enemy2) {
        return Entity.isColliding(enemy1, enemy2);
    }
    public boolean isDamaged() {
        return isDamaged;
    }

    public void setDamaged(boolean damaged) {
        isDamaged = damaged;
    }
}

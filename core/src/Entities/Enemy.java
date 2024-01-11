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
    private float animationDuration = 0.3f; // duração da animação em segundos
    private float animationTimer = 0; // temporizador para auxiliar na transição de sprites de animação
    private float speed = 10; // velocidade do movimento inimigo
    private boolean isDamaged = false; // indica se o inimigo está atualmente sofrendo dano
    private int life = 15; // vida ou saúde do inimigo
    private int damage = 1; // quantidade de dano que o inimigo causa ao jogador
    private float stateTime = 0; // temporizador geral do estado que é usado para várias tarefas
    private float damagedDuration = 0.2f; // duração da animação de dano em segundos
    private float maxViewDistance = 50f; // A distância máxima em que o inimigo pode ver o jogador e atacar
    private int shotsHit = 0;
    private float originalSpeed;
    private String direction;

    public Enemy(float x, float y, float width, float height, Array<TextureRegion> sprites, Player player) {
        super(x, y, width, height); // chamada ao construtor da superclasse (Entity)
        this.sprites = sprites; // atribui as sprites de animação fornecidas ao inimigo
        this.player = player; // referência ao objeto do jogador para interação
        originalSpeed = this.speed;

        // Criação de arrays de sprites para diferentes direções e estados do inimigo
        RightSprites = new Array<TextureRegion>();
        LeftSprites = new Array<TextureRegion>();

        // Armazena as sprites individuais para animações direita e esquerda nas respectivas listas
        for(int i = 0; i<4; i++){
            RightSprites.add(sprites.get(i));
            LeftSprites.add(sprites.get(i+5));
        }

        // Define a sprite atual como a primeira sprite de direita
        SpriteCurrent = new Sprite(RightSprites.get(0));

        // Define array de sprites para o estado de dano
        DamageSprites = new Array<TextureRegion>();
        DamageSprites.add(sprites.get(4));
        DamageSprites.add(sprites.get(9));

        // Cria animações para movimentos para a direita e para a esquerda usando as listas de sprites
        animationRight = new Animation<TextureRegion>(animationDuration, RightSprites);
        animationLeft = new Animation<TextureRegion>(animationDuration, LeftSprites);

        // Define a animação atual logo no inicio do objeto
        currentAnimation = animationRight;
    }

    // Este método é chamado em cada quadro de animação para realizar ações do inimigo
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
            setSpeed(originalSpeed);
        }

        float distX = Math.abs(player.getX() - getX());
        float distY = Math.abs(player.getY() - getY());
        float distance = (float) Math.sqrt(distX * distX + distY * distY);

        if (distance < maxViewDistance) {
            if (player.getX() < getX() && !isCollidingWithPlayer()) {
                moveLeft(delta);
                direction = "left";
            } else if (player.getX() > getX() && !isCollidingWithPlayer()) {
                moveRight(delta);
                direction = "right";
            }

            if(player.getY() < getY() && !isCollidingWithPlayer()){
                setPosition(getX(), getY() - speed * delta);
                direction = "up";
            } else if(player.getY() > getY() && !isCollidingWithPlayer()) {
                setPosition(getX(), getY() + speed * delta);
                direction = "down";
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

    public void setMaxViewDistance(float maxViewDistance) {
        this.maxViewDistance = maxViewDistance;
    }
    public String getDirection() {
        return direction;
    }

    public Sprite getSpriteCurrent() {
        return SpriteCurrent;
    }

    public int getShotsHit() {
        return shotsHit;
    }

    public void setShotsHit(int shotsHit) {
        this.shotsHit = shotsHit;
    }
}

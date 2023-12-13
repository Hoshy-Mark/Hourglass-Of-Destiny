package Entities;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class Blade extends Entity {

    private Array<TextureRegion> LeftBlade;
    private Array<TextureRegion> RightBlade;
    private Animation<TextureRegion> animationCurrent;
    private Animation<TextureRegion> animationRight;
    private Animation<TextureRegion> animationLeft;
    private final float animationDuration = 0.15f;
    private float stateTime = 0.0f;
    private String direction;
    private boolean isAttacking;
    private boolean isIdle;
    private int damage = 1;


    public Blade(float x, float y, float width, float height, Array<TextureRegion> blades) {
        super(x, y, width, height);

        LeftBlade = new Array<TextureRegion>(blades.size / 2);
        RightBlade = new Array<TextureRegion>(blades.size / 2);

        for(int i=0; i < blades.size / 2; i++){
            RightBlade.add(blades.get(i));
            LeftBlade.add(blades.get(i+ (blades.size / 2)));
        }
        animationLeft = new Animation<TextureRegion>(animationDuration, LeftBlade);
        animationRight = new Animation<TextureRegion>(animationDuration, RightBlade);
        animationCurrent = animationRight;

    }

    public void setOrientation(String direction) {
        if("right".equals(direction)){
            animationCurrent = animationRight;
        }
        else if("left".equals(direction)){
            animationCurrent = animationLeft;
        }

        this.direction = direction;
    }

    public void draw(Batch batch){
        TextureRegion currentFrame = isAttacking ? animationCurrent.getKeyFrame(stateTime) : ("right".equals(direction) ? RightBlade.get(0) : LeftBlade.get(0));
        float xOffset = animationCurrent.equals(animationLeft) ? -10 : 10;
        batch.draw(currentFrame, getX() + xOffset, getY(), getWidth(), getHeight());
    }

    public void act(float delta){
        super.act(delta);
        if(isAttacking) {
            stateTime += delta;
        }

        if(!isIdle && animationCurrent.isAnimationFinished(stateTime)) {
            this.isAttacking = false;
            this.isIdle = true;
            this.stateTime = 0f;
        }
    }

    public String getDirection() {
        return direction;
    }
    public void attack() {
        if(!isAttacking) {
            if("right".equals(this.direction)){
                this.animationCurrent = animationRight;
                this.isAttacking = true;
                this.stateTime = 0f;
            } else if("left".equals(this.direction)){
                this.animationCurrent = animationLeft;
                this.isAttacking = true;
                this.stateTime = 0f;
            }
            isIdle = false;
        }
    }
    public boolean isAttacking() {
        return isAttacking;
    }
    public int getDamage() {
        return damage;
    }
}
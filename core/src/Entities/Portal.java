package Entities;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class Portal extends Entity{
    private Sprite SpriteCurrent;
    private final float animationDuration = 0.5f;
    private Array<TextureRegion> Sprites = new  Array<TextureRegion>();
    private Animation<TextureRegion> animation = new Animation<TextureRegion>(0.1f, Sprites);
    float stateTime = 0;


    public Portal(float x, float y, float width, float height, Array<TextureRegion> sprites) {
        super(x, y, width, height);

        Sprites.add(sprites.get(0));
        Sprites.add(sprites.get(1));
        Sprites.add(sprites.get(2));
        animation = new Animation<TextureRegion>(animationDuration, Sprites);
        SpriteCurrent = new Sprite(Sprites.get(0));

    }

    @Override
    public void act(float delta) {

        SpriteCurrent.setRegion((TextureRegion) animation.getKeyFrame(stateTime, true));

        stateTime += delta;

        SpriteCurrent.setPosition(getX(), getY());

    }

    public void draw(Batch batch){
        SpriteCurrent.setPosition(this.getX(), this.getY());
        SpriteCurrent.draw(batch);
    }
}

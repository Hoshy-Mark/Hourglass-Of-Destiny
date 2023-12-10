package Entities;


import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class Item extends Entity {
    private Animation<TextureRegion> animation;
    private Array<TextureRegion> Sprites;
    private Sprite SpriteCurrent;
    private float animationDuration = 0.3f;
    private float stateTime = 0;
    private int frameNumber;


    public Item(float x, float y, float width, float height, Array<TextureRegion> sprites, int frameNumber) {
        super(x, y, width, height);
        // Inicialização dos Arrays antes de usar
        Sprites = new Array<TextureRegion>();
        this.frameNumber = frameNumber;

        for(int i = 0; i<frameNumber;i++){
            Sprites.add(sprites.get(i));
        }
        animation = new Animation<TextureRegion>(animationDuration, Sprites);

        SpriteCurrent = new Sprite(Sprites.get(0));

    }

    public Item(float x, float y, float width, float height, TextureRegion sprite){
        super(x, y, width, height);

        SpriteCurrent = new Sprite(sprite);
    }
    public void update(float delta) {
        stateTime += delta;
        SpriteCurrent.setRegion(animation.getKeyFrame(stateTime, true));
    }
    public void draw(Batch batch){
        SpriteCurrent.setPosition(this.getX(), this.getY());
        SpriteCurrent.draw(batch);
    }
}

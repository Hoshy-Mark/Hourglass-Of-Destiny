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

    private final float animationDuration = 0.15f;
    private float stateTime = 0.0f;
    private String direction;

    public Blade(float x, float y, float width, float height, Array<TextureRegion> blades) {
        super(x, y, width, height);

        LeftBlade = new Array<TextureRegion>(blades.size / 2);
        RightBlade = new Array<TextureRegion>(blades.size / 2);

        for(int i=0; i < blades.size / 2; i++){
            LeftBlade.add(blades.get(i));
            RightBlade.add(blades.get(i+ (blades.size / 2)));
        }

        animationCurrent = new Animation<TextureRegion>(animationDuration, RightBlade, Animation.PlayMode.LOOP);
    }

    public void setOrientation(String direction) {
        if("right".equals(direction)){
            animationCurrent = new Animation<TextureRegion>(animationDuration, RightBlade, Animation.PlayMode.LOOP);
        }
        else if("left".equals(direction)){
            animationCurrent = new Animation<TextureRegion>(animationDuration, LeftBlade, Animation.PlayMode.LOOP);
        }

        this.direction = direction;
    }

    public void draw(Batch batch){
        TextureRegion currentFrame = animationCurrent.getKeyFrame(stateTime);
        batch.draw(currentFrame, getX(), getY(), getWidth(), getHeight());
    }

    public void act(float delta){
        super.act(delta);

        //Aqui você pode adicionar a lógica da espada.

        //Exemplo: se o jogador pressionou a tecla de atacar, inicie a animação e verifique se é necessário atacar algum inimigos etc.
    }

    public String getDirection() {
        return direction;
    }
}
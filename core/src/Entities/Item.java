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
    private float animationDuration = 0.3f; // Define a duração da animação
    private float stateTime = 0; // Variável que mantém o andamento do tempo de animação
    private int frameNumber; // Número de frames na animação.
    private float scale;


    // Construtor se a Item tiver várias sprites (uma animação)
    public Item(float x, float y, float width, float height, Array<TextureRegion> sprites, int frameNumber) {
        super(x, y, width, height);

        // inicialize a Array de Sprites e mantenha o número de frames para fins de animação
        Sprites = new Array<TextureRegion>();
        this.frameNumber = frameNumber;

        // adiciona as sprites à lista de sprites
        for(int i = 0; i<frameNumber;i++){
            Sprites.add(sprites.get(i));
        }
        // define a animação usando os frames disponíveis e a duração pré-definida
        animation = new Animation<TextureRegion>(animationDuration, Sprites);

        // Define a sprite atual como a primeira na lista de sprites
        SpriteCurrent = new Sprite(Sprites.get(0));
    }

    // Se o Item tiver apenas uma sprite (não é animado)
    public Item(float x, float y, float width, float height, TextureRegion sprite){
        super(x, y, width, height);

        // Nesse caso, só existe uma única sprite que se torna a sprite atual.
        SpriteCurrent = new Sprite(sprite);
    }
    public void update(float delta) {
        // Atualiza o tempo de estado e altera a sprite atual com base na animação
        stateTime += delta;
        SpriteCurrent.setRegion(animation.getKeyFrame(stateTime, true));
    }
    public void draw(Batch batch){
        SpriteCurrent.setPosition(this.getX(), this.getY());
        SpriteCurrent.setScale(scale);
        SpriteCurrent.draw(batch);
    }

    public Sprite getSpriteCurrent() {
        return SpriteCurrent;
    }

    @Override
    public void setScale(float scale) {
        this.scale = scale;
    }
}

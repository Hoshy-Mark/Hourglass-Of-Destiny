package Entities.InteractiveObjects;

import Entities.InteractiveObject;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

public class Chest extends InteractiveObject {
    private Sprite OpenedChest;
    private Sprite ClosedChest;
    private Sprite currentSprite;
    private boolean opened;

    public Chest(float x, float y, float width, float height, Array<TextureRegion> sprites) {
        super(x, y, width, height, "Chest");

        ClosedChest = new Sprite(sprites.get(0));
        OpenedChest = new Sprite(sprites.get(1));

        currentSprite = ClosedChest;
    }

    public void draw(Batch batch){
        currentSprite.setPosition(this.getX(), this.getY());
        currentSprite.draw(batch);
    }

    public void openChest(){
        currentSprite = OpenedChest;
        opened = true;
    }

    public boolean isOpened() {
        return opened;
    }

}

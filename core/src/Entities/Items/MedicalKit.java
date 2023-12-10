package Entities.Items;

import Entities.Item;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class MedicalKit extends Item {
    private final int lifeValue = 20;
    public MedicalKit(float x, float y, float width, float height, Array<TextureRegion> sprites) {
        super(x, y, width, height, sprites, 3);
    }

    public int getLifeValue(){
        return lifeValue;
    }
    public int use(){
        int lifeRecovered = this.lifeValue; // vida que será recuperada, supondo que essa seja a variável que guarda o valor de vida que o kit recupera
        return lifeRecovered;
    }
}

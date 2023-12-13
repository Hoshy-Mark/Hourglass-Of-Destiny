package Entities;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class Gun extends Entity{

    // Declaramos Sprites para cada direção que a arma pode mirar e declarando o Sprite que será considerado o atual
    private Sprite RightSprite;
    private Sprite LeftSprite;
    private Sprite UpSprite;
    private Sprite DownSprite;
    private Sprite SpriteCurrent;


    public Gun(float x, float y, float width, float height, Array<TextureRegion> sprites) {
        super(x, y, width, height);

        //Defino os Sprites para cada direção e o atual
        RightSprite = new Sprite(sprites.get(0));
        LeftSprite = new Sprite(sprites.get(1));
        DownSprite = new Sprite(sprites.get(2));
        UpSprite = new Sprite(sprites.get(3));
        SpriteCurrent = RightSprite;
    }

    public void setOrientation(String direction) {

        //Descubro pra qua direção o Player está virado, para o sprite da arma virar junto
        if(direction.equals("right")) {
            this.SpriteCurrent = RightSprite;
        }
        else if(direction.equals("left")) {
            this.SpriteCurrent = LeftSprite;
        }
        else if(direction.equals("down")){
            this.SpriteCurrent = DownSprite;
        }
        else if(direction.equals("up")){
            this.SpriteCurrent = UpSprite;
        }
    }
    public void draw(Batch batch){

        //Desenho a arma na posição do Player, com algumas mudanças de x e y para se adequar.
        if(SpriteCurrent.equals(LeftSprite)){
            SpriteCurrent.setPosition(this.getX() - 8, this.getY());
        }
        else if(SpriteCurrent.equals(RightSprite)){
            SpriteCurrent.setPosition(this.getX(), this.getY());
        }
        else if(SpriteCurrent.equals(UpSprite)){
            SpriteCurrent.setPosition(this.getX(), this.getY() +5);
        }
        else if(SpriteCurrent.equals(DownSprite)){
            SpriteCurrent.setPosition(this.getX()-2, this.getY() - 3);
        }
        SpriteCurrent.draw(batch);
    }

}

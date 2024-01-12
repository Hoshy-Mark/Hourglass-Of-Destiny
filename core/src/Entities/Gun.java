package Entities;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class Gun extends Entity {

    // Declaramos Sprites para cada direção que a arma pode mirar e declarando o Sprite que será considerado o atual
    private Sprite RightSprite;
    private Sprite LeftSprite;
    private Sprite UpSprite;
    private Sprite DownSprite;
    private Sprite upRightSprite;
    private Sprite downRightSprite;
    private Sprite upLeftSprite;
    private Sprite downLeftSprite;
    private Sprite SpriteCurrent;
    private String directionName;


    public Gun(float x, float y, float width, float height, Array<TextureRegion> sprites) {
        super(x, y, width, height);

        //Defino os Sprites para cada direção e o atual
        RightSprite = new Sprite(sprites.get(0));
        LeftSprite = new Sprite(sprites.get(1));
        DownSprite = new Sprite(sprites.get(2));
        UpSprite = new Sprite(sprites.get(3));
        upRightSprite = new Sprite(sprites.get(5));
        upLeftSprite = new Sprite(sprites.get(4));
        downRightSprite = new Sprite(sprites.get(6));
        downLeftSprite = new Sprite(sprites.get(7));
        SpriteCurrent = RightSprite;
    }

    public void draw(Batch batch) {

        //Desenho a arma na posição do Player, com algumas mudanças de x e y para se adequar.
        if (SpriteCurrent.equals(LeftSprite)) {
            SpriteCurrent.setPosition(this.getX() - 8, this.getY());
        } else if (SpriteCurrent.equals(RightSprite)) {
            SpriteCurrent.setPosition(this.getX(), this.getY());
        } else if (SpriteCurrent.equals(UpSprite)) {
            SpriteCurrent.setPosition(this.getX(), this.getY() + 5);
        } else if (SpriteCurrent.equals(DownSprite)) {
            SpriteCurrent.setPosition(this.getX() - 2, this.getY() - 3);
        } else if (SpriteCurrent.equals(upRightSprite)) {
            SpriteCurrent.setPosition(this.getX(), this.getY() + 5);
        } else if (SpriteCurrent.equals(downRightSprite)) {
            SpriteCurrent.setPosition(this.getX(), this.getY() - 3);
        } else if (SpriteCurrent.equals(downLeftSprite)) {
            SpriteCurrent.setPosition(this.getX() - 8, this.getY() - 3);
        } else if (SpriteCurrent.equals(upLeftSprite)) {
            SpriteCurrent.setPosition(this.getX() - 8, this.getY() + 5);
        }
        SpriteCurrent.draw(batch);
    }

    public void setOrientation(Vector2 direction, String currentAnimation) {
        float radian = direction.angleRad();

        if (currentAnimation.equals("right")) {
            if (radian >= -Math.PI / 8 && radian < Math.PI / 8) {
                this.SpriteCurrent = RightSprite;
                directionName = "right";
            } else if (radian >= Math.PI / 8 && radian < 3 * Math.PI / 8) {
                this.SpriteCurrent = upRightSprite;
                directionName = "upRight";
            } else if (radian >= 3 * Math.PI / 8 && radian < 5 * Math.PI / 8) {
                this.SpriteCurrent = UpSprite;
                directionName = "up";
            } else if (radian >= -5 * Math.PI / 8 && radian < -3 * Math.PI / 8) {
                this.SpriteCurrent = DownSprite;
                directionName = "down";
            } else if (radian >= -3 * Math.PI / 8 && radian < -Math.PI / 8) {
                this.SpriteCurrent = downRightSprite;
                directionName = "downRight";
            }
        } else {
            if ((radian >= 7 * Math.PI / 8 && radian <= Math.PI) || (radian >= -Math.PI && radian < -7 * Math.PI / 8)) {
                this.SpriteCurrent = LeftSprite;
                directionName = "left";
            } else if (radian >= 5 * Math.PI / 8 && radian < 7 * Math.PI / 8) {
                this.SpriteCurrent = upLeftSprite;
                directionName = "upLeft";
            } else if (radian >= -7 * Math.PI / 8 && radian < -5 * Math.PI / 8) {
                this.SpriteCurrent = downLeftSprite;
                directionName = "downLeft";
            } else if (radian >= 3 * Math.PI / 8 && radian < 5 * Math.PI / 8) {
                this.SpriteCurrent = UpSprite;
                directionName = "up";
            } else if (radian >= -5 * Math.PI / 8 && radian < -3 * Math.PI / 8) {
                this.SpriteCurrent = DownSprite;
                directionName = "down";
            }
        }
    }

    public String getDirectionName() {
        return directionName;
    }
}

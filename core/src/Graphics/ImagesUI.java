package Graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class ImagesUI extends Actor {
    private Texture image;
    private float x, y; // Posição da imagem
    private float scaleX, scaleY; // Escala da imagem

    public ImagesUI(String name) {
        String imagePath = name + ".png"; // Forma o caminho com o nome da imagem
        image = new Texture(imagePath);
        x = 0;
        y = 0;

    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void render(SpriteBatch batch, Stage stage) {
        if (image != null) {
            batch.draw(image, x, y, 1080, 650);
        }
    }

    public void dispose() {
        if (image != null) {
            image.dispose();
        }
    }



}
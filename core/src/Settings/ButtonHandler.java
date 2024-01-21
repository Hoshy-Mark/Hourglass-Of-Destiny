package Settings;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class ButtonHandler {
    private Stage stage;

    public ButtonHandler(Stage stage) {
        this.stage = stage;
    }

    public TextButton createButton(String buttonText, ClickListener clickListener, float x, float y) {
        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.font = new BitmapFont();

        TextButton button = new TextButton(buttonText, buttonStyle);

        button.setPosition(x, y);

        if (clickListener != null) {
            button.addListener(clickListener);
        }

        stage.addActor(button);

        return button;
    }

    public TextButton createButton(String buttonText, ClickListener clickListener, float x, float y, String upTexturePath, String downTexturePath) {
        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();

        Texture upTexture = new Texture(Gdx.files.internal(upTexturePath));
        Texture downTexture = new Texture(Gdx.files.internal(downTexturePath));

        buttonStyle.up = new TextureRegionDrawable(new TextureRegion(upTexture));
        buttonStyle.down = new TextureRegionDrawable(new TextureRegion(downTexture));

        buttonStyle.font = new BitmapFont();

        TextButton button = new TextButton(buttonText, buttonStyle);

        button.setPosition(x, y);

        if (clickListener != null) {
            button.addListener(clickListener);
        }

        stage.addActor(button);

        return button;
    }

    public void render() {
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    public Stage getStage() {
        return stage;
    }

    public void dispose() {
        stage.dispose();
    }
}
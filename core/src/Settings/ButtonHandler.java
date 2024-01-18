package Settings;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class ButtonHandler {
    private Stage stage;

    public ButtonHandler() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
    }

    public TextButton createButton(String buttonText, ClickListener clickListener, float x, float y) {
        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();

        buttonStyle.font = new BitmapFont();
        buttonStyle.up = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("buttons/botaoTeste.png"))));
        buttonStyle.down = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("buttons/botaoTesteApertado.png"))));

        TextButton button = new TextButton("", buttonStyle);
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
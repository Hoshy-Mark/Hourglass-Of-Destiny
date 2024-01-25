package Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.HourglassOfDestiny;

import Settings.ButtonHandler;

public class GameOverScreen implements Screen {

    private final HourglassOfDestiny game;
    private Stage stage;
    private TextButton restartButton, menuButton;

    public GameOverScreen(final HourglassOfDestiny game) {
        this.game = game;
        stage = new Stage();

        Texture texture = new Texture(Gdx.files.internal("ImagesUI/GameOver.png")); // substitua pelo caminho correto da imagem
        Image backgroundImage = new Image(texture);
        backgroundImage.setFillParent(true);  // Isso dimensionará a imagem para o tamanho do Stage
        stage.addActor(backgroundImage);

        ButtonHandler buttonHandler = new ButtonHandler(stage);

        restartButton = buttonHandler.createButton("Restart", new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new GameScreen(game)); // Inicia de novo
            }
        }, 0, 0);

        menuButton = buttonHandler.createButton("Main Menu", new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MenuScreen(game)); // Volta ao menu
            }
        }, 0, 0);

        positionButtons();

        stage.addActor(restartButton);
        stage.addActor(menuButton);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        restartButton.setTouchable(Touchable.enabled);  // habilita botão
        menuButton.setTouchable(Touchable.enabled); // habilita botão
    }
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();

        //Full Screen
        if(Gdx.input.isKeyPressed(Input.Keys.K)){
            Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
        }
        //Janela
        if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)){
            Gdx.graphics.setWindowedMode(1080, 720);
        }
        //Fechar o Jogo
        if(Gdx.input.isKeyPressed(Input.Keys.P)){
            Gdx.app.exit();
        }
    }

    @Override
    public void resize(int width, int height) {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {
        restartButton.setTouchable(Touchable.disabled);  // desabilita botão
        menuButton.setTouchable(Touchable.disabled); // desabilita botão
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
    private void positionButtons() {

        restartButton.setPosition(stage.getWidth() / 2, stage.getHeight() / 2);
        menuButton.setPosition(stage.getWidth() / 2, stage.getHeight() / 2 - restartButton.getHeight() - 10);
    }
}
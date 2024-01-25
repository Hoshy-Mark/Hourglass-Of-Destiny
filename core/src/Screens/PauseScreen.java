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

public class PauseScreen implements Screen {

    private final HourglassOfDestiny game;
    private final Screen parentScreen;
    private Stage stage;
    private TextButton resumeButton, menuButton;

    public PauseScreen(final HourglassOfDestiny game, final Screen parentScreen) {
        this.game = game;
        this.parentScreen = parentScreen;
        stage = new Stage();

        Texture texture = new Texture(Gdx.files.internal("ImagesUI/Pause.png")); // substitua pelo caminho correto da imagem
        Image backgroundImage = new Image(texture);
        backgroundImage.setFillParent(true); // Isso dimensionará a imagem para o tamanho do Stage
        stage.addActor(backgroundImage);

        ButtonHandler buttonHandler = new ButtonHandler(stage);

        resumeButton = buttonHandler.createButton("Resume", new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(parentScreen);
            }
        }, 0, 0);


        menuButton = buttonHandler.createButton("Main Menu", new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MenuScreen(game));
                parentScreen.dispose();
            }
        }, 0, 0);

        positionButtons();

        stage.addActor(resumeButton);
        stage.addActor(menuButton);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        resumeButton.setTouchable(Touchable.enabled);
        menuButton.setTouchable(Touchable.enabled);
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
        resumeButton.setTouchable(Touchable.disabled);
        menuButton.setTouchable(Touchable.disabled);
    }

    @Override
    public void dispose () {
        stage.dispose();
    }
    private void positionButtons() {

        resumeButton.setPosition(stage.getWidth() / 2, stage.getHeight() / 2);
        menuButton.setPosition(stage.getWidth() / 2, stage.getHeight() / 2 - resumeButton.getHeight() - 10);
    }
}
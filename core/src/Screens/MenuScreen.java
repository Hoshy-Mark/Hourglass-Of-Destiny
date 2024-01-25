package Screens;

import Audio.AudioManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.HourglassOfDestiny;
import com.badlogic.gdx.graphics.Texture;

import Settings.ButtonHandler;

public class MenuScreen implements Screen {
    private final HourglassOfDestiny game;
    private Stage stage;
    private TextButton startButton, newGameButton, settingsButton;
    private AudioManager audioManager = new AudioManager();

    public MenuScreen(final HourglassOfDestiny game) {
        this.game = game;
        this.stage = new Stage();

        Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());


        Texture texture = new Texture(Gdx.files.internal("ImagesUI/Menu.png")); // substitua pelo caminho correto da imagem
        Image backgroundImage = new Image(texture);
        backgroundImage.setFillParent(true);  // Isso dimensionará a imagem para o tamanho do Stage
        stage.addActor(backgroundImage);

        ButtonHandler buttonHandler = new ButtonHandler(stage);

        startButton = buttonHandler.createButton("Load Game", new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new GameScreen(game));
            }
        }, 0, 0);

        newGameButton = buttonHandler.createButton("New Game", new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Aqui você colocaria o código para iniciar um novo jogo
            }
        }, 0, 0);

        settingsButton = buttonHandler.createButton("Settings", new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Aqui você colocaria o código para abrir a tela de configurações
            }
        }, 0, 0);


        positionButtons();

        stage.addActor(startButton);
        stage.addActor(newGameButton);
        stage.addActor(settingsButton);

    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        audioManager.playBackgroundMusic();
        startButton.setTouchable(Touchable.enabled);
        newGameButton.setTouchable(Touchable.enabled);
        settingsButton.setTouchable(Touchable.enabled);
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
    public void resize(int width, int height) {
        // Atualize o viewport quando a tela for redimensionada
        stage.getViewport().update(width, height, true);

        // Reposiciona os botões
        positionButtons();
    }

    @Override
    public void pause () {}

    @Override
    public void resume() {}

    @Override
    public void hide() {
        // A tela ficou inativa - parar a música
        audioManager.stopBackgroundMusic();
        startButton.setTouchable(Touchable.disabled);
        newGameButton.setTouchable(Touchable.disabled);
        settingsButton.setTouchable(Touchable.disabled);
    }

    @Override
    public void dispose () {
        stage.dispose();
    }

    private void positionButtons() {
        float x = (stage.getWidth() - startButton.getWidth()) / 2;
        float y = (stage.getHeight() - startButton.getHeight()) / 2;
        startButton.setPosition(x, y);
        newGameButton.setPosition(x, y - startButton.getHeight() - 10);
        settingsButton.setPosition(x, y - startButton.getHeight() * 2 - 20);
    }
}

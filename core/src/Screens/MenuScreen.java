package Screens;

import Audio.AudioManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.mygdx.game.HourglassOfDestiny;
import com.badlogic.gdx.graphics.Texture;

import Settings.ButtonHandler;


    public class MenuScreen implements Screen {
        private final HourglassOfDestiny game;
        private Stage stage;
        private TextButton startButton, newGameButton, settingsButton;
        private AudioManager audioManager = new AudioManager();
        private BitmapFont font;  // variável de instância para a fonte
        private Image logoImage;


        public MenuScreen(final HourglassOfDestiny game) {
            this.game = game;
            this.stage = new Stage(new ExtendViewport(1920, 1080));

            font = new BitmapFont();  // inicialize a fonte no construtor

            Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());

            Texture texture = new Texture(Gdx.files.internal("ImagesUI/Menu.png"));
            Image backgroundImage = new Image(texture);
            backgroundImage.setFillParent(true);

            stage.addActor(backgroundImage);

            startButton = createCustomButton("buttons/Load Game.png", new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                }
            });

            newGameButton = createCustomButton("buttons/New Game.png", new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    game.setScreen(new GameScreen(game));
                }
            });

            settingsButton = createCustomButton("buttons/Settings.png", new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                }
            });

            Texture logoTexture = new Texture(Gdx.files.internal("buttons/Logo.png"));
            logoImage = new Image(logoTexture);

            positionButtons();

            stage.addActor(startButton);
            stage.addActor(newGameButton);
            stage.addActor(settingsButton);
            stage.addActor(logoImage);

        }

        private TextButton createCustomButton(String textureName, ClickListener listener) {
            Texture buttonTex = new Texture(Gdx.files.internal(textureName));
            TextureRegionDrawable buttonRegion = new TextureRegionDrawable(new TextureRegion(buttonTex));
            TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
            textButtonStyle.up = buttonRegion;
            textButtonStyle.font = font;  // Use a variável de instância `font`
            TextButton button = new TextButton(" ", textButtonStyle);
            button.addListener(listener);
            return button;
        }

        @Override
        public void show() {
            Gdx.input.setInputProcessor(stage);
            audioManager.playBackgroundMusic();
            startButton.setTouchable(Touchable.enabled);
            newGameButton.setTouchable(Touchable.enabled);
            settingsButton.setTouchable(Touchable.enabled);
            positionButtons();
        }

        @Override
        public void render(float delta) {
            Gdx.gl.glClearColor(0, 0, 0, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            stage.act(delta);
            stage.draw();

            //Full Screen
            if (Gdx.input.isKeyPressed(Input.Keys.K)) {
                Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
            }
            //Janela
            if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
                Gdx.graphics.setWindowedMode(1080, 720);
            }
            //Fechar o Jogo
            if (Gdx.input.isKeyPressed(Input.Keys.P)) {
                Gdx.app.exit();
            }
        }

        @Override
        public void resize(int width, int height) {
            stage.getViewport().update(width, height, true);
            positionButtons();
        }

        @Override
        public void pause() {
        }

        @Override
        public void resume() {
        }

        @Override
        public void hide() {
            // A tela ficou inativa - parar a música
            audioManager.stopBackgroundMusic();
            startButton.setTouchable(Touchable.disabled);
            newGameButton.setTouchable(Touchable.disabled);
            settingsButton.setTouchable(Touchable.disabled);
        }

        @Override
        public void dispose() {
            stage.dispose();
            font.dispose();
        }

        private void positionButtons() {
            float x = (stage.getWidth() - startButton.getWidth()) / 2;
            float y = (stage.getHeight() - startButton.getHeight()) / 2;

            System.out.println("X " + x+ " Y " + y);
            System.out.println("Width " + stage.getWidth() + " Height " + stage.getHeight());

            startButton.setPosition(x, y);
            newGameButton.setPosition(x, y - startButton.getHeight() - 10);
            settingsButton.setPosition(x, y - startButton.getHeight() * 2 - 20);
            logoImage.setPosition((stage.getWidth() - logoImage.getWidth()) / 2, stage.getHeight() - logoImage.getHeight());
        }
    }

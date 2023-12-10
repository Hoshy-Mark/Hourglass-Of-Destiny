package Graphics;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import Entities.Player;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class PlayerUI {
    private Stage stage;
    private Label lifeLabel;
    private Label ammunitionLabel;
    private BitmapFont font;
    private Player player;

    public PlayerUI(Batch batch, Player player) {
        this.player = player;
        this.stage = new Stage(new ScreenViewport(), batch);

        // Inicia a fonte e o estilo da label
        font = new BitmapFont();
        font.getData().setScale(1.5f, 1.5f); // Aumenta o tamanho da fonte
        LabelStyle style = new LabelStyle(font, Color.WHITE);
        lifeLabel = new Label(player.getLifeString(), style);
        ammunitionLabel = new Label(player.getAmmunitionString(), style);

        // Posiciona a label no canto superior esquerdo
        lifeLabel.setPosition(30, stage.getHeight() - lifeLabel.getHeight() - 15);
        ammunitionLabel.setPosition(30, stage.getHeight() - lifeLabel.getHeight() - 35);
        // Adiciona a vida no UI stage
        stage.addActor(lifeLabel);
        stage.addActor(ammunitionLabel);
    }

    public void update() {
        // Atualiza a vida na label com a mais recente vida do player
        lifeLabel.setText(player.getLifeString());
        ammunitionLabel.setText(player.getAmmunitionString());
    }

    public void draw() {
        stage.draw();
    }

    public void dispose() {
        font.dispose();
        stage.dispose();
    }
}
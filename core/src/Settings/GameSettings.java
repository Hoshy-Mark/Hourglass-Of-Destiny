package Settings;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class GameSettings {

    private int width;
    private int height;

    private FitViewport viewport;

    public GameSettings (FitViewport viewport){
        this.width = Gdx.graphics.getWidth();
        this.height = Gdx.graphics.getHeight();
        this.viewport = viewport;
    }

    public void setResolution(int width, int height) {
        this.width = width;
        this.height = height;

        Gdx.graphics.setWindowedMode(width, height);
        viewport.update(width, height, true);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
package Settings;

import Entities.Player;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class SaveState {
    private final Preferences prefs;

    public SaveState() {
        prefs = Gdx.app.getPreferences("MyGamePreferences");
    }

    public void saveGame(float life, int ammunition) {
        prefs.putFloat("life", life);
        prefs.putInteger("ammunition", ammunition);
        prefs.flush(); // Isto salvará as preferências
    }

    public float getLife() {
        return prefs.getFloat("life", 0); // retorna 0 como padrão se nada estiver salvo
    }

    public int getAmmunition() {
        return prefs.getInteger("ammunition", 0); // retorna 0 como padrão se nada estiver salvo
    }

    public Preferences getPrefs() {
        return prefs;
    }
}
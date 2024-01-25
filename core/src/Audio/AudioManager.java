package Audio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class AudioManager {

    private Sound shootSound; // som de tiro
    private Sound swordCutSound; // som de espada cortando
    private Sound hitSound; // som do impacto de uma flecha
    private Sound hitWood; // som do impacto da flecha na arvore
    private Sound DropArrow; // som da Flecha Dropando

    private Music backgroundMusic; // música de fundo

    // volumes
    private float soundVolume = 0.05f; // 50%
    private float musicVolume = 0.003f; // 50%
    public AudioManager() {
        // carregar os sons e a música de fundo (substitua pelos caminhos corretos dos arquivos)
        shootSound = Gdx.audio.newSound(Gdx.files.internal("Sounds/Shoot.wav"));
        swordCutSound = Gdx.audio.newSound(Gdx.files.internal("Sounds/Slash.wav"));
        hitSound = Gdx.audio.newSound(Gdx.files.internal("Sounds/Arrow Hit.wav"));
        hitWood = Gdx.audio.newSound(Gdx.files.internal("Sounds/Arrow Wood.wav"));
        DropArrow = Gdx.audio.newSound(Gdx.files.internal("Sounds/Arrow Drop.wav"));

        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("Musics/MusicOfMenu.mp3"));
        backgroundMusic.setLooping(true); // fazer a música de fundo tocar em loop
    }

    public void playShootSound() {
        shootSound.play(soundVolume);
    }

    public void playSwordCutSound() {
        swordCutSound.play(soundVolume);
    }

    public void playHitSound() {
        hitSound.play(soundVolume);
    }
    public void playHitWood(){
        hitWood.play(soundVolume);
    }
    public void playDropArrow(){
        DropArrow.play(soundVolume);
    }
    public void playBackgroundMusic() {
        backgroundMusic.setVolume(musicVolume);
        backgroundMusic.play();
    }

    public void stopBackgroundMusic() {
        backgroundMusic.stop();
    }

    public void dispose() {
        // não esqueça de liberar os recursos quando eles não forem mais necessários
        shootSound.dispose();
        swordCutSound.dispose();
        hitSound.dispose();
        DropArrow.dispose();
        backgroundMusic.dispose();
    }
}
package Graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import java.util.HashMap;

public class LoadSprites {
    private HashMap<String, Array<TextureRegion>> spritesheets;
    private HashMap<String, Texture> sprites;

    private HashMap<String, Texture> imageUi;

    // Construtor
    public LoadSprites() {
        this.spritesheets = new HashMap<>();
        this.sprites = new HashMap<>();
        this.imageUi = new HashMap<>();

        loadSpriteSheet("Crossbow","Sprites/Crossbow-sheet.png", 16, 16);
        loadSpriteSheet("CrossbowAnimation","Sprites/Crossbow Animation-sheet.png", 16, 16);
        loadSpriteSheet("Arrows","Sprites/Arrows-sheet.png", 16, 16);
        loadSpriteSheet("Bat","Sprites/Bat-sheet.png", 16, 16);
        loadSpriteSheet("Chest","Sprites/Chest-sheet.png", 16, 16);
        loadSpriteSheet("Blue Armor","Sprites/Blue Armor-sheet.png", 16, 16);
        loadSpriteSheet("Blue Cape","Sprites/Blue Cape-sheet.png", 16, 16);
        loadSpriteSheet("Green Armor","Sprites/Green Armor-sheet.png", 16, 16);
        loadSpriteSheet("Green Cape","Sprites/Green Cape-sheet.png", 16, 16);
        loadSpriteSheet("King Slime Death Animation","Sprites/King Slime Death Animation-sheet.png", 16, 16);
        loadSpriteSheet("King Slime","Sprites/King Slime-sheet.png", 16, 16);
        loadSpriteSheet("Medical Kit","Sprites/Medical Kit-sheet.png", 16, 16);
        loadSpriteSheet("Orange Cape","Sprites/Orange Cape-sheet.png", 16, 16);
        loadSpriteSheet("Pink Armor","Sprites/Pink Armor-sheet.png", 16, 16);
        loadSpriteSheet("Pink Cape","Sprites/Pink Cape-sheet.png", 16, 16);
        loadSpriteSheet("PortalUp","Sprites/PortalUp-sheet.png", 16, 16);
        loadSpriteSheet("PortalDown","Sprites/PortalDown-sheet.png", 16, 16);
        loadSpriteSheet("Slime","Sprites/Slime-sheet.png", 16, 16);
        loadSpriteSheet("Player","Sprites/Player-sheet.png", 16, 16);
        loadSpriteSheet("Yellow Armor","Sprites/Yellow Armor-sheet.png", 16, 16);
        loadSpriteSheet("Sword", "Sprites/Sword-sheet.png",16,16);
        loadSpriteSheet("Sword Power Up", "Sprites/Sword PowerUp-sheet.png",16,16);
        loadAllSprites();
        loadAllImageUi();
    }

    // Metodo para carregar as imagens

    public void loadSprite(String keyName, String filePath){
        sprites.put(keyName, new Texture(Gdx.files.internal(filePath)));
    }
    public void loadAllSprites(){
        loadSprite("Grass1", "Sprites/Grass One.png");
        loadSprite("Grass2", "Sprites/Grass Two.png");
        loadSprite("Grass3", "Sprites/Grass Three.png");
        loadSprite("Stone1", "Sprites/Stone One.png");
        loadSprite("Stone2", "Sprites/Stone Two.png");
        loadSprite("Crow", "Sprites/Crown.png");
        loadSprite("RedRing", "Sprites/Red Ring.png");
        loadSprite("GreenRing", "Sprites/Green Ring.png");
        loadSprite("Arrow", "Sprites/Arrow.png");
        loadSprite("Sword", "Sprites/Sword.png");
    }
    public void loadAllImageUi(){
        loadimagesUI("Pause", "ImagesUi/Pause.png");
        loadimagesUI("Menu", "ImagesUi/Menu.png");
    }

    public void loadSpriteSheet(String keyName, String filePath, int spriteWidth, int spriteHeight) {
        Texture spritesheet = new Texture(Gdx.files.internal(filePath));
        TextureRegion[][] spriteRegions = TextureRegion.split(spritesheet, spriteWidth, spriteHeight);

        Array<TextureRegion> sprites = new Array<>();

        for (TextureRegion[] spriteRow : spriteRegions) {
            for (TextureRegion sprite :  spriteRow) {
                sprites.add(sprite);
            }
        }

        // Adiciona essa nova lista de sprites ao hashMap
        spritesheets.put(keyName, sprites);
    }

    // Metodo para recuperar os sprites carregados
    public Array<TextureRegion> getSprites(String name) {
        return spritesheets.get(name);
    }
    public Texture getSprite(String name){
        return sprites.get(name);
    }

    public void loadimagesUI(String keyName, String filePath) {
        imageUi.put(keyName, new Texture(Gdx.files.internal(filePath)));
    }

}

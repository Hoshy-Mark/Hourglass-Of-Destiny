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
        Texture sprite = new Texture(Gdx.files.internal(filePath));
        sprites.put(keyName, sprite);
    }
    public void loadAllSprites(){
        loadSprite("ChaoDeLama","Tiles/ChaoDeLama.png");
        loadSprite("ChaoDePedra","Tiles/ChaoDePedra.png");
        loadSprite("GramaFofaSombra","Tiles/GramaFofa Sombra.png");
        loadSprite("GramaFofa1","Tiles/GramaFofa 1.png");
        loadSprite("GramaFofa2","Tiles/GramaFofa 2.png");
        loadSprite("GramaFofa3","Tiles/GramaFofa 3.png");
        loadSprite("GramaFofa4","Tiles/GramaFofa 4.png");
        loadSprite("GramaFofa5","Tiles/GramaFofa 5.png");
        loadSprite("GramaMaisRalaSombra","Tiles/GramaMaisRala Sombra.png");
        loadSprite("GramaMaisRala1","Tiles/GramaMaisRala 1.png");
        loadSprite("GramaMaisRala2","Tiles/GramaMaisRala 2.png");
        loadSprite("GramaMaisRala3","Tiles/GramaMaisRala 3.png");
        loadSprite("GramaMaisRala4","Tiles/GramaMaisRala 4.png");
        loadSprite("GramaMaisRala5","Tiles/GramaMaisRala 5.png");
        loadSprite("GramaRala1","Tiles/GramaRala 1.png");
        loadSprite("GramaRala2","Tiles/GramaRala 2.png");
        loadSprite("GramaRala3","Tiles/GramaRala 3.png");
        loadSprite("GramaRala4","Tiles/GramaRala 4.png");
        loadSprite("GramaRala5","Tiles/GramaRala 5.png");
        loadSprite("GramaRala6","Tiles/GramaRala 6.png");
        loadSprite("GramaRala7","Tiles/GramaRala 7.png");
        loadSprite("GramaRala8","Tiles/GramaRala 8.png");

        loadSprite("Cerca1","Tiles/Cerca 1.png");
        loadSprite("Cerca2","Tiles/Cerca 2.png");
        loadSprite("Cerca3","Tiles/Cerca 3.png");
        loadSprite("Cerca4","Tiles/Cerca 4.png");
        loadSprite("Cerca5","Tiles/Cerca 5.png");
        loadSprite("Cerca6","Tiles/Cerca 6.png");
        loadSprite("Cerca7","Tiles/Cerca 7.png");
        loadSprite("Cerca8","Tiles/Cerca 8.png");
        loadSprite("Cerca9","Tiles/Cerca 9.png");
        loadSprite("Cerca10","Tiles/Cerca 10.png");
        loadSprite("Cerca11","Tiles/Cerca 11.png");

        loadSprite("CercaFloresta1","Tiles/CercaFloresta 1.png");
        loadSprite("CercaFloresta2","Tiles/CercaFloresta 2.png");
        loadSprite("CercaFloresta3","Tiles/CercaFloresta 3.png");
        loadSprite("CercaFloresta4","Tiles/CercaFloresta 4.png");
        loadSprite("CercaFloresta5","Tiles/CercaFloresta 5.png");
        loadSprite("CercaFloresta6","Tiles/CercaFloresta 6.png");
        loadSprite("CercaFloresta7","Tiles/CercaFloresta 7.png");
        loadSprite("CercaFloresta8","Tiles/CercaFloresta 8.png");
        loadSprite("CercaFloresta9","Tiles/CercaFloresta 9.png");

        loadSprite("Pedra","Tiles/pedras.png");
        loadSprite("Tronco1","Tiles/Tronco 1.png");
        loadSprite("Tronco2","Tiles/Tronco 2.png");
        loadSprite("Tronco3","Tiles/Tronco 3.png");
        loadSprite("Tronco4","Tiles/Tronco 4.png");
        loadSprite("Cruz","Tiles/cruz.png");
        loadSprite("Flor","Tiles/flor.png");

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

package Graphics;

import Entities.*;
import Entities.Enemies.Bat;
import Entities.Enemies.Slime;
import Entities.Items.Arrow;
import Entities.Items.Crossbow;
import Entities.Items.MedicalKit;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

import java.awt.*;
import java.util.ArrayList;

import static com.badlogic.gdx.math.MathUtils.random;

public class LevelBuilder {
    private LoadSprites loader;

    public LevelBuilder(LoadSprites loader) {
        this.loader = loader;
    }

    public Player createPlayer(int x, int y, LoadSprites loader) {
        return new Player(x, y, 16, 16, loader.getSprites("Player"), loader);
    }
    public Crossbow createCrossbow(int x, int y){
        return new Crossbow(x, y, 16, 16, loader.getSprites("CrossbowAnimation"));
    }

    public ArrayList<Enemy> createEnemies(ArrayList<Point> enemyPositions, Player player) {
        ArrayList<Enemy> enemies = new ArrayList<Enemy>();
        for(Point point : enemyPositions) {
            int x = ((int) point.getX()) * 16;
            int y = ((int) point.getY()) * 16;
            int opcaoEscolhida = random.nextInt(3);

            if(opcaoEscolhida == 1) {
                Array<TextureRegion> enemySprites = loader.getSprites("Slime");
                Enemy enemy = new Slime(x, y, 16, 16, enemySprites, player);
                enemies.add(enemy);
            }
            else{
                Array<TextureRegion> enemySprites = loader.getSprites("Bat");
                Enemy enemy = new Bat(x, y, 16, 16, enemySprites, player);
                enemies.add(enemy);
            }
        }
        return enemies;
    }
    public ArrayList<MedicalKit> createMedicalKits(ArrayList<Point> medicalKitsPositions) {
        ArrayList<MedicalKit> medicalKits = new ArrayList<MedicalKit>();

        for (Point point : medicalKitsPositions) {
            int x = ((int) point.getX()) * 16;
            int y = ((int) point.getY()) * 16;

            Array<TextureRegion> medicalKitSprites = loader.getSprites("Medical Kit");
            MedicalKit medicalKit = new MedicalKit(x, y, 16, 16, medicalKitSprites);

            medicalKits.add(medicalKit);
        }

        return medicalKits;
    }

    public ArrayList<Arrow> createArrows(ArrayList<Point> arrowsPositions) {
        ArrayList<Arrow> arrows = new ArrayList<Arrow>();

        for (Point point : arrowsPositions) {
            int x = ((int) point.getX()) * 16;
            int y = ((int) point.getY()) * 16;

            Texture arrowSprite = loader.getSprite("Arrow");
            Arrow arrow = new Arrow(x, y, 16, 16, (new TextureRegion(arrowSprite)));

            arrows.add(arrow);
        }

        return arrows;
    }
}

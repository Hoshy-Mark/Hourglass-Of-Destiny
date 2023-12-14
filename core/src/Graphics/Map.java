package Graphics;

import Entities.Tiles;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import java.awt.Point;
import java.util.ArrayList;

import java.util.HashMap;
import java.util.Random;

public class Map {
    private Pixmap pixmap;
    private TiledMap map = new TiledMap();
    private TiledMapTileLayer layer;
    private Random random = new Random();
    private HashMap<String, int[]> positions;
    private int red, green, blue;
    private Tiles[][] tiles;
    TextureRegion sprite;
    private ArrayList<Point> enemiesListed;
    private ArrayList<Point> arrowsListed;
    private ArrayList<Point> medicalKitsListed;

    public Map(String Map, LoadSprites Sprites) {
        this.positions = new HashMap<>();
        pixmap = new Pixmap(Gdx.files.internal(Map));
        layer = new TiledMapTileLayer(pixmap.getWidth(), pixmap.getHeight(), 16, 16); // 16x16 é o tamanho de cada tile
        tiles = new Tiles[pixmap.getWidth()][pixmap.getHeight()]; // Inicialização da nova matriz de Tiles
        enemiesListed = new ArrayList<>();
        arrowsListed = new ArrayList<>();
        medicalKitsListed = new ArrayList<>();

        for (int y = 0; y < pixmap.getHeight(); y++) {
            for (int x = 0; x < pixmap.getWidth(); x++) {

                int pixel = pixmap.getPixel(x, y);


                // Se o pixel não for totalmente opaco nem totalmente transparente
                if ((pixel & 0x000000ff) != 0) {
                    red = (pixel >>> 24) & 0xff;
                    green = (pixel >>> 16) & 0xff;
                    blue = (pixel >>> 8) & 0xff;

                    // Agora você pode usar os valores red, green e blue como quiser
                }

                switch (blue) {
                    case 0:
                        if (green == 0 && red == 0) {

                            String tile;

                            int opcaoEscolhida = random.nextInt(3);

                            if(opcaoEscolhida == 1){
                             tile = "Grass1";
                            }
                            else if(opcaoEscolhida == 2){
                                tile = "Grass2";
                            }
                            else{
                                tile = "Grass3";
                            }

                            setCellAndTile(x, y,tile,new Tiles(x, y, 16, 16, sprite, "floor"),Sprites);

                        }
                        else if(green == 255 && red==76){
                            // chao (pixel verde claro)
                            setCellAndTile(x, y,"Grass1",new Tiles(x, y, 16, 16, sprite, "floor"),Sprites);
                        }
                        else if(green == 127 && red==38){
                            setCellAndTile(x, y,"Grass2",new Tiles(x, y, 16, 16, sprite,"floor"),Sprites);
                        }
                        else if(green == 0 && red==255){
                            //inimigo (pixel vermelho)
                            int opcaoEscolhida = random.nextInt(3);

                            if(opcaoEscolhida == 1) {
                                enemiesListed.add(new Point(x, y));
                            }
                            setCellAndTile(x, y,"Grass2",new Tiles(x, y, 16, 16, sprite, "floor"),Sprites);
                        }
                        else if(green == 0 && red== 158){
                            //king slime (pixel vermelho escuro)
                            enemiesListed.add(new Point(x, y));
                            setCellAndTile(x, y,"Grass1",new Tiles(x, y, 16, 16, sprite, "floor"),Sprites);
                        }
                        else if(green == 106 && red==255){
                            //crossbow(pixel Laranja)
                            setCellAndTile(x, y,"Grass1",new Tiles(x, y, 16, 16, sprite, "floor"),Sprites);
                            int[] xy = new int[2];
                            xy[0] = x;
                            xy[1] = y;
                            positions.put("Crossbow", xy);
                        }
                        else if(green == 216 && red== 255){
                            //flecha(pixel amarelo)
                            arrowsListed.add(new Point(x, y));
                            setCellAndTile(x, y,"Grass3",new Tiles(x, y, 16, 16, sprite, "floor"),Sprites);

                        }
                        else if(green == 53 && red== 122){
                            //bau (pixel marrom)
                            setCellAndTile(x, y,"Grass1",new Tiles(x, y, 16, 16, sprite, "floor"),Sprites);

                        }
                        else if(green == 100 && red == 100){
                            //espada (pixel verde musgo)
                            setCellAndTile(x, y, "Grass2", new Tiles(x, y, 16, 16, sprite, "floor"), Sprites);
                            int[] xy = new int[2];
                            xy[0] = x;
                            xy[1] = y;
                            positions.put("Sword", xy);
                        }

                        // outros casos com blue == 0
                        break;
                    case 127:
                        if(green == 127 && red== 255){
                            //medical kit (pixel bege)
                            medicalKitsListed.add(new Point(x, y));
                            setCellAndTile(x, y,"Grass3",new Tiles(x, y, 16, 16, sprite, "floor"),Sprites);
                        }
                        break;
                    case 128:
                        if(green == 128 && red== 128){
                            // chao (Pixel Cinza)
                            setCellAndTile(x, y,"Grass3",new Tiles(x, y, 16, 16, sprite, "floor"),Sprites);
                        }
                        break;
                    case 255:
                        if(green == 255 && red== 255){
                            // parede (pixel branco)
                            setCellAndTile(x, y,"Stone2",new Tiles(x, y, 16, 16,  sprite, "wall"),Sprites);

                        }
                        else if(green == 38 && red==0){
                            //player (azul)
                            setCellAndTile(x, y,"Grass3",new Tiles(x, y, 16, 16, sprite,"floor"),Sprites);
                            int[] xy = new int[2];
                            xy[0] = x;
                            xy[1] = y;
                            positions.put("Player", xy);
                        }
                        else if(green == 255 && red==0){
                            //parte de cima do portal (pixel azul claro)
                            setCellAndTile(x, y,"Grass2",new Tiles(x, y, 16, 16,  sprite, "floor"),Sprites);
                            int[] xy = new int[2];
                            xy[0] = x;
                            xy[1] = y;
                            positions.put("PortalDown", xy);
                        }
                        else if(green == 132 && red== 0){
                            //parte de baixo do portal (pixel azul escuro)
                            setCellAndTile(x, y,"Grass2",new Tiles(x, y, 16, 16, sprite, "floor"),Sprites);
                            int[] xy = new int[2];
                            xy[0] = x;
                            xy[1] = y;
                            positions.put("PortalUp", xy);
                        }
                        break;
                }
            }
            // finalize adicionando a layer no mapa
            map.getLayers().add(layer);
        }
    }
    public int[] getPosition(String key){
        return positions.get(key);
    }
    public TiledMap getTiledMap() {
        return map;
    }
    public Tiles getTileAt(int x, int y) {
        return tiles[x][y];
    }
    public ArrayList<Point> getEnemiesListed() {
        return enemiesListed;
    }

    public ArrayList<Point> getArrowsListed() {
        return arrowsListed;
    }

    public ArrayList<Point> getMedicalKitsListed() {
        return medicalKitsListed;
    }

    private void setCellAndTile(int x, int y, String spriteName, Tiles tile, LoadSprites Sprites) {
        TextureRegion sprite = new TextureRegion(Sprites.getSprite(spriteName));
        TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
        cell.setTile(new StaticTiledMapTile(sprite));
        layer.setCell(x, y, cell);
        tiles[x][y] = tile;
    }
    public Tiles[][] getTiles() {
        return tiles;
    }
}

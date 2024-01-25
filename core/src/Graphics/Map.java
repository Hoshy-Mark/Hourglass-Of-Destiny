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
    private TiledMapTileLayer layer1;
    private TiledMapTileLayer layer2;
    private Random random = new Random();
    private HashMap<String, int[]> positions;
    private int red, green, blue;
    private Tiles[][] tiles;
    TextureRegion sprite;
    private ArrayList<Point> enemiesListed;
    private ArrayList<Point> arrowsListed;
    private ArrayList<Point> medicalKitsListed;
    private ArrayList<Point> chestListed;

    public Map(String Map, LoadSprites Sprites) {
        this.positions = new HashMap<>();
        pixmap = new Pixmap(Gdx.files.internal(Map));

        // Criando as camadas
        layer1 = new TiledMapTileLayer(pixmap.getWidth(), pixmap.getHeight(), 64, 64);
        layer2 = new TiledMapTileLayer(pixmap.getWidth(), pixmap.getHeight(), 64, 64);

        tiles = new Tiles[pixmap.getWidth()][pixmap.getHeight()]; // Inicialização da nova matriz de Tiles

        enemiesListed = new ArrayList<>();
        chestListed = new ArrayList<>();
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

                            if (opcaoEscolhida == 1) {
                                tile = "GramaRala1";
                            } else if (opcaoEscolhida == 2) {
                                tile = "GramaMaisRala1";
                            } else {
                                tile = "GramaFofa1";
                            }

                            setCellAndTile(x, y, tile, new Tiles(x, y, 16, 16, sprite, "floor"), Sprites,1);

                        } else if (green == 255 && red == 76) {
                            // chao (pixel verde claro)
                            setCellAndTile(x, y, "GramaRala1", new Tiles(x, y, 16, 16, sprite, "floor"), Sprites,1);
                        } else if (green == 127 && red == 38) {
                            setCellAndTile(x, y, "GramaRala2", new Tiles(x, y, 16, 16, sprite, "floor"), Sprites,1);
                        } else if (green == 0 && red == 255) {
                            //inimigo (pixel vermelho)
                            int opcaoEscolhida = random.nextInt(3);

                            if (opcaoEscolhida == 1) {
                                enemiesListed.add(new Point(x, y));
                            }
                            setCellAndTile(x, y, "GramaRala2", new Tiles(x, y, 16, 16, sprite, "floor"), Sprites,1);
                        } else if (green == 0 && red == 158) {
                            //king slime (pixel vermelho escuro)
                            enemiesListed.add(new Point(x, y));
                            setCellAndTile(x, y, "Grass1", new Tiles(x, y, 16, 16, sprite, "floor"), Sprites,1);
                        } else if (green == 106 && red == 255) {
                            //crossbow(pixel Laranja)
                            setCellAndTile(x, y, "GramaRala3", new Tiles(x, y, 16, 16, sprite, "floor"), Sprites,1);
                            int[] xy = new int[2];
                            xy[0] = x;
                            xy[1] = y;
                            positions.put("Crossbow", xy);
                        } else if (green == 216 && red == 255) {
                            //flecha(pixel amarelo)
                            arrowsListed.add(new Point(x, y));
                            setCellAndTile(x, y, "GramaRala4", new Tiles(x, y, 16, 16, sprite, "floor"), Sprites,1);

                        } else if (green == 53 && red == 122) {
                            //bau (pixel marrom)
                            chestListed.add(new Point(x, y));
                            setCellAndTile(x, y, "Grass1", new Tiles(x, y, 16, 16, sprite, "floor"), Sprites,1);

                        } else if (green == 100 && red == 100) {
                            //grama (pixel verde musgo)
                            setCellAndTile(x, y, "GramaRala5", new Tiles(x, y, 16, 16, sprite, "floor"), Sprites,1);
                        }

                        //Cercas da Floresta

                        else if (green == 255 && red == 255) {
                            setCellAndTile(x, y, "CercaFloresta1", new Tiles(x, y, 16, 16, sprite, "wall"), Sprites, 2);
                            setCellAndTile(x, y, "GramaRala2", new Tiles(x, y, 16, 16, sprite, "wall"), Sprites, 1);
                        } else if (green == 240 && red == 240) {
                            setCellAndTile(x, y, "CercaFloresta2", new Tiles(x, y, 16, 16, sprite, "floor"), Sprites, 2);
                            setCellAndTile(x, y, "GramaRala1", new Tiles(x, y, 16, 16, sprite, "floor"), Sprites, 1);
                        } else if (green == 230 && red == 230) {
                            setCellAndTile(x, y, "CercaFloresta3", new Tiles(x, y, 16, 16, sprite, "floor"), Sprites, 2);
                            setCellAndTile(x, y, "GramaRala1", new Tiles(x, y, 16, 16, sprite, "floor"), Sprites, 1);
                        } else if (green == 220 && red == 220) {
                            setCellAndTile(x, y, "CercaFloresta4", new Tiles(x, y, 16, 16, sprite, "wall"), Sprites, 2);
                            setCellAndTile(x, y, "GramaRala1", new Tiles(x, y, 16, 16, sprite, "wall"), Sprites, 1);
                        } else if (green == 210 && red == 210) {
                            setCellAndTile(x, y, "CercaFloresta5", new Tiles(x, y, 16, 16, sprite, "wall"), Sprites, 2);
                            setCellAndTile(x, y, "GramaRala8", new Tiles(x, y, 16, 16, sprite, "wall"), Sprites, 1);
                        } else if (green == 200 && red == 200) {
                            setCellAndTile(x, y, "CercaFloresta6", new Tiles(x, y, 16, 16, sprite, "wall"), Sprites, 2);
                            setCellAndTile(x, y, "GramaRala8", new Tiles(x, y, 16, 16, sprite, "wall"), Sprites, 1);
                        } else if (green == 190 && red == 190) {
                            setCellAndTile(x, y, "CercaFloresta7", new Tiles(x, y, 16, 16, sprite, "wall"), Sprites, 2);
                            setCellAndTile(x, y, "GramaRala1", new Tiles(x, y, 16, 16, sprite, "wall"), Sprites, 1);
                        } else if (green == 180 && red == 180) {
                            setCellAndTile(x, y, "CercaFloresta8", new Tiles(x, y, 16, 16, sprite, "wall"), Sprites, 2);
                            setCellAndTile(x, y, "GramaRala8", new Tiles(x, y, 16, 16, sprite, "wall"), Sprites, 1);
                        } else if (green == 170 && red == 170) {
                            setCellAndTile(x, y, "CercaFloresta9", new Tiles(x, y, 16, 16, sprite, "wall"), Sprites, 2);
                            setCellAndTile(x, y, "GramaRala1", new Tiles(x, y, 16, 16, sprite, "wall"), Sprites, 1);
                        }

                        // outros casos com blue == 0
                        break;
                    case 127:
                        if (green == 127 && red == 255) {
                            //medical kit (pixel bege)
                            medicalKitsListed.add(new Point(x, y));
                            setCellAndTile(x, y, "GramaRala7", new Tiles(x, y, 16, 16, sprite, "floor"), Sprites,1);
                        }
                        break;
                    case 128:
                        if (green == 128 && red == 128) {
                            // chao (Pixel Cinza)
                            setCellAndTile(x, y, "GramaRala8", new Tiles(x, y, 16, 16, sprite, "floor"), Sprites,1);
                        }
                        break;
                    case 255:

                        //Cercas da Fazenda

                        if (green == 255 && red == 255) {
                            // parede (pixel branco)
                            setCellAndTile(x, y, "Cerca2", new Tiles(x, y, 16, 16, sprite, "wall"), Sprites,2);
                            setCellAndTile(x, y, "GramaRala1", new Tiles(x, y, 16, 16, sprite, "wall"), Sprites,1);
                        }
                        else if (green == 240 && red == 240){
                            setCellAndTile(x, y, "Cerca9", new Tiles(x, y, 16, 16, sprite, "wall"), Sprites,2);
                            setCellAndTile(x, y, "GramaRala1", new Tiles(x, y, 16, 16, sprite, "wall"), Sprites,1);

                        }
                        else if (green == 230 && red == 230){
                            setCellAndTile(x, y, "Cerca8", new Tiles(x, y, 16, 16, sprite, "wall"), Sprites,2);
                            setCellAndTile(x, y, "GramaRala1", new Tiles(x, y, 16, 16, sprite, "wall"), Sprites,1);

                        }
                        else if (green == 220 && red == 220){
                            setCellAndTile(x, y, "Cerca2", new Tiles(x, y, 16, 16, sprite, "wall"), Sprites,2);
                            setCellAndTile(x, y, "GramaRala1", new Tiles(x, y, 16, 16, sprite, "wall"), Sprites,1);
                        }
                        else if (green == 210 && red == 210){
                            setCellAndTile(x, y, "Cerca4", new Tiles(x, y, 16, 16, sprite, "wall"), Sprites,2);
                            setCellAndTile(x, y, "GramaRala1", new Tiles(x, y, 16, 16, sprite, "wall"), Sprites,1);
                        }
                        else if (green == 200 && red == 200){
                            setCellAndTile(x, y, "Cerca1", new Tiles(x, y, 16, 16, sprite, "wall"), Sprites,2);
                            setCellAndTile(x, y, "GramaRala1", new Tiles(x, y, 16, 16, sprite, "wall"), Sprites,1);
                        }
                        else if (green == 190 && red == 190){
                            setCellAndTile(x, y, "Cerca6", new Tiles(x, y, 16, 16, sprite, "wall"), Sprites,2);
                            setCellAndTile(x, y, "GramaRala1", new Tiles(x, y, 16, 16, sprite, "wall"), Sprites,1);
                        }
                        else if (green == 180 && red == 180){
                            setCellAndTile(x, y, "Cerca7", new Tiles(x, y, 16, 16, sprite, "wall"), Sprites,2);
                            setCellAndTile(x, y, "GramaRala1", new Tiles(x, y, 16, 16, sprite, "wall"), Sprites,1);
                        }
                        else if (green == 140 && red == 140){
                            setCellAndTile(x, y, "Cerca10", new Tiles(x, y, 16, 16, sprite, "wall"), Sprites,2);
                            setCellAndTile(x, y, "GramaRala1", new Tiles(x, y, 16, 16, sprite, "wall"), Sprites,1);
                        }
                        else if (green == 130 && red == 130){
                            setCellAndTile(x, y, "Cerca11", new Tiles(x, y, 16, 16, sprite, "wall"), Sprites,2);
                            setCellAndTile(x, y, "GramaRala1", new Tiles(x, y, 16, 16, sprite, "wall"), Sprites,1);
                        }


                        else if (green == 38 && red == 0) {
                            //player (azul)
                            setCellAndTile(x, y, "GramaMaisRala1", new Tiles(x, y, 16, 16, sprite, "floor"), Sprites,1);
                            int[] xy = new int[2];
                            xy[0] = x;
                            xy[1] = y;
                            positions.put("Player", xy);
                        } else if (green == 255 && red == 0) {
                            //parte de cima do portal (pixel azul claro)
                            setCellAndTile(x, y, "ChaoDePedra", new Tiles(x, y, 16, 16, sprite, "floor"), Sprites, 1);
                            int[] xy = new int[2];
                            xy[0] = x;
                            xy[1] = y;
                            positions.put("PortalDown", xy);
                        } else if (green == 132 && red == 0) {
                            //parte de baixo do portal (pixel azul escuro)
                            setCellAndTile(x, y, "ChaoDePedra", new Tiles(x, y, 16, 16, sprite, "floor"), Sprites,1);
                            int[] xy = new int[2];
                            xy[0] = x;
                            xy[1] = y;
                            positions.put("PortalUp", xy);
                        }
                        else if (green == 40 && red == 90){
                            //Cruz
                            setCellAndTile(x, y, "Cruz", new Tiles(x, y, 16, 16, sprite, "wall"), Sprites,2);
                            setCellAndTile(x, y, "GramaMaisRala1", new Tiles(x, y, 16, 16, sprite, "wall"), Sprites,1);
                        }

                        else if (green == 150 && red == 150){
                            //Tronco 2
                           setCellAndTile(x, y, "Tronco2", new Tiles(x, y, 16, 16, sprite, "wall"), Sprites,2);
                           setCellAndTile(x, y, "GramaRala8", new Tiles(x, y, 16, 16, sprite, "wall"), Sprites,1);
                        }
                        else if (green == 160 && red == 160){
                            //Tronco 1
                           setCellAndTile(x, y, "Tronco1", new Tiles(x, y, 16, 16, sprite, "wall"), Sprites,2);
                           setCellAndTile(x, y, "GramaRala8", new Tiles(x, y, 16, 16, sprite, "wall"), Sprites,1);
                        }
                        else if (green == 170 && red == 170){
                            //Tronco 3
                            setCellAndTile(x, y, "Tronco3", new Tiles(x, y, 16, 16, sprite, "wall"), Sprites,2);
                            setCellAndTile(x, y, "GramaRala8", new Tiles(x, y, 16, 16, sprite, "wall"), Sprites,1);
                        }
                        else if (green == 70 && red == 70){
                            //Tronco 4
                            setCellAndTile(x, y, "Tronco4", new Tiles(x, y, 16, 16, sprite, "wall"), Sprites,2);
                            setCellAndTile(x, y, "GramaRala8", new Tiles(x, y, 16, 16, sprite, "wall"), Sprites,1);
                        }
                        else if (green == 60 && red == 60){
                            //Pedra
                            setCellAndTile(x, y, "Pedra", new Tiles(x, y, 16, 16, sprite, "wall"), Sprites,2);
                            setCellAndTile(x, y, "GramaRala8", new Tiles(x, y, 16, 16, sprite, "wall"), Sprites,1);
                        }
                        else if (green == 50 && red == 50){
                            //Flor
                            setCellAndTile(x, y, "Flor", new Tiles(x, y, 16, 16, sprite, "wall"), Sprites,2);
                            setCellAndTile(x, y, "GramaRala8", new Tiles(x, y, 16, 16, sprite, "floor"), Sprites,1);
                        }
                        break;
                }
            }
            //Adicionando as camadas no mapa
            map.getLayers().add(layer1);
            map.getLayers().add(layer2);
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

    private void setCellAndTile(int x, int y, String spriteName, Tiles tile, LoadSprites Sprites, int camada) {
        TextureRegion sprite = new TextureRegion(Sprites.getSprite(spriteName));
        TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
        cell.setTile(new StaticTiledMapTile(sprite));
        if(camada == 1){
            layer1.setCell(x, y, cell);
            tiles[x][y] = tile;
        }
        else{
            layer2.setCell(x, y, cell);
            tiles[x][y] = tile;
        }
    }
    public Tiles[][] getTiles() {
        return tiles;
    }

    public ArrayList<Point> getChestListed() {
        return chestListed;
    }
}

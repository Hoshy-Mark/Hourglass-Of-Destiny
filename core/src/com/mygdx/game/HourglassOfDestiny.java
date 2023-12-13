package com.mygdx.game;

import Entities.*;
import Entities.Items.Arrow;
import Entities.Items.Crossbow;
import Entities.Items.MedicalKit;
import Entities.Items.Sword;
import Graphics.LevelBuilder;
import Graphics.LoadSprites;
import Graphics.Map;
import Graphics.PlayerUI;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;

import java.util.ArrayList;
import java.util.Iterator;

public class HourglassOfDestiny extends ApplicationAdapter {
	private SpriteBatch batch;
	private OrthographicCamera camera;
	private PlayerUI playerUI;

	private Player player; // Fazer 'player' uma variável global
	private Crossbow crossbowItem;
	private Entities.Guns.Crossbow crossbowGun;
	private Map map;
	private OrthogonalTiledMapRenderer mapRenderer;
	private ArrayList<Enemy> enemies;
	LoadSprites loader;
	private LevelBuilder levelBuilder;

	private ArrayList<MedicalKit> medicalKits;
	private ArrayList<Arrow> arrows;
	private ArrayList<Bullet> bullets;
	private int currentNivel;
	private Portal portalUp;
	private Portal portalDown;
	private Sword swordItem;
	private Entities.Blades.Sword sword;
	@Override
	public void create () {
		batch = new SpriteBatch();
		camera = new OrthographicCamera(1280, 720);
		loader = new LoadSprites();
		currentNivel = 0;

		map = new Map("Levels/level"+currentNivel+".png",loader);
		levelBuilder = new LevelBuilder(loader);

		player = levelBuilder.createPlayer(map.getPosition("Player")[0]*16, map.getPosition("Player")[1]*16, loader);
		enemies = levelBuilder.createEnemies(map.getEnemiesListed(), player);
		mapRenderer = new OrthogonalTiledMapRenderer(map.getTiledMap(), 1f, batch);
		playerUI = new PlayerUI(batch, player);
		medicalKits = levelBuilder.createMedicalKits(map.getMedicalKitsListed());
		arrows = levelBuilder.createArrows(map.getArrowsListed());
		crossbowItem = levelBuilder.createCrossbow(map.getPosition("Crossbow")[0]*16, map.getPosition("Crossbow")[1]*16);
		crossbowGun = null;
		swordItem = levelBuilder.createSword(map.getPosition("Sword")[0]*16,map.getPosition("Sword")[1]*16);
		bullets = new ArrayList<Bullet>();
		portalUp = levelBuilder.createPortalUp(map.getPosition("PortalUp")[0]*16, map.getPosition("PortalUp")[1]*16);
		portalDown = levelBuilder.createPortalDown(map.getPosition("PortalDown")[0]*16, map.getPosition("PortalDown")[1]*16);

	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Limpar a tela
		float delta = Gdx.graphics.getDeltaTime();  // Obtem o tempo entre frames

		player.act(delta, map, enemies, crossbowGun, swordItem);

		if (enemies.isEmpty() && Entity.isColliding(player, portalUp)) {
			currentNivel++;
			System.out.println(currentNivel);
			if (currentNivel < 0) currentNivel = 0;  // Limita o nível mínimo a 0
			reloadLevel();
		}

		if (enemies.isEmpty() && Entity.isColliding(player, portalDown)) {
			currentNivel++;
			System.out.println(currentNivel);
			if (currentNivel < 0) currentNivel = 0;  // Limita o nível mínimo a 0
			reloadLevel();
		}

		if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
			if(player.getCrossbow() != null && player.getAmmunition() > 0 && player.getShootElapsedTime() >= 0.3f){
				bullets.add(player.getCrossbow().shoot());
				player.setAmmunition(player.getAmmunition() - 1); // diminui a munição do jogador a cada tiro
				player.setShootElapsedTime(0);  // reset the shootElapsedTime each time you shoot.
			}
		}
		for(Enemy enemy : enemies) {
			enemy.act(delta);
		}

		portalUp.act(delta);
		portalDown.act(delta);

		for(MedicalKit medkit : medicalKits) {
			medkit.update(delta);
		}

		if(crossbowItem != null) {
			crossbowItem.update(delta);
		}

		camera.zoom = 0.2f; // 2 vezes mais zoom do que o normal
		camera.update();

		mapRenderer.setView(camera); // Nova linha
		mapRenderer.render(); // Nova linha

		batch.setProjectionMatrix(camera.combined); // Define a matrix de projeção para a câmera

		batch.begin(); // Iniciar o desenho

		player.draw(batch); // Desenha o 'player'


		for(Enemy enemy : enemies) {
			enemy.draw(batch);
		}

		if(enemies.isEmpty()) {
			portalUp.draw(batch);
			portalDown.draw(batch);
		}

		if(crossbowItem != null && Entity.isColliding(player, crossbowItem) && crossbowGun == null) {
			crossbowGun = new Entities.Guns.Crossbow(player.getX(),player.getY(), 16, 16, loader.getSprites("Crossbow"), loader);
			crossbowItem = null;
		}

		if(player.getCrossbow() == null && crossbowItem != null && crossbowGun == null) {
			crossbowItem.draw(batch);
		}
		if(crossbowGun != null) {
			crossbowGun.setPosition(player.getX()+4,player.getY()-3);
			crossbowGun.draw(batch);
		}
		if(swordItem != null && Entity.isColliding(player, swordItem) && sword == null) {
			sword = new Entities.Blades.Sword(player.getX(),player.getY(), 16, 16, loader.getSprites("Sword"));
			swordItem = null;
		}

		if(player.getSword() == null && swordItem != null && sword == null) {
			swordItem.draw(batch);
		}

		for (Bullet bullet : bullets) {
			bullet.update(Gdx.graphics.getDeltaTime()); // Atualiza a posição da bala.
			batch.draw(bullet.getCurrentSprite(), bullet.getPosition().x, bullet.getPosition().y);
		}

		checkBulletCollision(delta);

		Iterator<MedicalKit> medkitIter = medicalKits.iterator();
		while(medkitIter.hasNext()){
			MedicalKit medkit = medkitIter.next();
			if(medkit != null && Entity.isColliding(player, medkit)){
				player.receiveHealth(medkit.use());
				medkitIter.remove();
			} else {
				medkit.update(delta);
				medkit.draw(batch);
			}
		}

		Iterator<Arrow> arrowIter = arrows.iterator();
		while(arrowIter.hasNext()){
			Arrow arrow = arrowIter.next();
			if(arrow != null && Entity.isColliding(player, arrow)){
				player.setAmmunition(player.getAmmunition() + 5);         // add this line to increase ammo
				arrowIter.remove();
			} else {
				arrow.draw(batch);
			}
		}

		Iterator<Bullet> bulletIterator = bullets.iterator();
		while (bulletIterator.hasNext()) {
			Bullet bullet = bulletIterator.next();
			bullet.update(Gdx.graphics.getDeltaTime()); // Atualiza a posição da bala.

			if (bullet.getPosition().x < 0 || bullet.getPosition().x > Gdx.graphics.getWidth()
					|| bullet.getPosition().y < 0 || bullet.getPosition().y > Gdx.graphics.getHeight()) {
				// Se a bala saiu da tela, remove-a da lista.
				bulletIterator.remove();
			} else {
				// Se a bala ainda está na tela, desenha-a.
				batch.draw(bullet.getCurrentSprite(), bullet.getPosition().x, bullet.getPosition().y);
			}
		}

		for(Arrow arrow : arrows) {
			arrow.draw(batch);
		}

		batch.end(); // Finalizar o desenho

		playerUI.update();
		playerUI.draw();

		updateCamera(); // Atualiza a câmera para seguir o 'player'
	}
	public void updateCamera() {
		float camX = MathUtils.clamp(player.getX(), -1000, 3600);
		float camY = MathUtils.clamp(player.getY(), -1000, 3600);
		camera.position.set(camX, camY, 0);
		camera.update();
	}
	@Override
	public void dispose () {
		batch.dispose();// Limpar os recursos
		mapRenderer.dispose(); // Disponibiliza os recursos do mapRenderer
		playerUI.dispose();
	}
	public void checkBulletCollision(float delta) {
		// Inicia a iteração sobre as balas
		Iterator<Bullet> bulletIterator = bullets.iterator();
		while (bulletIterator.hasNext()) {
			Bullet bullet = bulletIterator.next();

			// Inicia a iteração sobre os inimigos
			Iterator<Enemy> enemyIterator = enemies.iterator();
			while (enemyIterator.hasNext()) {
				Enemy enemy = enemyIterator.next();

				// Verifica se a bala está colidindo com o inimigo atual
				if (bullet.getPosition().x + bullet.getCurrentSprite().getRegionWidth() > enemy.getX()
						&& bullet.getPosition().x < enemy.getX() + enemy.getWidth()
						&& bullet.getPosition().y + bullet.getCurrentSprite().getRegionHeight() > enemy.getY()
						&& bullet.getPosition().y < enemy.getY() + enemy.getHeight()) {

					// Aqui implementamos o dano que a bala causa ao inimigo.
					enemy.setLife(enemy.getLife() - bullet.getDamage());

					enemy.setDamaged(true);

					// Se a vida do inimigo chegar a 0, removemos ele da lista de inimigos.
					if (enemy.getLife() <= 0) {
						enemy.setDamaged(true);
						enemy.act(delta);
						enemy.draw(batch);
						enemyIterator.remove();
					}

					// Removemos a bala da lista de balas depois de atingir o inimigo.
					bulletIterator.remove();

					// Interrompemos a repetição pois a bala já atingiu um inimigo e não pode atingir outro.
					break;
				}
			}
		}
	}
	private void reloadLevel(){
		map = new Map("Levels/level"+currentNivel+".png",loader);
		// A posição do jogador é atualizada, mas o objeto não é recriado.
		player.setPosition(map.getPosition("Player")[0]*16, map.getPosition("Player")[1]*16);
		enemies = levelBuilder.createEnemies(map.getEnemiesListed(), player);
		mapRenderer = new OrthogonalTiledMapRenderer(map.getTiledMap(), 1f, batch);
		medicalKits = levelBuilder.createMedicalKits(map.getMedicalKitsListed());
		arrows = levelBuilder.createArrows(map.getArrowsListed());
		if (map.getPosition("Crossbow") != null) {
			crossbowItem = levelBuilder.createCrossbow(map.getPosition("Crossbow")[0]*16, map.getPosition("Crossbow")[1]*16);
		} else {
			crossbowItem = null;
		}
		bullets = new ArrayList<Bullet>();
		portalUp = levelBuilder.createPortalUp(map.getPosition("PortalUp")[0]*16, map.getPosition("PortalUp")[1]*16);
		portalDown = levelBuilder.createPortalDown(map.getPosition("PortalDown")[0]*16, map.getPosition("PortalDown")[1]*16);
	}
}

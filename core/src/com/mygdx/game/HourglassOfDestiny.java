package com.mygdx.game;

import Entities.*;
import Entities.Blades.Sword;
import Entities.InteractiveObjects.Chest;
import Entities.Items.Arrow;
import Entities.Items.Crossbow;
import Entities.Items.MedicalKit;
import Graphics.ImagesUI;
import Entities.Items.Ring;
import Graphics.LevelBuilder;
import Graphics.LoadSprites;
import Graphics.Map;
import Graphics.PlayerUI;
import Settings.GameSettings;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import java.util.ArrayList;
import java.util.Iterator;
import Settings.SaveState;
import com.badlogic.gdx.utils.viewport.FitViewport;

import static com.badlogic.gdx.math.MathUtils.random;

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
	private ArrayList<Ring> rings;
	private ArrayList<InteractiveObject> objects;
	private ArrayList<Bullet> bullets;
	private int currentNivel = 0;
	private Portal portalUp;
	private Portal portalDown;
	private ArrayList<Chest> chest;
	private Entities.Blades.Sword sword;

	public static String gameState = "PLAY";

	private ImagesUI imageUI;
	private GameSettings gameSettings;
	private FitViewport viewport;


	@Override
	public void create() {
		Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
		batch = new SpriteBatch();
		camera = new OrthographicCamera(1920, 1080);
		viewport = new FitViewport(1920, 1080, camera);
		gameSettings = new GameSettings(viewport);

		loader = new LoadSprites();

		map = new Map("Levels/level" + currentNivel + ".png", loader);
		levelBuilder = new LevelBuilder(loader);
		mapRenderer = new OrthogonalTiledMapRenderer(map.getTiledMap(), 1f, batch);

		createPlayer();
		createEnemies();
		createGameItems();

		portalUp = levelBuilder.createPortalUp(map.getPosition("PortalUp")[0] * 16, map.getPosition("PortalUp")[1] * 16);
		portalDown = levelBuilder.createPortalDown(map.getPosition("PortalDown")[0] * 16, map.getPosition("PortalDown")[1] * 16);

		crossbowGun = null;
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Limpar a tela
		float delta = Gdx.graphics.getDeltaTime();  // Obtem o tempo entre frames

		if(gameState == "PLAY") {
			updatePlayer(delta);
			updateEnemies(delta);
			updateGameItems(delta);
			handleCollision(delta);
		}
		else if(gameState == "MENU"){

		}

		//Full Screen
		if(Gdx.input.isKeyPressed(Input.Keys.K)){
			Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
		}
		//Janela
		if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)){
			Gdx.graphics.setWindowedMode(1080, 720);
		}
		//Fechar o Jogo
		if(Gdx.input.isKeyPressed(Input.Keys.P)){
			Gdx.app.exit();
		}
		//Save
		if(Gdx.input.isKeyPressed(Input.Keys.ENTER)){
			SaveState saveState = new SaveState();
			saveState.saveGame((float)player.getLife(),player.getAmmunition());
		}
		//Load
		if(Gdx.input.isKeyPressed(Input.Keys.NUM_6)){
			SaveState saveState = new SaveState();
			player.setLife(saveState.getLife());
			player.setAmmunition(saveState.getAmmunition());
		}
		//Apagar Save
		if(Gdx.input.isKeyPressed(Input.Keys.NUM_0)){
			SaveState saveState = new SaveState();
			saveState.getPrefs().clear();
			saveState.getPrefs().flush(); // Isto salvará as preferências
		}

		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.zoom = 0.2f; // 2 vezes mais zoom do que o normal
		camera.update();

		mapRenderer.setView(camera); // Nova linha
		mapRenderer.render(); // Nova linha

		batch.setProjectionMatrix(camera.combined); // Define a matrix de projeção para a câmera

		batch.begin(); // Iniciar o desenho

		if(gameState == "PLAY") {
			renderPlayer();
			renderEnemies();
			renderGameItems();
		}

		else if(gameState == "MENU"){

			imageUI = new ImagesUI("ImagesUi/Menu"); // Substitua pelo nome da sua imagem
			// Defina a posição da imagem, se necessário
			imageUI.setPosition(-50, 245);

			// Renderize a imagem usando a classe ImagesUI
			imageUI.render(batch);

		}

		if(portalUp != null && portalDown != null) {
			if (enemies.isEmpty()) {
				portalDown.act(delta);
				portalUp.act(delta);
				portalUp.draw(batch);
				portalDown.draw(batch);
		 	}
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
	public void dispose() {
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
					enemy.setShotsHit(enemy.getShotsHit() + 1);

					enemy.setDamaged(true);

					// Se a vida do inimigo chegar a 0, removemos ele da lista de inimigos.
					if (enemy.getLife() <= 0) {
						enemy.setDamaged(true);
						enemy.act(delta);
						enemyIterator.remove();
						dropItems("Enemy", enemy.getX(), enemy.getY(), enemy.getShotsHit());
					}

					// Removemos a bala da lista de balas depois de atingir o inimigo.
					bulletIterator.remove();

					// Interrompemos a repetição pois a bala já atingiu um inimigo e não pode atingir outro.
					break;
				}
			}
		}
	}

	private void reloadLevel() {
		map = new Map("Levels/level" + currentNivel + ".png", loader);
		// A posição do jogador é atualizada, mas o objeto não é recriado.
		player.setPosition(map.getPosition("Player")[0] * 16, map.getPosition("Player")[1] * 16);
		enemies = levelBuilder.createEnemies(map.getEnemiesListed(), player);
		mapRenderer = new OrthogonalTiledMapRenderer(map.getTiledMap(), 1f, batch);
		medicalKits = levelBuilder.createMedicalKits(map.getMedicalKitsListed());
		arrows = levelBuilder.createArrows(map.getArrowsListed());
		if (map.getPosition("Crossbow") != null) {
			crossbowItem = levelBuilder.createCrossbow(map.getPosition("Crossbow")[0] * 16, map.getPosition("Crossbow")[1] * 16);
		} else {
			crossbowItem = null;
		}
		chest = levelBuilder.createChests(map.getChestListed());
		bullets = new ArrayList<Bullet>();
		portalUp = levelBuilder.createPortalUp(map.getPosition("PortalUp")[0] * 16, map.getPosition("PortalUp")[1] * 16);
		portalDown = levelBuilder.createPortalDown(map.getPosition("PortalDown")[0] * 16, map.getPosition("PortalDown")[1] * 16);
	}

	private void createPlayer() {
		player = levelBuilder.createPlayer(map.getPosition("Player")[0] * 16, map.getPosition("Player")[1] * 16, loader);
		playerUI = new PlayerUI(batch, player);
		Sword sword = new Sword(player.getX() - 8, player.getY() + 3, 16, 16, loader.getSprites("Sword"));
		player.setSword(sword);
	}

	private void createEnemies() {
		enemies = levelBuilder.createEnemies(map.getEnemiesListed(), player);
	}

	private void createGameItems() {

		// Criar kits médicos
		medicalKits = levelBuilder.createMedicalKits(map.getMedicalKitsListed());

		// Criar flechas
		arrows = levelBuilder.createArrows(map.getArrowsListed());

		// Criar baús
		chest = levelBuilder.createChests(map.getChestListed());

		// Criar besta
		crossbowItem = levelBuilder.createCrossbow(map.getPosition("Crossbow")[0] * 16, map.getPosition("Crossbow")[1] * 16);

		// Inicializar balas
		bullets = new ArrayList<Bullet>();

		// Inicializar Objetos Interativos
		objects = new ArrayList<InteractiveObject>();

		rings = new ArrayList<Ring>();
	}

	private void updatePlayer(float delta) {
		player.act(delta, map, enemies, bullets, objects, camera);

		// Verifica colisão com a Besta
		if (crossbowItem != null && Entity.isColliding(player, crossbowItem) && crossbowGun == null) {
			crossbowGun = new Entities.Guns.Crossbow(player.getX(), player.getY(), 16, 16, loader.getSprites("Crossbow"), loader);
			crossbowItem = null;
			player.setCrossbow(crossbowGun);
		}

		// Verifica colisões com o MedicalKit
		Iterator<MedicalKit> medkitIter = medicalKits.iterator();
		while (medkitIter.hasNext()) {
			MedicalKit medkit = medkitIter.next();
			if (medkit != null && Entity.isColliding(player, medkit)) {
				player.receiveHealth(medkit.use());
				medkitIter.remove();
			}
		}

		// Verifica colisões com o Rings
		Iterator<Ring> ring = rings.iterator();
		while (ring.hasNext()) {
			Ring ring1 = ring.next();
			if (ring1 != null && Entity.isColliding(player, ring1)) {
				if(ring1.getType() == "speed"){
					player.improveSpeed(ring1.getImprove());
				} else if(ring1.getType() == "life"){
					player.improveLife(ring1.getImprove());
				}
				ring.remove();
			}
		}

		// Verifica colisões com Arrow
		Iterator<Arrow> arrowIter = arrows.iterator();
		while (arrowIter.hasNext()) {
			Arrow arrow = arrowIter.next();
			if (arrow != null && Entity.isColliding(player, arrow)) {
				player.setAmmunition(player.getAmmunition() + arrow.getAmmunition());
				arrowIter.remove();
			}
		}
	}

	private void updateEnemies(float delta) {
		for (Enemy enemy : enemies) {
			enemy.act(delta);
		}

		Iterator<Enemy> enemyIterator = enemies.iterator();
		while (enemyIterator.hasNext()) {
			Enemy enemy = enemyIterator.next();

			if(player.getSword() != null) {
				Blade blade = player.getSword();
				if (blade.isAttacking() && Entity.isColliding(blade, enemy)) {
					enemy.setLife(enemy.getLife() - blade.getDamage());
					enemy.setDamaged(true);
				}
				// Verifica se a vida do inimigo é menor ou igual a zero
				if (enemy.getLife() <= 0) {
					enemyIterator.remove(); // Se sim, remove o inimigo
				}
			}
		}
	}

	private void updateGameItems(float delta) {
		// atualizar medkits
		for (MedicalKit medkit : medicalKits) {
			medkit.update(delta);
		}

		// atualizar crossbowItem se existir alguma
 		if (crossbowItem != null) {
			crossbowItem.update(delta);
		}

		// atualizar balas
		Iterator<Bullet> bulletIterator = bullets.iterator();
		while (bulletIterator.hasNext()) {
			Bullet bullet = bulletIterator.next();
			bullet.update(delta); // Atualiza a posição da bala.

			if (bullet.isCollidingWithWall(map.getTiles())) {  // Verifique a colisão com a parede
				// Se a bala colidiu com uma parede, remove-a da lista.
				bulletIterator.remove();
			}
		}
	}

	private void renderPlayer() {
		player.draw(batch); // Desenha o 'player'

		// Desenha a besta se o jogador tiver uma
		if (player.getCrossbow() == null && crossbowItem != null && crossbowGun == null) {
			crossbowItem.draw(batch);
		}
	}

	private void renderEnemies() {
		for (Enemy enemy : enemies) {
			enemy.draw(batch);
		}
	}

	private void renderGameItems() {
		// Renderize os medkits
		for (MedicalKit medkit : medicalKits) {
			medkit.draw(batch);
		}

		// Renderiza os Anéis
		if(rings != null) {
			for (Ring ring : rings) {
				ring.draw(batch);
			}
		}

		// Renderize o item crossbow se o mesmo existir
		if (crossbowItem != null) {
			crossbowItem.draw(batch);
		}

		// Renderize as setas
		for (Arrow arrow : arrows) {
			arrow.draw(batch);
		}

		// Renderize as balas
		for (Bullet bullet : bullets) {
			batch.draw(bullet.getCurrentSprite(), bullet.getPosition().x, bullet.getPosition().y);
		}

		// Renderiza o Chest
		if (chest != null && enemies.isEmpty()) {
			for (Chest chest1 : chest) {
				objects.add(chest1);
				chest1.draw(batch);
			}
		}
	}

	private void handleCollision(float delta) {
		// Verifica se o jogador colide com o portal
		if(portalUp != null && portalDown != null) {
			if (enemies.isEmpty() && Entity.isColliding(player, portalUp) || enemies.isEmpty() && Entity.isColliding(player, portalDown)) {
				levelUp();
			}
		}
		// Código para verificar a colisão entre a espada do jogador e os inimigos
		handlePlayerEnemyCollision(delta);

		// Código para verificar a colisão entre o jogador e um baú
		handlePlayerChestCollision();

		// Checa se o jogador colide com a besta
		handlePlayerCrossbowCollision();

		// Checa colisões de balas
		checkBulletCollision(delta);

		// Código para verificar a colisão entre o jogador e os medkits
		handlePlayerMedicalKitCollision();

		// Código para verificar a colisão entre o jogador e as flechas
		handlePlayerArrowCollision();
	}

	private void handlePlayerEnemyCollision(float delta) {
		Iterator<Enemy> enemyIterator = enemies.iterator();
		while (enemyIterator.hasNext()) {
			Enemy enemy = enemyIterator.next();

			if(player.getSword() != null) {
				Blade blade = player.getSword();
				if (blade.isAttacking() && Entity.isColliding(blade, enemy)) {
					enemy.setLife(enemy.getLife() - blade.getDamage());
					enemy.setDamaged(true);
				}
				// Verifica se a vida do inimigo é menor ou igual a zero
				if (enemy.getLife() <= 0) {
					enemyIterator.remove(); // Se sim, remove o inimigo
				}
			}
		}
	}

	private void handlePlayerChestCollision() {
		if (enemies.isEmpty() && chest != null) {
			Iterator<Chest> chestIterator = chest.iterator();
			while (chestIterator.hasNext()) {
				Chest chest1 = chestIterator.next();

				if (chest1 != null && Entity.isColliding(player, chest1.getInteractionRect())) {
					if (chest1.isOpened() == false) {
						chest1.openChest();
						dropItems("Chest", chest1.getX(), chest1.getY() - 16, 10);
					}
				}
			}
		}
	}

	private void handlePlayerCrossbowCollision() {
		// Verifica colisão com a Besta
		if (crossbowItem != null && Entity.isColliding(player, crossbowItem) && crossbowGun == null) {
			crossbowGun = new Entities.Guns.Crossbow(player.getX(), player.getY(), 16, 16, loader.getSprites("Crossbow"), loader);
			crossbowItem = null;
			player.setCrossbow(crossbowGun);
		}
	}

	private void handlePlayerMedicalKitCollision() {
		// Verifica colisões com o MedicalKit
		Iterator<MedicalKit> medkitIter = medicalKits.iterator();
		while (medkitIter.hasNext()) {
			MedicalKit medkit = medkitIter.next();
			if (medkit != null && Entity.isColliding(player, medkit)) {
				player.receiveHealth(medkit.use());
				medkitIter.remove();
			}
		}
	}

	private void handlePlayerArrowCollision() {
		// Verifica colisões com Arrow
		Iterator<Arrow> arrowIter = arrows.iterator();
		while (arrowIter.hasNext()) {
			Arrow arrow = arrowIter.next();
			if (arrow != null && Entity.isColliding(player, arrow)) {
				player.setAmmunition(player.getAmmunition() + arrow.getAmmunition());
				arrowIter.remove();
			}
		}
	}

	private void levelUp() {
		currentNivel++;
		System.out.println(currentNivel);
		if (currentNivel < 0) currentNivel = 0;
		reloadLevel();
	}

    private void dropItems(String type, float x, float y, int ammunition) {
		if (type == "Chest") {
			int randomNumber = random.nextInt(2);
			if(randomNumber == 0){
				Ring ring2 = new Ring(x, y, 16, 16, new Sprite(loader.getSprite("GreenRing")), "speed");
				rings.add(ring2);
			}else{
				Ring ring2 = new Ring(x, y, 16, 16, new Sprite(loader.getSprite("RedRing")), "life");
				rings.add(ring2);
			}
		}
		if (type == "Enemy") {
			Arrow arrow = new Arrow(x, y, 16, 16, new Sprite(loader.getSprite("Arrow")), ammunition);
			arrows.add(arrow);
		}
	}
	@Override
	public void resize(int width, int height) {
		camera.viewportWidth = width;
		camera.viewportHeight = height;
		camera.update();
		playerUI.resize(width, height);
	}
}
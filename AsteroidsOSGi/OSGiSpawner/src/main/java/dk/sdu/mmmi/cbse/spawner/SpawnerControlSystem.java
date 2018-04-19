package dk.sdu.mmmi.cbse.spawner;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.mmmi.cbse.commonasteroid.data.Asteroid;
import dk.sdu.mmmi.cbse.commonenemy.data.Enemy;
import dk.sdu.mmmi.cbse.commonplayer.data.Player;
import dk.sdu.mmmi.cbse.commonspawner.services.ISpawningService;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

public class SpawnerControlSystem implements IEntityProcessingService, IGamePluginService {

	private float nextSpawnX;
	private float nextSpawnY;
	private float nextSpawnRadians;

	private Random random = new Random();

	private int enemyAmount = 1;
	private int asteroidAmount = 3;

	private int enemySpawnGap = 5;
	private int asteroidSpawnGap = 2;

	private float enemySpawnTimer = 0;
	private float asteroidSpawnTimer = 0;

	private List<ISpawningService> spawningServices = new CopyOnWriteArrayList<>();
	private boolean playerHasSpawned = false;

	@Override
	public void start(GameData gameData, World world) {
		//Should spawn the initial entities
		ISpawningService playerSpawner = getSpawningService(Player.class);
		if (playerSpawner != null) {
			world.addEntity(playerSpawner.createEntity(gameData.getDisplayWidth() / 2, gameData.getDisplayHeight() / 2, 3.1415f * 0.5f));
			playerHasSpawned = true;
		}

		for (int i = 0; i < enemyAmount; i++) {
			spawnEntityRandomly(gameData, world, Enemy.class);
		}

		for (int i = 0; i < asteroidAmount; i++) {
			spawnEntityRandomly(gameData, world, Asteroid.class);
		}
	}

	@Override
	public void stop(GameData gameData, World world) {
		//Removes all entities
	}

	@Override
	public void process(GameData gameData, World world) {
		if (!playerHasSpawned) {
			ISpawningService playerSpawner = getSpawningService(Player.class);
			if (playerSpawner != null) {
				world.addEntity(playerSpawner.createEntity(gameData.getDisplayWidth() / 2, gameData.getDisplayHeight() / 2, 3.1415f * 0.5f));
				playerHasSpawned = true;
			}
		}

		//Checks how many enemies and asteroids there currently are
		int currentEnemyAmount = 0;
		int currentAsteroidAmount = 0;
		for (Entity entity : world.getEntities()) {
			if (entity.getClass().equals(Enemy.class)) {
				currentEnemyAmount++;
			} else if (entity.getClass().equals(Asteroid.class)) {
				currentAsteroidAmount++;
			}
		}

		if (currentEnemyAmount < enemyAmount) {
			enemySpawnTimer += gameData.getDelta();
			if (enemySpawnTimer > enemySpawnGap) {
				spawnEntityRandomly(gameData, world, Enemy.class);
				enemySpawnTimer = 0;
			}
		}

		if (currentAsteroidAmount < asteroidAmount) {
			asteroidSpawnTimer += gameData.getDelta();
			if (asteroidSpawnTimer > asteroidSpawnGap) {
				spawnEntityRandomly(gameData, world, Asteroid.class);
				asteroidSpawnTimer = 0;
			}
		}

	}

	private void spawnEntityRandomly(GameData gameData, World world, Class type) {
		setNextSpawn(gameData);
		ISpawningService spawner = getSpawningService(type);
		if (spawner != null) {
			world.addEntity(spawner.createEntity(nextSpawnX, nextSpawnY, nextSpawnRadians));
		}
	}

	private void setNextSpawn(GameData gameData) {

		float width = gameData.getDisplayWidth();
		float height = gameData.getDisplayHeight();

		float sideDecider = random.nextFloat();
		float coordinateDecider = random.nextFloat();
		float radiansDecider = random.nextFloat();

		if (sideDecider < 0.25) {
			nextSpawnX = width * coordinateDecider;
			nextSpawnY = height;
			nextSpawnRadians = 3.1415f + 3.1415f * radiansDecider;
		} else if (sideDecider < 0.5) {
			nextSpawnX = 0;
			nextSpawnY = height * coordinateDecider;
			nextSpawnRadians = 3.1415f * 1.5f + 3.1415f * radiansDecider;
		} else if (sideDecider < 0.75) {
			nextSpawnX = width * coordinateDecider;
			nextSpawnY = 0;
			nextSpawnRadians = 3.1415f * radiansDecider;
		} else {
			nextSpawnX = width;
			nextSpawnY = height * coordinateDecider;
			nextSpawnRadians = 3.1415f * 0.5f + 3.1415f * radiansDecider;
		}
	}

	private <T extends Entity> ISpawningService getSpawningService(Class<T> type) {

		System.out.println("spawners: " + spawningServices.size());

		for (ISpawningService service : spawningServices) {
			if (service.getEntityType().equals(type)) {
				return service;
			}
		}
		return null;
	}

	public void addSpawningService(ISpawningService spawningService) {
		this.spawningServices.add(spawningService);
	}

	public void removeSpawningService(ISpawningService spawningService) {
		this.spawningServices.remove(spawningService);
	}

}

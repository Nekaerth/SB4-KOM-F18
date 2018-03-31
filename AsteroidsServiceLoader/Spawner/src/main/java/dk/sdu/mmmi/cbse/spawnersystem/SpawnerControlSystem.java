package dk.sdu.mmmi.cbse.spawnersystem;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.mmmi.cbse.common.util.SPILocator;
import dk.sdu.mmmi.cbse.commonasteroid.services.IAsteroidSpawningService;
import dk.sdu.mmmi.cbse.commonenemy.services.IEnemySpawningService;
import dk.sdu.mmmi.cbse.commonplayer.services.IPlayerSpawningService;
import java.util.List;

public class SpawnerControlSystem implements IEntityProcessingService, IGamePluginService {

	@Override
	public void start(GameData gameData, World world) {
		//Should spawn the initial entities
		IPlayerSpawningService playerSpawner = getPlayerSpawningService();
		world.addEntity(playerSpawner.createPlayer(100, 100, 0));

		IEnemySpawningService enemySpawner = getEnemySpawningService();
		world.addEntity(enemySpawner.createEnemy(200, 200, 0));

		IAsteroidSpawningService asteroidSpawner = getAsteroidSpawningService();
		world.addEntity(asteroidSpawner.createAsteroid(300, 300, 0));
	}

	@Override
	public void stop(GameData gameData, World world) {
		//Removes all entities
	}

	@Override
	public void process(GameData gameData, World world) {
		//Should check if new asteroids or enemies should be spawned
	}

	private IPlayerSpawningService getPlayerSpawningService() {
		List<IPlayerSpawningService> playerSpawners = SPILocator.locateAll(IPlayerSpawningService.class);
		if (playerSpawners.isEmpty()) {
			return null;
		}
		return playerSpawners.get(0);
	}

	private IEnemySpawningService getEnemySpawningService() {
		List<IEnemySpawningService> enemySpawners = SPILocator.locateAll(IEnemySpawningService.class);
		if (enemySpawners.isEmpty()) {
			return null;
		}
		return enemySpawners.get(0);
	}

	private IAsteroidSpawningService getAsteroidSpawningService() {
		List<IAsteroidSpawningService> asteroidSpawners = SPILocator.locateAll(IAsteroidSpawningService.class);
		if (asteroidSpawners.isEmpty()) {
			return null;
		}
		return asteroidSpawners.get(0);
	}

}

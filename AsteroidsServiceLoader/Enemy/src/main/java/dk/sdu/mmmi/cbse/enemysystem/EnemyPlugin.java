package dk.sdu.mmmi.cbse.enemysystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;

public class EnemyPlugin implements IGamePluginService {

	@Override
	public void start(GameData gameData, World world) {

		// Add entities to the world
		Entity enemy = createEnemyShip(gameData);
		world.addEntity(enemy);
	}

	private Entity createEnemyShip(GameData gameData) {

		float deacceleration = 10;
		float acceleration = 200;
		float maxSpeed = 50;
		float rotationSpeed = 2;
		float x = gameData.getDisplayWidth() / 3;
		float y = gameData.getDisplayHeight() / 3;
		float radians = 3.1415f / 2;

		Entity playerShip = new Enemy();
		playerShip.add(new MovingPart(deacceleration, acceleration, maxSpeed, rotationSpeed));
		playerShip.add(new PositionPart(x, y, radians));

		return playerShip;
	}

	@Override
	public void stop(GameData gameData, World world) {
		// Remove all enemy-entities
		for (Entity enemy : world.getEntities(Enemy.class)) {
			world.removeEntity(enemy);
		}
	}

}

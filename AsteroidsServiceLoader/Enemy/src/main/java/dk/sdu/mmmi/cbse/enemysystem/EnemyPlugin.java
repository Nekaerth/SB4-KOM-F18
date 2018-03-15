package dk.sdu.mmmi.cbse.enemysystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.TimerPart;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;

public class EnemyPlugin implements IGamePluginService {

	@Override
	public void start(GameData gameData, World world) {

		// Add entities to the world
		Entity enemy = createEnemyShip(gameData, 100, 100);
		world.addEntity(enemy);

		Entity enemy2 = createEnemyShip(gameData, 100, 200);
		world.addEntity(enemy2);
	}

	private Entity createEnemyShip(GameData gameData, int x, int y) {

		float deacceleration = 10;
		float acceleration = 200;
		float maxSpeed = 50;
		float rotationSpeed = 2;
		//float x = gameData.getDisplayWidth() / 3;
		//float y = gameData.getDisplayHeight() / 3;
		float radians = 3.1415f / 2;

		Entity enemy = new Enemy();
		enemy.setShapeX(new float[4]);
		enemy.setShapeY(new float[4]);
		enemy.add(new MovingPart(deacceleration, acceleration, maxSpeed, rotationSpeed));
		enemy.add(new PositionPart(x, y, radians));
		//Adds two timers for turning and shooting
		float[] timers = new float[2];
		enemy.add(new TimerPart(timers));

		return enemy;
	}

	@Override
	public void stop(GameData gameData, World world) {
		// Remove all enemy-entities
		for (Entity enemy : world.getEntities(Enemy.class)) {
			world.removeEntity(enemy);
		}
	}

}

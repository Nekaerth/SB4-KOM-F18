package dk.sdu.mmmi.cbse.asteroidsystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PolygonShapePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.TimerPart;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;

public class AsteroidPlugin implements IGamePluginService {

	@Override
	public void start(GameData gameData, World world) {

		//Add entities to the world
		Entity asteroid1 = createAsteroid(gameData, 200, 100);
		world.addEntity(asteroid1);

		Entity asteroid2 = createAsteroid(gameData, 200, 200);
		world.addEntity(asteroid2);
	}

	private Entity createAsteroid(GameData gameData, int x, int y) {

		//float x = gameData.getDisplayWidth() / 3;
		//float y = gameData.getDisplayHeight() / 3;
		float radians = 3.1415f / 2;

		float[] shapeX = new float[4];
		float[] shapeY = new float[4];

		Entity asteroid = new Asteroid();
		asteroid.add(new PositionPart(x, y, radians));
		asteroid.add(new PolygonShapePart(shapeX, shapeY));

		return asteroid;
	}

	@Override
	public void stop(GameData gd, World world) {
		for (Entity asteroid : world.getEntities(Asteroid.class)) {
			world.removeEntity(asteroid);
		}
	}

}

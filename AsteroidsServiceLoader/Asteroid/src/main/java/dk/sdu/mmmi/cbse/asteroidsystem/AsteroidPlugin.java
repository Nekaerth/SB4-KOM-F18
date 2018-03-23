package dk.sdu.mmmi.cbse.asteroidsystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.PolygonShapePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import java.util.Random;

public class AsteroidPlugin implements IGamePluginService {

	private Random random = new Random();

	@Override
	public void start(GameData gameData, World world) {

		//Add entities to the world
		Entity asteroid1 = createAsteroid(gameData, 200, 100);
		world.addEntity(asteroid1);

		Entity asteroid2 = createAsteroid(gameData, 200, 200);
		world.addEntity(asteroid2);
	}

	private Entity createAsteroid(GameData gameData, int x, int y) {

		Entity asteroid = new Asteroid();

		//float x = gameData.getDisplayWidth() / 3;
		//float y = gameData.getDisplayHeight() / 3;
		float radians = 2 * 3.1415f * random.nextFloat();

		asteroid.add(new PositionPart(x, y, radians));

		int joints = random.nextInt(5) + 8;
		
		float[] shapeX = new float[joints];
		float[] shapeY = new float[joints];
		float[] length = new float[joints];
		
		for (int i = 0; i < joints; i++) {
			length[i] = 10 + (10 * random.nextFloat());
		}

		PolygonShapePart polygonShapePart = new PolygonShapePart(shapeX, shapeY);
		polygonShapePart.setLength(length);

		asteroid.add(polygonShapePart);

		return asteroid;
	}

	@Override
	public void stop(GameData gd, World world) {
		for (Entity asteroid : world.getEntities(Asteroid.class)) {
			world.removeEntity(asteroid);
		}
	}

}

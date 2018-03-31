package dk.sdu.mmmi.cbse.asteroidsystem;

import dk.sdu.mmmi.cbse.common.data.entityparts.HitboxPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PolygonShapePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.commonasteroid.data.Asteroid;
import dk.sdu.mmmi.cbse.commonasteroid.services.IAsteroidSpawningService;
import java.util.Random;

public class AsteroidSpawningService implements IAsteroidSpawningService {

	private Random random = new Random();

	@Override
	public Asteroid createAsteroid(float x, float y, float radians) {
		Asteroid asteroid = new Asteroid();

		asteroid.add(new PositionPart(x, y, radians));

		//Randomly chooses an amount of edges on the asteroid
		int edges = random.nextInt(5) + 8;
		float[] shapeX = new float[edges];
		float[] shapeY = new float[edges];
		float[] length = new float[edges];

		//Randomly chooses the distance from asteroid center to the edge for each edge
		for (int i = 0; i < edges; i++) {
			length[i] = 10 + (10 * random.nextFloat());
		}

		PolygonShapePart polygonShapePart = new PolygonShapePart(shapeX, shapeY);
		polygonShapePart.setLength(length);

		asteroid.add(polygonShapePart);

		float width = 30;
		float height = 30;
		float offsetX = 0;
		float offsetY = 0;
		asteroid.add(new HitboxPart(width, height, offsetX, offsetY, x, y));

		return asteroid;
	}

}

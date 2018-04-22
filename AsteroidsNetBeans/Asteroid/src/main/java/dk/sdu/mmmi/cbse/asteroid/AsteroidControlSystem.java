package dk.sdu.mmmi.cbse.asteroid;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.HitboxPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PolygonShapePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;
import dk.sdu.mmmi.cbse.commonasteroid.data.Asteroid;
import dk.sdu.mmmi.cbse.commonasteroid.data.entityparts.AsteroidPart;
import dk.sdu.mmmi.cbse.commonbullet.data.Bullet;
import dk.sdu.mmmi.cbse.commonbullet.data.entityparts.OwnershipPart;
import dk.sdu.mmmi.cbse.commonplayer.data.Player;
import dk.sdu.mmmi.cbse.commonspawner.services.ISpawningService;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import java.util.Random;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;

@ServiceProviders(value = {
	@ServiceProvider(service = ISpawningService.class)
	,
	@ServiceProvider(service = IEntityProcessingService.class)
	,
	@ServiceProvider(service = IPostEntityProcessingService.class)
})
public class AsteroidControlSystem implements ISpawningService<Asteroid>, IEntityProcessingService, IPostEntityProcessingService {

	private Random random = new Random();

	@Override
	public Class<Asteroid> getEntityType() {
		return Asteroid.class;
	}

	@Override
	public Asteroid createEntity(float x, float y, float radians) {
		return createEntity(x, y, radians, 2);
	}

	private Asteroid createEntity(float x, float y, float radians, int size) {
		Asteroid asteroid = new Asteroid();

		asteroid.add(new PositionPart(x, y, radians));

		asteroid.add(new AsteroidPart(size));

		//Randomly chooses an amount of edges on the asteroid
		int edges = random.nextInt(5) + 8;
		float[] shapeX = new float[edges];
		float[] shapeY = new float[edges];
		float[] length = new float[edges];

		//Randomly chooses the distance from asteroid center to the edge for each edge
		for (int i = 0; i < edges; i++) {
			length[i] = size * 5 + (size * 5 * random.nextFloat());
		}

		PolygonShapePart polygonShapePart = new PolygonShapePart(shapeX, shapeY);
		polygonShapePart.setLength(length);

		asteroid.add(polygonShapePart);

		float width = size * 15;
		float height = size * 15;
		float offsetX = 0;
		float offsetY = 0;
		asteroid.add(new HitboxPart(width, height, offsetX, offsetY, x, y));

		return asteroid;
	}

	@Override
	public void process(GameData gameData, World world) {
		for (Entity asteroid : world.getEntities(Asteroid.class)) {
			//Calculates position
			PositionPart pos = asteroid.getPart(PositionPart.class);

			float x = pos.getX();
			float y = pos.getY();
			float radians = pos.getRadians();

			x = (float) (x + cos(radians));
			y = (float) (y + sin(radians));

			//Wrapping
			if (x > gameData.getDisplayWidth()) {
				x = 0;
			} else if (x < 0) {
				x = gameData.getDisplayWidth();
			}
			if (y > gameData.getDisplayHeight()) {
				y = 0;
			} else if (y < 0) {
				y = gameData.getDisplayHeight();
			}

			//Set position
			pos.setX(x);
			pos.setY(y);

			//updates hitbox position after player position is updated
			HitboxPart hitboxPart = asteroid.getPart(HitboxPart.class);
			hitboxPart.process(gameData, asteroid);
		}
	}

	@Override
	public void postProcess(GameData gameData, World world) {
		for (Entity asteroid : world.getEntities(Asteroid.class)) {
			handleCollision(world, asteroid);
		}
		for (Entity asteroid : world.getEntities(Asteroid.class)) {
			updateShape(asteroid);
		}
	}

	private void handleCollision(World world, Entity asteroid) {
		HitboxPart hitbox = asteroid.getPart(HitboxPart.class);
		if (!hitbox.isHit()) {
			return;
		}

		for (Entity entity : hitbox.getCollidingEntities()) {
			Class type = entity.getClass();

			if (type.equals(Bullet.class)) {
				OwnershipPart ownerPart = entity.getPart(OwnershipPart.class);
				Entity owner = ownerPart.getOwner();
				if (owner.getClass().equals(Player.class)) {
					AsteroidPart asteroidPart = asteroid.getPart(AsteroidPart.class);
					int size = asteroidPart.getSize();
					//Creates two new smaller asteroids if asteroid size is bigger than 2
					if (size > 1) {
						PositionPart positionPart = asteroid.getPart(PositionPart.class);
						float x = positionPart.getX();
						float y = positionPart.getY();
						float radians = positionPart.getRadians();

						world.addEntity(createEntity(x, y, radians + 3.1416f * 0.2f, size - 1));
						world.addEntity(createEntity(x, y, radians - 3.1416f * 0.2f, size - 1));
					}
					world.removeEntity(asteroid);
				}
			}
		}
	}

	private void updateShape(Entity entity) {

		PositionPart pos = entity.getPart(PositionPart.class);

		float x = pos.getX();
		float y = pos.getY();
		float radians = pos.getRadians();

		PolygonShapePart poly = entity.getPart(PolygonShapePart.class);

		float[] shapeX = poly.getShapeX();
		float[] shapeY = poly.getShapeY();
		float[] length = poly.getLength();
		int joints = shapeX.length;

		for (int i = 0; i < joints; i++) {

			shapeX[i] = (float) (x + cos(radians) * length[i]);
			shapeY[i] = (float) (y + sin(radians) * length[i]);

			radians += 2 * 3.1415f / joints;
		}

	}

}

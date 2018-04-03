package dk.sdu.mmmi.cbse.asteroidsystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.HitboxPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PolygonShapePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IPostPostEntityProcessingService;
import dk.sdu.mmmi.cbse.commonasteroid.data.Asteroid;
import dk.sdu.mmmi.cbse.commonbullet.data.Bullet;
import dk.sdu.mmmi.cbse.commonbullet.data.entityparts.OwnershipPart;
import dk.sdu.mmmi.cbse.commonplayer.data.Player;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class AsteroidControlSystem implements IEntityProcessingService, IPostPostEntityProcessingService {

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
	public void postPostProcess(GameData gameData, World world) {
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

package dk.sdu.mmmi.cbse.bulletsystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.PointShapePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;

public class BulletControlSystem implements IEntityProcessingService {

	private float bulletSpeed = 200;

	@Override
	public void process(GameData gameData, World world) {
		float dt = gameData.getDelta();

		for (Entity bullet : world.getEntities(Bullet.class)) {
			PositionPart positionPart = bullet.getPart(PositionPart.class);

			float x = positionPart.getX();
			float y = positionPart.getY();
			float radians = positionPart.getRadians();
			//Calculates and sets next position
			positionPart.setX((float) (x + Math.cos(radians) * bulletSpeed * dt));
			positionPart.setY((float) (y + Math.sin(radians) * bulletSpeed * dt));

			//Check if out of boundary
			if (isOutOfWorld(gameData, bullet)) {
				world.removeEntity(bullet);
			}

			updateDraw(bullet);
		}
	}

	private boolean isOutOfWorld(GameData gameData, Entity entity) {
		int width = gameData.getDisplayWidth();
		int height = gameData.getDisplayHeight();

		PositionPart positionPart = entity.getPart(PositionPart.class);

		if (positionPart.getX() <= 0 || positionPart.getX() > width || positionPart.getY() <= 0 || positionPart.getY() > height) {
			return true;
		}
		return false;
	}

	private void updateDraw(Entity entity) {
		PositionPart positionPart = entity.getPart(PositionPart.class);
		float x = positionPart.getX();
		float y = positionPart.getY();

		PointShapePart pointPart = entity.getPart(PointShapePart.class);
		pointPart.setX(x);
		pointPart.setY(y);
	}

}

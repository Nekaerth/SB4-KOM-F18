package dk.sdu.mmmi.cbse.bulletsystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import java.util.List;

public class BulletControlSystem implements IEntityProcessingService {

	private float bulletSpeed = 200;

	@Override
	public void process(GameData gameData, World world) {
		float dt = gameData.getDelta();

		List<Entity> bullets = world.getEntities(Bullet.class);
		System.out.println("Bullet amount: " + bullets.size());

		for (Entity bullet : bullets) {
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
		float[] shapex = entity.getShapeX();
		float[] shapey = entity.getShapeY();
		PositionPart positionPart = entity.getPart(PositionPart.class);
		float x = positionPart.getX();
		float y = positionPart.getY();

		shapex[0] = x;
		shapex[1] = x + 1;
		shapey[0] = y;
		shapey[1] = y;

		entity.setShapeX(shapex);
		entity.setShapeY(shapey);
	}

}

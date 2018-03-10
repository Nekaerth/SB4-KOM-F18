package dk.sdu.mmmi.cbse.enemysystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.IBulletService;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.util.SPILocator;
import java.util.Collection;
import java.util.List;
import java.util.Random;

public class EnemyControlSystem implements IEntityProcessingService {

	private Random random = new Random();
	private float turnTimer = 0;
	private float shootTimer = 0;

	@Override
	public void process(GameData gameData, World world) {

		for (Entity enemy : world.getEntities(Enemy.class)) {
			PositionPart positionPart = enemy.getPart(PositionPart.class);
			MovingPart movingPart = enemy.getPart(MovingPart.class);

			//Decides  where to turn
			turnTimer += gameData.getDelta();

			if (turnTimer >= 0.1) {
				float direction = random.nextFloat();
				if (direction < 0.3) {
					movingPart.setLeft(true);
				} else {
					movingPart.setLeft(false);
				}
				if (direction > 0.7) {
					movingPart.setRight(true);
				} else {
					movingPart.setRight(false);
				}
				turnTimer -= 0.1;
			}
			movingPart.setUp(true);
			movingPart.process(gameData, enemy);

			//Shoots automatically
			shootTimer += gameData.getDelta();
			if (shootTimer > 0.1) {
				IBulletService b = getBulletService().get(0);
				b.shoot(gameData, world, positionPart);

				shootTimer -= 0.1;
			}

			updateShape(enemy);
		}
	}

	private void updateShape(Entity entity) {
		float[] shapex = entity.getShapeX();
		float[] shapey = entity.getShapeY();
		PositionPart positionPart = entity.getPart(PositionPart.class);
		float x = positionPart.getX();
		float y = positionPart.getY();
		float radians = positionPart.getRadians();

		shapex[0] = (float) (x + Math.cos(radians) * 8);
		shapey[0] = (float) (y + Math.sin(radians) * 8);

		shapex[1] = (float) (x + Math.cos(radians - 4 * 3.1415f / 5) * 8);
		shapey[1] = (float) (y + Math.sin(radians - 4 * 3.1415f / 5) * 8);

		shapex[2] = (float) (x + Math.cos(radians + 3.1415f) * 5);
		shapey[2] = (float) (y + Math.sin(radians + 3.1415f) * 5);

		shapex[3] = (float) (x + Math.cos(radians + 4 * 3.1415f / 5) * 8);
		shapey[3] = (float) (y + Math.sin(radians + 4 * 3.1415f / 5) * 8);

		entity.setShapeX(shapex);
		entity.setShapeY(shapey);
	}

	private List<? extends IBulletService> getBulletService() {
		return SPILocator.locateAll(IBulletService.class);
	}

}

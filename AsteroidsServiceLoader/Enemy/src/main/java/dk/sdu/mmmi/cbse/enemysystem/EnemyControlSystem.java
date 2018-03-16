package dk.sdu.mmmi.cbse.enemysystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.ShapePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.TimerPart;
import dk.sdu.mmmi.cbse.common.services.IBulletService;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.util.SPILocator;
import java.util.List;
import java.util.Random;

public class EnemyControlSystem implements IEntityProcessingService {

	private Random random = new Random();

	@Override
	public void process(GameData gameData, World world) {

		for (Entity enemy : world.getEntities(Enemy.class)) {
			MovingPart movingPart = enemy.getPart(MovingPart.class);
			TimerPart timerPart = enemy.getPart(TimerPart.class);
			float[] timers = timerPart.getTimers();

			//Decides  where to turn
			timerPart.process(gameData, enemy);

			if (timers[0] >= 0.1) {
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
				timers[0] -= 0.1;
			}
			movingPart.setUp(true);
			movingPart.process(gameData, enemy);

			//Shoots automatically
			shoot(gameData, world, enemy);

			updateShape(enemy);
		}
	}

	private void shoot(GameData gameData, World world, Entity enemy) {
		PositionPart positionPart = enemy.getPart(PositionPart.class);
		TimerPart timerPart = enemy.getPart(TimerPart.class);
		float[] timers = timerPart.getTimers();

		if (timers[1] > 0.1) {
			IBulletService bulletService = getBulletService();
			//Creates PositionPart that has same values as the enemy's PositionPart
			bulletService.shoot(gameData, world, positionPart);

			timers[1] -= 0.2;
		}
	}

	private void updateShape(Entity entity) {
		ShapePart shapePart = entity.getPart(ShapePart.class);
		float[] shapeX = shapePart.getShapeX();
		float[] shapeY = shapePart.getShapeY();
		PositionPart positionPart = entity.getPart(PositionPart.class);
		float x = positionPart.getX();
		float y = positionPart.getY();
		float radians = positionPart.getRadians();

		shapeX[0] = (float) (x + Math.cos(radians) * 8);
		shapeY[0] = (float) (y + Math.sin(radians) * 8);

		shapeX[1] = (float) (x + Math.cos(radians - 4 * 3.1415f / 5) * 8);
		shapeY[1] = (float) (y + Math.sin(radians - 4 * 3.1415f / 5) * 8);

		shapeX[2] = (float) (x + Math.cos(radians + 3.1415f) * 5);
		shapeY[2] = (float) (y + Math.sin(radians + 3.1415f) * 5);

		shapeX[3] = (float) (x + Math.cos(radians + 4 * 3.1415f / 5) * 8);
		shapeY[3] = (float) (y + Math.sin(radians + 4 * 3.1415f / 5) * 8);
	}

	private IBulletService getBulletService() {
		return SPILocator.locateAll(IBulletService.class).get(0);
	}

}

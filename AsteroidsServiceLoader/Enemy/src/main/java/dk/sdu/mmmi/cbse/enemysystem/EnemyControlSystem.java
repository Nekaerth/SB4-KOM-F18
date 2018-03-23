package dk.sdu.mmmi.cbse.enemysystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PolygonShapePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.TimerPart;
import dk.sdu.mmmi.cbse.common.services.IBulletService;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.util.SPILocator;
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
		PolygonShapePart polygonPart = entity.getPart(PolygonShapePart.class);
		float[] shapeX = polygonPart.getShapeX();
		float[] shapeY = polygonPart.getShapeY();
		PositionPart positionPart = entity.getPart(PositionPart.class);
		float x = positionPart.getX();
		float y = positionPart.getY();

		shapeX[0] = x + 4;
		shapeY[0] = y + 5;

		shapeX[1] = x + 3;
		shapeY[1] = y + 10;

		shapeX[2] = x - 3;
		shapeY[2] = y + 10;

		shapeX[3] = x - 4;
		shapeY[3] = y + 5;

		shapeX[4] = x - 15;
		shapeY[4] = y;

		shapeX[5] = x - 4;
		shapeY[5] = y - 5;

		shapeX[6] = x + 4;
		shapeY[6] = y - 5;

		shapeX[7] = x + 15;
		shapeY[7] = y;

		shapeX[8] = x - 15;
		shapeY[8] = y;

		shapeX[9] = x + 15;
		shapeY[9] = y;

		shapeX[10] = x + 4;
		shapeY[10] = y + 5;

		shapeX[11] = x - 4;
		shapeY[11] = y + 5;
	}

	private IBulletService getBulletService() {
		return SPILocator.locateAll(IBulletService.class).get(0);
	}

}

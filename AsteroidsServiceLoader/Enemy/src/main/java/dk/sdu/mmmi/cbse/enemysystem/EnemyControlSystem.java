package dk.sdu.mmmi.cbse.enemysystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.HitboxPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PolygonShapePart;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IPostPostEntityProcessingService;
import dk.sdu.mmmi.cbse.common.util.SPILocator;
import dk.sdu.mmmi.cbse.commonbullet.data.entityparts.ShootingPart;
import dk.sdu.mmmi.cbse.commonbullet.services.IBulletService;
import java.util.List;
import java.util.Random;

public class EnemyControlSystem implements IEntityProcessingService, IPostPostEntityProcessingService {

	private Random random = new Random();

	@Override
	public void process(GameData gameData, World world) {

		for (Entity entity : world.getEntities(Enemy.class)) {
			Enemy enemy = (Enemy) entity;
			MovingPart movingPart = enemy.getPart(MovingPart.class);

			//Decides where to turn
			enemy.addTurnTimer(gameData.getDelta());

			float turnTimeGap = 0.25f;
			if (enemy.getTurnTimer() >= turnTimeGap) {
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
				enemy.addTurnTimer(-turnTimeGap);
			}
			movingPart.setUp(true);
			movingPart.process(gameData, enemy);

			//Shoots automatically
			shoot(gameData, world, enemy);

			//Updates hitbox position after player position is updated
			HitboxPart hitboxPart = enemy.getPart(HitboxPart.class);
			hitboxPart.process(gameData, enemy);
		}
	}

	@Override
	public void postPostProcess(GameData gameData, World world) {
		for (Entity enemy : world.getEntities(Enemy.class)) {
			updateShape(enemy);
		}
	}

	private void updateShape(Entity entity) {
		PolygonShapePart polygonPart = entity.getPart(PolygonShapePart.class);
		float[] shapeX = polygonPart.getShapeX();
		float[] shapeY = polygonPart.getShapeY();
		PositionPart positionPart = entity.getPart(PositionPart.class);
		float x = positionPart.getX();
		float y = positionPart.getY();

		shapeX[0] = x + 5;
		shapeY[0] = y + 5;

		shapeX[1] = x + 3;
		shapeY[1] = y + 10;

		shapeX[2] = x - 3;
		shapeY[2] = y + 10;

		shapeX[3] = x - 5;
		shapeY[3] = y + 5;

		shapeX[4] = x - 14;
		shapeY[4] = y + 1;

		shapeX[5] = x - 5;
		shapeY[5] = y - 5;

		shapeX[6] = x + 5;
		shapeY[6] = y - 5;

		shapeX[7] = x + 14;
		shapeY[7] = y + 1;

		shapeX[8] = x - 14;
		shapeY[8] = y + 1;

		shapeX[9] = x + 14;
		shapeY[9] = y + 1;

		shapeX[10] = x + 4;
		shapeY[10] = y + 5;

		shapeX[11] = x - 4;
		shapeY[11] = y + 5;
	}

	private void shoot(GameData gameData, World world, Entity enemy) {
		ShootingPart shooter = enemy.getPart(ShootingPart.class);
		shooter.addShootTimer(gameData.getDelta());

		if (shooter.getShootTimer() > shooter.getShootGap()) {
			PositionPart pos = enemy.getPart(PositionPart.class);
			IBulletService bulletService = getBulletService();
			if (bulletService != null) {
				bulletService.shoot(gameData, world, pos.getX(), pos.getY(), pos.getRadians(), enemy);
			}
			shooter.addShootTimer(-shooter.getShootGap());
		}

	}

	private IBulletService getBulletService() {
		List<IBulletService> bulletServices = SPILocator.locateAll(IBulletService.class);
		if (bulletServices.isEmpty()) {
			return null;
		}
		return bulletServices.get(0);
	}

}

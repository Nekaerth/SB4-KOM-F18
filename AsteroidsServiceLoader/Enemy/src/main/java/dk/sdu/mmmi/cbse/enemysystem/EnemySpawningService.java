package dk.sdu.mmmi.cbse.enemysystem;

import dk.sdu.mmmi.cbse.common.data.entityparts.HitboxPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PolygonShapePart;
import dk.sdu.mmmi.cbse.commonbullet.data.entityparts.ShootingPart;
import dk.sdu.mmmi.cbse.commonenemy.services.IEnemySpawningService;

public class EnemySpawningService implements IEnemySpawningService {

	@Override
	public Enemy createEnemy(float x, float y, float radians) {
		Enemy enemy = new Enemy();

		enemy.add(new PositionPart(x, y, radians));

		float deacceleration = 10;
		float acceleration = 200;
		float maxSpeed = 50;
		float rotationSpeed = 2;
		enemy.add(new MovingPart(deacceleration, acceleration, maxSpeed, rotationSpeed));

		float[] shapeX = new float[12];
		float[] shapeY = new float[12];
		enemy.add(new PolygonShapePart(shapeX, shapeY));

		float width = 26;
		float height = 13;
		float offsetX = 0;
		float offsetY = 2;
		enemy.add(new HitboxPart(width, height, offsetX, offsetY, x, y));

		float shootGap = 0.25f;
		enemy.add(new ShootingPart(shootGap));

		return enemy;
	}

}

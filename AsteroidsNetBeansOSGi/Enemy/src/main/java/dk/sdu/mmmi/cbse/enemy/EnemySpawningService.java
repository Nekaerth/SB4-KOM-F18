package dk.sdu.mmmi.cbse.enemy;

import dk.sdu.mmmi.cbse.common.data.entityparts.HitboxPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PolygonShapePart;
import dk.sdu.mmmi.cbse.commonbullet.data.entityparts.ShootingPart;
import dk.sdu.mmmi.cbse.commonenemy.data.Enemy;
import dk.sdu.mmmi.cbse.commonspawner.services.ISpawningService;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;

@ServiceProviders(value = {
	@ServiceProvider(service = ISpawningService.class)
})
public class EnemySpawningService implements ISpawningService<Enemy> {

	@Override
	public Class<Enemy> getEntityType() {
		return Enemy.class;
	}

	@Override
	public Enemy createEntity(float x, float y, float radians) {
		Enemy enemy = new Enemy();

		enemy.add(new EnemyPart());

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

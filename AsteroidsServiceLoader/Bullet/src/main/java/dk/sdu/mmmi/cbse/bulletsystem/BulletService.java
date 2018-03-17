package dk.sdu.mmmi.cbse.bulletsystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.PointShapePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.IBulletService;

public class BulletService implements IBulletService {

	@Override
	public void shoot(GameData gameData, World world, PositionPart positionPart) {
		//Creates bullet and adds to world
		Entity bullet = createBullet(positionPart);
		world.addEntity(bullet);
	}

	private Entity createBullet(PositionPart positionPart) {

		Entity bullet = new Bullet();
		float x = positionPart.getX();
		float y = positionPart.getY();
		float radians = positionPart.getRadians();

		bullet.add(new PositionPart(x, y, radians));
		bullet.add(new PointShapePart(x, y));

		return bullet;
	}

}

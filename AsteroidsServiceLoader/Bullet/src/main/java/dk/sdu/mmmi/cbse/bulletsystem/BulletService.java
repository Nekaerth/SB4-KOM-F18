package dk.sdu.mmmi.cbse.bulletsystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.PointShapePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.commonbullet.data.Bullet;
import dk.sdu.mmmi.cbse.commonbullet.data.entityparts.OwnershipPart;
import dk.sdu.mmmi.cbse.commonbullet.services.IBulletService;

public class BulletService implements IBulletService {

	@Override
	public void shoot(GameData gameData, World world, float x, float y, float radians, Entity owner) {
		//Creates bullet and adds to world
		Entity bullet = createBullet(x, y, radians, owner);
		world.addEntity(bullet);
	}

	private Entity createBullet(float x, float y, float radians, Entity owner) {

		Entity bullet = new Bullet();

		bullet.add(new PositionPart(x, y, radians));
		bullet.add(new PointShapePart(x, y));
		bullet.add(new OwnershipPart(owner));

		return bullet;
	}

}

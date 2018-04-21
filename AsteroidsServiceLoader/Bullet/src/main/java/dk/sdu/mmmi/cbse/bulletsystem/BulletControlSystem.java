package dk.sdu.mmmi.cbse.bulletsystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.HitboxPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PointShapePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;
import dk.sdu.mmmi.cbse.commonasteroid.data.Asteroid;
import dk.sdu.mmmi.cbse.commonbullet.data.Bullet;
import dk.sdu.mmmi.cbse.commonbullet.data.entityparts.OwnershipPart;
import dk.sdu.mmmi.cbse.commonenemy.data.Enemy;
import dk.sdu.mmmi.cbse.commonplayer.data.Player;

public class BulletControlSystem implements IEntityProcessingService, IPostEntityProcessingService {

	private float bulletSpeed = 150;

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

			//Updates hitbox position after player position is updated
			HitboxPart hitboxPart = bullet.getPart(HitboxPart.class);
			hitboxPart.process(gameData, bullet);

			//Check if out of boundary
			if (isOutOfWorld(gameData, bullet)) {
				world.removeEntity(bullet);
			}
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

	@Override
	public void postProcess(GameData gameData, World world) {
		for (Entity asteroid : world.getEntities(Bullet.class)) {
			handleCollision(world, asteroid);
		}
		for (Entity bullet : world.getEntities(Bullet.class)) {
			updateShape(bullet);
		}
	}

	private void handleCollision(World world, Entity bullet) {
		HitboxPart hitbox = bullet.getPart(HitboxPart.class);
		if (!hitbox.isHit()) {
			return;
		}
		OwnershipPart ownerPart = bullet.getPart(OwnershipPart.class);
		Entity owner = ownerPart.getOwner();

		for (Entity entity : hitbox.getCollidingEntities()) {
			Class type = entity.getClass();

			if (owner.getClass().equals(Player.class) && type.equals(Enemy.class)) {
				world.removeEntity(bullet);
			} else if (owner.getClass().equals(Player.class) && type.equals(Asteroid.class)) {
				world.removeEntity(bullet);
			} else if (owner.getClass().equals(Enemy.class) && type.equals(Player.class)) {
				world.removeEntity(bullet);
			}
		}
	}

	private void updateShape(Entity entity) {
		PositionPart positionPart = entity.getPart(PositionPart.class);
		float x = positionPart.getX();
		float y = positionPart.getY();

		PointShapePart pointPart = entity.getPart(PointShapePart.class);
		pointPart.setX(x);
		pointPart.setY(y);
	}

}

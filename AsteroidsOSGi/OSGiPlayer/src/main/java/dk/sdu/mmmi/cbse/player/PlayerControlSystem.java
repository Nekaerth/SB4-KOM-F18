package dk.sdu.mmmi.cbse.player;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import static dk.sdu.mmmi.cbse.common.data.GameKeys.LEFT;
import static dk.sdu.mmmi.cbse.common.data.GameKeys.RIGHT;
import static dk.sdu.mmmi.cbse.common.data.GameKeys.UP;
import static dk.sdu.mmmi.cbse.common.data.GameKeys.SPACE;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.HitboxPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PolygonShapePart;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.commonasteroid.data.Asteroid;
import dk.sdu.mmmi.cbse.commonbullet.data.Bullet;
import dk.sdu.mmmi.cbse.commonbullet.data.entityparts.OwnershipPart;
import dk.sdu.mmmi.cbse.commonbullet.data.entityparts.ShootingPart;
import dk.sdu.mmmi.cbse.commonbullet.services.IBulletService;
import dk.sdu.mmmi.cbse.commonenemy.data.Enemy;
import dk.sdu.mmmi.cbse.commonplayer.data.Player;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;

public class PlayerControlSystem implements IEntityProcessingService, IPostEntityProcessingService {

	private IBulletService bulletService = null;

	@Override
	public void process(GameData gameData, World world) {
		for (Entity player : world.getEntities(Player.class)) {
			//Updates player position based on keys down
			MovingPart movingPart = player.getPart(MovingPart.class);

			movingPart.setLeft(gameData.getKeys().isDown(LEFT));
			movingPart.setRight(gameData.getKeys().isDown(RIGHT));
			movingPart.setUp(gameData.getKeys().isDown(UP));

			movingPart.process(gameData, player);

			//Shoots if space key is down
			if (gameData.getKeys().isDown(SPACE)) {
				shoot(gameData, world, player);
			} else {
				ShootingPart s = player.getPart(ShootingPart.class);
				s.resetTimer();
			}

			//Updates hitbox position after player position is updated
			HitboxPart hitboxPart = player.getPart(HitboxPart.class);
			hitboxPart.process(gameData, player);
		}
	}

	private void shoot(GameData gameData, World world, Entity player) {
		ShootingPart shooter = player.getPart(ShootingPart.class);
		shooter.addShootTimer(gameData.getDelta());

		if (shooter.getShootTimer() > shooter.getShootGap()) {
			PositionPart pos = player.getPart(PositionPart.class);
			if (bulletService != null) {
				bulletService.shoot(gameData, world, pos.getX(), pos.getY(), pos.getRadians(), player);
			}
			shooter.addShootTimer(-shooter.getShootGap());
		}

	}

	public void setBulletService(IBulletService bulletService) {
		this.bulletService = bulletService;
	}

	public void removeBulletService(IBulletService bulletService) {
		this.bulletService = null;
	}

	@Override
	public void postProcess(GameData gameData, World world) {
		for (Entity player : world.getEntities(Player.class)) {
			handleCollision(world, player);
		}
		for (Entity player : world.getEntities(Player.class)) {
			updateShape(player);
		}
	}

	private void handleCollision(World world, Entity player) {
		HitboxPart hitbox = player.getPart(HitboxPart.class);
		if (!hitbox.isHit()) {
			return;
		}

		for (Entity entity : hitbox.getCollidingEntities()) {
			Class type = entity.getClass();

			//Handles the collision situation depending on who is colliding
			if (type.equals(Asteroid.class)) {
				world.removeEntity(player);
			} else if (type.equals(Enemy.class)) {
				world.removeEntity(player);
			} else if (type.equals(Bullet.class)) {
				OwnershipPart ownerPart = entity.getPart(OwnershipPart.class);
				Entity owner = ownerPart.getOwner();
				if (owner.getClass().equals(Enemy.class)) {
					world.removeEntity(player);
				}
			}
		}
	}

	private void updateShape(Entity entity) {
		PolygonShapePart polygonPart = entity.getPart(PolygonShapePart.class);
		float[] shapeX = polygonPart.getShapeX();
		float[] shapeY = polygonPart.getShapeY();
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

}

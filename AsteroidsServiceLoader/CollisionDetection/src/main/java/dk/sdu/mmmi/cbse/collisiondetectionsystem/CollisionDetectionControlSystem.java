package dk.sdu.mmmi.cbse.collisiondetectionsystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.HitboxPart;
import dk.sdu.mmmi.cbse.common.services.IEntityCollisionDetectionService;

public class CollisionDetectionControlSystem implements IEntityCollisionDetectionService {

	@Override
	public void collisionDetection(GameData gameData, World world) {
		for (Entity entity : world.getEntities()) {
			HitboxPart hitbox = entity.getPart(HitboxPart.class);
			if (hitbox == null) {
				continue;
			}

			hitbox.clearCollidingEntities();

			for (Entity otherEntity : world.getEntities()) {
				HitboxPart hitbox2 = otherEntity.getPart(HitboxPart.class);
				if (hitbox2 == null || hitbox == hitbox2) {
					continue;
				}

				if (isColliding(hitbox, hitbox2)) {
					hitbox.addCollidingEntity(otherEntity);
				}
			}
		}
	}

	private boolean isColliding(HitboxPart hitbox, HitboxPart hitbox2) {
		float centerDistanceX = Math.abs(hitbox.getX() - hitbox2.getX());
		float centerDistanceY = Math.abs(hitbox.getY() - hitbox2.getY());

		float allowedDistanceX = (hitbox.getWidth() / 2) + (hitbox2.getWidth() / 2);
		float allowedDistanceY = (hitbox.getHeight() / 2) + (hitbox2.getHeight() / 2);

		return (allowedDistanceX > centerDistanceX && allowedDistanceY > centerDistanceY);
	}

}

package dk.sdu.mmmi.cbse.collisiondetectionsystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.HitboxPart;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;

public class CollisionDetectionControlSystem implements IPostEntityProcessingService {

	@Override
	public void postProcess(GameData gameData, World world) {
		for (Entity entity : world.getEntities()) {
			HitboxPart hitboxPart = entity.getPart(HitboxPart.class);
			if (hitboxPart == null) {
				continue;
			}

			for (Entity otherEntity : world.getEntities()) {
				HitboxPart otherHitboxPart = otherEntity.getPart(HitboxPart.class);
				if (otherHitboxPart == null || hitboxPart == otherHitboxPart) {
					continue;
				}

				boolean isColliding = isColliding(hitboxPart, otherHitboxPart);
				hitboxPart.setHit(isColliding);
				otherHitboxPart.setHit(isColliding);
			}
		}
	}

	private boolean isColliding(HitboxPart box1, HitboxPart box2) {
		float centerDistanceX = Math.abs(box1.getX() - box2.getX());
		float centerDistanceY = Math.abs(box1.getY() - box2.getY());

		float allowedDistanceX = (box1.getWidth() / 2) + (box2.getWidth() / 2);
		float allowedDistanceY = (box1.getHeight() / 2) + (box2.getHeight() / 2);

		return (allowedDistanceX > centerDistanceX && allowedDistanceY > centerDistanceY);
	}

}

package dk.sdu.mmmi.cbse.common.data.entityparts;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import java.util.HashSet;
import java.util.Set;

public class HitboxPart implements EntityPart {

	private float x;
	private float y;
	private float width;
	private float height;

	//This coordinate is the difference between hitboxposition and entity position
	private float offsetX;
	private float offsetY;

	private Set<Entity> collidingEntities;

	public HitboxPart(float width, float height, float offsetX, float offsetY, float hostX, float hostY) {
		this.width = width;
		this.height = height;
		this.offsetX = offsetX;
		this.offsetY = offsetY;
		this.collidingEntities = new HashSet<>();

		updatePosition(hostX, hostY);
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public float getWidth() {
		return width;
	}

	public float getHeight() {
		return height;
	}

	public boolean isHit() {
		if (collidingEntities.isEmpty()) {
			return false;
		}
		return true;
	}

	public Set<Entity> getCollidingEntities() {
		return collidingEntities;
	}

	public void addCollidingEntity(Entity entity) {
		collidingEntities.add(entity);
	}

	public void clearCollidingEntities() {
		collidingEntities.clear();
	}

	@Override
	public void process(GameData gameData, Entity entity) {
		PositionPart pos = entity.getPart(PositionPart.class);
		updatePosition(pos.getX(), pos.getY());
	}

	/**
	 * Updates the hitbox position based on the host's position and the offset
	 * values.
	 *
	 * @param hostX is the x coordinate of the host
	 * @param hostY is the y coordinate of the hosst
	 */
	private void updatePosition(float hostX, float hostY) {
		x = hostX + offsetX;
		y = hostY + offsetY;
	}

}

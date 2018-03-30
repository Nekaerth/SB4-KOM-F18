package dk.sdu.mmmi.cbse.common.data.entityparts;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;

public class HitboxPart implements EntityPart {

	private float x;
	private float y;
	private float width;
	private float height;

	private boolean isHit;

	//This coordinate is the difference between hitboxposition and entity position
	private float offsetX;
	private float offsetY;

	public HitboxPart(float width, float height, float offsetX, float offsetY, float hostX, float hostY) {
		this.width = width;
		this.height = height;
		this.isHit = false;
		this.offsetX = offsetX;
		this.offsetY = offsetY;

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

	public boolean IsHit() {
		return isHit;
	}

	public void setHit(boolean isHit) {
		this.isHit = isHit;
	}

	/**
	 * Updates the hitbox position based on the host's position and the offset
	 * values.
	 *
	 * @param hostX is the x coordinate of the host
	 * @param hostY is the y coordinate of the host
	 */
	private void updatePosition(float hostX, float hostY) {
		this.x = x + offsetX;
		this.y = y + offsetY;
	}

	@Override
	public void process(GameData gameData, Entity entity) {
		PositionPart pos = entity.getPart(PositionPart.class);
		updatePosition(pos.getX(), pos.getY());
	}

}

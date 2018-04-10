package dk.sdu.mmmi.cbse.common.data.entityparts;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;

public class PointShapePart implements EntityPart {

	private float x;
	private float y;

	public PointShapePart(float x, float y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public void process(GameData gameData, Entity entity) {

	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

}

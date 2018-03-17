package dk.sdu.mmmi.cbse.common.data.entityparts;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;

public class PolygonShapePart implements EntityPart {

	private float[] shapeX;
	private float[] shapeY;

	public PolygonShapePart(float[] shapeX, float[] shapeY) {
		this.shapeX = shapeX;
		this.shapeY = shapeY;
	}

	@Override
	public void process(GameData gameData, Entity entity) {

	}

	public float[] getShapeX() {
		return shapeX;
	}

	public void setShapeX(float[] shapeX) {
		this.shapeX = shapeX;
	}

	public float[] getShapeY() {
		return shapeY;
	}

	public void setShapeY(float[] shapeY) {
		this.shapeY = shapeY;
	}

}

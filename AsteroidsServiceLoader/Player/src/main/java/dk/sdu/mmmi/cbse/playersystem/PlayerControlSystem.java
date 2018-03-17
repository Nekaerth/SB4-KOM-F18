package dk.sdu.mmmi.cbse.playersystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import static dk.sdu.mmmi.cbse.common.data.GameKeys.LEFT;
import static dk.sdu.mmmi.cbse.common.data.GameKeys.RIGHT;
import static dk.sdu.mmmi.cbse.common.data.GameKeys.UP;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PolygonShapePart;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;

public class PlayerControlSystem implements IEntityProcessingService {

	@Override
	public void process(GameData gameData, World world) {

		for (Entity player : world.getEntities(Player.class)) {
			PositionPart positionPart = player.getPart(PositionPart.class);
			MovingPart movingPart = player.getPart(MovingPart.class);

			movingPart.setLeft(gameData.getKeys().isDown(LEFT));
			movingPart.setRight(gameData.getKeys().isDown(RIGHT));
			movingPart.setUp(gameData.getKeys().isDown(UP));

			movingPart.process(gameData, player);

			updateShape(player);
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

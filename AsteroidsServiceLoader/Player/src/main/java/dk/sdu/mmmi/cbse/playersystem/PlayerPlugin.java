package dk.sdu.mmmi.cbse.playersystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.HitboxPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PolygonShapePart;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;

public class PlayerPlugin implements IGamePluginService {

	private Entity player;

	public PlayerPlugin() {
	}

	@Override
	public void start(GameData gameData, World world) {

		// Add entities to the world
		player = createPlayerShip(gameData);
		world.addEntity(player);
	}

	private Entity createPlayerShip(GameData gameData) {
		Entity player = new Player();

		float x = gameData.getDisplayWidth() / 2;
		float y = gameData.getDisplayHeight() / 2;
		float radians = 3.1415f / 2;
		player.add(new PositionPart(x, y, radians));

		float deacceleration = 10;
		float acceleration = 200;
		float maxSpeed = 300;
		float rotationSpeed = 5;
		player.add(new MovingPart(deacceleration, acceleration, maxSpeed, rotationSpeed));

		float[] shapeX = new float[4];
		float[] shapeY = new float[4];
		player.add(new PolygonShapePart(shapeX, shapeY));

		float width = 30;
		float height = 30;
		float offsetX = 0;
		float offsetY = 0;
		player.add(new HitboxPart(width, height, offsetX, offsetY, x, y));

		return player;
	}

	@Override
	public void stop(GameData gameData, World world) {
		// Remove entities
		world.removeEntity(player);
	}

}

package dk.sdu.mmmi.cbse.player;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.HitboxPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PolygonShapePart;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.mmmi.cbse.commonbullet.data.entityparts.ShootingPart;
import dk.sdu.mmmi.cbse.commonplayer.data.Player;
import org.openide.util.Exceptions;

public class PlayerPlugin implements IGamePluginService {

	@Override
	public void start(GameData gameData, World world) {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException ex) {
			Exceptions.printStackTrace(ex);
		}
		world.addEntity(createPlayer(gameData.getDisplayWidth() / 2, gameData.getDisplayHeight() / 2, 3.1415f * 0.5f));
	}

	@Override
	public void stop(GameData gameData, World world) {

	}

	private Player createPlayer(float x, float y, float radians) {
		Player player = new Player();

		player.add(new PositionPart(x, y, radians));

		float deacceleration = 10;
		float acceleration = 200;
		float maxSpeed = 300;
		float rotationSpeed = 5;
		player.add(new MovingPart(deacceleration, acceleration, maxSpeed, rotationSpeed));

		float[] shapeX = new float[4];
		float[] shapeY = new float[4];
		player.add(new PolygonShapePart(shapeX, shapeY));

		float width = 15;
		float height = 15;
		float offsetX = 0;
		float offsetY = 0;
		player.add(new HitboxPart(width, height, offsetX, offsetY, x, y));

		float shootGap = 0.2f;
		player.add(new ShootingPart(shootGap));

		return player;
	}

}

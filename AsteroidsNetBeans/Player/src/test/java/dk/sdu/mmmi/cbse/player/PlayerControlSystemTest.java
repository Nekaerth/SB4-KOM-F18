package dk.sdu.mmmi.cbse.player;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import static dk.sdu.mmmi.cbse.common.data.GameKeys.UP;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.HitboxPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PolygonShapePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.commonbullet.data.entityparts.ShootingPart;
import dk.sdu.mmmi.cbse.commonplayer.data.Player;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class PlayerControlSystemTest {

	public PlayerControlSystemTest() {
	}

	@BeforeClass
	public static void setUpClass() {
	}

	@AfterClass
	public static void tearDownClass() {
	}

	@Before
	public void setUp() {
	}

	@After
	public void tearDown() {
	}

	/**
	 * Test of process method, of class PlayerControlSystem.
	 */
	@Test
	public void testProcess() {
		//Instanciates GameData and sets up-key to true
		GameData gameData = new GameData();
		gameData.getKeys().setKey(UP, true);
		gameData.setDelta(1);
		gameData.setDisplayWidth(500);
		gameData.setDisplayHeight(500);

		//Instantiates world and adds a player
		World world = new World();
		world.addEntity(createPlayer());

		//Saves previous position
		PositionPart pos = getPlayerPositionPart(world);
		float prevX = pos.getX();
		float prevY = pos.getY();

		//Runs process on PlayerControlSystem once
		PlayerControlSystem instance = new PlayerControlSystem();
		instance.process(gameData, world);

		//Expects player to have moved right
		assertTrue(hasMovedRight(world, prevX));
		
		//Expects player to not have moved up or down
		assertFalse(hasMovedUpOrDown(world, prevY));
	}

	/**
	 * Creates and returns a default player that is looking right
	 *
	 * @return
	 */
	private Entity createPlayer() {
		Player player = new Player();

		float x = 100;
		float y = 100;
		float radians = 0; //Looking right
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

	private PositionPart getPlayerPositionPart(World world) {
		Entity player = world.getEntities(Player.class).get(0);
		return player.getPart(PositionPart.class);
	}

	private boolean hasMovedRight(World world, float prevX) {
		PositionPart pos = getPlayerPositionPart(world);
		return pos.getX() > prevX;
	}
	
	private boolean hasMovedUpOrDown(World world, float prevY) {
		PositionPart pos = getPlayerPositionPart(world);
		return pos.getY() != prevY;
	}

}

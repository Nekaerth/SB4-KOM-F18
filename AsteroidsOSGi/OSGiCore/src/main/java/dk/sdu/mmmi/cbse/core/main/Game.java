package dk.sdu.mmmi.cbse.core.main;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.GameKeys;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.HitboxPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PointShapePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PolygonShapePart;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IEntityCollisionDetectionService;
import dk.sdu.mmmi.cbse.core.managers.GameInputProcessor;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;

public class Game implements ApplicationListener {

	private static OrthographicCamera cam;
	private ShapeRenderer sr;
	private final GameData gameData = new GameData();
	private static World world = new World();

	private static List<IGamePluginService> gamePluginList = new CopyOnWriteArrayList<>();
	private static List<IEntityProcessingService> entityProcessorList = new CopyOnWriteArrayList<>();
	private static List<IEntityCollisionDetectionService> entityCollisionDetectorList = new CopyOnWriteArrayList<>();
	private static List<IPostEntityProcessingService> postEntityProcessorList = new CopyOnWriteArrayList<>();

	public Game() {
		init();
	}

	private void init() {

		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "Asteroids";
		cfg.width = 500;
		cfg.height = 400;
		cfg.useGL30 = false;
		cfg.resizable = false;

		new LwjglApplication(this, cfg);
	}

	@Override
	public void create() {
		gameData.setDisplayWidth(Gdx.graphics.getWidth());
		gameData.setDisplayHeight(Gdx.graphics.getHeight());

		cam = new OrthographicCamera(gameData.getDisplayWidth(), gameData.getDisplayHeight());
		cam.translate(gameData.getDisplayWidth() / 2, gameData.getDisplayHeight() / 2);
		cam.update();

		sr = new ShapeRenderer();

		Gdx.input.setInputProcessor(new GameInputProcessor(gameData));

	}

	@Override
	public void render() {
		// clear screen to black
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		gameData.setDelta(Gdx.graphics.getDeltaTime());
		gameData.getKeys().update();

		update();
		draw();
	}

	private void update() {
		//Does all all the proccessing of entities in correct order
		for (IEntityProcessingService entityProcessorService : entityProcessorList) {
			entityProcessorService.process(gameData, world);
		}

		for (IEntityCollisionDetectionService entityCollisionDetectionService : entityCollisionDetectorList) {
			entityCollisionDetectionService.collisionDetection(gameData, world);
		}

		for (IPostEntityProcessingService postEntityProcessorService : postEntityProcessorList) {
			postEntityProcessorService.postProcess(gameData, world);
		}
	}

	private void draw() {

		sr.begin(ShapeRenderer.ShapeType.Line);
		if (gameData.getKeys().isDown(GameKeys.SHIFT)) {
			drawHitboxes();
		}
		sr.setColor(1, 1, 1, 1);
		for (Entity entity : world.getEntities()) {
			//Draws polygon from polygon 
			PolygonShapePart polygonShape = entity.getPart(PolygonShapePart.class);
			if (polygonShape != null) {

				float[] shapex = polygonShape.getShapeX();
				float[] shapey = polygonShape.getShapeY();

				for (int i = 0, j = shapex.length - 1; i < shapex.length; j = i++) {
					sr.line(shapex[i], shapey[i], shapex[j], shapey[j]);
				}
			}

			//Draws point
			PointShapePart pointPart = entity.getPart(PointShapePart.class);
			if (pointPart != null) {
				sr.point(pointPart.getX(), pointPart.getY(), 0);
			}

		}

		sr.end();
	}

	private void drawHitboxes() {
		for (Entity entity : world.getEntities()) {
			HitboxPart hitbox = entity.getPart(HitboxPart.class);

			if (hitbox.getCollidingEntities().isEmpty()) {
				sr.setColor(Color.RED);
			} else {
				sr.setColor(Color.YELLOW);
			}

			float x = hitbox.getX();
			float y = hitbox.getY();
			float w = hitbox.getWidth() / 2;
			float h = hitbox.getHeight() / 2;

			float x1 = x + w;
			float y1 = y + h;

			float x2 = x - w;
			float y2 = y + h;

			float x3 = x - w;
			float y3 = y - h;

			float x4 = x + w;
			float y4 = y - h;

			sr.line(x1, y1, x2, y2);
			sr.line(x2, y2, x3, y3);
			sr.line(x3, y3, x4, y4);
			sr.line(x4, y4, x1, y1);
		}
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
	}

	public void addEntityProcessingService(IEntityProcessingService eps) {
		entityProcessorList.add(eps);
	}

	public void removeEntityProcessingService(IEntityProcessingService eps) {
		entityProcessorList.remove(eps);
	}

	public void addEntityCollisionDetectionService(IEntityCollisionDetectionService ecd) {
		entityCollisionDetectorList.add(ecd);
	}

	public void removeEntityCollisionDetectionService(IEntityCollisionDetectionService ecd) {
		entityCollisionDetectorList.remove(ecd);
	}

	public void addPostEntityProcessingService(IPostEntityProcessingService peps) {
		postEntityProcessorList.add(peps);
	}

	public void removePostEntityProcessingService(IPostEntityProcessingService peps) {
		postEntityProcessorList.remove(peps);
	}

	public void addGamePluginService(IGamePluginService plugin) {
		gamePluginList.add(plugin);
		plugin.start(gameData, world);
	}

	public void removeGamePluginService(IGamePluginService plugin) {
		gamePluginList.remove(plugin);
		plugin.stop(gameData, world);
	}

}

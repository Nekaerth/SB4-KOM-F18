package dk.sdu.mmmi.cbse.main;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.mmmi.cbse.common.util.SPILocator;
import dk.sdu.mmmi.cbse.managers.GameInputProcessor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Game implements ApplicationListener {

	private AssetManager manager;
	private SpriteBatch batch;
	private Sprite sprite;

	private static OrthographicCamera cam;
	private ShapeRenderer sr;

	private final GameData gameData = new GameData();
	private List<IEntityProcessingService> entityProcessors = new ArrayList<>();
	private World world = new World();

	@Override
	public void create() {

		gameData.setDisplayWidth(Gdx.graphics.getWidth());
		gameData.setDisplayHeight(Gdx.graphics.getHeight());

		cam = new OrthographicCamera(gameData.getDisplayWidth(), gameData.getDisplayHeight());
		cam.translate(gameData.getDisplayWidth() / 2, gameData.getDisplayHeight() / 2);
		cam.update();

		sr = new ShapeRenderer();

		manager = new AssetManager();
		manager.load("D:/Documents/GitHub/SB4-KOM-F18/AsteroidsServiceLoader/Core/src/main/java/dk/sdu/mmmi/cbse/main/dannebrog.jpg", Texture.class);

		manager.finishLoading();

		batch = new SpriteBatch();
		sprite = new Sprite((Texture) manager.get("D:/Documents/GitHub/SB4-KOM-F18/AsteroidsServiceLoader/Core/src/main/java/dk/sdu/mmmi/cbse/main/dannebrog.jpg"));

		Gdx.input.setInputProcessor(
						new GameInputProcessor(gameData)
		);

		// Lookup all Game Plugins using ServiceLoader
		for (IGamePluginService iGamePlugin : getPluginServices()) {
			iGamePlugin.start(gameData, world);
		}
	}

	@Override
	public void render() {

		// clear screen to black
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		gameData.setDelta(Gdx.graphics.getDeltaTime());

		update();

		draw();

		gameData.getKeys().update();
	}

	private void update() {
		// Update
		for (IEntityProcessingService entityProcessorService : getEntityProcessingServices()) {
			entityProcessorService.process(gameData, world);
		}
	}

	private void draw() {
		batch.begin();
		sprite.setSize(gameData.getDisplayWidth(), gameData.getDisplayHeight());
		sprite.draw(batch);
		batch.end();

		for (Entity entity : world.getEntities()) {

			sr.setColor(1, 1, 1, 1);

			sr.begin(ShapeRenderer.ShapeType.Line);

			float[] shapex = entity.getShapeX();
			float[] shapey = entity.getShapeY();

			for (int i = 0, j = shapex.length - 1; i < shapex.length; j = i++) {

				sr.line(shapex[i], shapey[i], shapex[j], shapey[j]);
			}

			sr.end();

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

	private Collection<? extends IGamePluginService> getPluginServices() {
		return SPILocator.locateAll(IGamePluginService.class);
	}

	private Collection<? extends IEntityProcessingService> getEntityProcessingServices() {
		return SPILocator.locateAll(IEntityProcessingService.class);
	}
}
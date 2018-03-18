package dk.sdu.mmmi.cbse.asteroidsystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;

public class AsteroidPlugin implements IGamePluginService {

	@Override
	public void start(GameData gd, World world) {

	}

	@Override
	public void stop(GameData gd, World world) {
		for (Entity asteroid : world.getEntities(Asteroid.class)) {
			world.removeEntity(asteroid);
		}
	}

}

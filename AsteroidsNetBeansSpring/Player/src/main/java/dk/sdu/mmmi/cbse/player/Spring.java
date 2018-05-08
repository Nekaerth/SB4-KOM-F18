package dk.sdu.mmmi.cbse.player;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;
import dk.sdu.mmmi.cbse.commonplayer.data.Player;
import dk.sdu.mmmi.cbse.commonspawner.services.ISpawningService;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@ServiceProviders(value = {
	@ServiceProvider(service = IEntityProcessingService.class)
	,
	@ServiceProvider(service = IPostEntityProcessingService.class)
	,
	@ServiceProvider(service = ISpawningService.class)
})
public class Spring implements IEntityProcessingService, IPostEntityProcessingService, ISpawningService<Player> {

	ApplicationContext ap;
	PlayerControlSystem pcs;
	PlayerSpawningService pss;

	boolean isInstantiated = false;

	private void instantiate() {
		ap = new ClassPathXmlApplicationContext("");
		isInstantiated = true;
	}

	@Override
	public void process(GameData gamaData, World world) {
		if (!isInstantiated) {
			instantiate();
		}

		if (pcs == null) {
			pcs = new PlayerControlSystem();
		}

		pcs.process(gamaData, world);
	}

	@Override
	public void postProcess(GameData gamaData, World world) {
		if (!isInstantiated) {
			instantiate();
		}

		if (pcs == null) {
			pcs = new PlayerControlSystem();
		}

		pcs.postProcess(gamaData, world);
	}

	@Override
	public Class getEntityType() {
		if (!isInstantiated) {
			instantiate();
		}

		if (pss == null) {
			pss = new PlayerSpawningService();
		}

		return pss.getEntityType();
	}

	@Override
	public Player createEntity(float x, float y, float radians) {
		if (!isInstantiated) {
			instantiate();
		}

		if (pss == null) {
			pss = new PlayerSpawningService();
		}

		return pss.createEntity(x, y, radians);
	}

}

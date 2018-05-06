package dk.sdu.mmmi.cbse.collisiondetection;

import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator {

	@Override
	public void start(BundleContext context) throws Exception {
		System.out.println("Collision system starting");
		context.registerService(IPostEntityProcessingService.class.getName(), new CollisionDetectionControlSystem(), null);
	}

	@Override
	public void stop(BundleContext context) throws Exception {

	}

}

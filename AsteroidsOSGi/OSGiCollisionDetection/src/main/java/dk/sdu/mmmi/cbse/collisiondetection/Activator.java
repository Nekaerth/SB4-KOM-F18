package dk.sdu.mmmi.cbse.collisiondetection;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import dk.sdu.mmmi.cbse.common.services.IEntityCollisionDetectionService;

public class Activator implements BundleActivator {

	@Override
	public void start(BundleContext context) throws Exception {
		context.registerService(IEntityCollisionDetectionService.class.getName(), new CollisionDetectionControlSystem(), null);
	}

	@Override
	public void stop(BundleContext context) throws Exception {

	}

}

package dk.sdu.mmmi.cbse.commonspawner.services;

import dk.sdu.mmmi.cbse.common.data.Entity;

public interface ISpawningService {
	
	public Class getEntityType();

	public Entity createEntity(float x, float y, float radians);

}

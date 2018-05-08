package dk.sdu.mmmi.cbse.commonspawner.services;

import dk.sdu.mmmi.cbse.common.data.Entity;

public interface ISpawningService<T extends Entity> {
	
	public Class<T> getEntityType();

	public T createEntity(float x, float y, float radians);

}

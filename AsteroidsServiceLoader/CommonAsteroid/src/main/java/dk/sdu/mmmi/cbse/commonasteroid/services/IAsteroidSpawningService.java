package dk.sdu.mmmi.cbse.commonasteroid.services;

import dk.sdu.mmmi.cbse.commonasteroid.data.Asteroid;

public interface IAsteroidSpawningService {

	public Asteroid createAsteroid(float x, float y, float radians);

}
